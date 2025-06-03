package com.company.project.springconfig;

import org.springframework.boot.validation.MessageInterpolatorFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.github.rapid.common.util.holder.BeanValidatorHolder;

import jakarta.validation.Validator;

@Configuration
public class ValidatorConfig {

    @Bean
    public Validator validator(MessageSource messageSource) {
        LocalValidatorFactoryBean factoryBean = new LocalValidatorFactoryBean();
        MessageInterpolatorFactory interpolatorFactory = new MessageInterpolatorFactory();
        factoryBean.setMessageInterpolator(interpolatorFactory.getObject());
        factoryBean.setValidationMessageSource(messageSource);
        return factoryBean;
    }
    
    @Bean
    public BeanValidatorHolder beanValidatorHolder(Validator validator) {
        BeanValidatorHolder beanValidatorHolder = new BeanValidatorHolder();
        beanValidatorHolder.setValidator(validator);
		return beanValidatorHolder;
    }
    
}
