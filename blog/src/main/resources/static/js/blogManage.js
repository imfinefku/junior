var table;
var curType;// 当前type,insert or update
var curID;// 当前编辑ID
var curOpen;// 当前打开的弹出层
var form;// 表单
var layedit;

$(function() {
	initLayui();
});

// 初始化layui
function initLayui() {
	layui.use([ 'layedit', 'layer', 'table', 'form' ], function() {
		table = layui.table;
		form = layui.form;
		layedit = layui.layedit;
		initTable();
		initForm();
		initEdit();
	});
}

function initEdit() {
	// 注意：layedit.set 一定要放在 build 前面，否则配置全局接口将无效。
	layedit.set({
		uploadImage : {
			url : "/blogManage/uploadImage", // 接口url
			type : 'post' // 默认post
		}
	});
	index = layedit.build('policy_content', {
		height : "500"
	}); // 建立编辑器

}

// 初始化表格
function initTable() {
	table
			.render({
				elem : '#grid',
				url : '/blogManage/getBlogPage',
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
							field : 'title',
							width : 0,
							title : '标题'
						},
						{
							field : 'tag_name',
							width : 80,
							title : '标签'
						},
						{
							field : 'author',
							width : 80,
							title : '作者'
						},
						{
							field : 'addtime',
							width : 0,
							title : '添加时间',
							templet : function(d) {
								return getDate(d.addtime);
							}
						},
						{
							field : 'updatetime',
							width : 0,
							title : '修改时间',
							templet : function(d) {
								return getDate(d.updatetime);
							}
						},
						{
							field : 'likenum',
							width : 80,
							title : '点赞量'
						},
						{
							field : 'status',
							width : 80,
							title : '状态',
							templet : function(d) {
								if (d.status == 0) {
									return "<span style='color:red;'>未发布</span>";
								}
								if (d.status == 1) {
									return "<span style='color:green;'>已发布</span>";
								}
							}
						},
						{
							field : 'cz',
							width : 0,
							title : '操作',
							templet : function(d) {
								var toolStr = "<a class='layui-btn layui-btn-xs' lay-event='update'>修改</a>"
										+ "<a class='layui-btn layui-btn-danger layui-btn-xs' lay-event='delete'>删除</a>";
								if (d.status == 0) {
									toolStr += "<a class='layui-btn layui-btn-xs layui-btn-normal' lay-event='publish'>发布</a>";
								}
								if (d.status == 1) {
									toolStr += "<a class='layui-btn layui-btn-xs layui-btn-danger' lay-event='unpublish'>取消发布</a>"
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
	// 绑定头部事件
	table.on('toolbar(grid)', function(obj) {
		switch (obj.event) {
		case 'insert':
			curType = "insert";
			curOpen = layer.open({
				type : 1,
				title : "写博客",
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
				title : "修改博客",
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
			layer.confirm('确认删除该博客？', {
				btn : [ '确认', '取消' ]
			// 按钮
			}, function() {
				deleteBlog(data.id);
			}, function() {
			});
		} else if (layEvent === 'publish') { // 发布
			publishBlog(data.id);
		} else if (layEvent === 'unpublish') { // 取消发布
			unpublishBlog(data.id);
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

// 删除博客
function deleteBlog(id) {
	$.ajax({
		url : "/blogManage/deleteBlog",
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
		url : "/blogManage/getBlogPage",
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

function publishBlog(id) {
	$.ajax({
		url : "/blogManage/publishBlog",
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

function unpublishBlog(id) {
	$.ajax({
		url : "/blogManage/unpublishBlog",
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