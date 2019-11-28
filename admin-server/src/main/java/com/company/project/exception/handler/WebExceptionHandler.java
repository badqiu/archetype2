package com.company.project.exception.handler;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity unknownException(Exception e) {
        log.error("unknownException", e);
        
        Map<String,String> result = new HashMap<String,String>();
        result.put("errCode", e.getClass().getSimpleName());
        result.put("errMsg", e.getMessage());
        
        return ResponseEntity.ok(result);
    }
    
    @ExceptionHandler
    public ResponseEntity securityException(SecurityException e) {
//        log.error("error on SecurityException", e);
        return unknownException(e);
    }
}
