<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="pragma" content="no-cache">
<title>脱北者博客</title>
<base href="<%=basePath%>" />
<script src="common/jquery-1.10.1.min.js"></script>
<script src="js/index.js"></script>
<script src="js/commonJs.js"></script>
<script src="common/commonUtil.js"></script>
<script src="common/common.js"></script>
<link rel="stylesheet" href="lib/layui/css/layui.css">
<link rel="stylesheet" href="css/common.css">
<link rel="icon" href="images/icon.png" type="img/x-ico" />
<script src="lib/layui/layui.js"></script>
</head>
<body>
	<jsp:include page="page/top.jsp" />
	<div class="content">
		<div class="layui-carousel" id="lunbo"
			style="width: 760px; height: 300px; float: left; margin-top: 20px;">
			<div carousel-item id="lbContent">
			</div>
		</div>
		<div
			style="width: 380px; height: 300px; float: right; margin-top: 20px;">
			<div style="cursor:pointer;width: 100%; height: 140px;position:relative;" id="top1"></div>
			<div
				style="cursor:pointer;width: 100%; height: 140px; margin-top: 20px;position:relative;" id="top2"></div>
		</div>
		<div style="width: 760px; float: left;">
			<div style="width: 100%;" id="newContent">
			</div>
			<div id="fy" style="width: 100%; margin-top: 10px;"></div>
		</div>
		<jsp:include page="page/right.jsp"/>
	</div>
	<jsp:include page="page/bottom.jsp" />
</body>
</html>
