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
		getDataByType();
	});
}

//获取最新数据
function getDataByType() {
	$.ajax({
		url : "view_sso.do?method=getDataByType",
		dataType : "json",
		data : {
			"cur" : 1,
			"limit" : onePageCount,
			"textType":$("#textType").val()
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
							url : "view_sso.do?method=getDataByType",
							dataType : "json",
							data : {
								"cur" : obj.curr, // 得到当前页，以便向服务端请求对应页的数据。
								"limit" : obj.limit,// 得到每页显示的条数
								"textType":$("#textType").val()
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