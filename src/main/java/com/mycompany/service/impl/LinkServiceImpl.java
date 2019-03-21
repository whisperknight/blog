package com.mycompany.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mycompany.dao.LinkDao;
import com.mycompany.entity.Link;
import com.mycompany.service.LinkService;

@Service("linkService")
public class LinkServiceImpl implements LinkService{

	@Resource
	private LinkDao linkDao;
	
	@Override
	public List<Link> getAllLinks() {
		return linkDao.findAllLinks();
	}

	@Override
	public List<Link> getLinkList(Map<String, Object> map) {
		return linkDao.findLinkList(map);
	}

	@Override
	public Long getLinkCount(Map<String, Object> map) {
		return linkDao.countLinkList(map);
	}

	@Override
	public Integer add(Link link) {
		return linkDao.add(link);
	}

	@Override
	public Integer update(Link link) {
		return linkDao.update(link);
	}

	@Override
	public Integer delete(Integer id) {
		return linkDao.delete(id);
	}
	
}
