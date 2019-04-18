//创建时间：2018年5月2日,下午4:11:07
package com.atguigu.shiro.handler;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.atguigu.shiro.service.ShiroService;
@Controller
@RequestMapping("/shiro")
public class ShiroHandler {
	@Autowired
	private ShiroService shiroService;
	@RequestMapping("/testShiroAnnotation")
	public String testShiroAnnotation(HttpSession session){
		session.setAttribute("key", "value12345");
		shiroService.test();
		return "redirect:/list.jsp";
	}
	
	@RequestMapping("/login")
	public String login(@RequestParam(value = "username",required=false) String username,
			@RequestParam(value = "password" ,required=false) String password) {
		System.out.println("用户登录...");
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.isAuthenticated()) {
			UsernamePasswordToken token = new UsernamePasswordToken(username, password);
			token.setRememberMe(true);
 			try {
				currentUser.login(token);
			} catch (AuthenticationException e) {
				// e.printStackTrace();
				System.out.println("登录失败:" + e.getMessage());
			}
		}
		//重定向
		return "redirect:/list.jsp";
	}
	/*@RequestMapping("/logout")
	public String logout(){
		System.out.println("登出....");
		return null;
	}*/
}
