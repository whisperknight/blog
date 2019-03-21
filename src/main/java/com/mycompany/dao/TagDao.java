package com.mycompany.dao;

import java.util.List;
import java.util.Map;

import com.mycompany.entity.Tag;

public interface TagDao {

	/**
	 * 查找所有标签
	 * @return
	 */
	public List<Tag> findAllTags();

	/**
	 * 分页查找标签列表
	 * @return
	 */
	public List<Tag> findTagList(Map<String, Object> map);
	
	/**
	 * 查找标签数量
	 * @return
	 */
	public Long countTagList(Map<String, Object> map);
	
	/**
	 * 添加标签
	 * @param tag
	 * @return
	 */
	public Integer add(Tag tag);
	
	/**
	 * 更新标签
	 * @param tag
	 * @return
	 */
	public Integer update(Tag tag);
	
	/**
	 * 删除标签
	 * @param tag
	 * @return
	 */
	public Integer delete(Integer id);
}
