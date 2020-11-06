var layedit;
var index;//富文本编辑器build返回值
var imgUrl = "";//文章配图路径
var form;//表单
var curType = "";//当前是编辑文章还是写文章
var response;

window.onload = function () {
    layui.use(['layedit', 'upload', 'form'], function () {
        form = layui.form;
        getTextType();
        getTag();
        initForm();
        addData();
        layedit = layui.layedit;
        //注意：layedit.set 一定要放在 build 前面，否则配置全局接口将无效。
        layedit.set({
            uploadImage: {
                url: "textController.do?method=uploadImg", //接口url
                type: 'post' //默认post
            }
        });
        index = layedit.build('policy_content', {
            height: "500"
        }); //建立编辑器

        var upload = layui.upload;
        //执行实例
        upload.render({
            elem: "#imageXs", //绑定元素
            url: "textController.do?method=uploadImg", //上传接口
            type: "post",
            done: function (res) {
                //上传完毕回调
                if (res.code == 0) {//上传成功
                    layer.msg('图片上传成功', {icon: 1});
                    document.getElementById("imageXs").src = res.data.src;
                    imgUrl = res.data.src;
                } else {//上传失败
                    layer.msg('图片上传失败!', {icon: 5});
                }
            },
            error: function () {
                //请求异常回调
                layer.msg('图片上传失败!', {icon: 5});
            }
        });
    });
    //每五分钟自动保存一下
    window.setInterval("autoSave()",5*60*1000);
};

function autoSave(){
	var time = getNowFormatDate();
	if ($("#textType").val()==null || $("#textType").val()==""){
		$("#autoSave").html("文章类型不能为空！"+time);
		return false;
	}
    var iftop = 0;
    if ($("#dingzhi").is(":checked")) {
        iftop = 1;
    }
    var ifcarousel = 0;
    if ($("#lunbo").is(":checked")) {
        ifcarousel = 1;
    }
    if (ifcarousel==1 && (imgUrl==null || imgUrl=="")){
    	$("#autoSave").html("轮播文章必须有配图！"+time);
		return false;
    }
    if (curType == "insert") {//添加文章
    	autoInsertText(iftop, ifcarousel);
    } else if (curType == "update") {//编辑文章
    	autoUpdateText(iftop, ifcarousel);
    }
}


//自动添加文章
function autoInsertText(iftop, ifcarousel) {
  $.ajax({
      url: "textController.do?method=insertText",
      dataType: "json",
      type: "post",
      data: {
          "title": $("#title").val(),
          "introduce": $("#introduce").val(),
          "image": imgUrl,
          "textType": $("#textType").val(),
          "iftop": iftop,
          "ifcarousel": ifcarousel,
          "content": layedit.getContent(index),
          "tag":$("#tag").val()
      },
      success: function (res) {
			var time = getNowFormatDate();
			response=res.result;
			if (res.errorCode == "0") {
				$("#autoSave").html("上次新增文章时间，"+time);
				curType = "update";
			} else {
				$("#autoSave").html("保存失败!"+time);
			}
      },
      error: function (error) {
      }
  });
}

//自动编辑文章
function autoUpdateText(iftop, ifcarousel) {
  $.ajax({
      url: "textController.do?method=updateText",
      dataType: "json",
      type: "post",
      data: {
          "id": response.id,
          "title": $("#title").val(),
          "introduce": $("#introduce").val(),
          "image": imgUrl,
          "textType": $("#textType").val(),
          "iftop": iftop,
          "ifcarousel": ifcarousel,
          "content": layedit.getContent(index),
          "tag":$("#tag").val()
      },
      success: function (res) {
			var time = getNowFormatDate();
			if (res.errorCode == "0") {
				$("#autoSave").html("上次保存时间，"+time);
			} else {
				$("#autoSave").html("保存失败!"+time);
			}
      },
      error: function (error) {
      }
  });
}


