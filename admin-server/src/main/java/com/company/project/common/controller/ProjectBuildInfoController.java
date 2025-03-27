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

	@GetMapping
	public Properties buildInfo() throws Exception {
		String buildInfoProps = ResourceUtil.getResourceAsText("/"+Build.BUILD_INFO_NAME);
		Properties props = PropertiesUtil.loadProperties(buildInfoProps);
		return props;
	}
	
}
