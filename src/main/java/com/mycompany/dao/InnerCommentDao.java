package com.mycompany.dao;

import java.util.List;
import java.util.Map;

import com.mycompany.entity.InnerComment;

public interface InnerCommentDao {

	/**
	 * 查找内部评论列表
	 * @return
	 */
	public List<InnerComment> findInnerCommentList(Map<String, Object> map);
	
	/**
	 * 内部评论列表总数
	 * @return
	 */
	public Long countInnerCommentList(Map<String, Object> map);
	
	/**
	 * 添加内部评论
	 * @param map
	 * @return
	 */
	public Integer add(InnerComment innerComment);
	
	/**
	 * 删除内部评论
	 * @param id
	 * @return
	 */
	public Integer delete(Integer id);
	
	/**
	 * 通过评论ID删除内部评论
	 * @param commentId
	 * @return
	 */
	public Integer deleteByCommentId(Integer commentId);
}
