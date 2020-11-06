<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String textType=request.getParameter("textType")==null?"":request.getParameter("textType").toString();
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="pragma" content="no-cache">
<title>技术专栏-脱北者博客</title>
<base href="<%=basePath%>" />
<script src="common/jquery-1.10.1.min.js"></script>
<script src="js/tey.js"></script>
<script src="js/commonJs.js"></script>
<script src="common/common.js"></script>
<script src="common/commonUtil.js"></script>
<link rel="stylesheet" href="lib/layui/css/layui.css">
<link rel="stylesheet" href="css/common.css">
<link rel="icon" href="images/icon.png" type="img/x-ico" />
<script src="lib/layui/layui.js"></script>
</head>
<body>
	<jsp:include page="top.jsp"/>
	<div class="content">
		<div style="width: 760px; float: left;">
			<div style="width: 100%;" id="newContent">
			</div>
			<div id="fy" style="width: 100%; margin-top: 10px;"></div>
		</div>
		<jsp:include page="right.jsp"/>
	</div>
	<jsp:include page="bottom.jsp"/>
	<input type="hidden" id="textType" value="<%=textType%>"/>
</body>
</html>
