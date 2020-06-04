package cn.ibdsr.web.modular.platform.shop.account.service;

import cn.ibdsr.web.modular.platform.shop.account.transfer.AccountOperDTO;

import java.util.List;
import java.util.Map;

/**
 * 店铺账户操作管理Service
 *
 * @author XuZhipeng
 * @Date 2019-02-26 11:26:18
 */
public interface IShopAcctOperateService {

    /**
     * 查询店铺名称和账户名
     *
     * @param accountId 店铺账号ID
     * @return
     */
    Map<String, Object> getShopNameAndAccount(Long accountId);

    /**
     * 添加账户操作记录
     *
     * @param accountOperDTO
     * @param operateCode 操作码（1-冻结；2-解冻；）
     */
    void addAcctOperRecord(AccountOperDTO accountOperDTO, Integer operateCode);

    /**
     * 获取店铺账户操作记录列表
     *
     * @param accountId 店铺账户ID
     * @return
     */
    List<Map<String, Object>> listOperRecords(Long accountId);

    /**
     * 注销店铺账号
     *
     * @param accountId 店铺账号ID
     */
    void cancel(Long accountId);
}
