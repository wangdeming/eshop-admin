/**
 * 店铺信息管理管理初始化
 */
var goodsList = {
    id: "goodsListTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
goodsList.initColumn = function () {
    return [
        {field: 'selectItem', checkbox: true,width:'5%'},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle',width:'5%'},
        {title: '商品价格', field: 'price', align: 'left', valign: 'middle',sortable: true,width:'25%',
            formatter: function (value, row, index) {
                return  '<img src="'+row.img+'"  class="pull-left col-sm-4"><div class="pull-left col-sm-8">'+row.goodsName+'</br>'+row.price+'</div>';
            }
        },
        {title: '浏览量', field: 'viewNum', align: 'left', valign: 'middle' ,sortable: true,width:'5%'},
        {title: '访客数', field: 'visitorNum', align: 'left', valign: 'middle',sortable: true,width:'5%'},
        {title: '库存', field: 'stock', align: 'left', valign: 'middle',sortable: true,width:'5%'},
        {title: '总销量', field: 'saleNum', align: 'left', valign: 'middle',sortable: true,width:'5%'},
        {title: '创建时间', field: 'createdTime', align: 'left', valign: 'middle',sortable: true,width:'15%'},
        {title: '排序', field: 'sequence', align: 'left', valign: 'middle',sortable: true,width:'10%',
            formatter: function (value, row, index) {
                var chtm='&nbsp;&nbsp;<span class="glyphicon glyphicon-edit" onclick="goodsList.updateseq('+value+','+row.id+')"></span>';
                return typeof(value)=='undefined'?chtm:value+chtm;
            }
        },
        {title: '下架原因', field: 'platformManageReason', align: 'left', valign: 'middle',width:'15%'},
        {title: '操作', field: 'stateDes', visible: true, align: 'left', valign: 'middle',width:'20%',
            formatter: function (value, row, index) {
                return '<a style="margin-right: 30px;" onclick="goodsList.opengoodsListDetail('+row.id+',1)">编辑</a>';
            }
        }

    ];
};
goodsList.updateseq = function (seq,id) {
    this.layerIndex = layer.open({
        type: 1,
        title:"修改顺序",
        shadeClose: true, //开启遮罩关闭
        content: '<br/><div class="col-sm-12"><input type="number" class="form-control" value="'+seq+'" id="seq"></div>' +
        '<div class="col-sm-12" style="padding-bottom: 20px"><br/><button class="btn btn-danger" onclick="goodsList.close()">取消</button>' +
        '<button class="btn btn-primary" onclick="goodsList.updateSequence('+id+')">保存</button></div>'
    });
};
goodsList.close = function() {

    layer.close(this.layerIndex);
};
/**
 * 检查是否选中
 */
goodsList.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        goodsList.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加店铺信息管理
 */
goodsList.openAddgoodsList = function () {
    window.location.href=Feng.ctxPath + '/shop/goods/toinsert';

};

/**
 * 打开查看店铺信息管理详情
 */
goodsList.opengoodsListDetail = function (id,isact) {
    window.location.href=Feng.ctxPath + '/shop/goods/goodsUpdate/'+id;
};

goodsList.shelf = function (obj) {
    var selected = $('#' + goodsList.id).bootstrapTable('getSelections');

    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        layer.confirm('确定'+$(obj).text()+'所选商品？', {
            btn: ['确定','取消'], //按钮
            shade: false //不显示遮罩
        }, function(index){
            var ids=[];
            $.each(selected,function (k,v) {
                console.info(v);
                ids[k]=v.id;
            });
            var ajax = new $ax(Feng.ctxPath + "/shop/goods/"+$(obj).attr("shelf"), function (data) {
                if(data.code==200){
                    layer.close(index);
                    Feng.success(data.message);
                    goodsList.table.refresh();
                }else{
                    Feng.error(data.message + "!");
                }

            }, function (data) {
                Feng.error(data.responseJSON.message + "!");
            });
            if($(obj).attr("shelf")=='delete'){
                ajax.set("goodsId",ids.join(','));
            }else{
                ajax.set("goodsIds",ids.join(','));
            }
            ajax.start();
        }, function(){

        });
    }

};
goodsList.updateSequence = function (goodsId) {
    var ajax = new $ax(Feng.ctxPath + "/shop/goods/updateSequence", function (data) {
        if(data.code==200){
            Feng.success(data.message);
            goodsList.close();
            goodsList.search()
        }else{
            Feng.error(data.message + "!");
        }
    }, function (data) {
        Feng.error(data.responseJSON.message + "!");
    });
    ajax.set("goodsId",goodsId);
    ajax.set("sequence",$("#seq").val());
    ajax.start();

};

