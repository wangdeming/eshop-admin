package cn.ibdsr.web.modular.shop.info.controller;

import cn.ibdsr.core.base.controller.BaseController;
import cn.ibdsr.core.shiro.ShiroKit;
import cn.ibdsr.core.shiro.ShopData;
import cn.ibdsr.web.common.exception.BizExceptionEnum;
import cn.ibdsr.web.common.exception.BussinessException;
import cn.ibdsr.web.modular.platform.shop.info.service.IShopInfoService;
import cn.ibdsr.web.modular.shop.info.service.IMyShopInfoService;
import cn.ibdsr.web.modular.shop.info.transfer.MyShopInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 店铺端-店铺信息管理控制器
 *
 * @author XuZhipeng
 * @Date 2019-02-21 11:26:17
 */
@Controller
@RequestMapping("/shop/info")
public class MyShopInfoController extends BaseController {

    private String PREFIX = "/shop/info/";

    @Autowired
    private IMyShopInfoService myShopInfoService;

    @Autowired
    private IShopInfoService shopInfoService;

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
        return PREFIX + "myShop.html";
    }

    /**
     * 修改店铺信息
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(MyShopInfoDTO myShopDTO) {
        myShopInfoService.update(myShopDTO);
        return super.SUCCESS_TIP;
    }
}
