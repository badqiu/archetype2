package com.company.project.web.springconf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import com.company.project.binder.CustomWebBindingInitializer;


@Configuration
public class WebBindingInitializerConfig {

    @Autowired
    public void setWebBindingInitializer(RequestMappingHandlerAdapter requestMappingHandlerAdapter) {
        //将自定义的CustomWebBindingInitializer属性编辑器绑定到RequestMappingHandlerAdapter里面.
    	requestMappingHandlerAdapter.setWebBindingInitializer(new CustomWebBindingInitializer());
    }

}