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
		url : 'biaoTiController.do?method=getData',
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
			title : '标题'
		}, {
			field : 'introduce',
			width : 0,
			title : '简介'
		}, {
			field : 'path',
			width : 0,
			title : '路径'
		}, {
			field : 'sort',
			width : 0,
			title : '显示顺序'
		}, {
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
				title : "添加标题",
				offset : "auto", // http://www.layui.com/doc/modules/layer.html#offset
				id : 'message', // 防止多次弹出
				content : $("#formbar").html(),
				area : [ '400px', '400px' ],
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
				title : "修改标题",
				offset : "auto", // http://www.layui.com/doc/modules/layer.html#offset
				id : 'message', // 防止弹出多个
				content : $("#formbar").html(),
				area : [ '400px', '400px' ],
				shade : 0
			// 是否遮盖，否
			});
			// 当前编辑ID
			curID = data.id;
			// 为表单赋值
			form.val('message', {
				"name" : data.name, // "name": "value"
				"introduce" : data.introduce,
				"path" : data.path,
				"sort" : data.sort
			});
		} else if (layEvent === 'delete') { // 删除
			// 询问框
			layer.confirm('确认删除该标题？', {
				btn : [ '确认', '取消' ]
			// 按钮
			}, function() {
				delTitle(data.id);
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
				return '标题不能为空!';
			}
			if (value.length > 20) {
				return '标题长度不能超过20个字符!';
			}
		},
		introduce : function(value) {
			if (value.length > 100) {
				return '标题简介不能超过100个字符!';
			}
		},
		path : function(value) {
			if (value.length > 100) {
				return '路径不能超过100个字符!';
			}
			if (value == null || value == "") {
				return '路径不能为空!';
			}
		},
		sort : function(value) {
			if (value == null || value == "") {
				return '显示顺序不能为空!';
			}
			if (value.length > 8) {
				return '显示顺序不能超过8个字符!';
			}
		}
	});
	// 提交表单
	form.on('submit(submitTitle)', function(data) {
		layer.close(curOpen);
		if (curType == "insert") {
			insertTitle(data.field);
		} else if (curType == "update") {
			updateTitle(data.field);
		}
		return false;
	});
}

// 删除标题
function delTitle(id) {
	$.ajax({
		url : "biaoTiController.do?method=delete",
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
		url : "biaoTiController.do?method=getData",
		where : {}
	});
}

// 清理缓存
function clear() {
	$.ajax({
		url : "biaoTiController.do?method=clearTitle",
		dataType : "json",
		data : {},
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

// 添加标题
function insertTitle(data) {
	$.ajax({
		url : "biaoTiController.do?method=insertTitle",
		dataType : "json",
		data : {
			"name" : data.name,
			"introduce" : data.introduce,
			"path" : data.path,
			"sort" : data.sort
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

// 修改标题
function updateTitle(data) {
	$.ajax({
		url : "biaoTiController.do?method=updateTitle",
		dataType : "json",
		data : {
			"name" : data.name,
			"introduce" : data.introduce,
			"path" : data.path,
			"sort" : data.sort,
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
