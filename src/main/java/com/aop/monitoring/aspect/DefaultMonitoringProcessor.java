package com.aop.monitoring.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;

/**
 * @author Ocean Liang
 * @date 4/6/2019
 */
@Component
@Slf4j
public class DefaultMonitoringProcessor extends AbstractMonitoringProcessor {
    @Override
    public void onSuccess(ProceedingJoinPoint joinPoint, Long costTime) {
        log.info(getMethodName(joinPoint) + " cost " + costTime + " ms");
    }

    @Override
    public void onError(ProceedingJoinPoint joinPoint, Long costTime, Throwable throwable) {
        log.error(getMethodName(joinPoint) + " cost " + costTime + " ms, but occurs exception: " + throwable.toString());
    }

    private String getMethodName(ProceedingJoinPoint joinPoint) {
        return joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName();
    }
}
