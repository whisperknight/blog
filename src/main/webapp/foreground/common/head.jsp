<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="b-head">
	<div class="b-head-contain" id="head-contain">
		<div class="text-center" style="width: 35px; display: inline-block;">
			<img src="${pageContext.request.contextPath}/static/image/shield.png"
				style="width: 35px; height: 50px; padding-bottom: 12px">
		</div>
		<h1 style="display: inline-block;">
			<b><a href="${pageContext.request.contextPath}"
				style="color: white; text-decoration: none"><span
					style="margin: 0px 10px 0px 10px">骑士博客</span></a></b>
		</h1>
		<div class="text-center" style="width: 35px; display: inline-block;">
			<img src="${pageContext.request.contextPath}/static/image/shield.png"
				style="width: 35px; height: 50px; padding-bottom: 12px">
		</div>
		<script type="text/javascript">
			setInterval(function() {
				$('#head-contain img').animate({
					width : "0px"
				}, 500, 'swing').animate({
					width : "35px"
				}, 500, 'swing');
			}, 1500);
		</script>
		<div class="b-head-line"></div>
		<h3>轮子诚可贵，分享价更高</h3>
	</div>
</div>


