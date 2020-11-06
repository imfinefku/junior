<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String id=request.getParameter("id")==null?"":request.getParameter("id").toString();
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="pragma" content="no-cache">
<title></title>
<base href="<%=basePath%>" />
<script src="common/jquery-1.10.1.min.js"></script>
<script src="js/text.js"></script>
<script src="js/commonJs.js"></script>
<script src="common/commonUtil.js"></script>
<script src="common/common.js"></script>
<link rel="stylesheet" href="lib/layui/css/layui.css">
<link rel="stylesheet" href="css/common.css">
<link rel="icon" href="images/icon.png" type="img/x-ico" />
<script src="lib/layui/layui.js"></script>
</head>
<body>
	<jsp:include page="top.jsp" />
	<div class="content">
		<div style="width: 760px; float: left;">
			<div style="width: 100%;background:white;line-height:30px;margin-top:20px;margin-bottom:20px;float:left;" id="newContent">
			</div>
			<div style="width: 100%;background:white;line-height:30px;margin-bottom:20px;float:left;">
			<span style="margin-left:10px;" id="lastText"></span>
			<br><span style="margin-left:10px;" id="nextText"></span>
			</div>
			<div style="width: 100%;float:left;" id="lyContent">
			</div>
			<div id="fy" style="width: 100%;"></div>
			<div style="background:white;float:left;width:100%;margin-bottom:20px;">
				<div style="margin-bottom:20px;margin-top:20px;width:94%;margin-left:3%;float:left;">
    				<label style="float:left;line-height:38px;">名称：</label>
      				<input type="text" class="layui-input" 
      				id="yourname" autocomplete="off" style="width:200px;">
				</div>
				<hr style="background:white;">
				<textarea class="layui-textarea layui-hide policy_content" 
                      name="policy_content" lay-verify="policy_content" id="policy_content">
            	</textarea>
            	<button class="layui-btn" onclick="submitly()">提交</button>
			</div>
		</div>
		<jsp:include page="right.jsp"/>
	</div>
	<jsp:include page="bottom.jsp" />
	<input type="hidden" id="textId" value="<%=id %>"/>
</body>
</html>