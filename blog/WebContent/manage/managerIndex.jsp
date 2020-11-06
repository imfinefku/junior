<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.base.init.Constant"%>
<%@ page import="com.base.bean.LoginInfo"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	LoginInfo account = (LoginInfo)request.getSession().getAttribute("account");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="pragma" content="no-cache">
<title>后台</title>
<base href="<%=basePath%>" />
<script src="common/jquery-1.10.1.min.js"></script>
<link rel="stylesheet" href="lib/layui/css/layui.css">
<link rel="icon" href="images/icon.png" type="img/x-ico" />
<script src="lib/layui/layui.js"></script>
<script src="js/managerIndex.js"></script>
</head>
<body style="min-width: 1200px;">
	<div class="layui-layout layui-layout-admin">
		<div class="layui-header">
			<div onclick='tz("manage/shouye.jsp")' class="layui-logo"
				style="color: white; font-size: 20px; cursor: pointer;"><%=Constant.name%></div>
			<!-- 头部区域（可配合layui已有的水平导航） -->

			<ul class="layui-nav layui-layout-right">
				<li class="layui-nav-item"><a href="javascript:;"> <img
						src="images/person.png" class="layui-nav-img"><%=account.getName()%>
				</a>
					<dl class="layui-nav-child">
						<dd>
							<a href='javascript:tz("manage/xgmm.jsp")'>修改密码</a>
						</dd>
						<dd>
							<a href="javascript:logout();">退出</a>
						</dd>
					</dl></li>
			</ul>
		</div>
		<div class="layui-side layui-bg-black">
			<div class="layui-side-scroll">
				<!-- 左侧导航区域（可配合layui已有的垂直导航） -->
				<ul class="layui-nav layui-nav-tree">
					<li class="layui-nav-item"><a
						href='javascript:tz("manage/shouye.jsp")'>首页</a></li>
					<li class="layui-nav-item layui-nav-itemed"><a class=""
						href="javascript:;">博客管理</a>
						<dl class="layui-nav-child">
							<dd>
								<a href='javascript:tz("manage/btgl.jsp")'>标题管理</a>
							</dd>
							<dd>
								<a href='javascript:tz("manage/bqgl.jsp")'>标签管理</a>
							</dd>
							<dd>
								<a href='javascript:tz("manage/wzgl.jsp")'>文章管理</a>
							</dd>
							<dd>
								<a href='javascript:tz("manage/lygl.jsp")'>留言管理</a>
							</dd>
							<dd>
								<a href='javascript:tz("manage/ylgl.jsp")'>友链管理</a>
							</dd>
						</dl></li>
					<li class="layui-nav-item"><a href="javascript:;">设置</a>
						<dl class="layui-nav-child">
							<dd>
								<a href='javascript:tz("manage/xgmm.jsp")'>修改密码</a>
							</dd>
							<dd>
								<a href='javascript:tz("manage/dtgl.jsp")'>数据库备份</a>
							</dd>
						</dl></li>
				</ul>
			</div>
		</div>
		<div class="layui-body">
			<iframe id="frame" src="manage/shouye.jsp"
				style="width: 100%; height: 99%;"> </iframe>
		</div>
	</div>
</body>
</html>