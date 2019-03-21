package com.mycompany.controller;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.mycompany.entity.Blog;
import com.mycompany.service.BlogService;
import com.mycompany.util.BootStrapPaginationUtil;
import com.mycompany.util.BootStrapPaginationUtil.Pagination;
import com.mycompany.util.StringUtil;

/**
 * 主控：设置未找到页面，设置主页index的控制
 * 此项目对restful风格的处理方式为：静态jsp在servlet的url匹配为/时本身是能访问的，此项目对所有的静态jsp页面做了restful处理
 * 特别的，springboot的restful处理方式为：是将静态页面写成html直接静态加载，而动态页面的处理交给freemaker或thymleaf
 * 
 * @author WhisperKnight
 *
 */
@Controller
public class MainController {

	private static Integer mainPageBlogSize = 10;

	@Resource
	private BlogService blogService;

	// 未找到页面的映射。匹配顺序：静态资源>精确匹配>模糊匹配
	@RequestMapping("/**")
	public String notFound() {
		return "error";
	}

	// 访问主页
	@RequestMapping("/index")
	public ModelAndView index(
			@RequestParam(value = "page", required = false, defaultValue = "1") String page,
			@RequestParam(value = "tagId", required = false) String tagId,
			@RequestParam(value = "releaseDateStr", required = false) String releaseDateStr,
			HttpServletRequest request) {
		var mav = new ModelAndView("mainTamplate");
		StringBuffer preUrl = new StringBuffer(request.getRequestURI() + "?");// 用于分页传值

		var map = new HashMap<String, Object>();
		map.put("start", (Integer.parseInt(page) - 1) * mainPageBlogSize);
		map.put("size", mainPageBlogSize);
		if (tagId != null) {
			map.put("tagId", Integer.parseInt(tagId));
			preUrl.append("tagId=" + tagId);
		}
		if (releaseDateStr != null) {
			map.put("releaseDateStr", releaseDateStr);
			preUrl.append("releaseDateStr=" + releaseDateStr);
		}

		// 主页博文列表
		List<Blog> blogList = blogService.getBlogList(map);
		for (Blog blog : blogList) {// 向每个blog里添加缩略图列表
			List<String> thumbnailList = blog.getThumbnailList();
			String content = blog.getContent();
			if (StringUtil.isNotEmpty(content)) {
				Document doc = Jsoup.parse(content);
				Elements jpgs = doc.select("img");
				for (int i = 0; i < jpgs.size(); i++) {
					Element jpg = jpgs.get(i);
					thumbnailList.add(jpg.toString());
					if (i >= 2)
						break;
				}
			}
		}
		mav.addObject("blogList", blogList);

		// 博文列表的分页
		Long blogRecord = blogService.getBlogCount(map);
		if(blogRecord>mainPageBlogSize) {
			Pagination pagination = BootStrapPaginationUtil.getPagination(blogRecord,
					mainPageBlogSize, Integer.parseInt(page), preUrl.toString());
			mav.addObject("pagination", pagination);
		}

		mav.addObject("pageTitle", "主页");
		mav.addObject("mainPage", "/foreground/blog/list.jsp");
		return mav;
	}

}
