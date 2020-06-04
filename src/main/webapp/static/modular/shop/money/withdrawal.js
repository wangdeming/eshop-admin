/**
 * 初始化店铺列表详情对话框
 */
var withdrawal = {
    withdrawalData : {},
    validateFields: {
        amount: {
            validators: {
                notEmpty: {
                    message: '提现金额不能为空'
                },
            }
        },
        accountName: {
            validators: {
                notEmpty: {
                    message: '真实姓名不能为空'
                },
            }
        },
        accountNo: {
            validators: {
                notEmpty: {
                    message: '账号不能为空'
                },
            }
        },

    }
};
/**
 * 验证数据是否为空
 */
withdrawal.validate = function () {
    $('#insertSubAccountForm').data("bootstrapValidator").resetForm();
    $('#insertSubAccountForm').bootstrapValidator('validate');
    return $("#insertSubAccountForm").data('bootstrapValidator').isValid();
};

/**
 * 清除数据
 */
withdrawal.clearData = function() {
    this.withdrawalData = {};
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
withdrawal.set = function(key, val) {
    this.withdrawalData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
withdrawal.get = function(key) {
    return $("#" + key).val();
};


/**
 * 收集数据
 */
withdrawal.collectData = function() {
    $("#id").val()?this.set('id'):'';
    this.set('amount');
    this.set('accountName');
    this.set('drawWay',$('input:radio[name="drawWay"]:checked').val());
    this.set('accountNo');
};


/**
 * 提交添加
 */
withdrawal.addSubmit = function() {

    this.collectData();
    //非空验证
    if (!withdrawal.validate()) {
        return;
    }
    var ajax = new $ax(Feng.ctxPath + "/shop/money/withdrawal", function (data) {
        if(data.code==200){
            Feng.success(data.message + "!");
            withdrawal.ok();
        }else{
            Feng.error( data.message + "!");
        }
    }, function (data) {
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.withdrawalData);
    ajax.start();
};


withdrawal.getUrlParam = function(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]); return null; //返回参数值
};
getfstr = function (sv){
    if(sv == "" || sv == null || sv == undefined){
        return '';
    }
    return sv;
};
$(function() {
    $(":radio").click(function(){
       if($(this).val()==2){
            $("#accountNolable").text('支付宝账号');
           $("#accountNo").attr('placeholder','请输入支付宝账号');
       }else{
           $("#accountNolable").text('微信账号');
           $("#accountNo").attr('placeholder','请输入微信账号');
       }
    });

    $("#allbtn").click(function () {
        $("#amount").val($("#availableBalance").text());
    });
    var ajax = new $ax(Feng.ctxPath + "/shop/money/lastwithdrawalinfo", function (res) {
        if(res.code==200){
            $("#accountName").val(getfstr(res.data.accountName));
            $("#accountNo").val(getfstr(res.data.accountNo));
            $("#availableBalance").text(getfstr(res.data.availableBalance));
            $("#amount").attr('max',getfstr(res.data.availableBalance))
        }else{
            Feng.error( res.message + "!");
        }
    }, function (data) {
        Feng.error(data.responseJSON.message + "!");
    });
    ajax.start();
    Feng.initValidator("insertSubAccountForm", withdrawal.validateFields);

});
