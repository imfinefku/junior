window.onload = function() {
	initTopAndBottom();
	initData();
};

function initData() {
	var id = getQueryVariable("id");
	if (id != null && id != "") {
		$.ajax({
			url : "/view/getBlogById",
			dataType : "json",
			data : {
				"id" : id
			},
			success : function(response) {
				document.title=response.data.title;
				$("#mid").html(response.data.content);
			},
			error : function(error) {
			}
		});
	}
}