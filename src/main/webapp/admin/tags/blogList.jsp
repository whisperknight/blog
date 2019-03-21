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
	function searchBlog() {
		$('#dg').datagrid('load', {
			'title' : $('#s_title').val()
		});
	}
	
	//跳转修改页面
	function editBlog() {
		var selectedRows = $("#dg").datagrid('getSelections');
		if (selectedRows.length != 1) {
			$.messager.alert('系统提示', '请选择一条要修改的博客！');
			return;
		}
		var row = selectedRows[0];
		window.parent.openTab('修改博客(ID:'+row.id+')','/admin/blog/editBlog?id='+row.id);//调用父窗体的方法
	}

	//批量删除
	function deleteBlogs() {
		var selectedRows = $("#dg").datagrid('getSelections');
		if (selectedRows.length == 0) {
			$.messager.alert('系统提示', '请选择要删除的博客！');
			return;
		}
		var blogIds = [];
		for (var i = 0; i < selectedRows.length; i++) {
			blogIds.push(selectedRows[i].id);
		}
		var strIds = blogIds.join(",");
		$.messager.confirm("系统提示", "确认要删除这<font color=red>"
				+ selectedRows.length + "</font>篇博客吗？", function(r) {
			if (r) {
				$.post("${pageContext.request.contextPath}/admin/blog/delete", {
					'strIds' : strIds
				}, function(result) {
					if (result.success) {
						$("#dg").datagrid("reload");
					} else {
						$.messager.alert('系统提示', '删除失败！');
					}
				}, "json");
			}
		});
	}

	//datagrid前端优化数据显示
	function formatterTitle(value, row) {
		return '<a style="text-decoration:none" target="_blank" href="${pageContext.request.contextPath}/blog/'+row.id+'">'+value+'</a>';
	}
</script>
<style type="text/css">
.lines-no td {
	border-right: 1px solid #ebebeb;
	border-bottom: 1px solid #ebebeb;
}
</style>
<table id="dg" class="easyui-datagrid"
	data-options="url:'${pageContext.request.contextPath}/admin/blog/list',pagination:true,rownumbers:true,fitColumns:true,fit:true,border:false,toolbar:'#tb',pageSize:20">
	<thead>
		<tr>
			<th data-options="field:'cb',checkbox:true"></th>
			<th data-options="field:'id'">ID</th>
			<th data-options="field:'releaseDateStr'">发布日期</th>
			<th data-options="field:'title',align:'center',formatter:formatterTitle,width:750">标题</th>
		</tr>
	</thead>
</table>
<!-- 工具条 -->
<div id="tb">
	<a href="javascript:editBlog()" class="easyui-linkbutton"
		data-options="iconCls:'icon-edit',plain:true">修改</a> <a
		href="javascript:deleteBlogs()" class="easyui-linkbutton"
		data-options="iconCls:'icon-cancel',plain:true">删除</a><span
		style="margin-right: 40px"></span><input type="text"
		class="easyui-searchbox" id="s_title"
		data-options="prompt:'搜索标题',searcher:searchBlog" />
</div>
