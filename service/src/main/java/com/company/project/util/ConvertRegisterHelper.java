package com.company.project.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.apache.commons.beanutils.converters.BigIntegerConverter;
import org.apache.commons.beanutils.converters.BooleanConverter;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.beanutils.converters.DateTimeConverter;
import org.apache.commons.beanutils.converters.DoubleConverter;
import org.apache.commons.beanutils.converters.FloatConverter;
import org.apache.commons.beanutils.converters.IntegerConverter;
import org.apache.commons.beanutils.converters.LongConverter;
import org.apache.commons.beanutils.converters.ShortConverter;
import org.apache.commons.beanutils.converters.SqlDateConverter;
import org.apache.commons.beanutils.converters.SqlTimeConverter;
import org.apache.commons.beanutils.converters.SqlTimestampConverter;
import org.springframework.beans.factory.InitializingBean;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.github.rapid.common.beanutils.converter.StringConverter;

/**
 * 注册Converter, 用于apache commons BeanUtils.copyProperties()方法中的class类型转换;
 * 
 * 可以修改此处代码以添加新的Converter
 * @author badqiu
 */
public class ConvertRegisterHelper implements InitializingBean {

	public static String[] datePattern = new String[] {
			"yyyy-MM-dd",
			"yyyy-MM-dd HH:mm:ss",
			"yyyy-MM-dd HH:mm:ss.SSS",
			"HH:mm:ss"
			//"EEE MMM dd HH:mm:ss 'CST' yyyy"
			};

	static EnumConvertUtilsBean convertUtilsBean = new EnumConvertUtilsBean();
	static BeanUtilsBean instance = new BeanUtilsBean(convertUtilsBean);
	static {
		BeanUtilsBean.setInstance(instance);
		registerConverters(datePattern);
	}
	
	public static void registerConverters(String... datePatterns) {
		registerConverters(convertUtilsBean,datePatterns);
	}
	
	public static void registerConverters(ConvertUtilsBean convertUtils) {
		registerConverters(convertUtils,datePattern);
	}
	
	public static void registerConverters(ConvertUtilsBean convertUtils,String... datePatterns) {
		convertUtils.register(new StringConverter(), String.class);
		
		//date 
		convertUtils.register(ConvertRegisterHelper.setPatterns(new DateConverter(null),datePatterns),java.util.Date.class);
		convertUtils.register(ConvertRegisterHelper.setPatterns(new SqlDateConverter(null),datePatterns),java.sql.Date.class);
		convertUtils.register(ConvertRegisterHelper.setPatterns(new SqlTimeConverter(null),datePatterns),Time.class);
		convertUtils.register(ConvertRegisterHelper.setPatterns(new SqlTimestampConverter(null),datePatterns),Timestamp.class);
		
		//number
		convertUtils.register(new BooleanConverter(null), Boolean.class);
		convertUtils.register(new ShortConverter(null), Short.class);
		convertUtils.register(new IntegerConverter(null), Integer.class);
		convertUtils.register(new LongConverter(null), Long.class);
		convertUtils.register(new FloatConverter(null), Float.class);
		convertUtils.register(new DoubleConverter(null), Double.class);
		convertUtils.register(new BigDecimalConverter(null), BigDecimal.class); 
		convertUtils.register(new BigIntegerConverter(null), BigInteger.class);	
	}
	
	public static <T extends DateTimeConverter> T setPatterns(T converter ,String... patterns) {
		converter.setPatterns(patterns);
		return converter;
	}

	@Override
	public void afterPropertiesSet()  {
		registerConverters(datePattern);
	}
	
	public static class EnumConvertUtilsBean extends ConvertUtilsBean {
	    @Override
	    public Object convert(String value, Class clazz) {
	        if (clazz.isEnum()) {
	        	if(StringUtils.isBlank(value)) {
		    		return null;
		    	}
	            return Enum.valueOf(clazz, value);
	        } else {
	            return super.convert(value, clazz);
	        }
	    }
	}
	
}
