var priceArr = [];
var basePriceArr = [];
var stockArr = [];
//创建富文本编辑器——wangEditor
var E = window.wangEditor;
var editor = new E('#div1', '#div2');
// 配置服务器端地址
editor.customConfig.uploadImgServer = "/eshop/file/image/upload";
editor.customConfig.showLinkImg = false;
editor.customConfig.uploadFileName = 'file';
editor.customConfig.uploadImgTimeout = 50000;
editor.customConfig.uploadImgHooks = {
    customInsert: function (insertImg, result, editor) {
        var url = result.data.path;
        insertImg(url);
    }
};
editor.customConfig.onchange = function (html) {
    $("#previewDiv").empty().append(html);
};
editor.create();
//input验证
var validateFields = {
    productName: {
        validators: {
            notEmpty: {
                message: '商品名称不能为空'
            },
            regexp: {//正则验证
                regexp: /^.{1,50}$/,
                message: '长度为1-50'
            },
        }
    },
    productPrice: {
        validators: {
            notEmpty: {
                message: '商品价格不能为空'
            },
            number: {
                message: '必须输入合法的数字'
            }
        }
    },
    productStock: {
        validators: {
            notEmpty: {
                message: '库存不能为空'
            },
            number: {
                message: '必须输入合法的数字'
            },
            regexp: {
                regexp:/^\d+$/,
                message: '请输入正整数'
            }
        }
    },
    kdyfInput: {
        validators: {
            notEmpty: {
                message: '快递运费不能为空'
            },
            number: {
                message: '必须输入合法的数字'
            }
        }
    },
    setupInput: {
        validators: {
            number: {
                message: '必须输入合法的数字'
            }
        }
    }
};
//页面加载后执行
$(function() {
    //加载一级类目
    initGoodsCategoryList(0,"typeOne");
    //图片移动排序
    try{
        $("#uliImg").dragsort({
            dragSelector: "li",
            dragBetween: true ,
            dragEnd:function(){

            }
        });
    }catch(e){

    }
    //选择一级类目后加载对应的二级类目
    $("#typeOne").change(function(){
        var index = $("#typeOne").get(0).selectedIndex;
        if (index == 0){
            $("#typeTwo").empty().append("<option>请选择商品二级类目</option>");
        }else {
            var pid = $("#typeOne>option:selected").data("id");
            initGoodsCategoryList(pid,"typeTwo");
        }
    });
    //如果为编辑页面执行下面语句
    if(document.getElementById("goodId")){
        var categoryInfo = JSON.parse($("#categoryInfo").text());
        $("#typeOne").find("option[data-id = '"+categoryInfo[0]+"']").prop("selected","selected");
        initGoodsCategoryList(categoryInfo[0],"typeTwo");
        $("#typeTwo").find("option[data-id = '"+categoryInfo[1]+"']").prop("selected","selected");
        var expressFee = $("#goodId").attr("data-expressFee");
        if (expressFee == "0"){
            $("input[name='kdyf']:eq(0)").prop("checked",true);
        }else {
            $("input[name='kdyf']:eq(1)").prop("checked",true);
            $("#kdyfInput").val(expressFee);
        }
        var introContent = $("#introContent").text();
        $("#previewDiv").empty().append(introContent);
        editor.txt.html(introContent);
        var specsList = JSON.parse($("#specsList").text());
        var goodsSkuList = JSON.parse($("#goodsSkuList").text());
        var checkboxChecked = 0;
        if (goodsSkuList.length != 0) {
            $("#productPrice").attr("readonly","readonly");
            $("#productBasePrice").attr("readonly","readonly");
            $("#productStock").attr("readonly","readonly");
        }
        for (var i = 0;i<goodsSkuList.length;i++){
            if (goodsSkuList[i].img != undefined){
                checkboxChecked = 1;
            }
            if (goodsSkuList[i].price == null){
                priceArr.push("");
            }else {
                priceArr.push(goodsSkuList[i].price);
            }
            if (goodsSkuList[i].referPrice == null){
                basePriceArr.push("");
            }else {
                basePriceArr.push(goodsSkuList[i].referPrice);
            }
            if (goodsSkuList[i].stock == null){
                stockArr.push("");
            }else {
                stockArr.push(goodsSkuList[i].stock);
            }
        }
        var categoryListHtml = '';
        var keys = Object.keys(specsList);
        for(var i = 0;i<keys.length;i++){
            var showOrHide = "none";
            if (i == 0){
                showOrHide = "block";
            }
            var checkedOrNo = false;
            if (checkboxChecked == 1){
                checkedOrNo = true;
            }
            var tempHtml = '<div class="categoryDiv" onmouseover="showDeleteCategoryBtn(this)" onmouseout="hideDeleteCategoryBtn(this)">\
                               <img class="deleteCategoryBtn" src="'+ Feng.ctxPath +'/static/img/close.svg" onclick="deleteCategory(this)" style="display: none;">\
                               <div class="categoryHead">\
                                    <span class="categoryTitle">规格名称：</span>\
                                    <input class="categoryInput" name="lv1" type="text" placeholder="规格名称" value="'+ keys[i] +'"/>\
                                    <div class="categorySetImg" style="display:'+ showOrHide +';">\
                                        <input type="checkbox" checked="'+ checkedOrNo +'" onclick="checkBoxBtnClick(this)"/>\
                                        <span>设置规格图片</span>\
                                    </div>\
                               </div>\
                               <div class="categoryContent">\
                                    <span class="categoryTitle">规格值：</span>\
                                    <div class="categoryValueDiv">';
            var specsCategoryArr = specsList[keys[i]];
            for (var j = 0;j<specsCategoryArr.length;j++){
                tempHtml += '<div class="inputAndImage" onmouseover="showDeleteInputBtn(this)" onmouseout="hideDeleteInputBtn(this)">\
                                <img class="deleteInputBtn" src="'+ Feng.ctxPath +'/static/img/close.svg" onclick="deleteCategoryInput(this)" style="display: none;">\
                                <input class="categoryValueItemInput" name="lv2" type="text" placeholder="规格值" value="'+ specsCategoryArr[j] +'">\
                            </div>';
            }
            tempHtml += '<button type="button" class="btn btn-primary" onclick="addCategoryValueAndImg(this)" style="width: 100px;height: 30px;color: #FFFFFF;margin-top:5px;">添加规格值</button>\
                       </div>\
                       <div class="categoryImg" style="display: '+ showOrHide +';">\
                            <div class="categoryImgDiv">';
            for (var j = 0;j<specsCategoryArr.length;j++){
                var hasImage = 0;
                if(goodsSkuList[i].img != undefined){
                    hasImage = 1;
                }
                var showImage = "block";
                if (hasImage == 1){
                    showImage = 'none';
                }
                var showLoadImg = "none";
                if (hasImage == 1){
                    showLoadImg = "block";
                }
                tempHtml += '<div class="categoryImgDivItem">\
                                <div class="insertImageItem" style="display: '+ showImage +';">\
                                    <div class="imageDiv">\
                                        <img class="addImage" src="'+ Feng.ctxPath +'/static/img/upload.png"/>\
                                        <input class="opt addImageInput" type="file" name="" onchange="addCategoryImg(this)" style="cursor: pointer;"/>\
                                    </div>\
                                    <div class="addImageInfo1">点击上传图片</div>\
                                    <div class="addImageInfo2">文件不大于3M</div>\
                                    <div class="addImageInfo3">尺寸:750x750像素</div>\
                                </div>\
                                <div class="insertImageDiv" style="display: '+ showLoadImg +';">\
                                    <div class="addImagesItemDiv">\
                                        <input type="hidden" value="'+goodsSkuList[i].img+'"/>\
                                        <img src="'+goodsSkuList[j].img+'" class="addImgIcon" style="" alt=""/>\
                                        <div class="deleteImageDiv" onmouseover="divMouseover(this)" onmouseout="divMouseout(this)">\
                                            <img src="'+ Feng.ctxPath +'/static/img/close.png" style="position: absolute;z-index: 999;right: 0;margin-top: 10px;margin-right: 10px;" onclick="deleteInsertImg(this)"/>\
                                        </div>\
                                    </div>\
                                </div>\
                            </div>';
            }
            tempHtml += '</div>\
                        </div>\
                   </div>\
                </div>';
            categoryListHtml += tempHtml;
        }
        $("#category_list").append(categoryListHtml);
        var imageList = JSON.parse($("#imageList").text());
        for (var i = 0;i<imageList.length;i++){
            var html = '\
                <li class="addImagesItemLi">\
                  <div class="addImagesItemDiv">\
                    <input id="'+imageList[i].id+'" type="hidden" value="'+imageList[i].img+'"/>\
                    <img src="'+imageList[i].img+'" class="addImgIcon" style="" alt=""/>\
                    <div class="deleteImageDiv" onmouseover="divMouseover(this)" onmouseout="divMouseout(this)">\
                      <img src="'+ Feng.ctxPath +'/static/img/close.png" style="position: absolute;z-index: 999;right: 0;margin-top: 10px;margin-right: 10px;" onclick="deleteImg(this)"/>\
                    </div>\
                  </div>\
                </li>';
            $("#uliImg").append(html);
        }
        createTable();
        $("input[name='lv1']").blur(function(){
            createTable();
        });
        $("input[name='lv2']").blur(function(){
            createTable();
        });
    }
    Feng.initValidator("insertGoodsForm", validateFields);
});
/**
 * 验证数据是否为空
 */
