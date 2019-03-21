package com.mycompany.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mycompany.entity.Blogger;
import com.mycompany.service.BloggerService;
import com.mycompany.service.UserService;
import com.mycompany.util.CryptographyUtil;
import com.mycompany.util.StringUtil;

@Controller
@RequestMapping("/blogger")
public class BloggerController implements EnvironmentAware {
	/**
	 * 注入环境属性
	 */
	private Environment env;

	@Override
	public void setEnvironment(Environment environment) {
		this.env = environment;
	}

	@Resource
	private BloggerService bloggerService;
	@Resource
	private UserService userService;

	@RequestMapping("/login")
	public String login() {
		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated() && subject.hasRole("blogger"))
			return "redirect:/admin/main";
		else
			return "foreground/blogger/login";
	}

	@RequestMapping("/loginOn")
	public String login(Blogger blogger, HttpSession session,
			RedirectAttributes redirectAttributes) {
		if (blogger != null && StringUtil.isNotEmpty(blogger.getUserName())
				&& StringUtil.isNotEmpty(blogger.getPassword())) {
			Subject subject = SecurityUtils.getSubject();
			var token = new UsernamePasswordToken(blogger.getUserName(),
					CryptographyUtil.md5(blogger.getPassword(), env.getProperty("md5.salt")));
			try {
				subject.login(token);
				subject.checkRole("blogger");
				session.setAttribute("currentUser",
						userService.getUserByUserName(blogger.getUserName()));
				return "redirect:/admin/main";
			} catch (Exception e) {
				redirectAttributes.addFlashAttribute("blogger", blogger);
				redirectAttributes.addFlashAttribute("errorMsg", "用户名或密码错误！");
				return "redirect:/blogger/login";// 这里最好用ajax处理，此处仅做为带参重定向的实例
			}
		} else
			return "redirect:/blogger/login";
	}

	@RequestMapping("/about")
	public ModelAndView about() {
		var mav = new ModelAndView("mainTamplate");
		mav.addObject("pageTitle", "博主信息");
		mav.addObject("mainPage", "/foreground/blogger/about.jsp");
		return mav;
	}
}
