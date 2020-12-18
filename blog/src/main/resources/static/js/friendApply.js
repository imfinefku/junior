var table;

$(function() {
	initLayui();
});

// 初始化layui
function initLayui() {
	layui.use([ 'layer', 'table', 'form' ], function() {
		table = layui.table;
		initTable();
	});
}

// 初始化表格
function initTable() {
	table
			.render({
				elem : '#grid',
				url : '/system/getFriendApplyPage',
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
							width : 0,
							title : '网站名称'
						},
						{
							field : 'url',
							width : '30%',
							title : '网址',
							templet : function(d) {
								return "<a style='color:blue;' target='_blank' href='"
										+ d.url + "'>" + d.url + "</a>";
							}
						},
						{
							field : 'status',
							width : 80,
							title : '状态',
							templet : function(d) {
								var str = "<span>未审批</span>";
								if (d.status == "1") {
									str = "<span style='color:green;'>已通过</span>";
								} else if (d.status == "2") {
									str = "<span style='color:red;'>已拒绝</span>";
								}
								return str;
							}
						},
						{
							field : 'applytime',
							width : 160,
							title : '申请时间',
							templet : function(d) {
								return getDate(d.applytime);
							}
						},
						{
							field : 'cz',
							width : 0,
							title : '操作',
							templet : function(d) {
								var toolStr = "";
								if (d.status == 0) {
									toolStr = "<a class='layui-btn layui-btn-xs' lay-event='pass'>通过</a>"
											+ "<a class='layui-btn layui-btn-danger layui-btn-xs' lay-event='refuse'>拒绝</a>";
								}
								return toolStr;
							}
						} ] ],
				page : true,
				limit : 10, // 分页，10条数据一页
				done : function(res) {
					// 获取数据后操作
				}
			});
	// 监听工具条
	table.on('tool(grid)', function(obj) { // 注：tool是工具条事件名，grid是table原始容器的属性lay-filter="对应的值"
		var data = obj.data; // 获得当前行数据
		var layEvent = obj.event; // 获得 lay-event 对应的值（也可以是表头的 event参数对应的值）
		var tr = obj.tr; // 获得当前行 tr 的DOM对象
		if (layEvent === 'pass') { // 通过
			// 询问框
			layer.confirm('确认通过该友链？', {
				btn : [ '确认', '取消' ]
			// 按钮
			}, function() {
				pass(data.id);
			}, function() {
			});
		} else if (layEvent === 'refuse') { // 拒绝
			// 询问框
			layer.confirm('确认拒绝该友链？', {
				btn : [ '确认', '取消' ]
			// 按钮
			}, function() {
				refuse(data.id);
			}, function() {
			});
		}
	});
}

// 表格数据重载
function tableReload() {
	table.reload('grid', {
		url : "/system/getFriendApplyPage",
		where : {},
		page : {
			curr : 1
		// 重新从第 1 页开始
		}
	});
}

// 通过
function pass(id) {
	$.ajax({
		url : "/system/passFriendApply",
		dataType : "json",
		type : "post",
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

// 拒绝
function refuse(id) {
	$.ajax({
		url : "/system/refuseFriendApply",
		dataType : "json",
		type : "post",
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