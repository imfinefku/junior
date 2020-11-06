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
<title>友链管理</title>
<base href="<%=basePath%>" />
<script src="common/jquery-1.10.1.min.js"></script>
<link rel="stylesheet" href="lib/layui/css/layui.css">
<script src="lib/layui/layui.js"></script>
<script src="js/ylgl.js"></script>
<script src="common/commonUtil.js"></script>
</head>
<body style="min-width: 1000px; background: #F2F2F2;">
<div style="padding: 20px;">
		<div class="layui-row">
			<div class="layui-col-md12">
				<div class="layui-card">
					<div class="layui-card-header">友链管理</div>
					<div class="layui-card-body">
						<table id="grid" lay-filter="grid"></table>
					</div>
				</div>
			</div>
		</div>
	</div>
	        <div class="layui-row" id="formbar" style="display: none;">
            <div class="layui-col-md10">
                <form class="layui-form" lay-filter="message">
                    <div class="layui-form-item"></div>
                    <div class="layui-form-item">
                        <label class="layui-form-label">站名：</label>
                        <div class="layui-input-block">
                            <input id="name" type="text" name="name" lay-verify="name" autocomplete="off" placeholder="请输入站名" class="layui-input">
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label">网址：</label>
                        <div class="layui-input-block">
                            <textarea id="url" class="layui-textarea" placeholder="请输入网址" name="url" lay-verify="url"></textarea>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label">备注：</label>
                        <div class="layui-input-block">
                            <input id="remarks" type="text" name="remarks" lay-verify="remarks" autocomplete="off" placeholder="请输入备注" class="layui-input">
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label">显示顺序：</label>
                        <div class="layui-input-block">
                            <input id="sort" type="number" name="sort" lay-verify="sort" autocomplete="off" placeholder="请输入顺序" class="layui-input">
                        </div>
                    </div>
                    <div class="layui-form-item" style="float: right;">
                        <button class="layui-btn layui-btn-sm" lay-submit="" lay-filter="submitYouLian">提交</button>
                        <button class="layui-btn layui-btn-sm layui-btn-primary" type="reset">重置</button>
                    </div>
                </form>
            </div>
        </div>
<script type="text/html" id="toolbar">
  <div class="layui-btn-container">
    <button class="layui-btn layui-btn-sm" lay-event="insert">添加友链</button>
  </div>
</script>
<script type="text/html" id="tool">
	<a class="layui-btn layui-btn-xs" lay-event="update">修改</a>
	<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="delete">删除</a>
</script>
</body>
</html>