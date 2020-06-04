package cn.ibdsr.web.modular.platform.shop.account.transfer;

import cn.ibdsr.core.base.dto.BaseDTO;
import cn.ibdsr.core.check.Verfication;

import java.util.List;

/**
 * 店铺账号操作DTO对象
 *
 * @author XuZhipeng
 * @Date 2019-02-26 09:26:17
 */
public class AccountOperDTO extends BaseDTO {

    /**
     * 店铺账户ID
     */
    private Long accountId;

    /**
     * 解冻或者冻结原因
     */
    @Verfication(name = "原因", maxlength = 500)
    private String reason;

    /**
     * 图片存储路径
     */
    private List<String> imgs;

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public List<String> getImgs() {
        return imgs;
    }

    public void setImgs(List<String> imgs) {
        this.imgs = imgs;
    }
}
