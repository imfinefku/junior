var table;

window.onload = function() {
	initLayui();
};

// 初始化layui
function initLayui() {
	layui.use([ 'layer', 'table' ], function() {
		table = layui.table;
		initTable();
	});
}

// 初始化表格
function initTable() {
	table.render({
		elem : '#grid',
		url : 'dataBaseController.do?method=getDataCopy',
		toolbar : '#toolbar',
		id: "grid",
		parseData : function(res) { // res 即为原始返回的数据
			return {
				"data" : res.result,
				"code" : res.errorCode,
				"count" : res.result.length,
				"code" : res.errorCode
			};
		},
		cols : [ [ {
			field : 'name',
			width : "20%",
			title : '名称'
		}, {
			field : 'path',
			width : "30%",
			title : '路径'
		}, {
			field : 'time',
			width : "20%",
			title : '备份时间',
			sort : true,
			templet: function(d){
				var name=d.name;
				var time=name.split("_")[1];
		        return timestampToTime(parseInt(time));
		    }
		}, {
			field : 'cz',
			width : "30%",
			toolbar : '#tool',
			title : '操作'
		} ] ]
	});
	// 监听工具条
	table.on('tool(grid)', function(obj) { // 注：tool是工具条事件名，test是table原始容器的属性lay-filter="对应的值"
		var data = obj.data; // 获得当前行数据
		var layEvent = obj.event; // 获得 lay-event 对应的值（也可以是表头的 event参数对应的值）
		var tr = obj.tr; // 获得当前行 tr 的DOM对象
		if (layEvent === 'export') { // 导出
			downLoad(data.name);
		} else if (layEvent === 'delete') { // 删除
			//询问框
			layer.confirm('确认删除该备份数据？', {
			  btn: ['确认','取消'] //按钮
			}, function(){
				del(data.path);
			}, function(){
			});
		}
	});
}

// 备份
function bf() {
	$.ajax({
		url : "dataBaseController.do?method=dataBaseCopy",
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

// 表格数据重载
function tableReload() {
	table.reload('grid', {
		url : "dataBaseController.do?method=getDataCopy",
		where : {}
	});
}

// 删除数据
function del(path) {
	$.ajax({
		url : "dataBaseController.do?method=delete",
		dataType : "json",
		data : {
			"path" : path
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

//下载数据
function downLoad(fileName) {
	window.location.href="dataBaseController.do?method=downLoad&fileName="+fileName;
}