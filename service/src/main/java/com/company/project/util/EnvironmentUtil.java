package com.company.project.util;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.stereotype.Component;

@Component
public class EnvironmentUtil implements EnvironmentAware,PriorityOrdered{
	
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
			if(environment != null && ArrayUtils.isNotEmpty(environment.getActiveProfiles())) {
				String[] activeProfiles = environment.getActiveProfiles();
				activeProfile = activeProfiles[activeProfiles.length - 1];
			}
		}
		return activeProfile;
	}
	

	@Override
	public void setEnvironment(Environment environment) {
		staticEnvironment = environment;
	}
	
	@Override
	public int getOrder() {
		return HIGHEST_PRECEDENCE;
	}
	
}
