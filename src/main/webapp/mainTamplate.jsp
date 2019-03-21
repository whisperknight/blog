<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="${pageContext.request.contextPath}/static/favicon.ico"
	rel="shortcut icon">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/static/bootstrap3/css/bootstrap.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/static/blog.css">
<script
	src="${pageContext.request.contextPath}/static/jquery/jquery-3.3.1.min.js"></script>
<script
	src="${pageContext.request.contextPath}/static/bootstrap3/js/bootstrap.min.js"></script>
<script type="text/javascript">
	//重写可以传入种子的随机数方法
	Math.random = function(seed) {
		return ('0.' + Math.sin(seed).toString().substr(6));
	}

	$(function() {
		//动态添加不同标签背景色
		$('.label').each(function() {
			//var status = $('.label').index(this) % 6;//按顺序循环加样式
			var tagId = $(this).attr('role');
			var status = Math.floor(Math.random(tagId) * 6); //随机色
			if (status == 0)
				$(this).addClass('label-default');
			else if (status == 1)
				$(this).addClass('label-primary');
			else if (status == 2)
				$(this).addClass('label-success');
			else if (status == 3)
				$(this).addClass('label-info');
			else if (status == 4)
				$(this).addClass('label-warning');
			else if (status == 5)
				$(this).addClass('label-danger');
		});
	});
</script>
<title>骑士博客 | ${pageTitle }</title>
</head>
<body>
	<jsp:include page="/foreground/common/head.jsp"></jsp:include>
	<jsp:include page="/foreground/common/nav.jsp"></jsp:include>
	<div class="container">
		<div class="row">
			<div class="col-md-9">
				<div class="panel panel-default">
					<div class="panel-body">
						<jsp:include page="${mainPage}"></jsp:include>
					</div>
				</div>
			</div>
			<div class="col-md-3">
				<div class="panel panel-default">
					<div class="panel-body">
						<p style="font-size: 16px">
							<span class="glyphicon glyphicon-user" style="margin-right: 10px"></span>博主
						</p>
						<hr>
						<a href="${pageContext.request.contextPath}/blogger/about">
						<div class="text-center" style="margin-bottom: 10px">
							<img src="/blog-data/user/${blogger.imageName}"
								style="max-width: 200px; max-height: 200px;">
						</div>
						<div style="margin-left: 26px">
							<p>
								<span>博主：</span>${blogger.nickName}</p>
							<p>
								<span>签名：</span>${blogger.sign}</p>
						</div></a>
					</div>
				</div>
				<div class="panel panel-default">
					<div class="panel-body">
						<p style="font-size: 16px">
							<span class="glyphicon glyphicon-fire" style="margin-right: 10px"></span>热门博文
						</p>
						<hr>
						<c:forEach items="${hotBlogList }" var="blog">
							<a href="${pageContext.request.contextPath}/blog/${blog.id}" title="${blog.title}"
								style="display: block; overflow: hidden; height: 22px">${blog.title}</a>
						</c:forEach>
					</div>
				</div>
				<div class="panel panel-default">
					<div class="panel-body">
						<p style="font-size: 16px">
							<span class="glyphicon glyphicon-tag" style="margin-right: 10px"></span>所有标签
						</p>
						<hr>
						<c:forEach items="${tagList }" var="tag">
							<a
								href="${pageContext.request.contextPath}/index?tagId=${tag.id}"
								style="float: left; margin: 2px"><span class="label"
								role="${tag.id }">${tag.name }（${tag.blogNum }）</span></a>
						</c:forEach>
					</div>
				</div>
				<div class="panel panel-default">
					<div class="panel-body">
						<p style="font-size: 16px">
							<span class="glyphicon glyphicon-calendar"
								style="margin-right: 10px"></span>博文日期
						</p>
						<hr>
						<c:forEach items="${blogReleaseDateList }" var="blogReleaseDate">
							<div style="margin-bottom: 4px">
								<a
									href="${pageContext.request.contextPath}/index?releaseDateStr=${blogReleaseDate.releaseDateStr}">${blogReleaseDate.releaseDateStr }<span
									class="badge" style="margin-left: 10px">${blogReleaseDate.blogNum }</span></a>
							</div>
						</c:forEach>
					</div>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="/foreground/common/foot.jsp"></jsp:include>
</body>
</html>