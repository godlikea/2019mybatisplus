package com.kai.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.kai.shiro.MyRealm;

@Configuration
public class ShiroConfiguration {
	
	/**
	 * 注入自定义realm
	 * @author 郭广凯
	 * @data 2019年3月20日上午10:20:10
	 * @return
	 */
	@Bean
	public MyRealm getMyRealm() {
		return new MyRealm();
	}
	/**
	 * 权限管理，shiro 核心   主要用于realm的权限管理
	 * @author 郭广凯
	 * @data 2019年3月20日上午10:24:14
	 * @return
	 */
	@Bean
	public SecurityManager getSecurityManager() {
		DefaultWebSecurityManager securityManager=new DefaultWebSecurityManager();
		securityManager.setRealm(getMyRealm());
		return securityManager;
	}
	/**
	 * Filter 工厂
	 * @author 郭广凯
	 * @data 2019年3月20日上午10:31:47
	 * @param securityManager
	 * @return
	 */
	@Bean
	public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
		ShiroFilterFactoryBean sffb=new ShiroFilterFactoryBean();
		sffb.setSecurityManager(securityManager);
		Map<String,String> map=new HashMap<String,String>();
		//登出
		map.put("/logout", "logout");
		//对所有用户验证
		map.put("/**", "authc");
		//登录
		sffb.setLoginUrl("/login");
		//首页
		sffb.setSuccessUrl("/index");
		//错误界面
		sffb.setUnauthorizedUrl("/exe");
		sffb.setFilterChainDefinitionMap(map);
		return sffb;
	}
	/**
	 * 加入使用注解  不加  则注解不生效
	 * @author 郭广凯
	 * @data 2019年3月20日上午10:40:57
	 * @param securityManager
	 * @return
	 */
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor=new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
		return authorizationAttributeSourceAdvisor;
	}
}
