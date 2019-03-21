package com.mycompany.entity;

import java.io.Serializable;

public class Link implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String name;
	private String url;
	private Integer orderNo;

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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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

}
