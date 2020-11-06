<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.base.init.Constant"%>
<%@ page import="com.base.bean.LoginInfo"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="pragma" content="no-cache">
<title>修改密码</title>
<base href="<%=basePath%>" />
<script src="common/jquery-1.10.1.min.js"></script>
<link rel="stylesheet" href="lib/layui/css/layui.css">
<script src="lib/layui/layui.js"></script>
<script src="js/xgmm.js"></script>
</head>
<body style="min-width: 1000px; background: #f2f2f2;">
	<div style="padding: 20px;">
		<div class="layui-row">
			<div class="layui-col-md12">
				<div class="layui-card">
					<div class="layui-card-header">修改密码</div>
					<div class="layui-card-body">
						<div class="layui-form-item">
							<label class="layui-form-label">当前密码</label>
							<div class="layui-input-inline">
								<input maxlength="50"  id="oldPassword" type="text" class="layui-input">
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">新密码</label>
							<div class="layui-input-inline">
								<input maxlength="50"  id="newPassword" type="password" class="layui-input">
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">确认新密码</label>
							<div class="layui-input-inline">
								<input maxlength="50" id="renewPassword" type="password" class="layui-input">
							</div>
						</div>
						<div class="layui-form-item">
							<div class="layui-input-block">
								<button onclick="updatePassword()" class="layui-btn" style="margin-le">确认修改</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>