/**
 * 店铺账号管理管理初始化
 */
var ShopAccount = {
    id: "ShopAccountTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};
var SubAccount={
    accountId:0
};
/**
 * 初始化表格的列
 */
ShopAccount.initColumn = function () {

    return [
        {title: '店铺名称', field: 'shopName', align: 'left',  },
        {title: '店铺类型', field: 'shopType', align: 'left', },
        {title: '账户名', field: 'account', align: 'left', },
        {title: '账号角色', field: 'role', align: 'left', },
        {title: '姓名', field: 'name', align: 'left', },
        {title: '账户级别', field: 'level', align: 'left', },
        {title: '手机', field: 'phone', align: 'left', },
        {title: '创建时间', field: 'createdTime', align: 'left', },
        {title: '状态', field: 'status', align: 'left',
            formatter: function (value, row, index) {
                var astr=['','未激活','正常','冻结'];
                return astr[row.status];
            }
        },
        {title: '操作', field: 'stateDes', visible: true, align: 'left',
            formatter: function (value, row, index) {
                if(row.pid==0){
                    var spro='';
                    if(row.status==2){
                        spro+='<a  style="margin-right: 30px;" onclick="ShopAccount.toFreeze('+row.id+')">冻结</a>';
                    }else if(row.status==3){
                        spro+='<a style="margin-right: 30px;" onclick="ShopAccount.cancel('+row.id+')">注销</a>';
                        spro+='<a  style="margin-right: 30px;" onclick="ShopAccount.toUnfreeze('+row.id+')">解冻</a>';
                    }
                    return '<a style="margin-right: 30px;" onclick="ShopAccount.openShopAccountDetail('+row.id+')">查看</a>'+spro;
                }else{
                    return '<a style="margin-right: 30px;" onclick="ShopAccount.openViewRole('+row.id+')">查看权限</a>';
                }

            }
        }
    ];
};
/**
 * 查看商家端角色管理详情
 */
ShopAccount.openViewRole = function (id) {
    SubAccount.accountId=id;
    var index = layer.open({
        type: 2,
        title: '查看权限',
        area: ['600px', '550px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/shop/subaccount/torole?id=' + id
    });
    this.layerIndex = index;

};
ShopAccount.ok = function () {
    ShopAccount.table.refresh();
    $("#ShopAccountTable>thead>tr>th").css("text-align","left");
};

ShopAccount.toFreeze = function (accountId) {
    window.location.href=Feng.ctxPath +  '/platform/shopacctoper/toFreeze/'+accountId;
};
ShopAccount.toUnfreeze = function (accountId) {
    window.location.href=Feng.ctxPath +  '/platform/shopacctoper/toUnfreeze/'+accountId;
};
/**
 * 点击添加店铺账号管理
 */
ShopAccount.openAddShopAccount = function () {
    window.location.href=Feng.ctxPath +  '/platform/shopaccount/toAdd/0'
};

/**
 * 打开查看店铺账号管理详情
 */
ShopAccount.openShopAccountDetail = function (id) {
    window.location.href=Feng.ctxPath + '/platform/shopaccount/toView/' + id+'?accountId='+id;
};
/**
 * 冻结账号
 */
ShopAccount.cancel = function (accountId) {

    var ajax = new $ax(Feng.ctxPath + "/platform/shopacctoper/cancel", function (data) {
        if(data.code==200){
            Feng.success(data.message);
            ShopAccount.table.refresh();
        }else{
            Feng.error(data.message);
        }

    }, function (data) {
        Feng.error( data.responseJSON.message + "!");
    });
    ajax.set("accountId",accountId);
    ajax.start();

};

/**
 * 删除店铺账号管理
 */
ShopAccount.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/shopAccount/delete", function (data) {
            Feng.success("删除成功!");
            ShopAccount.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("shopAccountId",this.seItem.id);
        ajax.start();
    }
};
function openSubmenu() {
    $("span.treegrid-expander").each(function (index,item) {
        if($(item).hasClass("glyphicon-chevron-right")){
            $(item).trigger("click");
        }
    });
}
function closeSubmenu() {
    $("span.treegrid-expander").each(function (index,item) {
        if($(item).hasClass("glyphicon-chevron-down")){
            $(item).trigger("click");
        }
    });
}
/**
 * 查询店铺账号管理列表
 */
ShopAccount.search = function () {
    var queryData = {};
    queryData['shopType'] = $("#shopType").val();
    queryData['condition'] = $("#condition").val();
    ShopAccount.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = ShopAccount.initColumn();

    var table = new BSTreeTable(ShopAccount.id, "/platform/shopaccount/list", defaultColunms);
    table.setExpandColumn(0);
    table.setIdField("id");
    table.setCodeField("id");
    table.setParentCodeField("pid");
    table.setExpandAll(true);
    table.init();
    ShopAccount.table = table;
    $("#shopType").change(function () {
        ShopAccount.search();
    });
    $("#ShopAccountTable>thead>tr>th").css("text-align","left");
    var tempHtml = '<div class="columns columns-right btn-group pull-right"><button type="button" class="btn btn-outline btn-default" onclick="openSubmenu();">展开</button><button type="button" class="btn btn-outline btn-default" onclick="closeSubmenu();" id="closesub">收起</button></div>';
    $("div.fixed-table-toolbar").append(tempHtml);
});
