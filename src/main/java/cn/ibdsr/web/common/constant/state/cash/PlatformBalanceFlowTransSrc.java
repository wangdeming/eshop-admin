package cn.ibdsr.web.common.constant.state.cash;

import cn.ibdsr.web.common.constant.state.shop.ShopType;

/**
 * @Description 平台余额流水交易来源
 * @Version V1.0
 * @CreateDate 2019-04-23 09:25:22
 *
 * Date                     Author               Description
 * ----------------------------------------------------------
 * 2019-04-23 09:25:22    XuZhipeng               类说明
 *
 */
public enum PlatformBalanceFlowTransSrc {

    ORDER_INCOME(1, "订单收入"),    // 用户支付+
    GOODS_REFUND(2, "用户退款"),    // 用户退款-
    WITHDRAWAL(3, "提现打款");      // 店铺提现成功-

    int code;
    String message;

    PlatformBalanceFlowTransSrc(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static String valueOf(Integer value) {
        if (value == null) {
            return "";
        } else {
            for (PlatformBalanceFlowTransSrc ms : PlatformBalanceFlowTransSrc.values()) {
                if (ms.getCode() == value) {
                    return ms.getMessage();
                }
            }
            return "";
        }
    }
}
