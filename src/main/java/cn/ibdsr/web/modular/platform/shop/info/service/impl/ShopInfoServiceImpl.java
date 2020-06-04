package cn.ibdsr.web.modular.platform.shop.info.service.impl;

import cn.ibdsr.core.check.StaticCheck;
import cn.ibdsr.core.shiro.ShiroKit;
import cn.ibdsr.core.util.ToolUtil;
import cn.ibdsr.web.common.constant.factory.ConstantFactory;
import cn.ibdsr.web.common.constant.factory.IConstantFactory;
import cn.ibdsr.web.common.constant.state.IsDeleted;
import cn.ibdsr.web.common.constant.state.shop.ShopStatus;
import cn.ibdsr.web.common.constant.state.shop.ShopType;
import cn.ibdsr.web.common.exception.BizExceptionEnum;
import cn.ibdsr.web.common.exception.BussinessException;
import cn.ibdsr.web.common.persistence.dao.ShopImgRelMapper;
import cn.ibdsr.web.common.persistence.dao.ShopMapper;
import cn.ibdsr.web.common.persistence.model.Shop;
import cn.ibdsr.web.common.persistence.model.ShopImgRel;
import cn.ibdsr.web.common.persistence.model.ShopUser;
import cn.ibdsr.web.core.util.ImageUtil;
import cn.ibdsr.web.core.util.MapUtils;
import cn.ibdsr.web.modular.platform.cash.service.IProfitDistributionService;
import cn.ibdsr.web.modular.platform.shop.info.dao.ShopInfoDao;
import cn.ibdsr.web.modular.platform.shop.info.service.IShopInfoService;
import cn.ibdsr.web.modular.platform.shop.info.transfer.ShopInfoDTO;
import cn.ibdsr.web.modular.platform.shop.info.transfer.ShopInfoVO;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.hc.core5.http.ParseException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 店铺信息管理Service
 *
 * @author XuZhipeng
 * @Date 2019-02-21 11:26:18
 */
@Service
public class ShopInfoServiceImpl implements IShopInfoService {

    @Autowired
    private ShopInfoDao shopInfoDao;

    @Autowired
    private ShopMapper shopMapper;

    @Autowired
    private ShopImgRelMapper shopImgRelMapper;

    @Autowired
    private IProfitDistributionService profitDistributionService;

    @Value(value = "${lingshan.key}")
    private String lingshanKey;

    @Value(value = "${lingshan.lon_lat}")
    private String lingshanLonLat;

    /**
     * 分页获取店铺信息列表
     *
     * @param page
     * @param condition 搜索关键字（店铺名称）
     * @param shopType  店铺类型（1-特产店铺；2-酒店店铺；）
     * @return
     */
    @Override
    public List<Map<String, Object>> list4Page(Page page, String condition, Integer shopType) {
        return shopInfoDao.list4Page(page, condition, shopType, page.getOrderByField(), page.isAsc());
    }

    /**
     * 查询店铺详细信息
     *
     * @param shopId 店铺ID
     * @return
     */
    @Override
    public ShopInfoVO detail(Long shopId) {
        // 根据店铺ID获取店铺信息
        Shop shop = getShopById(shopId);

        ShopInfoVO shopVO = new ShopInfoVO();
        BeanUtils.copyProperties(shop, shopVO);

        // 店铺类型名称
        shopVO.setShopTypeName(ShopType.valueOf(shopVO.getType()));

        // 省市区街道地区ID转名称
        convertAreaId2Name(shopVO);

        // 相关图片路径拼接
        combineImageUrl(shopVO);

        // 获取店铺图片集合
        shopVO.setImgList(getImgListByShopId(shopVO.getId()));
        return shopVO;
    }

    /**
     * 新增店铺
     *
     * @param shopDTO 店铺信息对象
     * @return
     */
    @Override
    @Transactional
    public void add(ShopInfoDTO shopDTO) {
        // 数据校验
        checkShopInfo(shopDTO, Boolean.TRUE);

        // 裁剪上传图片URL前缀
        cutImageUrl(shopDTO);

        Shop shop = new Shop();
        BeanUtils.copyProperties(shopDTO, shop);

        shop.setStatus(ShopStatus.UNACCOUNT.getCode()); // 店铺状态（未开通账号）
        shop.setCreatedUser(ShiroKit.getUser().getId());
        shop.setCreatedTime(new Date());
        shop.setIsDeleted(IsDeleted.NORMAL.getCode());
        if (shop.getLongitude() != null && shop.getLatitude() != null) {
            try {
                //增加酒店距灵山景区的距离，xujc
                shop.setDistanceLingshan(MapUtils.distance(lingshanKey, shop.getLongitude().toPlainString() + "," + shop.getLatitude().toPlainString(), lingshanLonLat));
            } catch (IOException | ParseException e) {
                e.printStackTrace();
            }
        }
        shop.insert();

        // 保存店铺图片集合
        saveShopImgList(shop.getId(), shopDTO.getImgList());

        // 初始化店铺服务费率
        profitDistributionService.initShopSerRate(shop.getId());
    }

