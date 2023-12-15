package com.company.project.controller;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.company.project.dto.HtmlInput;
import com.company.project.enums.Constant;
import com.company.project.model.BaseEntity;
import com.github.rapid.common.util.ScanClassUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Api(tags={"系统枚举"})
@RequestMapping("/enum")
@RestController
public class EnumController {

	Map allEnumMap;
	Map entityAllInputMap;
	Map entityAllModelMap;
	
	@GetMapping
	public Map<String,Map> getAllEnum() throws Exception {
		if(allEnumMap == null) {
			String basePackages = Constant.class.getPackage().getName();
			allEnumMap = scanAllEnum(basePackages);
			
			allEnumMap.put(Constant.class.getSimpleName(),getStaticFieldMap(Constant.class));
			allEnumMap.put("fields", getEntityAllInput());
			allEnumMap.put("models", getAllEntity());
		}
		
		return allEnumMap;
	}
	
	@GetMapping
	public Map<String,Map> getEntityAllInput() throws Exception {
		if(entityAllInputMap == null) {
			String basePackages = BaseEntity.class.getPackage().getName();
			entityAllInputMap = scanAllEntityForInput(basePackages);
		}
		
		return entityAllInputMap;
	}
	
	@GetMapping
	public Map<String,Map> getAllEntity() throws Exception {
		if(entityAllModelMap == null) {
			String basePackages = BaseEntity.class.getPackage().getName();
			entityAllModelMap = scanAllEntity(basePackages);
		}
		
		return entityAllModelMap;
	}

	//是否安全敏感数据
	public static boolean isSecuritySensitiveData(String name) {
		if(StringUtils.isBlank(name)) {
			return false;
		}
		String lowerName = name.toLowerCase();
		return lowerName.contains("access") || lowerName.contains("username") || lowerName.contains("password") || lowerName.contains("token");
	}
	
	private Map scanAllEntity(String basePackages) throws ClassNotFoundException {
		List<String> classList = ScanClassUtil.scanPackages(basePackages);
		Map result = new LinkedHashMap();
		for(String cls : classList) {
			Class clazz = Class.forName(cls);
			ApiModel am = (ApiModel)clazz.getAnnotation(ApiModel.class);
			if(am != null) {
				result.put(clazz.getSimpleName(), am.description());
			}
		}
		return result;
	}

	private Map scanAllEntityForInput(String basePackages) throws ClassNotFoundException {
		List<String> classList = ScanClassUtil.scanPackages(basePackages);
		Map result = new LinkedHashMap();
		for(String cls : classList) {
			Class clazz = Class.forName(cls);
			for(Field field : clazz.getDeclaredFields()) {
				ApiModelProperty mp = field.getAnnotation(ApiModelProperty.class);
				HtmlInput input = HtmlInput.from(mp,field);
				if(input != null){ 
					result.put(field.getName(), input);
				}
			}
		}
		return result;
	}

	public static Object getStaticFieldMap(Class clazz) throws IllegalArgumentException, IllegalAccessException {
		Field[] fields = Constant.class.getDeclaredFields();
		Map map = new LinkedHashMap();
		for(Field f : fields) {
			if(Modifier.isStatic(f.getModifiers())){
				String name = f.getName();
				Object value = f.get(clazz);

				if(isSecuritySensitiveData(name)) {
					continue;
				}
				
				map.put(name, value);
			}
		}
		return map;
	}

	public static Map scanAllEnum(String basePackages) throws ClassNotFoundException {
		List<String> classList = ScanClassUtil.scanPackages(basePackages);
		Map allEnumMap = new LinkedHashMap();
		for(String cls : classList) {
			Class clazz = Class.forName(cls);
			if(clazz.isEnum()) {
				Map map = enums2Map(clazz);
				allEnumMap.put(clazz.getSimpleName(), map);
			}
		}
		return allEnumMap;
	}

	public static Map enums2Map(Class clazz) {
		if(!clazz.isEnum()) {
			return null;
		}
		
		Enum[] enumList = (Enum[])clazz.getEnumConstants();
		Map map = new LinkedHashMap();
		for(Enum e : enumList) {
			map.put(e.name(), e.name());
		}
		return map;
	}
	
}
