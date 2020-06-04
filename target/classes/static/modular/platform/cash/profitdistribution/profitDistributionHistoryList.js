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
 * 确定
 */
AdInfoManagerAccount.openAdd = function() {
    window.history.go(-1);
};

/**
 * 获取变更记录列表
 */
AdInfoManagerAccount.getRecommendShopAd = function () {
    var profitDistributionId = $("#profitDistributionId").val();
    var ajax = new $ax(Feng.ctxPath + "/platform/cash/profitDistributionHistory/list", function (data) {
        var result = '';
        var elem = data.data;
        if (elem.length == 0) {
            result  += '                                <div id="tablelist" style="text-align: center;margin-bottom: 20px;">没有找到匹配的记录</div>\n';
            $(".project").append(result);
        } else {
            $.each(elem,function (index,elem) {
                var beforeServiceRate = elem.beforeServiceRate;
                var afterServiceRate = elem.afterServiceRate;
                var effectiveTime = elem.effectiveTime;
                var createdUserAccount = elem.createdUserAccount;
                var createdUserName = elem.createdUserName;
                var createdTime = elem.createdTime;
                result  +='<div class="col-sm-8" id="tablelist">\n' +
                    '                            <div class="" style="margin-bottom: 18px;padding: 10px 15px;border-bottom: 1px solid #666;">'+createdTime+'</div>\n' +
                    '                            <table class="table table-bordered">\n' +
                    '                                <tbody>\n' +
                    '                                <tr>\n' +
                    '                                    <th>变更前费率</th>\n' +
                    '                                    <th>变更后费率</th>\n' +
                    '                                    <th>生效日期</th>\n' +
                    '                                    <th>管理员账号</th>\n' +
                    '                                    <th>管理员姓名</th>\n' +
                    '                                </tr>\n' +
                    '                                <tr>\n' +
                    '                                    <td id="beforeServiceRate">'+beforeServiceRate+'</td>\n' +
                    '                                    <td id="afterServiceRate">'+afterServiceRate+'</td>\n' +
                    '                                    <td id="effectiveTime">'+effectiveTime+'</td>\n' +
                    '                                    <td id="createdUserAccount">'+createdUserAccount+'</td>\n' +
                    '                                    <td id="createdUserName"d>'+createdUserName+'</td>\n' +
                    '                                </tr>\n' +
                    '                                </tbody>\n' +
                    '                            </table>\n' +
                    '                        </div>'
            });
            $(".project").append(result);
         }
    }, function (data) {
        Feng.error(data.responseJSON.message);
    });
    ajax.set("profitDistributionId",profitDistributionId);
    ajax.start();
};