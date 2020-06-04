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
        {field: 'selectItem', checkbox: true,width:'5%'},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle',width:'5%'},
        {title: '房间价格', field: 'price', align: 'left', valign: 'middle',sortable: true,width:'25%',
            formatter: function (value, row, index) {
                return  '<img src="'+row.mainImg+'"  class="pull-left col-sm-4"><div class="pull-left col-sm-8">'+row.roomName+'</br>￥'+row.price+'</div>';
            }
        },
        {title: '浏览量', field: 'viewNum', align: 'left', valign: 'middle' ,sortable: true,width:'5%'},
        {title: '访客数', field: 'visitorNum', align: 'left', valign: 'middle',sortable: true,width:'5%'},
        {title: '创建时间', field: 'createdTime', align: 'left', valign: 'middle',sortable: true,width:'15%'},
        {title: '下架原因', field: 'platformManageReason', align: 'left', valign: 'middle',width:'15%'},
        {title: '操作', field: 'status', visible: true, align: 'left', valign: 'middle',width:'20%',
            formatter: function (value, row, index) {
                return '<a style="margin-right: 30px;" onclick="roomList.openRoomDetail('+row.id+')">编辑</a>' +
                    '<a style="margin-right: 30px;" onclick="roomList.openRoomManage('+row.id+')">房态管理</a>';
            }
        }

    ];
};

roomList.close = function() {

    layer.close(this.layerIndex);
};
/**
 * 检查是否选中
 */
roomList.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        roomList.seItem = selected[0];
        return true;
    }
};
/**
 * 点击添加房间管理
 */
roomList.openAddRoom = function () {
    window.location.href=Feng.ctxPath + '/shop/hotel/roomInsert';

};
/**
 * 打开查看房间编辑
 */
roomList.openRoomDetail = function (id) {
    window.location.href=Feng.ctxPath + '/shop/hotel/roomUpdate/'+id;
};
/**
 * 打开查看房态管理
 */
roomList.openRoomManage = function (id) {
    sessionStorage.setItem("roomId",id);
    window.location.href=Feng.ctxPath + '/shop/hotel/roomManage';
};
roomList.checkAllRoomManage = function () {
    window.location.href=Feng.ctxPath + '/shop/hotel/allRoomManage';
};
roomList.shelf = function (obj) {
    var selected = $('#' + roomList.id).bootstrapTable('getSelections');

    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        layer.confirm('确定'+$(obj).text()+'所选房间？', {
            btn: ['确定','取消'], //按钮
            shade: false //不显示遮罩
        }, function(index){
            var ids=[];
            $.each(selected,function (k,v) {
                console.info(v);
                ids[k]=v.id;
            });
            var ajax = new $ax(Feng.ctxPath + "/shop/hotel/"+$(obj).attr("shelf"), function (data) {
                if(data.code==200){
                    layer.close(index);
                    Feng.success(data.message);
                    roomList.table.refresh();
                }else{
                    Feng.error(data.message + "!");
                }

            }, function (data) {
                Feng.error(data.responseJSON.message + "!");
            });
            ajax.set("roomIds",ids.join(','));
            ajax.start();
        }, function(){

        });
    }
};

/**
 * 查询店铺信息管理列表
 */
roomList.search = function () {
    if($("#minPrice").val() != '' && !(/^\d+(?=\.{0,1}\d+$|$)/.test($("#minPrice").val()))){
        Feng.error("最低价请输入正数");
        return false;
    }
    if($("#maxPrice").val() != '' && !(/^\d+(?=\.{0,1}\d+$|$)/.test($("#maxPrice").val()))){
        Feng.error("最高价请输入正数");
        return false;
    }
    if(parseFloat($("#minPrice").val()) > parseFloat($("#maxPrice").val())){
        Feng.error("最低价应该不高于最高价");
        return false;
    }
    var queryData = {};
    queryData['minPrice'] = $("#minPrice").val();
    queryData['maxPrice'] = $("#maxPrice").val();
    queryData['roomName'] = $("#roomName").val();
    queryData['offset'] = 0;
    if ($("#status").val() == 2) {
        queryData['platformManage'] = 0;
        queryData['status'] = '';
    } else {
        queryData['status'] = $("#status").val();
        queryData['platformManage'] = 1;
    }
    roomList.table.refresh({query: queryData});
};

$(function () {
    $("#shelves").hide();
    var defaultColunms = roomList.initColumn();
    var table = new BSTable(roomList.id, "/shop/hotel/list", defaultColunms);
    table.setPaginationType("server");
    table.queryParams={'status':$("#status").val()};
    table.onLoadSuccess = function () {
        console.log($("#status").val());
        if ($("#status").val() == 2) {
            $("#RoomListTable").bootstrapTable('showColumn', 'platformManageReason');
        } else {
            $("#RoomListTable").bootstrapTable('hideColumn', 'platformManageReason');
        }
    };
    roomList.table = table.init( );

    $("#shelf").text('下架');
    $("#shelf").attr('shelf','offshelf');

    $(".mybt").click(function () {
        var staval=$(this).attr('data');
        if(staval==0){
            $("#shelves").hide();
            $("#shelf").show();
            $("#deletebtn").show();
            $("#shelf").text('上架');
            $("#shelf").attr('shelf','onshelf');
        }else if(staval==1) {
            $("#shelves").hide();
            $("#deletebtn").hide();
            $("#shelf").show();
            $("#shelf").text('下架');
            $("#shelf").attr('shelf','offshelf');
        }else if(staval==2) {
            $("#shelves").show();
            $("#deletebtn").show();
            $("#shelf").hide();
            $("#shelf").text('下架');
            $("#shelf").attr('shelf','offshelf');
        }
        $("#status").val(staval);
        $(".mybt").removeClass('active');
        $(this).addClass('active');
        roomList.search();
    })
});

/**
 * 系统下架房间重新上架
 */
roomList.shelves = function () {
    var selected = $('#' + roomList.id).bootstrapTable('getSelections');

    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        layer.confirm('是否申请上架所选房间？<div style="color: red;">注：申请需要平台审核，审核通过后，\n' +
            '\n' +
            '房间会恢复系统下架前的状态</div>', {
            btn: ['确定','取消'], //按钮
            shade: false //不显示遮罩
        }, function(index){
            var ids=[];
            $.each(selected,function (k,v) {
                console.info(v);
                ids[k]=v.id;
            });
            var ajax = new $ax(Feng.ctxPath + "/shop/hotel/applyRoomOnShelf/", function (data) {
                if(data.code==200){
                    layer.close(index);
                    Feng.success(data.message);
                    roomList .table.refresh();
                }else{
                    Feng.error(data.message + "!");
                }

            }, function (data) {
                Feng.error(data.responseJSON.message + "!");
            });
            ajax.set("roomIds",ids.join(','));
            ajax.start();
        }, function(){
        });
    }
};
