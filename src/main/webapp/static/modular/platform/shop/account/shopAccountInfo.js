/**
 * 初始化特产类别详情对话框
 */
var shopAccountInfo = {
    goodsCategoryInfoData : {}
};

/**
 * 清除数据
 */
shopAccountInfo.clearData = function() {
    this.goodsCategoryInfoData = {};
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
shopAccountInfo.set = function(key, val) {
    this.goodsCategoryInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
shopAccountInfo.get = function(key) {
    return $("#" + key).val();
};

/**
 * 关闭此对话框
 */
shopAccountInfo.close = function() {
    window.location.href=Feng.ctxPath + "/platform/shopaccount"
};
shopAccountInfo.torecord = function() {
    window.location.href=Feng.ctxPath + "/platform/shopacctoper/toOperRecord/"+shopAccountInfo.getUrlParam('accountId');
};
/**
 * 收集数据
 */
shopAccountInfo.collectData = function() {
    $("#id").val()?this.set('id'):'';
    this.set('pid');
    this.set('name');
};

/**
 * 提交添加
 */
shopAccountInfo.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/platform/goodscategory/add", function(data){
        if(data.code==200){
            Feng.success("添加成功!");
            window.parent.GoodsCategory.ok();
            shopAccountInfo.close();
        }else{
            Feng.error( data.message + "!");
        }

    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.goodsCategoryInfoData);
    ajax.start();
};

/**
 * 提交修改
 */
shopAccountInfo.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/platform/goodscategory/update", function(data){
        if(data.code==200){
            Feng.success("修改成功!");
            window.parent.GoodsCategory.ok();
            shopAccountInfo.close();
        }else{
            Feng.error( data.message + "!");
        }
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.goodsCategoryInfoData);
    ajax.start();
};
/**
 * 获取一级类别集合
 */
shopAccountInfo.listParentNodes = function() {
    var ajax = new $ax(Feng.ctxPath + "/platform/goodscategory/listParentNodes", function(res){
        $.each(res.data,function (k,v) {
            var selhtml='';
            if(shopAccountInfo.getUrlParam('pid')==v.id){
                selhtml='selected="selected"';
            }
            $("#pid").append('<option value="'+v.id+'" '+selhtml+'>'+v.name+'</option>');
        })
    },function(data){
        Feng.error(  data.responseJSON.message + "!");
    });
    ajax.start();
};
shopAccountInfo.getUrlParam = function(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]); return null; //返回参数值
};
$(function() {

});
