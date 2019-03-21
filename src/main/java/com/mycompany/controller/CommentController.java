package com.mycompany.controller;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mycompany.entity.Comment;
import com.mycompany.entity.InnerComment;
import com.mycompany.entity.User;
import com.mycompany.service.CommentService;
import com.mycompany.service.InnerCommentService;
import com.mycompany.util.BootStrapPaginationUtil;
import com.mycompany.util.BootStrapPaginationUtil.Pagination;

@Controller
@RequestMapping("/comment")
public class CommentController {

	private static Integer commentSize = 8;
	private static Integer innerCommentSize = 5;

	@Resource
	CommentService commentService;
	@Resource
	InnerCommentService innerCommentService;

	@RequestMapping("/list")
	public ModelAndView getCommentList(@RequestParam(value = "blogId") Integer blogId,
			@RequestParam(value = "page", required = false, defaultValue = "1") String page,
			HttpServletRequest request) {
		var mav = new ModelAndView("foreground/comment/commentList");

		// 列表
		var map = new HashMap<String, Object>();
		map.put("blogId", blogId);
		map.put("start", (Integer.parseInt(page) - 1) * commentSize);
		map.put("size", commentSize);
		List<Comment> commentList = commentService.getCommentList(map);
		mav.addObject("blogId", blogId);
		mav.addObject("commentList", commentList);

		// 分页
		Long totalRecord = commentService.getCommentCount(map);
		if (totalRecord > commentSize) {
			StringBuffer preUrl = new StringBuffer(request.getRequestURI() + "?");// 用于分页传值
			Pagination pagination = BootStrapPaginationUtil.getPagination(totalRecord, commentSize,
					Integer.parseInt(page), preUrl.toString());
			mav.addObject("pagination", pagination);
		}

		return mav;
	}

	@RequestMapping("/inner/list")
	public ModelAndView getInnerCommentList(@RequestParam(value = "commentId") Integer commentId,
			@RequestParam(value = "page", required = false, defaultValue = "1") String page,
			HttpServletRequest request) {
		var mav = new ModelAndView("foreground/comment/innerCommentList");

		// 列表
		var map = new HashMap<String, Object>();
		map.put("commentId", commentId);
		map.put("start", (Integer.parseInt(page) - 1) * innerCommentSize);
		map.put("size", innerCommentSize);
		List<InnerComment> innerCommentList = innerCommentService.getInnerCommentList(map);
		mav.addObject("commentId", commentId);
		mav.addObject("innerCommentList", innerCommentList);

		// 分页
		Long totalRecord = innerCommentService.getInnerCommentCount(map);
		if (totalRecord > innerCommentSize) {
			Pagination pagination = BootStrapPaginationUtil.getPaginationWithSimpleStyle(totalRecord,
					innerCommentSize, Integer.parseInt(page));
			mav.addObject("pagination", pagination);
		}

		return mav;
	}

	@RequestMapping("/save")
	public String saveComment(Comment comment, HttpSession session,
			RedirectAttributes redirectAttributes) {
		if (comment != null && comment.getBlog() != null && comment.getBlog().getId() != null
				&& comment.getContent() != null) {
			comment.setUser((User) session.getAttribute("currentUser"));

			commentService.add(comment);

			redirectAttributes.addAttribute("blogId", comment.getBlog().getId());
			return "redirect:/comment/list";
		} else
			return null;
	}

	@RequestMapping("/inner/save")
	public String saveInnerComment(InnerComment innerComment, HttpSession session,
			RedirectAttributes redirectAttributes) {
		if (innerComment != null && innerComment.getComment() != null
				&& innerComment.getComment().getId() != null && innerComment.getContent() != null) {
			innerComment.setUser((User) session.getAttribute("currentUser"));

			innerCommentService.add(innerComment);

			redirectAttributes.addAttribute("commentId", innerComment.getComment().getId());
			return "redirect:/comment/inner/list";
		} else
			return null;
	}
}
