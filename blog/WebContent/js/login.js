var typeFlag = 0;
var messageFlag = 0;
var descriptionFlag = 0;

function typeClick() {
    typeFlag++;
}

function messageClick() {
    messageFlag++;
}

function descriptionClick() {
    descriptionFlag++;
    if (typeFlag >= 5 && messageFlag >= 5 && descriptionFlag >= 5) {
        var html = "<input id=\"username\"/><br><input id=\"password\" type=\"password\" /><br><button onclick='login()'>go</button>";
        $("#lo").html(html);
    } else {
        $("#lo").html("");
    }
}

function login() {
	$.ajax({
		url : "login_sso.do?method=login",
		dataType : "text",
		data : {
			"username" : $("#username").val(),
			"password" : $("#password").val()
		},
		success : function(response) {
			if (response == "false") {
				 $("#lo").html("");
			} else {
				window.location = "manage/managerIndex.jsp";
			}
		},
		error : function(error) {
		}
	});
}