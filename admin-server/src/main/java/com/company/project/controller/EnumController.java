package com.company.project.controller;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.company.project.dto.HtmlInput;
import com.company.project.enums.Constant;
import com.company.project.model.BaseEntity;
import com.github.rapid.common.util.ScanClassUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Api("查询所有枚举 API")
@RequestMapping("/enum")
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

	private Map scanAllEntity(String basePackages) throws ClassNotFoundException {
		List<String> classList = ScanClassUtil.scanPackages(basePackages);
		Map result = new HashMap();
		for(String cls : classList) {
			Class clazz = Class.forName(cls);
			ApiModel am = (ApiModel)clazz.getAnnotation(ApiModel.class);
			if(am != null) {
				result.put(clazz.getSimpleName(), am);
			}
		}
		return result;
	}

	private Map scanAllEntityForInput(String basePackages) throws ClassNotFoundException {
		List<String> classList = ScanClassUtil.scanPackages(basePackages);
		Map result = new HashMap();
		for(String cls : classList) {
			Class clazz = Class.forName(cls);
			for(Field field : clazz.getFields()) {
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
		Map map = new HashMap();
		for(Field f : fields) {
			if(Modifier.isStatic(f.getModifiers())){
				map.put(f.getName(), f.get(clazz));
			}
		}
		return map;
	}

	public static Map scanAllEnum(String basePackages) throws ClassNotFoundException {
		List<String> classList = ScanClassUtil.scanPackages(basePackages);
		Map allEnumMap = new HashMap();
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
		Map map = new HashMap();
		for(Enum e : enumList) {
			map.put(e.name(), e.name());
		}
		return map;
	}
	
}
