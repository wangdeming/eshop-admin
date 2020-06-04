package cn.ibdsr.web.core.util;

import cn.ibdsr.web.common.constant.Const;
import cn.ibdsr.web.common.exception.BizExceptionEnum;
import cn.ibdsr.web.common.exception.BussinessException;
import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

/**
 * @Description: 订单工具类
 * @Version: V1.0
 * @CreateUser: xujincai
 * @CreateDate: 2019/3/20 9:56
 * <p>
 * Date Author Description
 * ------------------------------------------------------
 * 2019/3/20 xujincai init
 */
public class OrderUtils {

    /**
     * 高并发唯一订单号
     *
     * @param prefix 订单号前缀
     * @return
     */
    public static String getOrderNoByUUId(Object prefix) {
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyyMMdd");
        String time = format.format(date);
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        if (hashCodeV < 0) {// 有可能是负数
            hashCodeV = -hashCodeV;
        }
        // 0 代表前面补充0
        // 11 代表长度为11
        // d 代表参数为正数型
        return prefix + time + String.format("%011d", hashCodeV);
    }
}
