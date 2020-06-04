package cn.ibdsr.web.common.persistence.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 店铺商品评价表
 * </p>
 *
 * @author XuZhipeng
 * @since 2019-04-03
 */
@TableName("goods_evaluate")
public class GoodsEvaluate extends Model<GoodsEvaluate> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 订单商品ID
     */
	@TableField("order_goods_id")
	private Long orderGoodsId;
    /**
     * 订单ID
     */
	@TableField("order_id")
	private Long orderId;
    /**
     * 商品ID
     */
	@TableField("goods_id")
	private Long goodsId;
    /**
     * 顾客（用户/评论人）ID
     */
	@TableField("user_id")
	private Long userId;
    /**
     * 店铺ID
     */
	@TableField("shop_id")
	private Long shopId;
    /**
     * 评论内容
     */
	private String content;
    /**
     * 商品评分（1-5）
     */
	@TableField("goods_score")
	private Integer goodsScore;
    /**
     * 服务评分（1-5）
     */
	@TableField("service_score")
	private Integer serviceScore;
    /**
     * 物流快递评分（1-5）
     */
	@TableField("express_score")
	private Integer expressScore;
    /**
     * 评价类型（1-好评；2-中评；3-差评；）
     */
	private Integer type;
    /**
     * 评价图片（最多三张，多个用','隔开）
     */
	private String imgs;
    /**
     * 店铺回复
     */
	@TableField("shop_reply")
	private String shopReply;
    /**
     * 店铺回复时间
     */
	@TableField("reply_time")
	private Date replyTime;
    /**
     * 创建人
     */
	@TableField("created_user")
	private Long createdUser;
    /**
     * 创建时间
     */
	@TableField("created_time")
	private Date createdTime;
    /**
     * 修改人
     */
	@TableField("modified_user")
	private Long modifiedUser;
    /**
     * 修改时间
     */
	@TableField("modified_time")
	private Date modifiedTime;
    /**
     * 是否删除
     */
	@TableField("is_deleted")
	private Integer isDeleted;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOrderGoodsId() {
		return orderGoodsId;
	}

	public void setOrderGoodsId(Long orderGoodsId) {
		this.orderGoodsId = orderGoodsId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getGoodsScore() {
		return goodsScore;
	}

	public void setGoodsScore(Integer goodsScore) {
		this.goodsScore = goodsScore;
	}

	public Integer getServiceScore() {
		return serviceScore;
	}

	public void setServiceScore(Integer serviceScore) {
		this.serviceScore = serviceScore;
	}

	public Integer getExpressScore() {
		return expressScore;
	}

	public void setExpressScore(Integer expressScore) {
		this.expressScore = expressScore;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getImgs() {
		return imgs;
	}

	public void setImgs(String imgs) {
		this.imgs = imgs;
	}

	public String getShopReply() {
		return shopReply;
	}

	public void setShopReply(String shopReply) {
		this.shopReply = shopReply;
	}

	public Date getReplyTime() {
		return replyTime;
	}

	public void setReplyTime(Date replyTime) {
		this.replyTime = replyTime;
	}

	public Long getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(Long createdUser) {
		this.createdUser = createdUser;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Long getModifiedUser() {
		return modifiedUser;
	}

	public void setModifiedUser(Long modifiedUser) {
		this.modifiedUser = modifiedUser;
	}

	public Date getModifiedTime() {
		return modifiedTime;
	}

	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "GoodsEvaluate{" +
			"id=" + id +
			", orderGoodsId=" + orderGoodsId +
			", orderId=" + orderId +
			", goodsId=" + goodsId +
			", userId=" + userId +
			", shopId=" + shopId +
			", content=" + content +
			", goodsScore=" + goodsScore +
			", serviceScore=" + serviceScore +
			", expressScore=" + expressScore +
			", type=" + type +
			", imgs=" + imgs +
			", shopReply=" + shopReply +
			", replyTime=" + replyTime +
			", createdUser=" + createdUser +
			", createdTime=" + createdTime +
			", modifiedUser=" + modifiedUser +
			", modifiedTime=" + modifiedTime +
			", isDeleted=" + isDeleted +
			"}";
	}
}
