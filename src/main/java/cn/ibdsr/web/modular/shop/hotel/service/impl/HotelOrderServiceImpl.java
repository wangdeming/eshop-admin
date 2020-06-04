package cn.ibdsr.web.modular.shop.hotel.service.impl;

import cn.ibdsr.core.base.tips.SuccessDataTip;
import cn.ibdsr.core.shiro.ShiroKit;
import cn.ibdsr.core.shiro.ShopData;
import cn.ibdsr.core.util.ToolUtil;
import cn.ibdsr.web.common.constant.state.IsDeleted;
import cn.ibdsr.web.common.constant.state.hotel.CancelType;
import cn.ibdsr.web.common.constant.state.hotel.HotelOrderStatus;
import cn.ibdsr.web.common.constant.state.shop.ShopType;
import cn.ibdsr.web.common.exception.BizExceptionEnum;
import cn.ibdsr.web.common.exception.BussinessException;
import cn.ibdsr.web.common.persistence.dao.HotelOrderMapper;
import cn.ibdsr.web.common.persistence.dao.HotelOrderRefundMapper;
import cn.ibdsr.web.common.persistence.dao.HotelOrderUserMapper;
import cn.ibdsr.web.common.persistence.dao.ViewStatsMapper;
import cn.ibdsr.web.common.persistence.model.*;
import cn.ibdsr.web.core.mq.rabbitmq.MessageSenderTask;
import cn.ibdsr.web.core.util.AmountFormatUtil;
import cn.ibdsr.web.core.util.DateUtils;
import cn.ibdsr.web.modular.platform.cash.service.ICashTransferService;
import cn.ibdsr.web.modular.platform.cash.service.IProfitDistributionService;
import cn.ibdsr.web.modular.shop.hotel.dao.HotelOrderDao;
import cn.ibdsr.web.modular.shop.hotel.service.HotelOrderRefundService;
import cn.ibdsr.web.modular.shop.hotel.service.HotelOrderService;
import cn.ibdsr.web.modular.shop.hotel.transfer.HotelOrderListVO;
import cn.ibdsr.web.modular.shop.hotel.transfer.HotelOrderQueryDTO;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @Description 酒店商家端-订单管理Service
 * @Version V1.0
 * @CreateDate 2019/5/7 15:44
 * <p>
 * Date           Author               Description
 * ------------------------------------------------------
 * 2019/5/7      ZhuJingrui            类说明
 */
@Service
public class HotelOrderServiceImpl implements HotelOrderService {

    @Autowired
    private HotelOrderMapper hotelOrderMapper;

    @Autowired
    private HotelOrderDao hotelOrderDao;

    @Autowired
    private HotelOrderUserMapper hotelOrderUserMapper;

    @Autowired
    private HotelOrderRefundMapper hotelOrderRefundMapper;

    @Autowired
    private MessageSenderTask messageSenderTask;

    @Autowired
    private IProfitDistributionService profitDistributionService;

    @Autowired
    private ICashTransferService cashTransferService;

    @Autowired
    private ViewStatsMapper viewStatsMapper;


    @Autowired
    private HotelOrderRefundService hotelOrderRefundService;

    /**
     * 分页获取酒店订单列表
     *
     * @param page
     * @param queryDTO 商品订单查询对象DTO
     *                 orderNo 订单编号
     *                 roomName 房间名称
     *                 realname 入住人
     *                 createdTimeStart 下单时间查询起始时间
     *                 createdTimeEnd 下单时间查询终止时间
     *                 checkInDate 入住时间
     *                 status 状态，1待付款，2待确认，3待使用，4已消费，5已取消
     * @return
     */
    @Override
    public List<HotelOrderListVO> hotelOrderList(Page<HotelOrderListVO> page, HotelOrderQueryDTO queryDTO) {
        Long shopId = ((ShopData) ShiroKit.getUser().getData()).getShopId();
        if (ToolUtil.isEmpty(shopId)) {
            throw new BussinessException(BizExceptionEnum.NO_THIS_USER);
        }
        if (queryDTO.getCreatedTimeStart() != null && queryDTO.getCreatedTimeStart() != "") {
            queryDTO.setCreatedTimeStart(DateUtils.getStartTimeOfDay(queryDTO.getCreatedTimeStart()));
        }
        if (queryDTO.getCreatedTimeEnd() != null && queryDTO.getCreatedTimeEnd() != "") {
            queryDTO.setCreatedTimeEnd(DateUtils.getEndTimeOfDay(queryDTO.getCreatedTimeEnd()));
        }

        queryDTO.setShopId(shopId);
        List<HotelOrderListVO> hotelOrderListVOList = hotelOrderDao.hotleOrderList(page, queryDTO);
        return hotelOrderListVOList;
    }

