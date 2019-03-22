package com.kai.filter;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kai.mapper.UserMapper;
import com.kai.model.TUser;

import lombok.extern.slf4j.Slf4j;
/**
 * 登录次数限制
 * @author ggk
 * @data 2019年3月22日上午11:36:49
 */
@Slf4j
public class RetryLimitHashedCredentialsMatcher extends SimpleCredentialsMatcher {
	@Autowired
	private UserMapper userMapper;
	
	private Cache<String,AtomicInteger> passwordRetryCache;
	
	public RetryLimitHashedCredentialsMatcher(CacheManager cacheManager) {
		passwordRetryCache=cacheManager.getCache("passwordRetryCache");
	}

	public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
		String name=(String)token.getPrincipal();
		AtomicInteger retryCount = passwordRetryCache.get(name);
		if(retryCount==null) {
			retryCount=new AtomicInteger(0);
			passwordRetryCache.put(name, retryCount);
		}
		if(retryCount.incrementAndGet()>5) {
			TUser user = userMapper.selectOne(new QueryWrapper<TUser>().eq("name", name));
			if(user!=null) {
				
			}
			log.info("锁定用户"+user.getName());
			throw new LockedAccountException();
		}
		boolean match = super.doCredentialsMatch(token, info);
		//如果登录成功，从缓存中将用户
		if(match) {
			passwordRetryCache.remove(name);
		}
		return match;
	}
	/**
	 * 解除用户 根据用户名
	 * @author ggk
	 * @data 2019年3月22日上午11:49:06
	 * @param name
	 */
	public void unlockAccount(String name) {
		TUser user = userMapper.selectOne(new QueryWrapper<TUser>().eq("name", name));
		if(user !=null) {
			//修改数据库中的状态字段
			passwordRetryCache.remove(name);
		}
	}
	
	
	
}