    /**
     * 修改店铺信息
     *
     * @param shopDTO 店铺信息对象
     */
    @Override
    @Transactional
    public void update(ShopInfoDTO shopDTO) {
        // 数据校验
        checkShopInfo(shopDTO, Boolean.FALSE);

        // 裁剪上传图片URL前缀
        cutImageUrl(shopDTO);

        Shop shop = new Shop();
        BeanUtils.copyProperties(shopDTO, shop);

        shop.setModifiedUser(ShiroKit.getUser().getId());
        shop.setModifiedTime(new Date());
        if (shop.getLongitude() != null && shop.getLatitude() != null) {
            try {
                //增加酒店距灵山景区的距离，xujc
                shop.setDistanceLingshan(MapUtils.distance(lingshanKey, shop.getLongitude().toPlainString() + "," + shop.getLatitude().toPlainString(), lingshanLonLat));
            } catch (IOException | ParseException e) {
                e.printStackTrace();
            }
        }
        shop.updateById();

        // 保存店铺图片集合
        saveShopImgList(shop.getId(), shopDTO.getImgList());
    }

    /**
     * 删除店铺（逻辑删除）
     *
     * @param shopId 店铺ID
     */
    @Override
    @Transactional
    public void delete(Long shopId) {
        // 根据店铺ID获取店铺信息
        Shop shop = getShopById(shopId);

        shop.setIsDeleted(IsDeleted.DELETED.getCode());
        shop.setModifiedTime(new Date());
        shop.setModifiedUser(ShiroKit.getUser().getId());
        shop.updateById();

        // 并删除所有店铺账号
        ShopUser account = new ShopUser();
        account.setIsDeleted(IsDeleted.DELETED.getCode());
        account.setModifiedTime(new Date());
        account.setModifiedUser(ShiroKit.getUser().getId());
        account.update(new EntityWrapper().eq("shop_id", shop.getId()));

        // 删除店铺服务费率信息
        profitDistributionService.deleteShopSerRate(shop.getId());
    }

    /**
     * 更新店铺状态
     *
     * @param shopId     店铺ID
     * @param statusCode 店铺状态（1-未开通账号；2-正常营业；3-下架；）
     */
    @Override
    public void updateShopStatus(Long shopId, Integer statusCode) {
        // 根据店铺ID获取店铺信息
        Shop shop = getShopById(shopId);

        shop.setStatus(statusCode);
        shop.setModifiedTime(new Date());
        shop.setModifiedUser(ShiroKit.getUser().getId());
        shop.updateById();
    }

    /**
     * 根据店铺ID获取店铺信息
     *
     * @param shopId 店铺ID
     * @return
     */
    @Override
    public Shop getShopById(Long shopId) {
        if (shopId == null) {
            throw new BussinessException(BizExceptionEnum.SHOP_ID_IS_NULL);
        }

        Shop shop = shopMapper.selectById(shopId);
        if (shop == null) {
            throw new BussinessException(BizExceptionEnum.SHOP_IS_NOT_EXIST);
        }
        return shop;
    }

    /**
     * 检验新增或修改店铺信息参数
     *
     * @param shopDTO 店铺信息对象
     */
    private void checkShopInfo(ShopInfoDTO shopDTO, Boolean isInsert) {
        StaticCheck.check(shopDTO);

        // 校验营业电话和营业手机号码二选一必填
        if (ToolUtil.isEmpty(shopDTO.getOfficePhone()) && ToolUtil.isEmpty(shopDTO.getOfficeTelphone())) {
            throw new BussinessException(BizExceptionEnum.SHOP_OFFICEPHONE_IS_NULL);
        }


        // 校验店铺名称是否重复
        Wrapper<Shop> wrapper = new EntityWrapper()
                .eq("name", shopDTO.getName())
                .eq("is_deleted", IsDeleted.NORMAL.getCode());
        if (!isInsert) {
            // 修改操作 校验店铺ID是否为空
            if (ToolUtil.isEmpty(shopDTO.getId())) {
                throw new BussinessException(BizExceptionEnum.SHOP_ID_IS_NULL);
            }

            wrapper.ne("id", shopDTO.getId());
        }

        List<Shop> shopList = shopMapper.selectList(wrapper);
        if (shopList != null && shopList.size() > 0) {
            throw new BussinessException(BizExceptionEnum.SHOP_NAME_IS_EXIST);
        }
    }

