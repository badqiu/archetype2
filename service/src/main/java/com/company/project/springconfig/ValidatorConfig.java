package com.company.project.springconfig;

import javax.validation.Validator;

import org.springframework.boot.validation.MessageInterpolatorFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.github.rapid.common.util.holder.BeanValidatorHolder;

@Configuration
public class ValidatorConfig {

    @Bean
    public MessageSource validationMessageSource() {
        ResourceBundleMessageSource source = new ResourceBundleMessageSource();
        source.setDefaultEncoding("UTF-8");//读取配置文件的编码格式
        source.setCacheMillis(-1);//缓存时间，-1表示不过期
        source.setBasename("ValidationMessages");//配置文件前缀名，设置为Messages,那你的配置文件必须以Messages.properties/Message_en.properties...
        return source;
    }
    
    @Bean
    public Validator validator() {
        LocalValidatorFactoryBean factoryBean = new LocalValidatorFactoryBean();
        MessageInterpolatorFactory interpolatorFactory = new MessageInterpolatorFactory();
        factoryBean.setMessageInterpolator(interpolatorFactory.getObject());
        factoryBean.setValidationMessageSource(validationMessageSource());
        return factoryBean;
    }
    
    @Bean
    public BeanValidatorHolder beanValidatorHolder() {
        BeanValidatorHolder beanValidatorHolder = new BeanValidatorHolder();
        beanValidatorHolder.setValidator(validator());
		return beanValidatorHolder;
    }
    
}
