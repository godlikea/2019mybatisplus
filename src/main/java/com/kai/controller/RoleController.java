package com.kai.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kai.model.TRole;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/role")
public class RoleController {
	
	@RequestMapping("/getRoleAll")
	public Object getRoleAll() {
		TRole tr=new TRole();
		log.info("=================输出确认====================");
		return tr.selectAll();
	}
	
	
}
