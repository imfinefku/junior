<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.base.init.Constant"%>
<div style="width: 100%; height: 50px; background: black;">
	<div id="titleList" style="width: 1160px; height: 100%; margin: 0 auto; color: white; line-height: 45px; font-size: 15px;">
		<button style="width: 50px; height: 30px; float: right; 
			margin-top: 10px; cursor: pointer; border-radius: 5px; border: 0;">搜索</button>
		<input placeholder="输入关键词查找" type="text"
			style="padding:0 5px 0 5px; width: 150px; height: 30px; margin-right: 10px; float: right; margin-top: 10px; border-radius: 5px; border: 0;" />
	</div>
</div>
<script>
	function initDh() {
        var dat = JSON.stringify(<%=Constant.titleList%>);
        if (dat != null && dat != "") {
            initDhData(JSON.parse(dat));
        } else {
            getTitleData();
        }
	}
    function getTitleData() {
        $.ajax({
            url: "login_sso.do?method=getTitleList",
            dataType: "json",
            data: {
            },
            success: function (response) {
                initDhData(response.result);
            },
            error: function (error) {
            }
        });
    }

    function initDhData(response) {
		var titleList="<label style=\"font-size: 20px; cursor: pointer;\" onclick='tiaoZhuan(\"index.jsp\",\"\")'>"
		+"脱北者博客</label><label class=\"top\" onclick='tiaoZhuan(\"index.jsp\",\"\")'>首页</label>";
		for (var i=0;i<response.length;i++){
			titleList+="<label id='"+response[i].id+"' class=\"top\" onclick='tiaoZhuan(\""+response[i].path+"\",\""+response[i].id+"\")'>"
			+response[i].name+"</label>";
		}
		titleList+="<label class=\"top\" onclick='tiaoZhuan(\"page/ly.jsp\",\"\")'>留言</label>"
		+"<label class=\"top\" onclick='tiaoZhuan(\"page/abt.jsp\",\"\")'>关于</label>"
		+"<button onclick=\"search()\" style=\"width: 50px; height: 30px; float: right; margin-top: 10px; cursor: pointer; border-radius: 5px; border: 0;\">搜索</button>"
		+"<input id='content' placeholder='输入关键词查找'  type=\"text\""
		+" style=\"padding:0 5px 0 5px; width: 150px; height: 30px; margin-right: 10px; float: right; margin-top: 10px; border-radius: 5px; border: 0;\" />";
		$("#titleList").html(titleList);
    }
    
	function tiaoZhuan(type,textType){
		if(textType!=""){
			window.location.href=type+"?textType="+textType;
		}else{
			window.location.href=type;
		}
	}

	function searchByContent(content){
		window.open("page/search.jsp?content="+content);
	}

	function search(){
		var content=$("#content").val();
		if (content!=null){
			content=$("#content").val().replace(/\s*/g,"");
			if (content!=""){
				searchByContent(content);
			}
		}
	}
</script>