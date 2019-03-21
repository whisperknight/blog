package com.mycompany.dao;

import com.mycompany.entity.Blogger;

public interface BloggerDao {

	/**
	 * 查找博主详细信息。注：只有一个博主且ID为1，userName和t_user表的博主一样，没有密码
	 * @return
	 */
	public Blogger findBlogger();
	
	/**
	 * 更新博主
	 * @param user
	 * @return
	 */
	public Integer update(Blogger blogger);
}
