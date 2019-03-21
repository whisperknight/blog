package com.mycompany.entity;

import java.io.Serializable;

public class Blogger implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String userName;
	private String password;
	private String nickName;// 昵称
	private String sign;// 签名
	private String profile;// 简介
	private String imageName;// 头像名称

	public Blogger() {
		super();
	}

	public Blogger(String userName, String password) {
		super();
		this.userName = userName;
		this.password = password;
	}

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
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

}
