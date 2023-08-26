package com.midnightsun.productservice.service.cache;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.midnightsun.productservice.model.Product;
import com.midnightsun.productservice.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.midnightsun.productservice.config.Constants.*;

@Slf4j
@Service
public class CacheBufferRepository {

    private final ProductRepository productRepository;
    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    public CacheBufferRepository(ProductRepository productRepository,
                                 RedisTemplate<String, String> redisTemplate) {
        this.productRepository = productRepository;
        this.redisTemplate = redisTemplate;
        this.objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    public Product save(Product product) {
        final var tempID = generateTemporaryUUID();
        final var key = String.format("%s%s", PRODUCT_SAVE_KEY_PREFIX, tempID);
        final var value = stringifyValue(product);

        if (StringUtils.isNotBlank(key) && StringUtils.isNotBlank(value)) {
            redisTemplate.opsForValue().set(key, value);
        }

        log.debug("Saved product into 'save' cache buffer {}", product);
        return product;
    }

    public Product update(Product product) {
        final var key = String.format("%s%s", PRODUCT_UPDATE_KEY_PREFIX, product.getId());
        final var value = stringifyValue(product);

        if (StringUtils.isNotBlank(key) && StringUtils.isNotBlank(value)) {
            redisTemplate.opsForValue().set(key, value);
        }

        log.debug("Saved product into 'save' cache buffer {}", product);
        return product;
    }

    //    @Scheduled(cron = "0 0/1 * * * *")
    @Scheduled(cron = "0/10 * * * * *")
    public void batchSaveCachedProductsToDatabase() {
        log.debug("Executing batch operation to save all products from 'save' cache buffer into database");
        final var pattern = buildKeyPattern(PRODUCT_SAVE_KEY_PREFIX, 36);
        persistProductsIntoDatabase(pattern);
    }

    //    @Scheduled(cron = "0 0/1 * * * *")
    @Scheduled(cron = "0/10 * * * * *")
    public void batchUpdateCachedProductsToDatabase() {
        log.debug("Executing batch operation to save all products from 'update' cache buffer into database");
        final var pattern = buildKeyPattern(PRODUCT_UPDATE_KEY_PREFIX, 4);
        persistProductsIntoDatabase(pattern);
    }

    private void persistProductsIntoDatabase(String pattern) {
        final var keys = redisTemplate.keys(pattern);
        if (keys != null && !keys.isEmpty()) {

            final var productsFromCache = redisTemplate.opsForValue().multiGet(keys);
            if (productsFromCache != null && !productsFromCache.isEmpty()) {

                List<Product> savedProducts = productRepository.saveAll(castProducts(productsFromCache));

                deleteProductsFromCache(keys);

                log.debug("Total products in cache: {}, Total products saved/updated into database {}",
                        keys.size(),
                        savedProducts.size());
            }
        }
    }

    private void deleteProductsFromCache(Set<String> keys) {
        keys.forEach(redisTemplate::delete);
    }

    private List<Product> castProducts(List<String> stringProducts) {
        return stringProducts.stream()
                .filter(p -> convertIntoProduct(p) != null)
                .map(this::convertIntoProduct)
                .collect(Collectors.toList());
    }

    private String buildKeyPattern(String prefix, Integer idLength) {
        return prefix + "*".repeat(idLength);
    }

    private Product convertIntoProduct(String value) {
        try {
            return objectMapper.readValue(value, Product.class);
        } catch (JsonProcessingException e) {
            log.error("Error processing the Product: {}", value);
            return null;
        }
    }

    private String stringifyValue(Product product) {
        try {
            return objectMapper.writeValueAsString(product);
        } catch (JsonProcessingException e) {
            log.error("Error processing the Product: {}", product.toString());
            return null;
        }
    }

    private String generateTemporaryUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
