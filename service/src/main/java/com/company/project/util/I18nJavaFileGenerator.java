package com.company.project.util;

import java.io.File;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.util.Assert;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.company.project.enums.Constant;
import com.github.rapid.common.util.PropertiesUtil;
import com.github.rapid.common.util.ResourceUtil;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class I18nJavaFileGenerator {
	public static String I18N_KEYS_JAVA_FILE = "src/main/java/generated/I18nKeys.java";
	
	private String project_basedir;
	
	
    public I18nJavaFileGenerator(String project_basedir) {
    	Assert.hasText(project_basedir,"project_basedir must be not blank");
		this.project_basedir = project_basedir;
	}

	public static Properties filterKeys(Properties i18nEnMap) {
        Properties filteredProperties = new Properties();
        for (String key : i18nEnMap.stringPropertyNames()) {
            if (key.contains("javax.validation.constraints")) {
                continue;
            }
            if (key.contains("org.hibernate.validator.constraints")) {
                continue;
            }
            filteredProperties.setProperty(key, i18nEnMap.getProperty(key));
        }
        return filteredProperties;
    }
    
    public static Properties loadI18nFile(String filepath) {
    	Properties r = PropertiesUtil.loadProperties(ResourceUtil.getResourceAsText(filepath));
    	return filterKeys(r);
    }
    
	public void generateI18nFile() throws Exception {
		
		Properties i18nEnMap = loadI18nFile("/"+Constant.I18N_MESSAGE_SOURCE_BASENAME+".properties");
		Properties i18nZhCNMap = loadI18nFile("/"+Constant.I18N_MESSAGE_SOURCE_BASENAME+"_zh_CN.properties");
		
//		System.out.println("i18nZhCNMap:"+i18nZhCNMap);
		
		Configuration cfg = new Configuration();
		String templateStr = ResourceUtil.getResourceAsText("/i18n_key_java_template.ftl");
		Template template = new Template("i18n_key_java_template",new StringReader(templateStr),cfg);
		Map model = new HashMap();
		model.put("i18nEnMap", i18nEnMap);
		model.put("i18nZhCNMap", i18nZhCNMap);
		
		Map i18nMessageParamMap = new HashMap();
		i18nEnMap.forEach((key,value) -> {
			Map params = parseI18nValueParams((String)value);
			i18nMessageParamMap.put(key, params);
		});
		model.put("i18nMessageParamMap", i18nMessageParamMap);
		
		String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
		
		File i18nFile = new File(project_basedir,I18N_KEYS_JAVA_FILE);
		System.out.println("generatei18nFile() file:"+i18nFile);
		FileUtils.writeStringToFile(i18nFile, content,"UTF-8");
	}
	
    public static Map<String, String> parseI18nValueParams(String input) {
    	if(StringUtils.isBlank(input)) {
    		return new HashMap();
    	}
    	
        Map<String, String> result = new HashMap<>();
        // 定义正则表达式来匹配所有 {参数名} 格式的字符串
        Pattern pattern = Pattern.compile("\\{([^}]+)\\}");
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            String paramName = matcher.group(1);
            // 假设参数值紧跟在 } 后面，以空格分隔
            int startIndex = matcher.end();
            int endIndex = input.indexOf(" ", startIndex);
            if (endIndex == -1) {
                endIndex = input.length();
            }
            String paramValue = input.substring(startIndex, endIndex);
            result.put(paramName, paramValue);
        }
        return result;
    }
    
}
