var roomManage = {
    layerIndex: -1
};
$(function () {
    var todayStr = getDay(0);
    $("#createStartTime").datepicker("setDate", todayStr);
    getRoomSettingList(todayStr);
    $('#createStartTime')
        .datepicker()
        .on('changeDate', function(ev){
            if(isNaN($('#createStartTime').datepicker('getDate'))){
                return;
            }
            var startTime = getDay(0);
            var endTime = getDateStr(ev.date);
            dayNum = dateDiffIncludeToday(startTime, endTime);
            getRoomSettingList(getDay(dayNum));
        });
});
//批量设置
function openRoomsManage() {
    if (!$("input[name='selectRoom']:checked").val()){
        Feng.error("请选择一个房型!");
        return;
    }
    sessionStorage.setItem("roomId",$("input[name='selectRoom']:checked").val());
    var index = layer.open({
        type: 2,
        title: '批量设置',
        area: ['600px', '450px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: [Feng.ctxPath + '/shop/hotel/batchSetup']
    });
    roomManage.layerIndex = index;
}
var dayNum = 0;
//前15天
function foreDaysSetup() {
    dayNum = dayNum - 15;
    var foreDaysStr = getDay(dayNum);
    $("#createStartTime").datepicker("setDate", foreDaysStr);
    getRoomSettingList(foreDaysStr);
}
//后15天
function afterDaysSetup() {
    dayNum = dayNum + 15;
    var afterDaysStr = getDay(dayNum);
    $("#createStartTime").datepicker("setDate", afterDaysStr);
    getRoomSettingList(afterDaysStr);
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
//创建表格
function getRoomSettingList(dateStr) {
    var ajax = new $ax(Feng.ctxPath + "/shop/hotel/roomSettingList", function (data) {
        var rows = data.data.rows;
        var roomSettingList = rows[0].roomSettingList;
        var theadHtml = '<th></th>';
        for (var i=0;i<roomSettingList.length;i++){
            var tempDate = roomSettingList[i].date;
            var monthAndDay = tempDate.substr(5,5);
            var weekStr = getWeek(tempDate);
            theadHtml += '<th style="text-align: center;"><span>'+ monthAndDay +'</span><span style="margin-left: 5px;">'+ weekStr +'</span></th>';
        }
        var tbodyHtml = '';
        for (var i=0;i<rows.length;i++){
            var name = rows[i].name;
            tbodyHtml += '<tr style="text-align: center;"><td><input type="radio" name="selectRoom" value="'+ rows[i].roomId +'"><span style="margin-left: 5px;">'+ name +'</span></td>';
            var tempList = rows[i].roomSettingList;
            for (var j=0;j<tempList.length;j++){
                var status = '';
                if(tempList[j].status != null){
                    status = tempList[j].status;
                }
                var statusNum = 0;
                if(status == '开'){
                    statusNum =1;
                }
                var price = '';
                if(tempList[j].price != null){
                    price = tempList[j].price;
                }
                var number = '';
                if(tempList[j].number != null){
                    number = tempList[j].number;
                }
                var numOrder = '';
                if(tempList[j].numOrder != null){
                    numOrder = tempList[j].numOrder;
                }
                var tempDate = tempList[j].date;
                var atime=Date.parse(tempDate);
                var btime=Date.parse(getDay(0));
                var timeValue = parseInt((atime - btime) / (1000 * 60 * 60 * 24));
                if(Date.parse(tempDate)<Date.parse(getDay(0)) || timeValue>90){
                    tbodyHtml += '<td style="cursor: not-allowed;">' +
                        '<div style="height: 30px;line-height: 30px;">'+ status +'</div>' +
                        '<div style="height: 30px;line-height: 30px;">￥'+ price +'</div>' +
                        '<div style="height: 30px;line-height: 30px;">'+ number +'</div>' +
                        '<div style="height: 30px;line-height: 30px;position: relative;" onmouseover="mouseOver(event,'+ numOrder +')" onmouseout="mouseOut(event)">' +
                        '   <span style="width: 100%;height: 30px;border: 1px solid #CCCCCC;display: none;position: absolute;top: 0;left: 60%;">已订房数：'+ numOrder +'</span>' +
                        ''+ numOrder +'</div>' +
                        '</td>';
                }else {
                    tbodyHtml += '<td style="cursor: pointer;" onclick="goRoomManage('+ rows[i].roomId +','+ statusNum +','+ price +','+ atime +')">' +
                        '<div style="height: 30px;line-height: 30px;">'+ status +'</div>' +
                        '<div style="height: 30px;line-height: 30px;">￥'+ price +'</div>' +
                        '<div style="height: 30px;line-height: 30px;">'+ number +'</div>' +
                        '<div style="height: 30px;line-height: 30px;position: relative;" onmouseover="mouseOver(event,'+ numOrder +')" onmouseout="mouseOut(event)">' +
                        '   <span style="width: 100%;height: 30px;border: 1px solid #CCCCCC;display: none;position: absolute;top: 0;left: 60%;">已订房数：'+ numOrder +'</span>' +
                        ''+ numOrder +'</div>' +
                        '</td>';
                }
            }
            tbodyHtml += '</tr>';
        }
        $("#roomManageThead").empty().append(theadHtml);
        $("#roomManageTbody").empty().append(tbodyHtml);
    }, function (data) {
        Feng.error(data.responseJSON.message + "!");
    });
    ajax.set("offset",0);
    ajax.set("limit",10000);
    ajax.set("roomId",0);
    ajax.set("currentDate",dateStr);
    ajax.start();
}
function mouseOver(event,numOrder) {
    if (numOrder != null){
        $(event.target).find("span").css("display","inline-block");
    }
}
function mouseOut(event) {
    $(event.target).find("span").css("display","none");
}
//房态设置
function goRoomManage(roomId,statusNum,price,atime) {
    var newTime = new Date(atime);
    var timeStr = newTime.getFullYear() + '-' + addZero(newTime.getMonth() + 1) + '-' + addZero(newTime.getDate());
    var tempHtml = '';
    if(statusNum == 1){
        tempHtml = '<input type="radio" name="roomStatus" value="1" checked="checked"><label style="margin-left: 5px;">开</label><input style="margin-left: 40px;" type="radio" name="roomStatus" value="0"><label style="margin-left: 5px;">关</label>';
    }else {
        tempHtml = '<input type="radio" name="roomStatus" value="1"><label style="margin-left: 5px;">开</label><input style="margin-left: 40px;" type="radio" name="roomStatus" value="0" checked="checked"><label style="margin-left: 5px;">关</label>';
    }
    $.sendConfirm({
        title: '房态设置',
        content: '<div class="layerDivItem">' +
        '<div class="layerDivItemTitle">日期:</div>' +
        '<div class="layerDivItemContent">'+ timeStr +'</div>' +
        '</div>' +
        '<div class="layerDivItem">' +
        '<div class="layerDivItemTitle">*房间数量:</div>' +
        '<div class="layerDivItemContent"><input type="text" id="roomNum" style="width: 200px;height: 30px;line-height: 30px;font-size: 12px;" placeholder="请填写数量"></div>' +
        '</div>' +
        '<div class="layerDivItem">' +
        '<div class="layerDivItemTitle">*房间价格:</div>' +
        '<div class="layerDivItemContent" style="position: relative;">' +
        '<span style="position: absolute;left: 0;top: 5px;display: inline-block;width: 30px;height: 31px;line-height: 31px;text-align: center;background-color: #CCCCCC;">¥</span>' +
        '<input type="text" id="roomPrice" value="'+ price +'" style="width: 200px;height: 30px;line-height: 30px;font-size: 12px;text-indent: 30px;" placeholder="请填写价格">' +
        '</div>' +
        '</div>' +
        '<div class="layerDivItem">' +
        '<div class="layerDivItemTitle">*状态:</div>' +
        '<div class="layerDivItemContent">'+ tempHtml +'</div>' +
        '</div>',
        button: {
            confirm: '确定',
            cancel: '取消'
        },
        width: 400,
        onBeforeConfirm: function() {
            var number = $("#roomNum").val();
            if(number == ''){
                Feng.error("请填写房间数量");
                return false;
            }
            if (!(/^\d+$/.test(number))) {
                Feng.error('房间数量请输入正整数');
                return false;
            }
            var roomPrice = $("#roomPrice").val();
            if(roomPrice == ''){
                Feng.error("请填写房间价格");
                return false;
            }
            if (!(/^\d+(?=\.{0,1}\d+$|$)/.test(roomPrice))) {
                Feng.error('房间价格请输入正数');
                return false;
            }
            var roomStatus = $("input[name='roomStatus']:checked").val();
            var ajax = new $ax(Feng.ctxPath + "/shop/hotel/setRoom", function (data) {
                if(data.code != 200){
                    Feng.error(data.message);
                    return false;
                }else{
                    Feng.success(data.message);
                    var currentTimeStr = getDay(dayNum);
                    getRoomSettingList(currentTimeStr);
                }
            }, function (data) {
                Feng.error(data.responseJSON.message + "!");
            });
            ajax.set("roomId",roomId);
            ajax.set("date",timeStr);
            ajax.set("number",number);
            ajax.set("price",roomPrice);
            ajax.set("status",roomStatus);
            ajax.start();
        }
    });
}
function addZero(num){
    if(parseInt(num) < 10){
        num = '0'+num;
    }
    return num;
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

/**
 * 是否为Null
 * @param object
 * @returns {Boolean}
 */
function isNull(object){
    if(object == null || typeof object == "undefined"){
        return true;
    }
    return false;
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
    return parseInt((endDate - startDate ) / 1000 / 60 / 60 /24);//把相差的毫秒数转换为天数
}