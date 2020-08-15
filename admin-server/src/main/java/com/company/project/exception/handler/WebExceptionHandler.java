package com.company.project.exception.handler;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常处理
 * 
 * @author badqiu
 */

@ControllerAdvice
@ResponseBody
public class WebExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(WebExceptionHandler.class);

    @ExceptionHandler
    public Map exception(Exception e) {
        log.error("exception", e);
        return errorMap(e, e.getMessage());
    }

	private Map errorMap(Exception e, String errMsg) {
		return errorMap(e.getClass().getSimpleName(),errMsg);
	}
	
	private Map errorMap(String errCode, String errMsg) {
		Map<String,String> result = new HashMap<String,String>();
		result.put("errCode", errCode);
		result.put("errMsg", errMsg);
        
        return result;
	}
    
    @ExceptionHandler
    public Map securityException(SecurityException e) {
        return exception(e);
    }
    
    @ExceptionHandler
    public Map constraintViolationException(ConstraintViolationException e) {
    	Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
    	if(constraintViolations == null) {
    		return errorMap(e,e.getMessage());
    	}
    	
		String errMsg =  constraintViolations.stream()
		.map( cv -> cv == null ? "null" : cv.getPropertyPath() + ": " + cv.getMessage() )
		.collect( Collectors.joining( ", " ) );
		
		Map errMap = new HashMap();
		constraintViolations.forEach( row -> { 
			errMap.put(row.getPropertyPath(), row.getMessage());
		});
		
		Map map =  errorMap(e,errMsg);
		map.put("errMap",errMap);
		return map;
    }
    
}

