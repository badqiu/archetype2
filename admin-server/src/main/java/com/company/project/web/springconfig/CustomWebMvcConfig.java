package com.company.project.web.springconfig;
import java.util.List;

import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.company.project.util.HttpMessageConverterUtil;
import com.github.rapid.common.web.mvc.annotation.AutoMethod2PathAnnotationHandlerMapping;

/**
 * 主要功能:  约定大于配置的URL映射。 
 * 自动映射: 
 * @RequestMapping  stop()  => /stop
 * 
 * 
 * @author badqiu
 *
 */
@Configuration
public class CustomWebMvcConfig implements WebMvcRegistrations, WebMvcConfigurer{
    
    @Override
    public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
        AutoMethod2PathAnnotationHandlerMapping r = new AutoMethod2PathAnnotationHandlerMapping();
		return r;
    }
    
    //FIXME: 存在问题：不支持LocalDate LocalDateTime, Date时间是输出：1749128044130
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.removeIf(converter -> converter instanceof StringHttpMessageConverter);
//		converters.removeIf(converter -> converter instanceof ByteArrayHttpMessageConverter);
		converters.removeIf(c -> c instanceof MappingJackson2HttpMessageConverter);
		 
		//converters.clear();
		converters.addLast(HttpMessageConverterUtil.jsonMessageConverter());
//		converters.addFirst(new ByteArrayHttpMessageConverter());
	}
	
	
//	@Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//		// 访问静态资源
//        registry.addResourceHandler("/**")
//				//project
//                .addResourceLocations("classpath:/resources/")
//                .addResourceLocations("classpath:/public/")
//                .addResourceLocations("classpath:/static/")
//                .addResourceLocations("classpath:/META-INF/resources/")
//                ;
//    }
    
}