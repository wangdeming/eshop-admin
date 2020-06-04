package cn.ibdsr.web.modular.shop.goods.transfer;

import cn.ibdsr.core.base.dto.BaseDTO;
import cn.ibdsr.core.check.Verfication;

import java.math.BigDecimal;

/**
 * 商品列表查询DTO
 *
 * @author XuZhipeng
 * @Date 2019-03-04 15:26:18
 */
public class GoodsQueryDTO extends BaseDTO {

    /**
     * 商品状态（1-销售中；2-已售罄；3-仓库中；）
     */
    @Verfication(name = "商品状态（1-销售中；2-已售罄；3-仓库中；）", min = 1, max = 3)
    private Integer status;

    /**
     * 平台管理: 0为已下架，1为未下架
     */
    private Integer platformManage;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 最低价格
     */
    @Verfication(name = "最低价格", min = 0)
    private BigDecimal minPrice;

    /**
     * 最高价格
     */
    @Verfication(name = "最高价格", min = 0)
    private BigDecimal maxPrice;

    /**
     * 最低销量
     */
    @Verfication(name = "最低销量", min = 0)
    private Integer minSale;

    /**
     * 最高销量
     */
    @Verfication(name = "最高销量", min = 0)
    private Integer maxSale;

    /**
     * 店铺ID
     */
    private Long shopId;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(BigDecimal minPrice) {
        this.minPrice = minPrice;
    }

    public BigDecimal getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(BigDecimal maxPrice) {
        this.maxPrice = maxPrice;
    }

    public Integer getMinSale() {
        return minSale;
    }

    public void setMinSale(Integer minSale) {
        this.minSale = minSale;
    }

    public Integer getMaxSale() {
        return maxSale;
    }

    public void setMaxSale(Integer maxSale) {
        this.maxSale = maxSale;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Integer getPlatformManage() {
        return platformManage;
    }

    public void setPlatformManage(Integer platformManage) {
        this.platformManage = platformManage;
    }
}
