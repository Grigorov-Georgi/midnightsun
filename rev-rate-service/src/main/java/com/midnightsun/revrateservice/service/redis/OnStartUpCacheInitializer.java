package com.midnightsun.revrateservice.service.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OnStartUpCacheInitializer implements ApplicationRunner {

    private final CacheService cacheService;

    public OnStartUpCacheInitializer(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    @Override
    public void run(ApplicationArguments args) {
        cacheService.updateAllProductAverageScoreCache();
        cacheService.updateAllProductReviewsCache();
    }
}
