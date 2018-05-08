package cn.jadeStones.base.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.jadeStones.Entity.BUser;
import cn.jadeStones.Entity.Test;
import cn.jadeStones.Service.Impl.BUserServiceImpl;

@Component("myRealm")
public class ShiroDbRealm extends AuthorizingRealm {
	
	@Autowired
	private BUserServiceImpl bUsers;
	
	public static final String SESSION_USER_KEY = "gray";

	/**
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用,负责在应用程序中决定用户的访问控制的方法
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
		Test user = (Test) SecurityUtils.getSubject().getSession().getAttribute(ShiroDbRealm.SESSION_USER_KEY);
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.addRole(user.getId()+"");
		return info;
	}

	/**
	 * 认证回调函数，登录信息和用户验证信息验证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authcToken) throws AuthenticationException {
		// 把token转换成User对象
		BUser userLogin = tokenToUser((UsernamePasswordToken) authcToken);
		// 验证用户是否可以登录
		BUser ui = (BUser) bUsers.selectBySelective(userLogin);
		if(ui == null) return null; 
		// 设置session
		Session session = SecurityUtils.getSubject().getSession();
		session.setAttribute(ShiroDbRealm.SESSION_USER_KEY, ui); 
		//当前 Realm 的 name
		String realmName = this.getName();
		//登陆的主要信息: 可以是一个实体类的对象, 但该实体类的对象一定是根据 token 的 username 查询得到的.
//		Object principal = ui.getUsername();
		Object principal = authcToken.getPrincipal();
		return new SimpleAuthenticationInfo(principal, userLogin.getPassword(), realmName);
	}

	private BUser tokenToUser(UsernamePasswordToken authcToken) {
		BUser user = new BUser();
		user.setName(authcToken.getUsername());
		user.setPassword(String.valueOf(authcToken.getPassword()));
		return user;
	}
}
