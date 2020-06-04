//创建富文本编辑器——wangEditor
var E = window.wangEditor;
var editor = new E('#div1', '#div2');
// 配置服务器端地址
editor.customConfig.uploadImgServer = "/eshop/file/image/upload";
editor.customConfig.showLinkImg = false;
editor.customConfig.uploadFileName = 'file';
editor.customConfig.uploadImgTimeout = 50000;
editor.customConfig.uploadImgHooks = {
    customInsert: function (insertImg, result, editor) {
        var url = result.data.path;
        insertImg(url);
    }
};
editor.customConfig.onchange = function (html) {
    $("#previewDiv").empty().append(html);
};
editor.create();

var validateFields = {
    roomName: {
        validators: {
            notEmpty: {
                message: '房间名称不能为空'
            },
            regexp: {//正则验证
                regexp: /^.{1,10}$/,
                message: '长度为1-10'
            },
        }
    },
    roomPrice: {
        validators: {
            notEmpty: {
                message: '房间价格不能为空'
            }
        }
    }
};

$(function () {
    var status = sessionStorage.getItem("status");
    var platformManage = sessionStorage.getItem("platformManage");
    if (status == 1 && platformManage == 1) {
        $(".btn-primary").show();
    } else {
        $(".btn-primary").hide();
    }
    //图片移动排序
    try{
        $("#uliImg").dragsort({
            dragSelector: "li",
            dragBetween: true ,
            dragEnd:function(){

            }
        });
    }catch(e){

    }
    var breakfast = $("#breakfast").val();
    $("input[name='breakfast']").each(function (index,item) {
        if($(item).val() == breakfast){
            $(item).attr("checked","checked");
        }
    });
    var broadband = $("#broadband").val();
    $("input[name='broadband']").each(function (index,item) {
        if($(item).val() == broadband){
            $(item).attr("checked","checked");
        }
    });
    var window = $("#window").val();
    $("input[name='window']").each(function (index,item) {
        if($(item).val() == window){
            $(item).attr("checked","checked");
        }
    });
    var canCancel = $("#canCancel").val();
    $("input[name='cancelMethods']").each(function (index,item) {
        if($(item).val() == canCancel){
            $(item).attr("checked","checked");
        }
    });
    var imageList = JSON.parse($("#imageList").text());
    for (var i = 0;i<imageList.length;i++){
        var html = '\
                <li class="addImagesItemLi">\
                  <div class="addImagesItemDiv">\
                    <input id="'+imageList[i].id+'" type="hidden" value="'+imageList[i].img+'"/>\
                    <img src="'+imageList[i].img+'" class="addImgIcon" style="" alt=""/>\
                  </div>\
                </li>';
        $("#uliImg").append(html);
    }
    var introContent = $("#introContent").text();
    $("#previewDiv").empty().append(introContent);
    $("#previewDiv").css("word-wrap","break-word");
    editor.txt.html(introContent);
    Feng.initValidator("insertRoomForm", validateFields);
});

/**
 * 验证数据是否为空
 */
var validate = function () {
    $('#insertRoomForm').data("bootstrapValidator").resetForm();
    $('#insertRoomForm').bootstrapValidator('validate');
    return $("#insertRoomForm").data('bootstrapValidator').isValid();
};

function addImg() {
    if($("#uliImg li").length >= 10){
        Feng.error('图片不能大于10张');
        return;
    }
    var file = $("#imgupimg").find("input")[0].files[0];

    var fileData = new FormData();
    fileData.append('file', file);

    $.ajaxSetup({crossDomain: true, xhrFields: {withCredentials: true}});
    $.ajax({
        url: Feng.ctxPath +"/file/image/upload",
        type: 'POST',
        dataType: 'json',
        data: fileData,
        contentType: false,
        processData: false,
        async: false,
        success: function (data) {
            if(data.code != 200){
                Feng.error(data.message);
            }else{
                Feng.success("图片上传成功");
                var html = '\
                <li class="addImagesItemLi">\
                  <div class="addImagesItemDiv">\
                    <input type="hidden" value="'+data.data.path+'"/>\
                    <img src="'+data.data.path+'" class="addImgIcon" style="" alt=""/>\
                  </div>\
                </li>';
                $("#uliImg").append(html);
            }
        }
    });
}
function closePage() {
    window.history.go(-1);
}
function shelves () {
    var index = layer.open({
        type: 1,
        title: '提示',
        area: ['600px', '450px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: '<br/><form id="changePwdForm" action="#" class="form-horizontal">\n' +
            '<div class="ibox float-e-margins">\n' +
            '<div style="padding: 15px 20px 20px 20px;"><div class="row" style="margin-top: 15px;"><div class="col-sm-12" style="color: red;">此处为系统下架，下架后商家无法自行上架。</div>' +
            '<div class="col-sm-12" style="color: red;">下架后，商家可以申请重新上架。</div>' +
            '<div><input type="radio" name="reason" value="房间描述不符合实际情况" style="margin: 10px 0 0 50px;" onclick="clickCheck()">房间描述不符合实际情况</div>' +
            '<div><input type="radio" name="reason" value="房间图片不符合实际情况" style="margin: 10px 0 0 50px;" onclick="clickCheck()">房间图片不符合实际情况</div>' +
            '<div><input type="radio" name="reason" value="房间品质不符合标准" style="margin: 10px 0 0 50px;" onclick="clickCheck()">房间品质不符合标准</div>' +
            '<div style="display: flex;margin: 10px 0 0 50px;"><input id="check" type="radio" name="reason" value="0" onclick="clickCheck()"><span style="margin-right: 10px;">其他</span><div id="other" style="display: none;"><textarea id="replyContent" maxlength="20" class="form-control" rows="2" placeholder="20字以内。" style="display: inline-block;"></textarea></div></div>' +
            '</div>' +
            '<div class="row btn-group-m-t"> <div class="col-sm-10"> <button type="button" class="btn btn-primary" onclick="confirm();" style="width: 120px;height: 34px;color: #FFFFFF;">确定下架</button> ' +
            '<button type="button" class="btn btn-danger-xb" onclick="closePop();" style="width: 120px;height: 34px;color: #FFFFFF;margin-right: 20px;">取消 </button> </div> </div></div></div></form>'
    });
    this.layerIndex = index;
}
function closePop () {
    layer.close(this.layerIndex);
}
function clickCheck () {
    if ($("#check").prop("checked")) {
        $("#other").show()
    } else {
        $("#other").hide()
    }
}
function confirm () {
    var roomId = $("#roomId").val();
    if ($("#check").prop("checked")) {
        var reason = $("#replyContent").val();
    } else {
        var reason = $("input[name='reason']:checked").val();
    }

    if (($("#replyContent").val() == '') && ($("#check").prop("checked"))){
        Feng.error("请填写原因！");
        return;
    } else if (reason == null || reason == ''){
        Feng.error("请选择原因！");
        return;
    }
    var ajax = new $ax(Feng.ctxPath + "/platform/goodscenter/offShelfRoom", function (data) {
            if(data.code != 200){
                Feng.error(data.message);
                window.location.reload();
            }else{
                Feng.success(data.message);
                closePage();
            }
            }, function (data) {
                Feng.error(data.responseJSON.message + "!");
            });
            ajax.set("roomId",roomId);
            ajax.set("reason",reason);
            ajax.start();
}