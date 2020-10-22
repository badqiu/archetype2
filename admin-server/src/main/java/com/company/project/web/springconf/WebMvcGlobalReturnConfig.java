package com.company.project.web.springconf;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.company.project.dto.RestResult;

@EnableWebMvc
@Configuration
public class WebMvcGlobalReturnConfig {

    @RestControllerAdvice
    static class ResultResponseAdvice implements ResponseBodyAdvice<Object> {
        @Override
        public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
            return true;
        }

        @Override
        public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
            if (body instanceof RestResult) {
                return body;
            }
            
            if (body instanceof ResponseEntity) {
                return (ResponseEntity)body;
            }
            
            if(body != null) {
	            String className = body.getClass().toString();
	            if(className.contains("springfox")) { //swagger-ui
	            	return body;
	            }
            }
            
            RestResult resultMap = new RestResult().success().result(body);
			return ResponseEntity.ok(resultMap);
        }
        
        
//        @Override
//        public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
//            return beforeBodyWrite0(body);
//        }

		private ResponseEntity beforeBodyWrite0(Object body) {
			if (body instanceof ResponseEntity) {
                return (ResponseEntity)body;
            }
                        
			return ResponseEntity.ok(body);
		}
    }
    
}