package cn.ibdsr.web.core.task;

import cn.ibdsr.web.common.constant.state.IsDeleted;
import cn.ibdsr.web.common.constant.state.hotel.HotelOrderStatus;
import cn.ibdsr.web.common.constant.state.order.ShopOrderStatus;
import cn.ibdsr.web.common.persistence.dao.DictMapper;
import cn.ibdsr.web.common.persistence.dao.HotelOrderMapper;
import cn.ibdsr.web.common.persistence.dao.ShopOrderGoodsMapper;
import cn.ibdsr.web.common.persistence.dao.ShopOrderMapper;
import cn.ibdsr.web.common.persistence.model.Dict;
import cn.ibdsr.web.common.persistence.model.HotelOrder;
import cn.ibdsr.web.common.persistence.model.ShopOrder;
import cn.ibdsr.web.common.persistence.model.ShopOrderGoods;
import cn.ibdsr.web.core.util.DateUtils;
import cn.ibdsr.web.core.util.LogisticsUtils;
import cn.ibdsr.web.modular.shop.order.service.ShopOrderService;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @Description: 类功能和用法的说明
 * @Version: V1.0
 * @CreateUser: xujincai
 * @CreateDate: 2019/5/31 15:39
 * <p>
 * Date Author Description
 * ------------------------------------------------------
 * 2019/5/31 xujincai init
 */
@Component
public class ScheduledTasks {

    @Value(value = "${spring.redis.database}")
    private int redisDatabase;

    @Autowired
    private ShopOrderMapper shopOrderMapper;

    @Autowired
    private ShopOrderGoodsMapper shopOrderGoodsMapper;

    @Autowired
    private HotelOrderMapper hotelOrderMapper;

    @Autowired
    private DictMapper dictMapper;

    @Autowired
    private ShopOrderService shopOrderService;

    /**
     * 判断酒店订单是否已过期，状态为待使用且当前时间超过离店时间即为已过期，已过期则设置订单为已过期
     */
    @Scheduled(cron = "1 0 0 * * *")
    public void setHotelOrderExpire() {
        if (redisDatabase == 1) {//商家端启用定时器，防止平台端和商家端同时启用定时器
            List<HotelOrder> hotelOrderList = hotelOrderMapper.selectList(new EntityWrapper<HotelOrder>().eq("is_deleted", IsDeleted.NORMAL.getCode()).eq("status", HotelOrderStatus.WAIT_USE.getCode()).lt("check_out_date", LocalDate.now()));
            for (HotelOrder order : hotelOrderList) {
                HotelOrder hotelOrder = new HotelOrder();
                hotelOrder.setId(order.getId());
                hotelOrder.setStatus(HotelOrderStatus.EXPIRED.getCode());
                hotelOrder.setModifiedTime(new Date());
                hotelOrder.updateById();
            }
        }
    }

    /**
     * 已发货订单，用户未手动确认收货，系统自动确认收货
     * 1、如果物流超过7天未签收则系统确认收货
     * 2、如果订单全部物流已签收则系统确认收货
     */
    @Scheduled(cron = "1 0 0 * * *")
    public void setShopOrderReceipt() throws Exception {
        System.out.println("定时器开始");
        if (redisDatabase == 1) {//商家端启用定时器，防止平台端和商家端同时启用定时器
            List<ShopOrder> shopOrderList = shopOrderMapper.selectList(new EntityWrapper<ShopOrder>().eq("is_deleted", IsDeleted.NORMAL.getCode()).eq("status", ShopOrderStatus.WAIT_RECEIVE.getCode()));
            for (ShopOrder order : shopOrderList) {
                boolean receipt = Boolean.TRUE;
                List<ShopOrderGoods> shopOrderGoodsList = shopOrderGoodsMapper.selectList(new EntityWrapper<ShopOrderGoods>().eq("is_deleted", IsDeleted.NORMAL.getCode()).eq("order_id", order.getId()).orderBy("delivery_time", Boolean.FALSE));
                for (ShopOrderGoods shopOrderGoods : shopOrderGoodsList) {
                    List<Dict> expressCompanyNo = dictMapper.selectList(new EntityWrapper<Dict>().eq("name", shopOrderGoods.getExpressCompany()).setSqlSelect("tips"));
                    if (expressCompanyNo.size() > 0) {
                        JSONObject responseJson = JSONObject.parseObject(LogisticsUtils.getOrderTracesByJson(expressCompanyNo.get(0).getTips(), shopOrderGoods.getExpressNo()));
                        int State = responseJson.getIntValue("State");//物流状态：2-在途中,3-签收,4-问题件
                        if (State != 3) {
                            if (DateUtils.toLocalDateTime(shopOrderGoods.getDeliveryTime()).plusDays(7).isBefore(LocalDateTime.now())) {
//                            if (DateUtils.toLocalDateTime(shopOrderGoods.getDeliveryTime()).plusMinutes(5).isBefore(LocalDateTime.now())) {
                                //物流状态不是签收状态，若发货时间已超过7天则自动确认收货
                                receipt = Boolean.TRUE;
                                break;
                            } else {
                                //物流状态不是签收状态，若发货时间未超过7天则订单不做任何处理
                                receipt = Boolean.FALSE;
                            }
                        }
                    } else {
                        receipt = Boolean.FALSE;
                    }
                }
                //物流已全部签收
                if (receipt) {
                    shopOrderService.confirm(order.getId());
                }
            }
        }
        System.out.println("定时器结束");
    }

//    public static void main(String[] args) throws Exception {
//        System.out.println(LogisticsUtils.getOrderTracesByJson("YD", "3102554210749"));
//        JSONObject responseJson = JSONObject.parseObject(LogisticsUtils.getOrderTracesByJson("YD", "3102554210749"));
//        int State = responseJson.getIntValue("State");
//        System.out.println(State);
//        JSONArray Traces = responseJson.getJSONArray("Traces");
//        Traces.getJSONObject(Traces.size() - 1);
//        JSONObject last = Traces.getJSONObject(Traces.size() - 1);
//        System.out.println(Traces.getJSONObject(Traces.size() - 1));
//        LocalDateTime AcceptTime = LocalDateTime.parse(last.getString("AcceptTime"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
//        System.out.println(AcceptTime);
//    }

}
