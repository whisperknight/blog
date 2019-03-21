package com.mycompany.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Blog implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String title;
	private String summary;//摘要
	private String content;
	private Date releaseDate;
	private Integer clickNum;
	private Integer replyNum;
	
	//非数据库直接属性
	private String contentWithNoHTMLTag;//全部纯文本，用于lucene索引
	private String releaseDateStr;//发布日期字符串
	private Integer blogNum;//博文数量
	private List<Tag> tags;//所属标签
	private List<String> thumbnailList = new LinkedList<>();//从content里取到的缩略图

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public Integer getClickNum() {
		return clickNum;
	}

	public void setClickNum(Integer clickNum) {
		this.clickNum = clickNum;
	}

	public Integer getReplyNum() {
		return replyNum;
	}

	public void setReplyNum(Integer replyNum) {
		this.replyNum = replyNum;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public Integer getBlogNum() {
		return blogNum;
	}

	public void setBlogNum(Integer blogNum) {
		this.blogNum = blogNum;
	}

	public String getReleaseDateStr() {
		return releaseDateStr;
	}

	public void setReleaseDateStr(String releaseDateStr) {
		this.releaseDateStr = releaseDateStr;
	}

	public List<String> getThumbnailList() {
		return thumbnailList;
	}

	public void setThumbnailList(List<String> thumbnailList) {
		this.thumbnailList = thumbnailList;
	}

	public String getContentWithNoHTMLTag() {
		return contentWithNoHTMLTag;
	}

	public void setContentWithNoHTMLTag(String contentWithNoHTMLTag) {
		this.contentWithNoHTMLTag = contentWithNoHTMLTag;
	}

}
