/**
 * 店铺地址管理初始化
 */
var Address = {
    id: "AddressTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};
var total = '';

/**
 * 点击添加店铺地址
 */
Address.openAddAddress = function () {
    if (total == 15){
        Feng.error("店铺地址数量不能超过15个!" );
    } else {
        sessionStorage.setItem("totalRow",total);
        window.location.href = Feng.ctxPath + '/shop/address/toAdd/';
    }
};

/**
 * 打开查看店铺地址详情
 */
Address.openAddressDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 'post',
            title: '店铺地址详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/shop/address/address_update/' + Address.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除店铺地址
 */
Address.delete = function (addressId) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/shop/address/delete", function (data) {
                Feng.success("删除成功!");
                window.location.reload();
                Address.search();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("addressId", addressId);
            ajax.start();
        };
        Feng.confirm("是否确认删除本条地址?", operation);
};

/**
 *编辑店铺地址
 */
Address.edit = function(id) {
    window.location.href = Feng.ctxPath + '/shop/address/toEdit/' + id;
};

/**
 * 查询地址列表
 */
$(function () {
    Address.search();
});


/**
 * 查询地址列表
 */
Address.search = function () {
        var ajax = new $ax(Feng.ctxPath + "/shop/address/list", function (data) {
            var result = '';
            var elem = data.rows;
            total = data.total;
            if (elem.length == 0){
                result  += '                                <tr id="project"><td colspan="7" style="text-align: center">没有找到匹配的记录</td></tr>\n';
                $(".project").append(result);
            }else{
                $.each(elem,function (index,elem) {
                    var id = elem.id;
                    var consigneeName = elem.consigneeName;
                    var consigneePhone = elem.consigneePhone;
                    var address = elem.address;
                    var isDefault = elem.isDefault;
                    if (isDefault == 1) {
                        var a = '';
                        var type = '退货地址（默认）';
                    } else {
                        var a = '<span id="isDefault"><a onclick="Address.delete('+id+')">删除</a></span>';
                        var type = '<p>退货地址</p>'
                    }
                    result  +=
                        '                                <tr id="project">\n' +
                        '                                    <td id="consigneeName">'+consigneeName+'</td>\n' +
                        '                                    <td id="consigneePhone">'+consigneePhone+'</td>\n' +
                        '                                    <td id="address">'+address+'</td>\n' +
                        '                                    <td id="isDefault">'+type+'</td>\n' +
                        '                                    <td><span><a id="isDefault" style="margin-right: 30px;" onclick="Address.edit('+id+')">编辑</a></span>'+a+'</td>';
                    '                                </tr>\n'
                });
                $(".project").append(result);

            }
        }, function (data) {
            Feng.error(data.responseJSON.message);
        });
        ajax.set("order","desc");
        ajax.set("offset",0);
        ajax.set("limit",15);
        ajax.start();
    // }
};