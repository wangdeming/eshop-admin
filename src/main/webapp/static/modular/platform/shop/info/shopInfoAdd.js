/**
 * 初始化店铺列表详情对话框
 */
var ShopListInfoDlg = {
    map: null,
    district: [],
    township: [],
    shopListInfoData : {},
    validateFields: {
        name: {
            validators: {
                notEmpty: {
                    message: '店铺名称不能为空'
                },
                regexp: {//正则验证
                    regexp: /^.{1,50}$/,
                    message: '长度为50字符以内'
                },
            }
        },
        frontName: {
            validators: {
                regexp: {//正则验证
                    regexp: /^.{1,50}$/,
                    message: '长度为50字符以内'
                },
            }
        },

        officePhone: {
            validators: {
                regexp: {//正则验证
                    regexp: /^0\d{2,3}-\d{7,8}$/,
                    message: '号码格式错误'
                },
                callback: {
                    message: '请填写营业电话或营业手机',
                    callback: function (value, validator) {
                        if (value == '' && $("#officeTelphone").val() == '') {
                            return false;
                        } else {
                            return true;
                        }

                    }
                }
            }
        },
        officeTelphone: {
            validators: {

                regexp: {//正则验证
                    regexp: /^((1[3567894]\d{9}))$/,
                    message: '手机号码格式不对'
                },
                callback: {
                    message: '请填写营业电话或营业手机',
                    callback: function (value, validator) {
                        if (value == '' && $("#officePhone").val() == '') {
                            return false;
                        } else {
                            return true;
                        }

                    }
                }
            }
        },
        districtId:{
            validators: {
                notEmpty: {
                    message: '地址不能为空'
                }
            }
        },
        streetId:{
            validators: {
                notEmpty: {
                    message: '地址不能为空'
                }
            }
        },
        address: {
            validators: {

                regexp: {//正则验证
                    regexp: /^.{1,50}$/,
                    message: '长度为50字符以内'
                },
            }
        },

        intro: {
            validators: {

                regexp: {//正则验证
                    regexp: /^.{1,54}$/,
                    message: '长度为54字符以内'
                },
            }
        },
        legalPerson: {
            validators: {
                notEmpty: {
                    message: '法人不能为空'
                },
                regexp: {//正则验证
                    regexp: /(^[\u4e00-\u9fa5]{1}[\u4e00-\u9fa5\.·。]{0,8}[\u4e00-\u9fa5]{1}$)|(^[a-zA-Z]{1}[a-zA-Z\s*]{0,18}[a-zA-Z]{1}$)/,
                    message: '长度为2-10汉字字符或2-20英文字符 '
                }
            }
        },
        identityId: {
            validators: {
                notEmpty: {
                    message: '身份证号码不能为空'
                },
                regexp: {//正则验证
                    regexp: /^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/,
                    message: '身份证号格式错误'
                },
            }
        },

        phone: {
            validators: {
                notEmpty: {
                    message: '手机号码不能为空'
                },
                regexp: {//正则验证
                    regexp: /^1[3567894]\d{9}$/,
                    message: '手机号格式不对'
                },
            }
        },
        licenseId: {
            validators: {
                notEmpty: {
                    message: '营业执照编号不能为空'
                },
                regexp: {//正则验证
                    regexp: /^([0-9a-zA-Z]{18}$|\d{15}$)/,
                    message: '营业执照编号格式错误'
                },

            }
        },

    }
};
/**
 * 验证数据是否为空
 */
ShopListInfoDlg.validate = function () {
    $('#talentForm').data("bootstrapValidator").resetForm();
    $('#talentForm').bootstrapValidator('validate');
    return $("#talentForm").data('bootstrapValidator').isValid();
};

/**
 * 清除数据
 */
