package com.company.project.web.binder;

import java.util.Date;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;



/**
 * @Description 扩展web初始化的配置,WebBindingInitializer实现全局属性编辑器配置
 */
public class CustomWebBindingInitializer implements WebBindingInitializer {

    @Override
    public void initBinder(WebDataBinder binder) {
    	binder.registerCustomEditor(Date.class, new SmartCustomDateEditor());
    	binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

}