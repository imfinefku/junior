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
	});
}

//初始化表格
function initTable() {
	table
			.render({
				elem : '#grid',
				url : '/system/getFriendPage',
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
				cols : [ [
						{
							field : 'id',
							width : 0,
							title : 'id',
							hide : true
						},
						{
							field : 'name',
							width : 200,
							title : '网站名称'
						},
						{
							field : 'url',
							width : 0,
							title : '网址',
							templet : function(d) {
								return "<a style='color:blue;' target='_blank' href='"
										+ d.url + "'>" + d.url + "</a>";
							}
						},
						{
							field : 'orders',
							width : 100,
							title : '显示顺序'
						},
						{
							field : 'status',
							width : 80,
							title : '状态',
							templet : function(d) {
								var str = "<span style='color:green;'>正常</span>";
								if (d.status == "1") {
									str = "<span style='color:red;'>禁用</span>"
								}
								return str;
							}
						},
						{
							field : 'addtime',
							width : 160,
							title : '添加时间',
							templet : function(d) {
								return getDate(d.addtime);
							}
						},
						{
							field : 'cz',
							width : 200,
							title : '操作',
							templet : function(d) {
								var toolStr = "<a class='layui-btn layui-btn-xs' lay-event='update'>修改</a>";
								if (d.status == 0) {
									toolStr += "<a class='layui-btn layui-btn-xs layui-btn-danger' lay-event='unuse'>禁用</a>";
								}
								if (d.status == 1) {
									toolStr += "<a class='layui-btn layui-btn-xs layui-btn-normal' lay-event='use'>启用</a>"
								}
								toolStr += "<a class='layui-btn layui-btn-danger layui-btn-xs' lay-event='delete'>删除</a>";
								return toolStr;
							}
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
				title : "添加友链",
				offset : "auto", // http://www.layui.com/doc/modules/layer.html#offset
				id : 'message', // 防止多次弹出
				content : $("#formbar"),
				area : [ '400px', '400px' ],
				shade : 0.3,
				success : function(layero) {
					// 把内容放到遮罩层里，防止遮罩挡住弹出层
					var mask = $(".layui-layer-shade");
					mask.appendTo(layero.parent());
					$("#name").val("");
					$("#url").val("");
					findFriendMaxOrders();
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
				title : "修改友链",
				offset : "auto", // http://www.layui.com/doc/modules/layer.html#offset
				id : 'message', // 防止弹出多个
				content : $("#formbar"),
				area : [ '400px', '400px' ],
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
				"name" : data.name,
				"orders" : data.orders,
				"url" : data.url
			});
		} else if (layEvent === 'delete') { // 删除
			// 询问框
			layer.confirm('确认删除该友链？', {
				btn : [ '确认', '取消' ]
			// 按钮
			}, function() {
				deleteFriend(data.id);
			}, function() {
			});
		} else if (layEvent === 'unuse') { // 禁用
			unUseFriend(data.id);
		} else if (layEvent === 'use') { // 启用
			startUseFriend(data.id);
		}
	});
}
function initForm() {
	// 自定义表单验证
	form.verify({
		name : function(value) {
			if (value == null || value == "") {
				return '网站名称不能为空!';
			}
			if (value.length > 10) {
				return '网站名称长度不能超过10个字符!';
			}
		},
		orders : function(value) {
			if (value == null || value == "") {
				return '显示顺序不能为空!';
			}
			if (value > 10000) {
				return '显示顺序不能大于10000!';
			}
		}
	});
	// 提交表单
	form.on('submit(submitFriend)', function(data) {
		if (curType == "insert") {
			insertFriend(data.field);
		} else if (curType == "update") {
			updateFriend(data.field);
		}
		return false;
	});
}

//删除友链
function deleteFriend(id) {
	$.ajax({
		url : "/system/deleteFriend",
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
		url : "/system/getFriendPage",
		where : {},
		page : {
			curr : 1
		// 重新从第 1 页开始
		}
	});
}

//禁用友链
function unUseFriend(id) {
	$.ajax({
		url : "/system/unUseFriend",
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

// 启用友链
function startUseFriend(id) {
	$.ajax({
		url : "/system/startUseFriend",
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

// 添加友链
function insertFriend(data) {
	$.ajax({
		url : "/system/insertFriend",
		type : "post",
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		data : JSON.stringify({
			"name" : data.name,
			"orders" : data.orders,
			"url" : data.url
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

//修改友链
function updateFriend(data) {
	$.ajax({
		url : "/system/updateFriend",
		dataType : "json",
		type : "post",
		contentType : "application/json; charset=utf-8",
		data : JSON.stringify({
			"id" : curID,
			"name" : data.name,
			"orders" : data.orders,
			"url" : data.url
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

function findFriendMaxOrders() {
	$.ajax({
		url : "/system/findFriendMaxOrders",
		dataType : "json",
		data : {},
		success : function(response) {
			$("#orders").val(response.data);
		},
		error : function(error) {
		}
	});
}

function getDate(timestamp) {
	var date = new Date(timestamp);
	Y = date.getFullYear() + '-';
	M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date
			.getMonth() + 1)
			+ '-';
	D = date.getDate() < 10 ? '0' + date.getDate() : date.getDate();
	D += ' ';
	h = date.getHours() < 10 ? '0' + date.getHours() : date.getHours();
	h += ':';
	m = date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes();
	m += ':';
	s = date.getSeconds() < 10 ? '0' + date.getSeconds() : date.getSeconds();
	return Y + M + D + h + m + s;
}