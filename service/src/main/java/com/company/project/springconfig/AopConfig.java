package com.company.project.springconfig;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AopConfig {
	static Logger logger = LoggerFactory.getLogger(AopConfig.class);
	
	// 定义一个切入点(第一个*表示返回值，第二个*表示任意类，第三个*表示任意方法，(..)表示任意参数)
    @Pointcut("execution(* com.company.project.service.*.*(..))")
    public void logServicePerf(){
    }
    
	// 环绕通知
    @Around("logServicePerf()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        Signature signature = pjp.getSignature();
		String methodName = signature.getName();
        long start = System.currentTimeMillis();
        
        Object result = pjp.proceed();
        
        long end = System.currentTimeMillis();
        long cost = end - start;
		logger.info(signature.getDeclaringType() + "." + methodName + " method cost timeMills:" + cost);
        return result;
    }
    
}
