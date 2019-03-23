package com.kai.shiro;

import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kai.mapper.PermissionMapper;
import com.kai.mapper.RoleMapper;
import com.kai.mapper.RolePermissionMapper;
import com.kai.mapper.UserMapper;
import com.kai.mapper.UserRoleMapper;
import com.kai.model.TPermission;
import com.kai.model.TRole;
import com.kai.model.TRolePermission;
import com.kai.model.TUser;
import com.kai.model.TUserRole;
/**
 * 自定义realm
 * @author 郭广凯
 * @data 2019年3月20日上午9:27:27
 */

public class MyRealm extends AuthorizingRealm {
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private RoleMapper roleMapper;
	
	@Autowired
	private UserRoleMapper userRoleMapper;
	
	@Autowired
	private RolePermissionMapper rolePermissionMapper;
	
	@Autowired
	private PermissionMapper permissionMapper;
	
	/**
	 * 角色授权
	 */
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String name=(String)principals.getPrimaryPrincipal();
		TUser user = userMapper.selectOne(new QueryWrapper<TUser>().eq("name", name));
		SimpleAuthorizationInfo sai=new SimpleAuthorizationInfo();
		//获得当前用户对应的角色
		List<TUserRole> list = userRoleMapper.selectList(new QueryWrapper<TUserRole>().eq("uid", user.getId()));
		if(list!=null && list.size()>0) {
			for(TUserRole userRole:list) {
				//获取角色
				TRole role = roleMapper.selectOne(new QueryWrapper<TRole>().eq("id", userRole.getRid()));
				sai.addRole(role.getRole());
				//获取当前角色对应的权限
				List<TRolePermission> perList = rolePermissionMapper.
						selectList(new QueryWrapper<TRolePermission>().eq("rid", role.getId()));
				if(perList!= null && perList.size()>0) {
					for(TRolePermission rolePermission:perList) {
						TPermission permission = permissionMapper.
								selectOne(new QueryWrapper<TPermission>().eq("id", rolePermission.getPid()));
						sai.addStringPermission(permission.getPer());
					}
				}
			}
		}
		return sai;
	}

	/**
	 * 认证登录
	 */
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		if(token.getPrincipal()==null) {
			throw new AuthenticationException();
		}
		String name = token.getPrincipal().toString();
		TUser user = userMapper.selectOne(new QueryWrapper<TUser>().eq("name", name));
		if(user ==null) {
			throw new AuthenticationException();
		}else {
			SimpleAuthenticationInfo sai=new SimpleAuthenticationInfo(name,user.getPwd(),getName());
			return sai;
		}
	}

	protected void clearCachedAuthorizationInfo(PrincipalCollection principals) {
		super.clearCachedAuthorizationInfo(principals);
	}

	protected void doClearCache(PrincipalCollection principals) {
		super.doClearCache(principals);
	}
	
	/**
	 * 自定义方法：清除所有 授权缓存
	 */
	public void clearAllCachedAuthorizationInfo() {
	    getAuthorizationCache().clear();
	}

	/**
	 * 自定义方法：清除所有 认证缓存
	 */
	public void clearAllCachedAuthenticationInfo() {
	    getAuthenticationCache().clear();
	}

	/**
	 * 自定义方法：清除所有的  认证缓存  和 授权缓存
	 */
	public void clearAllCache() {
	    clearAllCachedAuthenticationInfo();
	    clearAllCachedAuthorizationInfo();
	}
	
	

}
