package com.kai.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kai.mapper.UserMapper;
import com.kai.model.TUser;

@RestController
@RequestMapping("/project")
public class ProjectController {
	
	@Autowired
	private UserMapper userMapper;
	
	@RequestMapping("/getAllList")
	public Object getAllList() {
		List<TUser> list = userMapper.selectList(null);
		return list;
	}
	@RequestMapping("/getOneList")
	public Object getOneList() {
		TUser u = userMapper.selectOne(new QueryWrapper<TUser>().eq("name", "张三"));
		Map<String,Object> data=new HashMap<String,Object>();
		data.put("u2", u);
		data.put("u3", "me");
		return data;
	}
	@RequestMapping("getAll")
	public Object getAll() {
		TUser user=new TUser();
		user.setName("李四");
		List<TUser> list = userMapper.getAll(Wrappers.query().eq("name", "李四"));
		return list;
	}
	@RequestMapping("toPage")
	public Object toPage() {
		Page<TUser> page=new Page<>(1,1);
		IPage<TUser> iPage = userMapper.selectPage(page, null);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("total", iPage.getTotal());
		map.put("rows", iPage.getRecords());
		return iPage;
	}
}
