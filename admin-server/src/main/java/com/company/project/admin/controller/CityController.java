/*
 * Copyright [rapid-framework]
 * Web Site: https://github.com/badqiu/rapid-framework
 * Since 2005 - 2019
 * author: badqiu email:badqiu(a)gmail.com
 */
package com.company.project.admin.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.company.project.model.City;
import com.company.project.model.CityQuery;
import com.company.project.service.CityService;
import com.github.rapid.common.util.page.Page;

/**
 * [City] 用户前台  Controller
 * 
 * @author badqiu
 * @version 1.0
 * @since 1.0 
 * created: 2019-11-27
 */

@Controller
@RequestMapping("/city")
public class CityController extends BaseController {

    @Autowired
    private CityService cityService;

	public void setCityService(CityService cityService) {
		this.cityService = cityService;
	}
	
	@PostMapping
	public void create(@RequestBody City city,HttpServletRequest request) {
		checkEntityPermission(request,city,"w");
		
		cityService.create(city);
	}
	
	@PostMapping
	public void update(@RequestBody City city,HttpServletRequest request) {
		checkEntityPermission(request,city,"w");
		
		cityService.update(city);
	}
	
	@PostMapping
	public void removeById(City city,int id,HttpServletRequest request) {
		checkEntityPermission(request,city,"w");
		
		cityService.removeById(id);
	}

	@GetMapping
	public ResponseEntity<?> getById(boolean join,int id,City city,HttpServletRequest request) {
		checkEntityPermission(request,city,"r");
		
		City result = cityService.getById(id);
		if(join) cityService.join(result);
		
		return ResponseEntity.ok(result);
	}
	
	@GetMapping
	public ResponseEntity<?> findPage(boolean join,CityQuery query,City city,HttpServletRequest request){
		checkEntityPermission(request,city,"r");
		
		Page<City> result = cityService.findPage(query);
		if(join) result.forEach(cityService::join);
		
		return ResponseEntity.ok(result);
	}
	
}

