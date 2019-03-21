package com.mycompany.entity;

import java.io.Serializable;
import java.util.Date;

public class Comment implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String content;
	private Date createTime;
	
	//非数据库直接属性
	private User user;
	private Blog blog;
	private String createTimeStr;
	private Long innerCommentCount;

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

	public Blog getBlog() {
		return blog;
	}

	public void setBlog(Blog blog) {
		this.blog = blog;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	public Long getInnerCommentCount() {
		return innerCommentCount;
	}

	public void setInnerCommentCount(Long innerCommentCount) {
		this.innerCommentCount = innerCommentCount;
	}

}
