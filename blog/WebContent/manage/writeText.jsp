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
<title>写文章</title>
<base href="<%=basePath%>" />
<script src="common/jquery-1.10.1.min.js"></script>
<link rel="stylesheet" href="lib/layui/css/layui.css">
<script src="lib/layui/layui.js"></script>
<script src="js/writeText.js"></script>
</head>
<body style="min-width: 1000px;">
	 <form class="layui-form" method="post" id="myForm" enctype="multipart/form-data" lay-filter="myForm">
            <div style="width: 100%;">
                <div class="layui-form-item">
                    <label class="layui-form-label">标题：</label>
                    <div class="layui-inline" style="width: 400px;">
                        <input id="title" type="text" name="title" lay-verify="title" autocomplete="off" placeholder="请输入标题" class="layui-input">
                    </div>
                    <div class="layui-inline" style="width: 400px;">
                        <input id="dingzhi" type="checkbox" title="顶置">
                        <input id="lunbo" type="checkbox" title="轮播">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">简介：</label>
                    <div class="layui-inline" style="width: 400px;">
                        <input type="text" id="introduce" name="introduce" lay-verify="introduce" 
                        autocomplete="off" placeholder="请输入简介" class="layui-input">
                    </div>
                    配图：<img class="layui-inline" id="imageXs" width="50px" height="50px" 
                            src="images/uploadBackground.png" 
                            style="border: 1px solid #8D8D8D;cursor:pointer;"/>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">文章类型：</label>
                    <div class="layui-inline" style="width: 400px;">
                        <select id="textType" name="textType" lay-filter="textType">
                        </select>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">标签：</label>
                    <div class="layui-inline" style="width: 400px;">
                        <select id="tag" name="tag" lay-filter="tag">
                        </select>
                    </div>
                    <div class="layui-inline" style="width: 400px;">
                        <button class="layui-btn layui-btn-sm" lay-submit="" lay-filter="submitTitle">提交</button>
                        <button class="layui-btn layui-btn-sm" onclick="closeParentFrame()">取消</button>
                        <label id="autoSave" style="margin-left:50px;color:green;"></label>
                    </div>
                </div>
            </div>
            <textarea class="layui-textarea layui-hide policy_content" 
                      name="policy_content" lay-verify="policy_content" id="policy_content">
            </textarea>
        </form>
</body>
</html>