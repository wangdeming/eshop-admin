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

        {title: 'id', field: 'tradeId', visible: false, align: 'center', valign: 'middle'},
        {title: '时间', field: 'createdTime', align: 'left', valign: 'middle' },
        {title: '类型', field: 'transSrcName', align: 'left', valign: 'middle',
            formatter: function (value, row, index) {
                if('订单收入'!=row.transSrcName){
                    return  row.transSrcName;
                }else{
                    return  row.transSrcName+'<p>订单号：<a onclick="balance.toorderdetail(\''+row.orderId+'\','+row.shopType+')">'+getfstr(row.orderNo)+'</a></p>';
                }
            }
        },
        {title: '余额变化', field: 'balance', align: 'left', valign: 'middle'},
        {title: '服务费', field: 'serviceFee', align: 'left', valign: 'middle'},
        {title: '详情', field: 'tradeId', visible: true, align: 'left', valign: 'middle',width:'20%',
            formatter: function (value, row, index) {
                if('订单收入'!=row.transSrcName){
                    return '<a style="margin-right: 30px;" onclick="balance.openbalancebalance('+row.tradeId+',1)">查看</a>';
                }else{
                    return '<a style="margin-right: 30px;" onclick="balance.toorderdetail(\''+row.orderId+'\','+row.shopType+')">查看</a>'
                }

            }
        }

    ];
};
balance.toorderdetail= function (id,shopType) {
    if(shopType==1){
        sessionStorage.setItem("orderDetailsOrderId",id);
        window.location.href=Feng.ctxPath + '/shop/order/detail';
    }else{
        sessionStorage.setItem("hotelOrderId",id);
        window.location.href=Feng.ctxPath + '/shop/hotel/hotelOrderDetail';
    }

};
getfstr = function (sv){
    if(sv == "" || sv == null || sv == undefined){
        return '';
    }
    return sv;
};
/**
 * 打开查看店铺信息管理详情
 */
balance.openbalancebalance = function (id) {
    window.location.href=Feng.ctxPath + '/shop/money/towithdrawaldetail?id='+id;
};
balance.search = function () {
    balance.table.queryParams={'beginDate':$("#createStartTime").val(),'endDate':$("#createEndTime").val()};
    balance.table.refresh();
};


$(function () {


    var defaultColunms = balance.initColumn();
    var table = new BSTable(balance.id, "/shop/money/balancelist", defaultColunms);
    table.setPaginationType("server");
    table.queryParams={'beginDate':$("#createStartTime").val(),'endDate':$("#createEndTime").val()};
    balance.table = table.init( );
    // balance.search();
});
