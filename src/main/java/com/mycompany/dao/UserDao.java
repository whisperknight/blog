package com.mycompany.dao;

import java.util.List;
import java.util.Map;

import com.mycompany.entity.Tag;
import com.mycompany.entity.User;

public interface UserDao {

	/**
	 * 分页查找用户列表（除博主）
	 * @return
	 */
	public List<Tag> findUserList(Map<String, Object> map);
	
	/**
	 * 查询用户数量（除博主）
	 * @return
	 */
	public Long countUserList(Map<String, Object> map);
	
	/**
	 * 通过用户名查找用户所有信息
	 * 
	 * @param userName
	 * @return
	 */
	public User findUserByUserName(String userName);

	/**
	 * 通过Id查找用户所有信息
	 * @param id
	 * @return
	 */
	public User findUserById(Integer id);
	
	/**
	 * 添加用户
	 * @param user
	 * @return
	 */
	public Integer add(User user);
	
	/**
	 * 更新用户
	 * @param user
	 * @return
	 */
	public Integer update(User user);
	
	/**
	 * 删除用户
	 * @param id
	 * @return
	 */
	public Integer delete(Integer id);
	
}
