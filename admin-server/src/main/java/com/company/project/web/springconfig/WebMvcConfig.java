package com.company.project.web.springconfig;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.company.project.web.mvc.AutoMethod2UriRequestMappingHandlerMapping;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.github.rapid.common.json.jackson.BigintToStringSerializer;


/**
 * 主要功能:  约定大于配置的URL映射。 
 * 自动映射: 
 * @RequestMapping  stop()  => /stop
 * 
 * 
 * SpringBoot升级到2.0以后配置文件继承的WebMvcConfigurerAdapter已废除，需要改为WebMvcConfigurationSupport
 * addInterceptors()方法覆写不变，但getRequestMappingHandlerMapping() 需要改为createRequestMappingHandlerMapping(), 自定义的RequestMappingHandlerMapping才能生效
 *    参考：https://deeplyloving.iteye.com/blog/2173927
 *    
 * @author badqiu
 * @date 2019年7月11日
 *
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

	private static Logger logger = LoggerFactory.getLogger(WebMvcConfig.class);

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		// 解决返回结果乱码问题
		super.configureMessageConverters(converters);
//		converters.add(responseBodyConverter());
		converters.add(responseJsonBodyConverter());
	}

	public HttpMessageConverter<String> responseBodyConverter() {
		StringHttpMessageConverter converter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
		List<MediaType> mediaTypes = new ArrayList<MediaType>();
		mediaTypes.add(MediaType.TEXT_HTML);
		mediaTypes.add(MediaType.TEXT_PLAIN);
		converter.setSupportedMediaTypes(mediaTypes);
		return converter;
	}

	public MappingJackson2HttpMessageConverter responseJsonBodyConverter() {
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter(objectMapper());
		List<MediaType> mediaTypes = new ArrayList<MediaType>();
		mediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
		converter.setSupportedMediaTypes(mediaTypes);
		return converter;
	}
	

	private ObjectMapper objectMapper() {
//		ObjectMapper objectMapper2 = Jackson2ObjectMapperBuilder.json().build();
	    ObjectMapper objectMapper = new ObjectMapper();
	    
	    // 不序列化null的属性
//	    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
//	    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//	    objectMapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
//	    JavaTimeModule javaTimeModule = new JavaTimeModule();
	    SimpleModule javaTimeModule = new SimpleModule();
	    javaTimeModule.addSerializer(Long.class, BigintToStringSerializer.instance);
	    javaTimeModule.addSerializer(Long.TYPE, BigintToStringSerializer.instance);
	    
//	    javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(Constants.DEFAULT_DATE_TIME_FORMAT)));
//	    javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(Constants.DEFAULT_DATE_FORMAT)));
//	    javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern(Constants.DEFAULT_TIME_FORMAT)));
//	    javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(Constants.DEFAULT_DATE_TIME_FORMAT)));
//	    javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(Constants.DEFAULT_DATE_FORMAT)));
//	    javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern(Constants.DEFAULT_TIME_FORMAT)));
	    objectMapper.registerModule(javaTimeModule).registerModule(new ParameterNamesModule());
//	    objectMapper.setDateFormat(new SimpleDateFormat(Constants.DEFAULT_DATE_TIME_FORMAT));
	    return objectMapper;
	}

	
	@Override
	protected RequestMappingHandlerMapping createRequestMappingHandlerMapping() {
		// controller映射加强, 自动映射: @RequestMapping  stop()  => /stop
		return new AutoMethod2UriRequestMappingHandlerMapping();
	}

	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// 访问静态资源
        registry.addResourceHandler("/**")
				//project
                .addResourceLocations("classpath:/resources/")
                .addResourceLocations("classpath:/public/")
                .addResourceLocations("classpath:/static/");
        
        /** swagger-ui 3 地址 */
        registry.addResourceHandler("/swagger-ui/**")
        .addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/");
        
        super.addResourceHandlers(registry);
    }
	

	
}
