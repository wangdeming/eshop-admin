/**
 * Copyright (c) 2015-2020, ShangRao Institute of Big Data co.,LTD and/or its
 * affiliates. All rights reserved.
 */
package cn.ibdsr.web.modular.platform.adinfomanager.transfer;

import cn.ibdsr.core.base.dto.BaseDTO;
import cn.ibdsr.core.check.Verfication;

import java.util.Date;

/**
 * @Description: 类功能和用法的说明
 * @Version: V1.0
 * @CreateDate: 2019/4/3 14:54
 *
 * Date           Author               Description
 * ------------------------------------------------------
 * 2019/4/3      Zhujingrui               类说明
 *
 */
public class AdInfoDTO extends BaseDTO {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 广告位置ID:（1-homePageAd-平台首页轮播广告；2-recommendShopAd-推荐店铺广告；3-shopGoodsAd-特产首页轮播广告）
     */
    @Verfication(name = "广告位置ID", notNull = true, min = 1, max = 3)
    private Long positionId;

    /**
     * 关联类型（1-商品；2-店铺；3-URL；4-无关联；）
     */
    @Verfication(name = "关联类型", notNull = true, min = 1, max = 4)
    private Integer type;

    /**
     * 关联值（根据type类型认定）
     */
    private String relationVal;

    /**
     * 图片
     */
    @Verfication(name = "图片", notNull = true)
    private String img;

    /**
     * 排序号
     */
    @Verfication(name = "排序", notNull = true)
    private Integer sequence;

    /**
     * 状态（1-已发布；2-创建；3-下线；）
     */
//    @Verfication(name = "状态", notNull = true, min = 1, max = 3)
    private Integer status;

    /**
     * 发布时间（生效时间）
     */
    private Date publishTime;

    /**
     * 创建时间
     */
    private Date createdTime;

    /**
     * 修改时间
     */
    private Date modifiedTime;

    /**
     * 创建用户
     */
    private Long createdUser;

    /**
     * 修改用户
     */
    private Long modifiedUser;

    /**
     * 是否删除:0-未删除；1-已删除
     */
    private Integer isDeleted;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Long getPositionId() {
        return positionId;
    }

    public void setPositionId(Long positionId) {
        this.positionId = positionId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getRelationVal() {
        return relationVal;
    }

    public void setRelationVal(String relationVal) {
        this.relationVal = relationVal;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public Long getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(Long createdUser) {
        this.createdUser = createdUser;
    }

    public Long getModifiedUser() {
        return modifiedUser;
    }

    public void setModifiedUser(Long modifiedUser) {
        this.modifiedUser = modifiedUser;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }
}
