
$(function(){
    if($("#phoneNum").text() == ""){
        $("#changePhone").text("绑定手机");
    }
});
//修改密码
function openChangePwd() {
    var index = layer.open({
        type: 2,
        title: '更换密码',
        area: ['600px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: [Feng.ctxPath + '/shop/account/toupdatepassword', 'no']
    });
    this.layerIndex = index;
}
//修改手机
function openModifyPhone() {
    var index = layer.open({
        type: 2,
        title: '修改手机',
        area: ['600px', '350px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: [Feng.ctxPath + '/shop/account/toupdatephone', 'no']
    });
    this.layerIndex = index;
}