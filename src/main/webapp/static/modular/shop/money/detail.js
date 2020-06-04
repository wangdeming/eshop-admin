/**
 * 管理管理初始化
 */
var detail = {
    id: "detailTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
detail.initColumn = function () {
    return [

        {title: 'id', field: 'tradeId', visible: false, align: 'center', valign: 'middle'},
        {title: '时间', field: 'createdTime', align: 'left', valign: 'middle' ,},
        {title: '类型', field: 'transSrcName', align: 'left', valign: 'middle',
            formatter: function (value, row, index) {
                if('订单收入'!=row.transSrcName){
                    return  row.transSrcName;
                }else{
                    return  row.transSrcName+'<p>订单号：<a onclick="detail.toorderdetail(\''+row.orderId+'\','+row.shopType+')">'+getfstr(row.orderNo)+'</a></p>';
                }
            }
        },
        {title: '余额变化', field: 'balance', align: 'left', valign: 'middle',},
        {title: '服务费', field: 'serviceFee', align: 'left', valign: 'middle',},
        {title: '详情', field: 'tradeId', visible: true, align: 'left', valign: 'middle',width:'20%',
            formatter: function (value, row, index) {
                if('订单收入'!=row.transSrcName){
                    return '<a style="margin-right: 30px;" onclick="detail.opendetailDetail('+row.tradeId+',1)">查看</a>';
                }else{
                    return '<a style="margin-right: 30px;" onclick="detail.toorderdetail(\''+row.orderId+'\','+row.shopType+')">查看</a>'
                }

           }
        }

    ];
};
detail.toorderdetail= function (id,shopType) {
    if(shopType==1){
        sessionStorage.setItem("orderDetailsOrderId",id);
        window.location.href=Feng.ctxPath + '/shop/order/detail';
    }else{
        sessionStorage.setItem("hotelOrderId",id);
        window.location.href=Feng.ctxPath + '/shop/hotel/hotelOrderDetail';
    }
};
detail.close = function() {

   layer.close(this.layerIndex);
};
/**
 * 检查是否选中
 */
detail.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        detail.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加管理
 */
detail.openAdddetail = function () {
    window.location.href=Feng.ctxPath + '/shop/goods/toinsert';

};

/**
 * 打开查看管理详情
 */
detail.opendetailDetail = function (id) {
    window.location.href=Feng.ctxPath + '/shop/money/towithdrawaldetail/?id='+id;
};

detail.getDate = function (dates) {
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
getfstr = function (sv){
    if(sv == "" || sv == null || sv == undefined){
        return '';
    }
    return sv;
};
$(function () {

    $(".glyphicon-question-sign").mouseover(function () {
        var obj=$(this);
        $("#"+obj.attr('data')).show()
    });
    $(".glyphicon-question-sign").mouseout(function () {
        var obj=$(this);
        $("#"+obj.attr('data')).hide()
    });
    var defaultColunms = detail.initColumn();
    var table = new BSTable(detail.id, "/shop/money/balancelist", defaultColunms);
    table.setPaginationType("server");
    table.queryParams={'beginDate':detail.getDate(0),'endDate':detail.getDate(0)};

    detail.table = table.init( );
    var ajax = new $ax(Feng.ctxPath + "/shop/money/moneydetail", function (data) {
        if(data.code==200){
            $.each(data.data,function (k,v) {
                if(k=='serviceRate'){
                    $("#"+k).text(v)
                }else{
                    $("#"+k).text("￥"+v)
                }
            })
        }else{
            Feng.error( data.message + "!");
        }

    }, function (data) {
        Feng.error(data.responseJSON.message + "!");
    });
    ajax.start();

});
