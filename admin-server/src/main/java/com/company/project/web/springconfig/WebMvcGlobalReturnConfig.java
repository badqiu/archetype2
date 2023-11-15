package com.company.project.web.springconfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.company.project.dto.RestResult;

/** MVC统一返回值处理 */
@Configuration
public class WebMvcGlobalReturnConfig {

    @RestControllerAdvice(basePackages = "com.company.project")
    static class ResultResponseAdvice implements ResponseBodyAdvice<Object> {
        @Override
        public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        	if(methodParameter.hasMethodAnnotation(ResponseBody.class)) {
        		return false;
        	}
            return true;
        }

        @Override
        public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
            if (body instanceof RestResult) {
                return body;
            }
            
            if (body instanceof ResponseEntity) {
                return (ResponseEntity)body;
            }
            
            //字符串，不处理会发生 ClassCastException
            if(returnType.getMethod().getReturnType() == String.class) {
            	return body;
            }
            
            if(body != null) {
	            String className = body.getClass().toString();
	            if(className.contains("springfox")) { //swagger-ui
	            	return body;
	            }
            }
            
            RestResult r = new RestResult().success().result(body);
            
            
			return r;
        }
        
    }
    
}