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
import com.mycompany.entity.InnerComment;
import com.mycompany.service.InnerCommentService;

@Service("innerCommentService")
public class InnerCommentServiceImpl implements InnerCommentService {

	@Resource
	private InnerCommentDao innerCommentDao;
	@Resource
	private BlogDao blogDao;
	@Resource
	private CommentDao commentDao;

	@Override
	public List<InnerComment> getInnerCommentList(Map<String, Object> map) {
		return innerCommentDao.findInnerCommentList(map);
	}

	@Override
	public Long getInnerCommentCount(Map<String, Object> map) {
		return innerCommentDao.countInnerCommentList(map);
	}

	@Transactional
	@Override
	public Integer add(InnerComment innerComment) {
		var blog = new Blog();
		blog.setId(commentDao.findCommentById(innerComment.getComment().getId()).getBlog().getId());
		blog.setReplyNum(blogDao.findBlogById(blog.getId()).getReplyNum() + 1);
		blogDao.update(blog);
		return innerCommentDao.add(innerComment);
	}

	@Override
	public Integer delete(Integer id) {
		return innerCommentDao.delete(id);
	}
}
