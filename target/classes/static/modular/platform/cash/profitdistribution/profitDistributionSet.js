/**
 * 初始化特产类别详情对话框
 */
var profitDistribution = {
    profitDistributionData : {},
    validateFields: {
        changeServiceRate: {
            validators: {
                notEmpty: {
                    message: '变更后费率不能为空'
                },
                regexp: {//正则验证
                    regexp:  /^(?:0|[1-9][0-9]?|100)$/,
                    message: '输入范围：0~100，不能与当前费率一致'
                },
            }
        },
        effectiveTime: {
            validators: {
                notEmpty: {
                    message: '生效日期不能为空'
                }
            }
        }
    }
};

/**
 * 清除数据
 */
profitDistribution.clearData = function() {
    this.profitDistributionData = {};
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
profitDistribution.set = function(key, val) {
    this.profitDistributionData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
profitDistribution.get = function(key) {
    return $("#" + key).val();
};

/**
 * 取消按钮
 */

profitDistribution.close = function() {
    window.history.go(-1);
};

profitDistribution.getDate = function (dates) {
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
    $("#effectiveTime").val(profitDistribution.getDate(1));
    Feng.initValidator("talentForm", profitDistribution.validateFields);
});
/**
 * 提交修改
 */
profitDistribution.editSubmit = function() {

    var pid = $("#pid").val();
    var changeServiceRate = $("#changeServiceRate").val();
    var effectiveTime = $("#effectiveTime").val();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/platform/cash/profitDistribution/update", function(data){
        if(data.code==200){
            Feng.success("修改成功!");
            profitDistribution.close();
        }else{
            Feng.error( data.message + "!");
        }
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set("profitDistributionId",pid);
    ajax.set("changeServiceRate",changeServiceRate);
    ajax.set("effectiveTime",effectiveTime);
    ajax.start();
};