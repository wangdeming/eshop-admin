package cn.ibdsr.web.modular.platform.ordercenter.goodsorder.service.impl;

import cn.ibdsr.web.core.util.AmountFormatUtil;
import cn.ibdsr.web.core.util.DateUtils;
import cn.ibdsr.web.core.util.ImageUtil;
import cn.ibdsr.web.modular.platform.ordercenter.goodsorder.dao.GoodsOrderDao;
import cn.ibdsr.web.modular.platform.ordercenter.goodsorder.transfer.GoodsOrderQueryDTO;
import cn.ibdsr.web.modular.shop.order.transfer.OrderGoodsVO;
import cn.ibdsr.web.modular.shop.order.transfer.OrderVO;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.ibdsr.web.modular.platform.ordercenter.goodsorder.service.GoodsOrderService;

import java.util.List;

/**
 * @Description 订单中心-特产订单Service
 * @Version V1.0
 * @CreateDate 2019/5/23 11:32
 *
 * Date           Author               Description
 * ------------------------------------------------------
 * 2019/5/23      ZhuJingrui           类说明
 */
@Service
public class GoodsOrderServiceImpl implements GoodsOrderService {

    @Autowired
    private GoodsOrderDao goodsOrderDao;

    /**
     * 订单查询
     * @param page 分页信息
     * @param queryDTO 商品订单查询对象DTO
     *      shopId 店铺id
     *      shopName 店铺名称
     *      orderNo 订单编号
     *      consigneePhone 收件人手机号
     *      consigneeName 收件人姓名
     *      servicePhone 售后人手机号
     *      createdTimeStart 下单时间查询起始时间
     *      createdTimeEnd 下单时间查询终止时间
     *      goodsName 商品名称
     *      goodsId 商品id
     *      orderStatus 查看订单的状态（0-全部；1-待付款；2-待发货；3-待收货；4-交易完成；5-已取消；6-售后中；7-交易关闭）
     * @return 返回订单列表
     */
    @Override
    public List<OrderVO> orderList(Page page, GoodsOrderQueryDTO queryDTO) {
        if (queryDTO.getCreatedTimeStart() != null && queryDTO.getCreatedTimeStart() != ""){
            queryDTO.setCreatedTimeStart(DateUtils.getStartTimeOfDay(queryDTO.getCreatedTimeStart()));
        }
        if (queryDTO.getCreatedTimeEnd() != null && queryDTO.getCreatedTimeEnd() != ""){
            queryDTO.setCreatedTimeEnd(DateUtils.getEndTimeOfDay(queryDTO.getCreatedTimeEnd()));
        }

        List<OrderVO> orderVOList = goodsOrderDao.orderList(page, queryDTO, page.getOrderByField(), page.isAsc());
        for (OrderVO orderVO: orderVOList){
            orderVO.setOrderPrice(AmountFormatUtil.amountFormat(orderVO.getOrderPrice()));
            orderVO.setExpressFee(AmountFormatUtil.amountFormat(orderVO.getExpressFee()));
            for (OrderGoodsVO orderGoodsVO: orderVO.getGoods()){
                orderGoodsVO.setGoodsImg(ImageUtil.setImageURL(orderGoodsVO.getGoodsImg()));
                orderGoodsVO.setUnitPrice(AmountFormatUtil.amountFormat(orderGoodsVO.getUnitPrice()));
            }
        }
        return orderVOList;
    }
}
