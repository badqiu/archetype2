package com.company.project.web.springconfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.company.project.web.mvc.ResultResponseAdvice;

/** MVC统一返回值处理 */
@Configuration
public class WebMvcGlobalReturnConfig {

    @RestControllerAdvice
    public static class CustomResultResponseAdvice extends ResultResponseAdvice{
    }
    
}