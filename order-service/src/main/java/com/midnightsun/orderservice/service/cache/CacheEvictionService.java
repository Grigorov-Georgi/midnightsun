package com.midnightsun.orderservice.service.cache;

import com.midnightsun.orderservice.config.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Set;

@Slf4j
@Service
public class CacheEvictionService {

    private final RedisTemplate<String, String> redisTemplate;

    public CacheEvictionService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Scheduled(cron = "0 0/10 * * * *")
    public void invalidateProductsCache() {
        log.debug("Scheduled job to delete products cache started");
        Set<String> keysToDelete = redisTemplate.keys(String.format("%s****", Constants.PRODUCT_PREFIX));
        if (keysToDelete != null && !keysToDelete.isEmpty()) {
            redisTemplate.delete(keysToDelete);
        }
    }
}
