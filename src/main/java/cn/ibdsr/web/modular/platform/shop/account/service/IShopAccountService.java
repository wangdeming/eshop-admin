package cn.ibdsr.web.modular.platform.shop.account.service;

import java.util.List;
import java.util.Map;

/**
 * 店铺账户信息管理Service
 *
 * @author XuZhipeng
 * @Date 2019-02-21 11:26:18
 */
public interface IShopAccountService {

    /**
     * 开通账户
     *
     * @param shopId 店铺ID
     * @param account 账户名称
     */
    void openAccount(Long shopId, String account);

    /**
     * 查询未开通账号的店铺列表
     *
     * @return
     */
    List<Map<String,Object>> listNoAccountShops();

    /**
     * 根据店铺ID查询新增主账号页面需要的信息
     *
     * @param shopId 店铺ID
     * @return
     */
    Map<String,Object> getShopInfoById(Long shopId);

    /**
     * 获取店铺账号列表
     *
     * @param condition 搜索关键字（店铺名/账户名/手机号）
     * @param shopType 店铺类型（1-特产店铺；2-酒店店铺；）
     * @return
     */
    Object list(String condition, Integer shopType);

    /**
     * 查询店铺账号详情
     *
     * @param accountId 店铺账号ID
     * @return
     */
    Map<String, Object> getAccountInfo(Long accountId);
}
