var offset = 0;//该页的起始记录，偏移量，如果是第一页就是（1-1）*limit，如果第二页就是（2-1）*limit，第n页就是，（n-1）*limit
var limit = 10;//每页显示记录数
var createdTimeStart = '';//下单时间查询起始时间
var createdTimeEnd = '';//下单时间查询终止时间
var orderNo = '';//订单编号
var goodsName = '';//商品名称
var evaluateWay = '';//评价方式
var goodsScore = '';//商品星级
var pageNum = 1;

$(function () {
    // 监听评价方式变化
    $("#evaluateWay").change(function () {
        getOrderList();
    });

    // 监听商品星级变化
    $("#goodsScore").change(function () {
        getOrderList();
    });

    getOrderList();
});
function orderSearch() {
    pageNum = 1;
    getOrderList();
}
function getOrderList() {
    offset = (pageNum - 1) * limit;
    createdTimeStart = $("#createStartTime").val();
    createdTimeEnd = $("#createEndTime").val();
    orderNo  = $("#orderNo ").val();
    evaluateWay  = $("#evaluateWay ").val();
    goodsScore  = $("#goodsScore ").val();
    goodsName = $("#goodsName").val();
    var orderData = {};
    orderData.offset = offset;
    orderData.limit = limit;
    if (createdTimeStart != '') {
        orderData.createdTimeStart = createdTimeStart;
    }
    if (createdTimeEnd != '') {
        orderData.createdTimeEnd = createdTimeEnd;
    }
    if (goodsName != '') {
        orderData.goodsName = goodsName;
    }
    if (orderNo != '') {
        orderData.orderNo = orderNo;
    }
    if (evaluateWay  != '') {
        orderData.evaluateWay  = evaluateWay ;
    }
    if (goodsScore  != '') {
        orderData.goodsScore  = goodsScore ;
    }
    $.ajaxSetup({crossDomain: true, xhrFields: {withCredentials: true}});
    $.ajax({
        url: Feng.ctxPath +"/shop/order/listOrderEvaluate",
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
                    orderTbodyHtml +='<tr style="height: 30px;line-height: 30px;"><td colspan="6"><span style="float: left;color: red;margin-left: 20px;">订单号:'
                        + orderDate[i].orderNo +'</span><a onclick="checkOrderDetails('+ orderDate[i].id +')" style="float: right;margin-right: 80px;">查看订单详情</a></td></tr>';

                    for(var j=0;j<orderDate[i].evalGoodsList.length;j++){
                        var evalGoodsList = orderDate[i].evalGoodsList[j];
                        var sendGoodsHtml = '';
                        if (orderDate[i].evalGoodsList[j].shopReply){
                            sendGoodsHtml = '<div>商家回复：'+orderDate[i].evalGoodsList[j].shopReply+'</div>'
                        } else {
                            sendGoodsHtml = '<a onclick="sendReply ('+ orderDate[i].evalGoodsList[j].goodsEvaluateId +')">回复</a>';
                        }

                        // 商品星级
                        var goodsScoreShow = '';
                        if (evalGoodsList.goodsScore == 5){
                            goodsScoreShow = '<span class="glyphicon glyphicon-star"></span><span class="glyphicon glyphicon-star"></span><span class="glyphicon glyphicon-star"></span><span class="glyphicon glyphicon-star"></span><span class="glyphicon glyphicon-star"></span>'
                        } else if (evalGoodsList.goodsScore == 4) {
                            goodsScoreShow = '<span class="glyphicon glyphicon-star"></span><span class="glyphicon glyphicon-star"></span><span class="glyphicon glyphicon-star"></span><span class="glyphicon glyphicon-star"></span>'
                        } else if (evalGoodsList.goodsScore == 3) {
                            goodsScoreShow = '<span class="glyphicon glyphicon-star"></span><span class="glyphicon glyphicon-star"></span><span class="glyphicon glyphicon-star"></span>'
                        } else if (evalGoodsList.goodsScore == 2) {
                            goodsScoreShow = '<span class="glyphicon glyphicon-star"></span><span class="glyphicon glyphicon-star"></span>'
                        } else if (evalGoodsList.goodsScore == 1) {
                            goodsScoreShow = '<span class="glyphicon glyphicon-star"></span>'
                        } else {
                            goodsScoreShow = ''
                        }
                        // 商家服务星级
                        var serviceScoreShow = '';
                        if (orderDate[i].serviceScore == 5){
                            serviceScoreShow = '<span class="glyphicon glyphicon-star"></span><span class="glyphicon glyphicon-star"></span><span class="glyphicon glyphicon-star"></span><span class="glyphicon glyphicon-star"></span><span class="glyphicon glyphicon-star"></span>'
                        } else if (orderDate[i].serviceScore == 4) {
                            serviceScoreShow = '<span class="glyphicon glyphicon-star"></span><span class="glyphicon glyphicon-star"></span><span class="glyphicon glyphicon-star"></span><span class="glyphicon glyphicon-star"></span>'
                        } else if (orderDate[i].serviceScore == 3) {
                            serviceScoreShow = '<span class="glyphicon glyphicon-star"></span><span class="glyphicon glyphicon-star"></span><span class="glyphicon glyphicon-star"></span>'
                        } else if (orderDate[i].serviceScore == 2) {
                            serviceScoreShow = '<span class="glyphicon glyphicon-star"></span><span class="glyphicon glyphicon-star"></span>'
                        } else if (orderDate[i].serviceScore == 1) {
                            serviceScoreShow = '<span class="glyphicon glyphicon-star"></span>'
                        } else {
                            serviceScoreShow = ''
                        }
                        // 商家物流星级
                        var expressScoreShow = '';
                        if (orderDate[i].expressScore == 5){
                            expressScoreShow = '<span class="glyphicon glyphicon-star"></span><span class="glyphicon glyphicon-star"></span><span class="glyphicon glyphicon-star"></span><span class="glyphicon glyphicon-star"></span><span class="glyphicon glyphicon-star"></span>'
                        } else if (orderDate[i].expressScore == 4) {
                            expressScoreShow = '<span class="glyphicon glyphicon-star"></span><span class="glyphicon glyphicon-star"></span><span class="glyphicon glyphicon-star"></span><span class="glyphicon glyphicon-star"></span>'
                        } else if (orderDate[i].expressScore == 3) {
                            expressScoreShow = '<span class="glyphicon glyphicon-star"></span><span class="glyphicon glyphicon-star"></span><span class="glyphicon glyphicon-star"></span>'
                        } else if (orderDate[i].expressScore == 2) {
                            expressScoreShow = '<span class="glyphicon glyphicon-star"></span><span class="glyphicon glyphicon-star"></span>'
                        } else if (orderDate[i].expressScore == 1) {
                            expressScoreShow = '<span class="glyphicon glyphicon-star"></span>'
                        } else {
                            expressScoreShow = ''
                        }

                        // 规格
                        var showSpecs = '';
                        if (evalGoodsList.goodsSpecs) {
                            showSpecs = evalGoodsList.goodsSpecs
                        } else {
                            showSpecs = ''
                        }

                        var evaluateImgs = '';
                        for (var k=0;k<orderDate[i].evalGoodsList[j].imgs.length;k++) {
                            evaluateImgs += '<img style="width: 60px;height: 40px;margin-top: 10px;margin-left: 10px;float: left;" src="'
                            + orderDate[i].evalGoodsList[j].imgs[k] +'">'
                        }
                        if(j==0){
                            orderTbodyHtml += '<tr style="height: 60px;line-height: 20px;"><td style="display: flex;border: none;padding: 10px;"><img style="width: 60px;height: 40px;margin-right: 5px;float: left;" src="'
                                + evalGoodsList.goodsImg +'"><div style="line-height: 20px;"><div>'
                                + evalGoodsList.goodsName +'</div><div style="margin-top: 5px;color: red;">'+ showSpecs +'</div></div></td><td style="text-align: center;"><div style="display: inherit;">'+evaluateImgs+'</div><div style="line-height: 20px;text-align: left;margin: 10px;" >'
                                + evalGoodsList.content +'</div></td><td style="text-align: center;">'
                                + goodsScoreShow +'</td><td rowspan="'+ orderDate[i].evalGoodsList.length +'" style="text-align: center;"><div style="height: 20px;line-height: 20px;">商家服务'
                                + serviceScoreShow +'</div><div style="height: 20px;line-height: 20px;">商家物流'+ expressScoreShow +'</div></td><td style="text-align: center;" rowspan="'+ orderDate[i].evalGoodsList.length +'">'
                                + orderDate[i].createdTime +'</td><td style="text-align: center;padding: 8px;">'
                                + sendGoodsHtml +'</td></tr>';
                        }else {
                            orderTbodyHtml += '<tr style="height: 50px;line-height: 20px;"><td style="display: flex;padding: 10px;border-top: 1px solid #808080;border-right: none;border-bottom: none;border-left: none;" id="borderSty"><img style="width: 60px;height: 40px;margin-right: 5px;float: left;" src="'
                                + evalGoodsList.goodsImg +'"><div style="line-height: 20px;"><div>'
                                + evalGoodsList.goodsName +'</div><div style="margin-top: 5px;color: red;">'+ showSpecs +'</div></div></td><td style="text-align: center;"><div style="display: inherit;">'+evaluateImgs+'</div><div style="line-height: 20px;text-align: left;margin: 10px;" >'
                                + evalGoodsList.content +'</div></td><td style="text-align: center;">'
                                + goodsScoreShow +'</td><td style="text-align: center;padding: 8px;">'
                                + sendGoodsHtml +'</td></tr>';
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

/**
 * 查看订单详情
 */
function checkOrderDetails(orderId) {
    sessionStorage.setItem("orderDetailsOrderId",orderId);
    window.location.href = Feng.ctxPath + '/shop/order/detail';
}
/**
 * 弹出弹框
 */
function sendReply (id) {
    var index = layer.open({
        type: 1,
        title: '回复',
        area: ['600px', '360px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: '<br/><form id="changePwdForm" action="#" class="form-horizontal">\n' +
            '    <div class="ibox float-e-margins">\n' +
            '        <div style="padding: 15px 20px 20px 20px;"><div class="form-horizontal"></div><div class="row" style="margin-top: 15px;"><div class="col-sm-12"><label class="control-label" style="margin-bottom: 20px;">回复内容：</label>' +
            '<textarea id="replyContent" class="form-control" rows="5" placeholder="礼貌回复一下买家吧，至少5个字。"></textarea> </div></div>' +
            '<div class="row btn-group-m-t"> <div class="col-sm-10"> <button type="button" class="btn btn-danger-xb" onclick="closePage();" style="width: 120px;height: 34px;color: #FFFFFF;margin-right: 20px;">取消 </button>' +
            '<button type="button" class="btn btn-primary" onclick="changePwd('+id+');" style="width: 120px;height: 34px;color: #FFFFFF;">提交 </button> </div> </div></div></div></form>'
    });
    this.layerIndex = index;
}

/**
 * 关闭弹框
 */
function closePage() {
    layer.close(this.layerIndex);
}

/**
 * 提交回复内容
 */
function changePwd (goodsEvaluateId) {
    var replyContent = $("#replyContent").val();
    var  objRegExp= /^.{5,200}$/;
    if (replyContent == '') {
        console.log($("#replyContent").val());
        Feng.error("回复内容不能为空!");
    } else if (objRegExp.test(replyContent) == false) {
        Feng.error("不能少于5个字符，最多200字!");
    } else {
        var ajax = new $ax(Feng.ctxPath + "/shop/order/shopReply", function (data) {
            if (data.code == 200) {
                Feng.success("操作成功!");
                closePage();
                getOrderList();
            } else {
                id;
                Feng.error(data.message + "!");
            }
        });
        ajax.set("goodsEvaluateId", goodsEvaluateId);
        ajax.set("replyContent", replyContent);
        ajax.start();
    }
}