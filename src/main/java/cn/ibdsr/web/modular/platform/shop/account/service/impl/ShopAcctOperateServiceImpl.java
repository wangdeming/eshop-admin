package cn.ibdsr.web.modular.platform.shop.account.service.impl;

import cn.ibdsr.core.check.StaticCheck;
import cn.ibdsr.core.shiro.ShiroKit;
import cn.ibdsr.core.util.ToolUtil;
import cn.ibdsr.web.common.constant.state.IsDeleted;
import cn.ibdsr.web.common.constant.state.shop.ShopAcctOperate;
import cn.ibdsr.web.common.constant.state.shop.ShopUserStatus;
import cn.ibdsr.web.common.exception.BizExceptionEnum;
import cn.ibdsr.web.common.exception.BussinessException;
import cn.ibdsr.web.common.persistence.dao.ShopAcctOperationRecordMapper;
import cn.ibdsr.web.common.persistence.dao.ShopMapper;
import cn.ibdsr.web.common.persistence.dao.ShopUserMapper;
import cn.ibdsr.web.common.persistence.model.Shop;
import cn.ibdsr.web.common.persistence.model.ShopAcctOperationRecord;
import cn.ibdsr.web.common.persistence.model.ShopUser;
import cn.ibdsr.web.core.util.ImageUtil;
import cn.ibdsr.web.modular.platform.shop.account.service.IShopAcctOperateService;
import cn.ibdsr.web.modular.platform.shop.account.transfer.AccountOperDTO;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 店铺账户操作管理Service
 *
 * @author XuZhipeng
 * @Date 2019-02-26 11:26:18
 */
@Service
public class ShopAcctOperateServiceImpl implements IShopAcctOperateService {

    @Autowired
    private ShopUserMapper shopUserMapper;

    @Autowired
    private ShopMapper shopMapper;

    @Autowired
    private ShopAcctOperationRecordMapper shopAcctOperationRecordMapper;

    /**
     * 查询店铺名称和账户名
     *
     * @param accountId 店铺账号ID
     * @return
     */
    @Override
    public Map<String, Object> getShopNameAndAccount(Long accountId) {
        // 校验账户ID并获取账户信息
        ShopUser account = checkAccountId(accountId);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("accountId", account.getId());                               // 账户ID
        result.put("account", account.getAccount());                            // 账户号

        // 查询店铺信息
        Shop shop = shopMapper.selectById(account.getShopId());
        if (shop == null) {
            throw new BussinessException(BizExceptionEnum.SHOP_IS_NOT_EXIST);
        }

        result.put("shopName", shop.getName());                                 // 店铺类型
        return result;
    }

    /**
     * 添加账户操作记录
     *
     * @param accountOperDTO
     * @param operateCode 操作码（1-冻结；2-解冻；）
     */
    @Override
    @Transactional
    public void addAcctOperRecord(AccountOperDTO accountOperDTO, Integer operateCode) {
        StaticCheck.check(accountOperDTO);

        // 校验账户ID并获取账户信息
        ShopUser account = checkAccountId(accountOperDTO.getAccountId());

        // 冻结操作
        if (ShopAcctOperate.FREEZE.getCode() == operateCode && ShopUserStatus.OK.getCode() != account.getStatus()) {
            throw new BussinessException(BizExceptionEnum.SHOP_ACCOUNT_CANNOT_FREEZE);
        }

        // 解冻操作
        if (ShopAcctOperate.UNFREEZE.getCode() == operateCode && ShopUserStatus.FREEZED.getCode() != account.getStatus()) {
            throw new BussinessException(BizExceptionEnum.SHOP_ACCOUNT_CANNOT_UNFREEZE);
        }

        ShopAcctOperationRecord acctOperRecord = new ShopAcctOperationRecord();

        acctOperRecord.setType(operateCode);                                    // 操作类型（1-冻结；2-解冻；）
        acctOperRecord.setShopId(account.getShopId());                          // 店铺ID
        acctOperRecord.setShopUserId(accountOperDTO.getAccountId());            // 店铺账户ID
        acctOperRecord.setReason(accountOperDTO.getReason());                   // 冻结/解冻原因
        acctOperRecord.setImgs(convertImgList2Str(accountOperDTO.getImgs()));   // 证据图片
        acctOperRecord.setCreatedTime(new Date());
        acctOperRecord.setCreatedUser(ShiroKit.getUser().getId());
        acctOperRecord.setIsDeleted(IsDeleted.NORMAL.getCode());
        acctOperRecord.insert();

        // 修改账户状态
        ShopUser updateShopUser = new ShopUser();
        updateShopUser.setId(accountOperDTO.getAccountId());
        updateShopUser.setStatus(operateCode == 1 ? ShopUserStatus.FREEZED.getCode() : ShopUserStatus.OK.getCode());
        updateShopUser.setModifiedTime(new Date());
        updateShopUser.setModifiedUser(ShiroKit.getUser().getId());
        updateShopUser.updateById();

        // 修改子账号状态
        if (ShopAcctOperate.FREEZE.getCode() == operateCode && account.getPid() == 0) {
            ShopUser children = new ShopUser();
            children.setStatus(ShopUserStatus.FREEZED.getCode());
            children.setModifiedTime(new Date());
            children.setModifiedUser(ShiroKit.getUser().getId());
            children.update(new EntityWrapper<ShopUser>().eq("pid", accountOperDTO.getAccountId()));
        }
    }

