package com.company.project.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 检查服务是否正常
 * 
 * @author dqiu
 *
 */
@Controller
@RequestMapping("/admin/ping")
public class AdminPingController {

	@RequestMapping
	@ResponseBody
	public String ping() {
		//可以添加执行，查询数据
		
		System.out.println("ping");
		return "PONG";
	}
	
}