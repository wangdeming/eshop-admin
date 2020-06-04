/**
 * Created by macroot on 2019/4/9.
 */
var AdInfoManagerAccount = {
    layerIndex: -1
};

$(function () {
    AdInfoManagerAccount.getRecommendShopAd();
});
/**
 * 获取推荐店铺广告列表
 */
AdInfoManagerAccount.getRecommendShopAd = function () {
    var ajax = new $ax(Feng.ctxPath + "/platform/adInfoManager/shopAdList", function (data) {
        for (var i=0;i<data.rows.length;i++){
            var index = i + 1;
            if (data.rows[i].status == 1){
                $("#type"+index).text("已发布");
            }else {
                $("#type"+index).text("未发布");
            }
            $("#type"+index).attr("data-adInfoId",data.rows[i].id);
            $("#img"+index).attr("src",data.rows[i].img);
            $("#shopId"+index).text(data.rows[i].relationVal);
            $("#time"+index).text(data.rows[i].publishTime);
        }
    }, function (data) {
        Feng.error(data.responseJSON.message);
    });
    ajax.start();
};
/**
 * 编辑推荐店铺广告
 */
AdInfoManagerAccount.editAd = function(sequence) {
    var adInfoId = $("#type" + sequence).attr("data-adInfoId");
    var tempStr = '';
    if(adInfoId == ''){
        tempStr = '/platform/adInfoManager/recommendShopAdAdd/';
    }else {
        tempStr = '/platform/adInfoManager/recommendShopAd_update/'+adInfoId;
    }
    var index = layer.open({
        type: 2,
        title: '编辑广告位',
        area: ['600px', '380px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: [Feng.ctxPath + tempStr]
    });
    this.layerIndex = index;
    sessionStorage.setItem("sequence",sequence);
};