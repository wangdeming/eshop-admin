package cn.ibdsr.web.modular.platform.goods.category.controller;

import cn.ibdsr.core.base.controller.BaseController;
import cn.ibdsr.core.base.tips.SuccessDataTip;
import cn.ibdsr.core.util.ToolUtil;
import cn.ibdsr.web.common.annotion.BussinessLog;
import cn.ibdsr.web.common.constant.BussinessLogType;
import cn.ibdsr.web.common.constant.dictmap.goodsdict.GoodsDict;
import cn.ibdsr.web.common.exception.BizExceptionEnum;
import cn.ibdsr.web.common.exception.BussinessException;
import cn.ibdsr.web.common.persistence.model.GoodsCategory;
import cn.ibdsr.web.core.log.ShopLogObjectHolder;
import cn.ibdsr.web.modular.platform.goods.category.service.IGoodsCategoryService;
import cn.ibdsr.web.modular.platform.goods.category.transfer.CategoryDTO;
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
 * 商品类别管理控制器
 *
 * @author XuZhipeng
 * @Date 2019-02-25 09:26:17
 */
@Controller
@RequestMapping("/platform/goodscategory")
public class GoodsCategoryController extends BaseController {

    @Autowired
    private IGoodsCategoryService goodsCategoryService;

    private String PREFIX = "/platform/goods/category/";

    /**
     * 跳转到商品类别管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "goodsCategoryList.html";
    }

    /**
     * 跳转到添加商品类别页面
     */
    @RequestMapping("/toAdd")
    public String toAdd() {
        return PREFIX + "goodsCategory_add.html";
    }

    /**
     * 跳转到编辑商品类别页面
     *
     * @param id 类别ID
     * @return
     */
    @RequestMapping("/toEdit/{id}")
    public String toEdit(@PathVariable Long id, Model model) {
        // 查询类别信息
        model.addAttribute("category", goodsCategoryService.getCategoryById(id));
        return PREFIX + "goodsCategory_edit.html";
    }

    /**
     * 获取商品类别管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list() {
        List<Map<String, Object>> result = goodsCategoryService.listGoodsCategorys();
        return result;
    }

    /**
     * 以树形结构查询商品类别列表
     *
     * @return
     */
    @RequestMapping(value = "/tree")
    @ResponseBody
    public Object tree() {
        List<Map<String, Object>> result = goodsCategoryService.listOfTree();
        return result;
    }

    /**
     * 获取上级所有节点
     */
    @RequestMapping(value = "/listParentNodes")
    @ResponseBody
    public Object listParentNodes() {
        List<Map<String, Object>> resultList = goodsCategoryService.listParentNodes();
        return new SuccessDataTip(resultList);
    }

    /**
     * 新增商品类别
     *
     * @param categoryDTO 类别信息
     *                    name 类别名称
     *                    frontName 前台展示名称
     *                    pid 父类别ID
     *                    iconImg 图标图片路径
     * @return
     */
    @RequestMapping(value = "/add")
    @BussinessLog(name = "新增商品类别", key = "pid,name,frontName,iconImg", dict = GoodsDict.GoodsCategoryDict)
    @ResponseBody
    public Object add(CategoryDTO categoryDTO) {
        goodsCategoryService.add(categoryDTO);
        return super.SUCCESS_TIP;
    }

    /**
     * 修改商品类别
     *
     * @param categoryDTO 类别信息
     *                    id 类别ID
     *                    name 类别名称
     *                    frontName 前台展示名称
     *                    pid 父类别ID
     *                    iconImg 图标图片路径
     * @return
     */
    @RequestMapping(value = "/update")
    @BussinessLog(name = "修改商品类别", key = "id,name,frontName,pid,iconImg", dict = GoodsDict.GoodsCategoryDict)
    @ResponseBody
    public Object update(CategoryDTO categoryDTO) {
        goodsCategoryService.update(categoryDTO);
        return super.SUCCESS_TIP;
    }

    /**
     * 删除商品类别管理
     */
    @RequestMapping(value = "/delete")
    @BussinessLog(name = "删除商品类别", logType = BussinessLogType.NOREQ_SIMPLELOG)
    @ResponseBody
    public Object delete(Long id) {
        if (ToolUtil.isEmpty(id)) {
            throw new BussinessException(BizExceptionEnum.GOODS_CATEGORY_ID_IS_NULL);
        }
        //日志内容
        GoodsCategory orig = goodsCategoryService.detailById(id);
        if (orig == null) {
            throw new BussinessException(BizExceptionEnum.GOODS_CATEGORY_IS_NOT_EXIST);
        }
        ShopLogObjectHolder.me().set("类别名称=" + orig.getName());

        goodsCategoryService.update2Del(id);
        return SUCCESS_TIP;
    }

    /**
     * 上移操作
     */
    @RequestMapping(value = "/moveUp")
    @BussinessLog(name = "商品类别上移", key = "id", dict = GoodsDict.GoodsCategoryDict)
    @ResponseBody
    public Object moveUp(@RequestParam Long id) {
        goodsCategoryService.moveUp(id);
        return super.SUCCESS_TIP;
    }

    /**
     * 下移操作
     */
    @RequestMapping(value = "/moveDown")
    @BussinessLog(name = "商品类别下移", key = "id", dict = GoodsDict.GoodsCategoryDict)
    @ResponseBody
    public Object moveDown(@RequestParam Long id) {
        goodsCategoryService.moveDown(id);
        return super.SUCCESS_TIP;
    }
}
