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
 * 平台管理店铺商品和酒店房间
 * </p>
 *
 * @author xxx
 * @since 2019-05-22
 */
@TableName("platform_manage")
public class PlatformManage extends Model<PlatformManage> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 商家ID
     */
    @TableField("shop_id")
    private Long shopId;
    /**
     * 特产店铺商品ID
     */
    @TableField("goods_id")
    private Long goodsId;
    /**
     * 酒店房间ID
     */
    @TableField("room_id")
    private Long roomId;
    /**
     * 平台管理，0为已下架，1为未下架
     */
    @TableField("platform_manage")
    private Integer platformManage;
    /**
     * 平台管理理由
     */
    @TableField("platform_manage_reason")
    private String platformManageReason;
    /**
     * 商家是否申请上架，0未申请，1已申请
     */
    @TableField("shop_apply")
    private Integer shopApply;
    /**
     * 商家申请理由
     */
    @TableField("shop_apply_reason")
    private String shopApplyReason;
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

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Integer getPlatformManage() {
        return platformManage;
    }

    public void setPlatformManage(Integer platformManage) {
        this.platformManage = platformManage;
    }

    public String getPlatformManageReason() {
        return platformManageReason;
    }

    public void setPlatformManageReason(String platformManageReason) {
        this.platformManageReason = platformManageReason;
    }

    public Integer getShopApply() {
        return shopApply;
    }

    public void setShopApply(Integer shopApply) {
        this.shopApply = shopApply;
    }

    public String getShopApplyReason() {
        return shopApplyReason;
    }

    public void setShopApplyReason(String shopApplyReason) {
        this.shopApplyReason = shopApplyReason;
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
        return "PlatformManage{" +
                "id=" + id +
                ", shopId=" + shopId +
                ", goodsId=" + goodsId +
                ", roomId=" + roomId +
                ", platformManage=" + platformManage +
                ", platformManageReason=" + platformManageReason +
                ", shopApply=" + shopApply +
                ", shopApplyReason=" + shopApplyReason +
                ", createdUser=" + createdUser +
                ", createdTime=" + createdTime +
                ", modifiedUser=" + modifiedUser +
                ", modifiedTime=" + modifiedTime +
                ", isDeleted=" + isDeleted +
                "}";
    }
}
