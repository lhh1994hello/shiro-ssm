//创建时间：2018年5月2日,下午7:47:41
package com.atguigu.shiro.service;

import java.util.Date;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Service;

@Service
public class ShiroService {
	// @RequiresRoles({"admin"})
	public void test() {
		System.out.println("测试Shiro注解...." + new Date());
		Session session = SecurityUtils.getSubject().getSession();
		Object val = session.getAttribute("key");
		System.out.println("service层的:" + val);
	}
}
