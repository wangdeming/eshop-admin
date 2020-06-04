/**
 * 商家端角色管理管理初始化
 */
var Accountmanage = {
    id: "AccountmanageTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
};

/**
 * 初始化表格的列
 */
Accountmanage.initColumn = function () {
    return [
        // {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '名称', field: 'name', visible: true, align: Feng.align, valign: 'middle'},
        {title: '店铺类型', field: 'shop_type', visible: true, align: Feng.align, valign: 'middle',
            formatter: function (value, row, index) {
                if (value == 1){
                    return "特产店铺";
                }else {
                    return "酒店店铺";
                }
            }
        },
        {title: '用户数量', field: 'userNum', visible: true, align: Feng.align, valign: 'middle'},
        {title: '操作', field: '', visible: true, align: Feng.align, valign: 'middle',
            formatter: function (value, row, index) {
                return '<a style="margin-right: 30px;" onclick="Accountmanage.openViewRole('+row.id+')">查看</a>'
                    +'<a style="margin-right: 30px;" onclick="Accountmanage.openEditRole('+row.id+')">编辑</a>'
                    +'<a onclick="Accountmanage.delete('+row.id+')">删除</a>';
            }
        }
    ];
};

/**
 * 点击添加商家端角色管理
 */
Accountmanage.openAddShopInfoMgr = function () {
    var index = layer.open({
        type: 2,
        title: '添加商家端角色',
        area: ['800px', '450px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/platform/accountmanage/roleManage_add'
    });
    this.layerIndex = index;
};

/**
 * 查看商家端角色管理详情
 */
Accountmanage.openViewRole = function (id) {
    var index = layer.open({
        type: 2,
        title: '查看商家端角色',
        area: ['800px', '450px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/platform/accountmanage/roleManage_detail/' + id
    });
    this.layerIndex = index;
};

/**
 * 编辑商家端角色管理详情
 */
Accountmanage.openEditRole = function (id) {
    var index = layer.open({
        type: 2,
        title: '编辑商家端角色',
        area: ['800px', '450px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/platform/accountmanage/roleManage_update/' + id
    });
    this.layerIndex = index;
};

/**
 * 删除商家端角色管理
 */
Accountmanage.delete = function (id) {
    var ajax = new $ax(Feng.ctxPath + "/platform/accountmanage/delete", function (data) {
        if (data.code == 200){
            Feng.success(data.message);
            Accountmanage.table.refresh();
        }else {
            Feng.error(data.message);
        }
    }, function (data) {
        Feng.error(data.message);
    });
    ajax.set("roleId",id);
    ajax.start();
};

/**
 * 查询商家端角色管理列表
 */
Accountmanage.search = function () {
    var queryData = {};
    queryData['shopType'] = shopType;
    queryData['condition'] = $("#condition").val();
    Accountmanage.table.refresh({query: queryData});
};

var shopType = 0;

$(function () {
    var defaultColunms = Accountmanage.initColumn();
    var table = new BSTable(Accountmanage.id, "/platform/accountmanage/list", defaultColunms);
    var queryData = {};
    queryData['shopType'] = shopType;
    table.setQueryParams(queryData);
    table.method = "GET";
    table.setPaginationType("server");
    Accountmanage.table = table.init();

    $("#shopType").change(function () {
        shopType = $("#shopType>option:selected").attr("value");
        var queryData = {};
        queryData['shopType'] = shopType;
        queryData['condition'] = $("#condition").val();
        Accountmanage.table.refresh({query: queryData});
    });
});
