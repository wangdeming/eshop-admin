package cn.ibdsr.web.core.util;

import cn.ibdsr.core.util.ToolUtil;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import static cn.ibdsr.core.util.DateUtil.formatDate;

/**
 * @Description 类功能和用法的说明
 * @Version V1.0
 * @CreateDate 2019/3/13 14:18
 *
 * Date           Author               Description
 * ------------------------------------------------------
 * 2019/3/13      wangzhipeng            类说明
 */
public class DateUtils {

    private static final String defaultZoneId = ZoneId.systemDefault().toString();
    /**
     * 获取yyyy/M/d H:m格式
     *
     * @return
     */
    public static String currentDateTime() {
        return formatDate(new Date(), "yyyy/M/d H:m");
    }

    /**
     * 获取yyyy/M/d格式
     *
     * @return
     */
    public static String currentDate() {
        return formatDate(new Date(), "yyyy/M/d");
    }

    /**
     * 获取yyyy-M-d格式
     */
    public static String birthdayDate(Date date) {
        if (ToolUtil.isNotEmpty(date)) {
            return formatDate(date, "yyyy-MM-dd");
        }else{
            return "请填写";
        }
    }

    /**
     * 将yyyy-M-d格式的字符串日期转换为Date类型
     */
    public static Date translateStrToDate (String strDate) {
        SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd" );
        Date date = null;
        try {
            date = sdf.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 获取yyyy-MM-dd HH:mm格式
     * @param dateTime 转换时间
     * @return
     */
    public static String convertDateFormat(Date dateTime) {
        return null != dateTime ? cn.ibdsr.core.util.DateUtil.format(dateTime, "yyyy-MM-dd HH:mm") : null;
    }

    /**
     * 获取当天的开始时间
     *
     * @return
     */
    public static Date getDayBegin() {
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 获取某天的开始时间
     *
     * @return
     */
    public static String getStartTimeOfDay(String date) {
        Calendar day = Calendar.getInstance();
        day.setTime(translateStrToDate(date));
        day.set(Calendar.HOUR_OF_DAY, 0);
        day.set(Calendar.MINUTE, 0);
        day.set(Calendar.SECOND, 0);
        day.set(Calendar.MILLISECOND, 0);
        return formatDate(day.getTime(),"yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 获取某天的介绍时间
     *
     * @return
     */
    public static String getEndTimeOfDay(String date) {
        Calendar day = Calendar.getInstance();
        day.setTime(translateStrToDate(date));
        day.set(Calendar.HOUR_OF_DAY, 23);
        day.set(Calendar.MINUTE, 59);
        day.set(Calendar.SECOND, 59);
        day.set(Calendar.MILLISECOND, 999);
        return formatDate(day.getTime(),"yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 获取明天的开始时间
     *
     * @return
     */
    public static Date getDateOfTomorrow() {
        Calendar cal = new GregorianCalendar();
        cal.setTime(getDayBegin());
        cal.add(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

    /**
     * 获取未来第days天的时间
     *
     * @param days 天数
     * @return
     */
    public static Date getFetureTime(Date time, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + days);
        Date dateTime = calendar.getTime();
        return dateTime;
    }

    /**
     * 获取未来第N天的时间列表
     *
     * @param days 天数
     * @return
     */
    public static List<String> getFetureTimeList(String date, int days) {
        SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd" );
        Date time = null;
        try {
            time = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<String> dateList = new ArrayList<>();
        for (int day=0; day < days; day++){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(time);
            calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + day);
            Date dateTime = calendar.getTime();
            dateList.add(DateUtils.birthdayDate(dateTime));
        }
        return dateList;
    }

    /**
     * 获取订单失效的剩余时间
     *
     * @param second 秒数
     * @return
     */
    public static Date getFailTime(Date time, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.set(Calendar.SECOND, calendar.get(Calendar.SECOND)+ second);
        Date dateTime = calendar.getTime();
        return dateTime;
    }

    /**
     * 获取一年后时间
     *
     * @return
     */
    public static Date getDateOfOneYearLater() {
        Calendar cal = new GregorianCalendar();
        cal.setTime(getDayBegin());
        cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) + 1);
        return cal.getTime();
    }

    /**
     * 获取两个时间点相距的分秒
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static String getDateTimeDiff(Date startTime, Date endTime) {
        long nd = 1000 * 24 * 60 * 60;      // 一天的毫秒数
        long nh = 1000 * 60 * 60;           // 一小时的毫秒数
        long nm = 1000 * 60;                // 一分钟的毫秒数
        long ns = 1000;                     // 一秒钟的毫秒数

        long diff = endTime.getTime() - startTime.getTime();
        String min = diff % nd % nh / nm +"";           // 计算差多少分钟
        String sec = diff % nd % nh % nm / ns + "";

        // 输出结果
        StringBuilder sb = new StringBuilder();
        return sb.append(min).append("分").append(sec).append("秒").toString();
    }
    /**
     * 获取两个时间点相距的天时分
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static String getDateDiff(Date startTime, Date endTime) {
        long nd = 1000 * 24 * 60 * 60;      // 一天的毫秒数
        long nh = 1000 * 60 * 60;           // 一小时的毫秒数
        long nm = 1000 * 60;                // 一分钟的毫秒数

        // 获得两个时间的毫秒时间差异
        long diff = endTime.getTime() - startTime.getTime();

        String day = diff / nd +"";                     // 计算差多少天
        String hour = diff % nd / nh +"";               // 计算差多少小时
        String min = diff % nd % nh / nm +"";           // 计算差多少分钟

        // 输出结果
        StringBuilder sb = new StringBuilder();
        return sb.append(day).append("天").append(hour).append("小时").append(min).append("分钟").toString();

    }

    // 得到某一天是星期几
    public static int getDateInWeek(String strDate) {
        DateFormat df = DateFormat.getDateInstance();
        try {
            df.parse(strDate);
            java.util.Calendar c = df.getCalendar();
            int day = c.get(Calendar.DAY_OF_WEEK) - Calendar.SUNDAY;
            return day;
        } catch (ParseException e) {
            return -1;
        }
    }

    /**
     * 获取某段时间内的周一（二等等）的日期
     * @param dateBegin 开始日期
     * @param dateEnd 结束日期
     * @param weekDay 获取周几，1－7分别代表周一到周日
     * @return 返回日期List
     */
    public static List<String> getDayOfWeek(String dateBegin, String dateEnd, int weekDay) {
        List<String> dateResult = new ArrayList<>();
        List<String> dateList = getBetweenDate(dateBegin, dateEnd);
        for (String strDate: dateList) {
            LocalDate date = LocalDate.parse(strDate);
            if (weekDay == date.getDayOfWeek().getValue()) {
                dateResult.add(strDate);
            }
        }
        return dateResult;
    }

    /**
     * Date -> LocalDateTime
     *
     * @param date 时间
     * @return LocalDateTime
     */
    public static LocalDateTime toLocalDateTime(Date date) {
        Objects.requireNonNull(date, "Date不能为空");
        return date.toInstant().atZone(ZoneId.of(defaultZoneId)).toLocalDateTime();
    }

    /**
     * 获取两个时间段内的所有日期，日期可跨年
     */
    public static List<String> getBetweenDate(String begin,String end){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        List<String> betweenList = new ArrayList<String>();
        try{
            Calendar startDay = Calendar.getInstance();
            startDay.setTime(format.parse(begin));
            startDay.add(Calendar.DATE, -1);
            while(true){
                startDay.add(Calendar.DATE, 1);
                Date newDate = startDay.getTime();
                String newend=format.format(newDate);
                betweenList.add(newend);
                if(end.equals(newend)){
                    break;
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return betweenList;
    }

}
