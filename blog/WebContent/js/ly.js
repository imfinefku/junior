var layedit;
var index;//富文本编辑器build返回值

window.onload=function(){
	//设置最小高度
	minHeight();
	initLayui();
	initDh();
	//获取标签数据
	common_labelView();
	//获取友链数据
	common_friendView();
	//获取点击排行数据
	common_djphView();
	//获取留言cookie
	getUserCookie();
	//获取最新留言数据
	common_LiuYanView();
};

function initLayui() {
	layui.use(['laypage','layedit'], function() {
		//分页
		laypage = layui.laypage;
		//富文本编辑器
		layedit = layui.layedit;
		index=layedit.build('policy_content', 
				{tool: ['face']});
		//获取留言数据
		getLy();
	});
}

function submitly(){
	var name=$("#yourname").val();
	var content=layedit.getContent(index);
	$.ajax({
		url:"view_sso.do?method=insertLy",
		type: 'POST',
		dataType:"json",
		data:{
			"name":name,
			"content":content,
			"lytype":"0",
			"type":0,
			"chunwenben":layedit.getText(index)
		},
		success:function(response){
			if (response.errorCode=="0"){
				layer.msg('评论成功', {icon: 1});
				$(window.frames["LAY_layedit_1"].document).find('body').html('');
				getLy();
				setCookie("name",name);
			}else{
				layer.msg(response.errorMsg, {
					icon : 5
				});
			}
		},
		error:function(error){
		}
	});
}

function getLy(){
	$.ajax({
		url : "view_sso.do?method=getLybFy",
		dataType : "json",
		data : {
			"cur" : 1,
			"limit" : onePageCount
		},
		success : function(response) {
			viewLiuYanBan(response);
			// 执行一个laypage实例
			laypage.render({
				elem : 'fy',
				count : response.result.total,// 数据总数，从服务端得到
				limit : onePageCount,
				jump : function(obj, first) {
					// 首次不执行
					if (!first) {
						$.ajax({
							url : "view_sso.do?method=getLybFy",
							dataType : "json",
							data : {
								"cur" : obj.curr, // 得到当前页，以便向服务端请求对应页的数据。
								"limit" : obj.limit
							// 得到每页显示的条数
							},
							success : function(response) {
								viewLiuYanBan(response);
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

function viewLiuYanBan(response){
	var data=response.result.data;
	var html="";
	for (var i=0;i<data.length;i++){
		html+="<div style='width:100%;background:white;float:left;margin-bottom:20px;line-height:30px;'>" +
				"<div style='width:97%;float:left;margin-left:10px;margin-right:10px;'><u>"
			+data[i].name+"</u>：<span style='float:right;'>"+(data[i].ls+i)+"L</span></div>" +
					"<div style='width:97%;float:left;margin-left:10px;margin-right:10px;'>"
			+data[i].content+"</div><div style='width:97%;float:left;margin-left:10px;margin-right:10px;'>"
			+timestampToTime(data[i].time)+"<span style='float:right;'>" +
					"<a href=\"javascript:zanPingLun('"+data[i].id+"')\">赞</a>" +
							"(<font id='"+data[i].id+"_zan'>"+data[i].zan+"</font>)&nbsp;&nbsp;" +
							"<a href=\"javascript:caiPingLun('"+data[i].id+"')\">踩</a>(<font id='"+data[i].id+"_cai'>"
			+data[i].cai+"</font>)</span></div></div>";
		$("#fy").css("display","");
	}
	if(data.length==0){
		html="<div style='width:100%;height:50px;line-height:50px;text-align:center;margin-bottom:20px;" +
				"background:white;'>暂无留言，快来抢个沙发吧~</div>";
		$("#fy").css("display","none");
	}
	$("#newContent").html(html);
}

function getUserCookie(){
	var name=getCookie("name");
	if (name!=null && name!=""){
		$("#yourname").val(name);
	}
}