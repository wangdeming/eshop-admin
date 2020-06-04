/**
 * 店铺信息管理管理初始化
 */
var balance = {
    id: "balanceTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
balance.initColumn = function () {
    return [

        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '申请时间', field: 'createdTime', align: 'left', valign: 'middle' },
        {title: '提现金额', field: 'amount', align: 'left', valign: 'middle'},
        {title: '提现状态', field: 'statusName', align: 'left', valign: 'middle'},
        {title: '详情', field: 'id', visible: true, align: 'left', valign: 'middle',width:'20%',
            formatter: function (value, row, index) {
                return '<a style="margin-right: 30px;" onclick="balance.opendetailDetail('+row.id+')">查看</a>';
            }
        }

    ];
};

balance.close = function() {

   layer.close(this.layerIndex);
};
/**
 * 检查是否选中
 */
balance.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        balance.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加店铺信息管理
 */
balance.opendetailDetail = function (id) {
    window.location.href=Feng.ctxPath + '/shop/money/towithdrawaldetail?id='+id;

};

/**
 * 打开查看店铺信息管理详情
 */
balance.openbalancebalance = function (id,isact) {
    window.location.href=Feng.ctxPath + '/shop/goods/goodsUpdate/'+id;
};
balance.getDate = function (dates) {
    var dd = new Date();
    var n = dates || 0;
    dd.setDate(dd.getDate() + n);
    var y = dd.getFullYear();
    var m = dd.getMonth() + 1;
    var d = dd.getDate();
    m = m < 10 ? "0" + m: m;
    d = d < 10 ? "0" + d: d;
    var day = y + "-" + m + "-" + d;
    return day;
};
balance.search = function () {
    balance.table.queryParams={'beginDate':$("#createStartTime").val(),'endDate':$("#createEndTime").val()};
    balance.table.refresh();
};
$(function () {
    $("#beginDate").val(balance.getDate(0));
    $("#endDate").val(balance.getDate(0));
    var defaultColunms = balance.initColumn();
    var table = new BSTable(balance.id, "/shop/money/withdrawallist", defaultColunms);
    table.setPaginationType("server");
    table.queryParams={'status':$("#status").val(),'beginDate':$("#createStartTime").val(),'endDate':$("#createEndTime").val()};
    balance.table = table.init( );
});
