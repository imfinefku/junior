window.onload=function(){
	//设置最小高度
	minHeight();
	initLayui();
	initDh();
	//根据id获取文章
	getTextById($("#textId").val());
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
	//百度推送链接
	pushBaidu();
};

//百度推送链接
function pushBaidu(){
    var bp = document.createElement('script');
    var curProtocol = window.location.protocol.split(':')[0];
    if (curProtocol === 'https') {
        bp.src = 'https://zz.bdstatic.com/linksubmit/push.js';
    }
    else {
        bp.src = 'http://push.zhanzhang.baidu.com/push.js';
    }
    var s = document.getElementsByTagName("script")[0];
    s.parentNode.insertBefore(bp, s);
}

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

function getTextById(id){
	$.ajax({
		url:"view_sso.do?method=getTextById",
		dataType:"json",
		data:{
			"id":id
		},
		success:function(response){
			if (response.errorCode=="0"){
				document.title=response.result.text.title+"-脱北者博客";
				var html="";
				var wztitle=response.result.text.title;
				if (wztitle.length>30){
					wztitle=wztitle.substring(0,30)+"...";
				}
				html+="<span style='margin-left:2%;margin-top:10px;float:left;width:96%;" +
						"border-bottom:1px solid black;'>当前位置：首页 >> "+response.result.text.titlename+" >> "+wztitle+"</span>";
				html+="<span style='margin-left:2%;margin-top:10px;float:left;width:96%;font-weight: bold;color: #555;font-size:18px;'>"
					+response.result.text.title+"</span>";
				html+="<span style='margin-left:2%;margin-top:10px;float:left;width:96%;color:#999;'>"
					+"时间："+timestampToTime3(response.result.text.inserttime)+"<font style='margin-left:40px;'>分类：</font>"
					+response.result.text.tagname+"<font style='margin-left:40px;'>浏览：</font>("+response.result.text.dianji+")"+"</span>";
				html+="<span style='margin-left:2%;margin-top:10px;padding-left:1%;padding-right:1%;" +
						"float:left;width:94%;background:#F6F6F6;color:#888888;'><b>简介：</b>"
					+response.result.text.introduce+"</span>";
				html+="<span style='margin-left:2%;margin-top:10px;float:left;width:96%;margin-bottom:20px;'>"
					+response.result.text.content+"</span>";
				$("#newContent").html(html);
				var lastText="上一篇：没有了";
				if (response.result.lastAndNext.last!=null){
					var lastTitle=response.result.lastAndNext.last.title;
					if (lastTitle.length>30){
						lastTitle=lastTitle.substring(0,40)+"...";
					}
					lastText="<a href=\"javascript:turnToNextOrLast('"
						+response.result.lastAndNext.last.id+"')\">上一篇："+lastTitle+"</a>";
				}
				var nextText="下一篇：没有了";
				if (response.result.lastAndNext.next!=null){
					var nextTitle=response.result.lastAndNext.next.title;
					if (nextTitle.length>30){
						nextTitle=nextTitle.substring(0,40)+"...";
					}
					nextText="<a href=\"javascript:turnToNextOrLast('"
						+response.result.lastAndNext.next.id+"')\">下一篇："+nextTitle+"</a>";
				}
				$("#lastText").html(lastText);
				$("#nextText").html(nextText);
			}
		},
		error:function(error){
		}
	});
}

function turnToNextOrLast(id){
	window.location.href="page/text.jsp?id="+id;
}

function getLy(){
	$.ajax({
		url : "view_sso.do?method=getTextLyFy",
		dataType : "json",
		data : {
			"cur" : 1,
			"limit" : onePageCount,
			"id":$("#textId").val()
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
							url : "view_sso.do?method=getTextLyFy",
							dataType : "json",
							data : {
								"cur" : obj.curr, // 得到当前页，以便向服务端请求对应页的数据。
								"limit" : obj.limit,// 得到每页显示的条数
								"id":$("#textId").val()
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
	$("#lyContent").html(html);
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
			"lytype":"1",
			"type":0,
			"sstext":$("#textId").val(),
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

function getUserCookie(){
	var name=getCookie("name");
	if (name!=null && name!=""){
		$("#yourname").val(name);
	}
}