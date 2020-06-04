var validateFields = {
    newPwd: {
        validators: {
            notEmpty: {
                message: '密码不能为空'
            },
            regexp: {//正则验证
                regexp: /^(?![\d]+$)(?![a-zA-Z]+$)(?![!#$%^&*]+$)[\da-zA-Z!#$%^&*]{6,16}$/,
                    message: '6位-16位，必须包含数字、字母或字符中的两种，不包含空格！'
            },
            identical: {
                field: 'rePwd',
                message: '两次输入密码不一致！'
            }
        }
    },
    rePwd: {
        validators: {
            notEmpty: {
                message: '密码不能为空'
            },
            regexp: {//正则验证
                regexp: /^(?![\d]+$)(?![a-zA-Z]+$)(?![!#$%^&*]+$)[\da-zA-Z!#$%^&*]{6,16}$/,
                    message: '6位-16位，必须包含数字、字母或字符中的两种，不包含空格！'
            },
            identical: {
                field: 'newPwd',
                message: '两次输入密码不一致！'
            }
        }
    }
};
$(function () {
    Feng.initValidator("changePwdForm", validateFields);
});
/**
 * 验证数据是否为空
 */
var validate = function () {
    $('#changePwdForm').data("bootstrapValidator").resetForm();
    $('#changePwdForm').bootstrapValidator('validate');
    return $("#changePwdForm").data('bootstrapValidator').isValid();
};

function changePwd() {
    if (!validate) {
        return;
    }
    var newPassword = $("#newPwd").val();
    $.ajax({
        url: Feng.ctxPath +"/shop/account/updatedefaultpassword",
        type: 'post',
        dataType: 'json',
        async: false,
        data: {newPassword:newPassword},
        success: function (data) {
            if(data.code == 200){
                Feng.success(data.message);
                window.location.href=Feng.ctxPath+'/'
            }else {
                Feng.error("修改失败!" + data.message + "!");
            }
        }
    });
}