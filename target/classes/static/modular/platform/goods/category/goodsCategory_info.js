/**
 * 初始化特产类别详情对话框
 */
var GoodsCategoryInfoDlg = {
    goodsCategoryInfoData : {},
    validateFields: {
        name: {
            validators: {
                notEmpty: {
                    message: '类目名称不能为空'
                },
                regexp: {//正则验证
                    regexp: /^[\u4e00-\u9fa5a-zA-Z-z]{1,20}$/,
                    message: '类目名称只允许有汉字或字母的组合，20个字以内'
                },
            }
        },
        frontName: {
            validators: {
                notEmpty: {
                    message: '类目前台名称不能为空'
                },
                regexp: {//正则验证
                    regexp: /^[\u4e00-\u9fa5a-zA-Z-z]{2,6}$/,
                    message: '前台名称只允许有汉字或字母的组合，2-6字以内，名称不能重复，可以和类目名称一致'
                },
            }
        },
        logo: {
            validators: {
                // notEmpty: {
                //     message: '图标不能为空'
                // },
            },
        },
    }
};

/**
 * 清除数据
 */
GoodsCategoryInfoDlg.clearData = function() {
    this.goodsCategoryInfoData = {};
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
GoodsCategoryInfoDlg.set = function(key, val) {
    this.goodsCategoryInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
GoodsCategoryInfoDlg.get = function(key) {
    return $("#" + key).val();
};

/**
 * 关闭此对话框
 */
GoodsCategoryInfoDlg.close = function() {
    parent.layer.close(window.parent.GoodsCategory.layerIndex);
};

/**
 * 收集数据
 */
GoodsCategoryInfoDlg.collectData = function() {
    $("#id").val()?this.set('id'):'';
    this.set('pid');
    this.set('name');
    this.set('frontName');
    this.set('iconImg');
};
GoodsCategoryInfoDlg.getImgURL = function (id,width,height) {
    var file = $("#imgup_"+id).find("input")[0].files[0];
    if (file.name && !(new RegExp('\.(jpg|jpeg|png|svg)$', 'i')).test(file.name)) {
        Feng.error("选择的图片中包含不支持的格式!");
        return false;
    }
    if(((file.size).toFixed(2))>=(3*1024*1024)){
        Feng.error("图片大小不能超过3M!");
        return false;
    }
    var fileData = new FormData();
    fileData.append('file', file);
    var reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = function(e) {
        var datas = e.target.result;
        var image = new Image();
        image.onload = function() {
            if (width && height && (image.width!=width || image.height!=height)) {
                Feng.error("请上传宽"+width+"px、高"+height+"px的图片,当前图片宽"+image.width+",图片高"+image.height);
                return false;
            }
            $.ajaxSetup({crossDomain: true, xhrFields: {withCredentials: true}});
            $.ajax({
                url: Feng.ctxPath +"/file/image/upload",
                type: 'POST',
                dataType: 'json',
                data: fileData,
                contentType: false,
                processData: false,
                async: false,
                success: function (data) {
                    if(data.code==200){
                        Feng.success("图片上传成功");
                        $("#img_"+id).attr('src', data.data.path);
                        $("#div_imgtype_" + id).css("opacity", "0");
                        $("#img_"+id).siblings('input').val(data.data.path);
                    }else{
                        Feng.error(data.message);
                    }


                }
            });
        };
        image.src = datas;
    };
};
/**
 * 提交添加
 */
GoodsCategoryInfoDlg.addSubmit = function() {
    if (!GoodsCategoryInfoDlg.validate()) {
        return;
    }
    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/platform/goodscategory/add", function(data){
        if(data.code==200){
            Feng.success("添加成功!");
            window.parent.GoodsCategory.ok();
            GoodsCategoryInfoDlg.close();
        }else{
            Feng.error( data.message + "!");
        }

    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.goodsCategoryInfoData);
    ajax.start();
};
/**
 * 验证数据是否为空
 */
GoodsCategoryInfoDlg.validate = function () {
    $('#talentForm').data("bootstrapValidator").resetForm();
    $('#talentForm').bootstrapValidator('validate');
    return $("#talentForm").data('bootstrapValidator').isValid();
};
/**
 * 提交修改
 */
GoodsCategoryInfoDlg.editSubmit = function() {
    if (!GoodsCategoryInfoDlg.validate()) {
        return;
    }
    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/platform/goodscategory/update", function(data){
        if(data.code==200){
            Feng.success("修改成功!");
            window.parent.GoodsCategory.ok();
            GoodsCategoryInfoDlg.close();
        }else{
            Feng.error( data.message + "!");
        }
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.goodsCategoryInfoData);
    ajax.start();
};
/**
 * 获取一级类别集合
 */
GoodsCategoryInfoDlg.listParentNodes = function() {
    var ajax = new $ax(Feng.ctxPath + "/platform/goodscategory/listParentNodes", function(res){
        $.each(res.data,function (k,v) {
            var selhtml='';
            if(GoodsCategoryInfoDlg.getUrlParam('pid')==v.id){
                selhtml='selected="selected"';
            }
            $("#pid").append('<option value="'+v.id+'" '+selhtml+'>'+v.name+'</option>');
        })
    },function(data){
        Feng.error(  data.responseJSON.message + "!");
    });
    ajax.start();
};
GoodsCategoryInfoDlg.getUrlParam = function(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]); return null; //返回参数值
};
$(function() {
    Feng.initValidator("talentForm", GoodsCategoryInfoDlg.validateFields);
    if($("#img_9").attr('src') != undefined&&$("#img_9").attr('src')  != ''){
        $("#div_imgtype_9").css("opacity", "0");
    }
    $("#pid").parents('.form-group').hide();
    if(GoodsCategoryInfoDlg.getUrlParam('pid')>0){
        $("#img_img").show();
        GoodsCategoryInfoDlg.listParentNodes();
        $("#pid").parents('.form-group').show();
        $("#pidname").parents('.form-group').hide();
    }
});