var validate = function () {
    $('#insertGoodsForm').data("bootstrapValidator").resetForm();
    $('#insertGoodsForm').bootstrapValidator('validate');
    return $("#insertGoodsForm").data('bootstrapValidator').isValid();
};
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
function addSubmit(el) {
    if (!validate) {
        return;
    }
    if ($("#typeTwo").val() == "请选择商品二级类目"){
        Feng.error('请选择商品二级类目');
        return;
    }
    if ($("#productName").val() == ''){
        Feng.error('商品名称不能为空');
        return;
    }
    if (!(/^.{1,50}$/.test($("#productName").val()))){
        Feng.error('商品名称长度不能超过50字符');
        return;
    }

    for (var i = 0;i<priceArr.length;i++){
        if($("#price"+i).val()<0 || $("#price"+i).val()>100000){
            Feng.error('价格输入的值不能小于0或者大于100000');
            return false;
        }
        if($("#basePrice"+i).val()<0 || $("#basePrice"+i).val()>100000){
            Feng.error('划线价输入的值不能小于0或者大于100000');
            return false;
        }
        if($("#stock"+i).val()<0 || $("#basePrice"+i).val()>100000){
            Feng.error('库存输入的值不能小于0或者大于100000');
            return false;
        }
    }
    var categoryId = $("#typeTwo>option:selected").attr("data-id");
    var name = $("#productName").val();
    var price = $("#productPrice").val();
    var referPrice = $("#productBasePrice").val();
    var stock = $("#productStock").val();
    if(price<0 || price>100000){
        Feng.error('价格输入的值不能小于0或者大于100000');
        return false;
    }
    if (referPrice != ''){
        if(referPrice<0 || referPrice>100000){
            Feng.error('划线价输入的值不能小于0或者大于100000');
            return false;
        }
    }
    if(stock<0 || stock>100000){
        Feng.error('库存输入的值不能小于0或者大于100000');
        return false;
    }
    if (!(/^[+]{0,1}(\d+)$/.test(stock))) {
        Feng.error('库存请输入正整数');
        return false;
    }
    var expressFee = 0;
    if ($('input:radio[name="kdyf"]:checked').val() != 0){
        if ($("#kdyfInput").val() == ''){
            Feng.error('快递运费不能为空');
            return;
        }
        if ($("#kdyfInput").val()<0 || $("#kdyfInput").val()>100){
            Feng.error('快递运费不能小于0或大于100');
            return;
        }
        if (!(/^\d+(?=\.{0,1}\d+$|$)/.test($("#kdyfInput").val()))) {
            Feng.error('运费请输入正数');
            return false;
        }
        expressFee = $("#kdyfInput").val();
    }
    var specsListObj = {};
    var lv1ValueArr = [];
    $('input[name="lv1"]').each(function(){
        lv1ValueArr.push($(this).val());
    });
    var lv1ValueArrSoft = lv1ValueArr.sort();

    for(var i=0;i<lv1ValueArr.length;i++){

        if (lv1ValueArrSoft[i] == lv1ValueArr[i+1]){
            Feng.error('规格项目不能相同');
            return false;
        }

    }
    var lv2ValueArr = [];
    $('input[name="lv2"]').each(function(){
        lv2ValueArr.push($(this).val());
    });

    var lv2ValueArrSoft = lv2ValueArr.sort();
    for(var i=0;i<lv2ValueArr.length;i++){

        if (lv2ValueArrSoft[i] == lv2ValueArr[i+1]){
            Feng.error('规格值不能重复');
            return false;
        }

    }

    var lv1Arr = $('input[name="lv1"]');
    for (var i = 0; i < lv1Arr.length; i++) {
        if($(lv1Arr[i]).val() == ""){
            Feng.error('规格名称不能为空！');
            return false;
        }
        var lv2Arr = $(lv1Arr[i]).parents('.categoryDiv').find('input[name="lv2"]');
        var tempArr = [];
        for (var j = 0;j<lv2Arr.length;j++) {
            if($(lv2Arr[j]).val() == ""){
                Feng.error('规格值不能为空！');
                return false;
            }
            tempArr.push($(lv2Arr[j]).val());
        }
        specsListObj[$(lv1Arr[i]).val()] = tempArr;
    }
    var specsList = JSON.stringify(specsListObj);
    var imageListArr = [];
    var imageArr = $("li.addImagesItemLi");
    if (imageArr.length == 0) {
        Feng.error('商品图片至少上传一张');
        return;
    }
    for (var i = 0;i<imageArr.length;i++) {
        var tempObj = {};
        tempObj.img = $(imageArr[i]).find('input[type="hidden"]').val();
        tempObj.sequence = i+1;
        imageListArr.push(tempObj);
    }
    var imageListJsonString = JSON.stringify(imageListArr);
    var goodsSkuListArr = [];
    var imageList = [];
    if ($("div.categoryDiv:eq(0)").find("input[type='checkbox']").is(':checked')){
        var categoryImgDivItems = $("div.categoryDiv:eq(0)>div.categoryImg>div.categoryImgDiv>div.categoryImgDivItem");
        for (var i = 0;i<categoryImgDivItems.length;i++){
            if($(categoryImgDivItems[i]).has($("input[type='hidden']"))) {
                var inputHidden = $(categoryImgDivItems[i]).find("input[type='hidden']").val();
                imageList.push(inputHidden);
            }else {
                imageList.push('');
            }
        }
    }
    var lv1Arr = $('input[name="lv1"]');
    var priceInputArr = $("input[data-name='price']");
    for(var i = 0;i<priceInputArr.length;i++){
        var goodsSkuObj = {};
        var specsStr = $(priceInputArr[i]).attr("data-allName");
        var specsArr = specsStr.split('_');
        var specsObj = {};
        for (var j = 0;j<lv1Arr.length;j++) {
            specsObj[$(lv1Arr[j]).val()] = specsArr[j];
        }
        goodsSkuObj.specs = specsObj;
        goodsSkuObj.price = priceArr[i];
        if (basePriceArr[i] != ""){
            goodsSkuObj.referPrice = basePriceArr[i];
        }
        goodsSkuObj.stock = stockArr[i];
        goodsSkuObj.img = imageList[i % imageList.length];
        goodsSkuListArr.push(goodsSkuObj);
    }
    var goodsSkuListJsonString = JSON.stringify(goodsSkuListArr);
    var content = editor.txt.html();
    $.ajaxSetup({crossDomain: true, xhrFields: {withCredentials: true}});
    $.ajax({
        url: Feng.ctxPath +"/shop/goods/insert",
        type: 'POST',
        dataType: 'json',
        data: {categoryId:categoryId,name:name,price:price,referPrice:referPrice,stock:stock,expressFee:expressFee,specsList:specsList,imageListJsonString:imageListJsonString,goodsSkuListJsonString:goodsSkuListJsonString,content:content},
        async: false,
        success: function (data) {
            if(data.code != 200){
                Feng.error(data.message);
            }else{
                Feng.success(data.message);
                setTimeout(function () {
                    window.history.go(-1);
                },1000);
            }
        }
    });
}
function closePage() {
    window.history.go(-1);
}
function addImg() {
    if($("#uliImg li").length >= 10){
        Feng.error('图片不能大于10张');
        return;
    }
    var file = $("#imgupimg").find("input")[0].files[0];

    var fileData = new FormData();
    fileData.append('file', file);

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
            if(data.code != 200){
                Feng.error(data.message);
            }else{
                Feng.success("图片上传成功");
                var html = '\
                <li class="addImagesItemLi">\
                  <div class="addImagesItemDiv">\
                    <input type="hidden" value="'+data.data.path+'"/>\
                    <img src="'+data.data.path+'" class="addImgIcon" style="" alt=""/>\
                    <div class="deleteImageDiv" onmouseover="divMouseover(this)" onmouseout="divMouseout(this)">\
                      <img src="'+ Feng.ctxPath +'/static/img/close.png" style="position: absolute;z-index: 999;right: 0;margin-top: 10px;margin-right: 10px;" onclick="deleteImg(this)"/>\
                    </div>\
                  </div>\
                </li>';
                $("#uliImg").append(html);
            }
        }
    });
}
function deleteImg(el) {
    var $li = $(el).closest('li');
    $li.remove();
}
function divMouseover(el) {
    $(el).css("opacity", "0.6");
    $(el).find('img').css("opacity", "1");
}
function divMouseout(el) {
    $(el).css("opacity", "0");
    $(el).find('img').css("opacity", "0");
}
var categoryDiv = '<div class="categoryDiv" onmouseover="showDeleteCategoryBtn(this)" onmouseout="hideDeleteCategoryBtn(this)">\
                       <img class="deleteCategoryBtn" src="'+ Feng.ctxPath +'/static/img/close.svg" onclick="deleteCategory(this)" style="display: none;">\
                       <div class="categoryHead">\
                            <span class="categoryTitle">规格名称：</span>\
                            <input class="categoryInput" name="lv1" type="text" placeholder="规格名称"/>\
                            <div class="categorySetImg" style="display: none;">\
                                <input type="checkbox" onclick="checkBoxBtnClick(this)"/>\
                                <span>设置规格图片</span>\
                            </div>\
                       </div>\
                       <div class="categoryContent">\
                            <span class="categoryTitle">规格值：</span>\
                            <div class="categoryValueDiv">\
                                <div class="inputAndImage" onmouseover="showDeleteInputBtn(this)" onmouseout="hideDeleteInputBtn(this)">\
                                    <img class="deleteInputBtn" src="'+ Feng.ctxPath +'/static/img/close.svg" onclick="deleteCategoryInput(this)" style="display: none;">\
                                    <input class="categoryValueItemInput" name="lv2" type="text" placeholder="规格值">\
                                </div>\
                            </div>\
                            <button type="button" class="btn btn-primary" onclick="addCategoryValueAndImg(this)" style="width: 100px;height: 30px;color: #FFFFFF;margin-top:5px;">添加规格值</button>\
                       </div>\
                       <div class="categoryImg" style="display: none;">\
                            <div class="categoryImgDiv">\
                                <div class="categoryImgDivItem">\
                                    <div class="insertImageItem" style="display: block;">\
                                        <div class="imageDiv">\
                                            <img class="addImage" src="'+ Feng.ctxPath +'/static/img/upload.png"/>\
                                            <input class="opt addImageInput" type="file" name="" onchange="addCategoryImg(this)"style="cursor: pointer;"/>\
                                        </div>\
                                        <div class="addImageInfo1">点击上传图片</div>\
                                        <div class="addImageInfo2">文件不大于3M</div>\
                                        <div class="addImageInfo3">尺寸:750x750像素</div>\
                                    </div>\
                                    <div class="insertImageDiv" style="display: none;">\
                                    </div>\
                                </div>\
                            </div>\
                       </div>\
                    </div>';

