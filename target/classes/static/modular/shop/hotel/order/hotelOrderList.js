/**
 * 订单列表表格的列
 */
var orderList = {
    id: "orderListTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
orderList.initColumn = function () {
    return [
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '订单编号', field: 'orderNo', align: 'left', valign: 'middle' ,sortable: false},
        {title: '下单时间', field: 'createdTime', align: 'left', valign: 'middle' ,sortable: false},
        {title: '房间名称', field: 'roomName', align: 'left', valign: 'middle' ,sortable: false},
        {title: '数量', field: 'roomNumber', align: 'left', valign: 'middle' ,sortable: false},
        {title: '入住时间', field: 'checkInDate', align: 'left', valign: 'middle' ,sortable: false},
        {title: '离店时间', field: 'checkOutDate', align: 'left', valign: 'middle' ,sortable: false},
        {title: '入住人', field: 'realname', align: 'left', valign: 'middle',sortable: false},
        {title: '联系电话', field: 'mobile', align: 'left', valign: 'middle',sortable: false},
        {title: '订单状态', field: 'status', align: 'left', valign: 'middle',sortable: false,
            formatter: function (value, row, index) {
                if (row.status == 1){
                    return "待付款";
                }else if(row.status == 2) {
                    return "待确认";
                }else if(row.status == 3) {
                    return "待使用";
                }else if(row.status == 4 || row.status == 6) {
                    return "已消费";
                }else if(row.status == 5) {
                    return "已取消";
                }else if(row.status == 7) {
                    return "已过期";
                }
            }
        },
        {title: '操作', field: 'id', visible: true, align: 'left', valign: 'middle',width:'20%',
            formatter: function (value, row, index) {
                if (row.status == 1){
                    return '<a style="margin-right: 30px;" id="'+row.id+'" onclick="orderList.hotelOrderDetails(this)">查看</a>';
                }else if(row.status == 2) {
                    return '<a style="margin-right: 30px;" id="'+row.id+'" onclick="orderList.confirm(this)">立即确认</a>'
                        + '<a style="margin-right: 30px;" id="'+row.id+'" onclick="orderList.cancel(this)">取消订单</a>'
                        + '<a id="'+row.id+'" onclick="orderList.hotelOrderDetails(this)">查看</a>';
                }else if(row.status == 3) {
                    return '<a style="margin-right: 30px;" id="'+row.id+'" onclick="orderList.confirmCheckIn(this)">确认入住</a>'
                        + '<a id="'+row.id+'" onclick="orderList.hotelOrderDetails(this)">查看</a>';
                }else if(row.status == 4) {
                    return '<a style="margin-right: 30px;" id="'+row.id+'" onclick="orderList.hotelOrderDetails(this)">查看</a>';
                }else {
                    return '<a id="'+row.id+'" onclick="orderList.hotelOrderDetails(this)">查看</a>';
                }
            }
        }

    ];
};

/**
 * 查询店铺信息管理列表
 */
orderList.search = function () {
    var queryData = {};
    queryData['realname'] = $("#realname").val();
    queryData['roomName'] = $("#roomName").val();
    queryData['orderNo'] = $("#orderNo").val();
    queryData['checkInDate'] = $("#checkInDate").val();
    queryData['createdTimeStart'] = $("#createStartTime").val();
    queryData['createdTimeEnd'] = $("#createEndTime").val();
    queryData['status'] = $("#stateType").val();
    $('#'+orderList.id).bootstrapTable('refreshOptions', {pageNumber: 1});
    orderList.table.refresh({query: queryData});
};

$(function () {
    $("#orderStatus .btn").click(function () {
        $("#orderStatus .btn").removeClass('active');
        $(this).addClass('active');
        var stateType=$(this).attr('data-calType');
        $("#stateType").val(stateType);
        //统计类型（1-待付款；2-待确认；3-待使用；4-已消费；5-已取消；6-已完成；7-已过期）
        orderList.search();
    });

    var defaultColunms = orderList.initColumn();
    var table = new BSTable(orderList.id, "/shop/hotel/hotleOrderList", defaultColunms);
    table.setPaginationType("server");
    table.queryParams={'status':$("#stateType").val()};
    orderList.table = table.init();
});

/**
 * 立即确认
 */
orderList.confirm = function (e) {
    var hotelOrderId = e.id;
    var operation = function(){
        var ajax = new $ax(Feng.ctxPath + "/shop/hotel/confirm", function () {
            Feng.success("确认成功!");
            orderList.table.refresh();
        }, function (data) {
            Feng.error("确认失败!" + data.message + "!");
        });
        ajax.set("hotelOrderId", hotelOrderId);
        ajax.start();
    };
    Feng.confirm("是否确认?", operation);
};

/**
 * 确认入住
 */
orderList.confirmCheckIn = function (e) {
    var hotelOrderId = e.id;
    var operation = function(){
        var ajax = new $ax(Feng.ctxPath + "/shop/hotel/confirmCheckIn", function () {
            Feng.success("确认成功!");
            orderList.table.refresh();
        }, function (data) {
            Feng.error("确认失败!" + data.message + "!");
        });
        ajax.set("hotelOrderId", hotelOrderId);
        ajax.start();
    };
    Feng.confirm("确认后，该订单将核销?", operation);
};

/**
 * 取消订单
 */
orderList.cancel = function (e) {
    var hotelOrderId = e.id;
    var operation = function(){
        var ajax = new $ax(Feng.ctxPath + "/shop/hotel/cancelOrder", function () {
            Feng.success("取消成功!");
            orderList.table.refresh();
        }, function (data) {
            Feng.error("取消失败!" + data.message + "!");
        });
        ajax.set("hotelOrderId", hotelOrderId);
        ajax.start();
    };
    Feng.confirm("确定取消该订单?", operation);
};

/**
 * 查看详情
 */
orderList.hotelOrderDetails = function (e) {
    sessionStorage.setItem("hotelOrderId",e.id);
    window.location.href = Feng.ctxPath + '/shop/hotel/hotelOrderDetail';
};
