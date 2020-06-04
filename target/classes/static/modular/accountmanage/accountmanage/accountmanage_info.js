/**
 * 初始化商家端角色管理详情对话框
 */
var AccountmanageInfoDlg = {
    deptZtree: null
};

/**
 * 关闭此对话框
 */
AccountmanageInfoDlg.close = function() {
    parent.layer.close(window.parent.Accountmanage.layerIndex);
};

$(function() {

    var shopType = $("#shopType").attr("data-shopType");
    if (shopType == "1"){
        $("#shopType").get(0).selectedIndex=0;
    }else {
        $("#shopType").get(0).selectedIndex=1;
    }
    initZtree();
    setDisabledNode();
});

function initZtree() {
    var zNodes = [];
    var ids = [];
    (new $ax(Feng.ctxPath + "/platform/menuManager/menuTreeListByRoleId?roleId=" + $('#roleId').val() + '&platformType=' + $("#shopType").attr("data-shopType"), function (data) {
        zNodes = data;
        ids = data.reduce(function (r, e) {
            return e.checked ? (r.push(e.id), r) : r
        }, []);
    }, function (data) {
        Feng.error("加载ztree信息失败!");
    })).set("roleId", $('#roleId').val()).start();
    $.fn.zTree.init($("#deptZtree"), {
        check: {
            enable: true
        },
        view: {
            dblClickExpand: true,
            selectedMulti: false
        },
        data: {simpleData: {enable: true}},
        callback: {
            onCheck: function (e, treeId, treeNode) {
                var tree = $.fn.zTree.getZTreeObj(treeId);
                var checked = tree.getCheckedNodes(true);
                ids = checked.map(function (e) {
                    return e.id
                });
                $("#ids").attr("value", ids.join(','));
            },
            beforeCheck: function zTreeBeforeCheck(treeId, treeNode) {
                return false
            }
        }
    }, zNodes);

    $("#ids").attr("value", ids.join(','));

    AccountmanageInfoDlg.deptZtree = deptZtree;
}

function setDisabledNode(){
    var treeObj = $.fn.zTree.getZTreeObj("deptZtree");
    var nodes = treeObj.transformToArray(treeObj.getNodes());
    for (var i=0, l=nodes.length; i<l; i++) {
        treeObj.setChkDisabled(nodes[i], true);
    }
}