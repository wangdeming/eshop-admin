/**
 * 初始化店铺地址详情对话框
 */
var AddressInfoDlg = {
    addressInfoData : {},
    validateFields: {
        consigneeName: {
            validators: {
                notEmpty: {
                    message: '不能为空'
                },
                regexp: {//正则验证
                    regexp:  /^.{0,15}$/,
                    message: '不能超过15个字符'
                },
            }
        },
        consigneePhone: {
            validators: {
                notEmpty: {
                    message: '不能为空'
                },
                regexp: {//正则验证
                    regexp:  /^[1][3,4,5,6,7,8,9][0-9]{9}$/,
                    message: '请输入正确的手机号格式'
                },
            }
        },
        provinceId: {
            validators: {
                notEmpty: {
                    message: '不能为空'
                },
            }
        },
        cityId: {
            validators: {
                notEmpty: {
                    message: '不能为空'
                },
            }
        },
        districtId: {
            validators: {
                notEmpty: {
                    message: '不能为空'
                },
            }
        },
        address: {
            validators: {
                notEmpty: {
                    message: '不能为空'
                },
                regexp: {//正则验证
                    regexp:  /^.{5,200}$/,
                    message: '不能少于5个字符，最多200字'
                },
            }
        },
    }
};

/**
 * 清除数据
 */
AddressInfoDlg.clearData = function() {
    this.addressInfoData = {};
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
AddressInfoDlg.set = function(key, val) {
    this.addressInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
AddressInfoDlg.get = function(key) {
    return $("#" + key).val();
};

AddressInfoDlg.clickCheck = function() {
    if ($("#type").prop("checked")) {
        $(".checkShow").show()
    } else {
        $(".checkShow").hide()
    }
};
/**
 * 初始化
 */
$(function () {
    var totalShow = sessionStorage.getItem("totalRow");
    if (window.location.pathname == "/shop/address/toAdd/") {
        if (totalShow == 0) {
            document.getElementById("type").disabled=true;
            document.getElementById("isDefault").disabled=true;
        } else {
            document.getElementById("type").disabled=false;
            document.getElementById("isDefault").disabled=false;
            document.getElementById("type").checked=false;
            document.getElementById("isDefault").checked=false;
            $(".checkShow").hide()
        }
    } else {
        if ($("#isDefaultaa").val()==1) {
            document.getElementById("isDefault").disabled=true;
        } else {
            document.getElementById("isDefault").disabled=false;
        }
    }
});
/**
 * 关闭此对话框
 */
AddressInfoDlg.close = function() {
    parent.layer.close(window.parent.Address.layerIndex);
};

/**
 * 收集数据
 */
AddressInfoDlg.collectData = function() {
    this.set('id');
    this.set('consigneeName');
    this.set('consigneePhone');
    this.set('provinceId');
    this.set('cityId');
    this.set('districtId');
    this.set('address');
    this.addressInfoData['isDefault'] = $("#isDefault" ).is(':checked')?1:0;
    this.addressInfoData['type'] = $("#type" ).is(':checked')?1:0;
};

/**
 * 提交添加
 */
AddressInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    if (!AddressInfoDlg.validate()) {
        return;
    }
    //提交信息
    if (this.addressInfoData['type'] == 0) {
        Feng.error("请勾选地址类型!");
    } else {
        var ajax = new $ax(Feng.ctxPath + "/shop/address/add", function (data) {
            if (data.code == 200) {
                Feng.success("添加成功!");
                window.location.href = Feng.ctxPath + '/shop/address'
            } else {
                Feng.error(data.message + "!");
            }
        }, function (data) {
            Feng.error("添加失败!" + data.responseJSON.message + "!");
        });
        ajax.set(this.addressInfoData);
        ajax.start();
    }
};
AddressInfoDlg.changearea = function(obj,targetobjid) {
    var id=obj?$(obj).val():0;
    targetobjid=obj?targetobjid:'provinceId';
    $.ajax({
        url: Feng.ctxPath +"/platformshop/area/listAreasByPid",
        type: 'post',
        dataType: 'json',
        data: {pid:id},
        success: function (data) {
            var html = "";
            $("#"+targetobjid).html('<option value="">请选择</option>');
            for (var i = 0, l = data.data.length; i < l; i++) {
                html = '<option value="' + data.data[i].id + '">' + data.data[i].name + '</option>';
                $("#"+targetobjid).append(html);
            }
            if($("#"+targetobjid).attr('data')){
                $("#"+targetobjid).val($("#"+targetobjid).attr('data')).trigger('change');
            }
        }
    });
};
/**
 * 验证数据是否为空
 */
AddressInfoDlg.validate = function () {
    $('#addSubAccount').data("bootstrapValidator").resetForm();
    $('#addSubAccount').bootstrapValidator('validate');
    return $("#addSubAccount").data('bootstrapValidator').isValid();
};
/**
 * 提交修改
 */
AddressInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();
    //非空验证
    if (!AddressInfoDlg.validate()) {
        return;
    }
    //提交信息
    if (this.addressInfoData['type'] == 0) {
        Feng.error( "请勾选地址类型!");
    } else {
        var ajax = new $ax(Feng.ctxPath + "/shop/address/update", function(data){
            if(data.code==200){
                Feng.success("修改成功!");
                window.location.href=Feng.ctxPath+'/shop/address'
            }else{
                Feng.error( data.message + "!");
            }
        },function(data){
            Feng.error("修改失败!" + data.responseJSON.message + "!");
        });
        ajax.set(this.addressInfoData);
        ajax.start();
    }
};

$(function() {
    AddressInfoDlg.changearea();
    Feng.initValidator("addSubAccount", AddressInfoDlg.validateFields);

});
