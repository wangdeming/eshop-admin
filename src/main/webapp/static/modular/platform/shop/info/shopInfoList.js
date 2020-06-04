/**
 * 店铺信息管理管理初始化
 */
var ShopInfoMgr = {
    id: "ShopInfoMgrTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
ShopInfoMgr.initColumn = function () {
    return [
        {title: 'id', field: 'id', visible: false, align: 'left', valign: 'middle'},
        {title: '店铺名称', field: 'name', align: 'left', valign: 'middle', width:'17%'},
        {title: '店铺类型', field: 'shopTypeName', align: 'left', valign: 'middle', width:'12%'},
        {title: '店铺ID', field: 'id', align: 'left', valign: 'middle'},
        {title: '店铺地址', field: 'address', align: 'left', valign: 'middle'},
        {title: '法人', field: 'legalPerson', align: 'left', valign: 'middle', width:'15%'},
        {title: '入驻时间', field: 'createdTime', align: 'left', valign: 'middle'},
        {title: '状态', field: 'statusName', align: 'left', valign: 'middle'},
        {title: '操作', field: 'stateDes', visible: true, align: 'left', valign: 'middle',width:'20%',
            formatter: function (value, row, index) {

                if(row.statusName=='未开通账号'){
                    return  '<a style="margin-right: 30px;" onclick="ShopInfoMgr.openAddShopAccount('+row.id+')">开通账号</a>'+
                            '<a style="margin-right: 30px;" onclick="ShopInfoMgr.openShopInfoMgrDetail('+row.id+',1)">编辑</a>'+
                            '<a  style="margin-right: 30px;" onclick="ShopInfoMgr.delete('+row.id+')">删除</a>';
                }else if(row.statusName=='正常营业'){
                    return  '<a style="margin-right: 30px;" onclick="ShopInfoMgr.openShopInfoMgrDetail('+row.id+')">查看</a>'+
                            '<a style="margin-right: 30px;" onclick="ShopInfoMgr.offshelf('+row.id+')">下架</a>';
                }else{
                    return  '<a style="margin-right: 30px;" onclick="ShopInfoMgr.openShopInfoMgrDetail('+row.id+',1)">编辑</a>'+
                            '<a style="margin-right: 30px;" onclick="ShopInfoMgr.onshelf('+row.id+')">上架</a>'+
                            '<a  style="margin-right: 30px;" onclick="ShopInfoMgr.delete('+row.id+')">删除</a>';
                }



            }
        }

    ];
};
ShopInfoMgr.openAddShopAccount = function (id) {
    window.location.href=Feng.ctxPath +  '/platform/shopaccount/toAdd/'+id+"?shopId="+id;
};
/**
 * 检查是否选中
 */
ShopInfoMgr.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        ShopInfoMgr.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加店铺信息管理
 */
ShopInfoMgr.openAddShopInfoMgr = function () {
    window.location.href=Feng.ctxPath + '/platformshop/shopInfo/toAdd?isact=1';

};

/**
 * 打开查看店铺信息管理详情
 */
ShopInfoMgr.openShopInfoMgrDetail = function (id,isact) {
    if(isact){
        window.location.href=Feng.ctxPath + '/platformshop/shopInfo/toEdit/'+id+"?isact="+isact;
    }else{
        window.location.href=Feng.ctxPath + '/platformshop/shopInfo/toEdit/'+id;
    }

};
/**
 * 下架店铺
 */
ShopInfoMgr.offshelf = function (id) {
    var ajax = new $ax(Feng.ctxPath + "/platformshop/shopInfo/offshelf", function (data) {
        Feng.success(data.message);
        ShopInfoMgr.table.refresh();
    }, function (data) {
        Feng.error("删除失败!" + data.responseJSON.message + "!");
    });
    ajax.set("shopId",id);
    ajax.start();

};
/**
 * 上架店铺
 */
ShopInfoMgr.onshelf = function (id) {
    var ajax = new $ax(Feng.ctxPath + "/platformshop/shopInfo/onshelf", function (data) {
        Feng.success(data.message);
        ShopInfoMgr.table.refresh();
    }, function (data) {
        Feng.error("删除失败!" + data.responseJSON.message + "!");
    });
    ajax.set("shopId",id);
    ajax.start();

};
/**
 * 删除店铺信息管理
 */
ShopInfoMgr.delete = function (id) {
    var ajax = new $ax(Feng.ctxPath + "/platformshop/shopInfo/delete", function (data) {
        Feng.success(data.message);
        ShopInfoMgr.table.refresh();
    }, function (data) {
        Feng.error("删除失败!" + data.responseJSON.message + "!");
    });
    ajax.set("shopId",id);
    ajax.start();

};

/**
 * 查询店铺信息管理列表
 */
ShopInfoMgr.search = function () {
    var queryData = {};
    queryData['shopType'] = $("#shopType").val();
    queryData['condition'] = $("#condition").val();
    ShopInfoMgr.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = ShopInfoMgr.initColumn();
    var table = new BSTable(ShopInfoMgr.id, "/platformshop/shopInfo/list", defaultColunms);
    table.setPaginationType("server");
    ShopInfoMgr.table = table.init();
    
    $("#shopType").change(function () {
        ShopInfoMgr.search();
    })
});
