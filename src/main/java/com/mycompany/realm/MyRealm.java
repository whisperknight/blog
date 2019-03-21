package com.mycompany.realm;

import java.util.HashSet;

import javax.annotation.Resource;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.mycompany.service.UserService;

public class MyRealm extends AuthorizingRealm {

	@Resource
	private UserService userService;

	/**
	 * 授权认证，此项目不做授权认证
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String userName = (String) principals.getPrimaryPrincipal();
		var authorizationInfo = new SimpleAuthorizationInfo();

		var set = new HashSet<String>();
		set.add(userService.getUserByUserName(userName).getStatus() == -1 ? "blogger" : "user");// 状态为-1表示博主，为其他表示用户

		authorizationInfo.setRoles(set);
		return authorizationInfo;
	}

	/**
	 * 身份认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
			throws AuthenticationException {
		String userName = (String) token.getPrincipal();
		var user = userService.getUserByUserName(userName);
		if (user != null) {
			return new SimpleAuthenticationInfo(user.getUserName(), user.getPassword(), "xx");
		} else {
			return null;
		}
	}

}
