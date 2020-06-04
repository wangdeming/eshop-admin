package cn.ibdsr.web.common.exception;

/**
 * @author fengshuonan
 * @Description 所有业务异常的枚举
 * @date 2016年11月12日 下午5:04:51
 */
public enum BizExceptionEnum {

    DATA_ERROR(300, "数据错误"),
    PARAMETER_ERROR(301, "传参错误"),

    /**
     * 字典
     */
    DICT_EXISTED(400, "字典已经存在"),
    ERROR_CREATE_DICT(500, "创建字典失败"),
    ERROR_WRAPPER_FIELD(500, "包装字典属性失败"),


    /**
     * 文件上传
     */
    FILE_READING_ERROR(400, "FILE_READING_ERROR!"),
    FILE_NOT_FOUND(400, "FILE_NOT_FOUND!"),
    UPLOAD_ERROR(500, "上传图片出错"),
    IMAGE_FILE_TYPE_ERROR(400, "上传图片格式错误"),
    IMAGE_FILE_CONTENT_ERROR(400, "上传图片内容不正确"),
    IMAGE_UPLOAD_SIZE_TOO_BIG(400, "上传图片文件大小超过3M!"),


    /**
     * 权限和数据问题
     */
    DB_RESOURCE_NULL(400, "数据库中没有该资源"),
    NO_PERMITION(405, "权限异常"),
    REQUEST_INVALIDATE(400, "请求数据格式不正确"),
    INVALID_KAPTCHA(400, "验证码不正确"),
    CANT_DELETE_ADMIN(600, "不能删除超级管理员"),
    CANT_FREEZE_ADMIN(600, "不能冻结超级管理员"),
    CANT_CHANGE_ADMIN(600, "不能修改超级管理员角色"),

    /**
     * 账户问题
     */
    USER_ALREADY_REG(401, "该用户已经注册"),
    NO_THIS_USER(400, "没有此用户"),
    USER_NOT_EXISTED(400, "没有此用户"),
    ACCOUNT_FREEZED(401, "账号被冻结"),
    OLD_PWD_NOT_RIGHT(402, "原密码不正确"),
    TWO_PWD_NOT_MATCH(405, "两次输入密码不一致"),

    /**
     * 错误的请求
     */
    MENU_PCODE_COINCIDENCE(400, "菜单编号和副编号不能一致"),
    EXISTED_THE_MENU(400, "菜单编号重复，不能添加"),
    DICT_MUST_BE_NUMBER(400, "字典的值必须为数字"),
    REQUEST_NULL(400, "请求有错误"),
    SESSION_TIMEOUT(400, "会话超时"),
    SERVER_ERROR(500, "服务器异常"),

    /**
     * 店铺管理
     */
    SHOP_IS_NOT_EXIST(1000, "店铺不存在"),
    SHOP_ID_IS_NULL(1001, "店铺ID不能为空"),
    SHOP_NAME_IS_EXIST(1002, "店铺名称已存在"),
    SHOP_ACCOUNT_IS_EXIST(1003, "店铺账户名称已存在"),
    SHOP_IS_NOT_NOACCOUNT(1004, "该店铺已开通过账户"),
    SHOP_OFFICEPHONE_IS_NULL(1005, "营业电话和营业手机号码二选一必填"),
    SHOP_FRONT_NAME_IS_EXIST(1006, "店铺前台名称已存在"),

    /**
     * 账号问题
     */
    ORIGINAL_PASSWORD_ERROR(1100, "原密码错误"),
    PASSWORD_FORMAT_ERROR(1101, "密码格式错误，格式为长度为6-16字符，必须包含字母、数字、符号中至少两种，不包含空格。"),
    SMS_CODE_SEND_FILE(1102, "短信验证码发送失败"),
    MOBILE_FORMAT_ERROR(1103, "手机号格式错误"),
    SMS_CODE_ERROR(1104, "验证码错误"),
    ACCOUNT_REPEAT(1105, "账号重复"),
    ACCOUNT_FORMAT_ERROR(1106, "为避免和手机号混淆，账号不能为11位的数字。"),
    SUB_ACCOUNT_MAX(1107, "子账号数已达最大数20，不能再添加子账号。"),
    PASSWORD_REPEAT(1108, "新密码不能和旧密码一致"),
    PASSWORD_DEFAULT(1109, "新密码不能和初始密码一致"),

