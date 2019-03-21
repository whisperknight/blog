package com.mycompany.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mycompany.dao.CommentDao;
import com.mycompany.dao.InnerCommentDao;
import com.mycompany.dao.UserDao;
import com.mycompany.entity.Tag;
import com.mycompany.entity.User;
import com.mycompany.service.UserService;
import com.mycompany.util.StringUtil;

@Service("userService")
public class UserServuceImpl implements UserService {
	@Resource
	private UserDao userDao;
	@Resource
	private CommentDao commentDao;
	@Resource
	private InnerCommentDao innerCommentDao;

	@Override
	public User getUserByUserName(String userName) {
		if (StringUtil.isNotEmpty(userName))
			return userDao.findUserByUserName(userName);
		else
			return null;
	}

	@Override
	public Integer add(User user) {
		return userDao.add(user);
	}

	@Override
	public Integer update(User user) {
		if (user != null && user.getId() != null)
			return userDao.update(user);
		else
			return null;
	}

	@Override
	public List<Tag> getUserList(Map<String, Object> map) {
		return userDao.findUserList(map);
	}

	@Override
	public Long getUserCount(Map<String, Object> map) {
		return userDao.countUserList(map);
	}

	@Transactional
	@Override
	public Integer delete(Integer id) {
		List<Integer> commentIdList = commentDao.findCommentIdListByUserId(id);
		for (Integer commentId : commentIdList) {
			// 删除内部评论
			innerCommentDao.deleteByCommentId(commentId);
			// 删除评论
			commentDao.delete(commentId);
		}

		// 删除用户
		return userDao.delete(id);
	}

}
