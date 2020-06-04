var ShopListInfoDlg = {
    shopListInfoData: {},
    validateFields: {
        account: {
            validators: {
                notEmpty: {
                    message: '账户名不能为空'
                },
                regexp: {//正则验证
                    regexp: /^[0-9a-zA-Z]{2,15}$/,
                    message: '长度为2-15字符以内,允许包含字母、数字、不包含空格'
                },
            }
        },
    }
};
ShopListInfoDlg.validate = function () {
    $('#talentForm').data("bootstrapValidator").resetForm();
    $('#talentForm').bootstrapValidator('validate');
    return $("#talentForm").data('bootstrapValidator').isValid();
};
ShopListInfoDlg.close = function() {
    // parent.layer.close(window.parent.ShopList.layerIndex);
    window.history.go(-1);
};
ShopListInfoDlg.addSubmit = function() {
    if (!ShopListInfoDlg.validate()) {
        return;
    }
    var ajax = new $ax(Feng.ctxPath + "/platform/shopaccount/openAccount", function (data) {
        if(data.code==200){
            Feng.success(data.message + "!");
            window.location.href=Feng.ctxPath +"/platform/shopaccount";
        }else{
            Feng.error( data.message + "!");
        }
    }, function (data) {
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set({"shopId":$("#shopId").val(),"account":$("#account").val()});
    ajax.start();
};

$(function() {
    Feng.initValidator("talentForm", ShopListInfoDlg.validateFields);
    var vshopId=getUrlParam('shopId');
    var ajax1 = new $ax(Feng.ctxPath + "/platform/shopaccount/listNoAccountShops", function(res){
        $("#shopId").empty();
        vshopId=vshopId?vshopId:res.data[0].id;
        $.each(res.data,function (k,v) {
            var selhtml='';
            if(vshopId==v.id){
                selhtml='selected="selected"';
                vshopId=v.id;
            }
            $("#shopId").append('<option value="'+v.id+'" '+selhtml+'>'+v.name+'</option>');
        })
    },function(data){
        Feng.error(  data.responseJSON.message + "!");
    });
    ajax1.start();

    getdetail(vshopId);

    $("#shopId").change(function () {
        getdetail($(this).val());
    });

    function getUrlParam(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
        var r = window.location.search.substr(1).match(reg);  //匹配目标参数
        if (r != null) return unescape(r[2]); return null; //返回参数值
    }
    function getdetail(shopid) {
        var ajax2 = new $ax(Feng.ctxPath + "/platform/shopaccount/getShopInfoById", function(res){
            $.each(res.data,function (k,v) {
                $("#"+k).text(v);
            })
        },function(data){
            Feng.error(  data.responseJSON.message + "!");
        });
        ajax2.set({'shopId':shopid});
        ajax2.start();
    }
});