goodsList.del = function (goodsId) {
    var ajax = new $ax(Feng.ctxPath + "/shop/goods/updateSequence", function (data) {
        if(data.code==200){
            Feng.success(data.message);

        }else{
            Feng.error(data.message + "!");
        }
    }, function (data) {
        Feng.error(data.responseJSON.message + "!");
    });
    ajax.set("goodsId",goodsId);
    ajax.start();

};
/**
 * 查询店铺信息管理列表
 */
goodsList.search = function () {
    var queryData = {};
    queryData['minPrice'] = $("#minPrice").val();
    queryData['maxPrice'] = $("#maxPrice").val();
    queryData['minSale'] = $("#minSale").val();
    queryData['maxSale'] = $("#maxSale").val();
    queryData['goodsName'] = $("#goodsName").val();
    if ($("#status").val() == 4) {
        queryData['platformManage'] = 0;
        queryData['status'] = '';
    } else {
        queryData['status'] = $("#status").val();
        queryData['platformManage'] = 1;
    }
    goodsList.table.refresh({query: queryData});
};

$(function () {
    $("#shelves").hide();
    var defaultColunms = goodsList.initColumn();
    var table = new BSTable(goodsList.id, "/shop/goods/list", defaultColunms);
    table.setPaginationType("server");
    table.queryParams={'status':$("#status").val()};
    table.onLoadSuccess = function () {
        if ($("#status").val() == 4) {
            $("#goodsListTable").bootstrapTable('showColumn', 'platformManageReason');
        } else {
            $("#goodsListTable").bootstrapTable('hideColumn', 'platformManageReason');
        }
    };
    goodsList.table = table.init( );

    $("#shelf").text('下架');
    $("#shelf").attr('shelf','offshelf');

    $(".mybt").click(function () {
        var staval=$(this).attr('data');
        console.info(staval);
        if(staval==3){
            $("#shelves").hide();
            $("#deletebtn").show();
            $("#shelf").show();
            $("#shelf").text('上架');
            $("#shelf").attr('shelf','onshelf');
        } else if (staval==4) {
            $("#shelves").show();
            $("#shelf").hide();
            $("#deletebtn").show();
        } else if (staval==1 || staval==2) {
            $("#shelf").show();
            $("#shelves").hide();
            $("#deletebtn").hide();
            $("#shelf").text('下架');
            $("#shelf").attr('shelf','offshelf');
        }
        $("#status").val(staval);
        $(".mybt").removeClass('active');
        $(this).addClass('active');
        goodsList.search();
    });
    $("#shopType").change(function () {
        goodsList.search();
    })
});

/**
 * 系统下架商品重新上架
 */
goodsList.shelves = function (obj) {
    var selected = $('#' + goodsList.id).bootstrapTable('getSelections');

    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        layer.confirm('是否申请上架所选商品？<div style="color: red;">注：申请需要平台审核，审核通过后，\n' +
            '\n' +
            '商品会恢复系统下架前的状态</div>', {
            btn: ['确定','取消'], //按钮
            shade: false //不显示遮罩
        }, function(index){
            var ids=[];
            $.each(selected,function (k,v) {
                console.info(v);
                ids[k]=v.id;
            });
            var ajax = new $ax(Feng.ctxPath + "/shop/goods/applyGoodsOnShelf/", function (data) {
                if(data.code==200){
                    layer.close(index);
                    Feng.success(data.message);
                    goodsList.table.refresh();
                }else{
                    Feng.error(data.message + "!");
                }

            }, function (data) {
                Feng.error(data.responseJSON.message + "!");
            });
            if($(obj).attr("shelf")=='delete'){
                ajax.set("goodsId",ids.join(','));
            }else{
                ajax.set("goodsIds",ids.join(','));
            }
            ajax.start();
        }, function(){
        });
    }
};
