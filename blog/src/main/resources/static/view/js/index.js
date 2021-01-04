var laypage;// 分页
var limit = 5;// 每页数据量
var form;// 表单
var curOpen;// 当前打开的弹出层

window.onload = function() {
	initTopAndBottom();
	initLayui();
	initTag();
	initFriend();
};

function initLayui() {
	layui.use([ 'laypage', 'layer', 'form' ], function() {
		laypage = layui.laypage;
		form = layui.form;
		initBlog();
		initForm();
	});
}

function initForm() {
	// 自定义表单验证
	form.verify({
		name : function(value) {
			if (value == null || value == "") {
				return '昵称不能为空!';
			}
			if (value.length > 10) {
				return '昵称长度不能超过10个字符!';
			}
		},		
		webname : function(value) {
			if (value == null || value == "") {
				return '网站名称不能为空!';
			}
			if (value.length > 10) {
				return '网站名称长度不能超过10个字符!';
			}
		}
	});
	// 提交表单
	form.on('submit(submitSubscribe)', function(data) {
		submitSubscribe(data.field);
		return false;
	});
	// 提交表单
	form.on('submit(submitApply)', function(data) {
		submitApply(data.field);
		return false;
	});
}

function submitApply(data){
	$.ajax({
		url : "/view/addFriendAapply",
		type : "post",
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		data : JSON.stringify({
			"name" : data.webname,
			"url" : data.url
		}),
		success : function(response) {
			if (response.code == "200") {
				layer.msg("申请成功，请等待站长审核", {
					icon : 1
				});
				layer.close(curOpen);
			} else {
				layer.msg(response.message + ":" + response.data[0], {
					icon : 5
				});
			}
		},
		error : function(error) {
		}
	});
}

function initFriend() {
	$
			.ajax({
				url : "/view/getViewFriends",
				dataType : "json",
				data : {},
				success : function(response) {
					var html = "";
					for (var i = 0; i < response.data.length; i++) {
						html += "<div onclick=\"friendLoad('"
								+ response.data[i].url
								+ "')\" style='float:left;margin-left:10px;cursor: pointer;'>"
								+ response.data[i].name + "</div>";
					}
					if (html != "") {
						$("#friend").css("display", "");
					}
					html += "<a href='javascript:applyFriend()' style='color:blue;'>申请友链</a>";
					$("#friend").html(html);

				},
				error : function(error) {
				}
			});
}

function applyFriend() {
	curOpen = layer.open({
		type : 1,
		title : "申请友链",
		offset : "auto", // http://www.layui.com/doc/modules/layer.html#offset
		id : 'message', // 防止多次弹出
		content : $("#apply"),
		area : [ '400px', '300px' ],
		shade : 0.3,
		success : function(layero) {
			// 把内容放到遮罩层里，防止遮罩挡住弹出层
			var mask = $(".layui-layer-shade");
			mask.appendTo(layero.parent());
			$("#webname").val("");
			$("#url").val("");
			form.render();
		}
	});
}

function friendLoad(url) {
	window.open(url);
}

function initTag() {
	$.ajax({
		url : "/view/getViewTags",
		dataType : "json",
		data : {},
		success : function(response) {
			var html = "";
			for (var i = 0; i < response.data.length; i++) {
				html += "<div style='float:left;margin-left:10px;'>"
						+ response.data[i].name + "(" + response.data[i].num
						+ ")</div>";
			}
			$("#tag").html(html);
			if (html != "") {
				$("#tag").css("display", "");
			}

		},
		error : function(error) {
		}
	});
}

function initBlog() {
	$.ajax({
		url : "/view/getIndexBlogPage",
		dataType : "json",
		data : {
			"page" : 1,
			"limit" : limit
		},
		success : function(response) {
			setBlogData(response.data);
			// 执行一个laypage实例
			laypage.render({
				elem : 'fy',
				count : response.count,// 数据总数，从服务端得到
				limit : limit,
				jump : function(obj, first) {
					// 首次不执行
					if (!first) {
						$.ajax({
							url : "/view/getIndexBlogPage",
							dataType : "json",
							data : {
								"page" : obj.curr, // 得到当前页，以便向服务端请求对应页的数据。
								"limit" : obj.limit
							// 得到每页显示的条数
							},
							success : function(response) {
								setBlogData(response.data);
							},
							error : function(error) {
							}
						})
					}
				}
			});
		},
		error : function(error) {
		}
	});
}

function setBlogData(response) {
	var str = "";
	for (var i = 0; i < response.length; i++) {
		str += "<div style='width:100%;height:245px;margin-top:1px;background:white;' onclick=\"viewBlog('"
				+ response[i].id
				+ "')\"><div style='height:50px;line-height:50px;margin-left:5%;font-size:20px;overflow:hidden;'>"
				+ response[i].title
				+ "</div><div style='margin-left:5%;margin-right:5%;height:20px;line-height:20px;background:#EAEAEA;margin-bottom:20px;overflow:hidden;'>作者："
				+ response[i].author
				+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;发布时间："
				+ getDate(response[i].addtime)
				+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;浏览："
				+ response[i].hits + "</div>";
		if (response[i].picture != null && response[i].picture != "") {
			str += "<div style='background: url("
					+ response[i].picture
					+ ") repeat;background-size:100% 100%;height:130px;width:35%;margin-left:5%;float:left;'></div><div style='float:left;height:130px;width:52%;margin-left:3%;line-height:23px;overflow:hidden;'>"
					+ response[i].summary + "</div>";
		} else {
			str += "<div style='float:left;height:130px;width:90%;margin-left:5%;line-height:23px;overflow:hidden;'>"
					+ response[i].summary + "</div>";
		}

		str += "</div>";
	}
	$("#left").html(str);
}

function viewBlog(id) {
	window.open("/view/html/blog.html?id=" + id)
}

function subscribe() {
	curOpen = layer.open({
		type : 1,
		title : "订阅",
		offset : "auto", // http://www.layui.com/doc/modules/layer.html#offset
		id : 'message', // 防止多次弹出
		content : $("#formbar"),
		area : [ '400px', '300px' ],
		shade : 0.3,
		success : function(layero) {
			// 把内容放到遮罩层里，防止遮罩挡住弹出层
			var mask = $(".layui-layer-shade");
			mask.appendTo(layero.parent());
			$("#name").val("");
			$("#email").val("");
			form.render();
		}
	});
}

function submitSubscribe(data) {
	$.ajax({
		url : "/view/viewSubscribe",
		type : "post",
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		data : JSON.stringify({
			"name" : data.name,
			"email" : data.email
		}),
		success : function(response) {
			if (response.code == "200") {
				layer.msg("订阅成功", {
					icon : 1
				});
				layer.close(curOpen);
			} else {
				layer.msg(response.message + ":" + response.data[0], {
					icon : 5
				});
			}
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