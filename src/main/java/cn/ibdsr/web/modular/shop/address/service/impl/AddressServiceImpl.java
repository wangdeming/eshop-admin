package cn.ibdsr.web.modular.shop.address.service.impl;

import cn.ibdsr.core.check.StaticCheck;
import cn.ibdsr.core.shiro.ShiroKit;
import cn.ibdsr.core.shiro.ShiroUser;
import cn.ibdsr.core.shiro.ShopData;
import cn.ibdsr.web.common.constant.factory.ConstantFactory;
import cn.ibdsr.web.common.constant.factory.IConstantFactory;
import cn.ibdsr.web.common.constant.state.IsDefault;
import cn.ibdsr.web.common.constant.state.IsDeleted;
import cn.ibdsr.web.common.exception.BizExceptionEnum;
import cn.ibdsr.web.common.exception.BussinessException;
import cn.ibdsr.web.common.persistence.dao.ShopDeliveryAddressMapper;
import cn.ibdsr.web.common.persistence.model.Shop;
import cn.ibdsr.web.common.persistence.model.ShopDeliveryAddress;
import cn.ibdsr.web.common.persistence.model.ShopOrder;
import cn.ibdsr.web.modular.shop.address.dao.AddressDao;
import cn.ibdsr.web.modular.shop.address.transfer.AddressListVO;
import cn.ibdsr.web.modular.shop.address.transfer.ShopAddressDTO;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.ibdsr.web.modular.shop.address.service.IAddressService;

import java.util.Date;
import java.util.List;

/**
 * @Description 店铺地址ServiceImpl
 * @Version V1.0
 * @CreateDate 2019-04-19 14:01:57
 *
 * Date           Author               Description
 * ------------------------------------------------------
 * 2019-04-19     Wujiayun           类说明
 */
@Service
public class AddressServiceImpl implements IAddressService {

    @Autowired
    private AddressDao addressDao;

    @Autowired
    private ShopDeliveryAddressMapper addressMapper;
    /**
     * 分页获取店铺地址列表
     */
    @Override
    public List<AddressListVO> list(Page page){
        // 登录用户所属店铺ID
        Long shopId = ((ShopData)ShiroKit.getUser().getData()).getShopId();
        return addressDao.list(page, shopId);

    }

    /**
     * 新增店铺地址
     *
     * @param shopAddressDTO 店铺地址对象
     * @return
     */
    @Override
    public void add(ShopAddressDTO shopAddressDTO){
        StaticCheck.check(shopAddressDTO);
        Long userId = ShiroKit.getUser().getId();
        Long shopId = ((ShopData)ShiroKit.getUser().getData()).getShopId();
        checkDefault(shopAddressDTO,shopId);
        ShopDeliveryAddress address = new ShopDeliveryAddress();
        BeanUtils.copyProperties(shopAddressDTO,address);
        convertAreaId2Name(address);
        address.setShopId(shopId);
        address.setCreatedUser(userId);
        address.setCreatedTime(new Date());
        address.setIsDeleted(IsDeleted.NORMAL.getCode());
        address.insert();

    }

    /**
     * 修改店铺地址
     *
     * @param shopAddressDTO 店铺信息对象
     * @return
     */
    @Override
    public void update(ShopAddressDTO shopAddressDTO){
        StaticCheck.check(shopAddressDTO);
        Long userId = ShiroKit.getUser().getId();
        Long shopId = ((ShopData)ShiroKit.getUser().getData()).getShopId();
        ShopDeliveryAddress shopDeliveryAddress = addressMapper.selectById(shopAddressDTO.getId());
        if(shopAddressDTO.getIsDefault() ==IsDefault.N.getCode() && shopDeliveryAddress.getIsDefault() == IsDefault.Y.getCode()){
            throw new BussinessException(BizExceptionEnum.DEFAULT_ADDRESS_CAN_NOT_TO_NONDEFAULT);
        }
        checkDefault(shopAddressDTO,shopId);
        ShopDeliveryAddress address = new ShopDeliveryAddress();
        BeanUtils.copyProperties(shopAddressDTO,address);
        convertAreaId2Name(address);
        address.setModifiedUser(userId);
        address.setModifiedTime(new Date());
        address.updateById();
    }

    /**
     * 删除店铺地址
     * @param addressId 地址Id
     */
    @Override
    public void delete(Long addressId){
        if (addressId == null) {
            throw new BussinessException(BizExceptionEnum.SHOP_ADDRESS_ID_IS_NULL);
        }
        ShopDeliveryAddress shopAddress = addressMapper.selectById(addressId);
        if (shopAddress == null) {
            throw new BussinessException(BizExceptionEnum.SHOP_ADDRESS_IS_NOT_EXIST);
        }
        if(shopAddress.getIsDefault() == IsDefault.Y.getCode()){
            throw new BussinessException(BizExceptionEnum.DEFAULT_ADDRESS_CAN_NOT_DELETE);
        }
        shopAddress.setModifiedUser(ShiroKit.getUser().getId());
        shopAddress.setModifiedTime(new Date());
        shopAddress.setIsDeleted(IsDeleted.DELETED.getCode());
        shopAddress.updateById();

    }

    /**
     * 统计当前店铺收货地址数量
     * @param shopId
     * @return
     */
    @Override
    public int addressCount(Long shopId){
        int addressCount = addressMapper.selectCount(
                new EntityWrapper()
                        .eq("shop_id", shopId)
                        .eq("is_deleted", IsDeleted.NORMAL.getCode())
        );
        return addressCount;
    }

    /**
     * 查询店铺地址详细信息
     *
     * @param addressId 店铺地址ID
     * @return
     */
    @Override
    public AddressListVO detail(Long addressId){
        if (addressId == null) {
            throw new BussinessException(BizExceptionEnum.SHOP_ADDRESS_ID_IS_NULL);
        }
        ShopDeliveryAddress shopAddress = addressMapper.selectById(addressId);
        if (shopAddress == null) {
            throw new BussinessException(BizExceptionEnum.SHOP_ADDRESS_IS_NOT_EXIST);
        }
        AddressListVO addressListVO = new AddressListVO();
        BeanUtils.copyProperties(shopAddress, addressListVO);
        return addressListVO;
    }

    private void convertAreaId2Name(ShopDeliveryAddress address) {
        IConstantFactory factory = ConstantFactory.me();
        address.setProvince(factory.getAreaNameById(address.getProvinceId()));
        address.setCity(factory.getAreaNameById(address.getCityId()));
        address.setDistrict(factory.getAreaNameById(address.getDistrictId()));
    }

    /**
     * 默认地址唯一性
     * @param
     */
    private void checkDefault(ShopAddressDTO addressDTO,Long shopId){
        if(addressDTO.getIsDefault() == IsDefault.Y.getCode()){
            List<ShopDeliveryAddress> addresses = addressMapper.selectList(
                    new EntityWrapper()
                            .eq("shop_id", shopId).eq("is_default", IsDefault.Y.getCode()).eq("is_deleted",IsDeleted.NORMAL.getCode())
            );
            if(addresses != null && addresses.size() > 0){
                for (ShopDeliveryAddress address: addresses){
                    address.setIsDefault(IsDefault.N.getCode());
                    address.setModifiedUser(ShiroKit.getUser().getId());
                    address.setModifiedTime(new Date());
                    address.updateById();
                }
            }

        }
    }

}
