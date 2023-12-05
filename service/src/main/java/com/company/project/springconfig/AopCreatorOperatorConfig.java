package com.company.project.springconfig;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.company.project.common.security.ActionSecurityUtil;
import com.github.rapid.common.beanutils.BeanUtils;

/**
 * 为对象统一注入 creator,operator,createTime,updateTime,enabled
 */
@Aspect
@Component
public class AopCreatorOperatorConfig {
	private static Logger logger = LoggerFactory.getLogger(AopCreatorOperatorConfig.class);
	
    @Pointcut("execution(* com.bigdata.ai.service.*.updateByManual(..))")
    public void serviceUpdate(){
    }
    
    @Pointcut("execution(* com.bigdata.ai.service.*.create(..))")
    public void serviceCreate(){
    }
    
    @Pointcut("serviceUpdate() || serviceCreate()")
    private void serviceUpdateCreate(){}
    
    
    @Around("serviceUpdateCreate()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
    	
        Signature signature = pjp.getSignature();
		String methodName = signature.getName();

		Object[] args = pjp.getArgs();
        Object target = args[0];
        
        Map<String,Object> sourceProps = newBeanProps(methodName);
        
//        BeanUtils.copyProperties(beanProps, firstArg);
        BeanUtils.copyProperties(target,sourceProps);
        logger.info("around for method:"+methodName+"."+signature.getDeclaringTypeName()+" beanProps:"+sourceProps+" firstArgs:"+target);
        
        Object result = pjp.proceed();
        
        return result;
    }

	private Map<String,Object> newBeanProps(String methodName) {
		Map<String,Object> beanProps = new HashMap<String,Object>();
		String username = ActionSecurityUtil.getLoginUser(getRequest()).getUsername();
        
		if("create".equals(methodName)){
			beanProps.put("creator", username);
	        beanProps.put("createTime",new Date());
	        beanProps.put("enabled",true);
        }
		
		beanProps.put("operator",username);
    	beanProps.put("updateTime",new Date());
        
		return beanProps;
	}
	
	public static HttpServletRequest getRequest() {
		return getContextServletRequestAttributes().getRequest();
	}
	
	public static HttpServletResponse getResponse() {
		return getContextServletRequestAttributes().getResponse();
	}

	public static ServletRequestAttributes getContextServletRequestAttributes() {
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		return servletRequestAttributes;
	}
}
