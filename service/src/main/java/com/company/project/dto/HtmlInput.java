package com.company.project.dto;

import java.lang.reflect.Field;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;


/**
 * 返回给前端的输入字段
 */
public class HtmlInput {

	private String name; //字段英文代号
	private String label; //字段中文名称
	private String dataType; //字段类型,string,int,long
	private String helpText; //帮助文案
	private String example; //示例值
	private boolean required; //是否必填
	private boolean hidden; //是否隐藏
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String type) {
		this.dataType = type;
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
	
	
	
	public boolean isHidden() {
		return hidden;
	}
	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}
	@Override
	public String toString() {
		return "HtmlInput [name=" + name + ", label=" + label + ", dataType=" + dataType + ", helpText=" + helpText
				+ ", example=" + example + ", required=" + required + "]";
	}
	
	public static HtmlInput from(Schema mp,Field field) {
		if(mp == null) return null;
		
		HtmlInput input = new HtmlInput();
		
		input.setLabel(mp.title());
		input.setHelpText(mp.description());
		input.setExample(mp.example());
		input.setRequired(mp.required());
		input.setHidden(mp.hidden());
		
		fromField(field, input);
		
		return input;
	}
	
	private static void fromField(Field field, HtmlInput input) {
		input.setName(field.getName());
		input.setDataType(field.getType().getSimpleName().toLowerCase());
		
		if(field.isAnnotationPresent(NotNull.class) || field.isAnnotationPresent(NotBlank.class) || field.isAnnotationPresent(NotEmpty.class)) {
			input.setRequired(true);
		}
	}
}
