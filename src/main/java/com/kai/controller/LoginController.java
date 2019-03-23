package com.kai.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kai.model.TUser;

import io.undertow.servlet.api.SessionManagerFactory;
/**
 * 登录测试shiro
 * @author 郭广凯
 * @data 2019年3月20日上午10:45:31
 */
@RestController
public class LoginController {
	
	@Autowired
	private SessionDAO sessionDao;
	
	/*@RequestMapping(value="/login",method=RequestMethod.GET)
	public Object login() {
		return "login";
	}*/
	/**
	 * 登录
	 * @author 郭广凯
	 * @data 2019年3月20日上午10:50:15
	 * @param user
	 * @return
	 */
	@GetMapping("/login")
	public Object login(TUser user) {
		Subject subject = SecurityUtils.getSubject();
		//FIXME    2019.03.23   此方法暂不需要
		/*if((System.currentTimeMillis()-subject.getSession().getStartTimestamp().getTime())>=1000) {
			//移除线程中的subject
			ThreadContext.remove(ThreadContext.SUBJECT_KEY);
			sessionDao.delete(subject.getSession());
			subject=SecurityUtils.getSubject();
		}*/
		UsernamePasswordToken upt=new UsernamePasswordToken(user.getName(), user.getPwd(),true);
		subject.login(upt);
		return "Login";
	}
	
	@RequestMapping("/index")
	public Object index() {
		return "index";
	}
	
	@RequestMapping("/logout")
	public Object logout() {
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		return "logout";
	}
	
	@RequestMapping("/addNew")
	@RequiresRoles("经理")
	@RequiresPermissions("添加")
	public Object addList() {
		return "添加权限";
	}
	@RequestMapping("/exe")
	public Object exe() {
		return "无此操作";
	}
	
}
