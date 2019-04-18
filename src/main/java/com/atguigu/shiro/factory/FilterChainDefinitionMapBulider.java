//创建时间：2018年5月2日,下午8:06:03
package com.atguigu.shiro.factory;

import java.util.LinkedHashMap;

public class FilterChainDefinitionMapBulider {
	
	public LinkedHashMap<String, String> bulidFilterChainDefinitionMap() {
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		map.put("/login.jsp", "anon");
		map.put("/shiro/login", "anon");
		map.put("/shiro/logout", "logout");
		
		map.put("/user.jsp", "authc,roles[user]");//认证之后，同时具有user这个权限
		map.put("/admin.jsp", "authc,roles[admin]");
		map.put("/list.jsp", "user");
		map.put("/**", "authc");
		return map;
	}

}
