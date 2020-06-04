package cn.ibdsr.web.core.util;

import cn.ibdsr.core.util.ToolUtil;

import java.math.BigDecimal;

/**
 * @Description 金额格式工具类
 * @Version V1.0
 * @CreateDate 2019-03-18 09:31:22
 *
 * Date                     Author               Description
 * ----------------------------------------------------------
 * 2019-03-18 09:31:22    XuZhipeng               类说明
 *
 */
public class AmountFormatUtil {

    /**
     * 金额保留小数点后两位
     *
     * @param amount 金额（单位：分）
     * @return
     */
    public static String amountFormat(Object amount) {
        if (ToolUtil.isNotEmpty(amount)) {
            return new BigDecimal(String.valueOf(amount))
                    .divide(BigDecimal.valueOf(100))
                    .setScale(2, BigDecimal.ROUND_DOWN).toString();
        } else {
            return "0.00";
        }
    }

    /**
     * 金额变动格式保留小数点后两位
     *
     * @param amount 金额（单位：分）
     * @return
     */
    public static String amountChangeFormat(Object amount) {
        if (ToolUtil.isNotEmpty(amount)) {
            BigDecimal amt = new BigDecimal(String.valueOf(amount)).divide(BigDecimal.valueOf(100));
            if (BigDecimal.ZERO.compareTo(amt) < 0) {
                return "+" + amt.setScale(2, BigDecimal.ROUND_DOWN);
            }
            return amt.setScale(2, BigDecimal.ROUND_DOWN).toString();
        } else {
            return "0.00";
        }
    }

    /**
     * 小数转折扣
     *
     * @param discount 格式为两位小数
     * @return
     */
    public static String discountFormat(Object discount) {
        if (ToolUtil.isNotEmpty(discount)) {
            BigDecimal temp = new BigDecimal(String.valueOf(discount))
                    .multiply(BigDecimal.TEN)
                    .setScale(1, BigDecimal.ROUND_DOWN);
            return temp.toString() + "折";
        } else {
            return "不打折";
        }
    }

    /**
     * 保留小数点后四位
     *
     * @param amount
     * @return
     */
    public static String keep4DecimalFormat(Object amount) {
        if (ToolUtil.isNotEmpty(amount)) {
            return new BigDecimal(String.valueOf(amount))
                    .setScale(4, BigDecimal.ROUND_DOWN).toString();
        } else {
            return "0.0000";
        }
    }

    /**
     * 小数点转%
     *
     * @param rate 比率小数点
     * @return
     */
    public static String convertPercent(Object rate) {
        if (ToolUtil.isNotEmpty(rate)) {
            return new BigDecimal(String.valueOf(rate))
                    .multiply(BigDecimal.valueOf(100))
                    .stripTrailingZeros()
                    .toPlainString() + "%";
        } else {
            return null;
        }
    }
}
