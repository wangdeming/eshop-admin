package cn.ibdsr.web.modular.shop.goods.controller;

import cn.ibdsr.core.base.controller.BaseController;
import cn.ibdsr.web.common.annotion.BussinessLog;
import cn.ibdsr.web.common.constant.dictmap.goodsdict.GoodsDict;
import cn.ibdsr.web.common.constant.dictmap.shopdict.ShopDict;
import cn.ibdsr.web.common.constant.factory.PageFactory;
import cn.ibdsr.web.common.constant.state.goods.GoodsStatus;
import cn.ibdsr.web.modular.shop.goods.service.IGoodsListService;
import cn.ibdsr.web.modular.shop.goods.transfer.GoodsListVO;
import cn.ibdsr.web.modular.shop.goods.transfer.GoodsQueryDTO;
import cn.ibdsr.web.modular.shop.goods.warpper.GoodsListWarpper;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 店铺端-商品列表信息管理控制器
 *
 * @author XuZhipeng
 * @Date 2019-03-04 11:26:17
 */
@Controller
@RequestMapping("/shop/goods")
public class GoodsListController extends BaseController {

    private String PREFIX = "/shop/goods/";

    @Autowired
    private IGoodsListService goodsListService;

    /**
     * 跳转到商品列表首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "goodsList.html";
    }

    /**
     * 分页获取商品信息列表
     *
     * @param goodsQueryDTO 商品查询对象DTO
     *                      status 商品状态（1-销售中；2-已售罄；3-仓库中；）
     *                      goodsName 商品名称
     *                      minPrice 价格范围最低
     *                      maxPrice 价格范围最高
     *                      minSale 销量范围最低
     *                      maxSale 销量范围最高
     * @return
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(GoodsQueryDTO goodsQueryDTO) {
        Page<GoodsListVO> page = new PageFactory<GoodsListVO>().defaultPage();
        List<Map<String, Object>> result = goodsListService.list(page, goodsQueryDTO);
        page.setRecords((List<GoodsListVO>) new GoodsListWarpper(result).warp());
        return super.packForBT(page);
    }

    /**
     * 批量上架商品
     *
     * @param goodsIds 商品ID数组
     * @return
     */
    @RequestMapping(value = "/onshelf")
    @BussinessLog(name = "上架商品", key = "goodsIds", dict = GoodsDict.GoodsInfoDict)
    @ResponseBody
    public Object onshelf(@RequestParam Long[] goodsIds) {
        goodsListService.batchUpdateGoodsStatus(goodsIds, GoodsStatus.NORMAL.getCode());
        return SUCCESS_TIP;
    }

    /**
     * 批量下架商品
     *
     * @param goodsIds 商品ID数组
     * @return
     */
    @RequestMapping(value = "/offshelf")
    @BussinessLog(name = "下架商品", key = "goodsIds", dict = GoodsDict.GoodsInfoDict)
    @ResponseBody
    public Object offshelf(@RequestParam Long[] goodsIds) {
        goodsListService.batchUpdateGoodsStatus(goodsIds, GoodsStatus.OFFSHELF.getCode());
        return SUCCESS_TIP;
    }

    /**
     * 被系统下架的商品批量申请重新上架
     *
     * @param goodsIds 商品ID数组
     */
    @RequestMapping(value = "/applyGoodsOnShelf")
    @ResponseBody
    public Object applyGoodsOnShelf(@RequestParam Long[] goodsIds) {
        goodsListService.applyGoodsOnShelf(goodsIds);
        return SUCCESS_TIP;
    }

    /**
     * 更新商品排序号
     *
     * @param goodsId 商品ID
     * @param sequence 商品序号
     * @return
     */
    @RequestMapping(value = "/updateSequence")
    @BussinessLog(name = "更新商品排序号", key = "goodsId,sequence", dict = GoodsDict.GoodsInfoDict)
    @ResponseBody
    public Object updateSequence(@RequestParam Long goodsId, @RequestParam Integer sequence) {
        goodsListService.updateSequence(goodsId, sequence);
        return SUCCESS_TIP;
    }
    /**
     * 删除商品
     *
     * @param goodsId 商品ID
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object deleteGoods(@RequestParam(required = false) Long[] goodsId) {
        goodsListService.deleteGoods(goodsId);
        return SUCCESS_TIP;
    }
}
