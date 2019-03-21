<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<p style="font-size: 16px">
	<span class="glyphicon glyphicon-list" style="margin-right: 10px"></span>最新博客
</p>
<hr>
<c:forEach items="${blogList }" var="blog">
	<p class="text-warning" style="margin-bottom: 0px">${blog.releaseDateStr }</p>
	<a href="${pageContext.request.contextPath}/blog/${blog.id}"><p
			class="text-center" style="font-size: 16px">${blog.title }</p>
		<p class="remove-bootstrap-a-css" style="font-weight: normal">${blog.summary }</p>
		<div class="blogList-thumbnail">
			<c:forEach items="${blog.thumbnailList }" var="thumbnail">
		${thumbnail }
		</c:forEach>
		</div></a>
	<div class="row">
		<div class="col-md-10">
			<c:forEach items="${blog.tags }" var="tag">
				<a href="${pageContext.request.contextPath}/index?tagId=${tag.id}"
					style="float: left; margin: 2px"><span class="label"
					role="${tag.id }">${tag.name }</span></a>
			</c:forEach>
		</div>
		<div class="col-md-2 text-right help-block">
			<span>${blog.clickNum } 阅读</span> <span>${blog.replyNum} 评论</span>
		</div>
	</div>
	<hr>
</c:forEach>
<c:if test="${not empty pagination}">
	<div class="row text-center">
		<ul class="pagination">
			<c:forEach items="${pagination.items }" var="item">
				<li
					class="${item.disabled?'disabled':'' } ${item.active?'active':''}"><a
					href="${item.disabled || item.active ? 'javascript:;' : item.url }"><span>${item.number }</span></a></li>
			</c:forEach>
		</ul>
	</div>
</c:if>