ShopListInfoDlg.clearData = function() {
    this.shopListInfoData = {};
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ShopListInfoDlg.set = function(key, val) {
    this.shopListInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ShopListInfoDlg.get = function(key) {
    return $("#" + key).val();
};

/**
 * 关闭此对话框
 */
ShopListInfoDlg.close = function() {
    // parent.layer.close(window.parent.ShopList.layerIndex);
    window.history.go(-1);
};

/**
 * 收集数据
 */
ShopListInfoDlg.collectData = function() {
    $("#id").val()?this.set('id'):'';
    this.set('name');
    this.set('frontName');
    this.set('type');
    this.set('officePhone');
    this.set('officeTelphone');
    this.set('address');
    this.set('provinceId');
    this.set('cityId');
    this.set('districtId');
    this.set('streetId');
    this.set('latitude');
    this.set('longitude');
    this.set('intro');
    this.set('cover');
    this.set('logo');
    this.set('legalPerson');
    this.set('sex',$('input:radio[name="sex"]:checked').val());
    this.set('phone');
    this.set('identityPositive');
    this.set('identityNegative');
    this.set('licenseId');
    this.set('licenseImage');
    this.set('identityId');
    // this.set('imgList');
};

/**
 * 添加上传图片
 */
var ShopIDListInfoDlg = {};
ShopIDListInfoDlg.deleteImg=function(el) {
    var $li = $(el).closest('li');
    $li.remove();
};
ShopIDListInfoDlg.deleteImgByAjax=function(el) {
    var $li = $(el).closest('li');
    var id = $li.data('id');

    (new $ax(Feng.ctxPath + "/shopList/deleteShopImg", function(data){
        Feng.success("操作成功!");
        $li.remove();
    }, function(data){
        Feng.error("操作失败!" + data.responseJSON.message + "!");
    })).set({imgId: id}).start();

    $li.remove();
};
ShopIDListInfoDlg.mouseover=function(el) {
    $(el).css("opacity", "0.6");
    $(el).find('img').css("opacity", "1");
};
ShopIDListInfoDlg.mouseout=function(el) {
    $(el).css("opacity", "0");
    $(el).find('img').css("opacity", "0");
};
ShopIDListInfoDlg.addImg = function () {
    if($("#uliImg li").length >= 5){
        Feng.error('图片不能大于5张');
        return;
    }
    var file = $("#imgupimg").find("input")[0].files[0];

    var fileData = new FormData();
    fileData.append('file', file);

    $.ajaxSetup({crossDomain: true, xhrFields: {withCredentials: true}});
    $.ajax({
        url: Feng.ctxPath +"/image/upload",
        type: 'POST',
        dataType: 'json',
        data: fileData,
        contentType: false,
        processData: false,
        async: false,
        success: function (data) {
            if(data.code != 200){
                Feng.error(data.message);
            }else{
                Feng.success("图片上传成功");
                console.log(data);

                var html = '\
                <li>\
                  <div class="upImgType col-sm-4" style="margin: 0 0 5px 0;padding: 0;">\
                    <input type="hidden" value="'+data.data.path+'"/>\
                    <img src="'+data.data.path+'" class="imgIcon" style="" alt=""/>\
                    <div class="div_imgtype col-sm-12" style="z-index:1;position: absolute;background:#000;padding:0px;opacity:0;" onmouseover="ShopIDListInfoDlg.mouseover(this)" onmouseout="ShopIDListInfoDlg.mouseout(this)">\
                      <img src="'+ Feng.ctxPath +'/static/img/close.png" style="position: absolute;z-index: 999;right: 0;margin-top: 10px;margin-right: 10px;" onclick="ShopIDListInfoDlg.deleteImg(this)"/>\
                    </div>\
                  </div>\
                </li>';
                $("#uliImg").append(html);
            }
        }
    });
};
ShopIDListInfoDlg.editAddImg = function () {
    if($("#uliImg li").length >= 5){
        Feng.error('图片不能大于5张');
        return;
    }
    var file = $("#imgupimg").find("input")[0].files[0];

    var fileData = new FormData();
    fileData.append('ImgFile', file);
    fileData.append('shopId', $('#shopId').val());

    $.ajaxSetup({crossDomain: true, xhrFields: {withCredentials: true}});
    $.ajax({
        url: Feng.ctxPath +"/shopList/addShopImg",
        type: 'POST',
        dataType: 'json',
        data: fileData,
        contentType: false,
        processData: false,
        async: false,
        success: function (data) {
            if(data.code != 200){
                Feng.error(data.message);
            }else{
                Feng.success("图片上传成功");
                console.log(data);

                var html = '\
                <li data-id="'+data.data.id+'">\
                  <div class="upImgType col-sm-4" style="margin: 0 0 5px 0;padding: 0;">\
                    <input type="hidden" value="'+data.data.path+'"/>\
                    <img src="'+data.data.path+'" class="imgIcon" style="" alt=""/>\
                    <div class="div_imgtype col-sm-12" style="position: absolute;background:#000;padding:0px;opacity:0;" onmouseover="ShopIDListInfoDlg.mouseover(this)" onmouseout="ShopIDListInfoDlg.mouseout(this)">\
                      <img src="'+ Feng.ctxPath +'/static/img/close.png" style="position: absolute;z-index: 999;right: 0;margin-top: 10px;margin-right: 10px;" onclick="ShopIDListInfoDlg.deleteImgByAjax(this)"/>\
                    </div>\
                  </div>\
                </li>';
                $("#uliImg").append(html);
            }
        }
    });
};
/**
 * 提交添加
 */
ShopListInfoDlg.addSubmit = function() {

    this.collectData();
    //非空验证
    if (!ShopListInfoDlg.validate()) {
        return;
    }
    var ajax = new $ax(Feng.ctxPath + "/platformshop/shopInfo/add", function (data) {
        if(data.code==200){
            Feng.success(data.message + "!");
            ShopListInfoDlg.ok();
        }else{
            Feng.error( data.message + "!");
        }
    }, function (data) {
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.shopListInfoData);
    ajax.start();
};

/**
 * 提交修改
 */
ShopListInfoDlg.editSubmit = function() {
    this.collectData();
    //非空验证
    if (!ShopListInfoDlg.validate()) {
        return;
    }
    var ajax = new $ax(Feng.ctxPath + "/platformshop/shopInfo/update", function (data) {
        if(data.code==200){
            Feng.success(data.message + "!");
            ShopListInfoDlg.ok();
        }else{
            Feng.error( data.message + "!");
        }

    }, function (data) {
        Feng.error(data.responseJSON.message + "!");
    });
    ajax.set(this.shopListInfoData);
    ajax.start();
};
ShopListInfoDlg.ok=function () {
    window.location.href=Feng.ctxPath + "/platformshop/shopInfo";
};
/**
 * 添加上传图片
 */
ShopListInfoDlg.getImgURL = function (id,width,height) {
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
 * 初始化店铺类型
 */
ShopListInfoDlg.initshopType = function () {
    var typesID=$("#typeId").val();
    // alert("typesID="+typesID);
    $.ajax({
        url: Feng.ctxPath +"/shopList/listTypes",
        type: 'get',
        dataType: 'json',
        async: false,
        data: {},
        success: function (data) {
            console.log(data);
            var html = "";
            var $shopType = $("#shopType");
            $shopType.empty();
            for (var i = 0, l = data.length; i < l; i++) {
                if (data[i].num==typesID) {
                    // alert(data[i].name);
                    html = '<option value="' + data[i].num + '" selected="selected">' + data[i].name + '</option>';
                }else{
                    html = '<option value=' + data[i].num + '>' + data[i].name + '</option>';
                    console.log(data[i].num);
                }
                $shopType.append(html);
            }

        }
    });
};

function hovershow2(id) {
    var imgurl = $("#img_" + id).attr('src');
    if (imgurl != "" && imgurl != null) {
        $("#img_div_" + id).hover(function () {
            $("#div_imgtype_" + id).css("background-color", "#000");
            $("#div_imgtype_" + id).css("opacity", "0.6");
            $("#div_imgtype_" + id).find("div:eq(1)").css("color", "#fff");
            $("#div_imgtype_" + id).find("div:eq(2)").css("color", "#fff");
            $("#div_imgtype_" + id).find("div:eq(3)").css("color", "#fff");

        }, function () {
            $("#div_imgtype_" + id).css("opacity", "0");
        });
    }
}

/**
 * 初始化区县
 */
ShopListInfoDlg.initshopArea = function () {

    $.ajax({
        url: Feng.ctxPath +"/platformshop/area/listAreasByPid",
        type: 'post',
        dataType: 'json',
        async: false,
        data: {pid:$("#cityId").val()},
        success: function (data) {
            ShopListInfoDlg.district = data.data;
            var html = "";

            $("#districtId").html('<option value="">请选择区县</option>');
            for (var i = 0, l = data.data.length; i < l; i++) {

                if(data.data[i].id==$("#districtId").attr('data')){

                    html = '<option value="' + data.data[i].id + '" selected="selected">' + data.data[i].name + '</option>';
                }else {
                    html = '<option value="' + data.data[i].id + '">' + data.data[i].name + '</option>';
                }
                $("#districtId").append(html);
            }
        }
    });
};

/**
 * 区县点击事件
 */
function clickarea(){
    areaID=$("#districtId option:selected").val();
    provinceName=$("#province").val();
    cityName=$("#city").val();
    areaName=$("#districtId option:selected").text();
    searchAddr1=provinceName+cityName+areaName;
    ShopListInfoDlg.initshopRoad();
    $("#address").val(searchAddr1);
}


var areaID ;
var areaName;
var provinceName;
var cityName;
var searchAddr1;

/**
 * 街道点击事件
 */
function clickroad(){
    var roadName=$("#streetId option:selected").text();
    provinceName=$("#province").val();
    cityName=$("#city").val();
    areaName=$("#districtId option:selected").text();
    var searchAddr2=provinceName+cityName+areaName+roadName;
    $("#address").val(searchAddr2);
}
/**
 * 初始化大道
 */
ShopListInfoDlg.initshopRoad = function () {

    $.ajax({
        url: Feng.ctxPath +"/platformshop/area/listAreasByPid",
        type: 'get',
        dataType: 'json',
        async: false,
        data: {pid:$("#districtId").val()},
        success: function (res) {
            if(res.code==200){
                var data=res.data;
                ShopListInfoDlg.township = data;
                var html = "";
                $("#streetId").html('<option value="">请选择街道</option>');

                for (var i = 0, l = data.length; i < l; i++) {

                    if(data[i].id==$("#streetId").attr('data')){
                        html = '<option value="'+data[i].id+'" selected="selected">'+data[i].name+'</option>';
                    }else {
                        html = '<option value='+data[i].id+'>'+data[i].name+'</option>';
                    }
                    $("#streetId").append(html);
                }
            }

        }
    });

};

////显示地图/////
ShopListInfoDlg.showMap=function () {
    var marker;
    try {
        var map = new AMap.Map('mapContainer', {
            resizeEnable: true,
            zoom: 11,
            center: [117.950585, 28.481846]
        });
        //为地图注册click事件获取鼠标点击出的经纬度坐标
        var clickEventListener = map.on('click', function (e) {
            document.getElementById("lnglat").value = e.lnglat.getLng() + ',' + e.lnglat.getLat();
            document.getElementById("latitude").value = e.lnglat.getLat();
            document.getElementById("longitude").value = e.lnglat.getLng();

            console.log("latitude1="+ e.lnglat.getLat());
        });
        //定位输入位置
        var auto = new AMap.Autocomplete({
            input: "address"
        });
        var placeSearch = new AMap.PlaceSearch({
            map: map
        });
        AMap.event.addListener(auto, "select", select);//注册监听，当选中某条记录时会触发
        function select(e) {
            console.log(e);
            // placeSearch.setCity(e.poi.adcode);
            // placeSearch.search(e.poi.name);
            $("#address").val(e.name);
            if (e.poi && e.poi.location) {
                var val=e.poi.location.N+','+e.poi.location.Q;
                map.setZoom(15);
                map.setCenter(e.poi.location);
                marker.setPosition(new AMap.LngLat(e.poi.location.P,e.poi.location.Q));
                document.getElementById("lnglat").value = e.poi.location.P + ',' + e.poi.location.Q;
                document.getElementById("latitude").value = e.poi.location.Q;
                document.getElementById("longitude").value = e.poi.location.P;
            }
        }

        //获取标点地址
        AMap.plugin('AMap.Geocoder', function () {
            var latitude=$("#latitude").val();
            var longitude=$("#longitude").val();
            console.log("latitude2="+latitude);
            var geocoder = new AMap.Geocoder({
                city: "010"//城市，默认：“全国”
            });
            marker = new AMap.Marker({
                map: map,
                bubble: true,
                position:new AMap.LngLat(longitude,latitude)

            });
            var input = document.getElementById('address');
            map.on('click', function (e) {
                marker.setPosition(e.lnglat);
                geocoder.getAddress(e.lnglat, function (status, result) {
                    if (status == 'complete') {
                        console.log(result);
                        // result.regeocode.addressComponent.province;
                        // result.regeocode.addressComponent.city;
                        // result.regeocode.addressComponent.district;
                        // result.regeocode.addressComponent.township;
                        // console.log(ShopListInfoDlg.district);
                        // console.log(ShopListInfoDlg.township);
                        // input.value = result.regeocode.formattedAddress.split(result.regeocode.addressComponent.township)[1];
                        input.value = result.regeocode.formattedAddress;
                    }
                })
            });
        });
    }catch(err){/*console.log(err)*/}
};

ShopListInfoDlg.getUrlParam = function(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]); return null; //返回参数值
};
$(function() {
    $('.i-checks').iCheck({
        checkboxClass: 'icheckbox_square-green',
        radioClass: 'iradio_square-green',
    });

    if(!ShopListInfoDlg.getUrlParam('isact')){
        $("input,select,textarea").attr('disabled','disabled');
        $(".img_div").attr("onMouseOver",'javascript:;');
        $("#ensure").hide()
    }

    Feng.initValidator("talentForm", ShopListInfoDlg.validateFields);

    ShopListInfoDlg.showMap();
    // ShopListInfoDlg.initshopType();
    ShopListInfoDlg.initshopArea();

    ShopListInfoDlg.initshopRoad();



    //初始性别
    if($("#sex").val() != undefined){
        // alert($("#sex").val())
        var sex = $("#sexval").val();
        $("input[name='sex'][value='"+sex+"']").attr("checked",true);  //根据Value值设置Radio为选中状态
    }
    //初始化图片
    var arr = [5,6,7,8,9];

    $.each(arr,function(i,item){
        if($("#img_"+item).attr('src') != undefined&&$("#img_"+item).attr('src')  != ''){
            $("#div_imgtype_"+item).css("opacity", "0");
        }
    });
    $("#officePhone").blur(function () {
        if($("#talentForm").data("bootstrapValidator").isValidField('officePhone')){
            $("#talentForm").data("bootstrapValidator").updateStatus("officeTelphone",  "VALIDATED",  null );
        }
    });
    $("#officeTelphone").blur(function () {
        if($("#talentForm").data("bootstrapValidator").isValidField('officeTelphone')){
            $("#talentForm").data("bootstrapValidator").updateStatus("officePhone",  "VALIDATED",  null );
        }
    });
});
