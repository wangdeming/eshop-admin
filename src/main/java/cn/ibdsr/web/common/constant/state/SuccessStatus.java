package cn.ibdsr.web.common.constant.state;

/**
 * @Description description
 * @Author Administrator.xiaorongsheng
 * @Date created in 2018/4/19 9:06
 * @Modifed by
 */
public enum SuccessStatus {
    //message形式
    CHANGE_PWD_SUCCESS("密码修改成功！"),
    COMF_VERIFCODE_SUCCESS("手机校验成功！"),
    CHANGE_PHONE_SUCCESS("手机号修改成功！"),
    UNBIND_QQ_SUCCESS("解除QQ绑定成功！"),
    UNBIND_WEIXIN_SUCCESS("解除微信绑定成功！"),
    DELETE_ROLE_SUCCESS("删除角色成功！"),

    //code+message形式
//    COMF_VERIFCODE_SUCCESS1(200,"手机校验成功！"),
    ;
    int code;
    String message;

    SuccessStatus(String message){
        this.message=message;
    }
    SuccessStatus(int code, String message) {
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
            for (SuccessStatus ms : SuccessStatus.values()) {
                if (ms.getCode() == value) {
                    return ms.getMessage();
                }
            }
            return "";
        }
    }

    @Override
    public String toString() {
        return this.message;
    }
}
