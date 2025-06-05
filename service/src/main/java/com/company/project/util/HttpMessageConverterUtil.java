package com.company.project.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
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

public class HttpMessageConverterUtil {
	
	public static MappingJackson2HttpMessageConverter jsonMessageConverter() {
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter(objectMapper());
		List<MediaType> mediaTypes = new ArrayList<MediaType>();
		mediaTypes.add(MediaType.APPLICATION_JSON);
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
	    
	    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//	    objectMapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
	    
	    SimpleModule simpleModule = buildBigintModule();
	    
	    objectMapper.registerModule(javaTimeModule).registerModule(simpleModule).registerModule(new ParameterNamesModule());
//	    objectMapper.setDateFormat(new SimpleDateFormat(Constants.DEFAULT_DATE_TIME_FORMAT));
	    return objectMapper;
	}


	private static SimpleModule buildBigintModule() {
		SimpleModule simpleModule = new SimpleModule();
	    simpleModule.addSerializer(Long.class, BigintToStringSerializer.instance);
	    simpleModule.addSerializer(Long.TYPE, BigintToStringSerializer.instance);
		return simpleModule;
	}

	public static JavaTimeModule buildJavaTimeModule() {
	    JavaTimeModule javaTimeModule = new JavaTimeModule();
	    
	    DateTimeFormatter localDateTimeFormater = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
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
}
