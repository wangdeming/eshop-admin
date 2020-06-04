package cn.ibdsr.web.modular.platform.cash.service;

import cn.ibdsr.web.modular.platform.cash.transfer.WithdrawalQueryDTO;
import com.baomidou.mybatisplus.plugins.Page;

import java.util.List;
import java.util.Map;

/**
 * @Description 提现管理Service
 * @Version V1.0
 * @CreateDate 2019-04-22 14:12:11
 *
 * Date                     Author               Description
 * ----------------------------------------------------------
 * 2019-04-22 14:12:11    XuZhipeng               类说明
 *
 */
public interface IWithdrawalService {

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
    List<Map<String, Object>> listWithdrawals4Page(Page page, WithdrawalQueryDTO queryDTO);

    /**
     * 根据ID查询提现详情
     *
     * @param id 提现ID
     * @return
     */
    Map<String, Object> detailById(Long id);

    /**
     * 提现审核
     *
     * @param withdrawalId 提现ID
     * @param reviewRemark 审核备注
     * @param wdStatusCode 提现状态（2-审核通过；4-审核不通过；）
     */
    void review(Long withdrawalId, String reviewRemark, Integer wdStatusCode);

    /**
     * 确认打款
     *
     * @param withdrawalId 提现ID
     */
    void confirm(Long withdrawalId);
}
