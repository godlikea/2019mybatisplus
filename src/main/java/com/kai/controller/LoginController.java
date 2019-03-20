package com.kai.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kai.model.TUser;
/**
 * 登录测试shiro
 * @author 郭广凯
 * @data 2019年3月20日上午10:45:31
 */
@RestController
public class LoginController {
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public Object login() {
		return "login";
	}
	/**
	 * 登录
	 * @author 郭广凯
	 * @data 2019年3月20日上午10:50:15
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public Object login(TUser user) {
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken upt=new UsernamePasswordToken(user.getName(), user.getPwd());
		subject.login(upt);
		return "Login";
	}
	@RequestMapping("/index")
	public Object index() {
		return "index";
	}
	@RequestMapping("/logout")
	public Object logout() {
		return "logout";
	}
	
}
