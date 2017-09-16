package com.mark.demo.shiro.security;

import java.util.Collection;
import java.util.Iterator;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.PrincipalCollection;

import com.mark.demo.shiro.entity.User;
import com.mark.demo.shiro.mapper.UserMapper;
import com.mark.demo.shiro.security.session.CustomSessionDAO;
import com.mark.demo.shiro.utils.OnLineUserUtils;
import com.mark.demo.shiro.utils.PropertiesLoader;

public class MysqlRealm extends AuthorizingRealm {
	private UserMapper userMapper;
	private CustomSessionDAO sessionDAO;

	private static PropertiesLoader props = new PropertiesLoader("cookie.properties");
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String name = getName();
		Iterator iterator = principals.fromRealm(name).iterator();
		String userName = null;
		if (iterator.hasNext()) {
			userName = (String) iterator.next();
		}
		String[] names = userName.split(":");
		if (names.length != 2) {
			return null;
		}
		String prefix = names[0];

		return null;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)
			throws AuthenticationException {

		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		String username = token.getUsername();
		
		String allow = props.getProperty("user.multiAccountLogin");
		// 获取当前已登录的用户
		if (!Boolean.parseBoolean(allow)) {
			Collection<Session> sessions = sessionDAO.getActiveSessions(true, username, OnLineUserUtils.getSession());
			if (sessions.size() > 0) { // 如果是登录进来的，则踢出已在线用户
				if (OnLineUserUtils.getSubject().isAuthenticated()) {
					for (Session session : sessions)
						sessionDAO.delete(session);
				} else { // 记住我进来的，并且当前用户已登录，则退出当前用户提示信息。
					OnLineUserUtils.getSubject().logout();
					throw new AuthenticationException("msg:账号已在其它地方登录，请重新登录。");
				}
			}
		}

		
		User user = userMapper.getUserByUserName(username);
		if (user != null) {
			return new SimpleAuthenticationInfo(user, user.getPassword(), getName());
		}
		return null;

	}

	public void setSessionDAO(CustomSessionDAO sessionDAO) {
		this.sessionDAO = sessionDAO;
	}

	public void setUserMapper(UserMapper userMapper) {
		this.userMapper = userMapper;
	}

}
