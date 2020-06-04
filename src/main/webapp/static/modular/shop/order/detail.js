$(function () {
    getGoodsDetails();
});
function getGoodsDetails() {
    var orderId = sessionStorage.getItem("orderDetailsOrderId");
    var ajax = new $ax(Feng.ctxPath + "/shop/order/orderDetail", function (data) {
        if(data.code==200){
            $("#orderNo").text(data.data[0].orderNo);
            $("#userId").text(data.data[0].userId);
            $("#consigneeName").text(data.data[0].consigneeName);
            $("#consigneePhone").text(data.data[0].consigneePhone);
            var address = data.data[0].province + data.data[0].city + data.data[0].district + data.data[0].address;
            $("#address").text(address);
            var cancelRemark = '';
            if(data.data[0].cancelRemark!=null){
                cancelRemark = data.data[0].cancelRemark;
            }
            switch (data.data[0].status){
                case 1:
                    var orderStatusHtml =  '<div class="statusDiv">等待买家付款</div>';
                    $("#orderStatus").empty().append(orderStatusHtml);
                    $("#circle1").text("√");
                    $("#line1").css("background-color","#f2f8f9");
                    $("#line2").css("background-color","#f2f8f9");
                    $("#line3").css("background-color","#f2f8f9");
                    $("#time1").text(data.data[0].createdTime);
                    break;
                case 2:
                    var orderStatusHtml = '<div class="statusDiv">买家已付款，等待商家发货。</div>'
                        +'<div>注：（买家已付款至电商平台结算账户，请尽快发货，否则买家有权申请退款。）</div>'
                        +'<div><button type="button" class="btn btn-primary" onclick="sendGoods()" style="width: 120px;height: 34px;color: #FFFFFF;margin-top: 10px;">发货</button></div>';
                    $("#orderStatus").empty().append(orderStatusHtml);
                    $("#circle1").text("√");
                    $("#circle2").text("√");
                    $("#line2").css("background-color","#f2f8f9");
                    $("#line3").css("background-color","#f2f8f9");
                    $("#time1").text(data.data[0].createdTime);
                    $("#time2").text(data.data[0].paymentTime);
                    break;
                case 3:
                    var orderStatusHtml =  '<div class="statusDiv">等待用户收货</div>';
                    $("#orderStatus").empty().append(orderStatusHtml);
                    $("#circle1").text("√");
                    $("#circle2").text("√");
                    $("#circle3").text("√");
                    $("#line3").css("background-color","#f2f8f9");
                    $("#time1").text(data.data[0].createdTime);
                    $("#time2").text(data.data[0].paymentTime);
                    $("#time3").text(data.data[0].deliveryTime);
                    break;
                case 4:
                    var orderStatusHtml =  '<div class="statusDiv">交易完成</div>';
                    $("#orderStatus").empty().append(orderStatusHtml);
                    $("#circle1").text("√");
                    $("#circle2").text("√");
                    $("#circle3").text("√");
                    $("#circle4").text("√");
                    $("#time1").text(data.data[0].createdTime);
                    $("#time2").text(data.data[0].paymentTime);
                    $("#time3").text(data.data[0].deliveryTime);
                    $("#time4").text(data.data[0].receiveTime);
                    break;
                case 5:
                    var orderStatusHtml =  '<div class="statusDiv">已取消</div>'
                        +'<div>'+ cancelRemark +'</div>';
                    $("#orderSchedule").hide();
                    $("#orderStatus").empty().append(orderStatusHtml);
                    break;
                case 6:
                    var orderStatusHtml =  '<div class="statusDiv">售后进行中</div><div>注：请尽快前去处理，超时未处理所造成的损失由商家自行承担。</div>';
                    $("#orderSchedule").hide();
                    $("#orderStatus").empty().append(orderStatusHtml);
                    break;
                case 7:
                    var orderStatusHtml =  '<div class="statusDiv">交易关闭</div><div>注：订单内商品均已完成退款</div>';
                    $("#orderSchedule").hide();
                    $("#orderStatus").empty().append(orderStatusHtml);
                    $("#refundAmountDiv").show();
                    $("#settleAmountDiv").show();
                    break;
                case 8:
                    var orderStatusHtml =  '<div class="statusDiv">交易完成</div>';
                    $("#orderStatus").empty().append(orderStatusHtml);
                    $("#circle1").text("√");
                    $("#circle2").text("√");
                    $("#circle3").text("√");
                    $("#circle4").text("√");
                    $("#time1").text(data.data[0].createdTime);
                    $("#time2").text(data.data[0].paymentTime);
                    $("#time3").text(data.data[0].deliveryTime);
                    $("#time4").text(data.data[0].receiveTime);
                    break;
            }
            var goods = data.data[0].goods;
            var orderTbodyHtml = '';
            var goodsAmountPrice = 0;
            for (var i=0;i<goods.length;i++){
                var amountPrice = goods[i].unitPrice * goods[i].nums.toFixed(2);
                goodsAmountPrice += amountPrice;
                var orderStatusStr = '';
                if (data.data[0].status == 1 || data.data[0].status == 5){
                    orderStatusStr = '待付款';
                }else {
                    if(goods[i].serviceStatus == 2){
                        orderStatusStr = '<a onclick="goToRefund('+ goods[i].id +')">买家发起退款</a>';
                    }else if(goods[i].serviceStatus == 3){
                        orderStatusStr = '<a onclick="goToRefund('+ goods[i].id +')">退款完成</a>';
                    }else {
                        if(goods[i].deliveryStatus == 1){
                            orderStatusStr = '待发货';
                        }else if(goods[i].deliveryStatus == 2) {
                            orderStatusStr = '已发货';
                        }else {
                            orderStatusStr = '已收货';
                        }
                    }
                }
                var goodsSpecs = '';
                if (goods[i].goodsSpecs != null){
                    goodsSpecs = goods[i].goodsSpecs;
                }
                if(goods[i].deliveryStatus == 1){
                    orderTbodyHtml += '<tr style="height: 60px;line-height: 60px;"><td><img style="width: 60px;height: 40px;margin-left: 10px;margin-right: 5px;float: left;" src="'
                        + goods[i].goodsImg +'"><div style="width: calc(100% - 80px); height: 20px;line-height: 20px;﻿white-space: nowrap;overflow: hidden;text-overflow: ellipsis;">'
                        + goods[i].goodsName +'</div><div style="height: 20px;line-height: 20px;color: red;">'
                        + goodsSpecs +'</div></td><td style="text-align: center;">'
                        + goods[i].unitPrice +'</td><td style="text-align: center;">'
                        + goods[i].nums +'</td><td style="text-align: center;">'+ amountPrice +'</td><td style="text-align: center;">'+ orderStatusStr +'</td></tr>';
                }else {
                    var expressCompany = '';
                    if (goods[i].expressCompany != null){
                        expressCompany = goods[i].expressCompany;
                    }
                    var expressNo = '';
                    if (goods[i].expressNo != null){
                        expressNo = goods[i].expressNo;
                    }
                    orderTbodyHtml += '<tr style="height: 40px;line-height: 40px;background-color: #eeeeee;">'
                        +'<td colspan="5"><span style="margin-left: 20px;">包裹-'+ parseInt(i+1) +'</span><span style="margin-left: 20px;">'
                        + expressCompany +'</span><span style="margin-left: 20px;">'
                        + expressNo +'</span></td></tr><tr style="height: 60px;line-height: 60px;">'
                        +'<td><img style="width: 60px;height: 40px;margin-left: 10px;margin-right: 5px;float: left;" src="'
                        + goods[i].goodsImg +'"><div style="width: calc(100% - 80px); height: 20px;line-height: 20px;﻿white-space: nowrap;overflow: hidden;text-overflow: ellipsis;">'
                        + goods[i].goodsName +'</div><div style="height: 20px;line-height: 20px;color: red;">'
                        + goodsSpecs +'</div></td><td style="text-align: center;">'
                        + goods[i].unitPrice +'</td><td style="text-align: center;">'
                        + goods[i].nums +'</td><td style="text-align: center;">'+ amountPrice +'</td><td style="text-align: center;">'+ orderStatusStr +'</td></tr>';
                }
            }
            $("#orderTbody").empty().append(orderTbodyHtml);
            $("#goodsAmountPrice").text(goodsAmountPrice.toFixed(2));
            $("#expressFee").text(data.data[0].expressFee);
            $("#couponAmount").text(data.data[0].couponAmount);
            $("#orderPrice").text(data.data[0].orderPrice);
            $("#refundAmount").text(data.data[0].refundAmount);
            $("#settleAmount").text(data.data[0].settleAmount);
        }else{
            Feng.error(data.message + "!");
        }
    }, function (data) {
        Feng.error(data.responseJSON.message + "!");
    });
    ajax.set("orderId",orderId);
    ajax.start();
}
function sendGoods() {
    var ajax = new $ax(Feng.ctxPath + "/shop/address/list", function (data) {
        if(data.total!=0){
            var orderId = sessionStorage.getItem("orderDetailsOrderId");
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
function goToRefund(goodsId) {
    console.log(goodsId);
    sessionStorage.setItem("refundDetailsGoodsId",goodsId);
    window.location.href = Feng.ctxPath + '/shop/order/refund';
}