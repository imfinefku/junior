var id = "";

window.onload = function() {
	initTopAndBottom();
	id = getQueryVariable("id");
	initData();
	addHits(id);
};

function initData() {
	if (id != null && id != "") {
		$
				.ajax({
					url : "/view/getBlogById",
					dataType : "json",
					data : {
						"id" : id
					},
					success : function(response) {
						document.title = response.data.title;
						var html = "<div class='bldiv'><div style='text-align:left;width:100%;font-size:23px;'>"
								+ response.data.title
								+ "</div>"
								+ "<div style='text-align:left;width:100%;color:#999;margin-top:10px;'>作者："
								+ response.data.author
								+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;发布时间："
								+ getDate(response.data.addtime)
								+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;浏览："
								+ response.data.hits
								+ "</div>"
								+ "<div style='text-align:left;width:100%;color:#888888;background:#F6F6F6;margin-top:10px;margin-bottom:10px;'>简介："
								+ response.data.summary
								+ "</div>"
								+ "<div style='width:100%;line-height:23px;'>"
								+ response.data.content
								+ "</div><div style='width:100%;margin-bottom:10px;margin-top:10px;text-align:center;line-height:23px;'><a href=\"javascript:addLikeNum('"
								+ response.data.id
								+ "')\" style='background:red;color:white;height:30px;width:200px;'>赞一下(<font id='likenum'>"
								+ response.data.likenum
								+ "</font>)</a></div></div>";
						var last = "没有了";
						var next = "没有了";
						if (response.data.last != null) {
							last = "<a href=\"javascript:lastOrNext('"
									+ response.data.last + "')\">"
									+ response.data.lastTitle + "</a>";
						}
						if (response.data.next != null) {
							next = "<a href=\"javascript:lastOrNext('"
									+ response.data.next + "')\">"
									+ response.data.nextTitle + "</a>";
						}
						html += "<div style='margin-bottom:35px;background: white;padding-left: 2%;padding-right: 2%;line-height:23px;'>上一篇："
								+ last + "<br>下一篇：" + next + "</div>";
						$("#mid").html(html);
					},
					error : function(error) {
					}
				});
	}
}

function lastOrNext(id) {
	window.location.href = "/view/html/blog.html?id=" + id;
}

function addHits(id) {
	$.ajax({
		url : "/view/addHits",
		type : "post",
		dataType : "json",
		data : {
			"id" : id
		},
		success : function(response) {
		},
		error : function(error) {
		}
	});
}

function addLikeNum(id) {
	$("#likenum").html(parseInt($("#likenum").html()) + 1);
	$.ajax({
		url : "/view/addLikeNum",
		type : "post",
		dataType : "json",
		data : {
			"id" : id
		},
		success : function(response) {
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