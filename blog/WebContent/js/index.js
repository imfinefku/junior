var ins;// 幻灯
var laypage;// 分页
var carousel;// 轮播
var max = 5;// 轮播数量上限
var topsize = 2;// 顶置数量上限

window.onload = function() {
	//设置最小高度
	minHeight();
	initDh();
	initLayui();
	getCarousel();
	// 获取顶置文章数据
	getTop();
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
	layui.use([ 'carousel', 'laypage' ], function() {
		carousel = layui.carousel;
		// 幻灯
		ins = carousel.render({
			elem : '#lunbo',
			width : '760px',
			height : '300px',
			arrow : 'always'
		});
		laypage = layui.laypage;
		// 获取幻灯数据
		getCarousel();
		// 获取最新文章数据
		getNew();
	});
}

// 获取轮播数据
function getCarousel() {
	$.ajax({
				url : "view_sso.do?method=getCarousel",
				dataType : "json",
				data : {
					"max" : max
				},
				success : function(response) {
					if (response.result.length == 0) {
						$("#lunbo").css("display", "none");
					} else {
						var html = "";
						for (var i = 0; i < response.result.length; i++) {
							html += "<div><img src='"
									+ response.result[i].image
									+ "' width='100%' height='100%' \n\
		            onclick=\"textXx('"
									+ response.result[i].id
									+ "')\" style='cursor:pointer;'/></div>";
						}
						document.getElementById("lbContent").innerHTML = html;
						ins.reload();
					}
				},
				error : function(error) {
				}
			});
}

// 获取顶置数据
function getTop() {
	$.ajax({
				url : "view_sso.do?method=getTop",
				dataType : "json",
				data : {
					"max" : topsize
				},
				success : function(response) {
					if (parseInt(response.result.length) < 2) {
						$("#top1").css("display", "none");
						$("#top2").css("display", "none");
					} else {
						for (var i = 0; i < 2; i++) {
							var nr = response.result[i].title;
							if (nr.length > 35) {
								nr = nr.substring(0, 35) + "...";
							}
							var html = "<img onclick=\"textXx('"
									+ response.result[i].id
									+ "')\" src='"
									+ response.result[i].image
									+ "' width='100%' height='100%' style='opacity:0.7;'/>";
							html += "<span onclick=\"textXx('"
									+ response.result[i].id
									+ "')\" style='position:absolute;color:#FFF;height:50px;top:30%;"
									+ "left:0;right:0;text-align:center;font-size: 15px;margin-top: 5px;"
									+ "line-height: 24px;padding: 0 40px;'>"
									+ nr + "</span>";
							$("#top" + (i + 1)).html(html);
							$("#top" + (i + 1)).css("background", "#000");
						}
					}
				},
				error : function(error) {
				}
			});
}

// 获取最新数据
function getNew() {
	$.ajax({
		url : "view_sso.do?method=getNew",
		dataType : "json",
		data : {
			"cur" : 1,
			"limit" : onePageCount
		},
		success : function(response) {
			common_viewData(response);
			// 执行一个laypage实例
			laypage.render({
				elem : 'fy',
				count : response.result.total,// 数据总数，从服务端得到
				limit : onePageCount,
				jump : function(obj, first) {
					// 首次不执行
					if (!first) {
						$.ajax({
							url : "view_sso.do?method=getNew",
							dataType : "json",
							data : {
								"cur" : obj.curr, // 得到当前页，以便向服务端请求对应页的数据。
								"limit" : obj.limit
							// 得到每页显示的条数
							},
							success : function(response) {
								common_viewData(response);
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