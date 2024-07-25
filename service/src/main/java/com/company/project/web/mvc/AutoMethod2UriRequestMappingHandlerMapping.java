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

		logger.info("class={}, requestMethod={}, methodName={}, methodPatterns={}",
				handlerType.getTypeName(), info.getMethodsCondition(), method.getName(),
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

	String excludePackage = "springfox*"; // swagger-ui
	AntPathMatcher antPathMatcher = new AntPathMatcher();

	private boolean isExcludeHandlerPackage(Class<?> handlerType) {
		String className = handlerType.getTypeName();
		if (antPathMatcher.match(excludePackage, className)) {
			return true;
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
		String[] patterns = resolveEmbeddedValuesInPatterns(annotation.value());
		if (patterns != null && (patterns.length == 0)) {
			patterns = new String[] { method.getName() };
		}
		return new RequestMappingInfo(
				new PatternsRequestCondition(patterns, getUrlPathHelper(), getPathMatcher(), this.useSuffixPatternMatch,
						this.useTrailingSlashMatch, this.fileExtensions),
				new RequestMethodsRequestCondition(annotation.method()),
				new ParamsRequestCondition(annotation.params()), new HeadersRequestCondition(annotation.headers()),
				new ConsumesRequestCondition(annotation.consumes(), annotation.headers()), new ProducesRequestCondition(
						annotation.produces(), annotation.headers(), this.contentNegotiationManager),
				customCondition);
	}

}