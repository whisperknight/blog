package com.mycompany.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mycompany.dao.BlogDao;
import com.mycompany.dao.TagDao;
import com.mycompany.entity.Tag;
import com.mycompany.service.TagService;

@Service("tagService")
public class TagServiceImpl implements TagService {

	@Resource
	private TagDao tagDao;
	@Resource
	private BlogDao blogDao;

	@Override
	public List<Tag> getAllTags() {
		return tagDao.findAllTags();
	}

	@Override
	public Integer add(Tag tag) {
		return tagDao.add(tag);
	}

	@Override
	public Integer update(Tag tag) {
		if (tag != null && tag.getId() != null)
			return tagDao.update(tag);
		else
			return 0;
	}

	@Transactional
	@Override
	public Integer delete(Integer id) {
		blogDao.deleteTagOfBlogByTagId(id);
		return tagDao.delete(id);
	}

	@Override
	public List<Tag> getTagList(Map<String, Object> map) {
		return tagDao.findTagList(map);
	}

	@Override
	public Long getTagCount(Map<String, Object> map) {
		return tagDao.countTagList(map);
	}

}
