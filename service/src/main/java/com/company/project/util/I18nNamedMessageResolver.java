package com.company.project.util;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import com.github.rapid.common.util.MapUtil;

public class I18nNamedMessageResolver implements MessageSourceAware {

    private static final Pattern PARAMETER_PATTERN = Pattern.compile("\\{([^}]+)\\}");

    private MessageSource messageSource;
    
    public I18nNamedMessageResolver() {
    }
    
	public I18nNamedMessageResolver(MessageSource messageSource) {
		super();
		this.messageSource = messageSource;
	}

	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

    public String getMessage(String code, Map<String, Object> params) {
    	Locale locale = LocaleContextHolder.getLocale();
        try {
            String rawMessage = messageSource.getMessage(code, null, locale);
            return replaceNamedParameters(rawMessage, params);
        } catch (NoSuchMessageException e) {
            return code;
        }
    }
    
    public String getNamedMessage(String code, String... keyValuePairs) {
    	Map params = MapUtil.newMap(keyValuePairs);
    	return getMessage(code,params);
    }

    private String replaceNamedParameters(String message, Map<String, Object> namedParams) {
        Matcher matcher = PARAMETER_PATTERN.matcher(message);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            String paramName = matcher.group(1);
            Object paramValue = namedParams.get(paramName);
            if (paramValue != null) {
                matcher.appendReplacement(sb, paramValue.toString());
            } else {
                matcher.appendReplacement(sb, matcher.group(0));
            }
        }
        matcher.appendTail(sb);
        return sb.toString();
    }


}    