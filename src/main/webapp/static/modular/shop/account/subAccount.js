/**
 * 子账号管理管理初始化
 */
var SubAccount = {
    id: "SubAccountTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    accountId:null
};

/**
 * 初始化表格的列
 */
SubAccount.initColumn = function () {
    return [
        // {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: Feng.align, valign: 'middle'},
        {title: '账户名', field: 'account', visible: true, align: Feng.align, valign: 'middle'},
        {title: '姓名', field: 'name', visible: true, align: Feng.align, valign: 'middle'},
        {title: '账号角色', field: 'roleNames', visible: true, align: Feng.align, valign: 'middle'},
        {title: '手机', field: 'phone', visible: true, align: Feng.align, valign: 'middle'},
        {title: '创建时间', field: 'createdTime', visible: true, align: Feng.align, valign: 'middle'},
        {title: '状态', field: 'status', visible: true, align: Feng.align, valign: 'middle',
            formatter: function (value, row, index) {
                if(value == 1){
                    return "未激活";
                }else if(value == 2){
                    return "正常";
                }else if(value == 3){
                    return "冻结"
                }
            }
        },
        {title: '操作', field: 'status', visible: true, align: Feng.align, valign: 'middle',
            formatter: function (value, row, index) {
                if(value == 2){
                    return ''
                        + '<a style="margin-right: 30px;" data-roleid="'+row.roleid+'" data-account="'+row.account+'" onclick="SubAccount.showPrivilege('+row.id+')">查看权限</a>'
                        + '<a style="margin-right: 30px;" onclick="SubAccount.doFreeze('+row.id+')">冻结</a>'
                        + '<a style="margin-right: 30px;" onclick="SubAccount.resetpass('+row.id+')">重置密码</a>'
                    // + '<a>&nbsp</a>'
                }else if(value == 3){
                    return ''
                        + '<a style="margin-right: 30px;" data-roleid="'+row.roleid+'" data-account="'+row.account+'" onclick="SubAccount.showPrivilege('+row.id+')">查看权限</a>'
                        + '<a style="margin-right: 30px;" onclick="SubAccount.unFreeze('+row.id+')">解冻</a>'
                        + '<a style="margin-right: 30px;" onclick="SubAccount.delete('+row.id+')">注销</a>'
                        + '<a style="margin-right: 30px;" onclick="SubAccount.resetpass('+row.id+')">重置密码</a>'
                }else if(value == 1){
                    return ''
                        + '<a style="margin-right: 30px;" data-roleid="'+row.roleid+'" data-account="'+row.account+'" onclick="SubAccount.showPrivilege('+row.id+')">查看权限</a>'
                        // + '<a style="margin-right: 30px;">&nbsp</a>'
                        + '<a style="margin-right: 30px;" onclick="SubAccount.delete('+row.id+')">注销</a>'
                        + '<a style="margin-right: 30px;" onclick="SubAccount.resetpass('+row.id+')">重置密码</a>'
                }else{
                    return '<a style="margin-right: 30px;" onclick="SubAccount.resetpass('+row.id+')">重置密码</a>';
                }
            }
        },
    ];
};
SubAccount.resetpass = function (id) {

    var ajax = new $ax(Feng.ctxPath + "/shop/subaccount/resetpassword", function (data) {
        Feng.success(data.message+"!");
    }, function (data) {
        Feng.error("重置失败!" + data.message + "!");
    });
    ajax.set("id", id);
    ajax.start();

};
/**
 * 查看权限
 */
SubAccount.showPrivilege = function (id) {
    SubAccount.accountId = id;
    var index = layer.open({
        type: 2,
        title: '查看权限',
        area: ['600px', '500px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: [Feng.ctxPath + '/shop/subaccount/torole?id=' + id]
    });
    this.layerIndex = index;
};

/**
 * 解冻
 */
SubAccount.unFreeze = function (id) {
    var operation = function(){
        var ajax = new $ax(Feng.ctxPath + "/shop/subaccount/freezed", function () {
            Feng.success("解冻成功!");
            SubAccount.table.refresh();
        }, function (data) {
            Feng.error("解冻失败!" + data.message + "!");
        });
        ajax.set("id", id);
        ajax.set("status", 2);
        ajax.start();
    };
    Feng.confirm("是否解冻?", operation);
};

/**
 * 冻结
 */
SubAccount.doFreeze = function (id) {
    var operation = function(){
        var ajax = new $ax(Feng.ctxPath + "/shop/subaccount/freezed", function () {
            Feng.success("冻结成功!");
            SubAccount.table.refresh();
        }, function (data) {
            Feng.error("冻结失败!" + data.message + "!");
        });
        ajax.set("id", id);
        ajax.set("status", 3);
        ajax.start();
    };
    Feng.confirm("是否冻结?", operation);
};

/**
 * 注销
 */
SubAccount.delete = function (id) {
    var operation = function(){
        var ajax = new $ax(Feng.ctxPath + "/shop/subaccount/delete", function (data) {
            Feng.success("注销成功!");
            SubAccount.table.refresh();
        }, function (data) {
            Feng.error("注销失败!" + data.message + "!");
        });
        ajax.set("id", id);
        ajax.start();
    };
    Feng.confirm("是否注销?", operation);
};

/**
 * 点击添加子账号管理
 */
SubAccount.openAddSubAccount = function () {
    var index = layer.open({
        type: 2,
        title: '新增子账号',
        area: ['600px', '500px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: [Feng.ctxPath + '/shop/subaccount/toinsertsubaccount']
    });
    this.layerIndex = index;
};

/**
 * 检查是否选中
 */
SubAccount.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        SubAccount.seItem = selected[0];
        return true;
    }
};

$(function () {
    var defaultColunms = SubAccount.initColumn();
    var table = new BSTable(SubAccount.id, "/shop/subaccount/subaccountlist", defaultColunms);
    var queryData = {};
    queryData['limit'] = 20;
    table.setQueryParams(queryData);
    table.method = "GET";
    SubAccount.table = table.init();
});
