package com.company.project.util;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.stereotype.Component;

@Component
public class EnvironmentUtil implements InitializingBean{
	@Autowired
	Environment environment;
	
	private static Environment staticEnvironment = new StandardEnvironment();
	
	public static boolean acceptsProfiles(Profiles profiles) {
		return getEnvironment().acceptsProfiles(profiles);
	}
	
	public static boolean acceptsProfiles(String... profiles) {
		return getEnvironment().acceptsProfiles(Profiles.of(profiles));
	}
	
	public static boolean acceptsTestDevProfiles() {
		return acceptsProfiles("test","dev");
	}
	
	public static boolean acceptsProdProfiles() {
		return acceptsProfiles("prod");
	}
	
	public static boolean acceptsTestProfiles() {
		return acceptsProfiles("test");
	}
	
	public static boolean acceptsDevProfiles() {
		return acceptsProfiles("dev");
	}
	
	public static Environment getEnvironment() {
		return staticEnvironment;
	}

	private static String activeProfile = null;
	public static String getActiveProfile() {
		if(activeProfile == null) {
			Environment environment = getEnvironment();
			if(environment != null) {
				activeProfile = environment.getActiveProfiles()[0];
			}
		}
		return activeProfile;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		staticEnvironment = environment;
	}
	
	
}
