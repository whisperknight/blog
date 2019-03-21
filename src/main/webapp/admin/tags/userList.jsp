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
	function searchUser() {
		$('#dg').datagrid('load', {
			'userName' : $('#s_userName').val()
		});
	}

	//批量删除用户
	function deleteUsers() {
		var selectedRows = $("#dg").datagrid('getSelections');
		if (selectedRows.length == 0) {
			$.messager.alert('系统提示', '请选择要删除的用户！');
			return;
		}
		var userIds = [];
		for (var i = 0; i < selectedRows.length; i++) {
			userIds.push(selectedRows[i].id);
		}
		var strIds = userIds.join(",");
		$.messager
				.confirm(
						"系统提示",
						"确认要删除这<font color=red>"
								+ selectedRows.length
								+ "</font>位用户吗？<br><font color=red>注意：该用户的所有评论也会一起删除！</font>",
						function(r) {
							if (r) {
								$
										.post(
												"${pageContext.request.contextPath}/admin/user/delete",
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

	//打开添加用户对话框
	function openAddUserDialog() {
		$("#dlg").dialog("open").dialog("setTitle", "添加用户");
		//用户名关闭只读
		$("#userName").textbox("readonly", false);
		//打开用户名验证
		$('#userName').textbox({
			novalidate : false
		});
		//打开密码验证
		$('#password').passwordbox({
			novalidate : false
		});
		//无密码操作提示
		$('#password').passwordbox({
			"prompt" : null
		});
		$('#imageName').textbox("setValue", 'default.jpg');
	}

	//打开修改用户对话框
	function openModifyUserDialog() {
		var selectedRows = $("#dg").datagrid('getSelections');
		if (selectedRows.length > 1) {
			$.messager.alert('系统提示', '不能批量修改！');
			return;
		}
		if (selectedRows.length == 0) {
			$.messager.alert('系统提示', '请选择要修改的用户！');
			return;
		}
		var row = selectedRows[0];
		$("#dlg").dialog("open").dialog("setTitle", "修改用户");
		//$("#fm").form("load", row);
		$("#id").val(row.id);
		$("#userName").textbox("setValue", row.userName);
		//用户名打开只读
		$("#userName").textbox("readonly", true);
		//去除用户名验证
		$('#userName').textbox({
			novalidate : true
		});
		//去除密码验证
		$('#password').passwordbox({
			novalidate : true
		});
		//有密码操作提示
		$('#password').passwordbox({
			"prompt" : '不填则不修改'
		});
		$("#nickName").textbox("setValue", row.nickName);
		$("#imageName").textbox("setValue", row.imageName);
	}

	//保存用户
	function saveUser() {
		if (!$('#fm').form("validate"))
			return;

		var id = $('#id').val();
		var userName = $('#userName').textbox('getValue');
		var password = $('#password').passwordbox('getValue');
		var nickName = $('#nickName').textbox('getValue');
		var imageName = $('#imageName').textbox('getValue');

		var formData = new FormData();
		if (id != null && id != '')
			formData.append("id", id);
		if (id == null || id == '')//无ID表示添加需要用户名，有ID表示修改不需要用户名
			formData.append("userName", userName);
		if (password != null && password != '')
			formData.append("password", password);
		formData.append("nickName", nickName);//不需要验证，不然数据库里会把昵称存为用户名
		if (imageName != null && imageName != '')
		formData.append("imageName", imageName);

		$.ajax({
			async : false,
			url : "${pageContext.request.contextPath}/admin/user/save",
			type : "POST",
			data : formData,
			dataType : "json",
			cache : false,//上传文件无需缓存
			processData : false,//用于对data参数进行序列化处理 这里必须false
			contentType : false, //必须
			success : function(data) {
				if (data.success) {
					closeUserDialog();
					$("#dg").datagrid("reload");
				} else
					$.messager.alert("系统提示", "保存失败！");
			}
		});
	}

	//关闭对话框
	function closeUserDialog() {
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
	data-options="url:'${pageContext.request.contextPath}/admin/user/list',pagination:true,pageSize:20,rownumbers:true,fitColumns:true,fit:true,border:false,toolbar:'#tb'">
	<thead>
		<tr>
			<th data-options="field:'cb',checkbox:true"></th>
			<th data-options="field:'id'">ID</th>
			<th style="width: 100px" data-options="field:'statusStr'">身份</th>
			<th style="width: 100px" data-options="field:'userName'">用户名</th>
			<th style="width: 250px" data-options="field:'password'">密码</th>
			<th style="width: 100px" data-options="field:'nickName'">昵称</th>
			<th style="width: 280px" data-options="field:'imageName'">头像文件</th>
		</tr>
	</thead>
</table>
<!-- 工具条 -->
<div id="tb">
	<a href="javascript:openAddUserDialog()" class="easyui-linkbutton"
		data-options="iconCls:'icon-add',plain:true">添加</a> <a
		href="javascript:openModifyUserDialog()" class="easyui-linkbutton"
		data-options="iconCls:'icon-edit',plain:true">修改</a> <a
		href="javascript:deleteUsers()" class="easyui-linkbutton"
		data-options="iconCls:'icon-cancel',plain:true">删除</a><span
		style="margin-right: 20px"></span><input type="text"
		class="easyui-searchbox" id="s_userName"
		data-options="prompt:'用户名',searcher:searchUser" />
</div>
<!-- 对话框 -->
<div id="dlg" class="easyui-dialog"
	style="width: 750px; height: 300px; padding: 20px 20px"
	data-options="closed:true,buttons:'#dlg-buttons'">
	<form id="fm" method="post">
		<table>
			<tr style="display: none;">
				<td><input type="text" id="id" name="id" /></td>
			</tr>
			<tr>
				<td>身份：</td>
				<td id="statusStr">普通用户</td>
			</tr>
			<tr>
				<td>用户名：</td>
				<td><input type="text" id="userName" name="userName"
					class="easyui-textbox" style="width: 250px"
					data-options="
						required:true,
						validType:{remote:['${pageContext.request.contextPath}/admin/user/existUserWithUserName','userName']},
						invalidMessage:'用户名已存在'
					" /></td>
				<td><div style="padding: 8px"></div></td>
				<td>密码：</td>
				<td><input type="text" id="password" name="password"
					class="easyui-passwordbox" style="width: 250px" required="required" /></td>
			</tr>
			<tr>
				<td>昵称：</td>
				<td><input type="text" id="nickName" name="nickName"
					class="easyui-textbox" style="width: 250px" /></td>
				<td><div style="padding: 8px"></div></td>
				<td>头像文件：</td>
				<td><input type="text" id="imageName" name="imageName"
					class="easyui-textbox" style="width: 250px" /></td>
			</tr>
		</table>
	</form>
	<div id="dlg-buttons">
		<a href="javascript:saveUser()" class="easyui-linkbutton"
			data-options="iconCls:'icon-ok'">保存</a> <a
			href="javascript:closeUserDialog()" class="easyui-linkbutton"
			data-options="iconCls:'icon-cancel'">关闭</a>
	</div>
</div>