<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<p style="font-size: 16px">
	<span class="glyphicon glyphicon-list" style="margin-right: 10px"></span>搜索结果（为您搜索到共
	${blogListSize} 条记录）
</p>
<hr>
<c:choose>
	<c:when test="${blogList.size()>0 }">
		<c:forEach items="${blogList }" var="blog">
			<div>
				<a href="${pageContext.request.contextPath}/blog/${blog.id}"
					style="font-size: 16px">${blog.title }</a>
				<div style="font-weight: normal">${blog.content }</div>
				<p style="font-weight: normal">
					<a style="color: green"
						href="${pageContext.request.contextPath}/blog/${blog.id}">http://localhost:8080/Blog/blog/${blog.id}</a><span
						style="margin-left: 10px">${blog.releaseDateStr }</span>
				</p>
				<hr>
			</div>
		</c:forEach>
	</c:when>
	<c:otherwise>
		<div class="error-body">
			<h1 style="margin-left: 250px">
				<b>对不起，搜索结果为空...</b>
			</h1>
		</div>
	</c:otherwise>
</c:choose>
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