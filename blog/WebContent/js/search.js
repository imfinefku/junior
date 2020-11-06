var laypage;// 分页

window.onload = function() {
	//设置最小高度
	minHeight();
	initDh();
	initLayui();
	//获取标签数据
	common_labelView();
	//获取友链数据
	common_friendView();
	//获取点击排行数据
	common_djphView();
	//获取最新留言数据
	common_LiuYanView();
};

function initLayui() {
	layui.use('laypage', function() {
		laypage = layui.laypage;
		//根据分类获取数据
		getFyTextByFlOrSearch();
	});
}

//获取检索数据
function getFyTextByFlOrSearch() {
	$.ajax({
		url : "view_sso.do?method=getFyTextByFlOrSearch",
		dataType : "json",
		data : {
			"cur" : 1,
			"limit" : onePageCount,
			"content":$("#content_search").val(),
			"tag":$("#tag_search").val()
		},
		success : function(response) {
			common_viewData_this(response);
			// 执行一个laypage实例
			laypage.render({
				elem : 'fy',
				count : response.result.total,// 数据总数，从服务端得到
				limit : onePageCount,
				jump : function(obj, first) {
					// 首次不执行
					if (!first) {
						$.ajax({
							url : "view_sso.do?method=getFyTextByFlOrSearch",
							dataType : "json",
							data : {
								"cur" : obj.curr, // 得到当前页，以便向服务端请求对应页的数据。
								"limit" : obj.limit,// 得到每页显示的条数
								"content":$("#content_search").val(),
								"tag":$("#tag_search").val()
							},
							success : function(response) {
								common_viewData_this(response);
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

function common_viewData_this(response) {
	var data = response.result.data;
	var html = "";
	for (var i = 0; i < data.length; i++) {
		var title=data[i].title;
		if (title.length>36){
			title=title.substring(0,36)+"...";
		}
		if ($("#content_search").val()!=null && $("#content_search").val()!=""){
			title=title.replace(new RegExp($("#content_search").val(),"gm"), 
					"<font style='color:red;'>"+$("#content_search").val()+"</font>");
		}
		html += "<div class=\"zw\">"
				+ "<div style='width:100%;font-size:18px;margin-left:10px;margin-top:15px;'>"
				+ "<font style='font-weight: bold;color: #555;cursor: pointer;' onclick=\"textXx('"+ data[i].id+ "')\">"
				+ title
				+ "</font>"
				+ "</div><div style='width:100%;height:30px;margin-top:5px;padding-left:10px;line-height:30px;'>"+
				"<span>时间："+timestampToTime3(data[i].inserttime)+"</span>|" +
				"<span style='margin-left:5px;margin-right:5px;'>分类：<font " +
						"style='cursor: pointer;' onclick=\"searchByTag('"+data[i].tagid+"')\">"
				+data[i].tagname+"</font></span>|" +
				"<span style='margin-left:5px;margin-right:5px;'>浏览：("+data[i].djl+")"+"</span>|" +
				"<span style='margin-left:5px;margin-right:5px;cursor: pointer;' " +
				"onclick=\"textXx('"+ data[i].id+ "')\">阅读全文</span>"+
				"</div>"
				+ "<div style='width:100%;height:50%;margin-left:10px;margin-top:10px;'>";
		var contentWidth=95;
		var content=data[i].introduce;
		if (data[i].image != null && data[i].image != "") {
			html += "<img onclick=\"textXx('"+ data[i].id+ "')\" width='25%' height='100%' src='" + data[i].image
					+ "' style='float:left;margin-right:2%;cursor: pointer;'/> ";
			contentWidth=69;
			if (content.length>130){
				content=content.substring(0,130)+"...";
			}
		}
		if ($("#content_search").val()!=null && $("#content_search").val()!=""){
			content=content.replace(new RegExp($("#content_search").val(),"gm"), 
					"<font style='color:red;'>"+$("#content_search").val()+"</font>");
		}
		html += "<div style='width:"+contentWidth+"%;height:100%;float:left;line-height:25px;color:#888;'>" +
				"<font onclick=\"textXx('"+ data[i].id+ "')\" style='cursor: pointer;'>"
				+ content + "</font></div></div></div>";
	}
	if (data.length==0){
		$("#fy").css("display","none");
		html="<div style='width:100%;height:50px;line-height:50px;text-align:center;margin-bottom:20px;margin-top:20px;" +
		"background:white;'>暂无关键词为&nbsp;"+$("#content_search").val()+"&nbsp;的内容</div>";
	}
	$("#newContent").html(html);
}