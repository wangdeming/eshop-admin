/**
 * 店铺服务费管理初始化
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
        {title: '店铺名称', field: 'shopName', align: 'left', valign: 'middle', width:'17%'},
        {title: '店铺类型', field: 'shopType', align: 'left', valign: 'middle', width:'12%'},
        {title: '创建时间', field: 'createdTime', align: 'left', valign: 'middle', sortable: true},
        {title: '当前费率', field: 'serviceRate', align: 'left', valign: 'middle', width:'15%', sortable: true,
            formatter: function (value, row, index) {
                if (row.serviceRate == ''){
                    return "0%";
                }else {
                    return row.serviceRate;
                }
            }
        },
        {title: '变更后费率', field: 'changeServiceRate', align: 'left', valign: 'middle', sortable: true},
        {title: '变更后生效时间', field: 'effectiveTime', align: 'left', valign: 'middle', sortable: true},
        {title: '操作', field: 'stateDes', visible: true, align: 'left', valign: 'middle',width:'20%',
            formatter: function (value, row, index) {
                    return  '<a style="margin-right: 30px;" onclick="ShopInfoMgr.openShopInfoMgrDetail('+row.id+')">设置</a>'+
                        '<a style="margin-right: 30px;" onclick="ShopInfoMgr.openHistoryList('+row.id+')">操作记录</a>';
            }
        }

    ];
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
 * 打开设置费率
 */
ShopInfoMgr.openShopInfoMgrDetail = function (id) {
    window.location.href=Feng.ctxPath + '/platform/cash/profitDistribution/toSet/'+id;
};

/**
 * 打开操作记录
 */
ShopInfoMgr.openHistoryList = function (profitDistributionId) {
    window.location.href=Feng.ctxPath + '/platform/cash/profitDistributionHistory/toHistoryList/'+profitDistributionId;
};

/**
 * 查询服务费管理列表
 */
ShopInfoMgr.search = function () {
    var queryData = {};
    queryData['shopType'] = $("#shopType").val();
    queryData['condition'] = $("#condition").val();
    ShopInfoMgr.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = ShopInfoMgr.initColumn();
    var table = new BSTable(ShopInfoMgr.id, "/platform/cash/profitDistribution/list", defaultColunms);
    table.setPaginationType("server");
    ShopInfoMgr.table = table.init();

    // 监听服务费类型变化
    $("#shopType").change(function () {
        ShopInfoMgr.search();
    });
});