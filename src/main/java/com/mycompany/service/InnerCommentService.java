package com.mycompany.service;

import java.util.List;
import java.util.Map;

import com.mycompany.entity.InnerComment;

public interface InnerCommentService {
	/**
	 * 获取内部评论列表
	 * @return
	 */
	public List<InnerComment> getInnerCommentList(Map<String, Object> map);
	
	/**
	 * 内部评论列表数量
	 * @param map
	 * @return
	 */
	public Long getInnerCommentCount(Map<String, Object> map);
	
	/**
	 * 保存内部评论
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
}
