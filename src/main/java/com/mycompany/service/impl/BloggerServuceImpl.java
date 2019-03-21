package com.mycompany.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mycompany.dao.BloggerDao;
import com.mycompany.entity.Blogger;
import com.mycompany.service.BloggerService;

@Service("bloggerService")
public class BloggerServuceImpl implements BloggerService {
	@Resource
	private BloggerDao bloggerDao;

	@Override
	public Blogger getBlogger() {
		return bloggerDao.findBlogger();
	}

	@Override
	public Integer update(Blogger blogger) {
		return bloggerDao.update(blogger);
	}

}
