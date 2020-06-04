/**
 * 店铺服务费管理初始化
 */
var ShopInfoMgr = {
    id: "BalanceFlowTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
ShopInfoMgr.initColumn = function () {
    return [
        {title: 'id', field: 'id', visible: false, align: 'left', valign: 'middle'},
        {title: '申请时间', field: 'createdTime', align: 'left', valign: 'middle', width:'17%'},
        {title: '提现金额', field: 'amount', align: 'left', valign: 'middle', width:'12%'},
        {title: '提现状态', field: 'statusName', align: 'left', valign: 'middle'},
        {title: '店铺名称', field: 'shopName', align: 'left', valign: 'middle', width:'15%'},
        {title: '店铺类型', field: 'shopType', align: 'left', valign: 'middle'},
        {title: '操作', field: 'stateDes', visible: true, align: 'left', valign: 'middle',width:'20%',
            formatter: function (value, row, index) {
                if(row.status==1) {
                    return  '<a style="margin-right: 30px;" onclick="ShopInfoMgr.pass(' + row.id + ')">同意</a>'+
                        '<a style="margin-right: 30px;" onclick="ShopInfoMgr.refusePass(' + row.id + ')">不同意</a>'+
                        '<a style="margin-right: 30px;" onclick="ShopInfoMgr.openHistoryList(' + row.id + ')">查看</a>';
                } else if(row.status==2) {
                    return  '<a style="margin-right: 30px;" onclick="ShopInfoMgr.confirm(' + row.id + ')">确认打款</a>'+
                        '<a style="margin-right: 30px;" onclick="ShopInfoMgr.openHistoryList(' + row.id + ')">查看</a>';
                } else {
                    return  '<a style="margin-right: 30px;" onclick="ShopInfoMgr.openHistoryList(' + row.id + ')">查看</a>';
                }
            }
        }

    ];
};

/**
 * 查看提现详情
 */
ShopInfoMgr.openHistoryList = function (id) {
    sessionStorage.setItem("withdrawalId",id);
    window.location.href=Feng.ctxPath + '/platform/cash/withdrawal/toDetail/'+id;
};

/**
 * 查询提现列表
 */
ShopInfoMgr.search = function (timeType) {
    if (timeType == null) {
        timeType = $("#timeType").find('.gun-btn-active')[0].id;
    }
    if(timeType==1){
        $("#createStartTime").val(ShopInfoMgr.getDate(0));
        $("#createEndTime").val(ShopInfoMgr.getDate(0));
        $("#createStartTime,#createEndTime").attr("disabled","disabled");
    }else if(timeType==2){
        $("#createStartTime").val(ShopInfoMgr.getDate(-1));
        $("#createEndTime").val(ShopInfoMgr.getDate(-1));
        $("#createStartTime,#createEndTime").attr("disabled","disabled");
    }else if(timeType==3){
        $("#createStartTime").val(ShopInfoMgr.getDate(-6));
        $("#createEndTime").val(ShopInfoMgr.getDate(0));
        $("#createStartTime,#createEndTime").attr("disabled","disabled");
    }else if(timeType==4){
        $("#createStartTime,#createEndTime").removeAttr('disabled');
    }else if(timeType==0){
        $("#createStartTime,#createEndTime").attr("disabled","disabled");
        $("#createStartTime").val('');
        $("#createEndTime").val('');
    }

    ShopInfoMgr.table.queryParams={'condition':$("#condition").val(),'wdStatus':$("#stateType").val(),'timeType':timeType,'startDate':$("#createStartTime").val(),'endDate':$("#createEndTime").val()};
    ShopInfoMgr.table.refresh();
};
ShopInfoMgr.getDate = function (dates) {
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
    $("#createStartTime").val(ShopInfoMgr.getDate(0));
    $("#createEndTime").val(ShopInfoMgr.getDate(0));
    $("#createStartTime,#createEndTime").attr("disabled","disabled");

    // 添加选中时间快捷键样式
    $('#timeType').on('click', 'button', function() {
        $(this).addClass('gun-btn-active').siblings().removeClass("gun-btn-active");
    });

    // 监听开始时间
    $("#createStartTime").datepicker({
        keyboardNavigation: false,
        forceParse: false,
        autoclose: true,
        endDate: ShopInfoMgr.getDate(0)
    }).on("changeDate", function (e) {
        $("#createEndTime").datepicker("setStartDate", e.date);
        ShopInfoMgr.search();
    });

    // 监听结束时间
    $("#createEndTime").datepicker({
        keyboardNavigation: false,
        forceParse: false,
        autoclose: true,
        endDate: ShopInfoMgr.getDate(0)
    }).on("changeDate", function (e) {
        $("#createStartTime").datepicker("setEndDate", e.date);
        ShopInfoMgr.search();
    });

    $("#groupdate .btn").click(function () {
        $("#groupdate .btn").removeClass('active');
        $(this).addClass('active');
        var stateType=$(this).attr('data-stateType');
        $("#stateType").val(stateType);
        //统计类型（1-待审核；2-审核通过；3-确认打款；4-审核不通过；）
        ShopInfoMgr.search();
    });

    var defaultColunms = ShopInfoMgr.initColumn();
    var table = new BSTable(ShopInfoMgr.id, "/platform/cash/withdrawal/listWithdrawals4Page", defaultColunms);
    table.queryParams={'timeType':$("#timeType").find('.gun-btn-active')[0].id,'wdStatus':''};
    table.setPaginationType("server");
    ShopInfoMgr.table = table.init();
});

/**
 * 同意提现
 */
ShopInfoMgr.pass = function (id) {
    var operation = function(){
        var ajax = new $ax(Feng.ctxPath + "/platform/cash/withdrawal/pass", function(data){
            if(data.code==200){
                Feng.success("操作成功!");
                ShopInfoMgr.table.refresh();
            }else{id;
                Feng.error( data.message + "!");
            }
        });
        ajax.set("withdrawalId",id);
        ajax.start();
    };
    Feng.confirm("是否同意提现?", operation);
};

/**
 * 确认打款
 */
ShopInfoMgr.confirm = function(id) {
    var operation = function(){
        var ajax = new $ax(Feng.ctxPath + "/platform/cash/withdrawal/confirm", function(data){
            if(data.code==200){
                Feng.success("操作成功!");
                ShopInfoMgr.table.refresh();
            }else{id;
                Feng.error( data.message + "!");
            }
        });
        ajax.set("withdrawalId",id);
        ajax.start();
    };
    Feng.confirm("是否确认打款?", operation);
};

/**
 * 不同意提现
 */
ShopInfoMgr.refusePass = function(id) {
    var operation = function(){
        var ajax = new $ax(Feng.ctxPath + "/platform/cash/withdrawal/refusePass", function(data){
            if(data.code==200){
                Feng.success("操作成功!");
                ShopInfoMgr.table.refresh();
            }else{id;
                Feng.error( data.message + "!");
            }
        });
        ajax.set("withdrawalId",id);
        ajax.start();
    };
    Feng.confirm("是否不同意提现?", operation);
};