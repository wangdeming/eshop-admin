package cn.ibdsr.web.core.mq.rabbitmq;

import cn.ibdsr.web.common.constant.state.DelayTask;
import cn.ibdsr.web.common.constant.state.MessageQueueHandleStatus;
import cn.ibdsr.web.common.persistence.dao.MessageQueueProcessRecordMapper;
import cn.ibdsr.web.common.persistence.model.MessageQueueProcessRecord;
import cn.ibdsr.web.modular.platform.order.service.IShopOrderHandleService;
import cn.ibdsr.web.modular.shop.hotel.service.HotelOrderService;
import cn.ibdsr.web.modular.shop.order.service.ShopOrderRefundService;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description 消息消费者
 * @Version V1.0
 * @CreateDate 2019-04-25 08:31:22
 * <p>
 * Date                     Author               Description
 * ----------------------------------------------------------
 * 2019-04-25 08:31:22    XuZhipeng               类说明
 */
@Component
public class MessageReceiver {

    private final static Logger log = LoggerFactory.getLogger(MessageReceiver.class);

    @Autowired
    private IShopOrderHandleService shopOrderHandleService;

    @Autowired
    private ShopOrderRefundService shopOrderRefundService;

    @Autowired
    private MessageQueueProcessRecordMapper messageQueueProcessRecordMapper;

    @Autowired
    private HotelOrderService hotelOrderService;

    /**
     * 监听队列
     */
    private final String queue = "handle_dlx_queue";

    /**
     * 交换机
     */
    private final String exchange = "handle_dlx_exchange";

    /**
     * 监听handle_dlx_queue队列，接收消息
     *
     * @param message 消息体
     * @param channel 通信通道
     * @throws Exception
     */
    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(value = queue, durable = "true"),
                    exchange = @Exchange(value = exchange, durable = "true", type = "topic"),
                    key = "dlx.#"
            )
    )
    public void receiveMsg(Message message, Channel channel) throws Exception {
        log.info("消息内容: {}", message.getPayload());
        // 参数
        Map<String, Object> headers = message.getHeaders();

        Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);

        // messageId
        String messageId = (String) headers.get("messageId");

        try {
            Object delayTask = headers.get("delayTask");
            if (null == delayTask) {
                // 延迟任务不匹配，丢弃这条消息
                channel.basicNack(deliveryTag, false, false);
                return;
            }

            int delayTaskCode = (int) delayTask;
            if (DelayTask.SHOP_ORDER_CANCEL.getCode() == delayTaskCode) {
                // 1.订单取消
                List<Long> orderIdList = (List<Long>) headers.get("orderIdList");
                shopOrderHandleService.cancelOrder(orderIdList);
            } else if (DelayTask.SHOP_REFUND_PASS.getCode() == delayTaskCode) {
                // 2.商家7天内未审核的售后订单自动审核通过
                Long shopOrderRefundId = (Long) headers.get("shopOrderRefundId");
                shopOrderRefundService.passRefund(shopOrderRefundId);
            } else if (DelayTask.SHOP_ORDER_REVOKE.getCode() == delayTaskCode) {
                // 3.审核通过后，用户需要在7天内完成退货物流的填写，否则系统帮助用户撤销申请。
                Long shopOrderRefundId = (Long) headers.get("shopOrderRefundId");
                shopOrderRefundService.revoke(shopOrderRefundId);
            } else if (DelayTask.SHOP_CONFIRM_RECEIPT.getCode() == delayTaskCode) {
                // 4.用户填写退货物流后，商家需要在7天内，完成收货审核，否则系统帮助商家完成确认收获并退款。
                Long shopOrderRefundId = (Long) headers.get("shopOrderRefundId");
                Long shopOrderRefundLogisticsId = (Long) headers.get("shopOrderRefundLogisticsId");
                shopOrderRefundService.confirmReceipt(shopOrderRefundId, shopOrderRefundLogisticsId);
            } else if (DelayTask.SHOP_ORDER_SETTLE.getCode() == delayTaskCode) {
                // 5.订单结算
                Long orderId = (Long) headers.get("orderId");
                shopOrderHandleService.settleOrder(orderId);
            } else if (DelayTask.HOTEL_ORDER_CANCEL.getCode() == delayTaskCode) {
                //6.酒店订单超时未支付，自动取消
                String orderId = (String) headers.get("orderId");
                hotelOrderService.cancelOrder(orderId);
            } else if (DelayTask.HOTEL_ORDER_CONFIRM.getCode() == delayTaskCode) {
                //7.酒店订单超时未确认，自动确认
                String orderId = (String) headers.get("orderId");
                hotelOrderService.confirm(orderId);
            } else if (DelayTask.HOTEL_ORDER_SETTLE.getCode() == delayTaskCode) {
                //8.酒店已消费订单7天后结算
                String orderId = (String) headers.get("orderId");
                hotelOrderService.settleOrder(orderId);
            }

            // 更新消息状态：处理成功
            updateMessageStatus(messageId, MessageQueueHandleStatus.SUCCESS.getCode(), null);
            // 告诉服务器收到这条消息已经被我消费，可以在队列删掉
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            e.printStackTrace();
            // 更新消息状态：处理失败
            updateMessageStatus(messageId, MessageQueueHandleStatus.FAIL.getCode(), e.getMessage());
            // 丢弃这条消息
            channel.basicNack(deliveryTag, false, false);
        }
    }

    /**
     * 更新消息处理状态
     *
     * @param messageId  消息ID
     * @param statusCode 状态码
     * @param remark     备注信息
     */
    private void updateMessageStatus(String messageId, Integer statusCode, String remark) {
        if (null == messageId) {
            return;
        }
        MessageQueueProcessRecord processRecord = new MessageQueueProcessRecord();
        processRecord.setMessageId(messageId);
        processRecord = messageQueueProcessRecordMapper.selectOne(processRecord);
        if (null == processRecord) {
            return;
        }

        processRecord.setStatus(statusCode);
        processRecord.setRemark(remark);
        processRecord.setHandleTime(new Date());
        processRecord.updateById();
    }
}
