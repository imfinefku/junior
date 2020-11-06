var element;// 导航控件
var personMsgOpen;// 个人信息弹出层

window.onload = function() {
	initLayui();
};

// 初始化layui
function initLayui() {
	// 注意：导航 依赖 element 模块，否则无法进行功能性操作
	layui.use([ 'element', 'layer'], function() {
		element = layui.element;
		personMsgOpen = layui.layer;
	});
}

// 退出登录
function logout() {
	$.ajax({
		url : "login_sso.do?method=logout",
		dataType : "text",
		data : {},
		success : function(response) {
			window.location = "login.jsp";
		},
		error : function(error) {
		}
	});
}

// 跳转
function tz(data) {
	document.getElementById("frame").src = data;
}