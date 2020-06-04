/**
 * Copyright (c) 2015-2020, ShangRao Institute of Big Data co.,LTD and/or its
 * affiliates. All rights reserved.
 */
package cn.ibdsr.web.modular.platform.adinfomanager.transfer;

import java.util.Date;

/**
 * @Description: 类功能和用法的说明
 * @Version: V1.0
 * @CreateDate: 2019/4/4 9:09
 *
 * Date           Author               Description
 * ------------------------------------------------------
 * 2019/4/4      Zhujingrui               类说明
 *
 */
public class AdInfoVO {
    /**
     * 主键ID
     */
    private Long id;

    /**
     * 广告位置ID
     */
    private Long positionId;

    /**
     * 关联类型（1-商品；2-店铺；3-URL；4-无关联；）
     */
    private Integer type;

    /**
     * 关联值（根据type类型认定）
     */
    private String relationVal;

    /**
     * 图片
     */
    private String img;

    /**
     * 排序号
     */
    private Integer sequence;

    /**
     * 状态（1-已发布；2-创建；3-下线；）
     */
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
     * 是否删除
     */
    private Integer isDeleted;

    public Long getId() {
        return id;
    }

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

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }
}
