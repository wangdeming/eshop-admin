package cn.ibdsr.web.modular.platform.adinfomanager.controller;

import cn.ibdsr.core.base.controller.BaseController;
import cn.ibdsr.web.common.persistence.dao.AdInfoMapper;
import cn.ibdsr.web.common.persistence.model.AdInfo;
import cn.ibdsr.web.core.util.ImageUtil;
import cn.ibdsr.web.modular.platform.adinfomanager.service.IAdInfoManagerService;
import cn.ibdsr.web.modular.platform.adinfomanager.transfer.AdInfoDTO;
import cn.ibdsr.web.modular.platform.adinfomanager.transfer.AdInfoVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 测试控制器
 * @Version V1.0
 * @CreateDate 2019/4/3 14:38
 *
 * Date           Author               Description
 * ------------------------------------------------------
 * 2019/4/3      ZhuJingrui            类说明
 */
@Controller
@RequestMapping("/platform/adInfoManager")
public class AdInfoManagerController extends BaseController {

    @Resource
    private IAdInfoManagerService adInfoManagerService;

    @Resource
    private AdInfoMapper adInfoMapper;

    private String PREFIX = "/platform/adinfomanager/adInfoManager/";

    /**
     * 跳转到平台首页轮播广告管理
     */
    @RequestMapping("/homeAdIndex")
    public String homeAdIndex() {
        return PREFIX + "homeAdIndex.html";
    }

    /**
     * 跳转到平台首页轮播广告新增页面
     */
    @RequestMapping("/homeAdInfoAdd")
    public String homeAdInfoAdd() {
        return PREFIX + "homeAdInfo_add.html";
    }

    /**
     * 跳转到修改广告
     */
    @RequestMapping("/homeAdInfo_update/{adInfoId}")
    public String homeAdInfoUpdate(@PathVariable Integer adInfoId, Model model) {
        model.addAttribute("adInfoId", adInfoId);
        AdInfo adInfo = adInfoMapper.selectById(adInfoId);
        model.addAttribute("type", adInfo.getType()); //关联类型
        model.addAttribute("relationVal", adInfo.getRelationVal()); //关联值
        model.addAttribute("img", ImageUtil.setImageURL(adInfo.getImg()));
        return PREFIX + "homeAdInfo_edit.html";
    }

    /**
     * 跳转到推荐店铺广告首页
     */
    @RequestMapping("/recommendShopAdIndex")
    public String recommendShopAdIndex() {
        return PREFIX + "recommendShopAdIndex.html";
    }
    /**
     * 跳转到平台首页轮播广告新增页面
     */
    @RequestMapping("/recommendShopAdAdd")
    public String recommendShopAdAdd() {
        return PREFIX + "recommendShopAd_add.html";
    }
    /**
     * 跳转到推荐店铺广告修改页面
     */
    @RequestMapping("/recommendShopAd_update/{adInfoId}")
    public String recommendShopAdUpdate(@PathVariable Integer adInfoId, Model model) {
        model.addAttribute("adInfoId", adInfoId);
        AdInfo adInfo = adInfoMapper.selectById(adInfoId);
        model.addAttribute("type", adInfo.getType()); //关联类型
        model.addAttribute("relationVal", adInfo.getRelationVal()); //关联值
        model.addAttribute("img", ImageUtil.setImageURL(adInfo.getImg()));
        return PREFIX + "recommendShopAd_edit.html";
    }

    /**
     * 跳转到特产首页轮播广告管理
     */
    @RequestMapping("/shopGoodsAdIndex")
    public String shopGoodsAdIndex() {
        return PREFIX + "shopGoodsAdIndex.html";
    }

    /**
     * 跳转到特产首页轮播广告新增页面
     */
    @RequestMapping("/shopGoodsAdAdd")
    public String shopGoodsAdAdd() {
        return PREFIX + "shopGoodsAd_add.html";
    }

    /**
     * 跳转到特产首页轮播广告修改页面
     */
    @RequestMapping("/shopGoodsAd_update/{adInfoId}")
    public String shopGoodsAdUpdate(@PathVariable Integer adInfoId, Model model) {
        model.addAttribute("adInfoId", adInfoId);
        AdInfo adInfo = adInfoMapper.selectById(adInfoId);
        model.addAttribute("type", adInfo.getType()); //关联类型
        model.addAttribute("relationVal", adInfo.getRelationVal()); //关联值
        model.addAttribute("img", ImageUtil.setImageURL(adInfo.getImg()));
        return PREFIX + "shopGoodsAd_edit.html";
    }

