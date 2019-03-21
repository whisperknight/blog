package com.mycompany.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mycompany.dao.BlogDao;
import com.mycompany.dao.CommentDao;
import com.mycompany.dao.InnerCommentDao;
import com.mycompany.entity.Blog;
import com.mycompany.service.BlogService;

@Service("blogService")
public class BlogServiceImpl implements BlogService {

	@Resource
	private BlogDao blogDao;
	@Resource
	private CommentDao commentDao;
	@Resource
	private InnerCommentDao innerCommentDao;

	@Override
	public List<Blog> getBlogReleaseDateStrWithblogNum() {
		return blogDao.findBlogReleaseDateStrWithblogNum();
	}

	@Override
	public List<Blog> getBlogList(Map<String, Object> map) {
		return blogDao.findBlogList(map);
	}

	@Override
	public Long getBlogCount(Map<String, Object> map) {
		return blogDao.countBlogList(map);
	}

	@Override
	public List<Blog> getBlogListWithClickNumTop10() {
		return blogDao.findBlogListWithClickNumTop10();
	}

	@Override
	public Blog getBlogById(Integer id) {
		return blogDao.findBlogById(id);
	}

	@Override
	public Integer update(Blog blog) {
		if (blog != null && blog.getId() != null)
			return blogDao.update(blog);
		else
			return 0;
	}

	@Override
	public Blog getPreviousBlogById(Integer id) {
		return blogDao.findPreviousBlogById(id);
	}

	@Override
	public Blog getNextBlogById(Integer id) {
		return blogDao.findNextBlogById(id);
	}

	@Override
	public Integer add(Blog blog) {
		return blogDao.add(blog);
	}

	@Transactional
	@Override
	public Integer addTagForBlog(Integer blogId, List<Integer> tagIdList) {
		int result = 0;
		for (Integer tagId : tagIdList) {
			var map = new HashMap<String, Integer>();
			map.put("blogId", blogId);
			map.put("tagId", tagId);
			blogDao.addTagForBlog(map);
			result++;
		}
		return result;
	}

	@Transactional
	@Override
	public Integer delete(Integer id) {
		//删除标签
		blogDao.deleteTagOfBlogByBlogId(id);
		
		List<Integer> commentIdList = commentDao.findCommentIdListByBlogId(id);
		for (Integer commentId : commentIdList) {
			//删除内部评论
			innerCommentDao.deleteByCommentId(commentId);
			//删除评论
			commentDao.delete(commentId);
		}
		
		//删除博客
		return blogDao.delete(id);
	}

	@Override
	public Integer deleteTagOfBlogByBlogId(Integer blogId) {
		return blogDao.deleteTagOfBlogByBlogId(blogId);
	}

	@Override
	public Integer deleteTagOfBlogByTagId(Integer tagId) {
		return blogDao.deleteTagOfBlogByTagId(tagId);
	}

}
