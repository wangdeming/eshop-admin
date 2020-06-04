/**
 * 初始化商家端角色管理详情对话框
 */
var AccountmanageInfoDlg = {
    deptZtree: null,
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
 * 提交修改
 */
AccountmanageInfoDlg.editSubmit = function() {
    if (!validate) {
        return;
    }
    var name = $("#name").val();
    var ids = $("#ids").val();
    var roleId = $("#roleId").val();

    var ajax = new $ax(Feng.ctxPath + "/platform/accountmanage/editRole", function(data){
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
    ajax.set("roleId",roleId);
    ajax.start();
};

$(function() {

    var shopType = $("#shopType").attr("data-shopType");
    if (shopType == "1"){
        $("#shopType").get(0).selectedIndex=0;
    }else {
        $("#shopType").get(0).selectedIndex=1;
    }

    initZtree();

    AccountmanageInfoDlg.validator = Feng.initValidator("RoleForm", AccountmanageInfoDlg.validateFields);
});

function initZtree() {
    var zNodes = [];
    var ids = [];
    (new $ax(Feng.ctxPath + "/platform/menuManager/menuTreeListByRoleId?roleId="+$('#roleId').val()+'&platformType='+$("#shopType").attr("data-shopType"), function(data) {
        zNodes = data;
        ids = data.reduce(function(r, e){return e.checked ? (r.push(e.id), r) : r}, []);
    }, function(data) {
        Feng.error("加载ztree信息失败!");
    })).set("roleId", $('#roleId').val()).start();
    $.fn.zTree.init($("#deptZtree"), {
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
                ids = checked.map(function (e) {return e.id});
                $("#ids").attr("value", ids.join(','));
            }
        }
    }, zNodes);

    $("#ids").attr("value", ids.join(','));

    AccountmanageInfoDlg.deptZtree = deptZtree;
}