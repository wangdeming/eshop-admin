
var rolesArr = [];
var validateFields = {
    account: {
        validators: {
            notEmpty: {
                message: '账户名不能为空'
            },
            regexp: {//正则验证
                regexp: /^[0-9a-zA-Z\u4e00-\u9fa5]{2,15}$/,
                message: '长度为2-15字符，允许包含字母、数字、汉字，不包含空格'
            }
        }
    },
    name: {
        validators: {
            notEmpty: {
                message: '姓名不能为空'
            },
            regexp: {//正则验证
                regexp: /(^[\u4e00-\u9fa5]{1}[\u4e00-\u9fa5\.·。]{0,8}[\u4e00-\u9fa5]{1}$)|(^[a-zA-Z]{1}[a-zA-Z\s*]{0,18}[a-zA-Z]{1}$)/,
                message: '长度2-10汉字字符或2-20英文字符'
            }
        }
    },
    passwd: {
        validators: {
            notEmpty: {
                message: '密码不能为空'
            },
            regexp: {//正则验证
                regexp: /^(?![\d]+$)(?![a-zA-Z]+$)(?![!#$%^&*]+$)[\da-zA-Z!#$%^&*]{6,16}$/,
                message: '长度为6-16字符，必须包含字母、数字、符号中至少两种，不包含空格'
            },
            identical: {
                field: 'confirmPasswd',
                message: '两次输入密码不一致！'
            }
        }
    },
    confirmPasswd: {
        validators: {
            notEmpty: {
                message: '密码不能为空'
            },
            regexp: {//正则验证
                regexp: /^(?![\d]+$)(?![a-zA-Z]+$)(?![!#$%^&*]+$)[\da-zA-Z!#$%^&*]{6,16}$/,
                message: '长度为6-16字符，必须包含字母、数字、符号中至少两种，不包含空格'
            },
            identical: {
                field: 'passwd',
                message: '两次输入密码不一致！'
            }
        }
    }
};
$(function () {
    var ajax = new $ax(Feng.ctxPath + "/shop/subaccount/toinsertsubaccountdata", function (data) {
        var roleList = data.data.roleList;
        var roleSelectHtml = '';
        for (var i = 0;i<roleList.length;i++){
            var optionHtml = '<option data-id="' + roleList[i].id + '">' + roleList[i].name + '</option>';
            roleSelectHtml += optionHtml;
            var menuList = roleList[i].menuList;
            rolesArr.push(menuList);
        }
        $("#role").empty().append(roleSelectHtml);
        var zNodes = rolesArr[0];
        $.fn.zTree.init($("#pNameZtree"), {
            check: {
                enable: false,
                chkDisabledInherit: false
            },
            view: {
                dblClickExpand : true,
                selectedMulti : false
            },
            data: {simpleData : {enable : true}},
            callback: {
                onCheck: function (e, treeId, treeNode) {
                    var tree = $.fn.zTree.getZTreeObj(treeId);
                    var checked = tree.getCheckedNodes(true);
                }
            }
        }, zNodes);

    },function (data) {});
    ajax.start();

    $("#role").change(function(){
        var index = $("#role").get(0).selectedIndex;
        var zNodes = rolesArr[index];
        $.fn.zTree.init($("#pNameZtree"), {
            check: {
                enable: false,
                chkDisabledInherit: false
            },
            view: {
                dblClickExpand : true,
                selectedMulti : false
            },
            data: {simpleData : {enable : true}},
            callback: {
                onCheck: function (e, treeId, treeNode) {
                    var tree = $.fn.zTree.getZTreeObj(treeId);
                    var checked = tree.getCheckedNodes(true);
                }
            }
        }, zNodes);
    });

    Feng.initValidator("insertSubAccountForm", validateFields);
});

/**
 * 验证数据是否为空
 */
var validate = function () {
    $('#insertSubAccountForm').data("bootstrapValidator").resetForm();
    $('#insertSubAccountForm').bootstrapValidator('validate');
    return $("#insertSubAccountForm").data('bootstrapValidator').isValid();
};

function closePage() {
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
}
function addSubmit() {
    if (!validate) {
        return;
    }
    var account = $("#account").val();
    var roleid = $("#role>option:selected").attr("data-id");
    var name = $("#name").val();
    var sex = $("#sex>option:selected").attr("value");
    var password = $("#passwd").val();
    $.ajax({
        url: Feng.ctxPath +"/shop/subaccount/insertsubaccount",
        type: 'post',
        dataType: 'json',
        async: false,
        data: {account:account,roleid:roleid,name:name,sex:sex,password:password},
        success: function (data) {
            if(data.code == 200){
                Feng.success(data.message);
                setTimeout(function () {
                    parent.location.reload();
                },1000)
            }else {
                Feng.error(data.message);
            }
        }
    });
}
