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
                        +'<div class="itemDiv">收到买家发起的退款申请，请将在<span style="color: red;">'+ data.data[0].offTime +'</span>内处理，逾期系统将自动退款。</div>';
                }
                if(data.data[0].refundStatus == 4 || data.data[0].refundStatus == 2){
                    refundStatusDetailsStr = '<div class="itemDiv" style="font-weight: 600;">退款成功</div>'
                        +'<div class="itemDiv">退款金额:¥<span>'+ data.data[0].amount +'</span></div>'
                        +'<div class="itemDiv">原因:<span>'+ reviewRemark +'</span></div>';
                }
                if(data.data[0].refundStatus == 3){
                    refundStatusDetailsStr = '<div class="itemDiv" style="font-weight: 600;">商家拒绝退款</div>'
                        +'<div class="itemDiv">本次申请已结束。请与买家协商一致后，再继续完成商品发货。</div>';
                }
                if(data.data[0].refundStatus == 6){
                    refundStatusDetailsStr = '<div class="itemDiv" style="font-weight: 600;">用户撤销</div>'
                        +'<div class="itemDiv">原因:买家主动撤销。</div>'
                        +'<div class="itemDiv">注:后续如有问题，用户可继续发起退款申请。</div>';
                }
            }else {
                if(data.data[0].refundStatus == 1){
                    refundStatusDetailsStr = '<div class="itemDiv" style="font-weight: 600;">等待商家处理退款申请</div>'
                        +'<div class="itemDiv">收到买家发起的退款申请，请将在<span style="color: red;">'+ data.data[0].offTime +'</span>内处理，逾期系统将自动发送退货地址。</div>';
                }
                if(data.data[0].refundStatus == 2){
                    if (data.data[0].logistics != null){
                        if(data.data[0].logistics.status == 1){
                            refundStatusDetailsStr = '<div class="itemDiv" style="font-weight: 600;">商家已同意退货退款，等待买家退货。</div>'
                                +'<div class="itemDiv">您已同意退货退款，请等待买家处理。</div>'
                                +'<div class="itemDiv">买家将在<span>'+ data.data[0].offTime +'</span>内处理，逾期未处理申请将自动撤销。</div>';
                        }else {
                            refundStatusDetailsStr = '<div class="itemDiv" style="font-weight: 600;">买家已发货，等待商家确认收货。</div>'
                                +'<div class="itemDiv">买家已完成发货，请在<span style="color: red;">'+ data.data[0].offTime +'</span>内处理，逾期系统将自动发送确认收货。</div>';
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
                            refundDetailsHtml += '<img style="width: 60px;height: 60px;margin-top: 10px;margin-left: 5px;cursor: zoom-in;" onclick="zoomInImage(event)" src="'+ refundList[i].imgList[j] +'">';
                        }
                    }
                    refundDetailsHtml += '</div>';
                    if(refundList[i].reviewTime != null && refundList[i].reviewTime != ""){
                        refundDetailsHtml += '<div class="itemDiv" style="clear: both;border-bottom: 1px solid #CCCCCC;"><span>商家</span><span style="margin-left: 80px;">'+ refundList[i].reviewTime +'</span></div>';
                        if(refundList[i].refundType == 1){
                            refundDetailsHtml += '<div class="itemDiv">审核同意了退款申请。</div>';
                            if (refundList[i].reviewRemark == null){
                                refundDetailsHtml += '<div class="itemDiv">商家审核:同意退款申请</div>';
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
    window.location.href = Feng.ctxPath + '/platform/ordercenter/goodsOrderDetail';
}