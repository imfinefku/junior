var laypage;// 分页

window.onload = function() {
	initTopAndBottom();
	initLayui();
};

function initLayui() {
	layui.use([ 'laypage' ], function() {
		laypage = layui.laypage;
		initBlog();
	});
}

function initBlog() {
	$.ajax({
		url : "/view/getIndexBlogPage",
		dataType : "json",
		data : {
			"page" : 1,
			"limit" : 10
		},
		success : function(response) {
			setBlogData(response.data);
			// 执行一个laypage实例
			laypage.render({
				elem : 'fy',
				count : response.count,// 数据总数，从服务端得到
				limit : 10,
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
		str += "<div style='width:100%;height:260px;background:green;' onclick=\"viewBlog('"
				+ response[i].id + "')\">" + response[i].title + "</div>";
	}
	$("#left").html(str);
}

function viewBlog(id) {
	window.open("/view/html/blog.html?id=" + id)
}