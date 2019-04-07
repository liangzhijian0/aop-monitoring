### Aop-Monitoring
#### aop监控的两种方式
- @Around("execution(xxx)")
- @Around("@annotation(xxx)")

#### joinPoint
- 类名：joinPoint.getTarget().getClass().getName() 
- 方法名：joinPoint.getSignature().getName();
- 具体的哪个类中的哪个方法（带包名和方法参数）：

((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(xxx.class)

#### annotation类可以实现方法并且指定默认类，从而一个annotation有多个processor处理不同的实现
- Class<? extends xxx> process() default xxx.class 
- 使用：@Monitoring(process = xxx.class)
