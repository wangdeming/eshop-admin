/**
 * 店铺服务费管理初始化
 */
var ShopInfoMgr = {
};

/**
 * 查看提现详情
 */
ShopInfoMgr.withdrawalDetail = function (id) {
    window.location.href=Feng.ctxPath + '/platform/cash/withdrawal/toDetail/'+id;
};
/**
 * 查询今日余额收支明细列表
 */
$(function () {
    var ajax = new $ax(Feng.ctxPath + "/platform/cash/systemCash/listTodayPlatformBalanceFlow", function (data) {
        var result = '';
        var elem = data.data;
        if (elem.length == 0){
            result  += '                                <tr id="project"><td colspan="7" style="text-align: center">没有找到匹配的记录</td></tr>\n';
            $(".project").append(result);
        }else{
            $.each(elem,function (index,elem) {
                var createdTime = elem.createdTime;
                var flowType = elem.flowType;
                var orderNo = elem.orderNo;
                var amount = elem.amount;
                var shopName = elem.shopName;
                var shopType = elem.shopType;
                var shopId = elem.shopId;
                var tradeId = elem.tradeId;
                var transSrc = elem.transSrc;
                if (transSrc == 3) {
                    var a = '';
                    var b = '<a id="withdrawal" style="margin-right: 30px;" onclick="ShopInfoMgr.withdrawalDetail('+tradeId+')">查看</a>';
                } else if ((transSrc=='1' || transSrc=='2') && shopType=='特产店铺') {
                    var a = '<div id="transSrc">订单号：<a onclick="ShopInfoMgr.goodsOrderDetail(\''+tradeId+'\')">'+orderNo+'</a></div>';
                    var b = '<a id="withdrawal" style="margin-right: 30px;" onclick="ShopInfoMgr.goodsOrderDetail(\''+tradeId+'\')">查看</a>';
                } else if ((transSrc=='1' || transSrc=='2') && shopType=='酒店店铺') {
                    var a = '<div id="transSrc">订单号：<a onclick="ShopInfoMgr.goodsHotelOrderDetail(\''+tradeId+'\')">'+orderNo+'</a></div>';
                    var b = '<a id="withdrawal" style="margin-right: 30px;" onclick="ShopInfoMgr.goodsHotelOrderDetail(\''+tradeId+'\')">查看</a>';
                }
                result  +=
                    '                                <tr id="project">\n' +
                    '                                    <td id="createdTime">'+createdTime+'</td>\n' +
                    '                                    <td id="flowType"><p>'+flowType+'</p>'+a+'</td>\n' +
                    '                                    <td id="amount">'+amount+'</td>\n' +
                    '                                    <td id="shopName">'+shopName+'</td>\n' +
                    '                                    <td id="shopType">'+shopType+'</td>\n' +
                    '                                    <td id="shopId">'+shopId+'</td>\n' +
                    '                                    <td id="shopId">'+b+'</td>\n' +
                    '                                </tr>\n'
            });
            $(".project").append(result);

        }
    }, function (data) {
        Feng.error(data.responseJSON.message);
    });
    ajax.start();
});

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
 * 获取平台账号概览
 */
$(function () {
    var ajax = new $ax(Feng.ctxPath + "/platform/cash/systemCash/getPlatformAccountInfo", function (data) {
        var elem = data.data;
        $("#platformBalance").text('￥'+elem.platformBalance);
        $("#shopBalance").text('￥'+elem.shopBalance);
        $("#serviceAmount").text('￥'+elem.serviceAmount);
        $("#totalBalance").text('￥'+elem.totalBalance);
        $("#withdrawalAmount").text('￥'+elem.withdrawalAmount);
    });
    ajax.set("order","desc");
    ajax.set("offset",0);
    ajax.set("limit",14);
    ajax.start();
});

/**
 * 获取今日实时数据
 */
$(function () {
    var ajax = new $ax(Feng.ctxPath + "/platform/cash/systemCash/getTodayData", function (data) {
        var elem = data.data;
        $("#changeBalance").text('￥'+elem.changeBalance);
        $("#orderAmount").text('￥'+elem.orderAmount);
        $("#orderNum").text(elem.orderNum);
        $("#refundAmount").text('￥'+elem.refundAmount);
        $("#refundNum").text(elem.refundNum);
        $("#withdrawalAmount2").text('￥'+elem.withdrawalAmount);
        $("#withdrawalNum").text(elem.withdrawalNum);
    });
    ajax.set("order","desc");
    ajax.set("offset",0);
    ajax.set("limit",14);
    ajax.start();
});