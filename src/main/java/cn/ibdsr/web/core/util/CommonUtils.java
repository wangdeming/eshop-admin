package cn.ibdsr.web.core.util;

import cn.ibdsr.core.util.ToolUtil;
import com.alibaba.fastjson.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * Created by xjc on 2019/1/18.
 */
public class CommonUtils {

    /**
     * 32位长度的UUID
     *
     * @return
     */
    public static String getUUID32() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 36位长度的UUID
     *
     * @return
     */
    public static String getUUID36() {
        return UUID.randomUUID().toString();
    }

    /**
     * 获取pageNumber
     *
     * @param limit
     * @param offset
     * @return
     */
    public static int getPageNumber(int limit, int offset) {
        return offset / limit + 1;
    }

    /**
     * 获取订单号 24 位的订单号
     */
    public static String getOrderCode() {
        /**生成3位的随机数*/
        Random random = new Random();
        Integer rand = random.nextInt(8999999) + 1000000;
        /**格式化当前时间*/
        SimpleDateFormat sfDate = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        /**17位的日期*/
        String strDate = sfDate.format(new Date());
        return strDate + rand.toString();
    }

    /**
     * 清除返回数据的空字符串字段
     *
     * @param data 数据
     * @return
     */
    public static Object cleanData(Object data) {
        if (ToolUtil.isEmpty(data)) {
            return null;
        }

        String str = JSONObject.toJSONString(data);
        Object parse = JSONObject.parse(str);
        return parse;
    }

}
