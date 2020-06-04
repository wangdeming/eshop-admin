var offset = 0;//该页的起始记录，偏移量，如果是第一页就是（1-1）*limit，如果第二页就是（2-1）*limit，第n页就是，（n-1）*limit
var limit = 10;//每页显示记录数
var createdTimeStart = '';//下单时间查询起始时间
var createdTimeEnd = '';//下单时间查询终止时间
var goodsName = '';//商品名称
var goodsId = '';//商品ID
var shopName = '';//店铺名称
var shopId = '';//店铺ID
var orderStatus = 0;//订单的状态（0-全部；1-待付款；2-待发货；3-待收货；4-交易完成；5-已取消；6-售后中；7-交易关闭）
var pageNum = 1;
$(function () {
    getOrderList();
    $("#orderStatus button").each(function (index,item) {
        $(item).click(function () {
            $("#orderStatus button").removeClass("active");
            $(item).addClass("active");
            orderStatus = $(item).attr("data-calType");
            pageNum = 1;
            getOrderList();
        });
    });

    // 添加选中时间快捷键样式
    $('#timeType').on('click', 'button', function() {
        $(this).addClass('gun-btn-active').siblings().removeClass("gun-btn-active");
    });

    $('.input-daterange').datepicker({
        keyboardNavigation: false,
        forceParse: false,
        autoclose: true
    });

    // 监听开始时间
    $("#createStartTime").datepicker().on("changeDate",function() {
        getOrderList();
    });

    // 监听结束时间
    $("#createEndTime").datepicker().on("changeDate",function(){
        getOrderList();
    });
});
function orderSearch() {
    pageNum = 1;
    getOrderList();
}
function getDate (dates) {
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
}
function getOrderList(timeType) {
    if (timeType == null) {
        timeType = $("#timeType").find('.gun-btn-active')[0].id;
    }
    if(timeType==1){
        $("#createStartTime").val(getDate(0));
        $("#createEndTime").val(getDate(0));
        $("#createStartTime,#createEndTime").attr("disabled","disabled");
    }else if(timeType==2){
        $("#createStartTime").val(getDate(-1));
        $("#createEndTime").val(getDate(-1));
        $("#createStartTime,#createEndTime").attr("disabled","disabled");
    }else if(timeType==3){
        $("#createStartTime").val(getDate(-6));
        $("#createEndTime").val(getDate(0));
        $("#createStartTime,#createEndTime").attr("disabled","disabled");
    }else if(timeType==4){
        $("#createStartTime").val(getDate(-29));
        $("#createEndTime").val(getDate(0));
        $("#createStartTime,#createEndTime").attr("disabled","disabled");
    } else if(timeType==5){
        $("#createStartTime,#createEndTime").removeAttr('disabled');
    }else if(timeType==0){
        $("#createStartTime,#createEndTime").attr("disabled","disabled");
        $("#createStartTime").val('');
        $("#createEndTime").val('');
    }

    var orderNo = '';//订单编号
    var consigneePhone = '';//收件人手机号
    var consigneeName = '';//收件人姓名
    var servicePhone = '';//售后人手机号
    if($("#orderKey").val() != ''){
        var keyValue = parseInt($("#chooseOrderForKey option:selected").val());
        switch (keyValue){
            case 1:
                orderNo = $("#orderKey").val();
                break;
            case 2:
                consigneePhone = $("#orderKey").val();
                break;
            case 3:
                consigneeName = $("#orderKey").val();
                break;
            case 4:
                servicePhone = $("#orderKey").val();
                break;
        }
    }
    offset = (pageNum - 1) * limit;
    createdTimeStart = $("#createStartTime").val();
    createdTimeEnd = $("#createEndTime").val();
    goodsName = $("#goodsName").val();
    goodsId = $("#goodsId").val();
    shopName = $("#shopName").val();
    shopId = $("#shopId").val();
    var orderData = {};
    orderData.offset = offset;
    orderData.limit = limit;
    if (orderNo != '') {
        orderData.orderNo = orderNo;
    }
    if (consigneePhone  != '') {
        orderData.consigneePhone  = consigneePhone ;
    }
    if (consigneeName  != '') {
        orderData.consigneeName  = consigneeName ;
    }
    if (servicePhone  != '') {
        orderData.servicePhone  = servicePhone ;
    }
    if (createdTimeStart != '') {
        orderData.createdTimeStart = createdTimeStart;
    }
    if (createdTimeEnd != '') {
        orderData.createdTimeEnd = createdTimeEnd;
    }
    if (goodsName != '') {
        orderData.goodsName = goodsName;
    }
    if (goodsId != '') {
        orderData.goodsId = goodsId;
    }
    if (shopName != '') {
        orderData.shopName = shopName;
    }
    if (shopId != '') {
        orderData.shopId = shopId;
    }
    orderData.orderStatus = orderStatus;
    $.ajaxSetup({crossDomain: true, xhrFields: {withCredentials: true}});
    $.ajax({
        url: Feng.ctxPath +"/platform/ordercenter/orderList",
        type: 'POST',
        dataType: 'json',
        data: orderData,
        async: false,
        success: function (data) {
            if(data.code != 200){
                Feng.error(data.message);
            }else{
                var orderDate = data.data.rows;
                var orderTbodyHtml = '';
                for (var i=0;i<orderDate.length;i++){
                    orderTbodyHtml +='<tr style="height: 30px;line-height: 30px;"><td colspan="7"><span style="float: left;color: red;margin-left: 20px;">订单号:'
                        + orderDate[i].orderNo +'</span><span style="float: left;color: red;margin-left: 20px;">店铺名称:'
                        + orderDate[i].shopName +'</span><span style="float: left;color: red;margin-left: 20px;">店铺ID:'
                        + orderDate[i].shopId +'</span><a onclick="checkOrderDetails('+ orderDate[i].id +')" style="float: right;margin-right: 80px;">查看订单详情</a></td></tr>';
                    for(var j=0;j<orderDate[i].goods.length;j++){
                        var goodsItem = orderDate[i].goods[j];
                        var serviceStatus = '';
                        if(goodsItem.serviceStatus == 2){
                            serviceStatus = '买家发起退款';
                        }else if(goodsItem.serviceStatus == 3){
                            serviceStatus = '退款完成';
                        }else if(goodsItem.serviceStatus == 4){
                            serviceStatus = '退款关闭';
                        }else {

                        }
                        var sendGoodsHtml = '';
                        if (orderDate[i].status == 2){
                            sendGoodsHtml = '<span style="display: inline-block;width: 100%;line-height: 30px;">等待商家发货</span>';
                        }else if(orderDate[i].status == 1){
                            sendGoodsHtml = '等待买家付款';
                        }else if (orderDate[i].status == 3){
                            sendGoodsHtml = '等待用户收货';
                        }else if (orderDate[i].status == 4 || orderDate[i].status == 8){
                            sendGoodsHtml = '交易完成';
                        }else if (orderDate[i].status == 5){
                            sendGoodsHtml = '已取消';
                        }else if (orderDate[i].status == 6){
                            sendGoodsHtml = '售后中';
                        }else {
                            sendGoodsHtml = '交易关闭';
                        }
                        if(j==0){
                            orderTbodyHtml += '<tr style="height: 60px;line-height: 60px;"><td style="display: flex;border: none;padding: 10px;"><img style="width: 60px;height: 40px;margin-right: 5px;float: left;" src="'
                                + goodsItem.goodsImg +'"><div style="line-height: 20px;"><div style="width: 120px;height: 20px;line-height: 20px;white-space: nowrap;overflow: hidden;text-overflow: ellipsis;">'
                                + goodsItem.goodsName +'</div><div style="margin-top: 5px;color: red;">'+ goodsItem.goodsSpecs +'</div></div></td><td style="text-align: center;"><div style="height: 20px;line-height: 20px;">'
                                + goodsItem.unitPrice +'元</div><div style="height: 20px;line-height: 20px;">('+ goodsItem.nums +'件)</div></td><td style="text-align: center;"><a onclick="checkRefundDetail('+ goodsItem.id +','+ orderDate[i].id +')">'
                                + serviceStatus +'</a></td><td rowspan="'+ orderDate[i].goods.length +'"><div style="height: 20px;line-height: 20px;margin-left: 20px;">买家账户名:'
                                + orderDate[i].userId +'</div><div style="height: 20px;line-height: 20px;margin-left: 20px;">收货人姓名:'+ orderDate[i].consigneeName +'</div><div style="height: 20px;line-height: 20px;margin-left: 20px;">收货人手机号:'
                                + orderDate[i].consigneePhone +'</div></td><td style="text-align: center;" rowspan="'+ orderDate[i].goods.length +'">'
                                + orderDate[i].createdTime +'</td><td style="text-align: center;" rowspan="'+ orderDate[i].goods.length +'">'
                                + sendGoodsHtml +'</td><td style="text-align: center;" rowspan="'+ orderDate[i].goods.length +'"><div style="height: 20px;line-height: 20px;">'
                                + orderDate[i].orderPrice +'元</div><div style="height: 20px;line-height: 20px;">(含运费:'+ orderDate[i].expressFee +'元)</div></td></tr>';
                        }else{
                            orderTbodyHtml += '<tr style="height: 50px;line-height: 50px;"><td style="display: flex;padding: 10px;border-top: 1px solid #808080;border-right: none;border-bottom: none;border-left: none;" id="borderSty"><img style="width: 60px;height: 40px;margin-right: 5px;float: left;" src="'
                                + goodsItem.goodsImg +'"><div style="line-height: 20px;"><div>'
                                + goodsItem.goodsName +'</div><div style="margin-top: 5px;color: red;">'+ goodsItem.goodsSpecs +'</div></div></td><td style="text-align: center;"><div style="height: 20px;line-height: 20px;" >'
                                + goodsItem.unitPrice +'元</div><div style="height: 20px;line-height: 20px;">('+ goodsItem.nums +'件)</div></td><td style="text-align: center;"><a onclick="checkRefundDetail('+ goodsItem.id +','+ orderDate[i].id +')">'
                                + serviceStatus +'</a></td></tr>';
                        }
                    }
                }
                $("#borderSty:first-child").css("border", "none");
                $("#orderTbody").empty().append(orderTbodyHtml);
                var total = data.data.total;
                var totalNum = parseInt(total / limit);
                if(total % limit != 0){
                    totalNum += 1;
                }
                $("#page").paging({
                    pageNum: pageNum, // 当前页面
                    totalNum: totalNum, // 总页码
                    totalList: total, // 记录总数量
                    callback: function(num) { //回调函数
                        pageNum = num;
                        getOrderList();
                    }
                });
            }
        }
    });
}

