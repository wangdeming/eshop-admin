package cn.ibdsr.web.modular.platform.goodscenter.specialgoods.controller;

import cn.ibdsr.core.base.controller.BaseController;
import cn.ibdsr.core.base.tips.SuccessDataTip;
import cn.ibdsr.web.common.constant.factory.PageFactory;
import cn.ibdsr.web.modular.platform.goodscenter.specialgoods.service.SpecialGoodsService;
import cn.ibdsr.web.modular.platform.goodscenter.specialgoods.transfer.SpecialGoodsListVO;
import cn.ibdsr.web.modular.platform.goodscenter.specialgoods.transfer.SpecialGoodsQueryDTO;
import cn.ibdsr.web.modular.platform.goodscenter.specialgoods.warpper.SpecialGoodsListWarpper;
import cn.ibdsr.web.modular.shop.goods.service.GoodsService;
import cn.ibdsr.web.modular.shop.goods.transfer.GoodsVO;
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
 * @Description 商品中心-特产商品控制器
 * @Version V1.0
 * @CreateDate 2019/5/23 16:46
 *
 * Date           Author               Description
 * ------------------------------------------------------
 * 2019/5/23      ZhuJingrui            类说明
 */
@Controller
@RequestMapping("/platform/goodscenter")
public class SpecialGoodsController extends BaseController {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private SpecialGoodsService specialGoodsService;

    private String PREFIX = "/platform/goodscenter/specialgoods/";

    /**
     * 跳转到商品中心-特产商品首页
     */
    @RequestMapping("/goods")
    public String goodsIndex() {
        return PREFIX + "specialGoods.html";
    }

    /**
     * 跳转到商品中心-特产商品详情页
     */
    @RequestMapping("/detail/{goodsId}")
    public String detail(@PathVariable Long goodsId, Model model) {
        model.addAttribute(goodsId);
        GoodsVO goodsProp = goodsService.getGoods(goodsId);
        model.addAttribute("goodsProp",goodsProp);
        return PREFIX + "goodsDetail.html";
    }

    /**
     * 商品详情
     *
     * @param goodsId 商品ID
     * @return
     */
    @RequestMapping(value = "/goodsDetail")
    @ResponseBody
    public Object goodsDetail(Long goodsId) {
        GoodsVO goodsDetail = goodsService.getGoods(goodsId);
        return new SuccessDataTip(goodsDetail);
    }

    /**
     * 分页获取商品列表
     *
     * @param goodsQueryDTO 商品查询对象DTO
     *                 status 商品状态（1-销售中；2-已售罄；3-仓库中；）
     *                 platformManage 平台管理: 0为已下架，1为未下架
     *                 goodsName 商品名称
     *                 goodsId 商品ID
     *                 shopName 店铺名称
     *                 shopId 店铺ID
     *                 firstCategoryId 商品一级类目Id
     *                 secondCategoryId 商品二级类目Id
     * @return
     */
    @RequestMapping(value = "/goodsList")
    @ResponseBody
    public Object goodsList(SpecialGoodsQueryDTO goodsQueryDTO) {
        Page<SpecialGoodsListVO> page = new PageFactory<SpecialGoodsListVO>().defaultPage();
        List<Map<String, Object>> result = specialGoodsService.goodsList(page, goodsQueryDTO);
        page.setRecords((List<SpecialGoodsListVO>) new SpecialGoodsListWarpper(result).warp());
        return super.packForBT(page);
    }

    /**
     * 系统下架商品
     *
     * @param goodsId 商品ID
     * @param reason 下架原因
     * @return
     */
    @RequestMapping(value = "/offShelf")
    @ResponseBody
    public Object offShelf(@RequestParam Long goodsId, @RequestParam String reason) {
        specialGoodsService.offShelf(goodsId, reason);
        return SUCCESS_TIP;
    }

    /**
     * 平台审核商品是否可以重新上架
     *
     * @param goodsId 商品ID
     * @param isAgree 是否同意上架；1-同意，0-不同意
     * @return
     */
    @RequestMapping(value = "/goodsPlatformCheck")
    @ResponseBody
    public Object goodsPlatformCheck(Long goodsId, Boolean isAgree) {
        specialGoodsService.goodsPlatformCheck(goodsId, isAgree);
        return SUCCESS_TIP;
    }

}
