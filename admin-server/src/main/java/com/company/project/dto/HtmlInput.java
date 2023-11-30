package com.company.project.dto;

import java.lang.reflect.Field;

import io.swagger.annotations.ApiModelProperty;

public class HtmlInput {

	private String name;
	private String label;
	private String type;
	private String placeholder;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getPlaceholder() {
		return placeholder;
	}
	public void setPlaceholder(String placeholder) {
		this.placeholder = placeholder;
	}
	
	public static HtmlInput from(ApiModelProperty mp,Field field) {
		if(mp == null) return null;
		
		HtmlInput input = new HtmlInput();
		input.setName(field.getName());
		input.setType(mp.dataType());
		input.setLabel(mp.name());
		input.setPlaceholder(mp.value());
		return input;
	}
}
