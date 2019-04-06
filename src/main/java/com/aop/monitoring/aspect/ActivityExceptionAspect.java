package com.aop.monitoring.aspect;

import com.aop.monitoring.dto.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author Ocean Liang
 * @date 3/10/2019
 */
@Aspect
@Component
@Slf4j
public class ActivityExceptionAspect implements ApplicationContextAware {
    private ApplicationContext applicationContext;

    @Around("execution(* com.aop.monitoring.controller.ActivityController.*(..))")
    public Object duringExecuteActivity(ProceedingJoinPoint joinPoint) {
        try {
            return joinPoint.proceed();
        } catch (Throwable e) {
            log.error(String.format("thrown by %s, %s", joinPoint.getSignature().toShortString(), e.getMessage()));
            return ResponseDto.fail("execute activity has exception");
        }
    }

    @Around("@annotation(com.aop.monitoring.aspect.Monitoring)")
    public void duringMethod(ProceedingJoinPoint joinPoint) {
        Long startTime = System.currentTimeMillis();
        AbstractMonitoringProcessor monitoringProcessor = getMonitoringProcessor(joinPoint);
        try {
            joinPoint.proceed();
            monitoringProcessor.onSuccess(joinPoint, System.currentTimeMillis() - startTime);
        } catch (Throwable throwable) {
            monitoringProcessor.onError(joinPoint, System.currentTimeMillis() - startTime, throwable);
        }
    }

    private AbstractMonitoringProcessor getMonitoringProcessor(ProceedingJoinPoint joinPoint) {
        Class<? extends AbstractMonitoringProcessor> processClass = ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(Monitoring.class).process();
        return applicationContext.getBean(processClass);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
