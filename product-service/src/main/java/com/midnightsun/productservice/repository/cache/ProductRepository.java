package com.midnightsun.productservice.repository.cache;

import com.midnightsun.productservice.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import static com.midnightsun.productservice.config.Constants.*;

@Slf4j
@Repository
public class ProductRepository {

    private final RedisTemplate<String, String> redisTemplate;

    public ProductRepository(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void save(Product product) {
        String key = generateKey(product.getId());
        redisTemplate.opsForHash().put(PRODUCT_CACHE_HASH_NAME, key, product);
    }

    private String generateKey(Long id) {
        return PRODUCT_CACHE_PREFIX + id;
    }
}
