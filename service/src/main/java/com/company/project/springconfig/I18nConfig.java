package com.company.project.springconfig;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

import com.company.project.enums.Constant;

/**
 * 国际化配置
 * 
 * @author badqiu
 *
 */
@Configuration
public class I18nConfig {

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource source = new ResourceBundleMessageSource();
        source.setDefaultEncoding("UTF-8");//读取配置文件的编码格式
        source.setCacheMillis(-1);//缓存时间，-1表示不过期
        source.setBasename(Constant.I18N_MESSAGE_SOURCE_BASENAME);//配置文件前缀名，设置为Messages,那你的配置文件必须以Messages.properties/Message_en.properties...
        return source;
    }
    
}
