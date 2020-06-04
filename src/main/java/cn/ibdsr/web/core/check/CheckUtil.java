package cn.ibdsr.web.core.check;

public class CheckUtil {

    public static final String CHINESE_OR_LETTER = "^.{1,20}$";

    /**
     * 整型校验
     */
    public static final String INTEGER_STR = "^-?[1-9]\\d{0,8}|0$";

    /**
     * 浮点型校验
     */
    public static final String FLOAT_STR = "^(-?[1-9]\\d{0,12}|0)(\\.\\d{0,2})?$";

    /**
     * yyyy-MM-dd 日期格式
     */
    public static final String DATE_STR = "^(\\d{4}|\\d{2})-((1[0-2])|(0?[1-9]))-(([12][0-9])|(3[01])|(0?[1-9]))";

    /**
     * yyyy-MM-dd hh:mm:ss 日期时间格式
     */
    public static final String DATATIME_STR = "^(\\d{4}|\\d{2})-((1[0-2])|(0?[1-9]))-(([12][0-9])|(3[01])|(0?[1-9]))\\s+((1|0?)[0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])";

    /**
     * hh:mm 时间格式
     */
    public static final String TIME_STR = "^((1|0?)[0-9]|2[0-3]):([0-5][0-9])";

    /**
     * 店铺账号
     */
    public static final String ACCOUNT = "^[0-9a-zA-Z]{2,15}$";

    /**
     * 商品类别名称
     */
    public static final String GOODS_CATEGORY_NAME_STR = "^[A-Za-z\\u4e00-\\u9fa5]{1,20}$";

    /**
     * 商品类别前台名称
     */
    public static final String GOODS_CATEGORY_FRONT_NAME_STR = "^[A-Za-z\\u4e00-\\u9fa5]{2,6}$";
}
