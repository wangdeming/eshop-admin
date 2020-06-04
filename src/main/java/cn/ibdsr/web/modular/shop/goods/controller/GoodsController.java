package cn.ibdsr.web.modular.shop.goods.controller;

import cn.ibdsr.core.base.tips.SuccessDataTip;
import cn.ibdsr.core.base.tips.SuccessTip;
import cn.ibdsr.web.common.persistence.model.Goods;
import cn.ibdsr.web.modular.shop.goods.dao.GoodsDao;
import cn.ibdsr.web.modular.shop.goods.service.GoodsService;
import cn.ibdsr.web.modular.shop.goods.transfer.GoodsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "shop/goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    GoodsDao goodsDao;

    /**
     * 跳转新增商品页面
     *
     * @return
     */
    @RequestMapping(value = "toinsert")
    public String toInsert() {
        return "/shop/goods/insert.html";
    }

    /**
     * 跳转编辑商品页面
     *
     * @return
     */
    @RequestMapping(value = "/goodsUpdate/{goodsId}")
    public String goodsUpdate(@PathVariable Long goodsId, Model model) {
        model.addAttribute(goodsId);
        GoodsVO goodsProp = goodsService.getGoods(goodsId);
        model.addAttribute("goodsProp",goodsProp);
        return "/shop/goods/update.html";
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
     * 分类查询，查询一级分类列表pid传值0
     *
     * @param pid
     * @return
     */
    @RequestMapping(value = "goodscategorylist")
    @ResponseBody
    public SuccessDataTip goodsCategoryList(@RequestParam int pid) {
        return goodsService.goodsCategoryList(pid);
    }


/**
     * 新增商品
     * @param goods
     * @param imageListJsonString
     * @param goodsSkuListJsonString
     * @param content
     * @return
     */
    @RequestMapping(value = "insert")
    @ResponseBody
    public SuccessTip insert(Goods goods, String imageListJsonString, String goodsSkuListJsonString, String content) {
        return goodsService.insert(goods, imageListJsonString, goodsSkuListJsonString, content);
    }



    /**
     * @Description 方法描述
     * @param goodsId 商品ID
     * @param goods 商品对象
     * @param imageListJsonString 商品图片Json字符串
     * @param goodsSkuListJsonString 商品SKU的Json字符串
     * @param content 商品详情描述
     * @return
     */
    @RequestMapping(value = "update")
    @ResponseBody
    public SuccessTip update(Long goodsId, Goods goods, String imageListJsonString, String goodsSkuListJsonString, String content) {
        return goodsService.update(goodsId, goods, imageListJsonString, goodsSkuListJsonString, content);
    }

}
