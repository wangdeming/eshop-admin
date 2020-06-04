package cn.ibdsr.web.modular.platform.cash.transfer;

/**
 * @Description: 提现列表查询DTO
 * @Version: V1.0
 * @CreateDate: 2019-04-22 16:53:11
 *
 * Date                     Author               Description
 * ----------------------------------------------------------
 * 2019-04-22 16:53:11     XuZhipeng               类说明
 *
 */
public class WithdrawalQueryDTO extends QueryDTO {

    /**
     * 提现状态（1-待审核；2-审核通过待打款；3-提现成功；4-审核不通过；）
     */
    private Integer wdStatus;

    public Integer getWdStatus() {
        return wdStatus;
    }

    public void setWdStatus(Integer wdStatus) {
        this.wdStatus = wdStatus;
    }
}