    /**
     * 地区区域
     */
    AREA_PID_IS_NULL(1200, "父地区ID不能为空"),

    /**
     * 特产类别
     */
    GOODS_CATEGORY_ID_IS_NULL(1300, "特产类别ID不能为空"),
    GOODS_CATEGORY_IS_NOT_EXIST(1301, "特产类别不存在"),
    GOODS_CATEGORY_PID_IS_NULL(1302, "特产父类别ID不能为空"),
    GOODS_CATEGORY_NAME_IS_NULL(1303, "特产类别名称不能为空"),
    GOODS_CATEGORY_NAME_NOT_FORMAT(1304, "类别名称只充许汉字或字母的组合，20个字以内"),
    GOODS_CATEGORY_NAME_IS_EXIST(1305, "特产类别名称已存在"),
    GOODS_CATEGORY_UP_ERROR(1306, "该特产类别无法进行上移！"),
    GOODS_CATEGORY_DOWN_ERROR(1307, "该特产类别无法进行下移！"),
    GOODS_CATEGORY_HAVE_CHILDREN(1308, "该特产类别存在子类别，不能删除！"),
    GOODS_SUB_CATEGORY_CANNOT_UPDATE(1309, "特产二级类别不能修改父"),
    GOODS_CATEGORY_FRONT_NAME_IS_NULL(1310, "特产类别前台名称不能为空"),
    GOODS_CATEGORY_FRONT_NAME_NOT_FORMAT(1311, "类别前台名称只充许汉字或字母的组合，2-6个字以内"),
    GOODS_CATEGORY_FRONT_NAME_IS_EXIST(1312, "特产类别前台名称已存在"),
    CATEGORY_HAS_GOODS(1313, "该特产类别已被商品绑定"),

    /**
     * 店铺账号管理
     */
    SHOP_ACCOUNT_ID_IS_NULL(1400, "店铺账号ID不能为空"),
    SHOP_ACCOUNT_IS_NOT_EXIST(1401, "店铺账号不存在"),
    SHOP_ACCOUNT_CANNOT_FREEZE(1402, "只有正常状态下的店铺账号才能被冻结"),
    SHOP_ACCOUNT_CANNOT_UNFREEZE(1403, "只有已冻结的店铺账号才能被解冻"),
    SHOP_ACCOUNT_CANNOT_CANCEL(1404, "只有已冻结的店铺账号才能被注销"),
    SHOP_ACCOUNT_NOT_FORMAT(1405, "账户名格式错误，要求：15个字符以内，允许为数字、字母的任意组合。"),

    /**
     * 角色管理
     */
    DELETE_ROLE_ERROR(1501, "删除角色失败"),
    IS_NOT_MYROLE(1502, "不是我所拥有的角色"),
    ROLE_IS_EXIST(1503, "角色已经存在"),
    ROLE_CHECK(1504, "角色名长度不能超过15个字！"),
    CANT_DELETE_ROLEHASEUSER(1505, "不能删除已有用户使用的角色"),
    IDS_IS_NULL(1505, "权限不能为空！"),

    /**
     * 商品
     */
    GOODS_CATEGORY_NOT_BLANK(1600, "商品分类不能为空"),
    GOODS_CATEGORY_NOT_EXIST(1601, "分类不存在"),
    GOODS_CATEGORY_SECOND(1602, "请选择二级分类"),
    GOODS_NAME_NOT_BLANK(1603, "商品名称不能为空"),
    GOODS_NAME_LENGTH(1604, "商品名称长度不能超过50"),
    GOODS_IMAGE_MIN(1605, "请上传商品图片"),
    GOODS_IMAGE_MAX(1606, "商品图片允许最多上传10张"),
    GOODS_SPECS_MAX(1607, "商品规格最多3项"),
    GOODS_SUB_SPECS_MAX(1608, "商品规格子项最多10项"),
    GOODS_PRICE_MIN_MAX(1609, "商品价格不能为空，且范围为0-100000元，请重新输入。"),
    GOODS_REFER_PRICE_MIN_MAX(16010, "商品划线价不能为空，且范围为0-100000元，请重新输入。"),
    GOODS_STOCK_MIN_MAX(16011, "商品库存不能为空，且范围为0-100000，请重新输入。"),
    GOODS_IS_NOT_EXIST(1610, "操作失败，商品信息不存在"),
    GOODS_ID_IS_NULL(1611, "请选择商品"),
    GOODS_SEQUENCE_IS_NULL(1612, "商品排序号不能为空"),
    GOODS_CATEGORY_SECOND_IS_NULL(1613, "商品的二级分类为空！"),
    GOODS_NAME_REPEAT(1614, "同商店商品名称不能重复"),
    GOODS_SKU_PRICE_MIN_MAX(1609, "商品规格价格不能为空，且范围为0-100000元，请重新输入。"),
    GOODS_SKU_REFER_PRICE_MIN_MAX(16010, "商品规格划线价不能为空，且范围为0-100000元，请重新输入。"),
    GOODS_SKU_STOCK_MIN_MAX(16011, "商品规格库存不能为空，且范围为0-100000，请重新输入。"),
    GOODS_PLATFORM_MANAGER_IS_NOT_EXIST(1610, "操作失败，商品的平台审核信息不存在！"),

