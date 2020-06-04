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
                    <div class="deleteImageDiv" onmouseover="divMouseover(this)" onmouseout="divMouseout(this)">\
                      <img src="'+ Feng.ctxPath +'/static/img/close.png" style="position: absolute;z-index: 999;right: 0;margin-top: 10px;margin-right: 10px;" onclick="deleteImg(this)"/>\
                    </div>\
                  </div>\
                </li>';
        $("#uliImg").append(html);
    }
    var introContent = $("#introContent").text();
    $("#previewDiv").empty().append(introContent);
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
                    <div class="deleteImageDiv" onmouseover="divMouseover(this)" onmouseout="divMouseout(this)">\
                      <img src="'+ Feng.ctxPath +'/static/img/close.png" style="position: absolute;z-index: 999;right: 0;margin-top: 10px;margin-right: 10px;" onclick="deleteImg(this)"/>\
                    </div>\
                  </div>\
                </li>';
                $("#uliImg").append(html);
            }
        }
    });
}
function deleteImg(el) {
    var $li = $(el).closest('li');
    $li.remove();
}
function divMouseover(el) {
    $(el).css("opacity", "0.6");
    $(el).find('img').css("opacity", "1");
}
function divMouseout(el) {
    $(el).css("opacity", "0");
    $(el).find('img').css("opacity", "0");
}
function closePage() {
    window.history.go(-1);
}
function addSubmit() {
    if (!validate) {
        return;
    }
    var name = $("#roomName").val();
    var breakfast = $("input[name='breakfast']:checked").val();
    if (breakfast == null){
        Feng.error("请选择早餐选项！");
        return;
    }
    var broadband = $("input[name='broadband']:checked").val();
    if (broadband == null){
        Feng.error("请选择宽带选项！");
        return;
    }
    var window = $("input[name='window']:checked").val();
    if (window == null){
        Feng.error("请选择窗户选项！");
        return;
    }
    var area = $("#roomArea").val();
    if (area == ''){
        Feng.error("面积不能为空！");
        return;
    }
    if (!(/^\d+$/.test(area))) {
        Feng.error('面积请输入正数');
        return false;
    }
    var bedWidth = $("#bedWidth").val();
    if (bedWidth == ''){
        Feng.error("床宽不能为空！");
        return;
    }
    if (!(/^\d+(?=\.{0,1}\d+$|$)/.test(bedWidth))) {
        Feng.error('床宽请输入正数');
        return false;
    }
    var price = $("#roomPrice").val();
    var person = $("#peopleNum").val();
    if (person == ''){
        Feng.error("可住人数不能为空！");
        return;
    }
    if (!(/^\d+$/.test(person))) {
        Feng.error('可住人数请输入正整数');
        return false;
    }
    var canCancel = $("input[name='cancelMethods']:checked").val();
    if (canCancel == null){
        Feng.error("请选择取消方式选项！");
        return;
    }
    var imageListArr = [];
    var imageArr = $("li.addImagesItemLi");
    if (imageArr.length == 0) {
        Feng.error('商品图片至少上传一张');
        return;
    }
    for (var i = 0;i<imageArr.length;i++) {
        var tempObj = {};
        tempObj.img = $(imageArr[i]).find('input[type="hidden"]').val();
        tempObj.sequence = i+1;
        imageListArr.push(tempObj);
    }
    var imageListJsonString = JSON.stringify(imageListArr);
    var content = editor.txt.html();
    var roomId = $("#roomId").val();
    $.ajaxSetup({crossDomain: true, xhrFields: {withCredentials: true}});
    $.ajax({
        url: Feng.ctxPath +"/shop/hotel/update",
        type: 'POST',
        dataType: 'json',
        data: {roomId:roomId,name:name,breakfast:breakfast,broadband:broadband,window:window,area:area,bedWidth:bedWidth,price:price,person:person,canCancel:canCancel,imageListJsonString:imageListJsonString,content:content},
        async: false,
        success: function (data) {
            if(data.code != 200){
                Feng.error(data.message);
            }else{
                Feng.success(data.message);
                closePage();
                // window.location.href = Feng.ctxPath + '/shop/hotel/room';
            }
        }
    });
}