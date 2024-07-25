package com.company.project.web.exception.handler;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.project.dto.RestResult;
import com.company.project.enums.Constant;

import io.swagger.annotations.ApiModelProperty;

/**
 * 统一异常处理
 * 
 * @author badqiu
 */

@ControllerAdvice
@ResponseBody
public class WebExceptionHandler implements InitializingBean{

    private static final Logger log = LoggerFactory.getLogger(WebExceptionHandler.class);

    @Autowired
	private Environment environment;
    boolean enabledShowErrorLog = false;
    
    @ExceptionHandler
    public RestResult exception(Exception e) {
        log.warn("exception", e);
        return errorMap(e, e.getMessage());
    }

	private RestResult errorMap(Exception e, String errMsg) {
		return errorMap(e.getClass().getSimpleName(),errMsg,ExceptionUtils.getStackTrace(e));
	}
	
	private RestResult errorMap(String errCode, String errMsg,String stackTrace) {
		
		RestResult result = new RestResult();
		result.fail(errMsg);
		result.errCode(errCode);
		
		if(enabledShowErrorLog) {
			result.setErrorLog(stackTrace);
		}
        
        return result;
	}
    
    @ExceptionHandler
    public RestResult securityException(SecurityException e) {
        return exception(e);
    }
    
    @ExceptionHandler
    public RestResult duplicateKeyException(DuplicateKeyException e) {
        return errorMap(e,"数据出现重复,"+e.getMessage());
    }
    
    
    @ExceptionHandler
    public RestResult constraintViolationException(ConstraintViolationException e) {
    	Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
    	if(constraintViolations == null) {
    		return errorMap(e,e.getMessage());
    	}
    	
//		String errMsg =  constraintViolations.stream()
//		.map( cv -> cv == null ? "null" : ConstraintViolationUtil.getPropertyPathName(cv) + ": " + cv.getMessage() )
//		.collect( Collectors.joining( ", " ) );
		
		Map errMap = new HashMap();
		constraintViolations.forEach( row -> { 
			errMap.put(ConstraintViolationUtil.getPropertyPathName(row), row.getPropertyPath().toString() + " " + row.getMessage());
		});
		
		RestResult r =  errorMap(e,errMap.toString());
		r.data(errMap);
		return r;
    }
    
    static class ConstraintViolationUtil {
	    private static String getPropertyPathName(ConstraintViolation<?> row) {
	    	ApiModelProperty amp = getFieldApiModelPropertyAnnotation(row);
	    	if(amp == null) {
	    		return row.getPropertyPath().toString();
	    	}
	    	return amp.value();
		}
	    
		private static ApiModelProperty getFieldApiModelPropertyAnnotation(ConstraintViolation<?> row) {
			Field field = null;
			try {
				field = row.getRootBeanClass().getDeclaredField(row.getPropertyPath().toString());
			} catch (NoSuchFieldException e1) {
				e1.printStackTrace();
				return null;
			} catch (SecurityException e1) {
				e1.printStackTrace();
				return null;
			}
			ApiModelProperty amp = field.getAnnotation(ApiModelProperty.class);
			return amp;
		}
    }

	@Override
	public void afterPropertiesSet() throws Exception {
		enabledShowErrorLog = environment.acceptsProfiles(Constant.SHOW_ERROR_LOG_FOR_HTTP_RESPONSE);
	}
	
}

