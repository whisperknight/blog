<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/ueditor/third-party/SyntaxHighlighter/shCore.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/static/ueditor/third-party/SyntaxHighlighter/shCoreDefault.css">
<script type="text/javascript">
	SyntaxHighlighter.all();
</script>
<script>
	window._bd_share_config = {
		"common" : {
			"bdSnsKey" : {},
			"bdText" : "骑士博客的这篇文章写得非常不错，分享给大家一起交流学习！",
			"bdMini" : "1",
			"bdMiniList" : [ "qzone", "tsina", "weixin", "sqq", "tieba",
					"fbook", "twi", "copy" ],
			"bdPic" : "",
			"bdStyle" : "1",
			"bdSize" : "16"
		},
		"slide" : {
			"type" : "slide",
			"bdImg" : "5",
			"bdPos" : "right",
			"bdTop" : "212.5"
		},
		"image" : {
			"viewList" : [ "qzone", "tsina", "weixin", "sqq", "tieba", "fbook",
					"twi" ],
			"viewText" : "分享到：",
			"viewSize" : "16"
		},
		"selectShare" : {
			"bdContainerClass" : null,
			"bdSelectMiniList" : [ "qzone", "tsina", "weixin", "sqq", "tieba",
					"fbook", "twi" ]
		}
	};
	with (document)
		0[(getElementsByTagName('head')[0] || body)
				.appendChild(createElement('script')).src = 'http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion='
				+ ~(-new Date() / 36e5)];
</script>
<script type="text/javascript">
	//异步加载评论区域
	function loadComment(url) {
		if (url == null)
			$.post("../comment/list", {
				blogId : ${blog.id }
			}, function(data) {
				$('#commentDiv').html(data);
			});
		else if(url =='doNothing')
			return;
		else
			$.post(url, {
				blogId : ${blog.id }
			}, function(data) {
				$('#commentDiv').html(data);
			});
	}

	$(function() {

		//若没有上下页时的情况
		if ($('.previous a').html() == '上一页：') {
			$('.previous a').html('上一页：没有了');
			$('.previous a').attr('href', 'javascript:;');
			$('.previous').addClass('disabled');
		}
		if ($('.next a').html() == '下一页：') {
			$('.next a').html('下一页：没有了');
			$('.next a').attr('href', 'javascript:;');
			$('.next').addClass('disabled');
		}
		
		loadComment();
	});
</script>
<div style="height: 22px">
	<div style="font-size: 16px; display: inline">
		<p style="float: left;">
			<span class="glyphicon glyphicon-file" style="margin-right: 10px"></span>详细博文
		</p>
	</div>
	<div class="help-block" style="display: inline">
		<p style="float: right;">
			<span>${blog.clickNum } 阅读</span> <span>${blog.replyNum} 评论</span>
		</p>
	</div>
</div>
<hr>
<div class="row">
	<div class="col-md-3">
		<p class="text-warning" style="margin-bottom: 0px">${blog.releaseDateStr }</p>
	</div>
	<div class="col-md-9">
		<c:forEach items="${blog.tags }" var="tag">
			<a href="${pageContext.request.contextPath}/index?tagId=${tag.id}"
				style="float: right; margin: 2px"><span class="label"
				role="${tag.id }">${tag.name }</span></a>
		</c:forEach>
	</div>
</div>
<p class="text-center" style="font-size: 24px">${blog.title }</p>
<div style="font-weight: normal">${blog.content }</div>
<nav>
	<ul class="pager">
		<li class="previous"><a
			href="${pageContext.request.contextPath}/blog/${previousBlog.id}">上一页：${previousBlog.title }</a></li>
		<li class="next"><a
			href="${pageContext.request.contextPath}/blog/${nextBlog.id}">下一页：${nextBlog.title }</a></li>
	</ul>
</nav>
<hr>
<div id="commentDiv"></div>