package com.mycompany.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mycompany.entity.Tag;
import com.mycompany.entity.User;
import com.mycompany.service.UserService;
import com.mycompany.util.CryptographyUtil;
import com.mycompany.util.StringUtil;

@Controller
@RequestMapping("/admin/user")
public class UserAdminController implements EnvironmentAware {

	/**
	 * 注入环境属性
	 */
	private Environment env;

	@Override
	public void setEnvironment(Environment environment) {
		this.env = environment;
	}

	@Resource
	UserService userService;

	/**
	 * 请求JSP：标签列表
	 * 
	 * @return
	 */
	@RequestMapping("/userList")
	public String blogList() {
		return "admin/tags/userList";
	}

	@PostMapping("/existUserWithUserName")
	@ResponseBody
	public String existUserWithUserName(@RequestParam("userName") String userName) {
		User user = userService.getUserByUserName(userName);
		if (user == null)
			return "true";
		else
			return "false";
	}

	@RequestMapping("/list")
	@ResponseBody
	public Map<String, Object> list(@RequestParam("page") Integer page,
			@RequestParam("rows") Integer rows, User s_user) {
		var map = new HashMap<String, Object>();
		if (s_user != null && StringUtil.isNotEmpty(s_user.getUserName()))
			map.put("userName", s_user.getUserName());
		map.put("start", (page - 1) * rows);
		map.put("size", rows);
		List<Tag> userList = userService.getUserList(map);
		Long total = userService.getUserCount(map);
		map.clear();
		map.put("rows", userList);
		map.put("total", total);
		return map;
	}

	@RequestMapping("/save")
	@ResponseBody
	public Map<String, Object> save(User user, HttpSession session) {
		var map = new HashMap<String, Object>();
		if (user != null) {

			if (StringUtil.isNotEmpty(user.getPassword()))
				user.setPassword(
						CryptographyUtil.md5(user.getPassword(), env.getProperty("md5.salt")));

			if (user.getId() == null)
				userService.add(user);
			else
				userService.update(user);
			map.put("success", true);
		}
		return map;
	}

	@RequestMapping("/delete")
	@ResponseBody
	public Map<String, Object> delete(@RequestParam("strIds") String strIds, HttpSession session) {
		var map = new HashMap<String, Object>();
		if (strIds != null) {
			String[] tagIds = strIds.split(",");
			for (String tagId : tagIds) {
				userService.delete(Integer.parseInt(tagId));
			}
			map.put("success", true);
		} else
			map.put("success", false);
		return map;
	}
}
