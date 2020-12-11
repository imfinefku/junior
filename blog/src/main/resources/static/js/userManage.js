var table;
var curType;// 当前type,insert or update
var curID;// 当前编辑ID
var curOpen;// 当前打开的弹出层
var form;// 表单

$(function() {
	initLayui();
});

// 初始化layui
function initLayui() {
	layui.use([ 'layer', 'table', 'form' ], function() {
		table = layui.table;
		form = layui.form;
		initTable();
		initForm();
		getRole();
	});
}

// 初始化表格
function initTable() {
	table.render({
		elem : '#grid',
		url : '/user/getDataPage',
		toolbar : '#toolbar',
		id : "grid",
		parseData : function(res) { // res 即为原始返回的数据
			return {
				"data" : res.data.data,
				"code" : res.data.code,
				"count" : res.data.count
			};
		},
		height : "full",
		cols : [ [ {
			field : 'name',
			width : 0,
			title : '用户名'
		}, {
			field : 'username',
			width : 0,
			title : '账号'
		}, {
			field : 'password',
			width : 0,
			title : '密码'
		}, {
			field : 'rolename',
			width : 0,
			title : '角色'
		}, {
			field : 'cz',
			width : 0,
			toolbar : '#tool',
			title : '操作'
		} ] ],
		page : true,
		limit : 10, // 分页，10条数据一页
		done : function(res) {
			// 获取数据后操作
		}
	});
	// 绑定头部事件
	table.on('toolbar(grid)', function(obj) {
		switch (obj.event) {
		case 'insert':
			curType = "insert";
			curOpen = layer.open({
				type : 1,
				title : "添加用户",
				offset : "auto", // http://www.layui.com/doc/modules/layer.html#offset
				id : 'message', // 防止多次弹出
				content : $("#formbar"),
				area : [ '400px', '460px' ],
				shade : 0.3,
				success : function(layero) {
					// 把内容放到遮罩层里，防止遮罩挡住弹出层
					var mask = $(".layui-layer-shade");
					mask.appendTo(layero.parent());
					$("#name").val("");
					$("#username").val("");
					$("#password").val("");
					$("#role").val("");
					$("#reset").css("display", "");
					$("#username").removeAttr("disabled");
					form.render();
				}
			});
			break;
		}
		;
	});
	// 监听工具条
	table.on('tool(grid)', function(obj) { // 注：tool是工具条事件名，grid是table原始容器的属性lay-filter="对应的值"
		var data = obj.data; // 获得当前行数据
		var layEvent = obj.event; // 获得 lay-event 对应的值（也可以是表头的 event参数对应的值）
		var tr = obj.tr; // 获得当前行 tr 的DOM对象
		if (layEvent === 'update') { // 修改
			curType = "update";
			curOpen = layer.open({
				type : 1,
				title : "修改用户",
				offset : "auto", // http://www.layui.com/doc/modules/layer.html#offset
				id : 'message', // 防止弹出多个
				content : $("#formbar"),
				area : [ '400px', '460px' ],
				shade : 0.3,
				success : function(layero) {
					// 把内容放到遮罩层里，防止遮罩挡住弹出层
					var mask = $(".layui-layer-shade");
					mask.appendTo(layero.parent());
					$("#reset").css("display", "none");
					$("#username").attr("disabled", "disabled");
					form.render();
				}
			});
			// 当前编辑ID
			curID = data.id;
			// 为表单赋值
			form.val('message', {
				"name" : data.name,
				"username" : data.username,
				"password" : data.password,
				"role" : data.role_id
			});
		} else if (layEvent === 'delete') { // 删除
			// 询问框
			layer.confirm('确认删除该用户？', {
				btn : [ '确认', '取消' ]
			// 按钮
			}, function() {
				deleteUser(data.id);
			}, function() {
			});
		}
	});
}

function initForm() {
	// 自定义表单验证
	form.verify({
		name : function(value) {
			if (value == null || value == "") {
				return '用户名不能为空!';
			}
			if (value.length > 20) {
				return '用户名长度不能超过10个字符!';
			}
		},
		username : function(value) {
			if (value == null || value == "") {
				return '账号不能为空!';
			}
			if (value.length > 20) {
				return '账号长度不能超过20个字符!';
			}
		},
		password : function(value) {
			if (value == null || value == "") {
				return '密码不能为空!';
			}
			if (value.length > 20) {
				return '密码长度不能超过20个字符!';
			}
		}
	});
	// 提交表单
	form.on('submit(submitUser)', function(data) {
		if (curType == "insert") {
			insertUser(data.field);
		} else if (curType == "update") {
			updateUser(data.field);
		}
		return false;
	});
}

// 删除用户
function deleteUser(id) {
	$.ajax({
		url : "/user/deleteUser",
		type : "post",
		dataType : "json",
		data : {
			"id" : id
		},
		success : function(response) {
			if (response.code == "200") {
				layer.msg(response.message, {
					icon : 1
				});
			} else {
				layer.msg(response.message, {
					icon : 5
				});
			}
			tableReload();
		},
		error : function(error) {
		}
	});
}

// 表格数据重载
function tableReload() {
	table.reload('grid', {
		url : "/user/getDataPage",
		where : {},
		page : {
			curr : 1
		// 重新从第 1 页开始
		}
	});
}

// 添加用户
function insertUser(data) {
	$.ajax({
		url : "/user/insertUser",
		type : "post",
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		data : JSON.stringify({
			"name" : data.name,
			"username" : data.username,
			"password" : data.password,
			"role_id" : data.role
		}),
		success : function(response) {
			if (response.code == "200") {
				layer.msg(response.message, {
					icon : 1
				});
			} else {
				layer.msg(response.message, {
					icon : 5
				});
			}
			layer.close(curOpen);
			tableReload();
		},
		error : function(error) {
		}
	});
}

// 修改用户
function updateUser(data) {
	$.ajax({
		url : "/user/updateUser",
		dataType : "json",
		type : "post",
		contentType : "application/json; charset=utf-8",
		data : JSON.stringify({
			"id" : curID,
			"name" : data.name,
			"username" : data.username,
			"password" : data.password,
			"role_id" : data.role
		}),
		success : function(response) {
			if (response.code == "200") {
				layer.msg(response.message, {
					icon : 1
				});
			} else {
				layer.msg(response.message, {
					icon : 5
				});
			}
			layer.close(curOpen);
			tableReload();
		},
		error : function(error) {
		}
	});
}

// 获取角色
function getRole(roleid) {
	$.ajax({
		url : "/user/getRole",
		dataType : "json",
		data : {},
		success : function(response) {
			var roleStr = "<option value=''>请选择角色</option>";
			for (var i = 0; i < response.data.length; i++) {
				roleStr += "<option value='" + response.data[i].id + "'>"
						+ response.data[i].name + "</option>";
			}
			$("#role").html(roleStr);
		},
		error : function(error) {
		}
	});
}
