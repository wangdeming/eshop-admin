package cn.ibdsr.web.modular.platform.cash.dao;

import cn.ibdsr.web.modular.platform.cash.transfer.WithdrawalQueryDTO;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Description 提现管理Dao
 * @Version V1.0
 * @CreateDate 2019-04-23 14:12:11
 *
 * Date                     Author               Description
 * ----------------------------------------------------------
 * 2019-04-23 14:12:11    XuZhipeng               类说明
 *
 */
public interface WithdrawalDao {

    /**
     * 分页查询提现列表
     *
     * @param page
     * @param queryDTO 搜索条件
     *                 condition 搜索关键字：店铺名称
     *                 timeType 时间类型（1-今日；2-昨日；3-近7日；4-自定义；）
     *                 startDate 统计开始日期
     *                 endDate 统计结束日期
     *                 wdStatus 提现状态（1-待审核；2-审核通过待打款；3-提现成功；4-审核不通过；）
     * @return
     */
    List<Map<String,Object>> listWithdrawals4Page(@Param("page") Page page,
                                                  @Param("queryDTO") WithdrawalQueryDTO queryDTO,
                                                  @Param("orderByField") String orderByField,
                                                  @Param("isAsc") Boolean isAsc);
}
