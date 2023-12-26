package com.company.project.web.springconfig;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.condition.ConsumesRequestCondition;
import org.springframework.web.servlet.mvc.condition.HeadersRequestCondition;
import org.springframework.web.servlet.mvc.condition.ParamsRequestCondition;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.ProducesRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.company.project.util.BigintToStringSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;


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
		converters.add(responseBodyConverter());
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
		// controller映射加强
		return new CustomRequestMappingHandlerMapping();
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
        
        
        /** swagger-ui 2 地址 */
 	   // 解决静态资源无法访问
// 	   registry.addResourceHandler("/**")
// 	       .addResourceLocations("classpath:/static/");
// 	   // 解决SWAGGER无法访问
// 	   registry.addResourceHandler("/swagger-ui.html")
// 	       .addResourceLocations("classpath:/META-INF/resources/");
// 	   // 解决SWAGGER的JS文件无法访问
// 	   registry.addResourceHandler("/webjars/**")
// 	       .addResourceLocations("classpath:/META-INF/resources/webjars/");
        
        
        
        super.addResourceHandlers(registry);
    }
	
	/*
	 * 不需要每个方法 都需要映射 
	 */
	static class CustomRequestMappingHandlerMapping extends RequestMappingHandlerMapping {  
		  
	    private boolean useSuffixPatternMatch = true;  
	  
	    private boolean useTrailingSlashMatch = true;  
	      
	    private ContentNegotiationManager contentNegotiationManager = new ContentNegotiationManager();  
	  
	    private final List<String> fileExtensions = new ArrayList<String>();  

	    @Override  
	    protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {  
	    	
	    	RequestMappingInfo info = createRequestMappingInfo(method);
	    	
	    	if (info == null) {
				return null;
			}
	    	
	    	// 获取方法的匹配路径
	        Set<String> methodPatterns = info.getPatternsCondition().getPatterns();
	        if (isNotEmpty(methodPatterns) || isExcludeHandlerPackage(handlerType)) { 
				info = super.getMappingForMethod(method, handlerType); // 走原来的方法
			} else { 
				RequestMapping methodAnnotation = AnnotationUtils.findAnnotation(method, RequestMapping.class);  
				if (methodAnnotation != null) {  
					RequestCondition<?> methodCondition = getCustomMethodCondition(method);  
					info = createRequestMappingInfo(methodAnnotation, methodCondition, method);  
					RequestMapping typeAnnotation = AnnotationUtils.findAnnotation(handlerType, RequestMapping.class);  
					if (typeAnnotation != null) {  
						RequestCondition<?> typeCondition = getCustomTypeCondition(handlerType);  
						info = createRequestMappingInfo(typeAnnotation, typeCondition, method).combine(info);  
					}  
				}  
			}
	        
	        WebMvcConfig.logger.info("class={}, requestMethod={}, methodName={}, methodPatterns={}", handlerType.getTypeName(), info.getMethodsCondition(), method.getName(), info.getPatternsCondition().getPatterns());
	    	return info;
	    }


		private boolean isNotEmpty(Set<String> set) {
			if(set == null) return false;
			
			if(set.isEmpty()) {
				return false;
			}
			
			if("[]".equals(set.toString())) {
				return false;
			}
			
			return true;
		}  
	    
	    String excludePackage = "springfox*"; //swagger-ui
	    AntPathMatcher antPathMatcher = new AntPathMatcher();
		private boolean isExcludeHandlerPackage(Class<?> handlerType) {
			String className = handlerType.getTypeName();
			if(antPathMatcher.match(excludePackage, className)) {
				return true;
			}
			
			return false;
		}  
		

		private RequestMappingInfo createRequestMappingInfo(AnnotatedElement element) {
			RequestMapping requestMapping = AnnotatedElementUtils.findMergedAnnotation(element, RequestMapping.class);
			RequestCondition<?> condition = (element instanceof Class ?
					getCustomTypeCondition((Class<?>) element) : getCustomMethodCondition((Method) element));
			return (requestMapping != null ? createRequestMappingInfo(requestMapping, condition) : null);
		}
		
		private RequestMappingInfo createRequestMappingInfo(RequestMapping annotation, RequestCondition<?> customCondition, Method method) {  
	        String[] patterns = resolveEmbeddedValuesInPatterns(annotation.value());  
	        if(patterns != null && (patterns.length == 0)){  
	            patterns = new String[]{method.getName()};  
	        }  
	        return new RequestMappingInfo(  
	                new PatternsRequestCondition(patterns, getUrlPathHelper(), getPathMatcher(),  
	                        this.useSuffixPatternMatch, this.useTrailingSlashMatch, this.fileExtensions),  
	                new RequestMethodsRequestCondition(annotation.method()),  
	                new ParamsRequestCondition(annotation.params()),  
	                new HeadersRequestCondition(annotation.headers()),  
	                new ConsumesRequestCondition(annotation.consumes(), annotation.headers()),  
	                new ProducesRequestCondition(annotation.produces(), annotation.headers(), this.contentNegotiationManager),  
	                customCondition);  
	    }  
	    
	}
	
}
