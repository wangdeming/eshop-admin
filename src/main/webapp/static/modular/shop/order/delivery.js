$(function () {
    getGoodsDetails();
    getListExpressCompanys();
    getCountOrderGoodsStatus();
    $("#selectAll").change(function() {
        if ($("#selectAll").is(":checked")){
            $("input[name='selectOneItem']").each(function (index,item) {
                $(item).prop("checked",true);
            });
        }else {
            $("input[name='selectOneItem']").each(function (index,item) {
                $(item).prop("checked",false);
            });
        }
    });
    $("input[name='selectOneItem']").each(function (index,item) {
        $(item).click(function () {
            if ($("input[name='selectOneItem']").length == $("input[name='selectOneItem']:checked").length){
                $("#selectAll").prop("checked",true);
            }else {
                $("#selectAll").prop("checked",false);
            }
        });
    });
});
function getGoodsDetails() {
    var orderId = sessionStorage.getItem("orderIdOfDelivery");
    var ajax = new $ax(Feng.ctxPath + "/shop/order/orderDetail", function (data) {
        if(data.code==200){
            $("#consigneeName").text(data.data[0].consigneeName);
            $("#consigneePhone").text(data.data[0].consigneePhone);
            var address = data.data[0].province + data.data[0].city + data.data[0].district + data.data[0].address;
            $("#consigneeAddress").text(address);
            var goods = data.data[0].goods;
            var orderTbodyHtml = '';
            for (var i=0;i<goods.length;i++){
                var serviceStatus = goods[i].serviceStatus;
                var deliveryStatus = goods[i].deliveryStatus;
                var deliveryStatusStr = '';
                var checkboxHtml = '';
                if (serviceStatus == 1 || serviceStatus == 4){
                    if(deliveryStatus == 1){
                        deliveryStatusStr = '待发货';
                        checkboxHtml = '<input type="checkbox" name="selectOneItem" value="'+ goods[i].id +'"/>';
                    }else {
                        deliveryStatusStr = '已发货';
                    }
                }else if(serviceStatus == 2) {
                    deliveryStatusStr = '<a onclick="goToRefund('+ goods[i].id +')">退款中</a>';
                }else {
                    deliveryStatusStr = '<a onclick="goToRefund('+ goods[i].id +')">已退款</a>';
                }
                var expressCompany = '';
                var expressNo = '';
                if (goods[i].expressCompany != null){
                    expressCompany = goods[i].expressCompany;
                }
                if (goods[i].expressNo != null){
                    expressNo = goods[i].expressNo;
                }
                orderTbodyHtml += '<tr style="height: 60px;line-height: 60px;text-align: center;"><td>'
                    + checkboxHtml +'</td><td><img style="width: 60px;height: 40px;margin-left: 10px;margin-right: 5px;float: left;" src="'
                    + goods[i].goodsImg +'"><div style="width: calc(100% - 80px); height: 20px;line-height: 20px;﻿white-space: nowrap;overflow: hidden;text-overflow: ellipsis;">'
                    + goods[i].goodsName +'</div><div style="height: 20px;line-height: 20px;color: red;text-align: left;">'
                    + goods[i].goodsSpecs +'</div></td><td>'
                    + goods[i].nums +'件</td><td>'+ deliveryStatusStr +'</td><td><div style="height: 20px;line-height: 20px;">'
                    + expressCompany +'</div><div style="height: 40px;line-height: 20px;width: 100%;word-wrap:break-word;">'+ expressNo +'</div></td></tr>';
            }
            $("#orderTbody").empty().append(orderTbodyHtml);
        }else{
            Feng.error(data.message + "!");
        }
    }, function (data) {
        Feng.error(data.responseJSON.message + "!");
    });
    ajax.set("orderId",orderId);
    ajax.start();
}
function getListExpressCompanys() {
    var ajax = new $ax(Feng.ctxPath + "/shop/order/listExpressCompanys", function (data) {
        if(data.code==200){
            var list = data.data;
            var optionsHtml = '';
            for (var i=0;i<list.length;i++){
                optionsHtml += '<option>'+ list[i].name +'</option>'
            }
            $("#newExpressCompany").empty().append(optionsHtml);
        }else{
            Feng.error(data.message + "!");
        }
    }, function (data) {
        Feng.error(data.responseJSON.message + "!");
    });
    ajax.start();
}
function sendGoods() {
    var orderId = sessionStorage.getItem("orderIdOfDelivery");
    var orderGoodsArr = [];
    $("input[name='selectOneItem']:checked").each(function (index,item) {
        orderGoodsArr.push($(item).val());
    });
    var orderGoodsIds = orderGoodsArr.join(',');
    var expressCompany = $("#newExpressCompany option:selected").text();
    var expressNo = $("#newExpressNo").val();
    if(orderGoodsIds == ''){
        Feng.error("请选择商品！");
        return false;
    }
    if(expressNo ==''){
        Feng.error("请填写运单号！");
        return false;
    }
    var ajax = new $ax(Feng.ctxPath + "/shop/order/deliver", function (data) {
        if(data.code==200){
            Feng.success(data.message + "!");
            window.location.reload();
        }else{
            Feng.error(data.message + "!");
        }
    }, function (data) {
        Feng.error(data.responseJSON.message + "!");
    });
    ajax.set("orderId",orderId);
    ajax.set("orderGoodsIds",orderGoodsIds);
    ajax.set("expressCompany",expressCompany);
    ajax.set("expressNo",expressNo);
    ajax.start();
}
function closePage() {
    window.history.go(-1);
}
function getCountOrderGoodsStatus() {
    var orderId = sessionStorage.getItem("orderIdOfDelivery");
    var ajax = new $ax(Feng.ctxPath + "/shop/order/countOrderGoodsStatus", function (data) {
        if(data.code==200){
            var waitDelivery = data.data.waitDelivery;
            var delivered = data.data.delivered;
            var refunding = data.data.refunding;
            var refunded = data.data.refunded;
            var orderStatusAndNumsStr = '';
            if (waitDelivery != null){
                orderStatusAndNumsStr += '待发货'+waitDelivery;
            }
            if (delivered != null){
                orderStatusAndNumsStr += ', 已发货'+delivered;
            }
            if (refunding != null){
                orderStatusAndNumsStr += ', 退款中'+refunding;
            }
            if (refunded != null){
                orderStatusAndNumsStr += '退款完成'+refunded;
            }
            $("#orderStatusAndNums").text(orderStatusAndNumsStr);
        }else{
            Feng.error(data.message + "!");
        }
    }, function (data) {
        Feng.error(data.responseJSON.message + "!");
    });
    ajax.set("orderId",orderId);
    ajax.start();
}
function goToRefund(goodsId) {
    sessionStorage.setItem("refundDetailsOrderId",goodsId);
    window.location.href = Feng.ctxPath + '/shop/order/refund';
}