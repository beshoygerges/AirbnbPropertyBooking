package com.airbnb.property.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;


@Slf4j
@Aspect
@Component
public class LoggingAspect {


    @Around("execution(* com.airbnb.property.service.*.*(..))")
    public Object logMethodCall(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().toShortString();

        long startTime = System.currentTimeMillis();
        Object result;
        try {
            result = joinPoint.proceed();
        } catch (Throwable throwable) {
            log.error("Exception occurred in method {}: {}", methodName, throwable.getMessage());
            throw throwable;
        } finally {
            long executionTime = System.currentTimeMillis() - startTime;
            log.info("Method {} executed in {}", methodName, formatTime(executionTime));
        }

        return result;
    }

    private String formatTime(long milliseconds) {
        long minutes = (milliseconds / 1000) / 60;
        long seconds = (milliseconds / 1000) % 60;
        long ms = milliseconds % 1000;
        return String.format("%d minutes, %d seconds, %d ms", minutes, seconds, ms);
    }
}
