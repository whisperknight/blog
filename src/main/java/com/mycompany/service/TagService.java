package com.mycompany.service;

import java.util.List;
import java.util.Map;

import com.mycompany.entity.Tag;

public interface TagService {
	/**
	 * 获取所有标签
	 * @return
	 */
	public List<Tag> getAllTags();
	
	/**
	 * 分页查找标签列表
	 * @return
	 */
	public List<Tag> getTagList(Map<String, Object> map);
	
	/**
	 * 查找标签数量
	 * @return
	 */
	public Long getTagCount(Map<String, Object> map);
	
	/**
	 * 添加标签
	 * @param tag
	 * @return 插入标签的ID
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