    /**
     * 首页轮播广告新增，包含：homePageAd-平台首页轮播广告；recommendShopAd-推荐店铺广告；shopGoodsAd-特产首页轮播广告
     * @param adInfoDTO
     * @return
     */
    @RequestMapping(value = "/homeAdAdd")
    @ResponseBody
    public Object homeAdAdd(AdInfoDTO adInfoDTO) {
        adInfoManagerService.homeAdAdd(adInfoDTO);
        return SUCCESS_TIP;
    }

    /**
     * 首页轮播广告修改
     * * @param adInfoDTO
     * @return
     */
    @RequestMapping(value = "/homeAdUpdate")
    @ResponseBody
    public Object homeAdUpdate(AdInfoDTO adInfoDTO) {
        adInfoManagerService.homeAdUpdate(adInfoDTO);
        return SUCCESS_TIP;
    }

    /**
     * 推荐商家广告新增（第一次编辑）
     * @param adInfoDTO
     * @return
     */
    @RequestMapping(value = "/shopAdAdd")
    @ResponseBody
    public Object shopAdAdd(AdInfoDTO adInfoDTO) {
        adInfoManagerService.shopAdAdd(adInfoDTO);
        return SUCCESS_TIP;
    }

    /**
     * 推荐商家广告编辑
     * @param adInfoDTO
     * @return
     */
    @RequestMapping(value = "/shopAdUpdate")
    @ResponseBody
    public Object shopAdUpdate(AdInfoDTO adInfoDTO) {
        adInfoManagerService.shopAdUpdate(adInfoDTO);
        return SUCCESS_TIP;
    }

    /**
     * 首页轮播广告删除
     * * @param adInfoDTO
     * @return
     */
    @RequestMapping(value = "/homeAdDelete")
    @ResponseBody
    public Object homeAdDelete(@RequestParam Long id){
        adInfoManagerService.logDelete(id);
        return SUCCESS_TIP;
    }

    /**
     * 修改广告的排序
     * @param id 主键id
     * @param sequence 排序号
     * @return
     */
    @RequestMapping(value = "/sequenceEdit")
    @ResponseBody
    public Object sequenceEdit(@RequestParam Long id, @RequestParam Integer sequence){
        adInfoManagerService.sequenceEdit(id, sequence);
        return SUCCESS_TIP;
    }

    /**
     * 首页轮播广告发布
     *  @param id
     * @return
     */
    @RequestMapping(value = "/adPublish")
    @ResponseBody
    public Object adPublish(@RequestParam Long id){
        adInfoManagerService.adPublish(id);
        return SUCCESS_TIP;
    }

    /**
     * 首页轮播广告下线
     *  @param id
     * @return
     */
    @RequestMapping(value = "/adOffShelf")
    @ResponseBody
    public Object adOffShelf(@RequestParam Long id){
        adInfoManagerService.adOffShelf(id);
        return SUCCESS_TIP;
    }

    /**
     * @Description 广告位查询
     * @param adPosition 广告位位置的英文缩写（homePageAd-平台首页轮播广告；recommendShopAd-推荐店铺广告；
     *                   shopGoodsAd-特产首页轮播广告）
     * @return
     */
    @RequestMapping(value = "/homeAdList")
    @ResponseBody
    public Object homeAdList(String adPosition){
        List<AdInfoVO> result = adInfoManagerService.homeAdList(adPosition);
        Map<String,Object> map = new HashMap<>();
        Integer total = result != null ? result.size() : 0;
        map.put("total",total);
        map.put("rows",result);
        return map;
    }

    /**
     * @Description recommendShopAd-推荐店铺广告列表查询
     * @param
     * @return
     */
    @RequestMapping(value = "/shopAdList")
    @ResponseBody
    public Object shopAdList(){
        List<AdInfoVO> result = adInfoManagerService.shopAdList();
        Map<String,Object> map = new HashMap<>();
        Integer total = result != null ? result.size() : 0;
        map.put("total",total);
        map.put("rows",result);
        return map;
    }
}
