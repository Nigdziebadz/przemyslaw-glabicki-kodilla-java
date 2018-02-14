package com.kodilla.patterns2.facade.api;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ShopWatcher {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShopWatcher.class);

    @Before("execution(* com.kodilla.patterns2.facade.api.OrderFacade.processOrder(..))"
            + "&& target(object)")
    public void logProcessOrder(Object object) {
        LOGGER.info("Class: " + object.getClass().getName());
    }

    @Around("execution(* com.kodilla.patterns2.facade.api.OrderFacade.processOrder(..))")
    public Object catchTime(final ProceedingJoinPoint proceedingJoinPoint) throws  Throwable {
        Object time;
        try {
            long start = System.currentTimeMillis();
            time = proceedingJoinPoint.proceed();
            long end = System.currentTimeMillis();
            LOGGER.info("Time consumed: " + (end - start) + "[ms]");
        } catch (Throwable throwable) {
            LOGGER.error(throwable.getMessage());
            throw throwable;
        }
        return time;
    }
}