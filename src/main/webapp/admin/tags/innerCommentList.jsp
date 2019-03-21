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
	function searchInnerComment() {
		$('#dg').datagrid('load', {
			'content' : $('#s_content').val()
		});
	}

	//批量删除子评论
	function deleteInnerComments() {
		var selectedRows = $("#dg").datagrid('getSelections');
		if (selectedRows.length == 0) {
			$.messager.alert('系统提示', '请选择要删除的子评论！');
			return;
		}
		var innerCommentIds = [];
		for (var i = 0; i < selectedRows.length; i++) {
			innerCommentIds.push(selectedRows[i].id);
		}
		var strIds = innerCommentIds.join(",");
		$.messager
				.confirm(
						"系统提示",
						"确认要删除这<font color=red>" + selectedRows.length
								+ "</font>个子评论吗？",
						function(r) {
							if (r) {
								$
										.post(
												"${pageContext.request.contextPath}/admin/comment/inner/delete",
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

	//datagrid前端优化数据显示
	function formatterParentCommentContent(value, row) {
		return '<span title="'+row.comment.content+'">'
				+ (row.comment.content.length > 10 ? (row.comment.content
						.substr(0, 10) + "...") : row.comment.content)
				+ '</span>';
	}
	function formatterUserName(value, row) {
		return row.user.userName;
	}
	function formatterNickName(value, row) {
		return row.user.nickName;
	}
	function formatterReplyToUserName(value, row) {
		if (row.replyToUser != null)
			return row.replyToUser.userName;
		else
			return "无";
	}
	function formatterReplyToNickName(value, row) {
		if (row.replyToUser != null)
			return row.replyToUser.nickName;
		else
			return "无";
	}
	function formatterContent(value, row) {
		return '<span title="'+value+'">'
				+ (value.length > 10 ? (value.substr(0, 10) + "...") : value)
				+ '</span>';
	}
</script>
<style type="text/css">
.lines-no td {
	border-right: 1px solid #ebebeb;
	border-bottom: 1px solid #ebebeb;
}
</style>
<table id="dg" class="easyui-datagrid"
	data-options="url:'${pageContext.request.contextPath}/admin/comment/inner/list?commentId=${not empty param.commentId?param.commentId:''}',pagination:true,pageSize:20,rownumbers:true,fitColumns:true,fit:true,border:false,toolbar:'#tb'">
	<thead>
		<tr>
			<th data-options="field:'cb',checkbox:true"></th>
			<th data-options="field:'id'">ID</th>
			<th
				data-options="field:'comment',formatter:formatterParentCommentContent"
				width="100px">父评论内容</th>
			<th data-options="field:'user.userName',formatter:formatterUserName"
				width="50px">评论用户</th>
			<th data-options="field:'user.nickName',formatter:formatterNickName"
				width="70px">评论用户昵称</th>
			<th
				data-options="field:'replyToUser.userName',formatter:formatterReplyToUserName"
				width="50px">被评论用户</th>
			<th
				data-options="field:'replyToUser.nickName',formatter:formatterReplyToNickName"
				width="70px">被评论用户昵称</th>
			<th data-options="field:'createTimeStr'" width="110px">此条子评论时间</th>
			<th data-options="field:'content',formatter:formatterContent"
				width="140px">此条子评论内容</th>
		</tr>
	</thead>
</table>
<!-- 工具条 -->
<div id="tb">
	<a href="javascript:deleteInnerComments()" class="easyui-linkbutton"
		data-options="iconCls:'icon-cancel',plain:true">删除</a><span
		style="margin-right: 20px"></span><input type="text"
		class="easyui-searchbox" id="s_content"
		data-options="prompt:'内容搜索',searcher:searchInnerComment" />
</div>