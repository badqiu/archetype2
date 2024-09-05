package com.company.project.util;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.github.rapid.common.util.DateConvertUtil;

import io.swagger.annotations.ApiModelProperty;

public class EasyExcelUtil {
	
	public static <T> void  writeExcel2Response(HttpServletResponse response,List<T> list,Class<T> head)  {
		setResponseHeaders(response, head.getSimpleName());
		
		OutputStream outputStream = null;
		try {
	        outputStream = response.getOutputStream();
			LinkedHashMap<String, String> headMap = getHeadMapByClass(head);
			buildExcelWriterSheetBuilder(outputStream,headMap).doWrite(list);
		}catch(IOException e) {
			throw new RuntimeException("writeExcel2Response error,head:"+head,e);
		}finally {
			IOUtils.closeQuietly(outputStream);
		}
	}

	public static <T> void  writeExcel2Response(HttpServletResponse response,List<T> list,String filenamePrefix,LinkedHashMap<String, String> head)  {
		setResponseHeaders(response, filenamePrefix);
		
		OutputStream outputStream = null;
		try {
	        outputStream = response.getOutputStream();
			buildExcelWriterSheetBuilder(outputStream,head).doWrite(list);
		}catch(IOException e) {
			throw new RuntimeException("writeExcel2Response error,head:"+head,e);
		}finally {
			IOUtils.closeQuietly(outputStream);
		}
	}
	
	private static <T> void setResponseHeaders(HttpServletResponse response, String filenamePrefix) {
		String date = DateConvertUtil.format(new Date(), "yyyyMMdd_HHmmss");
		String finalFileName = filenamePrefix + "_" + date + ".xls";
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + finalFileName);
	}
	
	public static <T> ExcelWriterSheetBuilder buildExcelWriterSheetBuilder(OutputStream outputStream,LinkedHashMap<String, String> head) {
		List<List<String>> headList = getEasyExcelHeadByMap(head);
		return EasyExcel.write(outputStream)
		.head(headList)
		.inMemory(true)
		.autoTrim(true)
        .excelType(ExcelTypeEnum.XLS)
        .sheet("sheet1");
	}
	
	
	public static List<List<String>> getEasyExcelHeadByMap(Map<String, String> apiModelProperties) {
		List<List<String>> head = new ArrayList<List<String>>();
		apiModelProperties.forEach((key,value) -> {
			head.add(Arrays.asList(value));
		});
		return head;
	}
	
	public static LinkedHashMap<String,String> getHeadMapByClass(Class<?> clazz) {
		LinkedHashMap<String, String> apiModelProperties = new LinkedHashMap<String, String>();  
        // 遍历TaskLog类的所有字段  
        Field[] fields = clazz.getDeclaredFields();  
        for (Field field : fields) {  
        	String fieldName = field.getName();
        	if(Modifier.isStatic(field.getModifiers())) {
        		continue;
        	}
        	
            String fieldDesc = getFieldDesc(field, fieldName);
            apiModelProperties.put(fieldName, fieldDesc);  
        } 
        return apiModelProperties;
	}

	private static String getFieldDesc(Field field, String fieldDefaultDesc) {
		// 获取字段上的ApiModelProperty注解  
		ApiModelProperty apiModelProperty = field.getAnnotation(ApiModelProperty.class);  
		ExcelProperty excelProperty = field.getAnnotation(ExcelProperty.class);  
		
		String fieldDesc = fieldDefaultDesc;
		if (apiModelProperty != null) {  
			String tempFieldDesc = apiModelProperty.value();
			if(StringUtils.isNotBlank(tempFieldDesc)) {
				fieldDesc = tempFieldDesc;
			}
		}
		
		if(excelProperty != null && ArrayUtils.isNotEmpty(excelProperty.value())) {
			String tempFieldDesc = excelProperty.value()[0];
			if(StringUtils.isNotBlank(tempFieldDesc)) {
				fieldDesc = tempFieldDesc;
			}
		}
		return fieldDesc;
	}
	
}
