package cn.ibdsr.web.core.mq.rabbitmq;


import cn.ibdsr.web.common.constant.state.MessageQueueHandleStatus;
import cn.ibdsr.web.common.persistence.model.MessageQueueProcessRecord;
import cn.ibdsr.web.core.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ReturnCallback;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

/**
 * @Description 消息生产者
 * @Version V1.0
 * @CreateDate 2019-04-25 08:31:22
 *
 * Date                     Author               Description
 * ----------------------------------------------------------
 * 2019-04-25 08:31:22    XuZhipeng               类说明
 *
 */
@Component
public class MessageSender {

    private final static Logger log = LoggerFactory.getLogger(MessageSender.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 回调函数: confirm确认
     */
    final ConfirmCallback confirmCallback = new ConfirmCallback() {
        @Override
        public void confirm(CorrelationData correlationData, boolean ack, String cause) {
            log.info("correlationData: {}", correlationData);
            log.info("ack: {}", ack);
        }
    };

    /**
     * 回调函数: return返回
     */
    final ReturnCallback returnCallback = new ReturnCallback() {
        @Override
        public void returnedMessage(org.springframework.amqp.core.Message message,
                                    int replyCode,
                                    String replyText,
                                    String exchange,
                                    String routingKey) {
            log.info("异常处理......");
        }
    };

    /**
     * 发送消息
     *
     * @param message 消息内容
     * @param msgHeaders 参数
     * @param exchange 交换机名称
     * @param routingKey 路由键
     * @throws Exception
     */
    public void sendMsg(String message, Map<String, Object> msgHeaders, String exchange, String routingKey) {
        // 定义一个全局唯一标识
        String messageId = CommonUtils.getUUID32();
        msgHeaders.put("messageId", messageId);
        CorrelationData correlationData = new CorrelationData(messageId);

        MessageHeaders mhs = new MessageHeaders(msgHeaders);
        Message msg = MessageBuilder.createMessage(message, mhs);

        rabbitTemplate.setConfirmCallback(confirmCallback);
        rabbitTemplate.setReturnCallback(returnCallback);

        // 添加消息记录
        addMessageRecord(messageId, message, msgHeaders, exchange, routingKey);

        rabbitTemplate.convertAndSend(exchange, routingKey, msg, correlationData);
    }

    /**
     * 添加消息记录
     *
     * @param messageId 消息ID
     * @param messageContent 消息内容
     * @param msgHeaders 参数
     */
    private void addMessageRecord(String messageId, String messageContent, Map<String, Object> msgHeaders, String exchange, String routingKey) {
        MessageQueueProcessRecord processRecord = new MessageQueueProcessRecord();
        processRecord.setMessageId(messageId);
        processRecord.setMessageContent(messageContent);
        processRecord.setParamData(msgHeaders.toString());
        processRecord.setExchange(exchange);
        processRecord.setRouteKey(routingKey);
        processRecord.setStatus(MessageQueueHandleStatus.WAIT_HANDLE.getCode());
        processRecord.setCreatedTime(new Date());
        processRecord.insert();
    }

    /**
     * 发送消息，延迟7天
     *
     * @param message 消息内容
     * @param msgHeaders 参数
     * @throws Exception
     */
    public void sendMsgTTLSevenDay(String message, Map<String, Object> msgHeaders) {
        sendMsg(message, msgHeaders, "ttl7d_dlx_exchange", "dlx.7d");
    }

    /**
     * 发送消息，延迟1小时
     *
     * @param message 消息内容
     * @param msgHeaders 参数
     * @throws Exception
     */
    public void sendMsgTTLOneHour(String message, Map<String, Object> msgHeaders) {
        sendMsg(message, msgHeaders, "ttl1h_dlx_exchange", "dlx.1h");
    }

    /**
     * 发送消息，延迟1分钟
     *
     * @param message 消息内容
     * @param msgHeaders 参数
     * @throws Exception
     */
    public void sendMsgTTLOneMinute(String message, Map<String, Object> msgHeaders) {
        sendMsg(message, msgHeaders, "ttl1m_dlx_exchange", "dlx.1m");
    }

    /**
     * 发送消息，延迟5分钟
     *
     * @param message 消息内容
     * @param msgHeaders 参数
     * @throws Exception
     */
    public void sendMsgTTLFiveMinute(String message, Map<String, Object> msgHeaders) {
        sendMsg(message, msgHeaders, "ttl5m_dlx_exchange", "dlx.5m");
    }
}
