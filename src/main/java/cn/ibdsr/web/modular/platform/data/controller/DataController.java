package cn.ibdsr.web.modular.platform.data.controller;

import cn.ibdsr.core.base.tips.SuccessDataTip;
import cn.ibdsr.core.shiro.ShiroKit;
import cn.ibdsr.core.shiro.ShiroUser;
import cn.ibdsr.core.shiro.ShopData;
import cn.ibdsr.web.common.constant.state.PlatformType;
import cn.ibdsr.web.modular.platform.data.service.DataService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description: 类功能和用法的说明
 * @Version: V1.0
 * @CreateUser: xujincai
 * @CreateDate: 2019/5/24 8:56
 * <p>
 * Date Author Description
 * ------------------------------------------------------
 * 2019/5/24 xujincai init
 */
@Controller
@RequestMapping("platformshop/data")
public class DataController {

    @Autowired
    private DataService dataService;

    @RequestMapping(value = "count")
    public String count() {
        return "/platform/data/data.html";
    }

    /**
     * 折线图
     *
     * @param type      报表纬度，1为日报，2为周报，3为月报
     * @param beginDate 开始时间
     * @param endDate   结束时间
     * @param shopType  商店类型，1为特产店铺，2为酒店店铺
     * @param shopId    店铺ID
     * @return SuccessDataTip
     */
    @RequestMapping(value = "chart")
    @ResponseBody
    public SuccessDataTip chart(@RequestParam Integer type, String beginDate, String endDate, Integer shopType, Long shopId) {
        ShiroUser user = ShiroKit.getUser();
        if (user.getPlatformType() == PlatformType.SHOP.getCode()) {
            //若是商家端接口访问，从shiro中获取商家ID
            shopId = ((ShopData) user.getData()).getShopId();
        }
        return dataService.chart(type, beginDate, endDate, shopType, shopId);
    }

    /**
     * 表格
     *
     * @param type      报表纬度，1为日报，2为周报，3为月报
     * @param beginDate 开始时间
     * @param endDate   结束时间
     * @param shopType  商店类型，1为特产店铺，2为酒店店铺
     * @param shopId    店铺ID
     * @param offset
     * @param limit
     * @return SuccessDataTip
     */
    @RequestMapping(value = "table")
    @ResponseBody
    public JSONObject table(@RequestParam Integer type, String beginDate, String endDate, Integer shopType, Long shopId, @RequestParam int offset, @RequestParam int limit) {
        ShiroUser user = ShiroKit.getUser();
        if (user.getPlatformType() == PlatformType.SHOP.getCode()) {
            //若是商家端接口访问，从shiro中获取商家ID
            shopId = ((ShopData) user.getData()).getShopId();
        }
        return dataService.table(type, beginDate, endDate, shopType, shopId, offset, limit);
    }

    /**
     * 导出
     *
     * @param type      报表纬度，1为日报，2为周报，3为月报
     * @param beginDate 开始时间
     * @param endDate   结束时间
     * @param shopType  商店类型，1为特产店铺，2为酒店店铺
     * @param shopId    店铺ID
     */
    @RequestMapping(value = "download")
    @ResponseBody
    public void download(@RequestParam Integer type, String beginDate, String endDate, Integer shopType, Long shopId) {
        ShiroUser user = ShiroKit.getUser();
        if (user.getPlatformType() == PlatformType.SHOP.getCode()) {
            //若是商家端接口访问，从shiro中获取商家ID
            shopId = ((ShopData) user.getData()).getShopId();
        }
        dataService.download(type, beginDate, endDate, shopType, shopId);
    }

}
