package cn.ibdsr.web.modular.shop.address.service;

import cn.ibdsr.web.modular.shop.address.transfer.AddressListVO;
import cn.ibdsr.web.modular.shop.address.transfer.ShopAddressDTO;
import com.baomidou.mybatisplus.plugins.Page;

import java.util.List;

/**
 * @Description 店铺地址Service
 * @Version V1.0
 * @CreateDate 2019-04-19 14:01:57
 *
 * Date           Author               Description
 * ------------------------------------------------------
 * 2019-04-19     Wujiayun            类说明
 */
public interface IAddressService {
    /**
     * 分页获取店铺地址列表
     */
    List<AddressListVO> list(Page<AddressListVO> page);
    /**
     * 新增店铺地址
     *
     * @param shopAddressDTO 店铺信息对象
     * @return
     */
    void add(ShopAddressDTO shopAddressDTO);
    /**
     * 修改店铺地址
     *
     * @param shopAddressDTO 店铺信息对象
     * @return
     */
    void update(ShopAddressDTO shopAddressDTO);
    /**
     * 删除店铺地址
     * @param addressId 地址Id
     */
    void delete(Long addressId);

    /**
     * 统计当前店铺收货地址数量
     * @param shopId
     * @return
     */
    int addressCount(Long shopId);

    /**
     * 查询店铺地址详细信息
     *
     * @param addressId 店铺地址ID
     * @return
     */
    AddressListVO detail(Long addressId);
}
