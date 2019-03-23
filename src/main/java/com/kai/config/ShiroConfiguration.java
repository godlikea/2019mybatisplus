package com.kai.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.kai.filter.KickoutSessionControlFilter;
import com.kai.filter.RetryLimitHashedCredentialsMatcher;
import com.kai.listener.ShiroSessionListener;
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
		MyRealm shiroRealm = new MyRealm();
	    shiroRealm.setCachingEnabled(true);
	    //启用身份验证缓存，即缓存AuthenticationInfo信息，默认false
	    shiroRealm.setAuthenticationCachingEnabled(true);
	    //缓存AuthenticationInfo信息的缓存名称 在ehcache-shiro.xml中有对应缓存的配置
	    shiroRealm.setAuthenticationCacheName("authenticationCache");
	    //启用授权缓存，即缓存AuthorizationInfo信息，默认false
	    shiroRealm.setAuthorizationCachingEnabled(true);
	    //缓存AuthorizationInfo信息的缓存名称  在ehcache-shiro.xml中有对应缓存的配置
	    shiroRealm.setAuthorizationCacheName("authorizationCache");
	    //配置自定义密码器
	    shiroRealm.setCredentialsMatcher(retryLimitHashedCredentialsMatcher());
		return shiroRealm;
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
		securityManager.setCacheManager(ehCacheManager());
		securityManager.setSessionManager(getSessionManager());
		securityManager.setRememberMeManager(getRememberMeManager());
		return securityManager;
	}
	@Bean 
	public CookieRememberMeManager getRememberMeManager() {
		CookieRememberMeManager crm=new CookieRememberMeManager();
		crm.setCookie(rememberMeCookie());
		crm.setCipherKey(Base64.decode("4AvVhmFLUs0KTA3Kprsdag=="));
		return crm;
	}
	/**
	 * 过滤记住我
	 * @author ggk
	 * @data 2019年3月22日上午10:31:36
	 * @return
	 *//*
	@Bean
	public FormAuthenticationFilter formAuthenticationFilter() {
		FormAuthenticationFilter faf=new FormAuthenticationFilter();
		faf.setRememberMeParam("rememberMe");
		return faf;
	}*/
	@Bean("rememberMeCookie")
	public SimpleCookie  rememberMeCookie() {
		SimpleCookie sc=new SimpleCookie("sessionOne"); 
		sc.setHttpOnly(true);
		sc.setPath("/");
		sc.setMaxAge(2592000);
		return sc;
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
		Map<String,Filter> filterMap=new LinkedHashMap<>();
		filterMap.put("kickout", kickoutSessionControlFilter());
		sffb.setFilters(filterMap);
		Map<String,String> map=new LinkedHashMap<String,String>();
		//登出
		map.put("/logout", "logout");
		//对所有用户验证
		//map.put("/**", "authc");
		map.put("/login", "kickout,anon");
		map.put("/flux", "anon");
		//其他资源都需要认证  authc 表示需要认证才能进行访问 user表示配置记住我或认证通过可以访问的地址
		//登录
		sffb.setLoginUrl("/login");
		//首页
		sffb.setSuccessUrl("/index");
		//错误界面
		sffb.setUnauthorizedUrl("/exe");
		map.put("/**", "kickout,user");
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
	/**
	 * 
	 * @author 郭广凯
	 * @data 2019年3月22日上午9:32:15
	 * @return
	 */
	@Bean
	public  ShiroSessionListener getShiroSessionListener() {
		return new ShiroSessionListener();
	}
	/**
	 * 配置回话id生成器
	 * @author ggk
	 * @data 2019年3月22日上午9:38:50
	 * @return
	 */
	@Bean
	public SessionIdGenerator sessionIdGenerator() {
		return new JavaUuidSessionIdGenerator();
	}
	/**
	 * SessionDAO的作用是为Session提供CRUD并进行持久化的一个shiro组件
	 * MemorySessionDAO 直接在内存中进行会话维护
	 * EnterpriseCacheSessionDAO  提供了缓存功能的会话维护，默认情况下使用MapCache实现，内部使用ConcurrentHashMap保存缓存的会话。
	 * @author ggk
	 * @data 2019年3月22日上午9:47:31
	 * @return
	 */
	@Bean
	public SessionDAO sessionDao() {
		EnterpriseCacheSessionDAO ecs=new EnterpriseCacheSessionDAO();
		//使用enCacheManager
		ecs.setCacheManager(ehCacheManager());
		//设置session缓存的名称  默认为 ggk-session
		ecs.setActiveSessionsCacheName("ggk-session");
		//sessionId生成器
		ecs.setSessionIdGenerator(sessionIdGenerator());
		return ecs;
	}
	/**
	 * 配置保存sessionId的cookie
	 * @author ggk
	 * @data 2019年3月22日上午9:52:32
	 * @return
	 */
	@Bean("sessionIdCookie")
	public SimpleCookie sessionIdCookie() {
		//cookie名称
		SimpleCookie sc=new SimpleCookie("ggkId");
		sc.setHttpOnly(true);
		sc.setPath("/");
		//-1 表示浏览器关闭 此cookie失效
		sc.setMaxAge(1000*1800);
		return sc;
	}
	@Bean
	public SessionManager getSessionManager() {
		DefaultWebSessionManager sessionManager=new DefaultWebSessionManager();
		Collection<SessionListener> listerners=new ArrayList<>();
		listerners.add(getShiroSessionListener());
		sessionManager.setSessionListeners(listerners);
		sessionManager.setSessionDAO(sessionDao());
		sessionManager.setSessionIdCookie(sessionIdCookie());
		sessionManager.setCacheManager(ehCacheManager());
		
		//全局回话超时时间 默认30分钟
		sessionManager.setGlobalSessionTimeout(1000*1800);
		//是否开启删除无效的session 默认为true
		sessionManager.setDeleteInvalidSessions(true);
		//是否开启定时调度器进行检测过期session默认为true
		sessionManager.setSessionValidationSchedulerEnabled(true);
		//设置session失效的扫描时间，清理用户直接关闭浏览器造成的孤立会话
		sessionManager.setSessionValidationInterval(1000*3600);
		//取消url后面的Jsessionid
		sessionManager.setSessionIdUrlRewritingEnabled(false);
		return sessionManager;
	}
	/**
	 * shiro缓存管理器;
	 * 需要添加到securityManager中
	 * @return
	 */
	@Bean
	public EhCacheManager ehCacheManager(){
	    EhCacheManager cacheManager = new EhCacheManager();
	    cacheManager.setCacheManagerConfigFile("classpath:config/ehcache-shiro.xml");
	    return cacheManager;
	}
	
	/**
	 * 让某个实例的某个方法的返回值注入为Bean的实例
	 * Spring静态注入
	 * @return
	 */
	@Bean
	public MethodInvokingFactoryBean getMethodInvokingFactoryBean(){
	    MethodInvokingFactoryBean factoryBean = new MethodInvokingFactoryBean();
	    factoryBean.setStaticMethod("org.apache.shiro.SecurityUtils.setSecurityManager");
	    factoryBean.setArguments(new Object[]{getSecurityManager()});
	    return factoryBean;
	}
	/**
	 * 并发登录控制
	 * @return
	 */
	@Bean
	public KickoutSessionControlFilter kickoutSessionControlFilter(){
	    KickoutSessionControlFilter kickoutSessionControlFilter = new KickoutSessionControlFilter();
	    //用于根据会话ID，获取会话进行踢出操作的；
	    kickoutSessionControlFilter.setSessionManager(getSessionManager());
	    //使用cacheManager获取相应的cache来缓存用户登录的会话；用于保存用户—会话之间的关系的；
	    kickoutSessionControlFilter.setCacheManager(ehCacheManager());
	    //是否踢出后来登录的，默认是false；即后者登录的用户踢出前者登录的用户；
	    kickoutSessionControlFilter.setKickoutAfter(false);
	    //同一个用户最大的会话数，默认1；比如2的意思是同一个用户允许最多同时两个人登录；
	    kickoutSessionControlFilter.setMaxSession(1);
	    //被踢出后重定向到的地址；
	    kickoutSessionControlFilter.setKickoutUrl("/login?kickout=1");
	    return kickoutSessionControlFilter;
	}
	
	 /**配置密码比较器
	 * @return
	 */
	@Bean("credentialsMatcher")
	public RetryLimitHashedCredentialsMatcher retryLimitHashedCredentialsMatcher(){
	    RetryLimitHashedCredentialsMatcher retryLimitHashedCredentialsMatcher = new RetryLimitHashedCredentialsMatcher(ehCacheManager());

	    //如果密码加密,可以打开下面配置
	    //加密算法的名称
	    //retryLimitHashedCredentialsMatcher.setHashAlgorithmName("MD5");
	    //配置加密的次数
	    //retryLimitHashedCredentialsMatcher.setHashIterations(1024);
	    //是否存储为16进制
	    //retryLimitHashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);

	    return retryLimitHashedCredentialsMatcher;
	}
}
