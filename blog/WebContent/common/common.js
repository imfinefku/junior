var noList=new Array();//本次点赞过的列表

function common_viewData(response) {
	var data = response.result.data;
	var html = "";
	for (var i = 0; i < data.length; i++) {
		var title=data[i].title;
		if (title.length>36){
			title=title.substring(0,36)+"...";
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
		html += "<div style='width:"+contentWidth+"%;height:100%;float:left;line-height:25px;color:#888;'>" +
				"<font onclick=\"textXx('"+ data[i].id+ "')\" style='cursor: pointer;'>"
				+ content + "</font></div></div></div>";
	}
	if (data.length==0){
		$("#fy").css("display","none");
	}
	$("#newContent").html(html);
}

function common_labelView(){
	$.ajax({
		url:"view_sso.do?method=getLabelCount",
		dataType:"json",
		data:{
		},
		success:function(response){
			if (response.errorCode=="0" && response.result.length>0){
				var html="";
				for (var i=0;i<response.result.length;i++){
					html+="<div onclick=\"searchByTag('"+response.result[i].id+"')\" " +
							"style='min-width:30px;height:30px;line-height:30px;" +
							"cursor: pointer;margin-right:10px;margin-left:10px;" +
							"margin-bottom:15px;" +
							"float:left;background:#808080;color:white;border-radius:5px;'>&nbsp;"+
							response.result[i].name+"("+response.result[i].sum+")&nbsp;</div>";
				}
				$("#label_content").html(html);
			}else{//隐藏标签栏
				$("#label").css("display","none");
			}
		},
		error:function(error){
		}
	})
}

function common_friendView(){
	$.ajax({
		url:"view_sso.do?method=getYlData",
		dataType:"json",
		data:{
		},
		success:function(response){
			if (response.errorCode=="0" && response.result.length>0){
				var html="";
				for (var i=0;i<response.result.length;i++){
					html+="<a target='_blank' href=\"javascript:yltz('"+response.result[i].url+"')\" style='min-width:30px;" +
							"height:30px;line-height:30px;margin-right:10px;margin-left:10px;" +
							"margin-bottom:15px;" +
							"float:left;background:#808080;color:white;border-radius:5px;cursor: pointer;'>&nbsp;"+
							response.result[i].name+"&nbsp;</a>";
				}
				$("#yqlj_content").html(html);
			}else{//隐藏友链栏
				$("#yqlj").css("display","none");
			}
		},
		error:function(error){
		}
	})
}

function yltz(url){
	window.open(url);
}

function minHeight(){
	$(".content").css("min-height", document.body.clientHeight - 50);
	$("#common_bottom").css("display","");
}

function textXx(id){
	window.open("page/text.jsp?id="+id);
}

function searchByTag(tag){
	window.open("page/search.jsp?tag="+tag);
}

function common_djphView(){
	$.ajax({
		url:"view_sso.do?method=getDjphData",
		dataType:"json",
		data:{
		},
		success:function(response){
			if (response.errorCode=="0" && response.result.length>0){
				var html="";
				for (var i=0;i<response.result.length;i++){
					var color="#8eb9f5";
					var titlename=response.result[i].title;
					if (titlename.length>40){
						titlename=titlename.substring(0,40)+"...";
					}
					if (i==0){
						color="#f54545";
					}
					if (i==1){
						color="#ff8547";
					}
					if (i==2){
						color="#ffac38";
					}
					html+="<div style='width:94%;float:left;margin-left:3%;margin-right:3%;line-height:25px;" +
							"background:#E9EAED;margin-bottom:15px;cursor: pointer;' " +
							"onclick=\"textXx('"+response.result[i].id+"')\">" +
							"<span style='text-align:center;" +
							"width:25px;background:"+color+";float:left;color:white;margin-right:5px;'>"
						+(i+1)+"</span>"+titlename+"</div>";
				}
				$("#djph_content").html(html);
			}else{
				$("#djph").css("display","none");
			}
		},
		error:function(error){
		}
	});
}

//写cookie
function setCookie(name,value) { 
    var Days = 30; 
    var exp = new Date(); 
    exp.setTime(exp.getTime() + Days*24*60*60*1000); 
    document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString(); 
}

//读取cookie
function getCookie(name) { 
    var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
    if(arr=document.cookie.match(reg)){
    	return unescape(arr[2]); 
    }
    return null; 
}

//删除cookie
function delCookie(name) {
    var exp = new Date(); 
    exp.setTime(exp.getTime() - 1); 
    var cval=getCookie(name); 
    if(cval!=null) {
    	document.cookie= name + "="+cval+";expires="+exp.toGMTString(); 
    }
}

//赞评论
function zanPingLun(id){
	var ifZan=true;
	for (var i=0;i<noList.length;i++){
		if (id==noList[i]){
			ifZan=false;
		}
	}
	if (ifZan){
		$.ajax({
			url:"view_sso.do?method=zanPingLun",
			dataType:"json",
			data:{
				"id":id
			},
			success:function(response){
				if (response.errorCode=="0"){
					$("#"+id+"_zan").html(parseInt($("#"+id+"_zan").html())+1);
					layer.msg('感谢您的参与', {icon: 1});
					noList.push(id);
				}
			},
			error:function(error){
			}
		});
	}else{
		layer.msg("您已点评过该留言", {
			icon : 5
		});
	}
}

//踩评论
function caiPingLun(id){
	var ifCai=true;
	for (var i=0;i<noList.length;i++){
		if (id==noList[i]){
			ifCai=false;
		}
	}
	if (ifCai){
		$.ajax({
			url:"view_sso.do?method=caiPingLun",
			dataType:"json",
			data:{
				"id":id
			},
			success:function(response){
				if (response.errorCode=="0"){
					$("#"+id+"_cai").html(parseInt($("#"+id+"_cai").html())+1);
					layer.msg('感谢您的参与', {icon: 1});
					noList.push(id);
				}
			},
			error:function(error){
			}
		});
	}else{
		layer.msg("您已点评过该留言", {
			icon : 5
		});
	}
}

function common_LiuYanView(){
	$.ajax({
		url:"view_sso.do?method=getNewLy",
		dataType:"json",
		data:{
		},
		success:function(response){
			if (response.errorCode=="0" && response.result.length>0){
				var html="";
				for (var i=0;i<response.result.length;i++){
					var content=response.result[i].content;
					html+="<div style='width:94%;float:left;margin-left:3%;margin-right:3%;line-height:25px;" +
							"background:#E9EAED;margin-bottom:15px;cursor: pointer;'>" +
							"<font style='margin-left:10px;'><u>"+response.result[i].name+"</u>：</font>" +
							"<font style='float:right;margin-right:10px;'>"
							+timestampToTime(response.result[i].time)+"</font><br>" +
							"<div style='margin-left:10px;'>"+content+"</div></div>";
				}
				$("#newpl_content").html(html);
			}else{
				$("#newpl").css("display","none");
			}
		},
		error:function(error){
		}
	});
}
