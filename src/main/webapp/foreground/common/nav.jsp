<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<nav class="navbar navbar-inverse" style="border-radius: 0px">
	<div class="container-fluid">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#my-navbar-1">
				<span class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="${pageContext.request.contextPath}">首页</a>
		</div>
		<div class="collapse navbar-collapse" id="my-navbar-1">
			<ul class="nav navbar-nav">
				<li><a href="${pageContext.request.contextPath}/blogger/about">关于博主</a></li>
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<li><form
						action="${pageContext.request.contextPath}/blog/search"
						method="get" onsubmit="return checkSearch()"
						class="navbar-form navbar-left">
						<div class="input-group">
							<input type="text" class="form-control" placeholder="全站搜索..."
								name="q" value="${q }" id="q"> <span class="input-group-btn">
								<button class="btn btn-primary" type="submit">搜索</button>
							</span>
						</div>
					</form></li>
				<c:choose>
					<c:when test="${not empty currentUser }">
						<li><div class="round_icon">
								<a href="${pageContext.request.contextPath}/user/center"> <img
									src="/blog-data/user/${empty currentUser?'default.jpg': currentUser.imageName }"
									alt="${currentUser.nickName }"></a>
							</div></li>
						<c:if test="${currentUser.status == -1 }">
							<li><a href="${pageContext.request.contextPath}/admin/main">后台</a></li>
						</c:if>
						<li><a href="${pageContext.request.contextPath}/user/logout">注销</a></li>
					</c:when>
					<c:otherwise>
						<li><a href="${pageContext.request.contextPath}/user/login">登录</a></li>
						<li><a
							href="${pageContext.request.contextPath}/user/register">注册</a></li>
					</c:otherwise>
				</c:choose>
			</ul>
		</div>
	</div>
</nav>
<script type="text/javascript">
	$(function() {
		$('.navbar-nav li').hover(function() {
			$(this).addClass('active');
		}, function() {
			$(this).removeClass('active');
		});
	});

	function checkSearch() {
		var content = $('#q').val();
		if(content==null || $.trim(content) == ""){
			location.reload();
			return false;
		}
	}
</script>