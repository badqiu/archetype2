package com.company.project.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin/ping")
public class AdminPingController {

	@RequestMapping
	@ResponseBody
	public String ping() {
		System.out.println("ping");
		return "PONG";
	}
	
	@GetMapping
	@ResponseBody
	public String exception(String name) {
		if(true) throw new RuntimeException("hi,some error",new RuntimeException("nest exception"));
		return "success";
	}
	
}
