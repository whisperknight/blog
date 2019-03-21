package com.mycompany.controller;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mycompany.entity.Blogger;
import com.mycompany.entity.User;
import com.mycompany.service.BloggerService;
import com.mycompany.service.UserService;
import com.mycompany.util.CryptographyUtil;
import com.mycompany.util.StringUtil;

@Controller
@RequestMapping("/user")
public class UserController implements EnvironmentAware {
	/**
	 * 注入环境属性
	 */
	private Environment env;

	@Override
	public void setEnvironment(Environment environment) {
		this.env = environment;
	}

	@Resource
	private UserService userService;
	@Resource
	private BloggerService bloggerService;

	@RequestMapping("/login")
	public String login() {
		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated())
			return "redirect:/index";
		else
			return "foreground/user/login";
	}

	@RequestMapping("/loginOn")
	public String login(User user, HttpSession session, RedirectAttributes redirectAttributes) {
		if (user != null && StringUtil.isNotEmpty(user.getUserName())
				&& StringUtil.isNotEmpty(user.getPassword())) {
			Subject subject = SecurityUtils.getSubject();
			var token = new UsernamePasswordToken(user.getUserName(),
					CryptographyUtil.md5(user.getPassword(), env.getProperty("md5.salt")));
			try {
				subject.login(token);
				session.setAttribute("currentUser",
						userService.getUserByUserName(user.getUserName()));
				return "redirect:/index";
			} catch (Exception e) {
				redirectAttributes.addFlashAttribute("user", user);
				redirectAttributes.addFlashAttribute("errorMsg", "用户名或密码错误！");
				return "redirect:/user/login";// 这里最好用ajax处理，此处仅做为带参重定向的实例
			}
		} else
			return "redirect:/user/login";
	}

	@RequestMapping("/center")
	public ModelAndView center(HttpSession session) {
		User currentUser = (User) session.getAttribute("currentUser");
		if (currentUser == null) {
			return new ModelAndView("error");
		}
		var mav = new ModelAndView("foreground/user/userTamplate");
		mav.addObject("pageTitle", "用户中心");
		mav.addObject("mainPage", "/foreground/user/center.jsp");
		return mav;
	}

	@RequestMapping("/modifyOn")
	@ResponseBody
	public String modifyOn(User user, HttpSession session,
			@RequestParam(value = "croppedImage", required = false) MultipartFile croppedImage,
			HttpServletResponse response) throws Exception {
		User currentUser = (User) session.getAttribute("currentUser");
		if (currentUser == null) {
			return "error";
		}
		if (user != null) {
			String imageName = null;// 头像文件名
			boolean isModified = false;// 判断是否有修改过

			if (StringUtil.isNotEmpty(user.getPassword())) {
				currentUser.setPassword(
						CryptographyUtil.md5(user.getPassword(), env.getProperty("md5.salt")));
				isModified = true;
			}
			if (StringUtil.isNotEmpty(user.getNickName())
					&& !user.getNickName().equals(currentUser.getNickName())) {
				currentUser.setNickName(user.getNickName());
				isModified = true;
			}
			if (croppedImage != null) {
				imageName = UUID.randomUUID().toString() + croppedImage.getOriginalFilename()
						.substring(croppedImage.getOriginalFilename().lastIndexOf("."));
				croppedImage.transferTo(new File("D:/Project-Data/blog-data/user/" + imageName));
				if (!currentUser.getImageName().equals("default.jpg"))
					FileUtils.deleteQuietly(new File(
							"D:/Project-Data/blog-data/user/" + currentUser.getImageName()));
				currentUser.setImageName(imageName);
				isModified = true;
			}
			if (isModified) {
				try {
					// 是博主则同时也更新博主部分信息
					if (SecurityUtils.getSubject().hasRole("blogger")) {
						Blogger blogger = (Blogger) session.getServletContext()
								.getAttribute("blogger");
						if (StringUtil.isNotEmpty(currentUser.getNickName())
								&& !blogger.getNickName().equals(currentUser.getNickName())) {
							blogger.setNickName(currentUser.getNickName());
						}
						if (imageName != null) {
							blogger.setImageName(imageName);
						}
						bloggerService.update(blogger);
					}

					userService.update(currentUser);
					Subject subject = SecurityUtils.getSubject();
					var token = new UsernamePasswordToken(currentUser.getUserName(),
							currentUser.getPassword());
					subject.login(token);
					session.setAttribute("currentUser",
							userService.getUserByUserName(currentUser.getUserName()));
					return "success";
				} catch (Exception e) {
					System.out.println(e);
					return "error";
				}
			} else
				return "success";
		}
		return "error";
	}

	@RequestMapping("/register")
	public ModelAndView register() {
		var mav = new ModelAndView("foreground/user/userTamplate");
		mav.addObject("pageTitle", "用户注册");
		mav.addObject("mainPage", "/foreground/user/register.jsp");
		return mav;
	}

	@PostMapping("/existUserWithUserName")
	@ResponseBody
	public Map<String, Boolean> existUserWithUserName(@RequestParam("userName") String userName) {
		User user = userService.getUserByUserName(userName);
		var map = new HashMap<String, Boolean>();
		if (user != null)
			map.put("exist", true);
		else
			map.put("exist", false);
		return map;
	}

	@RequestMapping("/registerOn")
	@ResponseBody
	public String registerOn(User user, HttpSession session,
			@RequestParam(value = "croppedImage", required = false) MultipartFile croppedImage,
			HttpServletResponse response) throws Exception {
		if (user != null && StringUtil.isNotEmpty(user.getUserName())
				&& StringUtil.isNotEmpty(user.getPassword())) {
			if (croppedImage != null) {
				String imageName = UUID.randomUUID().toString() + croppedImage.getOriginalFilename()
						.substring(croppedImage.getOriginalFilename().lastIndexOf("."));
				croppedImage.transferTo(new File("D:/Project-Data/blog-data/user/" + imageName));
				user.setImageName(imageName);
			}
			user.setPassword(CryptographyUtil.md5(user.getPassword(), env.getProperty("md5.salt")));
			userService.add(user);
			Subject subject = SecurityUtils.getSubject();
			var token = new UsernamePasswordToken(user.getUserName(), user.getPassword());
			try {
				subject.login(token);
				session.setAttribute("currentUser",
						userService.getUserByUserName(user.getUserName()));
				return "success";
			} catch (Exception e) {
				System.out.println(e);
				return "error";
			}
		}
		return "error";
	}

	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		SecurityUtils.getSubject().logout();
		return "redirect:/index";
	}
}
