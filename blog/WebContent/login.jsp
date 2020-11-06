<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="pragma" content="no-cache">
        <script src="common/jquery-1.10.1.min.js"></script>
        <script src="js/login.js"></script>
        <title>HTTP Status 404 – Not Found</title>
        <base href="<%=basePath%>" />
        <style>
            .nr{
                background: #525D76;
                color: white;
                font-weight: bold;
            }
        </style>
    </head>
    <body style="height: 100%;width: 100%;margin: 0 auto;">
        <div style="width: 99%;height: 27px;background: #525D76;color: white;line-height: 27px;font-size: 21px;font-weight: bold;margin-left: 0.5%;margin-top: 7px;">
            HTTP Status 404 - /login.jsp
        </div>
        <div style="width: 99%;height: 1px;background: #525D76;margin-top: 15px;margin-left: 0.5%;">
        </div>
        <div style="width: 99%;height: 62px;margin-top: 13px;margin-left: 0.5%;font-size: 13px;">
            <div style="height: 15px;"><font class="nr" onclick="typeClick()">type</font> Status report</div>
            <div style="margin-top: 10px;height: 15px;"><font class="nr" onclick="messageClick()">message</font> <font style="text-decoration:underline;">/login.jsp</font></div>
            <div style="margin-top: 10px;height: 15px;"><font class="nr" onclick="descriptionClick()">description</font> 
                <font style="text-decoration:underline;">The requested resource is not available.</font></div>
        </div>
        <div style="width: 99%;height: 1px;background: #525D76;margin-top: 15px;margin-left: 0.5%;">
        </div>
        <div style="width: 99%;height: 17px;background: #525D76;color: white;line-height: 17px;font-size: 15px;font-weight: bold;margin-left: 0.5%;margin-top: 13px;">
            Apache Tomcat/7.0.67
        </div>
        <div id="lo"></div>
    </body>
</html>
