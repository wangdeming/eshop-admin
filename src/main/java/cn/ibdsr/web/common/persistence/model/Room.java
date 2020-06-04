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
 * 房间
 * </p>
 *
 * @author XXX
 * @since 2019-05-07
 */
public class Room extends Model<Room> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 酒店ID
     */
    @TableField("shop_id")
    private Long shopId;
    /**
     * 名称
     */
    private String name;
    /**
     * 房间主图路径
     */
    @TableField("main_img")
    private String mainImg;
    /**
     * 早餐，0无早餐，1单人早餐，2双人早餐
     */
    private Integer breakfast;
    /**
     * 宽带，0无宽带，1有线宽带，2WIFI，3WIFI、有线宽带
     */
    private Integer broadband;
    /**
     * 窗户，0无窗，1有窗
     */
    private Integer window;
    /**
     * 面积，以㎡为单位
     */
    private Integer area;
    /**
     * 床宽，以m为单位
     */
    @TableField("bed_width")
    private BigDecimal bedWidth;
    /**
     * 可住多少人
     */
    private Integer person;
    /**
     * 价格，0-100000元之间，以分为单位
     */
    private BigDecimal price;
    /**
     * 取消方式，0不可取消，1免费取消（当天14：00前可取消）
     */
    @TableField("can_cancel")
    private Integer canCancel;
    /**
     * 状态，0未上架，1已上架
     */
    private Integer status;
    /**
     * 序列号
     */
    private Integer sequence;
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

    public Integer getBreakfast() {
        return breakfast;
    }

    public void setBreakfast(Integer breakfast) {
        this.breakfast = breakfast;
    }

    public Integer getBroadband() {
        return broadband;
    }

    public void setBroadband(Integer broadband) {
        this.broadband = broadband;
    }

    public Integer getWindow() {
        return window;
    }

    public void setWindow(Integer window) {
        this.window = window;
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public BigDecimal getBedWidth() {
        return bedWidth;
    }

    public void setBedWidth(BigDecimal bedWidth) {
        this.bedWidth = bedWidth;
    }

    public Integer getPerson() {
        return person;
    }

    public void setPerson(Integer person) {
        this.person = person;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getCanCancel() {
        return canCancel;
    }

    public void setCanCancel(Integer canCancel) {
        this.canCancel = canCancel;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
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
        return "Room{" +
                "id=" + id +
                ", shopId=" + shopId +
                ", name=" + name +
                ", mainImg=" + mainImg +
                ", breakfast=" + breakfast +
                ", broadband=" + broadband +
                ", window=" + window +
                ", area=" + area +
                ", bedWidth=" + bedWidth +
                ", person=" + person +
                ", price=" + price +
                ", canCancel=" + canCancel +
                ", status=" + status +
                ", sequence=" + sequence +
                ", platformManage=" + platformManage +
                ", createdUser=" + createdUser +
                ", createdTime=" + createdTime +
                ", modifiedUser=" + modifiedUser +
                ", modifiedTime=" + modifiedTime +
                ", isDeleted=" + isDeleted +
                "}";
    }
}
