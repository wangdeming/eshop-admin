$(function () {
    getGoodsDetails();
});
function getGoodsDetails() {

    var hotelOrderId = sessionStorage.getItem("hotelOrderId");
    var ajax = new $ax(Feng.ctxPath + "/shop/hotel/detail", function (data) {
        if(data.code==200){
            $("#orderNo").text(data.data.order.orderNo);
            $("#statusName").text(data.data.order.statusName);
            $("#createdTime").text(data.data.order.createdTime);
            $("#mobile").text(data.data.order.mobile);
            $("#days").text(data.data.order.days);
            $("#checkInDate").text(data.data.order.checkInDate);
            $("#checkOutDate").text(data.data.order.checkOutDate);
            $("#totalAmount").text(data.data.order.totalAmount);
            $("#refundAmount").text(data.data.order.refundAmount);
            var realnameList = [];
            for (var i=0;i<data.data.users.length;i++){
                realnameList.push(data.data.users[i].realname);
                $("#realname").text(realnameList.join('、'));
            }
            var cancelReason = '';
            if(data.data.order.cancelReason!=null){
                cancelReason = data.data.order.cancelReason;
            }
            switch (data.data.order.status){
                case 1:
                    var orderStatusHtml =  '<div class="statusDiv">等待买家付款</div>';
                    $("#orderStatus").empty().append(orderStatusHtml);
                    $("#circle1").text("√");
                    $("#line1").css("background-color","#f2f8f9");
                    $("#line2").css("background-color","#f2f8f9");
                    $("#line3").css("background-color","#f2f8f9");
                    $("#time1").text(data.data.order.createdTime);
                    break;
                case 2:
                    var orderStatusHtml = '<div class="statusDiv">等待商家确认</div>';
                    $("#orderStatus").empty().append(orderStatusHtml);
                    $("#circle1").text("√");
                    $("#circle2").text("√");
                    $("#line2").css("background-color","#f2f8f9");
                    $("#line3").css("background-color","#f2f8f9");
                    $("#time1").text(data.data.order.createdTime);
                    $("#time2").text(data.data.order.payDatetime);
                    break;
                case 3:
                    var orderStatusHtml = '<div class="statusDiv">等待买家入住</div>';
                    $("#orderStatus").empty().append(orderStatusHtml);
                    $("#circle1").text("√");
                    $("#circle2").text("√");
                    $("#circle3").text("√");
                    $("#line3").css("background-color","#f2f8f9");
                    $("#time1").text(data.data.order.createdTime);
                    $("#time2").text(data.data.order.payDatetime);
                    $("#time3").text(data.data.order.confirmDatetime);
                    break;
                case 4:
                    var orderStatusHtml =  '<div class="statusDiv">已消费</div>';
                    $("#orderStatus").empty().append(orderStatusHtml);
                    $("#circle1").text("√");
                    $("#circle2").text("√");
                    $("#circle3").text("√");
                    $("#circle4").text("√");
                    $("#time1").text(data.data.order.createdTime);
                    $("#time2").text(data.data.order.payDatetime);
                    $("#time3").text(data.data.order.confirmDatetime);
                    $("#time4").text(data.data.order.confirmInDatetime);
                    break;
                case 5:
                    var orderStatusHtml =  '<div><span class="statusDiv">已取消</span>&emsp;<span>'+ cancelReason +'</span></div>';
                    $("#orderStatus").empty().append(orderStatusHtml);
                    $("#orderSchedule").hide();
                    if (data.data.order.payDatetime) {
                        $("#cancel").show();
                    } else {
                        $("#cancel").hide();
                    }
                    break;
                case 6:
                    var orderStatusHtml =  '<div class="statusDiv">已消费</div>';
                    $("#orderStatus").empty().append(orderStatusHtml);
                    $("#circle1").text("√");
                    $("#circle2").text("√");
                    $("#circle3").text("√");
                    $("#circle4").text("√");
                    $("#time1").text(data.data.order.createdTime);
                    $("#time2").text(data.data.order.payDatetime);
                    $("#time3").text(data.data.order.confirmDatetime);
                    $("#time4").text(data.data.order.confirmInDatetime);
                    break;
                case 7:
                    var orderStatusHtml = '<div class="statusDiv">已过期</div>';
                    $("#orderStatus").empty().append(orderStatusHtml);
                    $("#circle1").text("√");
                    $("#circle2").text("√");
                    $("#circle3").text("√");
                    $("#line3").css("background-color","#f2f8f9");
                    $("#time1").text(data.data.order.createdTime);
                    $("#time2").text(data.data.order.payDatetime);
                    $("#time3").text(data.data.order.confirmDatetime);
                    break;
            }
        }else{
            Feng.error(data.message + "!");
        }
    }, function (data) {
        Feng.error(data.responseJSON.message + "!");
    });
    ajax.set("hotelOrderId",hotelOrderId);
    ajax.start();
}