function initForm() {
    //自定义表单验证
    form.verify({
        title: function (value) {
            if (value == null || value == "") {
                return '标题不能为空!';
            }
            if (value.length > 100) {
                return '标题长度不能超过100个字符!';
            }
        },
        introduce: function (value) {
            if (value == null || value == "") {
                return '简介不能为空!';
            }
            if (value.length > 200) {
                return '简介不能超过200个字符!';
            }
        },
        policy_content: function (value) {
            value = layedit.getContent(index);
            if (value.length > 200000) {
                return '内容过长!';
            }
        }
    });
    //提交表单
    form.on('submit(submitTitle)', function (data) {
    	if ($("#textType").val()==null || $("#textType").val()==""){
    		layer.msg('文章类型不能为空', {icon: 5});
    		return false;
    	}
        var iftop = 0;
        if ($("#dingzhi").is(":checked")) {
            iftop = 1;
        }
        var ifcarousel = 0;
        if ($("#lunbo").is(":checked")) {
            ifcarousel = 1;
        }
        if (ifcarousel==1 && (imgUrl==null || imgUrl=="")){
        	layer.msg('轮播文章必须有配图', {icon: 5});
    		return false;
        }
        if (curType == "insert") {//添加文章
            insertText(iftop, ifcarousel);
        } else if (curType == "update") {//编辑文章
            updateText(iftop, ifcarousel);
        }
        return false;
    });
}

//添加文章
function insertText(iftop, ifcarousel) {
    $.ajax({
        url: "textController.do?method=insertText",
        dataType: "json",
        type: "post",
        data: {
            "title": $("#title").val(),
            "introduce": $("#introduce").val(),
            "image": imgUrl,
            "textType": $("#textType").val(),
            "iftop": iftop,
            "ifcarousel": ifcarousel,
            "content": layedit.getContent(index),
            "tag":$("#tag").val()
        },
        success: function (res) {
			if (res.errorCode == "0") {
				parent.caozuoSuccess("insert");
			} else {
				layer.msg('添加失败!', {icon: 5});
			}
        },
        error: function (error) {
        }
    });
}

//编辑文章
function updateText(iftop, ifcarousel) {
    $.ajax({
        url: "textController.do?method=updateText",
        dataType: "json",
        type: "post",
        data: {
            "id": response.id,
            "title": $("#title").val(),
            "introduce": $("#introduce").val(),
            "image": imgUrl,
            "textType": $("#textType").val(),
            "iftop": iftop,
            "ifcarousel": ifcarousel,
            "content": layedit.getContent(index),
            "tag":$("#tag").val()
        },
        success: function (res) {
			if (res.errorCode == "0") {
				parent.caozuoSuccess("update");
			} else {
				layer.msg('修改失败!', {icon: 5});
			}
        },
        error: function (error) {
        }
    });
}

//获取文章类型
function getTextType() {
    $.ajax({
        url: "textController.do?method=getTextType",
        dataType: "json",
        data: {
        },
        success: function (res) {
            var html = "<option value=''></option>";
            for (var i = 0; i < res.result.length; i++) {
                html += "<option value='" + res.result[i].id + "'>" + res.result[i].name + "</option>";
            }
            document.getElementById("textType").innerHTML = html;
            if (response != null && response != "") {
                if (response.textType != "") {
                    //编辑文章时，默认选择下拉
                    $("#textType").val(response.textType);
                }
            }
            form.render('select');
        },
        error: function (error) {
        }
    });
}

//获取文章标签
function getTag(){
    $.ajax({
        url: "textController.do?method=getTag",
        dataType: "json",
        data: {
        },
        success: function (res) {
            var html = "<option value=''></option>";
            for (var i = 0; i < res.result.length; i++) {
                html += "<option value='" + res.result[i].id + "'>" + res.result[i].name + "</option>";
            }
            document.getElementById("tag").innerHTML = html;
            if (response != null && response != "") {
                if (response.tag != "") {
                    //编辑文章时，默认选择下拉
                    $("#tag").val(response.tag);
                }
            }
            form.render('select');
        },
        error: function (error) {
        }
    });
}

function closeParentFrame() {
    var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
    parent.layer.close(index);
}

//更改当前类型，是添加文章还是编辑文章。
function changeType(data) {
    curType = data;
}

//编辑文章时，父页面传值到子页面
function addTextData(data) {
    response = data;
}

//添加编辑文章的内容
function addData() {
    if (response != null && response != "") {
        $("#title").val(response.title);
        $("#introduce").val(response.introduce);
        if (response.iftop == 1) {
            document.getElementById("dingzhi").checked = true;
        }
        if (response.ifcarousel == 1) {
            document.getElementById("lunbo").checked = true;
        }
        if (response.image != null && response.image != "") {
            document.getElementById("imageXs").src = response.image;
            imgUrl = response.image;
        }
        $("#policy_content").val(response.content);
        //重新渲染
        form.render();
    }
}

function getNowFormatDate() {
    var date = new Date();
    var seperator1 = "-";
    var seperator2 = ":";
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
            + " " + date.getHours() + seperator2 + date.getMinutes()
            + seperator2 + date.getSeconds();
    return currentdate;
}