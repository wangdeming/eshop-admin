@/*
    select标签中各个参数的说明:

    name : select 的名称
    id : select 的 id
    options: 选框数据
@*/

<div class="form-group" style="margin-bottom:0;">
@if(isEmpty(direction)){
    <label class="col-sm-3 input-left control-label">${name}</label>
    <div class="col-sm-9 input-right">
@}else{
    <label class="col-sm-12 control-label vertical-top" style="font-size: 14px;color: #4E5558;">${name}</label>
    <div class="col-sm-12 vertical-bottom" style="position: relative">
@}
        @if(isEmpty(maxlength)){
        <textarea class="form-control" maxlength="300" id="${id}" name="${id}"
                  @if(isNotEmpty(placeholder)){
                   placeholder="${placeholder}"
                  @}
                  style="height:110px;resize:none;">${value!}</textarea>
        @}else{
        <textarea class="form-control" maxlength="${maxlength}" id="${id}"
                  @if(isNotEmpty(placeholder)){
                   placeholder="${placeholder}"
                  @}
                  name="${id}" style="height:136px;resize:none;">${value!}</textarea>
        @}
        <div style="position: absolute;right: 20px;bottom: 5px;z-index: 1">
            <span id="span_${id}">0</span>
            <span>/</span>
            @if(isEmpty(maxlength)){
            <span>300</span>
            @}else{
            <span>${maxlength}</span>
            @}
        </div>
    </div>
</div>
    <script>
        $(function () {
            setTimeout( function() {
                $("#span_${id}").text( $("#${id}").val().length);
            },500);

            $("#${id}").keyup(function(){
                var len = $(this).val().length;
                $("#span_${id}").text(len);
            });
        })
    </script>