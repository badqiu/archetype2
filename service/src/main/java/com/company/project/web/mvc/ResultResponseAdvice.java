package com.company.project.web.mvc;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import com.company.project.dto.RestResult;
import com.company.project.util.LogTraceUtil;

/**
 * 统一spring web mvc返回值
 */
public class ResultResponseAdvice implements ResponseBodyAdvice<Object> {
        @Override
        public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        	if(methodParameter.hasMethodAnnotation(ResponseBody.class)) {
        		return false;
        	}
            return true;
        }

        @Override
        public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
            Object r = beforeBodyWrite0(body, returnType);
            if (r instanceof RestResult) {
            	RestResult tmp = (RestResult)r;
				tmp.setTraceId(LogTraceUtil.getTraceId());
            }
            return r;
        }

		private Object beforeBodyWrite0(Object body, MethodParameter returnType) {
			if (body instanceof RestResult) {
                return body;
            }
            
            if (body instanceof ResponseEntity) {
                return body;
            }
            
            //SSE流式响应,SseEmitter
            if (body instanceof ResponseBodyEmitter) {
                return body;
            }
            
            if(body != null) {
	            String className = body.getClass().getName();
	            if(className.contains("springfox")) { //swagger-ui
	            	return body;
	            }
            }
            
            RestResult r = new RestResult().success().data(body);
            
			return r;
		}
        
    }