package com.mycompany.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.mycompany.entity.Blog;
import com.mycompany.lucene.BlogIndex;
import com.mycompany.service.BlogService;
import com.mycompany.util.BootStrapPaginationUtil;
import com.mycompany.util.BootStrapPaginationUtil.Pagination;

@Controller
@RequestMapping("/blog")
public class BlogController {

	private static Integer searchSizePerPage = 10;// 搜索结果页面每页记录数

	@Resource
	private BlogService blogService;

	private BlogIndex blogIndex = new BlogIndex();

	@RequestMapping("/{id}")
	public ModelAndView getBlogDetail(@PathVariable("id") Integer id, HttpSession session) {
		Blog blog = blogService.getBlogById(id);

		if (blog != null) {
			var mav = new ModelAndView("mainTamplate");
			mav.addObject("blog", blog);

			//阅读次数+1
			@SuppressWarnings("unchecked")
			List<Integer> browsedBlogIdList = (List<Integer>) session
					.getAttribute("browsedBlogIdList");
			if (browsedBlogIdList == null) {
				browsedBlogIdList = new ArrayList<>();
				session.setAttribute("browsedBlogIdList", browsedBlogIdList);
			}
			if(!browsedBlogIdList.contains(id)) {
				blog.setClickNum(blog.getClickNum() + 1);
				blogService.update(blog);// 阅读次数+1
				browsedBlogIdList.add(id);
			}
			
			mav.addObject("previousBlog", blogService.getPreviousBlogById(blog.getId()));
			mav.addObject("nextBlog", blogService.getNextBlogById(blog.getId()));
			mav.addObject("pageTitle", blog.getTitle());
			mav.addObject("mainPage", "/foreground/blog/detail.jsp");
			return mav;
		}
		var mav = new ModelAndView("error");
		return mav;
	}

	@RequestMapping("/search")
	public ModelAndView search(@RequestParam("q") String q,
			@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
			HttpServletRequest request) throws Exception {
		var mav = new ModelAndView("mainTamplate");
		List<Blog> searchRecordList = blogIndex.searchBlog(q);
		List<Blog> blogList = searchRecordList.subList((page - 1) * searchSizePerPage,
				searchRecordList.size() >= page * searchSizePerPage ? page * searchSizePerPage
						: searchRecordList.size());
		mav.addObject("blogList", blogList);
		mav.addObject("q", q);
		mav.addObject("blogListSize", searchRecordList.size());

		// 分页
		if (searchRecordList.size() > searchSizePerPage) {
			String preUrl = request.getRequestURI() + "?q=" + q;
			Pagination pagination = BootStrapPaginationUtil
					.getPagination((long) searchRecordList.size(), searchSizePerPage, page, preUrl);
			mav.addObject("pagination", pagination);
		}

		mav.addObject("pageTitle", "'" + q + "'的搜索结果");
		mav.addObject("mainPage", "/foreground/blog/searchResult.jsp");
		return mav;
	}
}