    /**
     * 省市区街道地区ID转名称
     *
     * @param shopVO
     */
    private void convertAreaId2Name(ShopInfoVO shopVO) {
        IConstantFactory factory = ConstantFactory.me();
        shopVO.setProvinceName(factory.getAreaNameById(shopVO.getProvinceId()));
        shopVO.setCityName(factory.getAreaNameById(shopVO.getCityId()));
        shopVO.setDistrictName(factory.getAreaNameById(shopVO.getDistrictId()));
        shopVO.setStreetName(factory.getAreaNameById(shopVO.getStreetId()));
    }

    /**
     * 拼接图片的URL前缀
     *
     * @param shopVO 店铺信息
     */
    private void combineImageUrl(ShopInfoVO shopVO) {
        shopVO.setCover(ImageUtil.setImageURL(shopVO.getCover()));
        shopVO.setLogo(ImageUtil.setImageURL(shopVO.getLogo()));
        shopVO.setIdentityPositive(ImageUtil.setImageURL(shopVO.getIdentityPositive()));
        shopVO.setIdentityNegative(ImageUtil.setImageURL(shopVO.getIdentityNegative()));
        shopVO.setLicenseImage(ImageUtil.setImageURL(shopVO.getLicenseImage()));
    }

    /**
     * 裁剪图片的URL前缀
     *
     * @param shopDTO 店铺信息
     */
    private void cutImageUrl(ShopInfoDTO shopDTO) {
        shopDTO.setCover(ImageUtil.cutImageURL(shopDTO.getCover()));
        shopDTO.setLogo(ImageUtil.cutImageURL(shopDTO.getLogo()));
        shopDTO.setIdentityPositive(ImageUtil.cutImageURL(shopDTO.getIdentityPositive()));
        shopDTO.setIdentityNegative(ImageUtil.cutImageURL(shopDTO.getIdentityNegative()));
        shopDTO.setLicenseImage(ImageUtil.cutImageURL(shopDTO.getLicenseImage()));
    }

    /**
     * 根据店铺ID查询店铺相关图片集合
     *
     * @param shopId 店铺ID
     * @return
     */
    private List<String> getImgListByShopId(Long shopId) {
        List<ShopImgRel> imgList = shopImgRelMapper.selectList(
                new EntityWrapper<ShopImgRel>()
                        .setSqlSelect("img_path")
                        .eq("shop_id", shopId)
                        .eq("is_deleted", IsDeleted.NORMAL.getCode()));
        if (imgList == null || imgList.size() == 0) {
            return null;
        }

        List<String> imgPathList = new ArrayList<String>();

        for (ShopImgRel imgRel : imgList) {
            imgPathList.add(ImageUtil.setImageURL(imgRel.getImgPath()));
        }
        return imgPathList;
    }

    /**
     * 新增店铺，保存店铺图片集合
     *
     * @param shopId  店铺ID
     * @param imgList 图片路径集合
     */
    private void saveShopImgList(Long shopId, List<String> imgList) {
        if (imgList == null || imgList.size() == 0) {
            return;
        }

        // 删除已存在的图片
        shopImgRelMapper.delete(new EntityWrapper<ShopImgRel>().eq("shop_id", shopId));

        // 批量新增店铺图片
        List<ShopImgRel> imgRelList = new ArrayList<ShopImgRel>();
        ShopImgRel imgRel;
        for (String path : imgList) {
            imgRel = new ShopImgRel();
            imgRel.setShopId(shopId);
            imgRel.setImgPath(ImageUtil.cutImageURL(path));    // 截去图片上传URL前缀
            imgRel.setCreatedUser(ShiroKit.getUser().getId());
            imgRelList.add(imgRel);
        }
        shopInfoDao.batchInsertShopImgRel(imgRelList);
    }
}
