package com.mycompany.entity;

import java.io.Serializable;

public class Tag implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String name;
	private Integer orderNo;

	// 非数据库直接属性
	private Integer blogNum;// 博文数量

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getOrderNo() {
		if (orderNo == null)
			return Integer.MAX_VALUE;// 默认优先级最小
		else
			return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getBlogNum() {
		return blogNum;
	}

	public void setBlogNum(Integer blogNum) {
		this.blogNum = blogNum;
	}
}
