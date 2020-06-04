$(function () {
    $("#commitBtn").hide();
    $("#surePayBtn").hide();
    $("#sureBtn").hide();
    $("#cancel").hide();
    if ($("#statusInput").val() == 1) {
        $("#commitBtn").show();
        $("#cancel").show();
    }else if($("#statusInput").val() == 2){
        $("#surePayBtn").show();
    }else if($("#statusInput").val() == 3){
        $("#sureBtn").show();
    }else{
        $("#sureBtn").show();
    }
});
/**
 * 初始化提现详情
 */
var withdrawalDetail = {
};

/**
 * 确认
 */
withdrawalDetail.close = function() {
    window.history.go(-1);
}

/**
 * 同意提现
 */
withdrawalDetail.pass = function() {
    var withdrawalId = sessionStorage.getItem("withdrawalId");
    var operation = function(){
        var ajax = new $ax(Feng.ctxPath + "/platform/cash/withdrawal/pass", function(data){
            if(data.code==200){
                Feng.success("操作成功!");
                withdrawalDetail.close();
            }else{id
                Feng.error( data.message + "!");
            }
        });
        ajax.set("withdrawalId",withdrawalId);
        ajax.start();
    };
    Feng.confirm("是否同意提现?", operation);
}

/**
 * 确认打款
 */
withdrawalDetail.confirm = function() {
    var withdrawalId = sessionStorage.getItem("withdrawalId");
    var operation = function(){
        var ajax = new $ax(Feng.ctxPath + "/platform/cash/withdrawal/confirm", function(data){
            if(data.code==200){
                Feng.success("操作成功!");
                withdrawalDetail.close();
            }else{id
                Feng.error( data.message + "!");
            }
        });
        ajax.set("withdrawalId",withdrawalId);
        ajax.start();
    };
    Feng.confirm("是否确认打款?", operation);
}

/**
 * 不同意提现
 */
withdrawalDetail.refusePass = function() {
    var withdrawalId = sessionStorage.getItem("withdrawalId");
    var operation = function(){
        var ajax = new $ax(Feng.ctxPath + "/platform/cash/withdrawal/refusePass", function(data){
            if(data.code==200){
                Feng.success("操作成功!");
                withdrawalDetail.close();
            }else{id
                Feng.error( data.message + "!");
            }
        });
        ajax.set("withdrawalId",withdrawalId);
        ajax.start();
    };
    Feng.confirm("是否不同意提现?", operation);
}

