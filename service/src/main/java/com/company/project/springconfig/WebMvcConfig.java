package com.company.project.springconfig;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
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


/**
 * 
 * @RequestMapping的value为空时默认以方法名为匹配路径
 * SpringBoot升级到2.0以后配置文件继承的WebMvcConfigurerAdapter已废除，需要改为WebMvcConfigurationSupport
 * addInterceptors()方法覆写不变，但getRequestMappingHandlerMapping() 需要改为createRequestMappingHandlerMapping(), 自定义的RequestMappingHandlerMapping才能生效
 *    参考：https://deeplyloving.iteye.com/blog/2173927
 * @author liuchaohong
 * @date 2019年7月11日
 *
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

	private static Logger logger = LoggerFactory.getLogger(WebMvcConfig.class);

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		// 解决返回结果中午乱码问题
		super.configureMessageConverters(converters);
		converters.add(responseBodyConverter());
		converters.add(responseJsonBodyConverter());
	}

	@Bean
	public HttpMessageConverter<String> responseBodyConverter() {
		StringHttpMessageConverter converter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
		List<MediaType> mediaTypes = getMediaTypes();
		converter.setSupportedMediaTypes(mediaTypes);
		return converter;
	}

	@Bean
	public MappingJackson2HttpMessageConverter responseJsonBodyConverter() {
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		List<MediaType> mediaTypes = getMediaTypes();
		converter.setSupportedMediaTypes(mediaTypes);
		return converter;
	}
	
	private List<MediaType> getMediaTypes() {
		List<MediaType> mediaTypes = new ArrayList<MediaType>();
		mediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
		mediaTypes.add(MediaType.TEXT_HTML);
		mediaTypes.add(MediaType.TEXT_PLAIN);
		return mediaTypes;
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
                .addResourceLocations("classpath:/resources/")
                .addResourceLocations("classpath:/public/")
                .addResourceLocations("classpath:/static/");
        super.addResourceHandlers(registry);
    }
	
	/*
	 * 不需要每个方法 都需要映射 
	 */
	class CustomRequestMappingHandlerMapping extends RequestMappingHandlerMapping {  
		  
	    private boolean useSuffixPatternMatch = true;  
	  
	    private boolean useTrailingSlashMatch = true;  
	      
	    private ContentNegotiationManager contentNegotiationManager = new ContentNegotiationManager();  
	  
	    private final List<String> fileExtensions = new ArrayList<String>();  

	    @Override  
	    protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {  
	    	
	    	RequestMappingInfo info = createRequestMappingInfo(method);
	    	
	    	if (info == null) {
				return info;
			}
	    	
	    	// 获取方法的匹配路径
	        Set<String> methodPatterns = info.getPatternsCondition().getPatterns();
	        if (CollectionUtils.isNotEmpty(methodPatterns)) { 
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