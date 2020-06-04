/**
 * 初始化商家端角色管理详情对话框
 */
var AccountmanageInfoDlg = {
    pNameZtree: null,
    validateFields: {
        name: {
            validators: {
                notEmpty: {
                    message: '角色名称不能为空'
                },
                regexp: {//正则验证
                    regexp: /^[0-9a-zA-Z\u4e00-\u9fa5]{2,15}$/,
                    message: '角色名长度为2-15字符，允许包含数字、字母、汉字'
                }
            }
        },
        ids: {
            validators: {
                notEmpty: {
                    message: '权限列表不能为空'
                }
            }
        }
    }
};

/**
 * 关闭此对话框
 */
AccountmanageInfoDlg.close = function() {
    parent.layer.close(window.parent.Accountmanage.layerIndex);
};

/**
 * 验证数据是否为空
 */
var validate = function () {
    $('#RoleForm').data("bootstrapValidator").resetForm();
    $('#RoleForm').bootstrapValidator('validate');
    return $("#RoleForm").data('bootstrapValidator').isValid();
};

/**
 * 提交添加
 */
AccountmanageInfoDlg.addSubmit = function() {
    if (!validate) {
        return;
    }
    var name = $("#name").val();
    var ids = $("#ids").val();
    var shopType = $("#shopType>option:selected").attr("value");
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/platform/accountmanage/add", function(data){
        if (data.code == 200){
            Feng.success(data.message);
            window.parent.Accountmanage.table.refresh();
            AccountmanageInfoDlg.close();
        }else {
            Feng.error(data.message);
        }

    },function(data){
        Feng.error(data.message);
    });
    ajax.set("name",name);
    ajax.set("ids",ids);
    ajax.set("shopType",shopType);
    ajax.start();
};

$(function() {
    initZtree();
    AccountmanageInfoDlg.validator = Feng.initValidator("RoleForm", AccountmanageInfoDlg.validateFields);

    $("#shopType").change(function () {
        initZtree();
    });

});
function initZtree() {
    var platformType = $("#shopType option:selected").val();
    var pNameZtree = new $ZTree("pNameZtree", "/platform/menuManager/menuTreeList?platformType="+platformType);
    pNameZtree.setSettings({
        check: {
            enable: true
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
                var ids = checked.map(function (e) {return e.id});
                $("#ids").attr("value", ids.join(','));
            }
        }
    });
    pNameZtree.init();
    AccountmanageInfoDlg.pNameZtree = pNameZtree;
}