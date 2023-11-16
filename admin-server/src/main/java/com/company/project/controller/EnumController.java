package com.company.project.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.rapid.common.util.ScanClassUtil;

import io.swagger.annotations.Api;

@Api("查询所有枚举 API")
@RequestMapping("/enum")
public class EnumController {

	Map allEnumMap;
	@PostMapping
	public Map<String,Map> getAllEnum() throws ClassNotFoundException {
		if(allEnumMap == null) {
			String basePackages = "com.bigdata.ai.enums";
			allEnumMap = scanAllEnum(basePackages);
		}
		return allEnumMap;
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
			map.put(e.name(), e);
		}
		return map;
	}
	
}
