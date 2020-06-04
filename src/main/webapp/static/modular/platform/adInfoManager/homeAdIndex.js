/**
 * Created by macroot on 2019/4/9.
 */
/**
 * 店铺账号管理管理初始化
 */
var AdInfoManagerAccount = {
    id: "AdInfoManagerTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};
/**
 * 初始化表格的列
 */
AdInfoManagerAccount.initColumn = function () {
    return [
        {title: 'id', field: 'id', visible: false, align: 'center'},
        {title: '排序', field: 'sequence', align: 'center',
            formatter: function (value, row, index) {
                var num = row.sequence;
                if (row.status == 1){
                    return '<span style="float: left;margin-left: 30%;">'+num+'</span>'+
                        '<a onclick="AdInfoManagerAccount.changeSequence('+row.id+','+row.sequence+')" style="float: left;width: 16px;height: 20px;background-image: url('+Feng.ctxPath+'/static/img/edit.svg);display: inline-block;background-size: 100% 100%;background-repeat: no-repeat;margin-left: 5px;"></a>';
                }else {
                    return '-';
                }
            }
        },
        {title: '状态', field: 'status', align: 'center',
            formatter: function (value, row, index) {
                if (row.status == 1){
                    return "发布中";
                }else {
                    return "未发布";
                }
            }
        },
        {title: '广告图', field: 'img', align: 'center',
            formatter: function (value, row, index) {
                var imgHtml = '<img src="'+ row.img +'" style="width: 120px;height: 40px;"/>';
                return imgHtml;
            }
        },
        {title: '创建时间', field: 'createdTime', align: 'center'},
        {title: '关联类型', field: 'type', align: 'center',
            formatter: function (value, row, index) {
                if (row.type == 1) {
                    return "商品";
                }else if (row.type == 2) {
                    return "店铺";
                }else if (row.type == 3) {
                    return "URL";
                }else {
                    return "无关联";
                }
            }
        },
        {title: '详情', field: 'relationVal', align: 'center'},
        {title: '操作', field: 'status', visible: true, align: 'center',
            formatter: function (value, row, index) {
                if(row.status==1){
                    return '<a style="margin-right: 10px;" onclick="AdInfoManagerAccount.adOffShelf('+row.id+')">下线</a><a style="margin-right: 10px;" onclick="AdInfoManagerAccount.openEdit('+row.id+','+row.sequence+')">编辑</a><a onclick="AdInfoManagerAccount.delete('+row.id+')">删除</a>';
                }else{
                    return '<a style="margin-right: 10px;" onclick="AdInfoManagerAccount.adPublish('+row.id+')">发布</a><a style="margin-right: 10px;" onclick="AdInfoManagerAccount.openEdit('+row.id+','+row.sequence+')">编辑</a><a onclick="AdInfoManagerAccount.delete('+row.id+')">删除</a>';
                }

            }
        }
    ];
};
/**
 * 查看商家端角色管理详情
 */
AdInfoManagerAccount.openEdit = function (id,sequence) {
    var index = layer.open({
        type: 2,
        title: '编辑广告位',
        area: ['600px', '500px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: [Feng.ctxPath + '/platform/adInfoManager/homeAdInfo_update/' + id],
        sequence:sequence
    });
    this.layerIndex = index;
};
/**
 * 修改排序
 */
AdInfoManagerAccount.changeSequence = function (id,sequence) {
    layer.prompt({
        formType: 0,
        value:sequence+'',
        title: '请填写新序号'
    }, function(value,index) {
        if (value == "") {
            Feng.error("新序号不能为空");
            return false;
        }
        if (!(/(^[1-9]\d*$)/.test(value))) {
            Feng.error("请输入正整数");
            return false;
        } else {
            var ajax = new $ax(Feng.ctxPath + "/platform/adInfoManager/sequenceEdit", function (data) {
                if (data.code == 200) {
                    Feng.success("修改成功!");
                    AdInfoManagerAccount.table.refresh();
                } else {
                    Feng.error(data.message + "!");
                }
            }, function (data) {
                Feng.error("修改失败" + data.responseJSON.message + "!");
            });
            ajax.set("id", id);
            ajax.set("sequence", value);
            ajax.start();
            layer.close(index);
            AdInfoManagerAccount.table.refresh();
        }
    });

};

/**
 * 刷新列表
 */
AdInfoManagerAccount.ok = function () {
    AdInfoManagerAccount.table.refresh();
};

/**
 * 下线
 */
AdInfoManagerAccount.adOffShelf = function (id) {
    var ajax = new $ax(Feng.ctxPath + "/platform/adInfoManager/adOffShelf", function (data) {
        if(data.code==200){
            Feng.success("下线成功!");
            window.location.reload();
        }else{
            Feng.error( data.message + "!");
        }
    }, function (data) {
        Feng.error("下线失败" + data.responseJSON.message + "!");
    });
    ajax.set("id",id);
    ajax.start();
};

/**
 * 发布
 */
AdInfoManagerAccount.adPublish = function (id) {
    var ajax = new $ax(Feng.ctxPath + "/platform/adInfoManager/adPublish", function (data) {
        if(data.code==200){
            Feng.success("发布成功!");
            window.location.reload();
        }else{
            Feng.error( data.message + "!");
        }
    }, function (data) {
        Feng.error("发布失败" + data.responseJSON.message + "!");
    });
    ajax.set("id",id);
    ajax.start();
};

/**
 * 点击添加店铺账号管理
 */
AdInfoManagerAccount.openAdd = function () {
    var index = layer.open({
        type: 2,
        title: '新增广告位',
        area: ['600px', '500px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: [Feng.ctxPath + '/platform/adInfoManager/homeAdInfoAdd']
    });
    this.layerIndex = index;
};

/**
 * 删除店铺账号管理
 */
AdInfoManagerAccount.delete = function (id) {
    var operation = function(){
        var ajax = new $ax(Feng.ctxPath + "/platform/adInfoManager/homeAdDelete", function (data) {
            if(data.code==200){
                Feng.success("删除成功!");
                window.location.reload();
            }else{
                Feng.error( data.message + "!");
            }
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("id", id);
        ajax.start();
    };
    Feng.confirm("是否确定删除本条广告?", operation);
};

$(function () {
    var defaultColunms = AdInfoManagerAccount.initColumn();
    var table = new BSTable(AdInfoManagerAccount.id, "/platform/adInfoManager/homeAdList", defaultColunms);
    var queryData = {};
    queryData['limit'] = 20;
    queryData['adPosition'] = 'homePageAd';
    table.setQueryParams(queryData);
    table.method = "GET";
    AdInfoManagerAccount.table = table.init();

    layer.config({extend:'extend/layer.ext.js'});
    setTimeout(function () {
        var adAmount = 0;
        if($("#AdInfoManagerTable>tbody>tr.no-records-found").length == 0){
            adAmount = $("#AdInfoManagerTable>tbody>tr").length;
        }
        var publishAmount = 0;
        $("#AdInfoManagerTable>tbody>tr").each(function(index,item){
            if ($(item).find("td:eq(1)").text() == "发布中"){
                publishAmount += 1;
            }
        });
        $("#publishAmount").text(publishAmount);
        $("#adAmount").text(adAmount);
    },300);
});
