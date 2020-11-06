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
<title>文章管理</title>
<base href="<%=basePath%>" />
<script src="common/jquery-1.10.1.min.js"></script>
<link rel="stylesheet" href="lib/layui/css/layui.css">
<script src="lib/layui/layui.js"></script>
<script src="js/wzgl.js"></script>
<script src="common/commonUtil.js"></script>
</head>
<body style="min-width: 1000px; background: #F2F2F2;">
	<div style="padding: 20px;">
		<div class="layui-row">
			<div class="layui-col-md12">
				<div class="layui-card">
					<div class="layui-card-header">文章管理</div>
					<div class="layui-card-body">
						<table class="layui-hide" id="textGrid" lay-filter="textGrid"></table>
						<script type="text/html" id="toolbarDemo">
            				<div class="layui-btn-container">
                				<button class="layui-btn layui-btn-sm" lay-event="insert">写文章</button>
            				</div>
        				</script>
						<script type="text/html" id="barDemo">
            				<a class="layui-btn layui-btn-xs" lay-event="update">编辑</a>
            				<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="delete">删除</a>
        				</script>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>