package cn.ibdsr.web.common.persistence.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 消息处理记录表
 * </p>
 *
 * @author XuZhipeng
 * @since 2019-05-05
 */
@TableName("message_queue_process_record")
public class MessageQueueProcessRecord extends Model<MessageQueueProcessRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * 自增ID
     */
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 消息ID
     */
	@TableField("message_id")
	private String messageId;
    /**
     * 消息体内容
     */
	@TableField("message_content")
	private String messageContent;
    /**
     * 参数数据
     */
	@TableField("param_data")
	private String paramData;
    /**
     * 交换机名称
     */
	private String exchange;
    /**
     * 路由键名称
     */
	@TableField("route_key")
	private String routeKey;
    /**
     * 状态（-2-未发送到交换机,消息丢失；-1-交换机未绑定队列，消息丢失；1-待处理；2-已成功处理；3-发现异常未处理；）
     */
	private Integer status;
    /**
     * 备注信息
     */
	private String remark;
    /**
     * 创建时间
     */
	@TableField("created_time")
	private Date createdTime;
    /**
     * 处理时间
     */
	@TableField("handle_time")
	private Date handleTime;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getMessageContent() {
		return messageContent;
	}

	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}

	public String getParamData() {
		return paramData;
	}

	public void setParamData(String paramData) {
		this.paramData = paramData;
	}

	public String getExchange() {
		return exchange;
	}

	public void setExchange(String exchange) {
		this.exchange = exchange;
	}

	public String getRouteKey() {
		return routeKey;
	}

	public void setRouteKey(String routeKey) {
		this.routeKey = routeKey;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Date getHandleTime() {
		return handleTime;
	}

	public void setHandleTime(Date handleTime) {
		this.handleTime = handleTime;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "MessageQueueProcessRecord{" +
			"id=" + id +
			", messageId=" + messageId +
			", messageContent=" + messageContent +
			", paramData=" + paramData +
			", exchange=" + exchange +
			", routeKey=" + routeKey +
			", status=" + status +
			", remark=" + remark +
			", createdTime=" + createdTime +
			", handleTime=" + handleTime +
			"}";
	}
}
