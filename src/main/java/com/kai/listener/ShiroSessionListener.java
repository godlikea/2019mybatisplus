package com.kai.listener;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;
/**
 * 配置Session监听
 * @author ggk
 * @data 2019年3月22日上午8:58:25
 */
public class ShiroSessionListener implements SessionListener {
	/**
	 * 原子类型自增，juc包下线程安全自增
	 */
	private final  AtomicInteger sessionCount=new AtomicInteger(0);
	/**
	 * 回话创建时触发
	 */
	public void onStart(Session session) {
		sessionCount.incrementAndGet();
	}
	/**
	 * 回话退出时触发
	 */
	public void onStop(Session session) {
		sessionCount.decrementAndGet();
	}
	/**
	 * 回话过期时触发
	 */
	public void onExpiration(Session session) {
		sessionCount.decrementAndGet();
	}
	/**
	 * 获取在线人数
	 * @author 郭广凯
	 * @data 2019年3月22日上午9:01:34
	 * @return
	 */
	public AtomicInteger getSessionCount() {
		return sessionCount;
	}

}
