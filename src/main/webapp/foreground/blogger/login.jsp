<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${pageContext.request.contextPath}/static/favicon.ico"
	rel="shortcut icon">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/static/bootstrap3/css/bootstrap.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/static/blog.css">
<script
	src="${pageContext.request.contextPath}/static/jquery/jquery-3.3.1.min.js"></script>
<script
	src="${pageContext.request.contextPath}/static/bootstrap3/js/bootstrap.min.js"></script>
<script type="text/javascript">
	function checkForm() {
		if ($.trim($('#userName').val()) == "") {
			$('#error').html('用户名为空！');
			return false;
		}

		if ($.trim($('#password').val()) == "") {
			$('#error').html('密码为空！');
			return false;
		}
		return true;
	}
</script>
<title>骑士博客 | 后台登录系统</title>
</head>
<body style="background: url('${pageContext.request.contextPath}/static/image/background.png') no-repeat center center;">
	<div class="container" style="margin-top: 12%">
		<div class="row">
			<div class="col-md-6 col-md-offset-3">
				<div class="panel panel-default" style="background-color: rgba(226, 226, 226, 0.7)">
					<div class="panel-body">
						<h3>
							<b>后台管理系统</b>
						</h3>
						<hr>
						<form action="${pageContext.request.contextPath}/blogger/loginOn"
							method="post" onsubmit="return checkForm()"
							style="margin-bottom: 30px;" class="form-horizontal">
							<div class="form-group">
								<label class="col-sm-2 control-label">用户名：</label>
								<div class="col-sm-10">
									<input type="text" class="form-control" name="userName"
										id="userName" value="${blogger.userName }"
										placeholder="请输入用户名">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">密码：</label>
								<div class="col-sm-10">
									<input type="password" class="form-control" name="password"
										id="password" value="${blogger.password }" placeholder="请输入密码">
								</div>
							</div>
							<div class="row">
								<div class="col-sm-offset-2 col-sm-8">
									<span id="error" class="help-block" style="color: #a94442">${errorMsg }</span>
								</div>
								<div class="col-sm-2 text-right">
									<button type="submit" class="btn btn-primary">登录</button>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>