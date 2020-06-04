/**
 * 店铺服务费管理初始化
 */
var ShopInfoMgr = {
    id: "BalanceFlowTable",	//表格id
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
        {title: '时间', field: 'createdTime', align: 'left', valign: 'middle', width:'17%'},
        {title: '类型', field: 'flowType', align: 'left', valign: 'middle', width:'12%',
            formatter: function (value, row, index) {

                if(row.transSrc=='3'){
                    return  '<p style="margin-right: 30px;">'+row.flowType+'</p>';
                }else if((row.transSrc=='1' || row.transSrc=='2') && row.shopType=='特产店铺'){
                    return  '<p style="margin-right: 30px;">'+row.flowType+'</p>'+
                        '<p>订单号：<a style="margin-right: 30px;" onclick="ShopInfoMgr.goodsOrderDetail(\''+row.tradeId+'\')">'+row.orderNo+'</a></p>';
                }else if((row.transSrc=='1' || row.transSrc=='2') && row.shopType=='酒店店铺'){
                    return  '<p style="margin-right: 30px;">'+row.flowType+'</p>'+
                        '<p>订单号：<a style="margin-right: 30px;" onclick="ShopInfoMgr.goodsHotelOrderDetail(\''+row.tradeId+'\')">'+row.orderNo+'</a></p>';
                }
            }
        },
        {title: '余额变化', field: 'amount', align: 'left', valign: 'middle'},
        {title: '店铺名称', field: 'shopName', align: 'left', valign: 'middle', width:'15%'},
        {title: '店铺类型', field: 'shopType', align: 'left', valign: 'middle'},
        {title: '查看', field: 'stateDes', visible: true, align: 'left', valign: 'middle',width:'20%',
            formatter: function (value, row, index) {

                if(row.transSrc=='3'){
                    return  '<a style="margin-right: 30px;" onclick="ShopInfoMgr.withdrawalDetail(\''+row.tradeId+'\')">查看</a>';
                }else if((row.transSrc=='1' || row.transSrc=='2') && row.shopType=='特产店铺'){
                    return  '<a style="margin-right: 30px;" onclick="ShopInfoMgr.goodsOrderDetail(\''+row.tradeId+'\')">查看</a>';
                }else if((row.transSrc=='1' || row.transSrc=='2') && row.shopType=='酒店店铺'){
                    return  '<a style="margin-right: 30px;" onclick="ShopInfoMgr.goodsHotelOrderDetail(\''+row.tradeId+'\')">查看</a>';
                }
            }
        }

    ];
};

/**
 * 查看订单详情
 */
ShopInfoMgr.goodsOrderDetail = function (id) {
    sessionStorage.setItem("orderDetailsOrderId",id);
    window.location.href = Feng.ctxPath + '/platform/ordercenter/goodsOrderDetail';
};
ShopInfoMgr.goodsHotelOrderDetail = function (id) {
    sessionStorage.setItem("hotelOrderId",id);
    window.location.href = Feng.ctxPath + '/platform/ordercenter/hotelOrderDetail';
};

/**
 * 查看提现详情
 */
ShopInfoMgr.withdrawalDetail = function (id) {
    window.location.href=Feng.ctxPath + '/platform/cash/withdrawal/toDetail/'+id;
};

/**
 * 查询服务费管理列表
 */
ShopInfoMgr.search = function (timeType) {
    if (timeType == null) {
        timeType = $("#timeType").find('.gun-btn-active')[0].id;
    }
    if(timeType==1){
        $("#createStartTime").val(ShopInfoMgr.getDate(0));
        $("#createEndTime").val(ShopInfoMgr.getDate(0));
        $("#createStartTime,#createEndTime").attr("disabled","disabled");
    }else if(timeType==2){
        $("#createStartTime").val(ShopInfoMgr.getDate(-1));
        $("#createEndTime").val(ShopInfoMgr.getDate(-1));
        $("#createStartTime,#createEndTime").attr("disabled","disabled");
    }else if(timeType==3){
        $("#createStartTime").val(ShopInfoMgr.getDate(-6));
        $("#createEndTime").val(ShopInfoMgr.getDate(0));
        $("#createStartTime,#createEndTime").attr("disabled","disabled");
    }else if(timeType==4){
        $("#createStartTime,#createEndTime").removeAttr('disabled');
    }else if(timeType==0){
        $("#createStartTime,#createEndTime").attr("disabled","disabled");
        $("#createStartTime").val('');
        $("#createEndTime").val('');
    }

    ShopInfoMgr.table.queryParams={'condition':$("#condition").val(),'timeType':timeType,'startDate':$("#createStartTime").val(),'endDate':$("#createEndTime").val()};
    ShopInfoMgr.table.refresh();
};
ShopInfoMgr.getDate = function (dates) {
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
$(function () {
    $("#createStartTime").val(ShopInfoMgr.getDate(0));
    $("#createEndTime").val(ShopInfoMgr.getDate(0));
    $("#createStartTime,#createEndTime").attr("disabled","disabled");

    // 添加选中时间快捷键样式
    $('#timeType').on('click', 'button', function() {
        $(this).addClass('gun-btn-active').siblings().removeClass("gun-btn-active");
    });

    $("#createStartTime").datepicker({
        keyboardNavigation: false,
        forceParse: false,
        autoclose: true,
        endDate: ShopInfoMgr.getDate(0)
    }).on("changeDate", function (e) {
        $("#createEndTime").datepicker("setStartDate", e.date);
        ShopInfoMgr.search();
    });

    $("#createEndTime").datepicker({
        keyboardNavigation: false,
        forceParse: false,
        autoclose: true,
        endDate: ShopInfoMgr.getDate(0)
    }).on("changeDate", function (e) {
        $("#createStartTime").datepicker("setEndDate", e.date);
        ShopInfoMgr.search();
    });

    var defaultColunms = ShopInfoMgr.initColumn();
    var table = new BSTable(ShopInfoMgr.id, "/platform/cash/systemCash/listPlatformBalanceFlow4Page", defaultColunms);
    table.queryParams={'timeType':$("#timeType").find('.gun-btn-active')[0].id};
    table.setPaginationType("server");
    ShopInfoMgr.table = table.init();
});
