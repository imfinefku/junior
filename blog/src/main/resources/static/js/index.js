var menuList = null;
var element = null;
var curOpen = null;// 当前打开的弹出层
window.onload = function() {
	layui.use([ 'element', 'form' ], function() {
		element = layui.element;
		form = layui.form;
		initForm();
	});
	setMenu();
};

function setMenu() {
	$
			.ajax({
				url : "/user/getUserInformation",
				dataType : "json",
				data : {},
				success : function(response) {
					if (response.code == "200") {
						menuList = response.data.menu;
						$("#nickname").html(response.data.name);
						var topMenu = "";
						for (var i = 0; i < menuList.length; i++) {
							var data = menuList[i];
							if (data.level == 1) {
								topMenu += "<li class='layui-nav-item'><a href=\"javascript:setChildMenu('"
										+ data.link
										+ "','"
										+ data.id
										+ "')\">"
										+ data.name + "</a></li>";
							}
							if (data.defaultview) {
								setContent(data.link);
							}
						}
						$("#topMenu").html(topMenu);
					}
				},
				error : function(error) {
				}
			});
}

function setChildMenu(url, id) {
	if (url != "") {
		setContent(url);
	}
	var childMenu = "";
	for (var i = 0; i < menuList.length; i++) {
		var firstChild = true;
		if (menuList[i].pid == id) {
			childMenu += "<li class='layui-nav-item'><a href=\"javascript:setContent('"
					+ menuList[i].link + "')\">" + menuList[i].name + "</a>";
			for (var j = 0; j < menuList.length; j++) {
				if (menuList[j].pid == menuList[i].id) {
					if (firstChild) {
						childMenu += "<dl class=\"layui-nav-child\">";
						firstChild = false;
					}
					childMenu += "<dd><a href=\"javascript:setContent('"
							+ menuList[j].link + "')\">" + menuList[j].name
							+ "</a></dd>";
				}
			}
			if (!firstChild) {
				childMenu += "</dl>";
			}
			childMenu += "</li>";
		}
	}
	$("#leftTree").html(childMenu);
	// 重新渲染
	element.init();
}

function setContent(url) {
	if (url != "") {
		$("#content").load(url);
	}
}

function updatePassword() {
	curOpen = layer.open({
		type : 1,
		title : "修改密码",
		offset : "auto", // http://www.layui.com/doc/modules/layer.html#offset
		content : $("#updatePs"),
		area : [ '400px', '400px' ],
		shade : 0.3,
		success : function(layero) {
			// 把内容放到遮罩层里，防止遮罩挡住弹出层
			var mask = $(".layui-layer-shade");
			mask.appendTo(layero.parent());
			form.render();
		}
	});
}

function initForm() {
	// 自定义表单验证
	form.verify({
		oldPassword : function(value) {
			if (value == null || value == "") {
				return '旧密码不能为空!';
			}
			if (value.length > 20) {
				return '旧密码长度不能超过20个字符!';
			}
		},
		newPassword : function(value) {
			if (value == null || value == "") {
				return '新密码不能为空!';
			}
			if (value.length > 20) {
				return '新密码长度不能超过20个字符!';
			}
		},
		repeatNew : function(value) {
			if (value == null || value == "") {
				return '确认新密码不能为空!';
			}
			if (value.length > 20) {
				return '确认新密码长度不能超过20个字符!';
			}
		}
	});
	// 提交表单
	form.on('submit(submitUpdatePs)', function(data) {
		submitUpdatePs(data.field);
		return false;
	});
}

function submitUpdatePs(data) {
	$.ajax({
		url : "/user/updatePassword",
		type : "post",
		dataType : "json",
		data : {
			"oldPassword" : data.oldPassword,
			"newPassword" : data.newPassword,
			"repeatNew" : data.repeatNew
		},
		success : function(response) {
			if (response.code == "200") {
				layer.close(curOpen);
				layer.confirm('修改密码成功，请重新登录', {
					btn : [ '确认' ]
				// 按钮
				}, function() {
					logout();
				});
			} else {
				layer.msg(response.message, {
					icon : 5
				});
			}
		},
		error : function(error) {
		}
	});
}

function logout() {
	$.ajax({
		url : "/logout",
		dataType : "json",
		data : {},
		success : function(response) {
			if (response.code == "200") {
				window.location = "login.html";
			} else {
				alert(response.message);
			}
		},
		error : function(error) {
		}
	});
}