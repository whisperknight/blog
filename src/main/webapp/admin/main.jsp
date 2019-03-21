<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
<title>骑士博客|后台主界面</title>
</head>
<body>
	<jsp:include page="/foreground/common/head.jsp"></jsp:include>
	<jsp:include page="/foreground/common/nav.jsp"></jsp:include>
	<div class="container">
		<div class="row">
			<jsp:include page="${mainPage}"></jsp:include>
		</div>
	</div>
	<jsp:include page="/foreground/common/foot.jsp"></jsp:include>
</body>
</html>