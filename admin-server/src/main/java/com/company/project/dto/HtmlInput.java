package com.company.project.dto;

import java.lang.reflect.Field;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;

public class HtmlInput {

	private String name;
	private String label;
	private String type;
	private String helpText;
	private String example; 
	private boolean required;
	
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
	public String getHelpText() {
		return helpText;
	}
	public void setHelpText(String placeholder) {
		this.helpText = placeholder;
	}
	
	public String getExample() {
		return example;
	}
	public void setExample(String example) {
		this.example = example;
	}
	
	public boolean isRequired() {
		return required;
	}
	
	public void setRequired(boolean required) {
		this.required = required;
	}
	
	@Override
	public String toString() {
		return "HtmlInput [name=" + name + ", label=" + label + ", type=" + type + ", helpText=" + helpText
				+ ", example=" + example + ", required=" + required + "]";
	}
	
	public static HtmlInput from(ApiModelProperty mp,Field field) {
		if(mp == null) return null;
		
		HtmlInput input = new HtmlInput();
		input.setName(field.getName());
		input.setType(field.getType().getSimpleName().toLowerCase());
		input.setLabel(mp.value());
		input.setHelpText(mp.notes());
		input.setExample(mp.example());
		
		input.setRequired(mp.required());
		if(field.isAnnotationPresent(NotNull.class) || field.isAnnotationPresent(NotBlank.class) || field.isAnnotationPresent(NotEmpty.class)) {
			input.setRequired(true);
		}
		
		return input;
	}
}
