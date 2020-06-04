package cn.ibdsr.web.modular.shop.info.service;

import cn.ibdsr.web.modular.shop.info.transfer.MyShopInfoDTO;

/**
 * 店铺端-店铺信息管理Service
 *
 * @author XuZhipeng
 * @Date 2019-02-21 11:26:18
 */
public interface IMyShopInfoService {

    /**
     * 修改店铺信息
     *
     * @param myShopDTO 店铺信息对象
     */
    void update(MyShopInfoDTO myShopDTO);
}
