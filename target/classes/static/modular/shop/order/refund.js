$(function () {
    $("#zoomInImageDiv").hide();
    getRefundDetail();
});
function getRefundDetail() {
    var orderGoodsId = sessionStorage.getItem("refundDetailsGoodsId");
    var ajax = new $ax(Feng.ctxPath + "/shop/order/getRefundDetailList", function (data) {
        if(data.code==200){
            var goods = data.data[0].goods;
            $("#goodsImg").attr("src",goods.goodsImg);
            $("#goodsName").text(goods.goodsName);
            $("#goodsSpecs").text(goods.goodsSpecs);
            $("#num").text('x'+data.data[0].goodsNum);
            $("#expressFee").text(data.data[0].expressFee);
            var refundType = '仅退款';
            if(data.data[0].refundType != 1){
                refundType = '退货退款';
            }
            $("#refundType").text(refundType);
            $("#reason").text(data.data[0].reason);
            $("#amount").text(data.data[0].amount);
            $("#refundOrderNo").text(data.data[0].refundOrderNo);
            $("#consigneePhone").text(data.data[0].phone);
            $("#expressNo").text(data.data[0].orderNo);
            var refundStatusDetailsStr = '';
            var receiveReason = '';
            if (data.data[0].receiveReason != null){
                receiveReason = data.data[0].receiveReason;
            }
            var reviewRemark = '';
            if (data.data[0].reviewRemark != null){
                reviewRemark = data.data[0].reviewRemark;
            }
            if(data.data[0].refundType == 1){
                if(data.data[0].refundStatus == 1){
                    refundStatusDetailsStr = '<div class="itemDiv" style="font-weight: 600;">等待商家处理退款申请</div>'
                        +'<div class="itemDiv">收到买家发起的退款申请，请将在<span style="color: red;">'+ data.data[0].offTime +'</span>内处理，逾期系统将自动退款。</div>'
                        +'<div class="itemDiv">'
                        +'<button type="button" class="btn btn-primary" style="width: 120px;height: 30px;color: #FFFFFF;margin-right: 20px;" onclick="passRefund('+ data.data[0].refundId +','+ data.data[0].amount +')">同意买家退款</button>'
                        +'<button type="button" class="btn btn-danger-xb" style="width: 120px;height: 30px;color: #FFFFFF;" onclick="refuseRefund('+ data.data[0].refundId +','+ data.data[0].amount +')">拒绝买家退款</button></div>';
                }
                if(data.data[0].refundStatus == 4 || data.data[0].refundStatus == 2){
                    refundStatusDetailsStr = '<div class="itemDiv" style="font-weight: 600;">退款成功</div>'
                        +'<div class="itemDiv">退款金额:¥<span>'+ data.data[0].amount +'</span></div>'
                        +'<div class="itemDiv">原因:<span>'+ reviewRemark +'</span></div>';
                }
                if(data.data[0].refundStatus == 3){
                    if(data.data[0].deliveryStatus != 2){
                        refundStatusDetailsStr = '<div class="itemDiv" style="font-weight: 600;">商家拒绝退款</div>'
                            +'<div class="itemDiv">本次申请已结束。请与买家协商一致后，再继续完成商品发货。</div>'
                            +'<div class="itemDiv"><button type="button" class="btn btn-primary" style="width: 120px;height: 30px;color: #FFFFFF;margin-right: 20px;" onclick="goTODelivery()">发货</button></div>';
                    }else {
                        refundStatusDetailsStr = '<div class="itemDiv" style="font-weight: 600;">商家拒绝退款并已发货</div>'
                            +'<div class="itemDiv">本次申请已结束。与买家协商后已经发货。</div>';
                    }
                }
                if(data.data[0].refundStatus == 6){
                    refundStatusDetailsStr = '<div class="itemDiv" style="font-weight: 600;">用户撤销</div>'
                        +'<div class="itemDiv">原因:买家主动撤销。</div>'
                        +'<div class="itemDiv">注:后续如有问题，用户可继续发起退款申请。</div>';
                }
            }else {
                if(data.data[0].refundStatus == 1){
                    refundStatusDetailsStr = '<div class="itemDiv" style="font-weight: 600;">等待商家处理退款申请</div>'
                        +'<div class="itemDiv">收到买家发起的退款申请，请将在<span style="color: red;">'+ data.data[0].offTime +'</span>内处理，逾期系统将自动发送退货地址。</div>'
                        +'<div class="itemDiv">'
                        +'<button type="button" class="btn btn-primary" style="width: auto;height: 30px;color: #FFFFFF;margin-right: 20px;" onclick="passRefundGoods('+ data.data[0].refundId +','+ data.data[0].amount +','+ data.data[0].goodsNum +')">同意退货，发送退货地址</button>'
                        +'<button type="button" class="btn btn-danger-xb" style="width: 120px;height: 30px;color: #FFFFFF;" onclick="refuseRefundGoods('+ data.data[0].refundId +','+ data.data[0].amount +','+ data.data[0].goodsNum +')">拒绝买家退货</button></div>';
                }
                if(data.data[0].refundStatus == 2){
                    if (data.data[0].logistics != null){
                        if(data.data[0].logistics.status == 1){
                            refundStatusDetailsStr = '<div class="itemDiv" style="font-weight: 600;">商家已同意退货退款，等待买家退货。</div>'
                                +'<div class="itemDiv">您已同意退货退款，请等待买家处理。</div>'
                                +'<div class="itemDiv">买家将在<span>'+ data.data[0].offTime +'</span>内处理，逾期未处理申请将自动撤销。</div>';
                        }else {
                            refundStatusDetailsStr = '<div class="itemDiv" style="font-weight: 600;">买家已发货，等待商家确认收货。</div>'
                                +'<div class="itemDiv">买家已完成发货，请在<span style="color: red;">'+ data.data[0].offTime +'</span>内处理，逾期系统将自动发送确认收货。</div>'
                                +'<div class="itemDiv">'
                                +'<button type="button" class="btn btn-primary" style="width: 120px;height: 30px;color: #FFFFFF;margin-right: 20px;" onclick="passReciveGoods('+ data.data[0].refundId +','+ data.data[0].amount +','+ data.data[0].goodsNum +','+ data.data[0].logistics.refundLogisticsId +')">确认收货并退款</button>'
                                +'<button type="button" class="btn btn-danger-xb" style="width: 120px;height: 30px;color: #FFFFFF;" onclick="refuseReciveGoods('+ data.data[0].refundId +','+ data.data[0].amount +','+ data.data[0].goodsNum +','+ data.data[0].logistics.refundLogisticsId +')">拒绝收货</button></div>';
                        }
                    }
                }
                if(data.data[0].refundStatus == 6){
                    refundStatusDetailsStr = '<div class="itemDiv" style="font-weight: 600;">申请已撤销</div>'
                        +'<div class="itemDiv">原因:买家主动撤销。</div>'
                        +'<div class="itemDiv">注:后续如有问题，用户可继续发起退款申请。</div>';
                }
                if(data.data[0].refundStatus == 7){
                    refundStatusDetailsStr = '<div class="itemDiv" style="font-weight: 600;">申请已撤销</div>'
                        +'<div class="itemDiv">原因：买家超时未发货，系统自动撤销。</div>'
                        +'<div class="itemDiv">注:后续如有问题，用户可继续发起退款申请。</div>';
                }
                if(data.data[0].refundStatus == 3){
                    refundStatusDetailsStr = '<div class="itemDiv" style="font-weight: 600;">商家拒绝退货退款</div>'
                        +'<div class="itemDiv">本次申请已结束。后续买家可再次发起退款申请。</div>'
                        +'<div class="itemDiv">注:后续如有问题，用户可继续发起退款申请。</div>';
                }
                if(data.data[0].refundStatus == 4){
                    refundStatusDetailsStr = '<div class="itemDiv" style="font-weight: 600;">退款成功</div>'
                        +'<div class="itemDiv">退款金额:¥<span>'+ data.data[0].amount +'</span></div>'
                        +'<div class="itemDiv">原因:<span>'+ receiveReason +'</span></div>';
                }
                if(data.data[0].refundStatus == 8){
                    refundStatusDetailsStr = '<div class="itemDiv" style="font-weight: 600;">商家拒绝收货</div>'
                        +'<div class="itemDiv">本次申请已结束。后续买家可再次发起退款申请。</div>'
                        +'<div class="itemDiv">注:若多次协商未果，平台管理将会介入。</div>';
                }
            }
            $("#refundStatusDetails").empty().append(refundStatusDetailsStr);
            var refundList = data.data;
            var buttonGroupsHtml = '';
            for (var i=0;i<refundList.length;i++){
                var refundDetailsHtml = '';
                if(i == 0){
                    buttonGroupsHtml += '<button type="button" data-calType="'+ parseInt(refundList.length - i) +'" class="btn btn-outline btn-default active">第'+ parseInt(refundList.length - i) +'次</button>';
                    $("#applyNumTime").text(parseInt(refundList.length - i));
                    refundDetailsHtml += '<div id="refundDetails'+ parseInt(refundList.length - i) +'" style="display: block">';
                }else {
                    buttonGroupsHtml += '<button type="button" data-calType="'+ parseInt(refundList.length - i) +'" class="btn btn-outline btn-default">第'+ parseInt(refundList.length - i) +'次</button>';
                    refundDetailsHtml += '<div id="refundDetails'+ parseInt(refundList.length - i) +'" style="display: none">';
                }
                if (refundList[i].createdTime != null && refundList[i].createdTime != ""){
                    refundDetailsHtml += '<div class="itemDiv" style="border-bottom: 1px solid #CCCCCC;"><span>买家</span><span style="margin-left: 80px;">'+ refundList[i].createdTime +'</span></div><div class="itemDiv">发起了退款申请。</div>';
                    if(refundList[i].refundType == 1){
                        refundDetailsHtml += '<div class="itemDiv">退款方式:仅退款</div>';
                    }else {
                        refundDetailsHtml += '<div class="itemDiv">退款方式:退货退款</div>';
                    }
                    refundDetailsHtml += '<div class="itemDiv">退款原因:'+ refundList[i].reason +'</div>';
                    refundDetailsHtml += '<div class="itemDiv">退款金额:<span style="color: red;">￥'+ refundList[i].amount +'</span>（含运费¥'+ refundList[i].expressFee +'）</div>';
                    refundDetailsHtml += '<div class="itemDiv">退款单号:'+ refundList[i].refundOrderNo +'</div>';
                    refundDetailsHtml += '<div style="height: auto;line-height: 30px;width: 100%;min-height: 30px;">退款说明:'+ refundList[i].refundRemark +'</div>';
                    refundDetailsHtml += '<div style="line-height: 30px;width: 100%;"><span style="float: left;">申请凭证:</span>';
                    if(refundList[i].imgList != null){
                        for (var j=0;j<refundList[i].imgList.length;j++){
                            refundDetailsHtml += '<img style="width: 60px;height: 60px;margin-top: 10px;margin-left: 5px;cursor: zoom-in;"onclick="zoomInImage(event)" src="'+ refundList[i].imgList[j] +'">';
                        }
                    }
                    refundDetailsHtml += '</div>';
                    if(refundList[i].reviewTime != null && refundList[i].reviewTime != ""){
                        refundDetailsHtml += '<div class="itemDiv" style="clear: both;border-bottom: 1px solid #CCCCCC;"><span>商家</span><span style="margin-left: 80px;">'+ refundList[i].reviewTime +'</span></div>';
                        if(refundList[i].refundType == 1){
                            if(refundList[i].refundStatus == 3){
                                refundDetailsHtml += '<div class="itemDiv">商家拒绝退款申请。</div>';
                                if (refundList[i].reviewRemark == null){
                                    refundDetailsHtml += '<div class="itemDiv">商家审核:拒绝退款申请</div>';
                                }else {
                                    refundDetailsHtml += '<div class="itemDiv">商家审核:'+ refundList[i].reviewRemark +'</div>';
                                }
                            }else {
                                refundDetailsHtml += '<div class="itemDiv">审核同意了退款申请。</div>';
                                if (refundList[i].reviewRemark == null){
                                    refundDetailsHtml += '<div class="itemDiv">商家审核:同意退款申请</div>';
                                }else {
                                    refundDetailsHtml += '<div class="itemDiv">商家审核:'+ refundList[i].reviewRemark +'</div>';
                                }
                            }
                        }else {
                            if(refundList[i].refundStatus == 3){
                                refundDetailsHtml += '<div class="itemDiv">商家拒绝退货退款申请。</div>';
                                if (refundList[i].reviewRemark == null){
                                    refundDetailsHtml += '<div class="itemDiv">商家审核:拒绝退货退款申请</div>';
                                }else {
                                    refundDetailsHtml += '<div class="itemDiv">商家审核:'+ refundList[i].reviewRemark +'</div>';
                                }
                            }else {
                                refundDetailsHtml += '<div class="itemDiv">审核同意了退货退款申请。</div>';
                                if (refundList[i].reviewRemark == null){
                                    refundDetailsHtml += '<div class="itemDiv">商家审核:同意退货退款申请</div>';
                                }else {
                                    refundDetailsHtml += '<div class="itemDiv">商家审核:'+ refundList[i].reviewRemark +'</div>';
                                }
                                var address = '';
                                if (refundList[i].logistics != null){
                                    address = refundList[i].logistics.province + refundList[i].logistics.city + refundList[i].logistics.district + refundList[i].logistics.address;
                                }
                                var consigneeName = '';
                                if (refundList[i].logistics != null){
                                    consigneeName = refundList[i].logistics.consigneeName;
                                }
                                var consigneePhone = '';
                                if (refundList[i].logistics != null){
                                    consigneePhone = refundList[i].logistics.consigneePhone;
                                }
                                refundDetailsHtml += '<div class="itemDiv">退货地址:【'+ consigneeName +'】'+ address +'<span style="margin-left: 4px;">'+ consigneePhone +'</span></div>';
                            }
                        }
                    }
                    if (refundList[i].logistics != null){
                        if(refundList[i].logistics.deliveryTime != null && refundList[i].logistics.deliveryTime != ""){
                            refundDetailsHtml += '<div class="itemDiv" style="clear: both;border-bottom: 1px solid #CCCCCC;"><span>买家</span><span style="margin-left: 80px;">'+ refundList[i].logistics.deliveryTime +'</span></div>';
                            refundDetailsHtml += '<div class="itemDiv">已发货</div>';
                            refundDetailsHtml += '<div class="itemDiv">物流公司:'+ refundList[i].logistics.expressCompany +'</div>';
                            refundDetailsHtml += '<div class="itemDiv">物流单号:'+ refundList[i].logistics.expressNo +'</div>';
                            refundDetailsHtml += '<div style="height: auto;line-height: 30px;width: 100%;min-height: 30px;">物流说明:'+ refundList[i].logistics.expressRemark +'</div>';
                            refundDetailsHtml += '<div class="itemDiv"><span style="float: left;">物流凭证:</span>';
                            if (refundList[i].logistics.imgList != null){
                                for (var j=0;j<refundList[i].logistics.imgList.length;j++){
                                    refundDetailsHtml += '<img style="width: 60px;height: 60px;margin-left: 5px;margin-top: 10px;cursor: zoom-in;" onclick="zoomInImage(event)" src="'+ refundList[i].logistics.imgList[j] +'">';
                                }
                            }
                            refundDetailsHtml += '</div>';
                            if(refundList[i].logistics.receive_time != null && refundList[i].logistics.receive_time != ""){
                                refundDetailsHtml += '<div class="itemDiv" style="clear: both;border-bottom: 1px solid #CCCCCC;"><span>商家</span><span style="margin-left: 80px;">'+ refundList[i].logistics.receive_time +'</span></div>';
                                refundDetailsHtml += '<div class="itemDiv">确认收货并退款。</div>';
                                refundDetailsHtml += '<div class="itemDiv">商家审核:确认收货并退款。</div>';
                            }
                            if(refundList[i].logistics.rejectTime != null && refundList[i].logistics.rejectTime != ""){
                                refundDetailsHtml += '<div class="itemDiv" style="clear: both;border-bottom: 1px solid #CCCCCC;"><span>商家</span><span style="margin-left: 80px;">'+ refundList[i].logistics.rejectTime +'</span></div>';
                                refundDetailsHtml += '<div class="itemDiv">拒绝收货。</div>';
                                refundDetailsHtml += '<div class="itemDiv">商家审核:拒绝收货。</div>';
                                refundDetailsHtml += '<div style="height: auto;line-height: 30px;width: 100%;min-height: 30px;">拒绝理由:'+ refundList[i].logistics.rejectReason +'</div>';
                            }
                        }
                    }
                    if(refundList[i].revokeTime != null && refundList[i].revokeTime != ""){
                        refundDetailsHtml += '<div class="itemDiv" style="clear: both;border-bottom: 1px solid #CCCCCC;"><span>买家</span><span style="margin-left: 80px;">'+ refundList[i].revokeTime +'</span></div>';
                        refundDetailsHtml += '<div class="itemDiv">申请已撤销</div>';
                        refundDetailsHtml += '<div class="itemDiv">撤销原因:'+ refundList[i].revokeReason +'</div>';
                    }
                }
                refundDetailsHtml += '</div>';
                $("#refundDetailsDiv").append(refundDetailsHtml);
            }
            $("#applyNumTimeDiv").empty().append(buttonGroupsHtml);
            $("#applyNumTimeDiv button").each(function (index,item) {
                var buttonNum = $(item).attr("data-calType");
                $(item).click(function () {
                    $("#applyNumTimeDiv>button").removeClass("active");
                    $("#refundDetailsDiv>div").hide();
                    $(item).addClass("active");
                    $("#refundDetails"+buttonNum).show();
                    $("#applyNumTime").text(buttonNum);
                });
            });
        }else{
            Feng.error(data.message + "!");
        }
    }, function (data) {
        Feng.error(data.responseJSON.message + "!");
    });

    ajax.set("orderGoodsId",orderGoodsId);
    ajax.start();
}
function zoomInImage(event) {
    var imageEl = event.target;
    var imageUrl = imageEl.src;
    console.log(imageUrl);
    $("#zoomInImageDiv").show();
    $("#zoomInImage").attr("src",imageUrl);
}
function zoomOutImage() {
    $("#zoomInImageDiv").hide();
}
function goToOrderDetails() {
    window.location.href = Feng.ctxPath + '/shop/order/detail';
}
function goTODelivery() {
    var ajax = new $ax(Feng.ctxPath + "/shop/address/list", function (data) {
        if(data.total!=0){
            var orderId = sessionStorage.getItem("orderIdOfDelivery");
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
function passReciveGoods(refundId,amount,num,refundLogisticsId) {
    $.sendConfirm({
        title: '提示',
        content: '<div>确认收货后，退款金额将原路退还至买家账户。</div>' +
            '<div>处理方式：退货退款</div><div>商品数量:'+ num +'</div>' +
            '<div>退款金额:¥'+ amount +'</div>',
        button: {
            confirm: '确认收货并退款',
            cancel: '取消'
        },
        width: 400,
        onBeforeConfirm: function() {
            var ajax = new $ax(Feng.ctxPath + "/shop/orderRefund/confirmReceipt", function (data) {
                if(data.code != 200){
                    Feng.error(data.message);
                    return false
                }else{
                    Feng.success(data.message);
                    window.location.reload();
                }
            }, function (data) {
                Feng.error(data.responseJSON.message + "!");
            });
            ajax.set("refundId",refundId);
            ajax.set("refundLogisticsId",refundLogisticsId);
            ajax.start();
        }
    });
}
function refuseReciveGoods(refundId,amount,num,refundLogisticsId) {
    $.sendConfirm({
        title: '提示',
        content: '<div style="color: red;">建议你与买家详细交流后，再确定是否拒绝。</div>' +
            '<div style="color: red;">买家还可以再次发起退款申请。</div>' +
            '<div style="color: red;">注:若多次协商未果，平台管理将会介入。</div>' +
            '<div>处理方式:退货退款</div>' +
            '<div>商品数量:'+ num +'</div>' +
            '<div>退款金额:¥'+ amount +'</div>' +
            '<div>拒绝理由:<textarea id="refuseReason" style="width: 200px;height: 80px;" placeholder="必填，200字以内."></textarea></div>',
        button: {
            confirm: '拒绝收货',
            cancel: '取消'
        },
        width: 300,
        onBeforeConfirm: function() {
            var ajax = new $ax(Feng.ctxPath + "/shop/orderRefund/refusereceipt", function (data) {
                if(data.code != 200){
                    Feng.error(data.message);
                    return false
                }else{
                    Feng.success(data.message);
                    window.location.reload();
                }
            }, function (data) {
                Feng.error(data.responseJSON.message + "!");
            });
            ajax.set("refundId",refundId);
            ajax.set("reason",$("#refuseReason").val());
            ajax.start();
        }
    });
}
function passRefundGoods(refundId,amount,num,refundLogisticsId) {
    var ajax = new $ax(Feng.ctxPath + "/shop/address/list", function (data) {
        if(data.total!=0){
            var addressList = data.rows;
            var tempHtml = '';
            for (var i=0;i<addressList.length;i++){
                var address = addressList[i].province + addressList[i].city + addressList[i].district + addressList[i].address;
                if (i == 0){
                    tempHtml += '<div style="width: 100%;height: 20px;line-height: 20px;background-color: #eeeeee;margin-bottom: 5px;">' +
                        '<input style="margin-left: 10px;" value="'+ addressList[i].id +'" type="radio" name="selectAddress" checked="checked">' +
                        '<span style="margin-left: 40px;">【'+ addressList[i].consigneeName +'】</span>' +
                        '<span style="margin-left: 10px;">'+ address +'</span>' +
                        '<span style="margin-left: 10px;">'+ addressList[i].consigneePhone +'</span>' +
                        '<span style="color: red;float: right;margin-right: 40px;">默认地址</span>' +
                        '</div>';
                }else {
                    tempHtml += '<div style="width: 100%;height: 20px;line-height: 20px;background-color: #eeeeee;margin-bottom: 5px;">' +
                        '<input style="margin-left: 10px;" value="'+ addressList[i].id +'" type="radio" name="selectAddress">' +
                        '<span style="margin-left: 40px;">【'+ addressList[i].consigneeName +'】</span>' +
                        '<span style="margin-left: 10px;">'+ address +'</span>' +
                        '<span style="margin-left: 10px;">'+ addressList[i].consigneePhone +'</span>' +
                        '</div>';
                }
            }
            $.sendConfirm({
                title: '提示',
                content: '<div>若您同意退货退款申请，需要将退货地址发送给买家，买家退货后,您再确认收货。退款金额将原路退还至卖家账户。</div>' +
                    '<div>处理方式：退货退款</div><div>商品数量:'+ num +'</div>' +
                    '<div>退款金额:¥'+ amount +'</div>' +
                    '<div>退货地址</div>' +
                    '<div style="width: 100%;max-height: 120px;overflow-y: scroll;">'+ tempHtml +'</div>' +
                    '<div onclick="jumpToAddress();">使用新地址</div>',
                button: {
                    confirm: '同意退货',
                    cancel: '取消'
                },
                width: 600,
                onBeforeConfirm: function() {
                    var ajax = new $ax(Feng.ctxPath + "/shop/orderRefund/pass", function (data) {
                        if(data.code != 200){
                            Feng.error(data.message);
                            return false
                        }else{
                            Feng.success(data.message);
                            window.location.reload();
                        }
                    }, function (data) {
                        Feng.error(data.responseJSON.message + "!");
                    });
                    ajax.set("refundId",refundId);
                    ajax.set("reviewRemark","商家已同意退货退款，等待买家退货");
                    ajax.set("shopAddrId",$("input[name='selectAddress']:checked").val());
                    ajax.start();
                }
            });
        }else{
            var operation = function(){
                top.document.querySelectorAll('#side-menu li a').forEach(function(e){
                    if (e.getAttribute('href').indexOf('/shop/address') != -1 ) {$(e).closest('ul').prev()[0].click();
                        e.click();
                    }
                });
            };
            Feng.confirm("你还未设置退货地址,请设置后同意退款并发送退货地址.", operation);
        }
    }, function (data) {
        Feng.error(data.responseJSON.message + "!");
    });
    ajax.set("offset",0);
    ajax.set("limit",15);
    ajax.start();
}
function jumpToAddress() {
    top.document.querySelectorAll('#side-menu li a').forEach(function(e){
        if (e.getAttribute('href').indexOf('/shop/address') != -1 ) {$(e).closest('ul').prev()[0].click();
            e.click();
        }
    });
}
function refuseRefundGoods(refundId,amount,num) {
    $.sendConfirm({
        title: '提示',
        content: '<div style="color: red;">建议你与买家详细交流后，再确定是否拒绝。</div>' +
            '<div style="color: red;">您拒绝后，买家还可以再次发起退款申请。</div>' +
            '<div style="color: red;">注:若多次协商未果，平台管理将会介入。</div>' +
            '<div>处理方式:退货退款</div>' +
            '<div>商品数量:'+ num +'</div>' +
            '<div>退款金额:¥'+ amount +'</div>' +
            '<div>拒绝理由:<textarea id="refuseReason" style="width: 200px;height: 80px;" placeholder="必填，200字以内."></textarea></div>',
        button: {
            confirm: '拒绝退款',
            cancel: '取消'
        },
        width: 300,
        onBeforeConfirm: function() {
            var ajax = new $ax(Feng.ctxPath + "/shop/orderRefund/refusePass", function (data) {
                if(data.code != 200){
                    Feng.error(data.message);
                    return false
                }else{
                    Feng.success(data.message);
                    window.location.reload();
                }
            }, function (data) {
                Feng.error(data.responseJSON.message + "!");
            });
            ajax.set("refundId",refundId);
            ajax.set("reviewRemark",$("#refuseReason").val());
            ajax.start();
        }
    });
}
function passRefund(refundId,amount) {
    $.sendConfirm({
        title: '提示',
        content: '<div>若您同意退款，平台将直接给买家退款，退款金额将原路返回至买家的支付账户。</div>' +
            '<div>处理方式：仅退款</div>' +
            '<div>退款金额:¥'+ amount +'</div>',
        button: {
            confirm: '同意退款',
            cancel: '取消'
        },
        width: 300,
        onBeforeConfirm: function() {
            var ajax = new $ax(Feng.ctxPath + "/shop/orderRefund/pass", function (data) {
                if(data.code != 200){
                    Feng.error(data.message);
                    return false
                }else{
                    Feng.success(data.message);
                    window.location.reload();
                }
            }, function (data) {
                Feng.error(data.responseJSON.message + "!");
            });
            ajax.set("refundId",refundId);
            ajax.set("reviewRemark","商家同意退款申请");
            ajax.start();
        }
    });
}
function refuseRefund(refundId,amount) {
    $.sendConfirm({
        title: '提示',
        content: '<div style="color: red;">建议你与买家详细交流后，再确定是否拒绝。</div>' +
            '<div style="color: red;">您拒绝后，买家还可以再次发起退款申请。</div>' +
            '<div style="color: red;">注:若多次协商未果，平台管理将会介入。</div>' +
            '<div>处理方式:仅退款</div>' +
            '<div>退款金额:¥'+ amount +'</div>' +
            '<div>拒绝理由:<textarea id="refuseReason" style="width: 200px;height: 80px;" placeholder="必填，200字以内."></textarea></div>',
        button: {
            confirm: '拒绝退款',
            cancel: '取消'
        },
        width: 300,
        onBeforeConfirm: function() {
            var ajax = new $ax(Feng.ctxPath + "/shop/orderRefund/refusePass", function (data) {
                if(data.code != 200){
                    Feng.error(data.message);
                    return false
                }else{
                    Feng.success(data.message);
                    window.location.reload();
                }
            }, function (data) {
                Feng.error(data.responseJSON.message + "!");
            });
            ajax.set("refundId",refundId);
            ajax.set("reviewRemark",$("#refuseReason").val());
            ajax.start();
        }
    });
}