function addCategory() {
    $("#productPrice").attr("readonly","readonly");
    $("#productBasePrice").attr("readonly","readonly");
    $("#productStock").attr("readonly","readonly");
    if($("#category_list>div.categoryDiv").length >= 3){
        Feng.error('规格项目数不能超过3');
        return;
    }
    if ($("#category_list>div.categoryDiv").length == 0){
        $("#category_list").append(categoryDiv);
        $("#category_list>div.categoryDiv:eq(0)").find("div.categorySetImg").css("display","block");
    }else {
        $("#category_list").append(categoryDiv);
    }
    createTable();
    $("input[name='lv1']").blur(function(){
        createTable();
    });
    $("input[name='lv2']").blur(function(){
        createTable();
    });
}
function addCategoryImg(el) {
    var file = $(el)[0].files[0];

    var fileData = new FormData();
    fileData.append('file', file);

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
            if(data.code != 200){
                Feng.error(data.message);
            }else{
                Feng.success("图片上传成功");
                var html = '\
                  <div class="addImagesItemDiv">\
                    <input type="hidden" value="'+data.data.path+'"/>\
                    <img src="'+data.data.path+'" class="addImgIcon" style="" alt=""/>\
                    <div class="deleteImageDiv" onmouseover="divMouseover(this)" onmouseout="divMouseout(this)">\
                      <img src="'+ Feng.ctxPath +'/static/img/close.png" style="position: absolute;z-index: 999;right: 0;margin-top: 10px;margin-right: 10px;" onclick="deleteInsertImg(this)"/>\
                    </div>\
                  </div>';
                $(el).parents("div.categoryImgDivItem").find("div.insertImageDiv").empty().append(html).show();
                $(el).parents("div.insertImageItem").hide();
            }
        }
    });
}
function deleteInsertImg(el) {
    $(el).parents("div.categoryImgDivItem").find("div.insertImageItem").show();
    $(el).parents("div.insertImageDiv").empty().hide();
}
function checkBoxBtnClick(el) {
    if($(el).prop('checked')){
        $(el).parents("div.categoryDiv").find("div.categoryImg").css("display","block");
    }else {
        $(el).parents("div.categoryDiv").find("div.categoryImg").css("display","none");
    }
}
function showDeleteCategoryBtn(el) {
    $(el).find("img.deleteCategoryBtn").css("display","block");
}
function hideDeleteCategoryBtn(el) {
    $(el).find("img.deleteCategoryBtn").css("display","none");
}
function showDeleteInputBtn(el) {
    $(el).find("img.deleteInputBtn").css("display","block");
}
function hideDeleteInputBtn(el) {
    $(el).find("img.deleteInputBtn").css("display","none");
}
var inputAndImageHtml = '<div class="inputAndImage" onmouseover="showDeleteInputBtn(this)" onmouseout="hideDeleteInputBtn(this)">' +
    '<img class="deleteInputBtn" src="' + Feng.ctxPath + '/static/img/close.svg" onclick="deleteCategoryInput(this)" style="display: none;">' +
    '<input class="categoryValueItemInput" name="lv2" type="text" placeholder="规格值"></div>';

