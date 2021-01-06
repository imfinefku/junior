var table;
var curID;// 当前编辑ID
var curOpen;// 当前打开的弹出层
var form;// 表单
var layedit;
var imgUrl = "";// 文章配图路径
var curType = "";// 当前是编辑文章还是写文章
var response;
var index;// 富文本编辑器build返回值

$(function() {
	setInterval("preventTimeOut()", 1000 * 60 * 5);
	initLayui();
});

function preventTimeOut() {
	$.ajax({
		url : "/view/preventTimeOut",
		dataType : "json",
		type : "post",
		data : {},
		success : function(response) {
		},
		error : function(error) {
		}
	});
}

// 初始化layui
function initLayui() {
	layui.use([ 'layedit', 'layer', 'upload', 'form' ], function() {
		form = layui.form;
		layedit = layui.layedit;
		initForm();
		initEdit();
		initTag();
		var upload = layui.upload;
		// 执行实例
		upload.render({
			elem : "#imageXs", // 绑定元素
			url : "/blogManage/uploadImage", // 上传接口
			type : "post",
			done : function(res) {
				// 上传完毕回调
				if (res.code == 0) {// 上传成功
					layer.msg('图片上传成功', {
						icon : 1
					});
					document.getElementById("imageXs").src = res.data.src;
					imgUrl = res.data.src;
				} else {// 上传失败
					layer.msg('图片上传失败!', {
						icon : 5
					});
				}
			},
			error : function() {
				// 请求异常回调
				layer.msg('图片上传失败!', {
					icon : 5
				});
			}
		});
	});
}

function initForm() {
	// 自定义表单验证
	form.verify({
		title : function(value) {
			if (value == null || value == "") {
				return '标题不能为空!';
			}
			if (value.length > 30) {
				return '标题长度不能超过30个字符!';
			}
		}
	});
	// 提交表单
	form.on('submit(submitBlog)', function(data) {
		if ($("#tag").val() == null || $("#tag").val() == "") {
			layer.msg("标签不能为空", {
				icon : 5
			});
			return false;
		}
		if ($("#summary").val() == null || $("#summary").val() == "") {
			layer.msg("摘要不能为空", {
				icon : 5
			});
			return false;
		}
		if ($("#summary").val().length > 100) {
			layer.msg("摘要长度不能大于100个字符", {
				icon : 5
			});
			return false;
		}
		if (curType == "insert") {// 添加文章
			insertBlog();
		} else if (curType == "update") {// 编辑文章
			updateBlog();
		}
		return false;
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

function initTag() {
	$.ajax({
		url : "/blogManage/getAllTag",
		dataType : "json",
		data : {},
		success : function(response) {
			if (response.code == "200") {
				var tagStr = "<option value=''>请选择标签</option>";
				for (var i = 0; i < response.data.length; i++) {
					tagStr += "<option value='" + response.data[i].id + "'>"
							+ response.data[i].name + "</option>";
				}
				$("#tag").html(tagStr);
				setValue();
				form.render();
			}
		},
		error : function(error) {
		}
	});
}

// 更改当前类型，是添加文章还是编辑文章。
function changeType(data) {
	curType = data;
}

// 编辑文章时，父页面传值到子页面
function addTextData(data) {
	response = data;
}

function setValue() {
	if (response != null && response != "") {
		// 为表单赋值
		$("#title").val(response.title);
		$("#tag").val(response.tag_id);
		$("#summary").val(response.summary);
		if (response.picture != null && response.picture != "") {
			document.getElementById("imageXs").src = response.picture;
			imgUrl = response.picture;
		}
		layedit.setContent(index, response.content);
	}
}

function closeParentFrame() {
	var index = parent.layer.getFrameIndex(window.name); // 先得到当前iframe层的索引
	parent.layer.close(index);
}

function insertBlog() {
	$.ajax({
		url : "/blogManage/insertBlog",
		dataType : "json",
		type : "post",
		contentType : "application/json; charset=utf-8",
		data : JSON.stringify({
			"title" : $("#title").val(),
			"content" : layedit.getContent(index),
			"tag_id" : $("#tag").val(),
			"summary" : $("#summary").val(),
			"picture" : imgUrl
		}),
		success : function(response) {
			if (response.code == "200") {
				parent.caozuoSuccess("insert");
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

function updateBlog() {
	$.ajax({
		url : "/blogManage/updateBlog",
		dataType : "json",
		type : "post",
		contentType : "application/json; charset=utf-8",
		data : JSON.stringify({
			"id" : response.id,
			"title" : $("#title").val(),
			"content" : layedit.getContent(index),
			"tag_id" : $("#tag").val(),
			"summary" : $("#summary").val(),
			"picture" : imgUrl
		}),
		success : function(response) {
			if (response.code == "200") {
				parent.caozuoSuccess("update");
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
