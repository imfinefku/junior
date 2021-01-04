function initTopAndBottom() {
	var header = "<div style='width: 73.5%;margin-left: 13.25%;margin-right: 13.25%;height:100%;font-size:16px;line-height:55px;'>"
			+ "<img style='height:40px;margin-bottom:6.5px;' src='/view/img/topimg.png' />"
			+ "<a href=\"javascript:tz('/index.html')\" style='color:white;margin-right:40px;margin-left:50px;'>首页</a>"
			+ "<a href=\"javascript:subscribe()\" style='color:white;'>订阅</a></div>";
	var bottom = "";
	$("#header").html(header);
	$("#bottom").html(bottom);
}

function tz(url) {
	window.location.href = url;
}

// 获取url中的参数
function getQueryVariable(param) {
	var query = window.location.search.substring(1);
	var vars = query.split("&");
	for (var i = 0; i < vars.length; i++) {
		var pair = vars[i].split("=");
		if (pair[0] == param) {
			return pair[1];
		}
	}
	return (false);
}