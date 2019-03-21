<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:forEach items="${innerCommentList }" var="innerComment">
	<div class="row">
		<div class="col-md-offset-1 col-md-1">
			<div class="round_icon">
				<img src="/blog-data/user/${innerComment.user.imageName }"
					alt="${innerComment.user.nickName }">
			</div>
		</div>
		<div class="col-md-10">
			<p>
				<b style="margin-right: 10px; color: #FB7A9C">${innerComment.user.statusStr }</b><b
					style="margin-right: 10px"><a href="#">${innerComment.user.nickName }</a></b>
				<c:if test="${not empty innerComment.replyToUser }">
						回复 <a href="#">@${innerComment.replyToUser.nickName }：</a>
				</c:if>
				${innerComment.content }
			</p>
			<p class="help-block">
				<span style="margin-right: 10px">${innerComment.createTimeStr }</span><a
					href="javascript:;"
					onclick="showTextArea(${commentId},${innerComment.user.id },'${innerComment.user.nickName }')">回复</a>
			</p>
		</div>
	</div>
</c:forEach>
<c:if test="${not empty pagination}">
	<div class="col-md-offset-1">
		<c:forEach items="${pagination.items }" var="item">
			<c:choose>
				<c:when test="${empty item.url}">
					<span style="padding: 0px 5px;color: gray">${item.number}</span>
				</c:when>
				<c:when test="${!item.disabled }">
					<a href="javascript:;"
						onclick="loadInnerComment_${commentId }('${item.disabled || item.active ? 'doNothing' : item.url }')"><span
						style="padding: 0px 5px;${item.active?'':'color:gray'}">${item.number}</span></a>
				</c:when>
			</c:choose>
		</c:forEach>
	</div>
</c:if>