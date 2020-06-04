var validateFields = {
    oldPwd: {
        validators: {
            notEmpty: {
                message: '密码不能为空'
            },
            regexp: {//正则验证
                regexp: /(?!^[0-9]+$)(?!^[A-z]+$)(?!^[^A-z0-9]+$)^.{6,16}$/,
                    message: '6位-16位，可以是字母与数字的组合，也可以是纯数字或纯字母'
            }
        }
    },
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
function closePage() {
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
}
function changePwd() {
    if (!validate) {
        return;
    }
    var originalPassword = $("#oldPwd").val();
    var newPassword = $("#newPwd").val();
    $.ajax({
        url: Feng.ctxPath +"/shop/account/updatepassword",
        type: 'post',
        dataType: 'json',
        async: false,
        data: {originalPassword:originalPassword,newPassword:newPassword},
        success: function (data) {
            if(data.code == 200){
                Feng.success(data.message);
                closePage();
            }else {
                Feng.error("修改失败!" + data.message + "!");
            }
        }
    });
}