    /**
     * 商家订单确认
     *
     * @param hotelOrderId 酒店订单主键ID
     * @return
     */
    @Override
    public void confirm(String hotelOrderId) {
        checkHotelOrderId(hotelOrderId);  //校验订单id
        HotelOrder hotelOrder = hotelOrderMapper.selectById(hotelOrderId);
        //只有待确认状态下才能进行确认操作
        if (hotelOrder.getStatus() != HotelOrderStatus.WAIT_CONFIRM.getCode()) {
            throw new BussinessException(BizExceptionEnum.ORDER_STATUS_NOT_WAIT_CONFIRM);
        }
        hotelOrder.setStatus(HotelOrderStatus.WAIT_USE.getCode());
        hotelOrder.setConfirmDatetime(new Date());
        hotelOrder.setModifiedTime(new Date());
        if (ToolUtil.isNotEmpty(ShiroKit.getUser())) {
            hotelOrder.setModifiedUser(ShiroKit.getUser().getId());
        }
        hotelOrder.updateById();
    }

    /**
     * 商家确认入住
     *
     * @param hotelOrderId 酒店订单主键ID
     * @return
     */
    @Override
    public void confirmCheckIn(String hotelOrderId) {
        checkHotelOrderId(hotelOrderId);  //校验订单id
        HotelOrder hotelOrder = hotelOrderMapper.selectById(hotelOrderId);
        //只有待使用状态下才能进行确认入住操作
        if (hotelOrder.getStatus() != HotelOrderStatus.WAIT_USE.getCode()) {
            throw new BussinessException(BizExceptionEnum.ORDER_STATUS_NOT_WAIT_USED);
        }
        hotelOrder.setStatus(HotelOrderStatus.USED.getCode());
        hotelOrder.setConfirmInDatetime(new Date());
        hotelOrder.setModifiedTime(new Date());
        hotelOrder.setModifiedUser(ShiroKit.getUser().getId());
        hotelOrder.updateById();

        messageSenderTask.sendMsgOfSettleHotelOrder(hotelOrderId);
    }

    /**
     * 商家取消订单
     *
     * @param hotelOrderId 酒店订单主键ID
     * @return
     */
    @Override
    public void cancelOrder(String hotelOrderId) throws Exception {
        checkHotelOrderId(hotelOrderId);  //校验订单id
        HotelOrder hotelOrder = hotelOrderMapper.selectById(hotelOrderId);

        if (ToolUtil.isNotEmpty(ShiroKit.getUser())) {
            //商家取消
            //只有待确认状态下才能进行取消订单操作
            if (hotelOrder.getStatus() != HotelOrderStatus.WAIT_CONFIRM.getCode()) {
                throw new BussinessException(BizExceptionEnum.ORDER_STATUS_NOT_WAIT_CONFIRM);
            }
        } else {
            //系统自动取消
            if (hotelOrder.getStatus() != HotelOrderStatus.WAIT_PAY.getCode()) {
                throw new BussinessException(BizExceptionEnum.ORDER_STATUS_NOT_WAIT_PAY);
            }
        }
        hotelOrder.setStatus(HotelOrderStatus.CANCEL.getCode());
        hotelOrder.setModifiedTime(new Date());
        if (ToolUtil.isNotEmpty(ShiroKit.getUser())) {
            hotelOrder.setCancelReason("商家取消");
            hotelOrder.setModifiedUser(ShiroKit.getUser().getId());
        } else {
            hotelOrder.setCancelReason("超时系统自动取消");
        }
        if (hotelOrder.getStatus() == HotelOrderStatus.WAIT_CONFIRM.getCode() || hotelOrder.getStatus() == HotelOrderStatus.WAIT_USE.getCode()) {
            hotelOrderRefundService.refund(hotelOrderId);
        }
        hotelOrder.updateById();
    }

    /**
     * 校验酒店订单ID
     *
     * @param hotelOrderId 酒店订单ID
     * @return
     */
    private void checkHotelOrderId(String hotelOrderId) {
        if (null == hotelOrderId) {
            throw new BussinessException(BizExceptionEnum.HOTEL_ORDER_ID_IS_NULL);
        }
        // 查询酒店订单信息
        HotelOrder hotelOrder = hotelOrderMapper.selectById(hotelOrderId);
        if (null == hotelOrder) {
            throw new BussinessException(BizExceptionEnum.HOTEL_ORDER_IS_NOT_EXIST);
        }
    }

