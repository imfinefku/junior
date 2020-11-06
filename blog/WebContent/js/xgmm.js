window.onload=function(){
	initLayui();
};

//初始化layui
function initLayui() {
	layui.use(['layer'], function() {
	});
}

function updatePassword(){
	if ($("#oldPassword").val()==null || $("#oldPassword").val()==""){
		layer.msg('当前密码不能为空!', {icon: 5});
		return;
    }
	if ($("#newPassword").val()==null || $("#newPassword").val()==""){
		layer.msg('新密码不能为空!', {icon: 5});
		return;
    }
	if ($("#renewPassword").val()==null || $("#renewPassword").val()==""){
		layer.msg('确认新密码不能为空!', {icon: 5});
		return;
    }
	sendUpdate();
}

function sendUpdate (){
	$.ajax({
		url:"login_sso.do?method=updatePassword",
		dataType:"json",
		data:{
			"oldPassword":$("#oldPassword").val(),
			"newPassword":$("#newPassword").val(),
			"renewPassword":$("#renewPassword").val()
		},
		success:function(response){
			if (response.errorCode=="0"){
				layer.msg('修改成功', {icon: 1});
			}else{
				layer.msg(response.errorMsg, {icon: 5});
			}
		},
		error:function(error){
		}
	});
}