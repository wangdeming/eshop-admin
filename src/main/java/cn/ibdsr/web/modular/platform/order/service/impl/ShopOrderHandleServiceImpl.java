package cn.ibdsr.web.modular.platform.order.service.impl;

import cn.ibdsr.web.common.constant.state.IsDeleted;
import cn.ibdsr.web.common.constant.state.order.RefundStatus;
import cn.ibdsr.web.common.constant.state.order.ShopOrderStatus;
import cn.ibdsr.web.common.constant.state.shop.ShopType;
import cn.ibdsr.web.common.persistence.dao.GoodsMapper;
import cn.ibdsr.web.common.persistence.dao.GoodsSkuMapper;
import cn.ibdsr.web.common.persistence.dao.ShopOrderGoodsMapper;
import cn.ibdsr.web.common.persistence.dao.ShopOrderMapper;
import cn.ibdsr.web.common.persistence.dao.ShopOrderRefundMapper;
import cn.ibdsr.web.common.persistence.dao.ViewStatsMapper;
import cn.ibdsr.web.common.persistence.model.Goods;
import cn.ibdsr.web.common.persistence.model.GoodsSku;
import cn.ibdsr.web.common.persistence.model.ShopOrder;
import cn.ibdsr.web.common.persistence.model.ShopOrderGoods;
import cn.ibdsr.web.common.persistence.model.ShopOrderRefund;
import cn.ibdsr.web.common.persistence.model.ShopOrderSettlement;
import cn.ibdsr.web.common.persistence.model.ViewStats;
import cn.ibdsr.web.modular.platform.cash.dao.SystemCashDao;
import cn.ibdsr.web.modular.platform.cash.service.ICashTransferService;
import cn.ibdsr.web.modular.platform.cash.service.IProfitDistributionService;
import cn.ibdsr.web.modular.platform.order.service.IShopOrderHandleService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Description 订单处理Service
 * @Version V1.0
 * @CreateDate 2019-04-25 16:38:11
 * <p>
 * Date                     Author               Description
 * ----------------------------------------------------------
 * 2019-04-25 16:38:11    XuZhipeng               类说明
 */
@Service
public class ShopOrderHandleServiceImpl implements IShopOrderHandleService {

    @Autowired
    private ShopOrderMapper shopOrderMapper;

    @Autowired
    private ShopOrderRefundMapper shopOrderRefundMapper;

    @Autowired
    private SystemCashDao systemCashDao;

    @Autowired
    private ICashTransferService cashTransferService;

    @Autowired
    private IProfitDistributionService profitDistributionService;

    @Autowired
    private ShopOrderGoodsMapper shopOrderGoodsMapper;

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private GoodsSkuMapper goodsSkuMapper;

    @Autowired
    private ViewStatsMapper viewStatsMapper;

    /**
     * 订单支付超时，自动取消
     *
     * @param orderIdList 订单ID集合
     * @return
     */
    @Override
    public void cancelOrder(List<Long> orderIdList) throws Exception {
        if (null == orderIdList || 0 == orderIdList.size()) {
            throw new Exception("订单ID为空");
        }
        Date now = new Date();
        for (Long orderId : orderIdList) {
            ShopOrder shopOrder = shopOrderMapper.selectById(orderId);
            if (null == shopOrder) {
                throw new Exception("未查询到订单信息");
            }

            // 判断订单状态是否为待支付状态，才能取消
            if (ShopOrderStatus.WAIT_PAY.getCode() != shopOrder.getStatus()) {
                throw new Exception("当前订单状态无法取消");
            }
            shopOrder.setCancelTime(new Date());
            shopOrder.setCancelRemark("买家超时未完成支付款");
            shopOrder.setStatus(ShopOrderStatus.CANCEL.getCode());
            shopOrder.setModifiedTime(new Date());
            shopOrder.updateById();

            //订单取消，库存返还
            List<ShopOrderGoods> shopOrderGoodsList = shopOrderGoodsMapper.selectList(new EntityWrapper<ShopOrderGoods>().eq("order_id", orderId).eq("is_deleted", IsDeleted.NORMAL.getCode()));
            for (ShopOrderGoods r : shopOrderGoodsList) {
                if (r.getSkuId() == null) {
                    //无规格商品
                    Goods param = goodsMapper.selectById(r.getGoodsId());
                    if (param != null) {
                        Goods goods = new Goods();
                        goods.setId(r.getGoodsId());
                        goods.setStock(param.getStock() + r.getNums());
                        goods.setModifiedTime(now);
                        goods.updateById();
                    }
                } else {
                    //有规格商品
                    GoodsSku param = goodsSkuMapper.selectById(r.getSkuId());
                    if (param != null) {
                        GoodsSku goodsSku = new GoodsSku();
                        goodsSku.setId(r.getSkuId());
                        goodsSku.setStock(param.getStock() + r.getNums());
                        goodsSku.setModifiedTime(now);
                        goodsSku.updateById();
                    }
                }
            }
        }
    }

