<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
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
<script type="text/javascript" charset="utf-8"
	src="${pageContext.request.contextPath}/static/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8"
	src="${pageContext.request.contextPath}/static/ueditor/ueditor.all.min.js">
	
</script>
<script type="text/javascript" charset="utf-8"
	src="${pageContext.request.contextPath}/static/ueditor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript">
	var ue = UE.getEditor('editor');

	//重写上传图片、涂鸦、截图、远程图片、视频、文件的路径，上传到非项目路径
	UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;
	UE.Editor.prototype.getActionUrl = function(action) {
		//判断路径   这里是config.json 中设置执行上传的action名称
		if (action == 'uploadimage' || action == 'uploadscrawl'
				|| action == 'catchimage' || action == 'uploadvideo'
				|| action == 'uploadfile') {
			return '${pageContext.request.contextPath}/admin/blogger/upload';
		} else {
			return this._bkGetActionUrl.call(this, action);
		}
	}
	
	$(function(){
		$('#nickName').textbox('setValue','${blogger.nickName}');
		$('#sign').textbox('setValue','${blogger.sign}');
		$('#imageName').textbox('setValue','${blogger.imageName}');
		ue.ready(function() {
			ue.setContent('${blogger.profile}');
		});
	});
</script>
<script type="text/javascript">
	//更新博主信息
	function updateBlogger() {
		if (!$('#fm').form("validate"))
			return;

		var content = ue.getContent();
		if (content == null || $.trim(content) == '') {
			$.messager.alert('系统提示', '内容不能为空！');
			return;
		}
		
		var form = document.querySelector("#fm");
		var formData = new FormData(form);
		formData.append("profile", content);

		$.ajax({
			async:false,
			url : "${pageContext.request.contextPath}/admin/blogger/update",
			type : "POST",
			data : formData,
			dataType : "json",
			cache : false,//上传文件无需缓存
			processData : false,//用于对data参数进行序列化处理 这里必须false
			contentType : false, //必须
			success : function(data) {
				if (data.success) {
					$.messager.alert('系统提示', '更新成功！', null, function() {
						location.reload();
					});
				} else
					$.messager.alert('系统提示', '更新失败！');
			}
		});
	}
</script>
</head>
<body>
	<form id="fm" method="post">
		<table>
			<tr>
				<td style="width: 150px" valign="top">博主昵称：</td>
				<td><input class="easyui-textbox" id="nickName" name="nickName"
					data-options="validType:'length[1,20]'" required="required"
					style="width: 200px"></td>
			</tr>
			<tr>
				<td style="width: 150px" valign="top">博主签名：</td>
				<td><input class="easyui-textbox" id="sign" name="sign"
					data-options="validType:'length[1,100]'" required="required"
					style="width: 400px"></td>
			</tr>
			<tr>
				<td style="width: 150px" valign="top">头像文件：</td>
				<td><input class="easyui-textbox" id="imageName" name="imageName"
					data-options="validType:'length[1,100]'" required="required"
					style="width: 400px"></td>
			</tr>
			<tr>
				<td style="width: 150px" valign="top">博主简介：</td>
				<td><script id="editor" type="text/plain"
						style="width: 890px; height: 500px;"></script></td>
			</tr>
			<tr>
				<td></td>
				<td style="text-align: center"><a href="javascript:;"
					onclick="updateBlogger()" class="easyui-linkbutton">更新完成</a></td>
			</tr>
		</table>
	</form>
</body>
</html>