    /**
     * 退款
     */
    ORDER_REFUND_NO_PAY_INFO(1701, "无支付信息"),
    REFUND_ID_IS_NULL(1702, "退款订单ID不能为空"),
    REFUND_ORDER_IS_NOT_EXIST(1703, "退款订单不存在"),
    REVIEW_REMARK_IS_NULL(1704, "审核说明不能为空"),
    REVIEW_REMARK_NOT_FORMAT(1705, "审核说明长度为200字符以内"),
    REFUND_ORDER_NOT_WAITING_REVIEW(1706, "退款订单只有是待审核状态下才能进行审核操作"),
    SHOP_DELIVERY_ADDR_ID_IS_NULL(1707, "店铺收货地址ID不能为空"),
    SHOP_DELIVERY_IS_NOT_EXIST(1708, "店铺收货地址不存在"),
    SHOP_REFUSE_RECEIPT_RESASON_BLANK(1709, "拒绝收货理由不能为空"),
    /**
     * 广告位管理
     */
    AD_NOT_EXIST(1801, "广告不存在"),
    AD_IS_PUBLISHING(1802, "广告处于发布状态中，不能删除！"),
    AD_CANNOT_PUBLISH(1803, "只有广告状态为正常时才能发布！"),
    AD_CANNOT_OFFSHELF(1804, "只有广告状态为上线时才能下线！"),
    SEQUENCE_CANNOT_EDIT(1805, "只有广告状态为发布时才能编辑排序！"),


    /**
     * 店铺服务费管理
     */
    PROFIT_DISTRIBUTION_ID_IS_NULL(1901, "收益分成ID不能为空"),
    PROFIT_DISTRIBUTION_IS_NOT_EXIST(1902, "收益分成信息不存在"),
    CHANGE_SERVICE_RATE_IS_NULL(1903, "变更后费率不能为空"),
    CHANGE_SERVICE_RATE_IS_ERROR(1904, "变更后费率范围：0~100"),
    CHANGE_SERRATE_CANNOT_EQUALS_SERRATE(1905, "变更后费率不能与当前费率一致"),
    EFFECTIVE_TIME_IS_NULL(1906, "生效时间不能为空"),
    EFFECTIVE_TIME_IS_ERROR(1907, "生效时间必须为明天开始一年内"),
    EFFECTIVE_TIME_IS_NOT_FORMAT(1908, "生效时间格式错误"),


    /**
     * 店铺地址管理
     */
    DEFAULT_ADDRESS_CAN_NOT_TO_NONDEFAULT(2001, "默认地址不可改成非默认地址"),
    SHOP_ADDRESS_ID_IS_NULL(2002, "店铺地址ID不能为空"),
    SHOP_ADDRESS_IS_NOT_EXIST(2003, "店铺地址不存在"),
    DEFAULT_ADDRESS_CAN_NOT_DELETE(2004, "默认地址不可删除"),
    SHOP_ADDRESS_ARRIVAL_CAP(2005, "店铺地址数量不能超过15个"),

    /**
     * 平台资金详情
     */
    QUERY_START_DATE_IS_NULL(2011, "查询开始时间不能为空"),
    QUERY_END_DATE_IS_NULL(2012, "查询结束时间不能为空"),

