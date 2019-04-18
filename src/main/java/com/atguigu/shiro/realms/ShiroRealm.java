//创建时间：2018年5月2日,下午3:25:22
package com.atguigu.shiro.realms;

import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

public class ShiroRealm extends AuthorizingRealm{
	
	//登录认证
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		System.out.println("进入方法...."+token);
		System.out.println("第一个Realm........................");
		UsernamePasswordToken upToken=(UsernamePasswordToken) token;
		String username=upToken.getUsername();
		System.out.println("从数据库中查询username...."+username+"所对应的信息");
		char[] password1 = upToken.getPassword();
		System.out.println("前台传入的密码:"+password1);
		for(char c:password1){
			System.out.print(c);
		}
		System.out.println("------------------------");
		//如果用户名不存在...
		if("unknown".equals(username)){
			throw new UnknownAccountException("用户不存在...");
		}
		if("monster".equals(username)){
			throw new LockedAccountException("用户被锁定了...");
		}
		//1.认证的实体信息
		Object principal =username;
		//2.密码 credentials
		Object credentials=null;//"00c18a96dcd54576524d9d284a70b1ff";
		if("admin".equals(username)){
			credentials="0d640a406596c708629a7824d34d0e39";//这个数据是从数据库中查询的
		}else if("user".equals(username)){
			credentials="6a8d3c384a19126e732016e58e7f97aa";
		}
		//3.realName 当前realm对象的name
		String realmName=this.getName();
		//以下信息是从数据库中获取的...
		//4.盐值
		ByteSource credentialsSalt=ByteSource.Util.bytes(username);
		SimpleAuthenticationInfo info=null;//new SimpleAuthenticationInfo(principal, credentials, realmName);
		
		info =new SimpleAuthenticationInfo(principal, credentials, credentialsSalt, realmName);
		return info;
	}
	
	
	public static void main(String[] args) {
		String hashAlgorithmName="MD5";
		//Object salt=null;
		ByteSource salt=ByteSource.Util.bytes("user");
		Object credentials="1";
		int hashIterations=1024;
		Object result=new SimpleHash(hashAlgorithmName, credentials, salt, hashIterations);
		System.out.println(result);
		//00c18a96dcd54576524d9d284a70b1ff 
	}

	//授权
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		System.out.println("授权................");
		//从PrincipalCollection获取登录的用户信息
		Object principal=principals.getPrimaryPrincipal();
		System.out.println("当前用户;"+principal);
		//2.
		Set<String> roles=new HashSet<String>();
		roles.add("user");
		if("admin".equals(principal)){
			roles.add("admin");
		}
		//创建SimpleAuthorizationInfo ，并且设置roles属性
		SimpleAuthorizationInfo info=new SimpleAuthorizationInfo(roles);
		return info;
	}

	
}
