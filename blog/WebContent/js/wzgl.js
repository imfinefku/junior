var form;//表单
var table;//表格

window.onload = function() {
	initLayui();
};

// 初始化layui
function initLayui() {
	layui.use([ 'layer', 'table' ], function() {
		table = layui.table;
		form = layui.form;
		initTable();
	});
}

function initTable() {
    table.render({
        elem: "#textGrid",
        url: "textController.do?method=getTextFy",
        height: "full",
        toolbar: '#toolbarDemo',
        id: "textGrid",
		parseData : function(res) { // res 即为原始返回的数据
			return {
				"data" : res.result.data,
				"code" : res.errorCode,
				"count" : res.result.count,
				"code" : res.errorCode
			};
		},
        cols: [[
                {field: 'title', title: '标题', width: 0},
                {field: 'titlename', title: '文章类型', width: 0},
                {field: 'biaoqianname', title: '标签', width: 0},
                {field: 'ifcarousel', title: '轮播', width:0,
                    templet: function (d) {
                        if (d.ifcarousel == 1) {
                            return "<font style='color:green;'>是</font>";
                        } else {
                            return "<font style='color:red;'>否</font>";
                        }
                    }},
                {field: 'iftop', title: '顶置', width: 0,
                    templet: function (d) {
                        if (d.iftop == 1) {
                            return "<font style='color:green;'>是</font>";
                        } else {
                            return "<font style='color:red;'>否</font>";
                        }
                    }},
                {field: 'dianzan', title: '点赞数', width: 0},
                {field: 'dianji', title: '点击量', width: 0},
                {field: 'insertTime', title: '添加时间', width: 0,
                    templet: function (d) {
                        return timestampToTime(d.insertTime);
                    }
                },
                {field: '', title: '操作', toolbar: '#barDemo', width: 0}
            ]],
        page: true,
        limit: 10, //分页，10条数据一页
        done: function (res) {
            //获取数据后操作
        }
    });
    //绑定头部事件
    table.on('toolbar(textGrid)', function (obj) {
        switch (obj.event) {
            case 'insert':
                writeText();
                break;
        }
        ;
    });
    //绑定行事件
    table.on('tool(textGrid)', function (obj) {
        var data = obj.data;
        if (obj.event == 'delete') {
            layer.confirm('确定要删除该文章?', function (index) {
                deleteText(data.id);
                layer.close(index);
            });
        } else if (obj.event == 'update') {
            getTextById(data.id);
        }
    });
}


function getTextById(id) {
    $.ajax({
        url: "textController.do?method=getTextById",
        dataType: "json",
        data: {
            "id": id
        },
        success: function (response) {
            layer.open({
                type: 2,
                title: "编辑文章",
                shadeClose: true,
                shade: false,
                area: ['100%', '100%'],
                content: "manage/writeText.jsp",
                success: function (layero, index) {
                    // 获取子页面的iframe
                    var iframe = window['layui-layer-iframe' + index];
                    // 向子页面的全局函数child传参
                    iframe.changeType("update");
                    iframe.addTextData(response.result);
                }
            });
        },
        error: function (error) {
        }
    });
}

function writeText() {
    layer.open({
        type: 2,
        title: "写文章",
        shadeClose: true,
        shade: false,
        area: ['100%', '100%'],
        content: "manage/writeText.jsp",
        success: function (layero, index) {
            // 获取子页面的iframe
            var iframe = window['layui-layer-iframe' + index];
            // 向子页面的全局函数child传参
            iframe.changeType("insert");
        }
    });
}

function tableReload(type) {
    if (type == "cur") {
        table.reload('textGrid', {
            url: "textController.do?method=getTextFy",
            where: {} //
        });
    } else {
        table.reload('textGrid', {
            url: "textController.do?method=getTextFy",
            where: {} //
            //,height: 300
            , page: {
                curr: 1 //从第一页开始
            }
        });
    }
}

//子页面写文章成功后调用该方法
function caozuoSuccess(data) {
    layer.closeAll();
    if (data == "insert") {
        tableReload("");
        layer.msg('添加成功', {icon: 1});
    } else if (data == "update") {
        tableReload("cur");
        layer.msg('修改成功', {icon: 1});
    }
}

function deleteText(id) {
    $.ajax({
        url: "textController.do?method=deleteText",
        dataType: "text",
        data: {
            id: id
        },
        success: function (response) {
            tableReload("");
        },
        error: function (error) {
        }
    });
}