    /**
     * 提现管理
     */
    WITHDRAWAL_ID_IS_NULL(2021, "提现ID不能为空"),
    WITHDRAWAL_IS_NOT_EXIST(2022, "提现信息不存在"),
    WITHDRAWAL_CANNOT_REVIEW(2023, "提现单只有在待审核状态下，才能进行审核操作"),
    WITHDRAWAL_CANNOT_CONFIRM(2024, "提现单只有在审核通过状态下，才能进行确认打款操作"),
    WITHDRAWAL_AMOUNT_NOT_BLANK(2025, "提现金额不能为空"),
    WITHDRAWAL_AMOUNT_100(2026, "每次提现金额不得低于100元"),
    WITHDRAWAL_ACCOUNTNAME_NOT_BLANK(2027, "提现账户名称不能为空"),
    WITHDRAWAL_ACCOUNTNO_NOT_BLANK(2028, "提现账户不能为空"),
    WITHDRAWAL_AMOUNT_CAN_NOT_GREATER_THAN_BALANCE(2029, "提现金额超过最大余额"),

    /**
     * 订单管理
     */
    SHOP_DELIVERY_ADDRESS_NOT_EXIST(2031, "你还未设置退货地址，请设置后再发货!"),
    SHOP_ORDER_GOODS_IS_NOT_EXIST(2032, "订单商品不存在!"),
    SHOP_ORDER_GOODS_ID_IS_NULL(2033, "订单商品ID不能为空"),
    ORDER_SETTLEMENT_IS_NOT_EXIST(2034, "店铺订单结算不存在!"),
    SHOP_ORDER_IS_NOT_EXIST(2035, "店铺订单不存在!"),
    SHOP_ORDER_ID_IS_NULL(2036, "订单ID不能为空!"),

    /**
     * 酒店管理
     */
    ROOM_NAME_NOT_BLANK(2041, "房间名称不能为空"),
    ROOM_IMAGE_MIN(2042, "请上传房间图片"),
    ROOM_IMAGE_MAX(2043, "房间图片允许最多上传10张"),
    ROOM_PRICE_MIN_MAX(2044, "房间价格不能为空，且范围为1-100000的整数，请重新输入。"),
    ROOM_ID_IS_NULL(2045, "请选择房间"),
    ROOM_IS_NOT_EXIST(2046, "操作失败，房间信息不存在"),
    DATE_NULL(2047, "输入时间不能为空！"),
    PAST_CANNOT_SET(2408, "历史日期不能设置！"),
    AFTER_NINETY_CANNOT_SET(2409, "90天之后的房态不能设置！"),
    HOTEL_ORDER_ID_IS_NULL(2410, "酒店订单为空！"),
    HOTEL_ORDER_IS_NOT_EXIST(2411, "订单不存在！"),
    ORDER_STATUS_NOT_WAIT_CONFIRM(2412, "订单不是待确认状态，不能进行取消操作！"),
    ORDER_STATUS_NOT_WAIT_USED(2413, "订单不是待使用状态，不能进行确认入住操作！"),
    DATE_IS_NULL(2414, "在该时间段内没有符合您勾选的工作日的日期！"),
    ORDER_STATUS_NOT_WAIT_PAY(2415, "订单不是待支付状态，不能进行取消操作！"),
    ROOM_NAME_MAX(2416, "房间名称长度为10字以内！"),
    BEGIN_DATE_NOT_AFTER_END_DATE(2500, "开始时间不能大于结束时间"),
    REFUND_FAIL(2417, "退款失败"),
    ORDER_PAY_IS_NOT_OPEN(2418, "暂未开通该支付方式"),
    RERUND_PAY_IS_NOT_OPEN(2419, "暂为开通该退款方式"),
    ;

    BizExceptionEnum(int code, String message) {
        this.friendlyCode = code;
        this.friendlyMsg = message;
    }

    BizExceptionEnum(int code, String message, String urlPath) {
        this.friendlyCode = code;
        this.friendlyMsg = message;
        this.urlPath = urlPath;
    }

    private int friendlyCode;

    private String friendlyMsg;

    private String urlPath;

    public int getCode() {
        return friendlyCode;
    }

    public void setCode(int code) {
        this.friendlyCode = code;
    }

    public String getMessage() {
        return friendlyMsg;
    }

    public void setMessage(String message) {
        this.friendlyMsg = message;
    }

    public String getUrlPath() {
        return urlPath;
    }

    public void setUrlPath(String urlPath) {
        this.urlPath = urlPath;
    }

}
