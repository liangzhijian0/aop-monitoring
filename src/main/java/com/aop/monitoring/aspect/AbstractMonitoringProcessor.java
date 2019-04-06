package com.aop.monitoring.aspect;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * @author Ocean Liang
 * @date 4/4/2019
 */
public abstract class AbstractMonitoringProcessor {
    public abstract void onSuccess(ProceedingJoinPoint joinPoint, Long costTime);

    public abstract void onError(ProceedingJoinPoint joinPoint, Long costTime, Throwable throwable);
}
