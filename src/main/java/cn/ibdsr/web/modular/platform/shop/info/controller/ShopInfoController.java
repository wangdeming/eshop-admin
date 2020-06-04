package cn.ibdsr.web.modular.platform.shop.info.controller;

import cn.ibdsr.core.base.controller.BaseController;
import cn.ibdsr.core.base.tips.SuccessDataTip;
import cn.ibdsr.core.shiro.ShiroKit;
import cn.ibdsr.core.shiro.ShopData;
import cn.ibdsr.web.common.annotion.BussinessLog;
import cn.ibdsr.web.common.constant.dictmap.shopdict.ShopDict;
import cn.ibdsr.web.common.constant.factory.ConstantFactory;
import cn.ibdsr.web.common.constant.factory.PageFactory;
import cn.ibdsr.web.common.constant.state.shop.ShopStatus;
import cn.ibdsr.web.common.exception.BizExceptionEnum;
import cn.ibdsr.web.common.exception.BussinessException;
import cn.ibdsr.web.modular.platform.shop.info.service.IShopInfoService;
import cn.ibdsr.web.modular.platform.shop.info.transfer.ShopInfoDTO;
import cn.ibdsr.web.modular.platform.shop.info.transfer.ShopListVO;
import cn.ibdsr.web.modular.platform.shop.info.warpper.ShopListWarpper;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 店铺信息管理控制器
 *
 * @author XuZhipeng
 * @Date 2019-02-21 11:26:17
 */
@Controller
@RequestMapping("/platformshop/shopInfo")
public class ShopInfoController extends BaseController {

    @Autowired
    private IShopInfoService shopInfoService;

    private String PREFIX = "/platform/shop/info/";

    /**
     * 跳转到店铺信息管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "shopInfoList.html";
    }

    /**
     * 跳转到添加店铺信息页面
     */
    @RequestMapping("/toAdd")
    public String toAdd() {
        return PREFIX + "shopInfo_add.html";
    }

    /**
     * 跳转到编辑店铺信息页面
     *
     * @param shopId 店铺ID
     * @return
     */
    @RequestMapping("/toEdit/{shopId}")
    public String toEdit(@PathVariable Long shopId, Model model) {
        model.addAttribute("isEdit", Boolean.TRUE);
        model.addAttribute("shop", shopInfoService.detail(shopId));
        return PREFIX + "shopInfo_edit.html";
    }

    /**
     * 商家端跳转到我的店铺信息页面
     *
     * @return
     */
    @RequestMapping("/toMyShop")
    public String toMyShop(Model model) {
        Long shopId = ((ShopData) ShiroKit.getUser().getData()).getShopId();
        if (shopId == null) {
            throw new BussinessException(BizExceptionEnum.SHOP_ID_IS_NULL);
        }
        model.addAttribute("shop", shopInfoService.detail(shopId));
        return PREFIX + "shopInfo_edit.html";
    }

    /**
     * 跳转到查询店铺信息页面
     *
     * @param shopId 店铺ID
     * @return
     */
    @RequestMapping("/toView/{shopId}")
    public String toView(@PathVariable Long shopId, Model model) {
        model.addAttribute("isEdit", Boolean.FALSE);
        model.addAttribute("shop", shopInfoService.detail(shopId));
        return PREFIX + "shopInfo_edit.html";
    }

    /**
     * 分页获取店铺信息列表
     *
     * @param condition 搜索关键字（店铺名称）
     * @param shopType 店铺类型（1-特产店铺；2-酒店店铺；）
     * @return
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam(required = false) String condition,
                       @RequestParam(required = false) Integer shopType) {
        Page<ShopListVO> page = new PageFactory<ShopListVO>().defaultPage();
        List<Map<String, Object>> result = shopInfoService.list4Page(page, condition, shopType);
        page.setRecords((List<ShopListVO>) new ShopListWarpper(result).warp());
        return super.packForBT(page);
    }

    /**
     * 获取店铺类型
     */
    @RequestMapping(value = "/listShopTypes")
    @ResponseBody
    public Object listShopTypes() {
        return new SuccessDataTip(ConstantFactory.me().getShopTypeList());
    }

    /**
     * 店铺信息详情
     *
     * @param shopId 店铺ID
     * @return
     */
    @RequestMapping(value = "/detail")
    @ResponseBody
    public Object detail(@RequestParam Long shopId) {
        return new SuccessDataTip(shopInfoService.detail(shopId));
    }

    /**
     * 新增店铺信息
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(ShopInfoDTO shopInfoDTO) {
        shopInfoService.add(shopInfoDTO);
        return super.SUCCESS_TIP;
    }

    /**
     * 修改店铺信息
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(ShopInfoDTO shopInfoDTO) {
        shopInfoService.update(shopInfoDTO);
        return super.SUCCESS_TIP;
    }

    /**
     * 删除店铺
     */
    @RequestMapping(value = "/delete")
    @BussinessLog(name = "删除店铺", key = "shopId", dict = ShopDict.ShopInfoDict)
    @ResponseBody
    public Object delete(@RequestParam Long shopId) {
        shopInfoService.delete(shopId);
        return SUCCESS_TIP;
    }

    /**
     * 店铺上架
     */
    @RequestMapping(value = "/onshelf")
    @BussinessLog(name = "上架店铺", key = "shopId", dict = ShopDict.ShopInfoDict)
    @ResponseBody
    public Object onshelf(@RequestParam Long shopId) {
        shopInfoService.updateShopStatus(shopId, ShopStatus.NORMAL.getCode());
        return SUCCESS_TIP;
    }

    /**
     * 店铺下架
     */
    @RequestMapping(value = "/offshelf")
    @BussinessLog(name = "下架店铺", key = "shopId", dict = ShopDict.ShopInfoDict)
    @ResponseBody
    public Object offshelf(@RequestParam Long shopId) {
        shopInfoService.updateShopStatus(shopId, ShopStatus.OFFSHELF.getCode());
        return SUCCESS_TIP;
    }
}
