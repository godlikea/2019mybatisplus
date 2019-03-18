package com.kai.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kai.model.TRole;

import lombok.extern.slf4j.Slf4j;
/**
 * 角色信息确认
 * @author 郭广凯
 * @data 2019年3月18日下午4:05:05
 */
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
	@RequestMapping("/rolePage")
	public Object getRolePage() {
		TRole tr=new TRole();
		IPage<TRole> page = tr.selectPage(new Page<>(1, 1), null);
		return page;
	}
	
	
}
