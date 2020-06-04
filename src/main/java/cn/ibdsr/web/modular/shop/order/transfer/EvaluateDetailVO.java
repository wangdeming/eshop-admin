package cn.ibdsr.web.modular.shop.order.transfer;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description 订单评价详情VO
 * @Version V1.0
 * @CreateDate 2019-04-26 08:51:33
 *
 * Date                     Author               Description
 * ----------------------------------------------------------
 * 2019-04-26 08:51:33    ZhuJingrui               类说明
 *
 */
public class EvaluateDetailVO {

    /**
     * 订单ID
     */
    private Long id;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 评价商品信息集合
     */
    private List<Map<String, Object>> evalGoodsList;

    /**
     * 服务评分
     */
    private Integer serviceScore;

    /**
     * 物流评分
     */
    private Integer expressScore;

    /**
     * 创建时间（评价时间）
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createdTime;

    public List<Map<String, Object>> getEvalGoodsList() {
        return evalGoodsList;
    }

    public void setEvalGoodsList(List<Map<String, Object>> evalGoodsList) {
        this.evalGoodsList = evalGoodsList;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
}
