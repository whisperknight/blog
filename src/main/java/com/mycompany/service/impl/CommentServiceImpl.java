package com.mycompany.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mycompany.dao.BlogDao;
import com.mycompany.dao.CommentDao;
import com.mycompany.dao.InnerCommentDao;
import com.mycompany.entity.Blog;
import com.mycompany.entity.Comment;
import com.mycompany.service.CommentService;

@Service("commentService")
public class CommentServiceImpl implements CommentService {

	@Resource
	private CommentDao commentDao;
	@Resource
	private InnerCommentDao innerCommentDao;
	@Resource
	private BlogDao blogDao;

	@Override
	public List<Comment> getCommentList(Map<String, Object> map) {
		return commentDao.findCommentList(map);
	}

	@Override
	public Long getCommentCount(Map<String, Object> map) {
		return commentDao.countCommentList(map);
	}

	@Transactional
	@Override
	public Integer add(Comment comment) {
		var blog = new Blog();
		blog.setId(comment.getBlog().getId());
		blog.setReplyNum(blogDao.findBlogById(blog.getId()).getReplyNum() + 1);
		blogDao.update(blog);
		return commentDao.add(comment);
	}

	@Override
	public Comment getCommentById(Integer id) {
		return commentDao.findCommentById(id);
	}

	@Transactional
	@Override
	public Integer delete(Integer id) {
		innerCommentDao.deleteByCommentId(id);
		return commentDao.delete(id);
	}
}
