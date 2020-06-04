/**
 * 店铺服务费管理初始化
 */
var DataCount = {
    id: "DataCountTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
DataCount.initColumn = function () {
    if(isplatform()){
        return [
            {title: 'id', field: 'date', visible: false, align: 'left', valign: 'middle'},
            {title: '时间', field: 'date', align: 'left', valign: 'middle'},
            {title: '浏览量', field: 'viewCount', align: 'left', valign: 'middle'},
            {title: '范围', field: 'range', align: 'left', valign: 'middle'},
            {title: '访客数', field: 'visitorCount', align: 'left', valign: 'middle'},
            {title: '付款人数', field: 'payerCount', align: 'left', valign:  'middle'},
            {title: '付款订单数', field: 'orderCount', align: 'left', valign: 'middle'},
            {title: '付款金额', field: 'totalPayAmount', align: 'left', valign: 'middle'},
            {title: '访问-付费转化率', field: 'payInversionRate', align: 'left', valign: 'middle'},
            {title: '客单价', field: 'customerUnitPrice', align: 'left', valign: 'middle'},
            {title: '退款订单数', field: 'refundOrderCount', align: 'left', valign: 'middle'},
            {title: '退款金额', field: 'totalRefundAmount', align: 'left', valign: 'middle'},
        ];
    }else{
        return [
            {title: 'id', field: 'date', visible: false, align: 'left', valign: 'middle'},
            {title: '时间', field: 'date', align: 'left', valign: 'middle'},
            {title: '浏览量', field: 'viewCount', align: 'left', valign: 'middle'},
            {title: '访客数', field: 'visitorCount', align: 'left', valign: 'middle'},
            {title: '付款人数', field: 'payerCount', align: 'left', valign:  'middle'},
            {title: '付款订单数', field: 'orderCount', align: 'left', valign: 'middle'},
            {title: '付款金额', field: 'totalPayAmount', align: 'left', valign: 'middle'},
            {title: '访问-付费转化率', field: 'payInversionRate', align: 'left', valign: 'middle'},
            {title: '客单价', field: 'customerUnitPrice', align: 'left', valign: 'middle'},
            {title: '退款订单数', field: 'refundOrderCount', align: 'left', valign: 'middle'},
            {title: '退款金额', field: 'totalRefundAmount', align: 'left', valign: 'middle'},
        ];
    }

};

/**
 * 查看提现详情
 */
DataCount.withdrawalDetail = function (id) {
    window.location.href=Feng.ctxPath + '/platform/cash/withdrawal/toDetail/'+id;
};
DataCount.showdesc =  function (pid) {
    var index = layer.open({
        type: 1,
        title: '数据指标',
        area: ['660px', '350px'], //宽高
        content: '<div style="line-height: 30px;padding: 15px;">\n' +
        '    1、浏览量：         时间段内，相关页面的浏览（店铺主页、商品详情页）总量。一个用户多次浏览会被记为多次。<br>\n' +
        '    2、访客数 ：           时间段内，相关页面的浏览（店铺主页、商品详情页）总量。一个用户多次浏览会被记为1次。<br>\n' +
        '    3、付款人数：           时间段内，下单且付款成功的用户数，一人多次付款记为1人（不记录退款）。<br>\n' +
        '    4、付款订单数：        时间段内，成功付款的订单数，以订单号为准。（不计退款）<br>\n' +
        '    5、付款金额 ：           时间段内，所有付款的订单金额总和。（不计退款）<br>\n' +
        '    6、访问-付费转化率 ：           时间段内，付款人数/访客数<br>\n' +
        '    7、客单价 ：           时间段内，付款金额/付款人数<br>\n' +
        '    8、退款订单数 ：           时间段内，有发起退款且退款成功的订单数。<br>\n' +
        '    9、退款金额 ：           时间段内，成功退款的订单总额（以实际退款时间为准）\n' +
        '</div>'
    });
    this.layerIndex = index;
};
isplatform=function () {
    // return true;
    if(window.location.host=='platform.iytour.com'){
        return true;
    }
    return false;
};
/**
 * 查询服务费管理列表
 */
DataCount.search = function (data) {
    // if($("#createStartTime").val()=='' || $("#createEndTime").val()==''){
    //     Feng.error( "时间周期不能为空!");
    //     return false;
    // }
    if(isplatform()){
        DataCount.table.queryParams={'type':$(".active").attr('data'),'beginDate':$("#createStartTime").val(),'endDate':$("#createEndTime").val(),'shopType':$("#shopType").val(),'shopId':$("#shopId").val()};
    }else{
        DataCount.table.queryParams={'type':$(".active").attr('data'),'beginDate':$("#createStartTime").val(),'endDate':$("#createEndTime").val()};
    }

    DataCount.table.refresh({'pageNumber':1});
    initchart();
};
DataCount.export = function () {
    if(isplatform()){
        window.location.href=Feng.ctxPath +'/platformshop/data/download?type='+$(".active").attr('data')+'&beginDate='+$("#createStartTime").val()+"&endDate="+$("#createEndTime").val()+'&shopType='+$("#shopType").val()+'&shopId='+$("#shopId").val()
    }else{
        window.location.href=Feng.ctxPath +'/platformshop/data/download?type='+$(".active").attr('data')+'&beginDate='+$("#createStartTime").val()+"&endDate="+$("#createEndTime").val()
    }

};
DataCount.getDate = function (dates) {
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
totable=function (obj) {
    $(".btntab").removeClass('active');
    $(obj).addClass('active');
    $("#table").show();
    $("#chart").hide()
};
tochart=function (obj) {
    $(".btntab").removeClass('active');
    $(obj).addClass('active');
    $("#table").hide();
    $("#chart").show();
    DataCount.search()

};
function toPoint(percent){
    var str=percent.replace("%","");
    str= str/100;
    return str;
}
initchart=function () {
    var date=[];
    var refundOrderCount=[];
    var customerUnitPrice=[];
    var payerCount=[];
    var visitorCount=[];
    var payInversionRate=[];
    var orderCount=[];
    var range=[];
    var viewCount=[];
    var totalPayAmount=[];
    var totalRefundAmount=[];
    var ajax = new $ax(Feng.ctxPath + "/platformshop/data/chart", function (res) {
        if(res.code==200){
            $.each(res.data.rows,function (index,item) {
                date.push(item.date);
                refundOrderCount.push(item.refundOrderCount);
                customerUnitPrice.push(item.customerUnitPrice);
                payerCount.push(item.payerCount);
                visitorCount.push(item.visitorCount);
                payInversionRate.push(toPoint(item.payInversionRate));
                orderCount.push(item.orderCount);
                range.push(item.range);
                viewCount.push(item.viewCount);
                totalPayAmount.push(item.totalPayAmount);
                totalRefundAmount.push(item.totalRefundAmount)
            });
        }else{
            Feng.error(res.message + "!");
        }
    }, function (data) {
        Feng.error(data.responseJSON.message + "!");
    });
    var params={'type':$(".active").attr('data'),'beginDate':$("#createStartTime").val(),'endDate':$("#createEndTime").val()};
    if(isplatform()){
        params={'type':$(".active").attr('data'),'beginDate':$("#createStartTime").val(),'endDate':$("#createEndTime").val(),'shopType':$("#shopType").val(),'shopId':$("#shopId").val()};
    }
    ajax.set(params);
    ajax.start();
    // console.info(viewCount)
    var option = {

        tooltip : {
            trigger: 'axis'
        },
        legend: {
            data:['浏览量','访客数','付款人数','付款订单数','付款金额','访问-付费转化率','客单价','退款订单数','退款金额']
        },
        toolbox: {
            show : false,
        },
        calculable : true,
        xAxis : [
            {
                type : 'category',

                data : date
            }
        ],
        yAxis : [
            {
                type : 'value',
                axisLabel : {
                    formatter: '{value}'
                }
            }
        ],
        series : [
            {
                name:'浏览量',
                type:'line',
                data:viewCount,

            },
            {
                name:'访客数',
                type:'line',
                data:visitorCount,

            },
            {
                name:'付款人数',
                type:'line',
                data:payerCount,
            },
            {
                name:'付款订单数',
                type:'line',
                data:orderCount,
            },
            {
                name:'付款金额',
                type:'line',
                data:totalPayAmount,

            },
            {
                name:'访问-付费转化率',
                type:'line',
                data:payInversionRate,

            },
            {
                name:'客单价',
                type:'line',
                data:customerUnitPrice,

            },
            {
                name:'退款订单数',
                type:'line',
                data:refundOrderCount,

            },
            {
                name:'退款金额',
                type:'line',
                data:totalRefundAmount,

            },

        ]
    };
    var myChart = echarts.init(document.getElementById('chart'));
    myChart.setOption(option);
};
$(function () {
    $("#createStartTime").val(DataCount.getDate(0));
    $("#createEndTime").val(DataCount.getDate(0));
    $(".mybt").click(function () {

        $(".mybt").removeClass('active');
        $(this).addClass('active')
        DataCount.search()
    });
    if(!isplatform()){
        $("#platformstr").text('商家报表')
    }else{
        $("#shoptyperow").show()
    }
    var defaultColunms = DataCount.initColumn();
    var table = new BSTable(DataCount.id, "/platformshop/data/table", defaultColunms);
    table.queryParams={'type':1,'beginDate':$("#createStartTime").val(),'endDate':$("#createEndTime").val()};
    table.setPaginationType("server");
    DataCount.table = table.init();
    $("div.fixed-table-toolbar").html('');
    initchart();

    $("#chart").hide()
});
