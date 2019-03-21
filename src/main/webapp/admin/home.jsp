<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/static/easyui/themes/gray/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/static/easyui/themes/icon.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/easyui/jquery.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
	function openTab(title, uri) {
		if ($('#tt').tabs('exists', title)) {
			$('#tt').tabs('select', title);
		} else {
			var content = "<iframe scrolling='auto' frameborder='0' src='${pageContext.request.contextPath}"
					+ uri + "' style='width:100%;height:99%'></iframe>";
			$('#tt').tabs('add', {
				title : title,
				content : content,
				closable : true
			});
		}
	}

	function refreshSystem() {
		$.post('${pageContext.request.contextPath}/admin/refresh', function(result) {
			if (result.success)
				$.messager.alert('系统提示', '刷新成功！');
			else
				$.messager.alert('系统提示', '刷新系统失败！');
		});
	}
</script>
<div style="margin-bottom: 20px">
	<div class="easyui-layout" style="width: auto; height: 700px;">
		<div data-options="region:'west'" style="width: 150px;">

			<div class="easyui-accordion" data-options="fit:true,border:false">
				<div title="博客管理" style="padding: 10px">
					<a href="javascript:openTab('添加博客','/admin/blog/editBlog')"
						style="display: block" class="easyui-linkbutton"
						data-options="plain:true">添加博客</a> <a
						href="javascript:openTab('所有博客','/admin/blog/blogList')"
						style="display: block" class="easyui-linkbutton"
						data-options="plain:true">所有博客</a><a
						href="javascript:openTab('所有标签','/admin/tag/tagList')"
						style="display: block" class="easyui-linkbutton"
						data-options="plain:true">所有标签</a>
				</div>
				<div title="用户管理" style="padding: 10px">
				<a href="javascript:openTab('博主信息','/admin/blogger/editBlogger')"
						style="display: block" class="easyui-linkbutton"
						data-options="plain:true">博主信息</a>
					<a href="javascript:openTab('所有用户','/admin/user/userList')" style="display: block"
						class="easyui-linkbutton" data-options="plain:true">所有用户</a>
				</div>
				<div title="评论管理" style="padding: 10px">
					<a href="javascript:openTab('所有评论','/admin/comment/commentList')"
						style="display: block" class="easyui-linkbutton"
						data-options="plain:true">所有评论</a>
					<a href="javascript:openTab('所有子评论','/admin/comment/innerCommentList')"
						style="display: block" class="easyui-linkbutton"
						data-options="plain:true">所有子评论</a>
				</div>
				<div title="外链管理" style="padding: 10px">
					<a href="javascript:openTab('底部外链','/admin/link/linkList')" style="display: block"
						class="easyui-linkbutton" data-options="plain:true">底部外链</a>
				</div>
				<div title="系统管理" style="padding: 10px">
					<a href="javascript:refreshSystem()" style="display: block"
						class="easyui-linkbutton" data-options="plain:true">刷新application</a>
				</div>
			</div>

		</div>
		<div data-options="region:'center'">
			<div class="easyui-tabs" data-options="fit:true,border:false" id="tt">
				<div title="首页"
					style="padding: 10px; background: url('../static/image/back-main.png') no-repeat right bottom; background-size: 400px">
					<p style="font-size: 18px; text-indent: 2em">
						欢迎<font color="red"> ${currentUser.statusStr }：${currentUser.nickName }
						</font>进入骑士博客网后台管理系统！
					</p>
				</div>
			</div>
		</div>
	</div>
</div>
