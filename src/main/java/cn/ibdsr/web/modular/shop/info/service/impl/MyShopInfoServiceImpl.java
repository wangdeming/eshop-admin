package cn.ibdsr.web.modular.shop.info.service.impl;

import cn.ibdsr.core.check.StaticCheck;
import cn.ibdsr.core.shiro.ShiroKit;
import cn.ibdsr.core.shiro.ShopData;
import cn.ibdsr.web.common.constant.state.IsDeleted;
import cn.ibdsr.web.common.exception.BizExceptionEnum;
import cn.ibdsr.web.common.exception.BussinessException;
import cn.ibdsr.web.common.persistence.dao.ShopMapper;
import cn.ibdsr.web.common.persistence.model.Shop;
import cn.ibdsr.web.core.util.ImageUtil;
import cn.ibdsr.web.modular.shop.info.service.IMyShopInfoService;
import cn.ibdsr.web.modular.shop.info.transfer.MyShopInfoDTO;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 店铺端-店铺信息管理Service
 *
 * @author XuZhipeng
 * @Date 2019-02-21 11:26:18
 */
@Service
public class MyShopInfoServiceImpl implements IMyShopInfoService {

    @Autowired
    private ShopMapper shopMapper;

    /**
     * 修改店铺信息
     *
     * @param myShopDTO 店铺信息对象
     */
    @Override
    public void update(MyShopInfoDTO myShopDTO) {
        // 当前登录用户店铺ID
        Long shopId = ((ShopData) ShiroKit.getUser().getData()).getShopId();

        // 数据校验
        checkShopInfo(myShopDTO, shopId);

        // 裁剪上传图片URL前缀
        cutImageUrl(myShopDTO);

        Shop shop = new Shop();
        BeanUtils.copyProperties(myShopDTO, shop);

        // 设置修改的店铺ID为当前登录用户店铺ID
        shop.setId(shopId);

        shop.setModifiedUser(ShiroKit.getUser().getId());
        shop.setModifiedTime(new Date());
        shop.updateById();
    }

    /**
     * 裁剪图片的URL前缀
     *
     * @param myShopDTO 店铺信息
     */
    private void cutImageUrl(MyShopInfoDTO myShopDTO) {
        myShopDTO.setCover(ImageUtil.cutImageURL(myShopDTO.getCover()));
        myShopDTO.setLogo(ImageUtil.cutImageURL(myShopDTO.getLogo()));
    }

    /**
     * 校验我的店铺修改信息
     *
     * @param myShopDTO 店铺信息对象
     * @param shopId 店铺Id
     */
    private void checkShopInfo(MyShopInfoDTO myShopDTO, Long shopId) {
        StaticCheck.check(myShopDTO);

        // 校验店铺前台名称是否重复
        Wrapper<Shop> wrapper = new EntityWrapper()
                .eq("front_name", myShopDTO.getFrontName())
                .eq("is_deleted", IsDeleted.NORMAL.getCode())
                .ne("id", shopId);

        List<Shop> shopList = shopMapper.selectList(wrapper);
        if (shopList != null && shopList.size() > 0) {
            throw new BussinessException(BizExceptionEnum.SHOP_FRONT_NAME_IS_EXIST);
        }
    }
}
