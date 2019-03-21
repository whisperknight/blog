package com.mycompany.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import com.mycompany.entity.Blog;
import com.mycompany.entity.Blogger;
import com.mycompany.entity.Link;
import com.mycompany.entity.Tag;
import com.mycompany.service.BlogService;
import com.mycompany.service.BloggerService;
import com.mycompany.service.LinkService;
import com.mycompany.service.TagService;

/**
 * web应用初始化配置
 * 
 * @author WhisperKnight
 *
 */
@Component
public class InitController implements ServletContextAware {

	@Resource
	private BloggerService bloggerService;
	@Resource
	private LinkService linkService;
	@Resource
	private TagService tagService;
	@Resource
	private BlogService blogService;

	/**
	 * 通过ServletContextAware获取application
	 */
	private ServletContext application;

	@Override
	public void setServletContext(ServletContext servletContext) {
		this.application = servletContext;
	}

	/**
	 * 初始化，被@PostConstruct注解的方法在依赖注入完成后被自动调用
	 */
	@PostConstruct
	public void init() {
		System.out.println("init 执行前");

		// 博主信息
		Blogger blogger = bloggerService.getBlogger();
		blogger.setUserName(null);
		application.setAttribute("blogger", blogger);

		// 底部链接信息
		List<Link> linkList = linkService.getAllLinks();
		application.setAttribute("linkList", linkList);

		// 右侧所有标签信息
		List<Tag> tagList = tagService.getAllTags();
		application.setAttribute("tagList", tagList);

		// 右侧博文日期分类信息
		List<Blog> blogReleaseDateList = blogService.getBlogReleaseDateStrWithblogNum();
		application.setAttribute("blogReleaseDateList", blogReleaseDateList);

		// 右侧10条最热门博文（以后可以设置定时刷新）
		List<Blog> hotBlogList = blogService.getBlogListWithClickNumTop10();
		application.setAttribute("hotBlogList", hotBlogList);

		System.out.println("init 执行完毕");
	}
}
