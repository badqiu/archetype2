package com.company.project.web.springconfig;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.github.rapid.common.json.jackson.BigintToStringSerializer;
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
    
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//		converters.clear();
//		converters.addFirst(responseJsonBodyConverter());
//		converters.addFirst(new ByteArrayHttpMessageConverter());
		
        converters.removeIf(converter -> converter instanceof StringHttpMessageConverter);
	        
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
	    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	    objectMapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false);
	    
	    //support json5
	    objectMapper.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
	    objectMapper.configure(Feature.ALLOW_SINGLE_QUOTES, true);
	    objectMapper.configure(Feature.ALLOW_COMMENTS, true);
	    
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