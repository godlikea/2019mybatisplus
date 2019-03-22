package com.kai.filter;

import java.io.Serializable;
import java.util.Deque;
import java.util.LinkedList;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import com.kai.model.TUser;

import lombok.extern.slf4j.Slf4j;
/**
 * 并发登录过滤
 * @author ggk
 * @data 2019年3月22日上午11:35:32
 */
@Slf4j
public class KickoutSessionControlFilter extends AccessControlFilter {
	/**
	 * 踢出去后的地址
	 */
	private String kickoutUrl;
	/**
	 * 踢出之前登录的/之后登录的用户 默认踢出之前登录的用户
	 */
	private boolean kickoutAfter=false;
	/**
	 * 同一账号最大Session数
	 */
	private int maxSession=1;
	
	private SessionManager sessionManager;
	
	private Cache<String,Deque<Serializable>> cache;
	

	public void setKickoutUrl(String kickoutUrl) {
		this.kickoutUrl = kickoutUrl;
	}

	public void setKickoutAfter(boolean kickoutAfter) {
		this.kickoutAfter = kickoutAfter;
	}

	public void setMaxSession(int maxSession) {
		this.maxSession = maxSession;
	}

	public void setSessionManager(SessionManager sessionManager) {
		this.sessionManager = sessionManager;
	}


	public void setCacheManager(CacheManager cacheManager) {
        this.cache = cacheManager.getCache("shiro-activeSessionCache");
    }
	/**
	 * 是否允许访问，返回true表示允许
	 */
	protected boolean isAccessAllowed(ServletRequest arg0, ServletResponse arg1, Object arg2) throws Exception {
		return false;
	}
	 /**
     * 表示访问拒绝时是否自己处理，如果返回true表示自己不处理且继续拦截器链执行，返回false表示自己已经处理了（比如重定向到另一个页面）。
     */
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		Subject subject = getSubject(request, response);
		if(!subject.isAuthenticated()&& !subject.isRemembered()) {
			return true;
		}
		Session session = subject.getSession();
		String name = ((TUser)subject.getPrincipal()).getName();
		Serializable id = session.getId();
		//初始化队列放到缓存中
		Deque<Serializable> deque = cache.get(name);
		if(deque==null) {
			deque=new LinkedList<Serializable>();
			cache.put(name, deque);
		}
		//如果队列中没有此sessionId,且用户没有被踢出；则放入队列
		if(!deque.contains(id) && session.getAttribute("kickout")==null) {
			deque.push(id);
		}
		//如果队列里的sessionId超出最大回话数，开始踢人
		while(deque.size()>maxSession) {
			Serializable kickoutSessionId=null;
			if(kickoutAfter) {//如果踢出后者
				kickoutSessionId=deque.getFirst();
				kickoutSessionId=deque.removeFirst();
			}else {//否则踢出前者
				kickoutSessionId=deque.removeLast();
			}
			try {
				Session kickSession = sessionManager.getSession(new DefaultSessionKey(kickoutSessionId));
				if(kickSession!=null) {
					kickSession.setAttribute("kickout", true);
				}
			}catch(Exception e) {
				log.info(e.getMessage());
			}
		}
		//如果被踢出，直接退出，重定向到踢出后的地址
		if(session.getAttribute("kickout")!=null) {
			try {
				subject.logout();
			}catch(Exception e) {
				
			}
			WebUtils.issueRedirect(request, response, kickoutUrl);
			return false;
		}
		return true;
	}

}
