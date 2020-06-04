package cn.ibdsr.web.modular.shop.order.transfer;

/**
 * @Description 商品信息VO
 * @Version V1.0
 * @CreateDate 2019-04-25 18:29:38
 *
 * Date                     Author               Description
 * ----------------------------------------------------------
 * 2019-04-25 18:29:38     ZhuJingrui             类说明
 */
public class GoodsVO {

    /**
     * 商品ID
     */
    private Long goodsId;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 商品图片
     */
    private String goodsImg;

    /**
     * 商品规格信息
     */
    private String goodsSpecs;

    /**
     * 购买数量
     */
    private Integer num;

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsImg() {
        return goodsImg;
    }

    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
    }

    public String getGoodsSpecs() {
        return goodsSpecs;
    }

    public void setGoodsSpecs(String goodsSpecs) {
        this.goodsSpecs = goodsSpecs;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}
