package com.company.project.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin/echo")
public class EchoController {

	@RequestMapping
	@ResponseBody
	public String echo(String name) {
		System.out.println("echo name:"+name);
		return "success";
	}
	
	@GetMapping
	@ResponseBody
	public String echo2(String name) {
		System.out.println("echo2 name:"+name);
		return "success";
	}
	
	@GetMapping
	@ResponseBody
	public String exception(String name) {
		if(true) throw new RuntimeException("hi,some error",new RuntimeException("nest exception"));
		return "success";
	}
	
}
