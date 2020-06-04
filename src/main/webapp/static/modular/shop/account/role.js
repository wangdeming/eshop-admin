
$(function () {
    var accountId = parent.SubAccount.accountId;
    var ajax = new $ax(Feng.ctxPath + "/shop/subaccount/toroledata", function (data) {
        var role = data.data.role;
        $("#account").val(role.shopTypeName);
        $("#name").val(role.name);
        var menuList = data.data.menuList;
        var zNodes = [];
        zNodes = menuList
            .map(function(e){return e.pId === 0 ? (e.chkDisabled = true, e) : e})
            .filter(function(e){return e.checked});
        $.fn.zTree.init($("#pNameZtree"), {
            check: {
                enable: true,
                chkDisabledInherit: true
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
    ajax.set("id",accountId);
    ajax.start();
});
function closePage() {
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
}