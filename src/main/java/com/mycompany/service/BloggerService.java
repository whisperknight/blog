package com.mycompany.service;

import com.mycompany.entity.Blogger;

public interface BloggerService {
	
	/**
	 * 获取博主信息，只有一个博主且ID为1
	 * @return
	 */
	public Blogger getBlogger();
	
	/**
	 * 更新博主
	 * @param user
	 * @return
	 */
	public Integer update(Blogger blogger);
}
