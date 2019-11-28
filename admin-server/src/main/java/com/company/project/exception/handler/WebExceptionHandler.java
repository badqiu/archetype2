package com.company.project.exception.handler;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@ResponseBody
public class WebExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(WebExceptionHandler.class);

    @ExceptionHandler
    public ResponseEntity unknownException(Exception e) {
        log.error("unknownException", e);
        Map result = new HashMap();
        result.put("errCode", e.getClass().getSimpleName());
        result.put("errMsg", e.getMessage());
        // 发送邮件通知技术人员.
        return ResponseEntity.ok(result);
    }
    
    @ExceptionHandler
    public ResponseEntity securityException(SecurityException e) {
        log.error("error on SecurityException", e);
        return unknownException(e);
    }
}