    /**
     * 获取店铺账户操作记录列表
     *
     * @param accountId 店铺账户ID
     * @return
     */
    @Override
    public List<Map<String, Object>> listOperRecords(Long accountId) {
        List<Map<String, Object>> mapList = shopAcctOperationRecordMapper.selectMaps(
                new EntityWrapper<ShopAcctOperationRecord>()
                        .setSqlSelect("type, reason, imgs, created_time AS createdTime")
                        .eq("shop_user_id", accountId)
                        .eq("is_deleted", IsDeleted.NORMAL.getCode())
                        .orderBy("created_time", Boolean.FALSE));
        if (mapList == null || mapList.size() == 0) {
            return null;
        }

        List<String> imgList;
        for (Map<String, Object> map : mapList) {
            // 操作类型（1-冻结；2-解冻；）
            Integer type = Integer.valueOf(map.get("type").toString());
            map.put("type", ShopAcctOperate.valueOf(type));

            // 时间舍去秒
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String dateString = formatter.format(map.get("createdTime"));
            map.put("createdTime", dateString);

            // 证据图片字符串转集合
            imgList = new ArrayList<String>();

            if (ToolUtil.isEmpty(map.get("imgs"))) {
                map.put("imgs", imgList);
                continue;
            }

            // 将字符串数组转换成集合list
            List<String> imgs = Arrays.asList(map.get("imgs").toString().split(","));
            for (String path : imgs) {
                imgList.add(ImageUtil.setImageURL(path));
            }
            map.put("imgs", imgList);
        }
        return mapList;
    }

    /**
     * 注销店铺账号
     *
     * @param accountId 店铺账号ID
     */
    @Override
    @Transactional
    public void cancel(Long accountId) {
        // 校验账户ID并获取账户信息
        ShopUser account = checkAccountId(accountId);
        // 已冻结账号才能被注销
        if (ShopUserStatus.FREEZED.getCode() != account.getStatus()) {
            throw new BussinessException(BizExceptionEnum.SHOP_ACCOUNT_CANNOT_CANCEL);
        }

        ShopUser updateShopUser = new ShopUser();
        updateShopUser.setId(accountId);
        updateShopUser.setIsDeleted(IsDeleted.DELETED.getCode());
        updateShopUser.setModifiedTime(new Date());
        updateShopUser.setModifiedUser(ShiroKit.getUser().getId());
        updateShopUser.updateById();

        // 删除子账号
        ShopUser children = new ShopUser();
        children.setIsDeleted(IsDeleted.DELETED.getCode());
        children.setModifiedTime(new Date());
        children.setModifiedUser(ShiroKit.getUser().getId());
        children.update(new EntityWrapper<ShopUser>().eq("pid", accountId));
    }

    /**
     * 校验账户ID并获取账户信息
     *
     * @param accountId 账户ID
     * @return
     */
    private ShopUser checkAccountId(Long accountId) {
        if (ToolUtil.isEmpty(accountId)) {
            throw new BussinessException(BizExceptionEnum.SHOP_ACCOUNT_ID_IS_NULL);
        }
        // 校验店铺账户ID是否正确
        ShopUser shopUser = shopUserMapper.selectById(accountId);
        if (shopUser == null) {
            throw new BussinessException(BizExceptionEnum.SHOP_ACCOUNT_IS_NOT_EXIST);
        }
        return shopUser;
    }

    /**
     * 图片路径集合转换为字符串
     *
     * @param imgs 图片路径集合
     * @return
     */
    private String convertImgList2Str(List<String> imgs) {
        if (imgs == null || imgs.size() == 0) {
            return "";
        }
        StringBuilder imgsb = new StringBuilder();
        for (String img : imgs) {
            imgsb.append(ImageUtil.cutImageURL(img)).append(",");
        }
        String str = imgsb.toString();
        return str.substring(0, str.lastIndexOf(","));
    }
}
