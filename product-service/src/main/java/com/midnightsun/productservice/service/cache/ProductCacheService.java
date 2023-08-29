package com.midnightsun.productservice.service.cache;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.midnightsun.productservice.model.Product;
import com.midnightsun.productservice.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.midnightsun.productservice.config.Constants.*;

@Slf4j
@Service
public class ProductCacheService {

    private final ProductRepository productRepository;
    private final ObjectMapper objectMapper;

    private final HashOperations hashOperations;
    private final SetOperations setOperations;

    public ProductCacheService(ProductRepository productRepository,
                               RedisTemplate<String, String> redisTemplate,
                               ObjectMapper objectMapper) {
        this.productRepository = productRepository;
        this.objectMapper = objectMapper;
        this.hashOperations = redisTemplate.opsForHash();
        this.setOperations = redisTemplate.opsForSet();
    }

    public Product save(Product product) {
        //TODO: Make that operations in a transactional context
        //TODO: Implement more reliable solution for invalidation products in the cache
        if (product.getId() == null) {
            generateAndSetId(product);
        }

        String key = generateKey(product.getId());
        String value = convertProductIntoString(product);

        if (value != null) {
            addToCache(key, value);
            makeItDirty(key);
        }

        return product;
    }

    public Product findById(UUID id) {
        final var productAsObject = hashOperations.get(PRODUCT_CACHE_HASH_NAME, generateKey(id));

        Product product;
        if (productAsObject != null) {
            log.debug("Cache hit");
            product = convertIntoProduct(productAsObject);
        } else {
            log.debug("Cache miss");
            product = productRepository.findById(id).orElse(null);

            if (product != null && product.getId() != null) {
                String key = generateKey(product.getId());
                String value = convertProductIntoString(product);
                addToCache(key, value);
            }
        }
        return product;
    }

    public void deleteById(UUID id) {
        String key = generateKey(id);
        hashOperations.delete(PRODUCT_CACHE_HASH_NAME, key);
        setOperations.remove(PRODUCT_CACHE_DIRTY_SET, key);
        productRepository.deleteById(id);
    }

    private void addToCache(String key, String value) {
        hashOperations.put(PRODUCT_CACHE_HASH_NAME, key, value);
    }

    private void makeItDirty(String key) {
        setOperations.add(PRODUCT_CACHE_DIRTY_SET, key);
    }

    @Scheduled(cron = "0 0/2 * * * *")
    public void synchronizeAndInvalidateProducts() {
        Set<String> dirtyProducts = setOperations.members(PRODUCT_CACHE_DIRTY_SET);
        List<Object> dirtyProductsAsObjects = dirtyProducts.stream().map(p -> (Object) p).collect(Collectors.toList());
        List<Object> products = hashOperations.multiGet(PRODUCT_CACHE_HASH_NAME, dirtyProductsAsObjects);

        if (products != null && !products.isEmpty()) {
            List<Product> savedProducts = productRepository.saveAll(castProducts(products));

            log.debug("Total products in cache: {}, Total products saved/updated into database {}",
                    products.size(),
                    savedProducts.size());
        }

        invalidateProducts();
    }

    private void invalidateProducts() {
        log.debug("Invalidating cache for products");
        final var productKeysInHash = hashOperations.keys(PRODUCT_CACHE_HASH_NAME);
        productKeysInHash.stream().forEach(p -> hashOperations.delete(PRODUCT_CACHE_HASH_NAME, p));

        final var dirtyProductsKeysInSet = setOperations.members(PRODUCT_CACHE_DIRTY_SET);
        dirtyProductsKeysInSet.stream().forEach(p -> setOperations.remove(PRODUCT_CACHE_DIRTY_SET, p));
    }

    private List<Product> castProducts(List<Object> stringProducts) {
        return stringProducts.stream()
                .filter(p -> convertIntoProduct(p) != null)
                .map(this::convertIntoProduct)
                .collect(Collectors.toList());
    }

    private Product generateAndSetId(Product product) {
        final var id = UUID.randomUUID();
        product.setId(id);
        return product;
    }

    private String generateKey(UUID id) {
        return PRODUCT_CACHE_PREFIX + id;
    }

    private Product convertIntoProduct(Object value) {
        try {
            return objectMapper.readValue(String.valueOf(value), Product.class);
        } catch (JsonProcessingException e) {
            log.error("Error converting String into Product: {}", value);
            log.error(e.getMessage());
            return null;
        }
    }

    private String convertProductIntoString(Product product) {
        try {
            return objectMapper.writeValueAsString(product);
        } catch (JsonProcessingException e) {
            log.error("Error converting Product into String: {}", product);
            log.error(e.getMessage());
            return null;
        }
    }
}
