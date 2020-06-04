package cn.ibdsr.web.modular.platform.cash.service.impl;

import cn.ibdsr.core.shiro.ShiroKit;
import cn.ibdsr.core.util.DateUtil;
import cn.ibdsr.web.common.constant.state.BizPayType;
import cn.ibdsr.web.common.constant.state.cash.WithdrawalStatus;
import cn.ibdsr.web.common.constant.state.shop.ShopType;
import cn.ibdsr.web.common.exception.BizExceptionEnum;
import cn.ibdsr.web.common.exception.BussinessException;
import cn.ibdsr.web.common.persistence.dao.CashWithdrawalMapper;
import cn.ibdsr.web.common.persistence.dao.ShopMapper;
import cn.ibdsr.web.common.persistence.model.CashWithdrawal;
import cn.ibdsr.web.common.persistence.model.Shop;
import cn.ibdsr.web.core.util.AmountFormatUtil;
import cn.ibdsr.web.modular.platform.cash.dao.WithdrawalDao;
import cn.ibdsr.web.modular.platform.cash.service.ICashTransferService;
import cn.ibdsr.web.modular.platform.cash.service.IWithdrawalService;
import cn.ibdsr.web.modular.platform.cash.transfer.WithdrawalQueryDTO;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 提现管理Service
 * @Version V1.0
 * @CreateDate 2019-04-23 14:12:11
 *
 * Date                     Author               Description
 * ----------------------------------------------------------
 * 2019-04-23 14:12:11    XuZhipeng               类说明
 *
 */
@Service
public class WithdrawalServiceImpl implements IWithdrawalService {

    @Autowired
    private WithdrawalDao withdrawalDao;

    @Autowired
    private CashWithdrawalMapper cashWithdrawalMapper;

    @Autowired
    private ShopMapper shopMapper;

    @Autowired
    private ICashTransferService cashTransferService;

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
    @Override
    public List<Map<String, Object>> listWithdrawals4Page(Page page, WithdrawalQueryDTO queryDTO) {
        List<Map<String, Object>> resultList = withdrawalDao.listWithdrawals4Page(page, queryDTO, page.getOrderByField(), page.isAsc());
        return resultList;
    }

    /**
     * 根据ID查询提现详情
     *
     * @param id 提现ID
     * @return
     */
    @Override
    public Map<String, Object> detailById(Long id) {
        // 校验提现ID，并返回提现信息
        CashWithdrawal cashWithdrawal = checkWithdrawalId(id);

        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("amount", AmountFormatUtil.amountFormat(cashWithdrawal.getAmount())); // 提现金额
        resultMap.put("status", cashWithdrawal.getStatus());                                // 提现状态码（1-待审核；2-审核通过待打款；3-提现成功；4-审核不通过；）
        resultMap.put("statusName", WithdrawalStatus.valueOf(cashWithdrawal.getStatus()));  // 提现状态
        resultMap.put("drawWay", BizPayType.valueOf(cashWithdrawal.getDrawWay()));          // 提现方式
        resultMap.put("accountName", cashWithdrawal.getAccountName());                      // 提现账号姓名
        resultMap.put("accountNo", cashWithdrawal.getAccountNo());                          // 提现账号
        resultMap.put("createdTime", DateUtil.format(cashWithdrawal.getCreatedTime(), "yyyy-MM-dd HH:mm:ss"));  // 申请时间

        // 查询店铺信息
        Shop shop = shopMapper.selectById(cashWithdrawal.getShopId());
        resultMap.put("shopName", shop.getName());
        resultMap.put("shopType", ShopType.valueOf(shop.getType()));
        return resultMap;
    }

    /**
     * 提现审核
     *
     * @param withdrawalId 提现ID
     * @param reviewRemark 审核备注
     * @param wdStatusCode 提现状态（2-审核通过；4-审核不通过；）
     */
    @Transactional
    @Override
    public void review(Long withdrawalId, String reviewRemark, Integer wdStatusCode) {
        // 校验提现ID，并返回提现信息
        CashWithdrawal cashWithdrawal = checkWithdrawalId(withdrawalId);

        // 判断提现订单状态，只有在待审核状态下，才能进行审核
        if (WithdrawalStatus.WAIT_REVIEW.getCode() != cashWithdrawal.getStatus()) {
            throw new BussinessException(BizExceptionEnum.WITHDRAWAL_CANNOT_REVIEW);
        }

        // 修改提现单状态
        cashWithdrawal.setStatus(wdStatusCode);
        cashWithdrawal.setReviewUserId(ShiroKit.getUser().getId());
        cashWithdrawal.setReviewRemark(reviewRemark);
        cashWithdrawal.setReviewTime(new Date());
        cashWithdrawal.setModifiedTime(new Date());
        cashWithdrawal.setModifiedUser(ShiroKit.getUser().getId());
        cashWithdrawal.updateById();

        // 审核不通过资金变动：返还店铺余额
        if (WithdrawalStatus.REFUSE.getCode() == wdStatusCode) {
            cashTransferService.withdrawalRefusePassTransfer(
                    cashWithdrawal.getShopId(),
                    String.valueOf(cashWithdrawal.getId()),
                    cashWithdrawal.getAmount());
        }
    }

    /**
     * 确认打款
     *
     * @param withdrawalId 提现ID
     */
    @Transactional
    @Override
    public void confirm(Long withdrawalId) {
        // 校验提现ID，并返回提现信息
        CashWithdrawal cashWithdrawal = checkWithdrawalId(withdrawalId);

        // 判断提现订单状态，只有在审核通过状态下，才能确认打款
        if (WithdrawalStatus.PASS.getCode() != cashWithdrawal.getStatus()) {
            throw new BussinessException(BizExceptionEnum.WITHDRAWAL_CANNOT_CONFIRM);
        }

        // 修改提现单状态
        cashWithdrawal.setStatus(WithdrawalStatus.CONFIRM.getCode());
        cashWithdrawal.setConfirmTime(new Date());
        cashWithdrawal.setModifiedTime(new Date());
        cashWithdrawal.setModifiedUser(ShiroKit.getUser().getId());
        cashWithdrawal.updateById();

        // 资金变动：平台余额相应减少
        cashTransferService.withdrawalConfirmTransfer(cashWithdrawal.getId(), cashWithdrawal.getAmount());
    }

    /**
     * 校验提现ID，并返回提现信息
     *
     * @param withdrawalId 提现ID
     * @return
     */
    private CashWithdrawal checkWithdrawalId(Long withdrawalId) {
        if (null == withdrawalId) {
            throw new BussinessException(BizExceptionEnum.WITHDRAWAL_ID_IS_NULL);
        }
        CashWithdrawal cashWithdrawal = cashWithdrawalMapper.selectById(withdrawalId);
        if (null == cashWithdrawal) {
            throw new BussinessException(BizExceptionEnum.WITHDRAWAL_IS_NOT_EXIST);
        }
        return cashWithdrawal;
    }
}
