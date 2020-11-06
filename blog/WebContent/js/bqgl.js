var table;
var curType;// 当前type,insert or update
var curID;// 当前编辑ID
var curOpen;// 当前打开的弹出层
var form;// 表单

window.onload = function() {
	initLayui();
};

// 初始化layui
function initLayui() {
	layui.use([ 'layer', 'table' ], function() {
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
		url : 'biaoQianController.do?method=getData',
		toolbar : '#toolbar',
		id : "grid",
		parseData : function(res) { // res 即为原始返回的数据
			return {
				"data" : res.result.data,
				"code" : res.errorCode,
				"count" : res.result.count,
				"code" : res.errorCode
			};
		},
		height : "full",
		cols : [ [ {
			field : 'name',
			width : 0,
			title : '标签'
		},{
			field : 'inserttime',
			width : 0,
			title : '添加时间',
			templet : function(d) {
				return timestampToTime(parseInt(d.inserttime));
			}
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
				title : "添加标签",
				offset : "auto", // http://www.layui.com/doc/modules/layer.html#offset
				id : 'message', // 防止多次弹出
				content : $("#formbar").html(),
				area : [ '400px', '160px' ],
				shade : 0
			// 是否遮盖，否
			});
			break;
		case 'clear':
			clear();
			break;
		}
		;
	});
	// 监听工具条
	table.on('tool(grid)', function(obj) { // 注：tool是工具条事件名，test是table原始容器的属性lay-filter="对应的值"
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
				content : $("#formbar").html(),
				area : [ '400px', '160px' ],
				shade : 0
			// 是否遮盖，否
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
				delBiaoQian(data.id);
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
				return '标签不能为空!';
			}
			if (value.length > 20) {
				return '标签长度不能超过20个字符!';
			}
		}
	});
	// 提交表单
	form.on('submit(submitBiaoQian)', function(data) {
		layer.close(curOpen);
		if (curType == "insert") {
			insertBiaoQian(data.field);
		} else if (curType == "update") {
			updateBiaoQian(data.field);
		}
		return false;
	});
}

// 删除标签
function delBiaoQian(id) {
	$.ajax({
		url : "biaoQianController.do?method=delete",
		dataType : "json",
		data : {
			"id" : id
		},
		success : function(response) {
			if (response.errorCode == "0") {
				layer.msg(response.errorMsg, {
					icon : 1
				});
			} else {
				layer.msg(response.errorMsg, {
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
		url : "biaoQianController.do?method=getData",
		where : {}
	});
}

// 添加标签
function insertBiaoQian(data) {
	$.ajax({
		url : "biaoQianController.do?method=insert",
		dataType : "json",
		data : {
			"name" : data.name
		},
		success : function(response) {
			if (response.errorCode == "0") {
				layer.msg(response.errorMsg, {
					icon : 1
				});
			} else {
				layer.msg(response.errorMsg, {
					icon : 5
				});
			}
			tableReload();
		},
		error : function(error) {
		}
	});
}

// 修改标签
function updateBiaoQian(data) {
	$.ajax({
		url : "biaoQianController.do?method=update",
		dataType : "json",
		data : {
			"name" : data.name,
			"id": curID
		},
		success : function(response) {
			if (response.errorCode == "0") {
				layer.msg(response.errorMsg, {
					icon : 1
				});
			} else {
				layer.msg(response.errorMsg, {
					icon : 5
				});
			}
			tableReload();
		},
		error : function(error) {
		}
	});
}
