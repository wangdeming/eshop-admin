/**
 * Copyright (c) 2015-2020, ShangRao Institute of Big Data co.,LTD and/or its
 * affiliates. All rights reserved.
 */
package cn.ibdsr.web.modular.shop.goods.transfer;

import cn.ibdsr.core.base.dto.BaseDTO;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

import java.util.Date;

/**
 * @Description: 商品图片DTO
 * @Version: V1.0
 * @CreateDate: 2019/3/6 15:14
 *
 * Date           Author               Description
 * ------------------------------------------------------
 * 2019/3/6      Zhujingrui               类说明
 *
 */
public class GoodsImgDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 商品ID
     */
    private Long goodsId;

    /**
     * 图片路径
     */
    private String img;

    /**
     * 序列号
     */
    private Integer sequence;

    /**
     * 创建人
     */
    private Long createdUser;

    /**
     * 创建时间
     */
    private Date createdTime;

    /**
     * 是否删除（0-未删除；1-已删除）
     */
    private Integer isDeleted;

    /**
     * 标志操作状态：0-不改变；1-删除；2-新增
     */
    private Integer flag;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
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

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }
}
