package cn.ibdsr.web.modular.shop.order.service;

/**
 * @Description 订单售后管理Service
 * @Version V1.0
 * @CreateDate 2019-04-04 15:19:05
 *
 * Date                     Author               Description
 * ----------------------------------------------------------
 * 2019-04-04 15:19:05    XuZhipeng               类说明
 *
 */
public interface IOrderRefundService {

    /**
     * 审核通过
     *
     * @param refundId 退款订单ID
     * @param reviewRemark 审核说明
     * @param shopAddrId 店铺收货地址
     */
    void pass(Long refundId, String reviewRemark, Long shopAddrId) throws Exception;

    /**
     * 审核不通过
     *
     * @param refundId 退款订单ID
     * @param reviewRemark 审核说明
     */
    void refusePass(Long refundId, String reviewRemark);
}
