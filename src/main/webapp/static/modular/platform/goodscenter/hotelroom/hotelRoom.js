/**
 * 店铺信息管理管理初始化
 */
var roomList = {
    id: "RoomListTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
roomList.initColumn = function () {
    return [
        {title: 'id', field: 'id', visible: false, align: 'left', valign: 'middle'},
        {title: '房间价格', field: 'price', align: 'left', valign: 'middle',sortable: true,width:'25%',
            formatter: function (value, row, index) {
                return  '<img src="'+row.mainImg+'"  class="pull-left col-sm-4"><div class="pull-left col-sm-8">'+row.roomName+'</br>'+row.price+'</div>';
            }
        },
        {title: '房间ID', field: 'id', align: 'center', valign: 'middle',width:'5%'},
        {title: '店铺名称', field: 'shopName', align: 'center', valign: 'middle',width:'5%'},
        {title: '店铺ID', field: 'shopId', align: 'center', valign: 'middle',width:'5%'},
        {title: '浏览量', field: 'viewNum', align: 'left', valign: 'middle' ,sortable: true,width:'5%'},
        {title: '访客数', field: 'visitorNum', align: 'left', valign: 'middle',sortable: true,width:'5%'},
        {title: '创建时间', field: 'createdTime', align: 'left', valign: 'middle',sortable: true,width:'15%'},
        {title: '下架原因', field: 'platformManageReason', align: 'left', valign: 'middle',width:'15%'},
        {title: '操作', field: 'stateDes', visible: true, align: 'left', valign: 'middle',width:'20%',
            formatter: function (value, row, index) {
                if (row.platformManage == 0 && row.shopApply == 1) {
                    return ''
                        + '<a style="margin-right: 30px;" data-roleid="'+row.roleid+'" data-account="'+row.account+'" onclick="roomList.openRoomDetail('+row.id+', '+row.status+', '+row.platformManage+')">房间详情</a>'
                        + '<a style="margin-right: 30px;" onclick="roomList.openRoomManage(\''+row.shopName+'\', '+row.shopId+')">查看房态</a>'
                        + '<a onclick="roomList.audit('+row.id+')">审核</a>'
                } else {
                    return ''
                        + '<a style="margin-right: 30px;" data-roleid="'+row.roleid+'" data-account="'+row.account+'" onclick="roomList.openRoomDetail('+row.id+', '+row.status+', '+row.platformManage+')">房间详情</a>'
                        + '<a onclick="roomList.openRoomManage(\''+row.shopName+'\', '+row.shopId+')">查看房态</a>'
                }
            }
        }

    ];
};

roomList.close = function() {
    layer.close(this.layerIndex);
}
/**
 * 审核商品
 */
roomList.audit = function (roomId) {
    var index = layer.open({
        type: 1,
        title: '审核',
        area: ['400px', '260px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: '<br/><form id="changePwdForm" action="#" class="form-horizontal">\n' +
            '    <div class="ibox float-e-margins">\n' +
            '        <div style="padding: 15px 20px 20px 20px;"><div class="form-horizontal"></div><div class="row" style="margin-top: 15px;"><div class="col-sm-12"><label class="control-label" style="margin-bottom: 20px;">是否同意重新上架房间？</label>' +
            '</div></div>' +
            '<div class="row btn-group-m-t"> <div class="col-sm-10"> <button type="button" class="btn btn-primary" onclick="roomList.agree(true,'+roomId+');" style="width: 120px;height: 34px;color: #FFFFFF;">同意 </button>' +
            ' <button type="button" class="btn btn-danger-xb" onclick="roomList.agree(false, '+roomId+');" style="width: 120px;height: 34px;color: #FFFFFF;margin-right: 20px;">不同意 </button> </div> </div></div></div></form>'
    });
    this.layerIndex = index;
}
roomList.agree = function (isAgree, roomId) {
    var ajax = new $ax(Feng.ctxPath + "/platform/goodscenter/roomPlatformCheck", function (data) {
        if (data.code == 200) {
            Feng.success("操作成功!");
            window.location.reload()
        } else {
            id
            Feng.error(data.message + "!");
        }
    });
    ajax.set("roomId", roomId);
    ajax.set("isAgree", isAgree);
    ajax.start();
};
/**
 * 打开查看房间编辑
 */
roomList.openRoomDetail = function (id, status, platformManage) {
    sessionStorage.setItem("status",status);
    sessionStorage.setItem("platformManage",platformManage);
    window.location.href=Feng.ctxPath + '/platform/goodscenter/roomDetailPage/'+id;
};
/**
 * 打开查看房态管理
 */
roomList.openRoomManage = function (shopName, shopId) {
    sessionStorage.setItem("shopName",shopName);
    sessionStorage.setItem("shopId",shopId);
    window.location.href=Feng.ctxPath + '/platform/goodscenter/roomSetDetailPage';
};
/**
 * 查询酒店房间列表
 */
roomList.search = function () {
    var queryData = {};
    queryData['roomName'] = $("#roomName").val();
    queryData['roomId'] = $("#roomId").val();
    queryData['shopName'] = $("#shopName").val();
    queryData['shopId'] = $("#shopId").val();
    if ($("#status").val() == 4) {
        queryData['platformManage'] = 0;
        queryData['status'] = '';
    } else {
        queryData['status'] = $("#status").val();
        queryData['platformManage'] = 1;
    }
    roomList.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = roomList.initColumn();
    var table = new BSTable(roomList.id, "/platform/goodscenter/roomList", defaultColunms);
    table.setPaginationType("server");
    table.queryParams={'status':$("#status").val()};
    table.onLoadSuccess = function () {
        if ($("#status").val() == 4) {
            $("#RoomListTable").bootstrapTable('showColumn', 'platformManageReason');
        } else {
            $("#RoomListTable").bootstrapTable('hideColumn', 'platformManageReason');
        }
    };
    roomList.table = table.init( );

    $(".mybt").click(function () {
        var staval=$(this).attr('data');
        $("#status").val(staval);
        $(".mybt").removeClass('active')
        $(this).addClass('active');
        roomList.search();
    })
});