var imageHtml = '<div class="categoryImgDivItem">\
                    <div class="insertImageItem" style="display: block;">\
                        <div class="imageDiv">\
                            <img class="addImage" src="' + Feng.ctxPath + '/static/img/upload.png"/>\
                            <input class="opt addImageInput" type="file" name="" onchange="addCategoryImg(this)"/>\
                        </div>\
                        <div class="addImageInfo1">点击上传图片</div>\
                        <div class="addImageInfo2">文件不大于3M</div>\
                        <div class="addImageInfo3">尺寸:750x750像素</div>\
                    </div>\
                    <div class="insertImageDiv" style="display: none;">\
                    </div>\
                </div>';

function addCategoryValueAndImg(el) {
    if($(el).prev("div.categoryValueDiv").find("div.inputAndImage").length >= 5){
        Feng.error('规格值数不能超过5');
        return;
    }
    var numsArr = getNumsArr();
    var index = $("div.categoryDiv").index($(el).parents("div.categoryDiv"));
    var everyNum = 1;
    var addTime = 1;
    var locationNum = 1;
    for (var i = index + 1;i <numsArr.length;i++){
        everyNum *= numsArr[i];
    }
    for (var i = 0;i<numsArr.length;i++){
        if (i < index){
            addTime *= numsArr[i];
        }
    }
    for (var i = 0;i<numsArr.length;i++){
        if (i >= index){
            locationNum *= numsArr[i];
        }
    }
    for (var i = 0;i<addTime;i++){
        for (var j = 0;j<everyNum;j++){
            priceArr.splice(locationNum*(i+1)+i*everyNum+j,0,'0');
            basePriceArr.splice(locationNum*(i+1)+i*everyNum+j,0,'0');
            stockArr.splice(locationNum*(i+1)+i*everyNum+j,0,'0');
        }
    }
    $(el).parents("div.categoryContent").find("div.categoryValueDiv").append(inputAndImageHtml);
    $(el).parents("div.categoryDiv").find("div.categoryImgDiv").append(imageHtml);
    createTable();
    $("input[name='lv1']").blur(function(){
        createTable();
    });
    $("input[name='lv2']").blur(function(){
        createTable();
    });
}
function deleteCategory(el) {
    if ($("#category_list>div.categoryDiv").length == 1){
        $("#productPrice").removeAttr("readonly").val('');
        $("#productBasePrice").removeAttr("readonly").val('');
        $("#productStock").removeAttr("readonly").val('');
    }
    var index = $("#category_list>div.categoryDiv").index($(el).parent());
    priceArr = [];
    basePriceArr = [];
    stockArr = [];
    if (index == 0) {
        $(el).parent().remove();
        $("#category_list>div.categoryDiv:eq(0)").find("div.categorySetImg").css("display","block");
    }else {
        $(el).parent().remove();
    }
    createTable();
}
function deleteCategoryInput(el) {
    var numsArr = getNumsArr();
    var index = $("div.categoryDiv").index($(el).parents("div.categoryDiv"));
    var indexItem = $(el).parents("div.categoryValueDiv").find("div.inputAndImage").index($(el).parent());
    var everyNum = 1;
    var addTime = 1;
    var locationNum = 1;
    for (var i = index + 1;i <numsArr.length;i++){
        everyNum *= numsArr[i];
    }
    for (var i = 0;i<numsArr.length;i++){
        if (i < index){
            addTime *= numsArr[i];
        }
    }
    locationNum = indexItem * everyNum;
    for (var i = 0;i<addTime;i++){
        for (var j = 0;j<everyNum;j++){
            priceArr.splice(locationNum*(i+1),1);
            basePriceArr.splice(locationNum*(i+1),1);
            stockArr.splice(locationNum*(i+1),1);
        }
    }
    var imageIndex = $(el).parents('.categoryValueDiv').find('.inputAndImage').index($(el).parents('.inputAndImage'));
    if(index == 0){
        $(".categoryDiv:eq(0)").find('.categoryImgDivItem:eq('+ imageIndex +')').remove();
    }
    $(el).parent().remove();
    createTable();
}
function createTable() {
    var lv1Arr = $('input[name="lv1"]');
    if (!lv1Arr || lv1Arr.length == 0){
        $("#category_table_div").hide();
        $("#category_table").html('');
        return;
    }
    var minPriceValue = 0;
    if (priceArr.length != 0){
        minPriceValue = Math.min.apply(null, priceArr);
    }
    $("#productPrice").val(minPriceValue);
    var minBasePriceValue = 0;
    if (basePriceArr.length != 0){
        minBasePriceValue = Math.min.apply(null, basePriceArr);
    }
    $("#productBasePrice").val(minBasePriceValue);
    var sumValue = 0;
    for (var i = 0;i<stockArr.length;i++){
        sumValue += parseInt(stockArr[i]);
    }
    $("#productStock").val(sumValue);
    var tableHTML = '';
    tableHTML += '<table class="table table-bordered">';
    tableHTML += '    <thead>';
    tableHTML += '        <tr>';
    for (var i = 0; i < lv1Arr.length; i++) {
        tableHTML += '<th width="50">' + $(lv1Arr[i]).val() + '</th>';
    }
    tableHTML += '            <th width="20">价格</th>';
    tableHTML += '            <th width="20">划线价</th>';
    tableHTML += '            <th width="20">库存</th>';
    tableHTML += '            <th width="20">销量</th>';
    tableHTML += '        </tr>';
    tableHTML += '    </thead>';
    tableHTML += '    <tbody>';

    var numsArr = [];
    var idxArr = [];
    for (var i = 0; i < lv1Arr.length; i++) {
        numsArr.push($(lv1Arr[i]).parents('.categoryDiv').find('input[name="lv2"]').length);
        idxArr[i] = 0;
    }
    var len = 1;
    var rowsArr = [];
    for (var i = 0; i < numsArr.length; i++) {
        len = len * numsArr[i];

        var tmpnum = 1;
        for (var j = numsArr.length - 1; j > i; j--) {
            tmpnum = tmpnum * numsArr[j];
        }
        rowsArr.push(tmpnum);
    }
    for (var i = 0; i < len; i++) {
        tableHTML += '        <tr data-row="' + (i+1) + '">';
        var name = '';
        for (var j = 0; j < lv1Arr.length; j++) {
            var n = parseInt(i / rowsArr[j]);
            if (j == 0) {
            } else if (j == lv1Arr.length - 1) {
                n = idxArr[j];
                if (idxArr[j] + 1 >= numsArr[j]) {
                    idxArr[j] = 0;
                } else {
                    idxArr[j]++;
                }
            } else {
                var m = parseInt(i / rowsArr[j]);
                n = m % numsArr[j];
            }
            var text = $(lv1Arr[j]).parents('.categoryDiv').find('input[name="lv2"]').eq(n).val();
            if (j != lv1Arr.length - 1) {
                name += text + '_';
            } else {
                name += text;
            }
            if (i % rowsArr[j] == 0) {
                tableHTML += '<td width="50" rowspan="' + rowsArr[j] + '" data-rc="' + (i+1) + '_' + (j+1) + '">' + text + '</td>';
            }
        }
        if (priceArr.length == 0){
            tableHTML += '<td width="20"><div class="col-sm-12"><input type="text" data-allName="' + name + '" onkeyup="clearNoNum(this)" data-name="price" class="setValueInput" id="price'+ i +'" name="price'+ i +'" value="0" required="true" number="true" max="100000" min="0"/></div></td>';
        }else {
            tableHTML += '<td width="20"><div class="col-sm-12"><input type="text" data-allName="' + name + '" onkeyup="clearNoNum(this)" data-name="price" class="setValueInput" id="price'+ i +'" name="price'+ i +'" value="'+ priceArr[i] +'" required="true" number="true" max="100000" min="0"/></div></td>';
        }
        if (basePriceArr.length == 0){
            tableHTML += '<td width="20"><div class="col-sm-12"><input type="text" data-allName="' + name + '" onkeyup="clearNoNum(this)" data-name="basePrice" class="setValueInput" id="basePrice'+ i +'" name="basePrice'+ i +'" value="0" number="true" max="100000" min="0"/></div></td>';
        }else {
            tableHTML += '<td width="20"><div class="col-sm-12"><input type="text" data-allName="' + name + '" onkeyup="clearNoNum(this)" data-name="basePrice" class="setValueInput" id="basePrice'+ i +'" name="basePrice'+ i +'" value="'+ basePriceArr[i] +'" number="true" max="100000" min="0"/></div></td>';
        }
        if (stockArr.length == 0){
            tableHTML += '<td width="20"><div class="col-sm-12"><input type="text" data-allName="' + name + '" onkeyup="clearNoNum(this)" data-name="stock" class="setValueInput" id="stock'+ i +'" name="stock'+ i +'" value="0" required="true" number="true" max="100000" min="0"/></div></td>';
        }else {
            tableHTML += '<td width="20"><div class="col-sm-12"><input type="text" data-allName="' + name + '" onkeyup="clearNoNum(this)" data-name="stock" class="setValueInput" id="stock'+ i +'" name="stock'+ i +'" value="'+ stockArr[i] +'" required="true" number="true" max="100000" min="0"/></div></td>';
        }
        tableHTML += '<td width="50">0</td>';
        tableHTML += '</tr>';
    }
    tableHTML += '</tbody>';
    tableHTML += '</table>';
    $('#category_table_div').show();
    $('#category_table').html(tableHTML);
    if (priceArr.length == 0){
        for (var i = 0; i < len; i++) {
            priceArr.push('0');
        }
    }
    if (basePriceArr.length == 0){
        for (var i = 0; i < len; i++) {
            basePriceArr.push('0');
        }
    }
    if (stockArr.length == 0){
        for (var i = 0; i < len; i++) {
            stockArr.push('0');
        }
    }
    $("input[data-name='price']").each(function () {
        $(this).blur(function () {
            var index = $("input[data-name='price']").index($(this));
            priceArr[index] = $(this).val();
            var minValue = Math.min.apply(null, priceArr);
            $("#productPrice").val(minValue);
        });
    });
    $("input[data-name='basePrice']").each(function () {
        $(this).blur(function () {
            var index = $("input[data-name='basePrice']").index($(this));
            basePriceArr[index] = $(this).val();
            var minValue = Math.min.apply(null, basePriceArr);
            $("#productBasePrice").val(minValue);
        });
    });
    $("input[data-name='stock']").each(function () {
        $(this).blur(function () {
            var index = $("input[data-name='stock']").index($(this));
            stockArr[index] = $(this).val();
            var sumValue = 0;
            for (var i = 0;i<stockArr.length;i++){
                sumValue += parseInt(stockArr[i]);
            }
            $("#productStock").val(sumValue);
        });
    });
    for (var i = 0;i<priceArr.length;i++){
        $("#insertGoodsForm").bootstrapValidator("addField","price"+i);
        $("#insertGoodsForm").bootstrapValidator("addField","basePrice"+i);
        $("#insertGoodsForm").bootstrapValidator("addField","stock"+i);
    }
}

