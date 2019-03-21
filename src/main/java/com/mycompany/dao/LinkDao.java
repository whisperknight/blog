package com.mycompany.dao;

import java.util.List;
import java.util.Map;

import com.mycompany.entity.Link;

public interface LinkDao {

	/**
	 * 查找所有链接
	 * @return
	 */
	public List<Link> findAllLinks();
	
	/**
	 * 分页查找外链列表
	 * @return
	 */
	public List<Link> findLinkList(Map<String, Object> map);
	
	/**
	 * 查找外链数量
	 * @return
	 */
	public Long countLinkList(Map<String, Object> map);
	
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
