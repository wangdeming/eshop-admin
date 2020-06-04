/**
 * 特产类别管理初始化
 */
var GoodsCategory = {
    id: "GoodsCategoryTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
GoodsCategory.initColumn = function () {
    return [


        {title: '特产类目', field: 'name', align: 'left', valign: 'middle', width:'25%'},
        {title: '顺序移动', field: 'sequence', align: 'left', valign: 'middle', width:'20%',
            formatter:function(value,row,index){
                var z;

                if(row.top=="true"){
                    var x = '<div id="updown" style="display: inline-block;margin-right: 10px;">'+
                        '<a id=\''+ row.id + 'upNo\' data-pId=\''+ row.pId + '\' data-sequence=\''+ row.sequence + '\' disabled="disable" style="display: inline-block;cursor:not-allowed;width: 10px;height: 20px;background-image: url('+Feng.ctxPath+'/static/img/up_disable.svg);background-size: 100% 100%;background-repeat: no-repeat;"></a>'+
                        '</div>';
                }else if(row.top=="false"){
                    var x = '<div id="updown" style="display: inline-block;margin-right: 10px;">'+
                        '<a id=\''+ row.id + 'up\' data-pId=\''+ row.pId + '\' data-id=\''+ row.id + '\' data-sequence=\''+ row.sequence + '\' onclick="GoodsCategory.up(\''+ row.id + '\')" style="display: inline-block;width: 10px;height: 20px;background-image: url('+Feng.ctxPath+'/static/img/up_able.svg);background-size: 100% 100%;background-repeat: no-repeat;"></a>'+
                        '</div>';
                }
                if(row.bottom=="true"){
                    var y = '<div style="display: inline-block;">'+
                        '<a id=\''+ row.id + 'downNo\' data-pId=\''+ row.pId + '\' data-sequence=\''+ row.sequence + '\' disabled="disable" style="display: inline-block;cursor:not-allowed;width: 10px;height: 20px;background-image: url('+Feng.ctxPath+'/static/img/down_disable.svg);background-size: 100% 100%;background-repeat: no-repeat;"></a>'+
                        '</div>';
                }else if(row.bottom=="false"){
                    var y = '<div style="display: inline-block;">'+
                        '<a id=\''+ row.id + 'down\' data-pId=\''+ row.pId + '\' data-id=\''+ row.id + '\' data-sequence=\''+ row.sequence + '\' onclick="GoodsCategory.down(\''+ row.id + '\')" style="display: inline-block;width: 10px;height: 20px;background-image: url('+Feng.ctxPath+'/static/img/down_able.svg);background-size: 100% 100%;background-repeat: no-repeat;"></a>'+
                        '</div>';
                }
                z = x+y;
                return z;
            }
        },
        {title: '创建时间', field: 'createdTime', align: 'left', valign: 'middle'},
        {title: '操作', field: 'stateDes', visible: true, align: 'left', valign: 'middle',width:'30%',
            formatter: function (value, row, index) {
                var createsub='';
                if(row.pId==0){
                    createsub='<a style="margin-right: 30px;" onclick="GoodsCategory.openAddGoodsCategory('+row.id+')">创建二级类目</a>';
                }
                return '<a style="margin-right: 30px;" onclick="GoodsCategory.openGoodsCategoryDetail('+row.pId+','+row.id+')">编辑</a>'+
                    '<a  style="margin-right: 30px;" onclick="GoodsCategory.delete('+row.id+')">删除</a>'+createsub;
            }
        }
    ];
};
//上移
GoodsCategory.up = function (id) {
    var ajax = new $ax(Feng.ctxPath + "/platform/goodscategory/moveUp", function (data) {
        Feng.success("上移成功!");
        GoodsCategory.ok()
    }, function (data) {
        Feng.error("上移失败!" + data.responseJSON.message + "!");
    });
    ajax.set("id",id);
    ajax.start();
};
//下移
GoodsCategory.down = function (id) {
    var ajax = new $ax(Feng.ctxPath + "/platform/goodscategory/moveDown", function (data) {
        Feng.success("下移成功!");
        GoodsCategory.ok();
    }, function (data) {
        Feng.error("下移失败!" + data.responseJSON.message + "!");
    });
    ajax.set("id",id);
    ajax.start();
};
/**
 * 检查是否选中
 */
GoodsCategory.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        GoodsCategory.seItem = selected[0];
        return true;
    }
};
GoodsCategory.ok = function () {
    GoodsCategory.table.refresh();
    $("#GoodsCategoryTable>thead>tr>th").css("text-align","left");
};
/**
 * 点击添加特产类别
 */
GoodsCategory.openAddGoodsCategory = function (pid) {
    var index = layer.open({
        type: 2,
        title: '添加特产类目',
        area: ['800px', '550px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/platform/goodscategory/toAdd?pid='+pid
    });
    this.layerIndex = index;
};

/**
 * 打开查看特产类别详情
 */
GoodsCategory.openGoodsCategoryDetail = function (pid,id) {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '编辑特产类目',
            area: ['800px', '550px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/platform/goodscategory/toEdit/'+id+'?pid='+pid
        });
        this.layerIndex = index;
    }
};

/**
 * 删除特产类别
 */
GoodsCategory.delete = function (id) {
    var ajax = new $ax(Feng.ctxPath + "/platform/goodscategory/delete", function (data) {
        if(data.code==200){
            Feng.success("删除成功!");
            GoodsCategory.table.refresh();
        }else{
            Feng.error( data.message + "!");
        }
    }, function (data) {
        Feng.error("删除失败!" + data.responseJSON.message + "!");
    });
    ajax.set("id",id);
    ajax.start();
};

/**
 * 查询特产类别列表
 */
GoodsCategory.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    GoodsCategory.table.refresh({query: queryData});
};
function openSubmenu() {
    $("span.treegrid-expander").each(function (index,item) {
        if($(item).hasClass("glyphicon-chevron-right")){
            $(item).trigger("click");
        }
    });
}
function closeSubmenu() {
    $("span.treegrid-expander").each(function (index,item) {
        if($(item).hasClass("glyphicon-chevron-down")){
            $(item).trigger("click");
        }
    });
}
$(function () {
    var defaultColunms = GoodsCategory.initColumn();
    var table = new BSTreeTable(GoodsCategory.id, "/platform/goodscategory/list", defaultColunms);
    table.setExpandColumn(0);
    table.setIdField("id");
    table.setCodeField("id");
    table.setParentCodeField("pId");
    table.setExpandAll(false);
    table.init();
    GoodsCategory.table = table;
    $("#GoodsCategoryTable>thead>tr>th").css("text-align","left");
    var tempHtml = '<div class="columns columns-right btn-group pull-right"><button type="button" class="btn btn-outline btn-default" onclick="openSubmenu();">展开</button><button type="button" class="btn btn-outline btn-default" onclick="closeSubmenu();" id="closesub">收起</button></div>';
    $("div.fixed-table-toolbar").append(tempHtml);





});