function clearNoNum(obj) {
    obj.value = obj.value.replace(/[^\d.]/g, "");  //清除“数字”和“.”以外的字符
    obj.value = obj.value.replace(/\.{2,}/g, "."); //只保留第一个. 清除多余的
    obj.value = obj.value.replace(".", "$#$").replace(/\./g, "").replace("$#$", ".");
    obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d).*$/, '$1$2.$3');//只能输入两个小数
    if (obj.value.indexOf(".") < 0 && obj.value != "") {//以上已经过滤，此处控制的是如果没有小数点，首位不能为类似于 01、02的金额
        obj.value = parseFloat(obj.value);
    }
}

function getNumsArr() {
    var lv1Arr = $('input[name="lv1"]');
    var numsArr = [];
    for (var i = 0; i < lv1Arr.length; i++) {
        numsArr.push($(lv1Arr[i]).parents('.categoryDiv').find('input[name="lv2"]').length);
    }
    return numsArr;
}
function getAmount() {
    var lv1Arr = $('input[name="lv1"]');
    var numsArr = [];
    for (var i = 0; i < lv1Arr.length; i++) {
        numsArr.push($(lv1Arr[i]).parents('.categoryDiv').find('input[name="lv2"]').length);
    }
    var len = 1;
    for (var i = 0; i < numsArr.length; i++) {
        len = len * numsArr[i];
    }
    return len;
}
var setupValue = 1;
function setValue(num) {
    setupValue = num;
    $("#setupBtnsGroup").hide();
    $("#saveSetupBtnsGroup").show();
}
function cancelSetup() {
    $("#setupInput").val('');
    $("#setupBtnsGroup").show();
    $("#saveSetupBtnsGroup").hide();
}
function saveSetupValue() {
    if ($("#setupInput").val() == '') {
        Feng.error('输入不能为空');
        return false;
    }
    if($("#setupInput").val()<0 || $("#setupInput").val()>100000){
        Feng.error('输入的值不能小于0或者大于100000');
        return false;
    }
    if (setupValue == 1){
        if (!(/^\d+(?=\.{0,1}\d+$|$)/.test($("#setupInput").val()))) {
            Feng.error('请输入正数');
            return false;
        }
        $("input[data-name='price']").each(function (index,item) {
            $(item).val($("#setupInput").val());
            priceArr[index] = $("#setupInput").val();
        });
    }else if (setupValue == 2){
        if (!(/^\d+(?=\.{0,1}\d+$|$)/.test($("#setupInput").val()))) {
            Feng.error('请输入正数');
            return false;
        }
        $("input[data-name='basePrice']").each(function (index,item) {
            $(item).val($("#setupInput").val());
            basePriceArr[index] = $("#setupInput").val();
        });
    }else {
        if (!(/^[+]{0,1}(\d+)$/.test($("#setupInput").val()))) {
            Feng.error('请输入正整数');
            return false;
        }
        $("input[data-name='stock']").each(function (index,item) {
            $(item).val($("#setupInput").val());
            stockArr[index] = $("#setupInput").val();
        });
    }
    createTable();
    cancelSetup();
}
function saveEditCommit() {
    if (!validate) {
        return;
    }
    if ($("#typeTwo").val() == "请选择商品二级类目"){
        Feng.error('请选择商品二级类目');
        return;
    }
    if ($("#productName").val() == ''){
        Feng.error('商品名称不能为空');
        return;
    }
    if (!(/^.{1,50}$/.test($("#productName").val()))){
        Feng.error('商品名称长度不能超过50字符');
        return;
    }

    for (var i = 0;i<priceArr.length;i++){
        if($("#price"+i).val()<0 || $("#price"+i).val()>100000){
            Feng.error('价格输入的值不能小于0或者大于100000');
            return false;
        }
        if($("#basePrice"+i).val()<0 || $("#basePrice"+i).val()>100000){
            Feng.error('划线价输入的值不能小于0或者大于100000');
            return false;
        }
        if($("#stock"+i).val()<0 || $("#basePrice"+i).val()>100000){
            Feng.error('库存输入的值不能小于0或者大于100000');
            return false;
        }
    }
    var goodsId;
    if(document.getElementById("goodId")){
        goodsId = $("#goodId").val();
    }
    var categoryId = $("#typeTwo>option:selected").attr("data-id");
    var name = $("#productName").val();
    var price = $("#productPrice").val();
    var referPrice = $("#productBasePrice").val();
    var stock = $("#productStock").val();
    if(price<0 || price>100000){
        Feng.error('价格输入的值不能小于0或者大于100000');
        return false;
    }
    if (referPrice != ''){
        if(referPrice<0 || referPrice>100000){
            Feng.error('划线价输入的值不能小于0或者大于100000');
            return false;
        }
    }
    if(stock<0 || stock>100000){
        Feng.error('库存输入的值不能小于0或者大于100000');
        return false;
    }
    if (!(/^[+]{0,1}(\d+)$/.test(stock))) {
        Feng.error('库存请输入正整数');
        return false;
    }
    var expressFee = 0;
    if ($('input:radio[name="kdyf"]:checked').val() != 0){
        if ($("#kdyfInput").val() == ''){
            Feng.error('快递运费不能为空');
            return;
        }
        if ($("#kdyfInput").val()<0 || $("#kdyfInput").val()>100){
            Feng.error('快递运费不能小于0或大于100');
            return;
        }
        if (!(/^\d+(?=\.{0,1}\d+$|$)/.test($("#kdyfInput").val()))) {
            Feng.error('运费请输入正数');
            return false;
        }
        expressFee = $("#kdyfInput").val();
    }
    var lv1ValueArr = [];
    $('input[name="lv1"]').each(function(){
        lv1ValueArr.push($(this).val());
    });
    var lv1ValueArrSoft = lv1ValueArr.sort();

    for(var i=0;i<lv1ValueArr.length;i++){

        if (lv1ValueArrSoft[i] == lv1ValueArr[i+1]){
            Feng.error('规格项目不能相同');
            return false;
        }

    }
    var lv2ValueArr = [];
    $('input[name="lv2"]').each(function(){
        lv2ValueArr.push($(this).val());
    });

    var lv2ValueArrSoft = lv2ValueArr.sort();
    for(var i=0;i<lv2ValueArr.length;i++){

        if (lv2ValueArrSoft[i] == lv2ValueArr[i+1]){
            Feng.error('规格值不能重复');
            return false;
        }

    }
    var specsListObj = {};
    var lv1Arr = $('input[name="lv1"]');
    for (var i = 0; i < lv1Arr.length; i++) {
        if($(lv1Arr[i]).val() == ""){
            Feng.error('规格名称不能为空！');
            return false;
        }
        var lv2Arr = $(lv1Arr[i]).parents('.categoryDiv').find('input[name="lv2"]');
        var tempArr = [];
        for (var j = 0;j<lv2Arr.length;j++) {
            if($(lv2Arr[j]).val() == ""){
                Feng.error('规格值不能为空！');
                return false;
            }
            tempArr.push($(lv2Arr[j]).val());
        }
        specsListObj[$(lv1Arr[i]).val()] = tempArr;
    }
    var specsList = JSON.stringify(specsListObj);
    var imageListArr = [];
    var imageArr = $("li.addImagesItemLi");
    if (imageArr.length == 0) {
        Feng.error('商品图片至少上传一张');
        return;
    }
    var oldImageList = JSON.parse($("#imageList").text());
    var tempIdArr = [];
    for (var i = 0;i<imageArr.length;i++) {
        var tempObj = {};
        tempObj.img = $(imageArr[i]).find('input[type="hidden"]').val();
        if($(imageArr[i]).find('input[type="hidden"]').attr("id") != undefined) {
            tempObj.id = $(imageArr[i]).find('input[type="hidden"]').attr("id");
            tempIdArr.push($(imageArr[i]).find('input[type="hidden"]').attr("id"));
            tempObj.flag = 0;
        }else {
            tempObj.flag = 2;
        }
        tempObj.sequence = i+1;
        imageListArr.push(tempObj);
    }
    for (var i =0;i<oldImageList.length;i++){
        if (tempIdArr.indexOf(oldImageList[i].id + '') == -1){
            var tempObj = {};
            tempObj.img = oldImageList[i].img;
            tempObj.id = oldImageList[i].id;
            tempObj.flag = 1;
            tempObj.sequence = imageListArr.length + 1;
            imageListArr.push(tempObj);
        }
    }
    var imageListJsonString = JSON.stringify(imageListArr);

    var goodsSkuListArr = [];
    var imageList = [];
    if ($("div.categoryDiv:eq(0)").find("input[type='checkbox']").is(':checked')){
        var categoryImgDivItems = $("div.categoryDiv:eq(0)").find("div.categoryImgDivItem");
        for (var i = 0;i<categoryImgDivItems.length;i++){
            if($(categoryImgDivItems[i]).has($("input[type='hidden']"))) {
                var inputHidden = $(categoryImgDivItems[i]).find("div.insertImageDiv").find("div.addImagesItemDiv").find("img.addImgIcon").attr("src");
                imageList.push(inputHidden);
            }else {
                imageList.push('');
            }
        }
    }
    var lv1Arr = $('input[name="lv1"]');
    var priceInputArr = $("input[data-name='price']");
    for(var i = 0;i<priceInputArr.length;i++){
        var goodsSkuObj = {};
        var specsStr = $(priceInputArr[i]).attr("data-allName");
        var specsArr = specsStr.split('_');
        var specsObj = {};
        for (var j = 0;j<lv1Arr.length;j++) {
            specsObj[$(lv1Arr[j]).val()] = specsArr[j];
        }
        goodsSkuObj.specs = specsObj;
        goodsSkuObj.price = priceArr[i];
        goodsSkuObj.stock = stockArr[i];
        goodsSkuObj.img = imageList[i % imageList.length];
        goodsSkuListArr.push(goodsSkuObj);
    }
    var goodsSkuListJsonString = JSON.stringify(goodsSkuListArr);
    var content = editor.txt.html();
    $.ajaxSetup({crossDomain: true, xhrFields: {withCredentials: true}});
    $.ajax({
        url: Feng.ctxPath +"/shop/goods/update",
        type: 'POST',
        dataType: 'json',
        data: {goodsId:goodsId,categoryId:categoryId,name:name,price:price,referPrice:referPrice,stock:stock,expressFee:expressFee,specsList:specsList,imageListJsonString:imageListJsonString,goodsSkuListJsonString:goodsSkuListJsonString,content:content},
        async: false,
        success: function (data) {
            if(data.code != 200){
                Feng.error(data.message);
            }else{
                Feng.success(data.message);
                setTimeout(function () {
                    window.history.go(-1);
                },1000);
            }
        }
    });
}