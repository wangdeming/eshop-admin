/**
 * 商品列表表格的列
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
        {title: 'id', field: 'id', visible: false, align: 'left', valign: 'middle'},
        {title: '商品价格', field: 'price', align: 'left', valign: 'middle',sortable: true,width:'20%',
            formatter: function (value, row, index) {
                return  '<img src="'+row.img+'"  class="pull-left col-sm-4"><div class="pull-left col-sm-8">'+row.goodsName+'</br>'+row.price+'</div>';
            }
        },
        {title: '商品ID', field: 'id', align: 'center', valign: 'middle',width:'5%'},
        {title: '一级类目', field: 'firstCategory', align: 'center', valign: 'middle',width:'5%'},
        {title: '二级类目', field: 'secondCategory', align: 'center', valign: 'middle',width:'5%'},
        {title: '店铺名称', field: 'shopName', align: 'center', valign: 'middle',width:'5%'},
        {title: '店铺ID', field: 'shopId', align: 'center', valign: 'middle',width:'5%'},
        {title: '浏览量', field: 'viewNum', align: 'left', valign: 'middle' ,sortable: true,width:'5%'},
        {title: '访客数', field: 'visitorNum', align: 'left', valign: 'middle',sortable: true,width:'5%'},
        {title: '库存', field: 'stock', align: 'left', valign: 'middle',sortable: true,width:'5%'},
        {title: '总销量', field: 'saleNum', align: 'left', valign: 'middle',sortable: true,width:'5%'},
        {title: '创建时间', field: 'createdTime', align: 'left', valign: 'middle',sortable: true,width:'10%'},
        {title: '下架原因', field: 'platformManageReason', align: 'left', valign: 'middle',width:'15%'},
        {title: '操作', field: 'stateDes', visible: true, align: 'left', valign: 'middle',width:'15%',
            formatter: function (value, row, index) {
                if (row.platformManage == 0 && row.shopApply == 1) {
                    return ''
                        + '<a style="margin-right: 30px;" data-roleid="'+row.roleid+'" data-account="'+row.account+'" onclick="goodsList.opengoodsListDetail('+row.id+', '+row.platformManage+')">查看</a>'
                        + '<a onclick="goodsList.audit('+row.id+')">审核</a>'
                } else {
                    return '<a style="margin-right: 30px;" onclick="goodsList.opengoodsListDetail('+row.id+', '+row.platformManage+')">查看</a>';
                }
            }
        }

    ];
};

/**
 * 查询店铺信息管理列表
 */
goodsList.search = function () {
    var queryData = {};
    queryData['goodsName'] = $("#goodsName").val();
    queryData['goodsId'] = $("#goodsId").val();
    queryData['shopName'] = $("#shopName").val();
    queryData['shopId'] = $("#shopId").val();
    queryData['firstCategoryId'] = $("#typeOne option:selected").attr("data-id");
    var secondCategoryId = '';
    if($("#typeTwo>option:selected").attr("data-id") != null){
        secondCategoryId = $("#typeTwo>option:selected").attr("data-id");
    }
    queryData['secondCategoryId'] = secondCategoryId;
    if ($("#status").val() == 4) {
        queryData['platformManage'] = 0;
        queryData['status'] = '';
    } else {
        queryData['status'] = $("#status").val();
        queryData['platformManage'] = 1;
    }
    goodsList.table.refresh({query: queryData});
};

/**
 * 获取商品类目
 */
function initGoodsCategoryList(pid,element) {
    $.ajaxSetup({crossDomain: true, xhrFields: {withCredentials: true}});
    $.ajax({
        url: Feng.ctxPath +"/shop/goods/goodscategorylist",
        type: 'POST',
        dataType: 'json',
        data: {pid:pid},
        async: false,
        success: function (data) {
            if(data.code != 200){
                Feng.error(data.message);
            }else{
                var dataArr = data.data;
                var optionsHtml='';
                if(element == "typeOne"){
                    optionsHtml = '<option>请选择商品一级类目</option>';
                }else {
                    optionsHtml = '<option>请选择商品二级类目</option>';
                }
                for(var i = 0;i<dataArr.length;i++){
                    var tempHtml = '<option data-id="'+ dataArr[i].id +'">' + dataArr[i].name + '</option>';
                    optionsHtml += tempHtml;
                }
                $("#" + element).empty().append(optionsHtml);
            }
        }
    });
}

/**
 * 打开查看特产商品详情
 */
goodsList.opengoodsListDetail = function (id, platformManage) {
    sessionStorage.setItem("platformManage",platformManage);
    window.location.href=Feng.ctxPath + '/platform/goodscenter/detail/'+id;
};
/**
 * 审核商品
 */
goodsList.audit = function (goodsId) {
    var index = layer.open({
        type: 1,
        title: '审核',
        area: ['400px', '260px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: '<br/><form id="changePwdForm" action="#" class="form-horizontal">\n' +
            '    <div class="ibox float-e-margins">\n' +
            '        <div style="padding: 15px 20px 20px 20px;"><div class="form-horizontal"></div><div class="row" style="margin-top: 15px;"><div class="col-sm-12"><label class="control-label" style="margin-bottom: 20px;">是否同意重新上架商品？</label>' +
            '</div></div>' +
            '<div class="row btn-group-m-t"> <div class="col-sm-10"> <button type="button" class="btn btn-primary" onclick="goodsList.agree(true,'+goodsId+');" style="width: 120px;height: 34px;color: #FFFFFF;">同意 </button>' +
            ' <button type="button" class="btn btn-danger-xb" onclick="goodsList.agree(false, '+goodsId+');" style="width: 120px;height: 34px;color: #FFFFFF;margin-right: 20px;">不同意 </button> </div> </div></div></div></form>'
    });
    this.layerIndex = index;
};
goodsList.agree = function (isAgree, goodsId) {
    console.log(isAgree);
    var ajax = new $ax(Feng.ctxPath + "/platform/goodscenter/goodsPlatformCheck", function (data) {
        if (data.code == 200) {
            Feng.success("操作成功!");
            window.location.reload();
            Address.search();
        } else {
            Feng.error(data.message + "!");
        }
    });
    ajax.set("goodsId", goodsId);
    ajax.set("isAgree", isAgree);
    ajax.start();
};
$(function () {
    // 加载一级类目
    initGoodsCategoryList(0,"typeOne");

    //选择一级类目后加载对应的二级类目
    $("#typeOne").change(function(){
        var index = $("#typeOne").get(0).selectedIndex;
        if (index == 0){
            $("#typeTwo").empty().append("<option data-id='' >请选择商品二级类目</option>");
        }else{
            var pid = $("#typeOne>option:selected").data("id");
            initGoodsCategoryList(pid,"typeTwo");
        }
    });

    $(".mybt").click(function () {
        $(".mybt").removeClass('active');
        $(this).addClass('active');
        var stateType=$(this).attr('data');
        $("#status").val(stateType);
        goodsList.search();
    });

    $("#typeOne").change(function () {
        goodsList.search();
    });

    $("#typeTwo").change(function () {
        goodsList.search();
    });

    var defaultColunms = goodsList.initColumn();
    var table = new BSTable(goodsList.id, "/platform/goodscenter/goodsList", defaultColunms);
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
});

