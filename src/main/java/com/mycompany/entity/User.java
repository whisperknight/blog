package com.mycompany.entity;

import java.io.Serializable;

public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String userName;
	private String password;
	private String nickName;
	private String imageName = "default.jpg";
	private Integer status;// 此项目的所用用户状态默认都是1表示普通用户，只有博主的状态为-1表示式博主

	// 非数据库直接属性
	@SuppressWarnings("unused")
	private String statusStr;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickName() {
		if (nickName == null)
			return userName;
		else
			return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getStatusStr() {
		if (this.status != null)
			return this.status == 1 ? "普通用户" : "博主";
		else
			return null;
	}

	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}
}
