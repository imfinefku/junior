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
<title>关于-脱北者博客</title>
<base href="<%=basePath%>" />
<script src="common/jquery-1.10.1.min.js"></script>
<script src="js/abt.js"></script>
<script src="js/commonJs.js"></script>
<script src="common/commonUtil.js"></script>
<script src="common/common.js"></script>
<link rel="stylesheet" href="lib/layui/css/layui.css">
<link rel="stylesheet" href="css/common.css">
<link rel="icon" href="images/icon.png" type="img/x-ico" />
<script src="lib/layui/layui.js"></script>
</head>
<body>
	<jsp:include page="top.jsp"/>
	<div class="content">
		<div style="width: 760px; float: left;">
			<div style="width: 100%;background:white;margin-top:20px;" id="newContent">
				<div style="font-size:17px;margin-left:10px;font-weight: bold;line-height:50px;">关于博客</div>
				<div style="margin-left:10px;margin-right:10px;line-height:30px;text-indent:2em;">
				开发这个博客是因为毕业工作一年多之后发现每天都在重复着没有技术含量的工作，以至于从零搭一套框架都不会，于是就打算自己从搭框架开始
				做点什么。刚开始想了半天不知道要做个什么类型的网站，偶然的一次机会在百度上搜到了杨青站长的博客。当时我眼前一亮，个人博客既简单又
				可以记录平时学习的东西，对我来说最合适不过了，于是这个博客就诞生了。<br>
				<p>至于为什么叫脱北者是因为开发博客的这个想法是在我第一份工作辞职的时候诞生的，
				当时我认为我的处境和朝鲜的脱北者一样，疯狂的想离开当时的公司，而且第一家
				公司也带个北字，因此就叫脱北者。</p>
				</div>
				<div style="font-size:17px;margin-left:10px;font-weight: bold;line-height:50px;">关于我</div>
				<div style="margin-left:10px;margin-right:10px;line-height:30px;text-indent:2em;">
				95年出生，17年毕业于山东科技大学，现居威海。技术比较垃圾，不过一直在向前追赶。
				<p>如果大家想咨询一些建站方面的问题或者是想找人代做毕业设计，可以加我微信联系。</p>
				<img src="images/mine.jpg" height="300px" width="300px" />
				</div>
			</div>
		</div>
		<jsp:include page="right.jsp"/>
	</div>
	<jsp:include page="bottom.jsp"/>
</body>
</html>
