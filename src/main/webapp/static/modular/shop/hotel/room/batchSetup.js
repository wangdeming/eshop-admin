$(function () {
    $("input[name='allweekDay']").prop("disabled","true");
    $("input[name='weekDay']").prop("disabled","true");
    var todayStr = getDay(0);
    $('#startTime').datepicker('setStartDate', getDay(0));
    $('#endTime').datepicker('setStartDate', getDay(0));
    $('#startTime').datepicker('setEndDate', getDay(90));
    $('#endTime').datepicker('setEndDate', getDay(90));
    $('#startTime')
        .datepicker()
        .on('changeDate', function(ev){
            if(isNaN($('#startTime').datepicker('getDate'))){
                return;
            }
            var endTimeStartTime =  getDateStr(ev.date);
            $('#endTime').datepicker('setStartDate', endTimeStartTime);
            if (!isNaN($('#endTime').datepicker('getDate'))){
                var startTime = getDateStr($('#startTime').datepicker('getDate'));
                var endTime = getDateStr($('#endTime').datepicker('getDate'));
                var dateDiff = dateDiffIncludeToday(startTime,endTime);
                var startWeekday = $('#startTime').datepicker('getDate').getDay();
                var weekArr = [1,2,3,4,5,6,7];
                if (dateDiff < 7){
                    $("input[name='allweekDay']").prop("disabled","true");
                    $("input[name='weekDay']").prop("disabled","true");
                    if (startWeekday == 0){
                        startWeekday = 7;
                    }
                    var index = weekArr.indexOf(startWeekday);
                    if (index > -1){
                        for (var i=0;i<dateDiff;i++){
                            var arrIndex = index + i;
                            if (arrIndex <7){
                                $("input[name='weekDay'][value='"+ (arrIndex + 1) +"']").removeAttr("disabled");
                            }else {
                                var tempIndex = arrIndex -7;
                                $("input[name='weekDay'][value='"+ (tempIndex + 1) +"']").removeAttr("disabled");
                            }
                        }
                    }
                    $("input[name='weekDay']:disabled").removeAttr("checked");
                }else {
                    $("input[name='allweekDay']").removeAttr("disabled");
                    $("input[name='weekDay']").removeAttr("disabled");
                }
            }else {
                $("input[name='allweekDay']").prop("disabled","true");
                $("input[name='weekDay']").prop("disabled","true");
            }
        });
    $('#endTime')
        .datepicker()
        .on('changeDate', function(ev){
            if(isNaN($('#endTime').datepicker('getDate'))){
                return;
            }
            var startTimeEndTime =  getDateStr(ev.date);
            $('#startTime').datepicker('setEndDate', startTimeEndTime);
            if (!isNaN($('#startTime').datepicker('getDate'))){
                var startTime = getDateStr($('#startTime').datepicker('getDate'));
                var endTime = getDateStr($('#endTime').datepicker('getDate'));
                var dateDiff = dateDiffIncludeToday(startTime,endTime);
                var startWeekday = $('#startTime').datepicker('getDate').getDay();
                var weekArr = [1,2,3,4,5,6,7];
                if (dateDiff < 7){
                    $("input[name='weekDay']").prop("disabled","true");
                    $("input[name='allweekDay']").prop("disabled","true");
                    if (startWeekday == 0){
                        startWeekday = 7;
                    }
                    var index = weekArr.indexOf(startWeekday);
                    if (index > -1){
                        for (var i=0;i<dateDiff;i++){
                            var arrIndex = index + i;
                            if (arrIndex <7){
                                $("input[name='weekDay'][value='"+ (arrIndex + 1) +"']").removeAttr("disabled");
                            }else {
                                var tempIndex = arrIndex -7;
                                $("input[name='weekDay'][value='"+ (tempIndex + 1) +"']").removeAttr("disabled");
                            }
                        }
                    }
                    $("input[name='weekDay']:disabled").removeAttr("checked");
                }else {
                    $("input[name='allweekDay']").removeAttr("disabled");
                    $("input[name='weekDay']").removeAttr("disabled");
                }
            }else {
                $("input[name='allweekDay']").prop("disabled","true");
                $("input[name='weekDay']").prop("disabled","true");
            }
        });
    $("input[name='allweekDay']").click(function () {
        if($(this).is(':checked')){
            $("input[name='weekDay']").prop("checked","checked");
        }else {
            $("input[name='weekDay']").removeAttr("checked");
        }
    });
    $("input[name='weekDay']").click(function () {
        if($("input[name='weekDay']").length == $("input[name='weekDay']:checked").length){
            $("input[name='allweekDay']").prop("checked","checked");
        }else {
            $("input[name='allweekDay']").removeAttr("checked");
        }
    });
});
function closePage() {
    parent.layer.close(window.parent.roomManage.layerIndex);
}
function addSubmit() {
    var roomId = sessionStorage.getItem("roomId");
    var startDate = getDateStr($('#startTime').datepicker('getDate'));
    var endDate = getDateStr($('#endTime').datepicker('getDate'));
    var weekdayListArr = [];
    $("input[name='weekDay']:checked").each(function (index,item) {
        weekdayListArr.push($(this).val());
    });
    var weekdayList = weekdayListArr.join(",");
    var number = $("#roomNum").val();
    if(number == ''){
        Feng.error("请填写房间数量");
        return false;
    }
    if (!(/^\d+$/.test(number))) {
        Feng.error('房间数量请输入正整数');
        return false;
    }
    var price = $("#roomPrice").val();
    if(price == ''){
        Feng.error("请填写房间价格");
        return false;
    }
    if (!(/^\d+(?=\.{0,1}\d+$|$)/.test(price)) || price < 1 || price > 100000) {
        Feng.error('房间价格范围为1-100000');
        return false;
    }
    var status = $("input[name='roomStatus']:checked").val();
    var ajax = new $ax(Feng.ctxPath + "/shop/hotel/batchSetRoom", function (data) {
        if(data.code != 200){
            Feng.error(data.message);
            return false;
        }else{
            Feng.success(data.message);
            var currentTimeStr = getDay(window.parent.dayNum);
            window.parent.getRoomSettingList(currentTimeStr);
            parent.layer.close(window.parent.roomManage.layerIndex);
        }
    }, function (data) {
        Feng.error(data.responseJSON.message + "!");
    });
    ajax.set("roomId",roomId);
    ajax.set("startDate",startDate);
    ajax.set("endDate",endDate);
    ajax.set("weekdayList",weekdayList);
    ajax.set("number",number);
    ajax.set("price",price);
    ajax.set("status",status);
    ajax.start();
}
//通过数字获得date字符串，1为后一天，-1为前一天
function getDay(day){
    var today = new Date();

    var targetday_milliseconds=today.getTime() + 1000*60*60*24*day;

    today.setTime(targetday_milliseconds); //注意，这行是关键代码

    var tYear = today.getFullYear();
    var tMonth = today.getMonth();
    var tDate = today.getDate();
    tMonth = doHandleMonth(tMonth + 1);
    tDate = doHandleMonth(tDate);
    return tYear+"-"+tMonth+"-"+tDate;
}
function doHandleMonth(month){
    var m = month;
    if(month.toString().length == 1){
        m = "0" + month;
    }
    return m;
}
//通过Date()获取date字符串
function getDateStr(date) {
    var y = date.getFullYear();
    var m = date.getMonth() + 1;
    m = m < 10 ? ('0' + m) : m;
    var d = date.getDate();
    d = d < 10 ? ('0' + d) : d;
    return y + '-' + m + '-' + d;
}
/**
 * 计算2个日期相差的天数，包含今天，如：2016-12-13到2016-12-15，相差3天
 * @param startDateString
 * @param endDateString
 * @returns
 */
function dateDiffIncludeToday(startDateString, endDateString){
    var separator = "-"; //日期分隔符
    var startDates = startDateString.split(separator);
    var endDates = endDateString.split(separator);
    var startDate = new Date(startDates[0], startDates[1]-1, startDates[2]);
    var endDate = new Date(endDates[0], endDates[1]-1, endDates[2]);
    return parseInt(Math.abs(endDate - startDate ) / 1000 / 60 / 60 /24) + 1;//把相差的毫秒数转换为天数
}
/**
 * 根据日期字符串获取星期几
 * @param dateString 日期字符串（如：2016-12-29），为空时为用户电脑当前日期
 * @returns {String}
 */
function getWeek(dateString){
    var date;
    if(isNull(dateString)){
        date = new Date();
    }else{
        var dateArray = dateString.split("-");
        date = new Date(dateArray[0], parseInt(dateArray[1] - 1), dateArray[2]);
    }
    return "周" + "日一二三四五六".charAt(date.getDay());
}