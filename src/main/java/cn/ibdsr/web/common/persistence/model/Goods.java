package cn.ibdsr.web.common.persistence.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 商品（特产）基本信息表
 * </p>
 *
 * @author XuZhipeng
 * @since 2019-03-19
 */
public class Goods extends Model<Goods> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 店铺ID
     */
	@TableField("shop_id")
	private Long shopId;
    /**
     * 商品类别ID
     */
	@TableField("category_id")
	private Long categoryId;
    /**
     * 商品名称
     */
	private String name;
    /**
     * 商品主图路径
     */
	@TableField("main_img")
	private String mainImg;
    /**
     * 状态（1-上架；2-下架；）
     */
	private Integer status;
    /**
     * 价格（单位：分）
     */
	private BigDecimal price;
    /**
     * 参考价（划线价，单位：分）
     */
	@TableField("refer_price")
	private BigDecimal referPrice;
    /**
     * 库存数量
     */
	private Integer stock;
    /**
     * 配送方式（1-快递发货；）
     */
	@TableField("delivery_type")
	private Integer deliveryType;
    /**
     * 快递运费（单位：分）
     */
	@TableField("express_fee")
	private BigDecimal expressFee;
    /**
     * 规格集合（格式：{"包装":["1KG","2KG","3KB"],"口味":["香辣","麻辣","微辣"]}）
     */
	@TableField("specs_list")
	private String specsList;
    /**
     * 序列号
     */
	private Integer sequence;
    /**
     * 上架时间
     */
	@TableField("onshelf_time")
	private Date onshelfTime;
    /**
     * 下架时间
     */
	@TableField("offshelf_time")
	private Date offshelfTime;
    /**
     * 平台管理，0为已下架，1为未下架
     */
    @TableField("platform_manage")
    private Integer platformManage;
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

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMainImg() {
		return mainImg;
	}

	public void setMainImg(String mainImg) {
		this.mainImg = mainImg;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getReferPrice() {
		return referPrice;
	}

	public void setReferPrice(BigDecimal referPrice) {
		this.referPrice = referPrice;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public Integer getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(Integer deliveryType) {
		this.deliveryType = deliveryType;
	}

	public BigDecimal getExpressFee() {
		return expressFee;
	}

	public void setExpressFee(BigDecimal expressFee) {
		this.expressFee = expressFee;
	}

	public String getSpecsList() {
		return specsList;
	}

	public void setSpecsList(String specsList) {
		this.specsList = specsList;
	}

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public Date getOnshelfTime() {
		return onshelfTime;
	}

	public void setOnshelfTime(Date onshelfTime) {
		this.onshelfTime = onshelfTime;
	}

	public Date getOffshelfTime() {
		return offshelfTime;
	}

    public void setOffshelfTime(Date offshelfTime) {
        this.offshelfTime = offshelfTime;
    }

    public Integer getPlatformManage() {
        return platformManage;
    }

    public void setPlatformManage(Integer platformManage) {
        this.platformManage = platformManage;
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
		return "Goods{" +
			"id=" + id +
			", shopId=" + shopId +
			", categoryId=" + categoryId +
			", name=" + name +
			", mainImg=" + mainImg +
			", status=" + status +
			", price=" + price +
			", referPrice=" + referPrice +
			", stock=" + stock +
			", deliveryType=" + deliveryType +
			", expressFee=" + expressFee +
			", specsList=" + specsList +
			", sequence=" + sequence +
			", onshelfTime=" + onshelfTime +
			", offshelfTime=" + offshelfTime +
                ", platformManage=" + platformManage +
			", createdUser=" + createdUser +
			", createdTime=" + createdTime +
			", modifiedUser=" + modifiedUser +
			", modifiedTime=" + modifiedTime +
			", isDeleted=" + isDeleted +
			"}";
	}
}
