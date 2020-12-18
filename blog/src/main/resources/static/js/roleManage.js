var table;
var curType;// 当前type,insert or update
var curID;// 当前编辑ID
var curOpen;// 当前打开的弹出层
var form;// 表单
var tree;
var layer;

$(function() {
	initLayui();
});

// 初始化layui
function initLayui() {
	layui.use([ 'tree', 'layer', 'table', 'form' ], function() {
		table = layui.table;
		tree = layui.tree
		form = layui.form;
		layer = layui.layer
		initTable();
		initForm();
		initTree();
	});
}

function initTree() {
	// 渲染
	tree.render({
		elem : '#tree' // 绑定元素
		,
		id : 'tree',
		showCheckbox : true // 是否显示复选框
		,
		data : []
	});
}

function reloadTree(id) {
	$.ajax({
		url : "/user/getRoleMenuTree",
		dataType : "json",
		data : {
			"id" : id,
			"spread" : true
		},
		success : function(response) {
			if (response.code == "200") {
				tree.reload('tree', {
					data : response.data
				});
			}
		},
		error : function(error) {
		}
	});
}

// 初始化表格
function initTable() {
	table.render({
		elem : '#grid',
		url : '/user/getRolePage',
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
			field : 'id',
			width : '0',
			title : 'id',
			hide : true
		}, {
			field : 'name',
			width : '60%',
			title : '角色名称'
		}, {
			field : 'cz',
			width : '40%',
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
				title : "添加角色",
				offset : "auto", // http://www.layui.com/doc/modules/layer.html#offset
				id : 'message', // 防止多次弹出
				content : $("#formbar"),
				area : [ '400px', '200px' ],
				shade : 0.3,
				success : function(layero) {
					// 把内容放到遮罩层里，防止遮罩挡住弹出层
					var mask = $(".layui-layer-shade");
					mask.appendTo(layero.parent());
					$("#name").val("");
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
				title : "修改角色",
				offset : "auto", // http://www.layui.com/doc/modules/layer.html#offset
				id : 'message', // 防止弹出多个
				content : $("#formbar"),
				area : [ '400px', '200px' ],
				shade : 0.3,
				success : function(layero) {
					// 把内容放到遮罩层里，防止遮罩挡住弹出层
					var mask = $(".layui-layer-shade");
					mask.appendTo(layero.parent());
					form.render();
				}
			});
			// 当前编辑ID
			curID = data.id;
			// 为表单赋值
			form.val('message', {
				"name" : data.name
			});
		} else if (layEvent === 'delete') { // 删除
			// 询问框
			layer.confirm('确认删除该角色？', {
				btn : [ '确认', '取消' ]
			// 按钮
			}, function() {
				deleteRole(data.id);
			}, function() {
			});
		} else if (layEvent === 'authorize') {
			// 当前编辑ID
			curID = data.id;
			curOpen = layer.open({
				type : 1,
				title : "授权",
				offset : "auto", // http://www.layui.com/doc/modules/layer.html#offset
				id : 'authorize', // 防止弹出多个
				content : $("#authorizeBar"),
				area : [ '450px', '475px' ],
				shade : 0.3,
				success : function(layero) {
					// 把内容放到遮罩层里，防止遮罩挡住弹出层
					var mask = $(".layui-layer-shade");
					mask.appendTo(layero.parent());
					reloadTree(data.id);
					form.render();
				}
			});
		}
	});
}

function initForm() {
	// 自定义表单验证
	form.verify({
		name : function(value) {
			if (value == null || value == "") {
				return '角色名称不能为空!';
			}
			if (value.length > 10) {
				return '角色名称长度不能超过10个字符!';
			}
		}
	});
	// 提交表单
	form.on('submit(submitRole)', function(data) {
		if (curType == "insert") {
			insertRole(data.field);
		} else if (curType == "update") {
			updateRole(data.field);
		}
		return false;
	});
}

// 删除角色
function deleteRole(id) {
	$.ajax({
		url : "/user/deleteRole",
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
		url : "/user/getRolePage",
		where : {},
		page : {
			curr : 1
		// 重新从第 1 页开始
		}
	});
}

// 添加角色
function insertRole(data) {
	$.ajax({
		url : "/user/insertRole",
		type : "post",
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		data : JSON.stringify({
			"name" : data.name
		}),
		success : function(response) {
			if (response.code == "200") {
				layer.msg(response.message, {
					icon : 1
				});
				layer.close(curOpen);
				tableReload();
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

// 修改角色
function updateRole(data) {
	$.ajax({
		url : "/user/updateRole",
		dataType : "json",
		type : "post",
		contentType : "application/json; charset=utf-8",
		data : JSON.stringify({
			"id" : curID,
			"name" : data.name
		}),
		success : function(response) {
			if (response.code == "200") {
				layer.msg(response.message, {
					icon : 1
				});
				layer.close(curOpen);
				tableReload();
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

function submit() {
	var checkedData = tree.getChecked('tree'); // 获取选中节点的数据
	$.ajax({
		url : "/user/insertRoleMenu?id=" + curID,
		dataType : "json",
		type : "post",
		contentType : "application/json; charset=utf-8",
		data : JSON.stringify(checkedData),
		success : function(response) {
			if (response.code == "200") {
				layer.msg(response.message, {
					icon : 1
				});
				layer.close(curOpen);
				tableReload();
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