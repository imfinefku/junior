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

// 初始化表格
function initTable() {
	table.render({
		elem : '#grid',
		url : '/blogManage/getDataPage',
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
			width : '30%',
			title : '标签名称'
		}, {
			field : 'addtime',
			width : '30%',
			title : '添加时间',
			templet : function(d) {
				return getDate(d.addtime);
			}
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
				title : "添加标签",
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
				title : "修改标签",
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
			layer.confirm('确认删除该标签？', {
				btn : [ '确认', '取消' ]
			// 按钮
			}, function() {
				deleteTag(data.id);
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
				return '标签名称不能为空!';
			}
			if (value.length > 20) {
				return '标签名称长度不能超过20个字符!';
			}
		}
	});
	// 提交表单
	form.on('submit(submitTag)', function(data) {
		if (curType == "insert") {
			insertTag(data.field);
		} else if (curType == "update") {
			updateTag(data.field);
		}
		return false;
	});
}

// 删除标签
function deleteTag(id) {
	$.ajax({
		url : "/blogManage/deleteTag",
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
		url : "/blogManage/getDataPage",
		where : {},
		page : {
			curr : 1
		// 重新从第 1 页开始
		}
	});
}

// 添加标签
function insertTag(data) {
	$.ajax({
		url : "/blogManage/insertTag",
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

// 修改标签
function updateTag(data) {
	$.ajax({
		url : "/blogManage/updateTag",
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