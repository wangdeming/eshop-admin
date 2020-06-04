@/*
标签中各个参数的说明:
name : 名称
clickFun : 回调函数
@*/
<style>
    .gun-btn-active{
        background-color: #bababa;
        border-color: #bababa;
        color: #FFFFFF;
    }
</style>
<div class="col-sm-12 form-inline" id="data_5">
    <label class="control-label">${name}</label>
    <div class="input-group input-daterange">
        <input type="text" class="form-control" id="createStartTime" placeholder="选择开始时间" autocomplete="off">
        <div class="input-group-addon"> ~</div>
        <input type="text" class="form-control" id="createEndTime" placeholder="选择结束时间" autocomplete="off">
    </div>

    <div class="btn-group hidden-xs" id="timeType" role="group"
         style="display: inline-block;margin-left: 20px;margin-bottom: -5px;">
        <button onclick="getList(5)" type="button" class="btn btn-outline btn-default gun-btn-active" data-id="5"><i
                class="fa"></i>&nbsp;自定义
        </button>
        <button onclick="getList(1)" type="button" class="btn btn-outline btn-default" data-id="1"><i class="fa"></i>&nbsp;今日
        </button>
        <button onclick="getList(2)" type="button" class="btn btn-outline btn-default" data-id="2"><i class="fa"></i>&nbsp;昨日
        </button>
        <button onclick="getList(3)" type="button" class="btn btn-outline btn-default" data-id="3"><i class="fa"></i>&nbsp;近7日
        </button>
        <button onclick="getList(4)" type="button" class="btn btn-outline btn-default" data-id="4"><i class="fa"></i>&nbsp;近30日
        </button>
        <button onclick="getList(0)" type="button" class="btn btn-outline btn-default" data-id="0"><i class="fa"></i>&nbsp;全部
        </button>
    </div>
</div>
<script>
    $(function () {
        $('#timeType').on('click', 'button', function() {
            $(this).addClass('gun-btn-active').siblings().removeClass("gun-btn-active");
        });
        $("#createEndTime").datepicker({
            keyboardNavigation: false,
            forceParse: false,
            autoclose: true,
            endDate: getDate(0)
        }).on("changeDate", function (e) {
            $("#createStartTime").datepicker("setEndDate", e.date);
            ${clickFun}
        });
        $("#createStartTime").datepicker({
            keyboardNavigation: false,
            forceParse: false,
            autoclose: true,
            endDate: getDate(0)
        }).on("changeDate", function (e) {
            $("#createEndTime").datepicker("setStartDate", e.date);
            ${clickFun}
        });
    })

    function getList(timeType) {
        if (timeType == null) {
            timeType = $("#timeType").find('.gun-btn-active')[0].id;
        }
        if (timeType == 1) {
            $("#createStartTime").val(getDate(0));
            $("#createEndTime").val(getDate(0));
            $("#createStartTime,#createEndTime").attr("disabled", "disabled");
        } else if (timeType == 2) {
            $("#createStartTime").val(getDate(-1));
            $("#createEndTime").val(getDate(-1));
            $("#createStartTime,#createEndTime").attr("disabled", "disabled");
        } else if (timeType == 3) {
            $("#createStartTime").val(getDate(-6));
            $("#createEndTime").val(getDate(0));
            $("#createStartTime,#createEndTime").attr("disabled", "disabled");
        } else if (timeType == 4) {
            $("#createStartTime").val(getDate(-29));
            $("#createEndTime").val(getDate(0));
            $("#createStartTime,#createEndTime").attr("disabled", "disabled");
        } else if (timeType == 5) {
            $("#createStartTime,#createEndTime").removeAttr('disabled');
        } else if (timeType == 0) {
            $("#createStartTime,#createEndTime").attr("disabled", "disabled");
            $("#createStartTime").val('');
            $("#createEndTime").val('');
        }
        if (timeType != 5) {
            ${clickFun}
        }
    }

    function getDate(dates) {
        var dd = new Date();
        var n = dates || 0;
        dd.setDate(dd.getDate() + n);
        var y = dd.getFullYear();
        var m = dd.getMonth() + 1;
        var d = dd.getDate();
        m = m < 10 ? "0" + m : m;
        d = d < 10 ? "0" + d : d;
        var day = y + "-" + m + "-" + d;
        return day;
    }

</script>