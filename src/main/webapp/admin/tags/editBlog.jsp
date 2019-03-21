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
			return '${pageContext.request.contextPath}/admin/blog/upload';
		} else {
			return this._bkGetActionUrl.call(this, action);
		}
	}
	
	$(function(){
		//若url里存在id表明是修改博客，则通过ajax赋初值
		if(${not empty param.id}){//不通过隐藏input,而通过直接从url里取修改博客的ID
			$.post('${pageContext.request.contextPath}/admin/blog/info',{id:'${param.id}'},function(data){
				$('#title').textbox('setValue',data.title);
				for(var i=0;i<data.tags.length;i++)
					moveToSelectedTag($('#optionalTag [role=' + data.tags[i].id + ']'));
				ue.ready(function() {
					ue.setContent(data.content);
				});
			},'json');
		}
	});
</script>
<script type="text/javascript">
	//发布博客
	function publish() {
		if (!$('#fm').form("validate"))
			return;

		var content = ue.getContent();
		if (content == null || $.trim(content) == '') {
			$.messager.alert('系统提示', '内容不能为空！');
			return;
		}
		
		var contentTxt = ue.getContentTxt();
		var summary;
		if (contentTxt.length > 155)
			summary = ue.getContentTxt().substr(0, 155)
					+ '...';
		else
			summary = ue.getContentTxt().substr(0, 155);

		var form = document.querySelector("#fm");
		var formData = new FormData(form);
		if(${not empty param.id})//不通过隐藏input,而直接从url里取博客ID
			formData.append("id", '${param.id}');
		formData.append("content", content);
		formData.append("summary", summary);
		formData.append("contentWithNoHTMLTag", contentTxt);

		$.ajax({
			async:false,
			url : "${pageContext.request.contextPath}/admin/blog/save",
			type : "POST",
			data : formData,
			dataType : "json",
			cache : false,//上传文件无需缓存
			processData : false,//用于对data参数进行序列化处理 这里必须false
			contentType : false, //必须
			success : function(data) {
				if (data.addSuccess) {
					$.messager.alert('系统提示', '发布成功！', null, function() {
						location.reload();
					});
				} else if(data.updateSuccess){
					$.messager.alert('系统提示', '更新成功！', null, function() {
						location.reload();
					});
				}else {
					$.messager.alert('系统提示', '发布失败！');
				}
			}
		});
	}

	//点击+号添加
	function toggleInput() {
		$('#add-button').toggle();
		$('#add-input').toggle();
		$("#editingName").textbox("setValue", '');
	}

	//添加一个新标签
	function addTag() {
		//获取内容
		var name = $('#editingName').val();
		//验证内容
		if (name == null || $.trim(name) == '') {
			$.messager.alert('系统提示', '不能为空！');
			return;
		}

		//验证是否已存在
		var existed = false;
		$('#selectedTag :button').each(function() {
			if ($(this).text() == $.trim(name) && $(this).is(':visible')) {
				$.messager.alert('系统提示', '已存在！');
				existed = true;
				return false;
			}
		});
		if (!existed)
			$('#optionalTag :button').each(function() {
				if ($(this).text() == $.trim(name) && $(this).is(':visible')) {
					$(this).click();
					toggleInput();
					existed = true;
					return false;
				}
			});

		if (!existed) {
			$
					.post(
							'${pageContext.request.contextPath}/admin/tag/save',
							{
								name : name
							},
							function(data) {
								if (data.success) {
									$('#optionalTag')
											.append(
													'<button class="btn-tag" type="button" onclick="moveToSelectedTag(this)" style="margin: 2px" role="'
															+ data.tagId
															+ '">'
															+ name
															+ '</button>');
									$('#optionalTag [role=' + data.tagId + ']')
											.click();
									toggleInput();
								} else
									$.messager.alert('系统提示', '添加失败！');
							}, 'json');
		}
	}

	//移到已选标签
	function moveToSelectedTag(btn) {
		$(btn).toggle();
		var selectedTag = '<button class="btn-tag" type="button" onclick="moveToOptionalTag(this)" style="margin: 2px" role="'
				+ $(btn).attr('role')
				+ '">'
				+ $(btn).html()
				+ '</button>'
				+ '<input style="display: none;" name="tagId" value="'
				+ $(btn).attr('role') + '">';
		$('#selectedTag').prepend(selectedTag);
	}

	//移回到可选标签
	function moveToOptionalTag(btn) {
		$(btn).next().remove();
		$(btn).remove();
		$('#optionalTag [role=' + $(btn).attr('role') + ']').toggle();
	}
</script>
</head>
<body>
	<form id="fm" method="post">
		<table>
			<tr>
				<td style="width: 50px" valign="top">标题：</td>
				<td><input class="easyui-textbox" id="title" name="title"
					data-options="validType:'length[2,50]'" required="required"
					style="width: 500px"></td>
			</tr>
			<tr>
				<td valign="top" style="padding-top: 10px">标签：</td>
				<td style="padding-top: 10px">
					<div>
						<div id="selectedTag" style="display: inline;"></div>
						<button class="btn-add" id="add-button" type="button"
							onclick="toggleInput()">+</button>
						<div style="display: none;" id="add-input">
							<input class="easyui-textbox" id="editingName"
								style="width: 200px">
							<button class="btn-add" type="button" onclick="addTag()">确定</button>
							<button class="btn-add" type="button" onclick="toggleInput()">取消</button>
						</div>
					</div>
					<div
						style="font-size: 10px; color: gray; background-color: #f3f3f3; border-radius: 5px; margin: 10px 0px">
						<div style="padding: 5px">可选标签</div>
						<div id="optionalTag" style="padding: 5px">
							<c:forEach items="${tagList }" var="tag">
								<button class="btn-tag" type="button"
									onclick="moveToSelectedTag(this)" style="margin: 2px"
									role="${tag.id }">${tag.name }</button>
							</c:forEach>
						</div>
					</div>
				</td>
			</tr>
			<tr>
				<td valign="top">内容：</td>
				<td><script id="editor" type="text/plain"
						style="width: 900px; height: 500px;"></script></td>
			</tr>
			<tr>
				<td></td>
				<td style="text-align: center"><a href="javascript:;"
					onclick="publish()" class="easyui-linkbutton">编辑完成并发布</a></td>
			</tr>
		</table>
	</form>
</body>
</html>