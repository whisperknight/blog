package com.mycompany.service;

import java.util.List;
import java.util.Map;

import com.mycompany.entity.Link;

public interface LinkService {
	/**
	 * 查找所有链接
	 * @return
	 */
	public List<Link> getAllLinks();
	
	/**
	 * 分页查找外链列表
	 * @return
	 */
	public List<Link> getLinkList(Map<String, Object> map);
	
	/**
	 * 查找外链数量
	 * @return
	 */
	public Long getLinkCount(Map<String, Object> map);
	
	/**
	 * 添加外链
	 * @param link
	 * @return
	 */
	public Integer add(Link link);
	
	/**
	 * 更新外链
	 * @param link
	 * @return
	 */
	public Integer update(Link link);
	
	/**
	 * 删除外链
	 * @param link
	 * @return
	 */
	public Integer delete(Integer id);
}
