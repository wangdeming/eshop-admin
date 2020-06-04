var validateFields = {
    phoneNumber_old: {
        validators: {
            notEmpty: {
                message: '手机号码不能为空'
            },
            regexp: {//正则验证
                regexp: /^1[3567894]\d{9}$/,
                message: '手机号格式不对'
            },
        }
    },
    phoneNumber_new: {
        validators: {
            notEmpty: {
                message: '手机号码不能为空'
            },
            regexp: {//正则验证
                regexp: /^1[3567894]\d{9}$/,
                message: '手机号格式不对'
            },
        }
    }
};

$(function () {
    var phone = parent.$("#phoneNum").text();
    if(phone != ""){
        $("#image1").attr("src",Feng.ctxPath + '/static/img/password_icon_pre.png');
        $("#image2").attr("src",Feng.ctxPath + '/static/img/password_icon_nor.png');
        $("#image3").attr("src",Feng.ctxPath + '/static/img/password_icon_nor.png');
        $("#phoneNumber_old").val(phone);
    }else {
        $("#image1").attr("src",Feng.ctxPath + '/static/img/password_icon_nor.png');
        $("#image2").attr("src",Feng.ctxPath + '/static/img/password_icon_pre.png');
        $("#image3").attr("src",Feng.ctxPath + '/static/img/password_icon_nor.png');
        $("#step1").css("color","#999999");
        $("#step2").css("color","#FF7D32");
        $("#modifyPhone1").css("display","none");
        $("#modifyPhone2").css("display","block");
    }
    Feng.initValidator("changePhoneForm", validateFields);

});
/**
 * 验证数据是否为空
 */
var validate = function () {
    $('#changePhoneForm').data("bootstrapValidator").resetForm();
    $('#changePhoneForm').bootstrapValidator('validate');
    return $("#changePhoneForm").data('bootstrapValidator').isValid();
};
function getVerifyCode(event, index) {
    if (!validate) {
        return;
    }
    if(!$(event.target).hasClass('not-send')){
        return;
    }
    var phone;
    if(index === 1){
        phone = $("#phoneNumber_old").val();
        if(phone != parent.$("#phoneNum").text()){
            Feng.error('手机号码与原手机号不符!');
            return;
        }
    }else{
        phone = $("#phoneNumber_new").val();
    }
    $.ajax({
        url: Feng.ctxPath +"/shop/account/sendsms",
        type: 'post',
        dataType: 'json',
        async: false,
        data: {phone:phone},
        success: function (data) {
            if(data.code == 200){
                Feng.success(data.message);
                var timeOut = 60;
                $(event.target).removeClass('not-send').text(--timeOut);
                var interval = setInterval(function(){
                    $(event.target).text(--timeOut);
                    if(timeOut == 0){
                        clearInterval(interval);
                        $(event.target).addClass('not-send').text('发送');
                    }
                }, 1000);
            }else {
                Feng.error(data.message);
            }
        }
    });
}

function next(num) {
    switch (num) {
        case 1:
            var verifycode = $("#verifyCode_old").val();
            $.ajax({
                url: Feng.ctxPath +"/shop/account/verifycode",
                type: 'post',
                dataType: 'json',
                async: false,
                data: {verifycode:verifycode},
                success: function (data) {
                    if(data.code == 200){
                        Feng.success(data.message);
                        $("#image1").attr("src",Feng.ctxPath + '/static/img/password_icon_nor.png');
                        $("#image2").attr("src",Feng.ctxPath + '/static/img/password_icon_pre.png');
                        $("#modifyPhone1").css("display","none");
                        $("#modifyPhone2").css("display","block");
                        $("#step1").css("color","#999999");
                        $("#step2").css("color","#FF7D32");
                    }else {
                        Feng.error(data.message);
                    }
                }
            });
            break;
        case 2:
            var verifycode = $("#verifyCode_new").val();
            var phone = $("#phoneNumber_new").val();
            $.ajax({
                url: Feng.ctxPath +"/shop/account/updatephone",
                type: 'post',
                dataType: 'json',
                async: false,
                data: {verifycode:verifycode,phone:phone},
                success: function (data) {
                    if(data.code == 200){
                        Feng.success(data.message);
                        $("#image2").attr("src",Feng.ctxPath + '/static/img/password_icon_nor.png');
                        $("#image3").attr("src",Feng.ctxPath + '/static/img/password_icon_pre.png');
                        $("#modifyPhone2").css("display","none");
                        $("#modifyPhone3").css("display","block");
                        $("#step2").css("color","#999999");
                        $("#step3").css("color","#FF7D32");
                    }else {
                        Feng.error(data.message);
                    }
                }
            });
            break;
        case 3:
            parent.location.reload();
            break;
    }
}

