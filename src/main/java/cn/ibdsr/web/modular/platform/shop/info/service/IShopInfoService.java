package cn.ibdsr.web.modular.platform.shop.info.service;

import cn.ibdsr.web.common.persistence.model.Shop;
import cn.ibdsr.web.modular.platform.shop.info.transfer.ShopInfoDTO;
import cn.ibdsr.web.modular.platform.shop.info.transfer.ShopInfoVO;
import com.baomidou.mybatisplus.plugins.Page;

import java.util.List;
import java.util.Map;

/**
 * 店铺信息管理Service
 *
 * @author XuZhipeng
 * @Date 2019-02-21 11:26:18
 */
public interface IShopInfoService {

    /**
     * 分页获取店铺信息列表
     *
     * @param page
     * @param condition 搜索关键字（店铺名称）
     * @param shopType 店铺类型（1-特产店铺；2-酒店店铺；）
     * @return
     */
    List<Map<String,Object>> list4Page(Page page, String condition, Integer shopType);

    /**
     * 查询店铺详细信息
     *
     * @param shopId 店铺ID
     * @return
     */
    ShopInfoVO detail(Long shopId);

    /**
     * 新增店铺
     *
     * @param shopDTO 店铺信息对象
     * @return
     */
    void add(ShopInfoDTO shopDTO);

    /**
     * 修改店铺信息
     *
     * @param shopDTO 店铺信息对象
     */
    void update(ShopInfoDTO shopDTO);

    /**
     * 删除店铺（逻辑删除）
     *
     * @param shopId 店铺ID
     */
    void delete(Long shopId);

    /**
     * 更新店铺状态
     *
     * @param shopId 店铺ID
     * @param statusCode 店铺状态（1-未开通账号；2-正常营业；3-下架；）
     */
    void updateShopStatus(Long shopId, Integer statusCode);

    /**
     * 根据店铺ID获取店铺信息
     *
     * @param shopId 店铺ID
     * @return
     */
    Shop getShopById(Long shopId);
}
