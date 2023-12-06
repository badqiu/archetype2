package com.company.project.springconfig;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.stereotype.Component;

@Component
public class EnvironmentUtil implements InitializingBean{
	@Autowired
	Environment environment;
	
	private static Environment staticEnvironment;
	
	public static boolean acceptsProfiles(Profiles profiles) {
		return getEnvironment().acceptsProfiles(profiles);
	}
	
	public static boolean acceptsProfiles(String... profiles) {
		return getEnvironment().acceptsProfiles(Profiles.of(profiles));
	}
	
	public static Environment getEnvironment() {
		return staticEnvironment;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		staticEnvironment = environment;
	}
	
	
}
