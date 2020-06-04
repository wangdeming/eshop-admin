/**
 * 订单列表初始化
 */
var orderList = {
    id: "orderListTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};
/**
 * 订单列表表格的列
 */
orderList.initColumn = function () {
    return [
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '序号', field: '', align: 'left', valign: 'middle' ,sortable: false,
            formatter: function (value, row, index) {
                var pageSize=$('#orderListTable').bootstrapTable('getOptions').pageSize;//通过表的#id 可以得到每页多少条
                var pageNumber=$('#orderListTable').bootstrapTable('getOptions').pageNumber;//通过表的#id 可以得到当前第几页
                return pageSize * (pageNumber - 1) + index + 1;//返回每条的序号： 每页条数 * （当前页 - 1 ）+ 序号
            }
        },
        {title: '订单编号', field: 'orderNo', align: 'left', valign: 'middle' ,sortable: false},
        {title: '店铺名称', field: 'shopName', align: 'left', valign: 'middle' ,sortable: false},
        {title: '店铺ID', field: 'shopId', align: 'left', valign: 'middle' ,sortable: false},
        {title: '下单时间', field: 'createdTime', align: 'left', valign: 'middle' ,sortable: false},
        {title: '房间名称', field: 'roomName', align: 'left', valign: 'middle' ,sortable: false},
        {title: '数量', field: 'roomNumber', align: 'left', valign: 'middle' ,sortable: false},
        {title: '入住时间', field: 'checkInDate', align: 'left', valign: 'middle' ,sortable: false},
        {title: '离店时间', field: 'checkOutDate', align: 'left', valign: 'middle' ,sortable: false},
        {title: '入住人', field: 'realname', align: 'left', valign: 'middle',sortable: false, width:'10%'},
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
                return '<a style="margin-right: 30px;" id="'+row.id+'" onclick="orderList.hotelOrderDetails(this)">查看</a>';
            }
        }
    ];
};

/**
 * 查看详情
 */
orderList.hotelOrderDetails = function (e) {
    sessionStorage.setItem("hotelOrderId",e.id);
    window.location.href = Feng.ctxPath + '/platform/ordercenter/hotelOrderDetail';
};

/**
 * 订单列表表格的列
 */
orderList.getDate = function (dates) {
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
    $("#orderStatus .btn").click(function () {
        $("#orderStatus .btn").removeClass('active');
        $(this).addClass('active');
        var stateType=$(this).attr('data-calType');
        $("#stateType").val(stateType);
        //统计类型（1-待付款；2-待确认；3-待使用；4-已消费；5-已取消；6-已完成；7-已过期）
        orderList.search();
    });

    var defaultColunms = orderList.initColumn();
    var table = new BSTable(orderList.id, "/platform/ordercenter/hotelOrderList", defaultColunms);

    table.queryParams = {
        createStartTime: $("#createStartTime").val(),
        createEndTime: $("#createEndTime").val(),
        status:$("#stateType").val(),
    };

    orderList.table = table.init();

});

orderList.search = function () {
    var queryData = {};
    queryData['realname'] = $("#realname").val();
    queryData['roomName'] = $("#roomName").val();
    queryData['orderNo'] = $("#orderNo").val();
    queryData['checkInDate'] = $("#checkInDate").val();
    queryData['createdTimeStart'] = $("#createStartTime").val();
    queryData['createdTimeEnd'] = $("#createEndTime").val();
    queryData['status'] = $("#stateType").val();
    queryData['shopName'] = $("#shopName").val();
    queryData['shopId'] = $("#shopId").val();
    orderList.table.refresh({query: queryData});
};
