package com.mycompany.controller.admin;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.mycompany.entity.Blogger;
import com.mycompany.entity.User;
import com.mycompany.service.BloggerService;
import com.mycompany.service.UserService;

@Controller
@RequestMapping("/admin/blogger")
public class BloggerAdminController {

	@Resource
	BloggerService bloggerService;
	@Resource
	UserService userService;

	/**
	 * 请求JSP：编辑博主额外信息
	 * 
	 * @return
	 */
	@RequestMapping("/editBlogger")
	public String editblogger() {
		return "admin/tags/editBlogger";
	}

	/**
	 * 执行：更新博主信息
	 * 
	 * @param blog
	 * @throws Exception
	 */
	@RequestMapping("/update")
	@ResponseBody
	public Map<String, Boolean> update(Blogger blogger, HttpSession session) throws Exception {
		var map = new HashMap<String, Boolean>();
		if (blogger != null) {
			bloggerService.update(blogger);
			User user = new User();
			
			//联动更新用户表的部分数据
			user.setId(((User)(session.getAttribute("currentUser"))).getId());
			user.setNickName(blogger.getNickName());
			user.setImageName(blogger.getImageName());
			userService.update(user);
			
			//刷新application
			blogger = bloggerService.getBlogger();
			blogger.setUserName(null);
			session.getServletContext().setAttribute("blogger", blogger);
			
			map.put("success", true);
		} else
			map.put("success", false);
		return map;
	}

	/**
	 * 执行：UEditor的文件上传
	 * 
	 * @param upfile
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/upload")
	@ResponseBody
	public Map<String, String> upload(@RequestParam("upfile") MultipartFile upfile)
			throws Exception {
		String originalFileName = upfile.getOriginalFilename();
		String newFileName = UUID.randomUUID().toString() + upfile.getOriginalFilename()
				.substring(upfile.getOriginalFilename().lastIndexOf("."));
		upfile.transferTo(new File("D:/Project-Data/blog-data/blogger/" + newFileName));

		// 返回结果信息(UEditor需要)
		Map<String, String> map = new HashMap<String, String>();
		map.put("state", "SUCCESS");// 是否上传成功
		map.put("title", newFileName);// 现在文件名称
		map.put("original", originalFileName);// 文件原名称
		map.put("type", originalFileName.substring(upfile.getOriginalFilename().lastIndexOf(".")));// 文件类型:
																									// .+后缀名
		map.put("url", "/blog-data/blogger/" + newFileName);// 回显访问路径
		map.put("size", upfile.getSize() + "");// 文件大小（字节数）
		return map;
	}
}
