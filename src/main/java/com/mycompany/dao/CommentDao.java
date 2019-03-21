package com.mycompany.dao;

import java.util.List;
import java.util.Map;

import com.mycompany.entity.Comment;

public interface CommentDao {

	/**
	 * 查找评论列表
	 * @return
	 */
	public List<Comment> findCommentList(Map<String, Object> map);

	/**
	 * 通过博文ID查找评论ID列表
	 * @return
	 */
	public List<Integer> findCommentIdListByBlogId(Integer blogId);

	/**
	 * 通过用户ID查找评论ID列表
	 * @return
	 */
	public List<Integer> findCommentIdListByUserId(Integer userId);

	/**
	 * 评论列表总数
	 * @return
	 */
	public Long countCommentList(Map<String, Object> map);
	
	/**
	 * 添加评论
	 * @param map
	 * @return
	 */
	public Integer add(Comment comment);

	/**
	 * 查找单条评论
	 * @param map
	 * @return
	 */
	public Comment findCommentById(Integer id);
	
	/**
	 * 删除评论
	 * @param id
	 * @return
	 */
	public Integer delete(Integer id);
}
