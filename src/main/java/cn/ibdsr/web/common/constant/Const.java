package cn.ibdsr.web.common.constant;

/**
 * 系统常量
 *
 * @author fengshuonan
 * @date 2017年2月12日 下午9:42:53
 */
public interface Const {

    /**
     * 系统默认的管理员密码
     */
    String DEFAULT_PASSWORD = "111111";

    /**
     * 密码盐默认长度
     */
    Integer SALT_LENGTH = 5;

    /**
     * 店铺主账号默认角色
     */
    String SHOP_MASTER_ROLE = "11";

    /**
     * 酒店主账号默认角色
     */
    String HOTEL_MASTER_ROLE = "12";

    /**
     * 管理员角色的名字
     */
    String ADMIN_NAME = "开发者管理员";

    /**
     * 管理员id
     */
    Integer ADMIN_ID = 1;

    /**
     * 超级管理员角色id
     */
    Integer ADMIN_ROLE_ID = 1;

    /**
     * 接口文档的菜单名
     */
    String API_MENU_NAME = "接口文档";

    /**
     * 下单短信校验URL
     */
    String ORDER_CODE_URL = "/ordercode";

    /**
     * 图片上传最大限制3M
     */
    Long IMAGE_SIZE_MAX_LIMIT = 3 * 1024 * 1024L;

    /**
     * 退款订单相关操作限时天数
     */
    long ORDER_REFUND_DAYS = 7;

    /**
     * 订单超时时间
     */
    int ORDER_OUT_TIME = 3600;

    /**
     * 处理期限天数
     */
    int HANDLE_DAY_LIMIT = 7;

    /**
     * 房态管理查看天数
     */
    int ROOM_SETTING_SHOW_DAY_LIMIT = 15;

    /**
     * 房态管理可设置天数限制
     */
    int ROOM_CAN_SET_DAY_LIMIT = 90;


    /**
     * 微信订单退款回调地址
     */
    String WX_REFUND_NOTIFY = "/shop/pay/wechatrefundnotify";

    String SUCCESS = "SUCCESS";
    String FAIL = "FAIL";

}
