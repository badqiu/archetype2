package com.company.project.webservice.dto;

/**
 * DTO (DataTransderObject) 数据传输对象
 * 
 * @author badqiu
 *
 */
public class HelloDTO {

	private String name;
	private String sex;
	private int age;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "HelloDTO [name=" + name + ", sex=" + sex + ", age=" + age + "]";
	}

}
