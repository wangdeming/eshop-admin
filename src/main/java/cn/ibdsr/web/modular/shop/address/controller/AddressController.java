package cn.ibdsr.web.modular.shop.address.controller;

import cn.ibdsr.core.base.controller.BaseController;
import cn.ibdsr.core.base.tips.SuccessDataTip;
import cn.ibdsr.core.shiro.ShiroKit;
import cn.ibdsr.core.shiro.ShopData;
import cn.ibdsr.web.common.constant.factory.PageFactory;
import cn.ibdsr.web.common.exception.BizExceptionEnum;
import cn.ibdsr.web.common.exception.BussinessException;
import cn.ibdsr.web.modular.shop.address.service.IAddressService;
import cn.ibdsr.web.modular.shop.address.transfer.AddressListVO;
import cn.ibdsr.web.modular.shop.address.transfer.ShopAddressDTO;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @Description 店铺地址控制器
 * @Version V1.0
 * @CreateDate 2019-04-19 14:01:57
 *
 * Date           Author               Description
 * ------------------------------------------------------
 * 2019-04-19     Wujiayun           类说明
 */

@Controller
@RequestMapping("/shop/address")
public class AddressController extends BaseController {

    private String PREFIX = "/shop/address/";

    @Autowired
    private IAddressService iAddressService;

    /**
     * 跳转到店铺地址首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "address.html";
    }

    /**
     * 跳转到添加店铺地址
     */
    @RequestMapping("/toAdd")
    public String toAdd() {
        Long shopId = ((ShopData) ShiroKit.getUser().getData()).getShopId();
        if(iAddressService.addressCount(shopId)>=15){
            throw new BussinessException(BizExceptionEnum.SHOP_ADDRESS_ARRIVAL_CAP);
        }
       // System.out.println(iAddressService.addressCount(shopId));
        return PREFIX + "address_add.html";
    }

    /**
     * 跳转到修改店铺地址
     */
    @RequestMapping("/toEdit/{addressId}")
    public String toEdit(@PathVariable Long addressId, Model model) {
        //model.addAttribute("isEdit", Boolean.TRUE);
        model.addAttribute("shopAddress", iAddressService.detail(addressId));
        return PREFIX + "address_edit.html";
    }

    /**
     * 分页获取店铺地址列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list() {
        Page<AddressListVO> page = new PageFactory<AddressListVO>().defaultPage();
        List<AddressListVO> addressList = iAddressService.list(page);
        page.setRecords(addressList);
        return super.packForBT(page);
    }

    /**
     * 新增店铺地址
     * @param shopAddressDTO
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(ShopAddressDTO shopAddressDTO) {
        iAddressService.add(shopAddressDTO);
        return super.SUCCESS_TIP;
    }

    /**
     * 删除店铺地址
     * @param addressId 地址Id
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Long addressId) {
        iAddressService.delete(addressId);
        return SUCCESS_TIP;
    }


    /**
     * 修改店铺地址
     * @param shopAddressDTO
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(ShopAddressDTO shopAddressDTO) {
        iAddressService.update(shopAddressDTO);
        return super.SUCCESS_TIP;
    }
    /*@RequestMapping(value = "/detail")
    @ResponseBody
    public Object detail(Long addressId) {
        return  iAddressService.detail(addressId);
        //return super.SUCCESS_TIP;
    }*/
}
