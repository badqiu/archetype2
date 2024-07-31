package com.company.project.web.mvc;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.bind.annotation.RequestMapping;
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
 * 主要功能:  约定大于配置的URL映射。 
 * 自动映射: 
 * @RequestMapping  stop()  => /stop
 */
public class AutoMethod2UriRequestMappingHandlerMapping extends RequestMappingHandlerMapping {
	private static Logger logger = LoggerFactory.getLogger(AutoMethod2UriRequestMappingHandlerMapping.class);

	private boolean useSuffixPatternMatch = true;

	private boolean useTrailingSlashMatch = true;

	private ContentNegotiationManager contentNegotiationManager = new ContentNegotiationManager();

	private final List<String> fileExtensions = new ArrayList<String>();

	private String[] excludePackages = new String[]{
			"springfox*", // swagger-ui
			"org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController"
			}; 
	
	private AntPathMatcher antPathMatcher = new AntPathMatcher();
	
	
	public boolean isUseSuffixPatternMatch() {
		return useSuffixPatternMatch;
	}

	public void setUseSuffixPatternMatch(boolean useSuffixPatternMatch) {
		this.useSuffixPatternMatch = useSuffixPatternMatch;
	}

	public boolean isUseTrailingSlashMatch() {
		return useTrailingSlashMatch;
	}

	public void setUseTrailingSlashMatch(boolean useTrailingSlashMatch) {
		this.useTrailingSlashMatch = useTrailingSlashMatch;
	}

	public String[] getExcludePackages() {
		return excludePackages;
	}

	public void setExcludePackages(String[] excludePackages) {
		this.excludePackages = excludePackages;
	}

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

		logger.info("url mapping: {}.{}()  =>\t {} {}",
				handlerType.getTypeName(), method.getName(),info.getMethodsCondition(),
				info.getPatternsCondition().getPatterns());
		return info;
	}

	private boolean isNotEmpty(Set<String> set) {
		if (set == null)
			return false;

		if (set.isEmpty()) {
			return false;
		}

		if ("[]".equals(set.toString())) {
			return false;
		}

		return true;
	}

	private boolean isExcludeHandlerPackage(Class<?> handlerType) {
		String className = handlerType.getTypeName();
		for(String excludePackage : excludePackages) {
			if (antPathMatcher.match(excludePackage, className)) {
				return true;
			}
		}

		return false;
	}

	private RequestMappingInfo createRequestMappingInfo(AnnotatedElement element) {
		RequestMapping requestMapping = AnnotatedElementUtils.findMergedAnnotation(element, RequestMapping.class);
		RequestCondition<?> condition = (element instanceof Class ? getCustomTypeCondition((Class<?>) element)
				: getCustomMethodCondition((Method) element));
		return (requestMapping != null ? createRequestMappingInfo(requestMapping, condition) : null);
	}

	private RequestMappingInfo createRequestMappingInfo(RequestMapping annotation, RequestCondition<?> customCondition,
			Method method) {
		String[] strPatterns = resolveEmbeddedValuesInPatterns(annotation.value());
		if (strPatterns != null && (strPatterns.length == 0)) {
			strPatterns = new String[] { method.getName() };
		}
		
		RequestMethodsRequestCondition methods = new RequestMethodsRequestCondition(annotation.method());
		ParamsRequestCondition params = new ParamsRequestCondition(annotation.params());
		HeadersRequestCondition headers = new HeadersRequestCondition(annotation.headers());
		ConsumesRequestCondition consumes = new ConsumesRequestCondition(annotation.consumes(), annotation.headers());
		ProducesRequestCondition produces = new ProducesRequestCondition(
				annotation.produces(), annotation.headers(), this.contentNegotiationManager);
		PatternsRequestCondition patterns = new PatternsRequestCondition(strPatterns, getUrlPathHelper(), getPathMatcher(), this.useSuffixPatternMatch,
				this.useTrailingSlashMatch, this.fileExtensions);
		
		return new RequestMappingInfo(
				patterns,
				methods,
				params, headers,
				consumes, produces,
				customCondition);
	}

}