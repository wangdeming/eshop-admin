package cn.ibdsr.web.modular.shop.order.transfer;

import cn.ibdsr.core.check.Verfication;

import java.util.List;

/**
 * @Description 退款物流信息DTO
 * @Version V1.0
 * @CreateDate 2019-04-25 18:29:38
 *
 * Date                     Author               Description
 * ----------------------------------------------------------
 * 2019-04-25 18:29:38     ZhuJingrui             类说明
 */
public class LogisticsDTO {

    /**
     * 退款订单ID
     */
    @Verfication(name = "退款订单ID", notNull = true)
    private Long refundId;

    /**
     * 快递公司
     */
    @Verfication(name = "物流公司", notNull = true)
    private String expressCompany;

    /**
     * 物流单号
     */
    @Verfication(name = "物流单号", notNull = true)
    private String expressNo;

    /**
     * 物流说明
     */
    @Verfication(name = "物流说明", maxlength = 200)
    private String expressRemark;

    /**
     * 物流凭证图片
     */
    private List<String> imgList;

    public Long getRefundId() {
        return refundId;
    }

    public void setRefundId(Long refundId) {
        this.refundId = refundId;
    }

    public String getExpressCompany() {
        return expressCompany;
    }

    public void setExpressCompany(String expressCompany) {
        this.expressCompany = expressCompany;
    }

    public String getExpressNo() {
        return expressNo;
    }

    public void setExpressNo(String expressNo) {
        this.expressNo = expressNo;
    }

    public String getExpressRemark() {
        return expressRemark;
    }

    public void setExpressRemark(String expressRemark) {
        this.expressRemark = expressRemark;
    }

    public List<String> getImgList() {
        return imgList;
    }

    public void setImgList(List<String> imgList) {
        this.imgList = imgList;
    }
}
