package com.company.project.common.controller;

import java.util.Properties;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.rapid.common.util.PropertiesUtil;
import com.github.rapid.common.util.ResourceUtil;

import maven_build.Build;

@RestController
@RequestMapping("/projectBuildInfo")
public class ProjectBuildInfoController {
	
	public static Properties buildInfoProps = null;
	
	@GetMapping
	public Properties buildInfo() throws Exception {
		if(buildInfoProps == null) {
			String buildInfoPropsText = ResourceUtil.getResourceAsText("/"+Build.BUILD_INFO_XML_FILE);
			buildInfoProps = PropertiesUtil.loadProperties(buildInfoPropsText);
		}
		return buildInfoProps;
	}
	
}
