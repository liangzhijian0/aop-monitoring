package com.aop.monitoring.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;

/**
 * @author Ocean Liang
 * @date 4/7/2019
 */
@Component
@Slf4j
public class SpecialMonitoringProcessor extends AbstractMonitoringProcessor {
    @Override
    public void onSuccess(ProceedingJoinPoint joinPoint, Long costTime) {
        log.info("success:this is special monitoring");
    }

    @Override
    public void onError(ProceedingJoinPoint joinPoint, Long costTime, Throwable throwable) {
        log.error("failed:this is special monitoring");
    }
}
