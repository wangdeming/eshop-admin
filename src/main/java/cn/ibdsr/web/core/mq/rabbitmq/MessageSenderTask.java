package cn.ibdsr.web.core.mq.rabbitmq;


import cn.ibdsr.web.common.constant.state.DelayTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 消息发送服务
 * @Version V1.0
 * @CreateDate 2019-04-26 08:31:22
 * <p>
 * Date                     Author               Description
 * ----------------------------------------------------------
 * 2019-04-26 08:31:22    XuZhipeng               类说明
 */
@Component
public class MessageSenderTask {

    @Autowired
    private MessageSender messageSender;

    /**
     * 处理订单支付超时，1小时后自动取消
     *
     * @param shopOrderIdList 订单Id集合
     */
    public void sendMsgOfCancelOrder(List<Long> shopOrderIdList) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("delayTask", DelayTask.SHOP_ORDER_CANCEL.getCode());
        paramMap.put("orderIdList", shopOrderIdList);
        messageSender.sendMsgTTLOneHour("订单支付超时，自动取消", paramMap);
//        messageSender.sendMsgTTLFiveMinute("订单支付超时，自动取消", paramMap);
    }

    /**
     * 处理特产店铺订单7天内未发起售后，自动结算
     *
     * @param orderId 订单ID
     */
    public void sendMsgOfSettleShopOrder(Long orderId) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("delayTask", DelayTask.SHOP_ORDER_SETTLE.getCode());
        paramMap.put("orderId", orderId);
        messageSender.sendMsgTTLSevenDay("订单完成，自动结算", paramMap);
//        messageSender.sendMsgTTLFiveMinute("订单完成，自动结算", paramMap);
    }

    /**
     * 审核通过后，用户需要在7天内完成退货物流的填写，否则系统帮助用户撤销申请。
     *
     * @param shopOrderRefundId shopOrderRefundId
     */
    public void sendMsgOfRevokeShopOrderRefund(Long shopOrderRefundId) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("delayTask", DelayTask.SHOP_ORDER_REVOKE.getCode());
        paramMap.put("shopOrderRefundId", shopOrderRefundId);
        messageSender.sendMsgTTLSevenDay("用户未在规定时间填写物流信息，系统撤销售后申请。", paramMap);
//        messageSender.sendMsgTTLFiveMinute("用户未在规定时间填写物流信息，系统撤销售后申请。", paramMap);
    }

    /**
     * 处理酒店已消费订单7天后自动结算
     *
     * @param orderId 订单ID
     */
    public void sendMsgOfSettleHotelOrder(String orderId) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("delayTask", DelayTask.HOTEL_ORDER_SETTLE.getCode());
        paramMap.put("orderId", orderId);
        messageSender.sendMsgTTLSevenDay(DelayTask.HOTEL_ORDER_SETTLE.getMessage(), paramMap);
//        messageSender.sendMsgTTLFiveMinute(DelayTask.HOTEL_ORDER_SETTLE.getMessage(), paramMap);
    }

}
