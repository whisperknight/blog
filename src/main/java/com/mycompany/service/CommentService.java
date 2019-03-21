package com.mycompany.service;

import java.util.List;
import java.util.Map;

import com.mycompany.entity.Comment;

public interface CommentService {
	/**
	 * 获取评论列表
	 * @return
	 */
	public List<Comment> getCommentList(Map<String, Object> map);
	
	/**
	 * 评论列表数量
	 * @param map
	 * @return
	 */
	public Long getCommentCount(Map<String, Object> map);
	
	/**
	 * 保存评论
	 * @param map
	 * @return
	 */
	public Integer add(Comment comment);
	
	/**
	 * 查找单条评论
	 * @param map
	 * @return
	 */
	public Comment getCommentById(Integer id);
	
	/**
	 * 删除评论
	 * @param id
	 * @return
	 */
	public Integer delete(Integer id);
	
}
