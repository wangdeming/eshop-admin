package cn.ibdsr.web.modular.shop.account.transfer;


/**
 * @Description 短信验证码通用模块
 * @Author taoll
 * @Date created in 2019/1/18 10:32
 * @Modifed by
 */
public class SmsgDto {
    private static final long serialVersionUID = 1L;
    // //验证码,0标识成功，其它标识失败。
    private Integer code;

    private Data data;

    private String message;


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