    /**
     * 酒店订单详情
     *
     * @param hotelOrderId 酒店订单主键ID
     * @return
     */
    @Override
    public SuccessDataTip hotelOrderDetail(String hotelOrderId) {
        JSONObject returnJson = new JSONObject();

        //订单信息
        HotelOrder hotelOrder = hotelOrderMapper.selectById(hotelOrderId);
        if (hotelOrder == null) {
            throw new BussinessException(BizExceptionEnum.DATA_ERROR);
        }

        SimplePropertyPreFilter filter = new SimplePropertyPreFilter(HotelOrder.class,
                "id", "orderNo", "roomNumber", "totalAmount", "mobile", "checkInDate", "checkOutDate", "canCancel", "status",
                "payDatetime", "confirmDatetime", "confirmInDatetime", "cancelReason", "createdTime");
        JSONObject hotelOrderJson = JSONObject.parseObject(JSONObject.toJSONString(hotelOrder, filter));
        LocalDate checkInDate = DateUtils.toLocalDateTime(hotelOrder.getCheckInDate()).toLocalDate();
        hotelOrderJson.put("checkInDate", checkInDate.format(DateTimeFormatter.ofPattern("M月d日")) + "(" + checkInDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.SIMPLIFIED_CHINESE) + ")");
        LocalDate checkOutDate = DateUtils.toLocalDateTime(hotelOrder.getCheckOutDate()).toLocalDate();
        hotelOrderJson.put("checkOutDate", checkOutDate.format(DateTimeFormatter.ofPattern("M月d日")) + "(" + checkOutDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.SIMPLIFIED_CHINESE) + ")");
        hotelOrderJson.put("days", checkOutDate.compareTo(checkInDate));
        hotelOrderJson.put("createdTime", DateUtils.toLocalDateTime(hotelOrder.getCreatedTime()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        if (hotelOrder.getPayDatetime() != null || ToolUtil.isNotEmpty(hotelOrder.getPayDatetime())) {
            hotelOrderJson.put("payDatetime", DateUtils.toLocalDateTime(hotelOrder.getPayDatetime()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        }
        if (hotelOrder.getConfirmDatetime() != null || ToolUtil.isNotEmpty(hotelOrder.getConfirmDatetime())) {
            hotelOrderJson.put("confirmDatetime", DateUtils.toLocalDateTime(hotelOrder.getConfirmDatetime()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        }
        if (hotelOrder.getConfirmInDatetime() != null || ToolUtil.isNotEmpty(hotelOrder.getConfirmInDatetime())) {
            hotelOrderJson.put("confirmInDatetime", DateUtils.toLocalDateTime(hotelOrder.getConfirmInDatetime()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        }
        hotelOrderJson.put("statusName", HotelOrderStatus.valueOf(hotelOrder.getStatus()));
        hotelOrderJson.put("canCancelName", CancelType.valueOf(hotelOrder.getCanCancel()));
        hotelOrderJson.put("totalAmount", AmountFormatUtil.amountFormat(hotelOrder.getTotalAmount()));

        //退款金额
        List<HotelOrderRefund> hotelOrderRefundList = hotelOrderRefundMapper.selectList(
                new EntityWrapper<HotelOrderRefund>()
                        .eq("order_id", hotelOrderId)
                        .eq("is_deleted", IsDeleted.NORMAL.getCode())
                        .last("LIMIT 1")
        );
        if (hotelOrderRefundList != null && hotelOrderRefundList.size() > 0) {
            hotelOrderJson.put("refundAmount", AmountFormatUtil.amountFormat(hotelOrderRefundList.get(0).getRefundAmount()));
        }
        returnJson.put("order", hotelOrderJson);


        //入住人
        List<Map<String, Object>> hotelOrderUserList = hotelOrderUserMapper.selectMaps(new EntityWrapper<HotelOrderUser>().setSqlSelect("realname").eq("is_deleted", IsDeleted.NORMAL.getCode()).eq("order_id", hotelOrder.getId()));
        returnJson.put("users", hotelOrderUserList);


        return new SuccessDataTip(returnJson);
    }

    /**
     * 酒店已消费订单7天后结算
     *
     * @param orderId 订单ID
     * @throws Exception
     */
    @Override
    @Transactional
    public void settleOrder(String orderId) throws Exception {
        if (null == orderId) {
            throw new Exception("订单ID为空");
        }
        HotelOrder hotelOrder = hotelOrderMapper.selectById(orderId);
        if (null == hotelOrder) {
            throw new Exception("未查询到订单信息");
        }
        // 订单状态为已消费，才能结算
        if (HotelOrderStatus.USED.getCode() != hotelOrder.getStatus()) {
            throw new Exception("当前订单状态无法进行结算");
        }

        // 查询订单完成退款总金额
        BigDecimal refundAmount = BigDecimal.ZERO;
        // 待到账金额
        BigDecimal accAmount = hotelOrder.getTotalAmount().subtract(refundAmount);
        // 获取店铺服务费率
        BigDecimal serviceRate = profitDistributionService.getServiceRateByShopId(hotelOrder.getShopId());
        // 服务费 = （订单金额 - 退款金额）* 服务费率
        BigDecimal serviceFee = accAmount.multiply(serviceRate);
        // 计算结算金额 = 订单金额 - 退款金额 - 服务费
        BigDecimal settleAmount = accAmount.subtract(serviceFee);

        // 1.插入订单结算信息
        ShopOrderSettlement orderSettlement = new ShopOrderSettlement();
        orderSettlement.setOrderId(orderId);
        orderSettlement.setOrderAmount(hotelOrder.getTotalAmount());
        orderSettlement.setRefundAmount(refundAmount);
        orderSettlement.setServiceRate(serviceRate);
        orderSettlement.setServiceFee(serviceFee);
        orderSettlement.setSettleAmount(settleAmount);
        orderSettlement.setCreatedTime(new Date());
        orderSettlement.insert();

        // 2.资金变动：待到账金额-（shop_order_cash_flow）；店铺余额+（shop_balance_flow）
        cashTransferService.orderSettleTransfer(hotelOrder.getShopId(), orderId, String.valueOf(orderSettlement.getId()), accAmount, settleAmount, ShopType.HOTEL.getCode());

        // 3.订单状态改为8-已结算
        HotelOrder hotelOrder1 = new HotelOrder();
        hotelOrder1.setId(orderId);
        hotelOrder1.setStatus(HotelOrderStatus.FINISHED.getCode());
        hotelOrder1.setModifiedTime(new Date());
        hotelOrder1.updateById();

        try {
            //统计房间销量
            List<ViewStats> viewStatsList = viewStatsMapper.selectList(new EntityWrapper<ViewStats>().eq("is_deleted", IsDeleted.NORMAL.getCode()).eq("room_id", hotelOrder.getRoomId()));
            if (viewStatsList.size() > 0) {
                ViewStats viewStats = new ViewStats();
                viewStats.setId(viewStatsList.get(0).getId());
                viewStats.setSaleNum(hotelOrder.getRoomNumber());
                viewStats.setModifiedTime(new Date());
                viewStats.updateById();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 退款
     *
     * @param hotelOrderId 酒店订单主键ID
     * @return
     */
//    @Override
//    public void refund(String hotelOrderId) throws Exception {
//        HotelOrder hotelOrder = hotelOrderMapper.selectById(hotelOrderId);
//        if (hotelOrder == null) {
//            throw new BussinessException(BizExceptionEnum.DATA_ERROR);
//        }
//        Payment param = new Payment();
//        param.setOrderIds(hotelOrderId);
//        Payment pay = paymentMapper.selectOne(param);
//        if (pay == null || pay.getStatus() != PaymentStatus.SUCCESS.getCode()) {
//            throw new BussinessException(BizExceptionEnum.DATA_ERROR);
//        }
//        Long userId = ShiroKit.getUser().getId();
//        Date now = new Date();
//        HotelOrderRefund hotelOrderRefund = new HotelOrderRefund();
//        hotelOrderRefund.setShopId(hotelOrder.getShopId());
//        hotelOrderRefund.setOrderId(hotelOrder.getId());
//        hotelOrderRefund.setRoomId(hotelOrder.getRoomId());
//        hotelOrderRefund.setRefundOrderNo(OrderUtils.getOrderNoByUUId(hotelOrder.getId()));
//        hotelOrderRefund.setRefundAmount(pay.getPayAmount());
//        hotelOrderRefund.setCreatedUser(userId);
//        hotelOrderRefund.setCreatedTime(now);
//
//        Payment payment = hotelOrderRefundService.payment(hotelOrderId);
//
//        hotelOrderRefund.setOriginalRefundId(result.getOutBizNo());
//        hotelOrderRefund.setOriginalString(result.getOriginalString());
//        hotelOrderRefund.setExpectedTime(new Date());
//        hotelOrderRefund.insert();
//
//        //增加资金变动记录，用户售后退款：待到账金额-（shop_order_cash_flow）；平台余额-（platform_balance_flow）
//        cashChangeService.debitOrderCash(hotelOrder.getShopId(), hotelOrder.getId(), hotelOrder.getTotalAmount(), OrderCashType.INCOME_AMOUNT.getCode(), OrderCashTransSrc.REFUND.getCode(), null, ShopType.HOTEL.getCode());
//        cashChangeService.debitPlatformBalance(hotelOrder.getTotalAmount(), PlatformBalanceFlowTransSrc.GOODS_REFUND.getCode(), hotelOrder.getId(), null);
//    }
}
