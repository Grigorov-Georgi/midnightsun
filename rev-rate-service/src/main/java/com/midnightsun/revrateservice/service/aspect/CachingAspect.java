package com.midnightsun.revrateservice.service.aspect;

import com.midnightsun.revrateservice.service.RatingService;
import com.midnightsun.revrateservice.service.ReviewService;
import com.midnightsun.revrateservice.service.cache.CacheService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

@Slf4j
@Aspect
@Component
public class CachingAspect {

    private final CacheService cacheService;
    private final RatingService ratingService;
    private final ReviewService reviewService;

    public CachingAspect(CacheService cacheService, RatingService ratingService, ReviewService reviewService) {
        this.cacheService = cacheService;
        this.ratingService = ratingService;
        this.reviewService = reviewService;
    }

    @Pointcut("execution(* com.midnightsun.revrateservice.service.*.save (..)) ||" +
            "execution(* com.midnightsun.revrateservice.service.*.update (..))")
    public void saveOrUpdateMethods() {}

    @Pointcut("execution(* com.midnightsun.revrateservice.service.*.delete (..))")
    public void deleteMethods() {}

    @AfterReturning(value = "saveOrUpdateMethods()", returning = "result")
    public void afterSaveOperationAdvice(JoinPoint joinPoint, Object result) throws NoSuchMethodException {
        Class<?> clazz = joinPoint.getArgs()[0].getClass();
        Method getProductId = clazz.getMethod("getProductId");
        try {
            UUID productId = (UUID) getProductId.invoke(joinPoint.getArgs()[0]);
            String serviceName = joinPoint.getSignature().getDeclaringType().getSimpleName();
            updateCache(serviceName, productId);
        } catch (Exception e) {
            log.error("Wrong class is targeted within the CacheAspect. Check {}", joinPoint.getSignature().getDeclaringType().getSimpleName());
        }
    }

    @Around("deleteMethods()")
    public Object afterDeleteOperationAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        Long entityId = Long.parseLong(joinPoint.getArgs()[0].toString());
        String serviceName = joinPoint.getSignature().getDeclaringType().getSimpleName();
        UUID productId = getProductId(serviceName, entityId);
        Object returnValue = joinPoint.proceed();
        updateCache(serviceName, productId);
        return returnValue;
    }

    private void updateCache(String serviceName, UUID productId) {
        switch (serviceName) {
            case "ReviewService":
                cacheService.updateProductReviews(productId);
                break;
            case "RatingService":
                cacheService.updateProductAverageRating(productId);
                break;
        }
    }

    private UUID getProductId(String serviceName, Long entityId) {
        UUID productId = null;
        if ("ReviewService".equals(serviceName)) {
            productId = reviewService.getOne(entityId).getProductId();
        } else if ("RatingService".equals(serviceName)) {
            productId = ratingService.getOne(entityId).getProductId();
        }
        return productId;
    }

    //The value returned by the around advice is the return value seen by the caller of the method. For example, a
    // simple caching aspect could return a value from a cache if it has one or invoke proceed() (and return that value)
    // if it does not.
}