    /**
     * 订单结算
     *
     * @param orderId 订单ID
     * @return
     */
    @Transactional
    @Override
    public void settleOrder(Long orderId) throws Exception {
        if (null == orderId) {
            throw new Exception("订单ID为空");
        }
        ShopOrder shopOrder = shopOrderMapper.selectById(orderId);
        if (null == shopOrder) {
            throw new Exception("未查询到订单信息");
        }

        // 判断订单状态是否为用户已收货状态，才能结算
        if (ShopOrderStatus.RECEIVED.getCode() != shopOrder.getStatus()) {
            throw new Exception("当前订单状态无法进行结算");
        }

        // 查询在当前时间之前的7天内，该订单下的订单商品是否发起过售后服务，存在则不做处理
        List<ShopOrderRefund> refundList = shopOrderRefundMapper.selectList(
                new EntityWrapper<ShopOrderRefund>()
                        .eq("order_id", orderId)
                        .where("created_time >= DATE_SUB(NOW(), INTERVAL 7 DAY)")
//                        .where("created_time >= DATE_SUB(NOW(), INTERVAL 5 MINUTE)")
        );
        if (null != refundList && 0 < refundList.size()) {
            throw new Exception("当前订单存在售后服务无法进行结算");
        }

        // 查询订单完成退款总金额
        BigDecimal refundAmount = systemCashDao.getOrderRefundAmount(orderId);
        if (null == refundAmount) {
            refundAmount = BigDecimal.ZERO;
        }
        // 待到账金额
        BigDecimal accAmount = shopOrder.getOrderPrice().subtract(refundAmount);
        // 获取店铺服务费率
        BigDecimal serviceRate = profitDistributionService.getServiceRateByShopId(shopOrder.getShopId());
        // 服务费 = （订单金额 - 退款金额）* 服务费率
        BigDecimal serviceFee = accAmount.multiply(serviceRate);
        // 计算结算金额 = 订单金额 - 退款金额 - 服务费
        BigDecimal settleAmount = accAmount.subtract(serviceFee);

        // 1.插入订单结算信息
        ShopOrderSettlement orderSettlement = new ShopOrderSettlement();
        orderSettlement.setOrderId(String.valueOf(orderId));
        orderSettlement.setOrderAmount(shopOrder.getOrderPrice());
        orderSettlement.setRefundAmount(refundAmount);
        orderSettlement.setServiceRate(serviceRate);
        orderSettlement.setServiceFee(serviceFee);
        orderSettlement.setSettleAmount(settleAmount);
        orderSettlement.setCreatedTime(new Date());
        orderSettlement.insert();

        // 2.资金变动：待到账金额-（shop_order_cash_flow）；店铺余额+（shop_balance_flow）
        cashTransferService.orderSettleTransfer(shopOrder.getShopId(), String.valueOf(orderId), String.valueOf(orderSettlement.getId()), accAmount, settleAmount, ShopType.STORE.getCode());

        // 3.订单状态改为8-已结算
        shopOrder.setStatus(ShopOrderStatus.COMPLETED.getCode());
        shopOrder.setModifiedTime(new Date());
        shopOrder.updateById();

        try {
            //统计商品销量
            List<ShopOrderGoods> shopOrderGoodsList = shopOrderGoodsMapper.selectList(new EntityWrapper<ShopOrderGoods>().eq("is_deleted", IsDeleted.NORMAL.getCode()).eq("order_id", orderId));
            for (ShopOrderGoods shopOrderGoods : shopOrderGoodsList) {
                List<ShopOrderRefund> shopOrderRefundList = shopOrderRefundMapper.selectList(new EntityWrapper<ShopOrderRefund>().eq("order_id", orderId).eq("order_goods_id", shopOrderGoods.getGoodsId()).eq("status", RefundStatus.SUCCESS.getCode()));
                List<ViewStats> viewStatsList = viewStatsMapper.selectList(new EntityWrapper<ViewStats>().eq("is_deleted", IsDeleted.NORMAL.getCode()).eq("goods_id", shopOrderGoods.getGoodsId()));
                if (viewStatsList.size() > 0) {
                    ViewStats viewStats = new ViewStats();
                    viewStats.setId(viewStatsList.get(0).getId());
                    viewStats.setSaleNum(shopOrderGoods.getNums() - (shopOrderRefundList.size() == 0 ? 0 : shopOrderRefundList.get(0).getGoodsNum()));
                    viewStats.setModifiedTime(new Date());
                    viewStats.updateById();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
