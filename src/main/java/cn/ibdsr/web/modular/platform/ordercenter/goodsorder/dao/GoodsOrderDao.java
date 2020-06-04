package cn.ibdsr.web.modular.platform.ordercenter.goodsorder.dao;

import cn.ibdsr.web.modular.platform.ordercenter.goodsorder.transfer.GoodsOrderQueryDTO;
import cn.ibdsr.web.modular.shop.order.transfer.OrderVO;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description 订单中心-特产订单Dao
 * @Version V1.0
 * @CreateDate 2019/5/23 11:32
 *
 * Date           Author               Description
 * ------------------------------------------------------
 * 2019/5/23      ZhuJingrui            类说明
 */
public interface GoodsOrderDao {
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
    List<OrderVO> orderList(@Param("page") Page page,
                            @Param("queryDTO") GoodsOrderQueryDTO queryDTO,
                            @Param("orderByField") String orderByField,
                            @Param("isAsc") Boolean isAsc);


}
