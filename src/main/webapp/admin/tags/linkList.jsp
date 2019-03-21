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

		//对话框关闭则自动重置内部内容
		$('#dlg').dialog({
			onClose : function() {
				$("#fm").form('reset');
			}
		});
	});

	//查询
	function searchLink() {
		$('#dg').datagrid('load', {
			'name' : $('#s_linkName').val()
		});
	}

	//批量删除外链
	function deleteLinks() {
		var selectedRows = $("#dg").datagrid('getSelections');
		if (selectedRows.length == 0) {
			$.messager.alert('系统提示', '请选择要删除的外链！');
			return;
		}
		var linkIds = [];
		for (var i = 0; i < selectedRows.length; i++) {
			linkIds.push(selectedRows[i].id);
		}
		var strIds = linkIds.join(",");
		$.messager
				.confirm(
						"系统提示",
						"确认要删除这<font color=red>"
								+ selectedRows.length
								+ "</font>个外链吗？",
						function(r) {
							if (r) {
								$
										.post(
												"${pageContext.request.contextPath}/admin/link/delete",
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

	//打开添加外链对话框
	function openAddLinkDialog() {
		$("#dlg").dialog("open").dialog("setTitle", "添加外链");
	}

	//打开修改外链对话框
	function openModifyLinkDialog() {
		var selectedRows = $("#dg").datagrid('getSelections');
		if (selectedRows.length > 1) {
			$.messager.alert('系统提示', '不能批量修改！');
			return;
		}
		if (selectedRows.length == 0) {
			$.messager.alert('系统提示', '请选择要修改的外链！');
			return;
		}
		var row = selectedRows[0];
		$("#dlg").dialog("open").dialog("setTitle", "修改外链");
		$("#fm").form("load",row);
	}

	//保存外链
	function saveLink() {
		$("#fm").form("submit", {
			url : '${pageContext.request.contextPath}/admin/link/save',
			onSubmit : function() {
				return $(this).form("validate");
			},
			success : function(result) {
				result = eval('('+result+')');
				if (result.success) {
					closeLinkDialog();
					$("#dg").datagrid("reload");
				} else
					$.messager.alert("系统提示", "保存失败！");
			}
		});
	}

	//关闭对话框
	function closeLinkDialog() {
		$("#dlg").dialog("close");
	}
</script>
<style type="text/css">
.lines-no td {
	border-right: 1px solid #ebebeb;
	border-bottom: 1px solid #ebebeb;
}
</style>
<table id="dg" class="easyui-datagrid"
	data-options="url:'${pageContext.request.contextPath}/admin/link/list',pagination:true,pageSize:20,rownumbers:true,fitColumns:true,fit:true,border:false,toolbar:'#tb'">
	<thead>
		<tr>
			<th data-options="field:'cb',checkbox:true"></th>
			<th data-options="field:'id'">ID</th>
			<th data-options="field:'name'" width="100px">外链名</th>
			<th data-options="field:'url'" width="200px">网址</th>
			<th data-options="field:'orderNo'" width="100px">优先级(数字越小优先级越高)</th>
		</tr>
	</thead>
</table>
<!-- 工具条 -->
<div id="tb">
	<a href="javascript:openAddLinkDialog()" class="easyui-linkbutton"
		data-options="iconCls:'icon-add',plain:true">添加</a> <a
		href="javascript:openModifyLinkDialog()" class="easyui-linkbutton"
		data-options="iconCls:'icon-edit',plain:true">修改</a> <a
		href="javascript:deleteLinks()" class="easyui-linkbutton"
		data-options="iconCls:'icon-cancel',plain:true">删除</a><span
		style="margin-right: 20px"></span><input type="text"
		class="easyui-searchbox" id="s_linkName"
		data-options="prompt:'搜索外链',searcher:searchLink" />
</div>
<!-- 对话框 -->
<div id="dlg" class="easyui-dialog"
	style="width: 500px; height: 300px; padding: 20px 20px"
	data-options="closed:true,buttons:'#dlg-buttons'">
	<form id="fm" method="post">
		<table>
			<tr style="display: none;">
				<td><input type="text" id="id" name="id" /></td>
			</tr>
			<tr>
				<td>外链名：</td>
				<td><input type="text" id="name" name="name"
					class="easyui-textbox" data-options="validType:'length[1,20]'"
					style="width: 200px" required="required" /></td>
			</tr>
			<tr>
				<td>网址：</td>
				<td><input type="text" id="url" name="url"
					class="easyui-textbox" data-options="validType:'url'"
					style="width: 300px" required="required" /></td>
			</tr>
			<tr>
				<td>优先级：</td>
				<td><input type="text" id="orderNo" name="orderNo"
					class="easyui-numberbox"
					data-options="min:-2147483648,max:2147483647,precision:0"
					style="width: 200px"/></td>
			</tr>
		</table>
	</form>
	<div id="dlg-buttons">
		<a href="javascript:saveLink()" class="easyui-linkbutton"
			data-options="iconCls:'icon-ok'">保存</a> <a
			href="javascript:closeLinkDialog()" class="easyui-linkbutton"
			data-options="iconCls:'icon-cancel'">关闭</a>
	</div>
</div>