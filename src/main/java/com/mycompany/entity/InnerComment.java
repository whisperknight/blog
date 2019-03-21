package com.mycompany.entity;

import java.io.Serializable;
import java.util.Date;

public class InnerComment implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String content;
	private Date createTime;

	// 非数据库直接属性
	private User user;
	private User replyToUser;
	private Comment comment;
	private String createTimeStr;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getReplyToUser() {
		return replyToUser;
	}

	public void setReplyToUser(User replyToUser) {
		this.replyToUser = replyToUser;
	}

	public Comment getComment() {
		return comment;
	}

	public void setComment(Comment comment) {
		this.comment = comment;
	}

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

}
