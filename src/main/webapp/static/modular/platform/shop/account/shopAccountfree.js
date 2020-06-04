var ShopListInfoDlg = {
    shopListInfoData: {},
    validateFields: {
        reason: {
            validators: {
                notEmpty: {
                    message: '描述原因不能为空'
                },
                regexp: {//正则验证
                    regexp: /^.{1,500}$/,
                    message: '长度为500字符以内'
                },
            }
        },
    }
};

ShopListInfoDlg.close = function() {
    // parent.layer.close(window.parent.ShopList.layerIndex);
    window.history.go(-1);
};
ShopListInfoDlg.validate = function () {
    $('#talentForm').data("bootstrapValidator").resetForm();
    $('#talentForm').bootstrapValidator('validate');
    return $("#talentForm").data('bootstrapValidator').isValid();
};
ShopListInfoDlg.addSubmit = function() {
    if (!ShopListInfoDlg.validate()) {
        return;
    }
    var paths = [];
    $("#uliImg li").each(function(i, e){
        paths.push($(e).find('input').val())
    });
    var ajax = new $ax(Feng.ctxPath + "/platform/shopacctoper/freeze", function (data) {
        if(data.code==200){
            Feng.success(data.message + "!");
            window.location.href=Feng.ctxPath +"/platform/shopaccount";
        }else{
            Feng.error( data.message + "!");
        }
    }, function (data) {
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });

    ajax.set({"accountId":$("#accountId").val(),"reason":$("#reason").val(),'imgs':paths.join(',')});
    ajax.start();
};
ShopListInfoDlg.unfreeze = function() {
    if (!ShopListInfoDlg.validate()) {
        return;
    }
    var paths = [];
    $("#uliImg li").each(function(i, e){
        paths.push($(e).find('input').val())
    });
    var ajax = new $ax(Feng.ctxPath + "/platform/shopacctoper/unfreeze", function (data) {
        if(data.code==200){
            Feng.success(data.message + "!");
            window.location.href=Feng.ctxPath +"/platform/shopaccount";
        }else{
            Feng.error( data.message + "!");
        }
    }, function (data) {
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });

    ajax.set({"accountId":$("#accountId").val(),"reason":$("#reason").val(),'imgs':paths.join(',')});
    ajax.start();
};
/**
 * 添加上传图片
 */
var ShopIDListInfoDlg = {};
ShopIDListInfoDlg.deleteImg=function(el) {
    var $li = $(el).closest('li');
    $li.remove();
};
ShopIDListInfoDlg.deleteImgByAjax=function(el) {
    var $li = $(el).closest('li');
    var id = $li.data('id');

    (new $ax(Feng.ctxPath + "/shopList/deleteShopImg", function(data){
        Feng.success("操作成功!");
        $li.remove();
    }, function(data){
        Feng.error("操作失败!" + data.responseJSON.message + "!");
    })).set({imgId: id}).start();

    $li.remove();
};
ShopIDListInfoDlg.mouseover=function(el) {
    $(el).css("opacity", "0.6");
    $(el).find('img').css("opacity", "1");
};
ShopIDListInfoDlg.mouseout=function(el) {
    $(el).css("opacity", "0");
    $(el).find('img').css("opacity", "0");
};
ShopIDListInfoDlg.addImg = function () {
    if($("#uliImg li").length >= 5){
        Feng.error('图片不能大于5张');
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
                console.log(data);

                var html = '\
                <li>\
                  <div class="upImgType col-sm-4" style="margin: 0 0 5px 0;padding: 0;">\
                    <input type="hidden" value="'+data.data.path+'"/>\
                    <img src="'+data.data.path+'" class="imgIcon" style="" alt=""/>\
                    <div class="div_imgtype col-sm-12" style="z-index:1;position: absolute;background:#000;padding:0px;opacity:0;" onmouseover="ShopIDListInfoDlg.mouseover(this)" onmouseout="ShopIDListInfoDlg.mouseout(this)">\
                      <img src="'+ Feng.ctxPath +'/static/img/close.png" style="position: absolute;z-index: 999;right: 0;margin-top: 10px;margin-right: 10px;" onclick="ShopIDListInfoDlg.deleteImg(this)"/>\
                    </div>\
                  </div>\
                </li>';
                $("#uliImg").append(html);
            }
        }
    });
};
ShopIDListInfoDlg.editAddImg = function () {
    if($("#uliImg li").length >= 5){
        Feng.error('图片不能大于5张');
        return;
    }
    var file = $("#imgupimg").find("input")[0].files[0];

    var fileData = new FormData();
    fileData.append('ImgFile', file);
    fileData.append('shopId', $('#shopId').val());

    $.ajaxSetup({crossDomain: true, xhrFields: {withCredentials: true}});
    $.ajax({
        url: Feng.ctxPath +"/shopList/addShopImg",
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
                console.log(data);

                var html = '\
                <li data-id="'+data.data.id+'">\
                  <div class="upImgType col-sm-4" style="margin: 0 0 5px 0;padding: 0;">\
                    <input type="hidden" value="'+data.data.path+'"/>\
                    <img src="'+data.data.path+'" class="imgIcon" style="" alt=""/>\
                    <div class="div_imgtype col-sm-12" style="position: absolute;background:#000;padding:0px;opacity:0;" onmouseover="ShopIDListInfoDlg.mouseover(this)" onmouseout="ShopIDListInfoDlg.mouseout(this)">\
                      <img src="'+ Feng.ctxPath +'/static/img/close.png" style="position: absolute;z-index: 999;right: 0;margin-top: 10px;margin-right: 10px;" onclick="ShopIDListInfoDlg.deleteImgByAjax(this)"/>\
                    </div>\
                  </div>\
                </li>';
                $("#uliImg").append(html);
            }
        }
    });
};
function hovershow2(id) {
    var imgurl = $("#img_" + id).attr('src');
    if (imgurl != "" && imgurl != null) {
        $("#img_div_" + id).hover(function () {
            $("#div_imgtype_" + id).css("background-color", "#000");
            $("#div_imgtype_" + id).css("opacity", "0.6");
            $("#div_imgtype_" + id).find("div:eq(1)").css("color", "#fff");
            $("#div_imgtype_" + id).find("div:eq(2)").css("color", "#fff");
            $("#div_imgtype_" + id).find("div:eq(3)").css("color", "#fff");

        }, function () {
            $("#div_imgtype_" + id).css("opacity", "0");
        });
    }
}
$(function() {
    Feng.initValidator("talentForm", ShopListInfoDlg.validateFields);

});