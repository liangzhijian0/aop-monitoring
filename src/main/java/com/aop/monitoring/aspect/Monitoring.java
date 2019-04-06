package com.aop.monitoring.aspect;

import java.lang.annotation.*;

/**
 * @author Ocean Liang
 * @date 4/4/2019
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Monitoring {
    Class<? extends AbstractMonitoringProcessor> process() default DefaultMonitoringProcessor.class;
}
