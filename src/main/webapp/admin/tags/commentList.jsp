<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/static/easyui/themes/gray/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/static/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/static/blog-admin.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/easyui/jquery.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
	$(function() {
		//网格线变成实线
		$('#dg').datagrid('getPanel').addClass('lines-no');
	});

	//查询
	function searchComment() {
		$('#dg').datagrid('load', {
			'content' : $('#s_content').val()
		});
	}

	//批量删除评论
	function deleteComments() {
		var selectedRows = $("#dg").datagrid('getSelections');
		if (selectedRows.length == 0) {
			$.messager.alert('系统提示', '请选择要删除的评论！');
			return;
		}
		var commentIds = [];
		for (var i = 0; i < selectedRows.length; i++) {
			commentIds.push(selectedRows[i].id);
		}
		var strIds = commentIds.join(",");
		$.messager
				.confirm(
						"系统提示",
						"确认要删除这<font color=red>"
								+ selectedRows.length
								+ "</font>个评论吗？<br><font color=red>注意：该评论下的所有子评论也会一起删除！</font>",
						function(r) {
							if (r) {
								$
										.post(
												"${pageContext.request.contextPath}/admin/comment/delete",
												{
													'strIds' : strIds
												},
												function(result) {
													if (result.success) {
														$("#dg").datagrid(
																"reload");
													} else {
														$.messager
																.alert('系统提示',
																		'删除失败！');
													}
												}, "json");
							}
						});
	}

	function openInnerCommentList(commentId) {
		window.parent.openTab('子评论列表(ID:' + commentId + ')',
				'/admin/comment/innerCommentList?commentId=' + commentId);//调用父窗体的方法
	}

	//datagrid前端优化数据显示
	function formatterTitle(value, row) {
		return '<a style="text-decoration:none" target="_blank" href="${pageContext.request.contextPath}/blog/'+row.blog.id+'" title="'+row.blog.title+'">'
				+ (row.blog.title.length > 10 ? (row.blog.title.substr(0, 10) + "...")
						: row.blog.title) + '</a>';
	}
	function formatterUserName(value, row) {
		return row.user.userName;
	}
	function formatterNickName(value, row) {
		return row.user.nickName;
	}
	function formatterContent(value, row) {
		return '<span title="'+value+'">'
				+ (value.length > 10 ? (value.substr(0, 10) + "...") : value)
				+ '</span>';
	}
	function formatterInnerCommentCount(value, row) {
		if (value == 0)
			return 0;
		else
			return value+"（<a href='javascript:openInnerCommentList(" + row.id
					+ ")'>查看子评论</a>）";
	}
</script>
<style type="text/css">
.lines-no td {
	border-right: 1px solid #ebebeb;
	border-bottom: 1px solid #ebebeb;
}
</style>
<table id="dg" class="easyui-datagrid"
	data-options="url:'${pageContext.request.contextPath}/admin/comment/list',pagination:true,pageSize:20,rownumbers:true,fitColumns:true,fit:true,border:false,toolbar:'#tb'">
	<thead>
		<tr>
			<th data-options="field:'cb',checkbox:true"></th>
			<th data-options="field:'id'">ID</th>
			<th data-options="field:'blog.title',formatter:formatterTitle"
				width="100px">博客标题</th>
			<th data-options="field:'user.userName',formatter:formatterUserName"
				width="50px">评论用户</th>
			<th data-options="field:'user.nickName',formatter:formatterNickName"
				width="70px">评论用户昵称</th>
			<th data-options="field:'createTimeStr'" width="110px">评论时间</th>
			<th data-options="field:'content',formatter:formatterContent"
				width="140px">评论内容</th>
			<th
				data-options="field:'innerCommentCount',formatter:formatterInnerCommentCount"
				width="80px">子评论数</th>
		</tr>
	</thead>
</table>
<!-- 工具条 -->
<div id="tb">
	<a href="javascript:deleteComments()" class="easyui-linkbutton"
		data-options="iconCls:'icon-cancel',plain:true">删除</a><span
		style="margin-right: 20px"></span><input type="text"
		class="easyui-searchbox" id="s_content"
		data-options="prompt:'内容搜索',searcher:searchComment" />
</div>