function sendGoods(orderId) {
    var ajax = new $ax(Feng.ctxPath + "/shop/address/list", function (data) {
        if(data.total!=0){
            sessionStorage.setItem("orderIdOfDelivery",orderId);
            window.location.href=Feng.ctxPath + '/shop/order/delivery/' + orderId;
        }else{
            var operation = function(){
                top.document.querySelectorAll('#side-menu li a').forEach(function(e){
                    if (e.getAttribute('href').indexOf('/shop/address') != -1 ) {$(e).closest('ul').prev()[0].click();
                        e.click();
                    }
                });
            };
            Feng.confirm("你还未设置退货地址,请设置后再发货.", operation);
        }
    }, function (data) {
        Feng.error(data.responseJSON.message + "!");
    });
    ajax.set("offset",0);
    ajax.set("limit",14);
    ajax.start();
}
function checkOrderDetails(orderId) {
    sessionStorage.setItem("orderDetailsOrderId",orderId);
    window.location.href = Feng.ctxPath + '/platform/ordercenter/goodsOrderDetail';
}
function checkRefundDetail(goodsId,orderId) {
    sessionStorage.setItem("orderDetailsOrderId",orderId);
    sessionStorage.setItem("refundDetailsGoodsId",goodsId);
    sessionStorage.setItem("orderIdOfDelivery",orderId);
    window.location.href = Feng.ctxPath + '/platform/ordercenter/goodsRefundDetail';
}