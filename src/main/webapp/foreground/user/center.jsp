<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<p style="font-size: 16px">
	<span class="glyphicon glyphicon-info-sign" style="margin-right: 10px"></span>用户中心
</p>
<hr>
<div class="m-register col-sm-8 col-sm-offset-2">
	<form class="form-horizontal" id="from">
		<div class="form-group">
			<label class="col-sm-2 control-label">用户名：</label>
			<div class="col-sm-10"><div style="padding-top: 7px">${currentUser.userName }</div></div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label">密码：</label>
			<div class="col-sm-10">
				<input type="password" class="form-control" name="password"
					id="password" onblur="checkPassword()" placeholder="不修改密码则不填">
				<span id="password-error" class="help-block" style="color: red"></span>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label">确认密码：</label>
			<div class="col-sm-10">
				<input type="password" class="form-control" id="rePassword"
					onblur="checkRePassword()"> <span id="rePassword-error"
					class="help-block" style="color: red"></span>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label">昵称：</label>
			<div class="col-sm-10">
				<input type="text" class="form-control" name="nickName"
					id="nickName" onblur="checkNickName()"
					value="${currentUser.nickName }"> <span id="nickName-error"
					class="help-block" style="color: red"></span>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label">头像：</label>
			<div class="col-sm-10">
				<div class="row">
					<div class="col-sm-2">
						<div class="input-group">
							<a class="btn btn-primary" id="upload-button" type="button">选择图像</a>
							<input style="display: none;" type="file" id="file" name="file"
								accept="image/gif,image/jpeg,image/jpg,image/png">
						</div>
					</div>
					<div class="col-sm-6">
						<div style="height: 300px; width: 300px">
							<img id="userImage"
								src="/blog-data/user/${currentUser.imageName }"
								style="max-width: 100%">
						</div>
					</div>
					<div class="col-sm-4">
						<div class="preview"
							style="width: 140px; height: 140px; overflow: hidden;"></div>
					</div>
				</div>
			</div>
		</div>
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<button type="button" class="btn btn-primary" id="modify">确定修改</button>
			</div>
		</div>
	</form>
</div>
<!-- Small modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog">
	<div class="modal-dialog modal-sm" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title">信息</h4>
			</div>
			<div class="modal-body"></div>
		</div>
	</div>
</div>
<script type="text/javascript">
	$(function() {
		//cropper头像上传插件
		var image = document.getElementById('userImage');
		var cropper = new Cropper(image, {
			viewMode : 1,
			dragMode : 'move',
			aspectRatio : 1,
			autoCropArea : 1,
			preview : ".preview",
			minCropBoxWidth : 100,
			minCropBoxHeight : 100,
		//crop : function(event) {
		//	console.log(event.detail.x);
		//	console.log(event.detail.y);
		//	console.log(event.detail.width);
		//	console.log(event.detail.height);
		//	console.log(event.detail.rotate);
		//	console.log(event.detail.scaleX);
		//	console.log(event.detail.scaleY);
		//}
		});

		//本地上传图片并显示
		$("#upload-button").click(function() {
			$("#file").click().change(function() {
				if (checkImageFile()) {
					var $file = $(this);
					var fileObj = $file[0];
					var windowURL = window.URL || window.webkitURL;
					var dataURL = null;
					if (!fileObj || !fileObj.files || !fileObj.files[0])
						return;
					dataURL = windowURL.createObjectURL(fileObj.files[0]);
					cropper.replace(dataURL);
				}
			});
		});

		//修改
		$('#modify').on('click', function modify() {
			if (!checkForm())
				return;

			cropper.getCroppedCanvas().toBlob(function(blob) {
				var form = document.querySelector("#from");
				var formData = new FormData(form);
				if (postFile != null)
					formData.append('croppedImage', blob, postFile.name);

				//ajax上传头像图片
				$.ajax({
					async : false,
					url : "modifyOn",
					data : formData,
					type : 'post',
					//dataType : "json",
					cache : false,//上传文件无需缓存
					processData : false,//用于对data参数进行序列化处理 这里必须false
					contentType : false, //必须
					success : function(result) {
						if (result == "success")
							window.location.reload();
						else {
							$('.modal-body').html('修改失败！');
							$('#myModal').modal('show');
						}
					}
				});
			});
		});

	});

	var postFile;//防止重复提交图片

	//单独验证图片文件
	function checkImageFile() {
		var file = $(':file')[0].files[0];
		if (file == null)
			return false;//为空不异步提交
		if (file != postFile)
			postFile = file;
		else
			return false;//防止重复提交图片

		var size = file.size;
		var type = file.type;
		if (size >= 2097152) {
			$('.modal-body').html('上传失败！文件必须小于2MB！');
			$('#myModal').modal('show');
			return false;
		}
		if (type != "image/png" && type != "image/jpeg" && type != "image/gif") {
			$('.modal-body').html('上传失败！只能上传png、jpg、gif图像文件！');
			$('#myModal').modal('show');
			return false;
		}
		return true;
	}

	var reg_en = /^[a-zA-Z0-9\_]+$/;//英文正则
	var reg_en_cn = /^[a-zA-Z0-9\u4E00-\u9FA5\_]+$/;//中文正则

	//表单验证
	function checkPassword() {
		if ($('#password').val() == ""){
			$('#password-error').html('');
			return true;//为空表示不修改密码
		}
		if (!reg_en.test($('#password').val())) {
			$('#password-error').html('密码只能为英文字母、数字和下划线_！');
			return false;
		} else {
			$('#password-error').html('');
			return true;
		}
	}

	function checkRePassword() {
		if ($('#password').val() != $('#rePassword').val()) {
			$('#rePassword-error').html('密码不一致！');
			return false;
		} else {
			$('#rePassword-error').html('');
			return true;
		}
	}

	function checkNickName() {
		if ($('#nickName').val() == "")
			return true;
		else if (!reg_en_cn.test($('#nickName').val())) {
			$('#nickName-error').html('昵称只能为英文字母、数字、中文和下划线_！');
			return false;
		} else {
			$('#nickName-error').html('');
			return true;
		}
	}

	function checkForm() {
		if (checkPassword() && checkRePassword() && checkNickName())
			return true;
		return false;
	}
</script>