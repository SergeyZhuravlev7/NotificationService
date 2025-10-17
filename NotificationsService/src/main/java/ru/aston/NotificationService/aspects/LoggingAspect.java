package ru.aston.NotificationService.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {

    @AfterReturning ("execution(* ru.aston.NotificationService.services..* (..)) || execution(* ru.aston.NotificationService.controllers..* (..))")
    public void logSuccess(JoinPoint joinPoint) {
        Logger log = LoggerFactory.getLogger(joinPoint
                .getTarget()
                .getClass());
        log.info("Successfully executed the method: {} with args {}",
                joinPoint
                        .getSignature()
                        .getName(),
                joinPoint.getArgs());
    }

    @AfterThrowing ("execution(* ru.aston.NotificationService.services..* (..))")
    public void logThrowing(JoinPoint joinPoint) {
        Logger log = LoggerFactory.getLogger(joinPoint
                .getTarget()
                .getClass());
        log.error("Method {} with args {} throw exception",
                joinPoint
                        .getSignature()
                        .getName(),
                joinPoint.getArgs());
    }

    @AfterThrowing ("execution(* ru.aston.NotificationService.controllers..* (..))")
    public void logController(JoinPoint joinPoint) {
        Logger log = LoggerFactory.getLogger(joinPoint
                .getTarget()
                .getClass());
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        StringBuilder sb = new StringBuilder();
        for (Object arg : args) {
            if (arg != null && ! arg
                    .getClass()
                    .getSimpleName()
                    .contains("Binding")) {
                sb
                        .append(arg.getClass())
                        .append(" ")
                        .append(arg)
                ;
            }
        }
        log.error("Method {} with args {} throw exception.", methodName, sb);
    }

}
