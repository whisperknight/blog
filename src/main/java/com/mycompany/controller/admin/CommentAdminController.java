package com.mycompany.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mycompany.entity.Blog;
import com.mycompany.entity.Comment;
import com.mycompany.entity.InnerComment;
import com.mycompany.entity.User;
import com.mycompany.service.CommentService;
import com.mycompany.service.InnerCommentService;
import com.mycompany.util.StringUtil;

@Controller
@RequestMapping("/admin/comment")
public class CommentAdminController {

	@Resource
	CommentService commentService;
	@Resource
	InnerCommentService innerCommentService;

	/**
	 * 请求JSP：评论列表
	 * 
	 * @return
	 */
	@RequestMapping("/commentList")
	public String commentList() {
		return "admin/tags/commentList";
	}

	/**
	 * 请求JSP：内部评论列表
	 * 
	 * @return
	 */
	@RequestMapping("/innerCommentList")
	public String innerCommentList() {
		return "admin/tags/innerCommentList";
	}

	@RequestMapping("/list")
	@ResponseBody
	public Map<String, Object> list(@RequestParam("page") Integer page,
			@RequestParam("rows") Integer rows, Comment s_comment) {
		var map = new HashMap<String, Object>();
		if (s_comment != null && StringUtil.isNotEmpty(s_comment.getContent()))
			map.put("content", s_comment.getContent());
		map.put("start", (page - 1) * rows);
		map.put("size", rows);
		List<Comment> commentList = commentService.getCommentList(map);
		Long total = commentService.getCommentCount(map);

		// 处理下association关联获取的数据，只往前端传递特定数据
		User user = null;
		Blog blog = null;
		for (Comment comment : commentList) {
			user = new User();
			user.setUserName(comment.getUser().getUserName());
			user.setNickName(comment.getUser().getNickName());
			comment.setUser(user);
			blog = new Blog();
			blog.setId(comment.getBlog().getId());
			blog.setTitle(comment.getBlog().getTitle());
			comment.setBlog(blog);

			map.clear();
			map.put("commentId", comment.getId());
			Long innerCommentCount = innerCommentService.getInnerCommentCount(map);
			comment.setInnerCommentCount(innerCommentCount);
		}

		map.clear();
		map.put("rows", commentList);
		map.put("total", total);
		return map;
	}

	@RequestMapping("/inner/list")
	@ResponseBody
	public Map<String, Object> innerList(
			@RequestParam(value = "commentId", required = false) Integer commentId,
			@RequestParam("page") Integer page, @RequestParam("rows") Integer rows,
			InnerComment s_innerComment) {
		var map = new HashMap<String, Object>();

		if (commentId != null)
			map.put("commentId", commentId);
		if (s_innerComment != null && StringUtil.isNotEmpty(s_innerComment.getContent()))
			map.put("content", s_innerComment.getContent());
		map.put("start", (page - 1) * rows);
		map.put("size", rows);

		List<InnerComment> innerCommentList = innerCommentService.getInnerCommentList(map);
		Long total = innerCommentService.getInnerCommentCount(map);

		// 处理下association关联获取的数据，只往前端传递特定数据
		Comment comment = null;
		User user = null;
		for (InnerComment innerComment : innerCommentList) {
			comment = new Comment();
			comment.setId(innerComment.getComment().getId());
			comment.setContent(innerComment.getComment().getContent());
			innerComment.setComment(comment);
			user = new User();
			user.setUserName(innerComment.getUser().getUserName());
			user.setNickName(innerComment.getUser().getNickName());
			innerComment.setUser(user);
			if(innerComment.getReplyToUser()!=null) {
				user = new User();
				user.setUserName(innerComment.getReplyToUser().getUserName());
				user.setNickName(innerComment.getReplyToUser().getNickName());
				innerComment.setReplyToUser(user);
			}
		}

		map.clear();
		map.put("rows", innerCommentList);
		map.put("total", total);
		return map;
	}

	@RequestMapping("/delete")
	@ResponseBody
	public Map<String, Object> delete(@RequestParam("strIds") String strIds) {
		var map = new HashMap<String, Object>();
		if (strIds != null) {
			String[] tagIds = strIds.split(",");
			for (String tagId : tagIds) {
				commentService.delete(Integer.parseInt(tagId));
			}
			map.put("success", true);
		} else
			map.put("success", false);
		return map;
	}

	@RequestMapping("/inner/delete")
	@ResponseBody
	public Map<String, Object> innerDelete(@RequestParam("strIds") String strIds) {
		var map = new HashMap<String, Object>();
		if (strIds != null) {
			String[] tagIds = strIds.split(",");
			for (String tagId : tagIds) {
				innerCommentService.delete(Integer.parseInt(tagId));
			}
			map.put("success", true);
		} else
			map.put("success", false);
		return map;
	}

}
