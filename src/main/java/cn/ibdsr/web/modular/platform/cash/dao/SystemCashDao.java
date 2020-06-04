package cn.ibdsr.web.modular.platform.cash.dao;

import cn.ibdsr.web.modular.platform.cash.transfer.PlatformBalanceFlowVO;
import cn.ibdsr.web.modular.platform.cash.transfer.QueryDTO;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Description 系统资金详情Dao
 * @Version V1.0
 * @CreateDate 2019-04-22 14:12:11
 *
 * Date                     Author               Description
 * ----------------------------------------------------------
 * 2019-04-22 14:12:11    XuZhipeng               类说明
 *
 */
public interface SystemCashDao {

    /**
     * 查询店铺总余额
     *
     * @return
     */
    BigDecimal getShopBalance();

    /**
     * 查询服务费总额
     *
     * @return
     */
    BigDecimal getServiceAmount();

    /**
     * 查询平台历史累计余额
     *
     * @return
     */
    BigDecimal getPlatformTotalBalance();

    /**
     * 查询历史累计提现金额
     *
     * @return
     */
    BigDecimal getWithdrawalAmount();

    /**
     * 查询今日余额变化
     *
     * @return
     */
    BigDecimal getTodayChangeBalance();

    /**
     * 查询今日订单成交额和成交笔数
     *
     * @return
     */
    Map<String, Object> getTodayOrderAmountAndNum();

    /**
     * 查询特产商品订单今日退款金额和退款笔数
     *
     * @return
     */
    Map<String, Object> getTodayRefundAmountAndNum();

    /**
     * 查询酒店订单今日退款金额和退款笔数
     *
     * @return
     */
    Map<String, Object> getTodayHotelRefundAmountAndNum();

    /**
     * 查询今日提现金额和提现笔数
     *
     * @return
     */
    Map<String, Object> getTodayWithdrawalAmountAndNum();

    /**
     * 查询今日平台余额收支流水列表
     *
     * @return
     */
    List<PlatformBalanceFlowVO> listTodayPlatformBalanceFlow();

    /**
     * 分页查询平台余额收支流水列表
     *
     * @return
     */
    List<PlatformBalanceFlowVO> listPlatformBalanceFlow4Page(@Param("page") Page page,
                                                             @Param("queryDTO") QueryDTO queryDTO,
                                                             @Param("orderByField") String orderByField,
                                                             @Param("isAsc") Boolean isAsc);

    /**
     * 查询订单退款总金额
     *
     * @param orderId 订单ID
     * @return
     */
    BigDecimal getOrderRefundAmount(Long orderId);
}
