package com.mycompany.dao;

import java.util.List;
import java.util.Map;

import com.mycompany.entity.Blog;
import com.mycompany.entity.Tag;

public interface BlogDao {
	/**
	 * 获取博文日期及其博文数量
	 * @return
	 */
	public List<Blog> findBlogReleaseDateStrWithblogNum();
	
	/**
	 * 分页获取博文列表
	 * @param map
	 * @return
	 */
	public List<Blog> findBlogList(Map<String, Object> map);
	
	/**
	 * 获取博文列表数量
	 * @param map
	 * @return
	 */
	public Long countBlogList(Map<String, Object> map);
	
	/**
	 * 获取点击次数最高的10条博文列表
	 * @param map
	 * @return
	 */
	public List<Blog> findBlogListWithClickNumTop10();
	
	/**
	 * 根据ID获取博文
	 * @param id
	 * @return
	 */
	public Blog findBlogById(Integer id);
	
	/**
	 * 更新博客
	 * @param blog
	 */
	public Integer update(Blog blog);
	
	/**
	 * 查找上一篇博文
	 * @param id
	 * @return
	 */
	public Blog findPreviousBlogById(Integer id);
	
	/**
	 * 查找下一篇博文
	 * @param id
	 * @return
	 */
	public Blog findNextBlogById(Integer id);
	
	/**
	 * 添加博文
	 * @param blog
	 * @return
	 */
	public Integer add(Blog blog);
	
	/**
	 * 通过博文ID查找其包含的所有标签
	 * @return
	 */
	public List<Tag> findTagListByBlogId(Integer blogId);
	
	/**
	 * 给博文添加标签
	 * @param tag
	 * @return
	 */
	public Integer addTagForBlog(Map<String, Integer> map);
	
	/**
	 * 删除博文
	 * @param id
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
