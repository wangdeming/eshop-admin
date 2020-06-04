/**
 * Created by macroot on 2019/4/11.
 */
$(function () {
    var imgstr = $("#imgValueInput").val();
    var type = $("#typeValueInput").val();
    var relationVal = $("#relationValInput").val();
    $("#typeRadio" + type).attr("checked","checked");
    $("#typeInput" + type).val(relationVal);
    $("#typeInput" + type).css("display","inline-block");
    $("#logo").val(imgstr);
    $("#img_9").attr("src",imgstr);
    $("input[name='type']").click(function () {
        var index = $(this).val();
        $("input[name='typeInput']").css("display","none");
        $("#typeInput" + index).css("display","inline-block");
    });
});
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
function getImgURL (id) {
    var file = $("#imgup_"+id).find("input")[0].files[0];
    var reader = new FileReader();
    var imgFile;
    reader.readAsDataURL(file);
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
            if(data.code==200){
                Feng.success("图片上传成功");
                $("#img_"+id).attr('src', data.data.path);
                $("#div_imgtype_" + id).css("opacity", "0");
                $("#logo").val(data.data.path);
            }else{
                Feng.error(data.message);
            }
        }
    });
}
function closePage() {
    parent.layer.close(window.parent.AdInfoManagerAccount.layerIndex);
}
function addSubmit() {
    var type = $("input[name='type']:checked").val();
    if (type == null){
        Feng.error("请选择关联类型");
        return;
    }
    if ($("#typeInput" + type).val() == '' && type != 4){
        Feng.error("请填写关联值");
        return;
    }
    if ($("#logo").val() == ''){
        Feng.error("请上传图片");
        return;
    }
    var positionId = $("#adIdInput").val();
    var relationVal = '';
    if (type != 4){
        relationVal = $("#typeInput" + type).val();
    }
    var img = $("#logo").val();
    var sequence = window.parent.layer.sequence;
    var ajax = new $ax(Feng.ctxPath + "/platform/adInfoManager/homeAdUpdate", function (data) {
        if(data.code==200){
            Feng.success("编辑广告成功!");
            window.parent.location.reload();
            closePage();
        }else{
            Feng.error( data.message + "!");
        }
    }, function (data) {
        Feng.error("编辑失败!" + data.responseJSON.message + "!");
    });
    ajax.set("id", positionId);
    ajax.set("type", type);
    ajax.set("relationVal", relationVal);
    ajax.set("img", img);
    ajax.set("sequence", sequence);
    ajax.start();
}