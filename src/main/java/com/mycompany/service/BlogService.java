package com.mycompany.service;

import java.util.List;
import java.util.Map;

import com.mycompany.entity.Blog;

public interface BlogService {
	/**
	 * 获取博文日期及其博文数量
	 * @return
	 */
	public List<Blog> getBlogReleaseDateStrWithblogNum();
	
	/**
	 * 分页获取博文列表
	 * @param map
	 * @return
	 */
	public List<Blog> getBlogList(Map<String, Object> map);
	
	/**
	 * 获取博文列表数量
	 * @param map
	 * @return
	 */
	public Long getBlogCount(Map<String, Object> map);
	
	/**
	 * 获取点击次数最高的10条博文列表
	 * @param map
	 * @return
	 */
	public List<Blog> getBlogListWithClickNumTop10();
	
	/**
	 * 根据ID获取博文，并增加一次clickNum阅读次数
	 * @return
	 */
	public Blog getBlogById(Integer id);
	
	/**
	 * 更新博文
	 * @param blog
	 * @return
	 */
	public Integer update(Blog blog);
	
	/**
	 * 查找上一篇博文
	 * @param id
	 * @return
	 */
	public Blog getPreviousBlogById(Integer id);
	
	/**
	 * 查找下一篇博文
	 * @param id
	 * @return
	 */
	public Blog getNextBlogById(Integer id);
	
	/**
	 * 添加博文
	 * @param blog
	 * @return 插入博文的ID
	 */
	public Integer add(Blog blog);
	
	/**
	 * 给博文添加标签
	 * @param tag
	 * @return
	 */
	public Integer addTagForBlog(Integer blogId, List<Integer> tagIdList);
	
	/**
	 * 删除博文
	 * @param blogId
	 * @return
	 */
	public Integer delete(Integer id);
	
	/**
	 * 删除指定博文的标签关系
	 * @param tag
	 * @return
	 */
	public Integer deleteTagOfBlogByBlogId(Integer blogId);
	
	/**
	 * 删除指定标签的博文标签关系
	 * @param tag
	 * @return
	 */
	public Integer deleteTagOfBlogByTagId(Integer tagId);
}
