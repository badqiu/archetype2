package com.company.project.web.springconfig;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
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
    
    //FIXME: 存在问题：不支持LocalDate LocalDateTime, Date时间是输出：1749128044130
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//		converters.clear();
		converters.addFirst(responseJsonBodyConverter());
		converters.addFirst(new ByteArrayHttpMessageConverter());
		
        converters.removeIf(converter -> converter instanceof StringHttpMessageConverter);
	        
	}
	
	
	public MappingJackson2HttpMessageConverter responseJsonBodyConverter() {
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter(objectMapper());
		List<MediaType> mediaTypes = new ArrayList<MediaType>();
		mediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
		converter.setSupportedMediaTypes(mediaTypes);
		return converter;
	}
	

	public static ObjectMapper objectMapper() {
//		ObjectMapper objectMapper2 = Jackson2ObjectMapperBuilder.json().build();
	    ObjectMapper objectMapper = new ObjectMapper();
	    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	    objectMapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false);
	    
	    //support json5
	    objectMapper.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
	    objectMapper.configure(Feature.ALLOW_SINGLE_QUOTES, true);
	    objectMapper.configure(Feature.ALLOW_COMMENTS, true);
	    
	    JavaTimeModule javaTimeModule = buildJavaTimeModule();
	    
	    //不序列化null的属性
	    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
	    
//	    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//	    objectMapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
	    
	    SimpleModule simpleModule = new SimpleModule();
	    simpleModule.addSerializer(Long.class, BigintToStringSerializer.instance);
	    simpleModule.addSerializer(Long.TYPE, BigintToStringSerializer.instance);
	    
	    objectMapper.registerModule(javaTimeModule).registerModule(simpleModule).registerModule(new ParameterNamesModule());
//	    objectMapper.setDateFormat(new SimpleDateFormat(Constants.DEFAULT_DATE_TIME_FORMAT));
	    return objectMapper;
	}

	public static JavaTimeModule buildJavaTimeModule() {
	    JavaTimeModule javaTimeModule = new JavaTimeModule();
	    
	    DateTimeFormatter localDateTimeFormater = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	    DateTimeFormatter localDateFormater = DateTimeFormatter.ISO_LOCAL_DATE;
	    DateTimeFormatter localTimeFormater = DateTimeFormatter.ISO_LOCAL_TIME;
	    
		javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(localDateTimeFormater));
		javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(localDateTimeFormater));

		javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(localDateFormater));
		javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(localDateFormater));

		javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(localTimeFormater));
		javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(localTimeFormater));
	    
		return javaTimeModule;
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