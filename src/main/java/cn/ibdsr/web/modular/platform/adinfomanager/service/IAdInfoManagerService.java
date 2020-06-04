package cn.ibdsr.web.modular.platform.adinfomanager.service;

import cn.ibdsr.core.base.service.BaseService;
import cn.ibdsr.web.common.persistence.model.AdInfo;
import cn.ibdsr.web.modular.platform.adinfomanager.transfer.AdInfoDTO;
import cn.ibdsr.web.modular.platform.adinfomanager.transfer.AdInfoVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description 测试Service
 * @Version V1.0
 * @CreateDate 2019/4/3 14:38
 *
 * Date           Author               Description
 * ------------------------------------------------------
 * 2019/4/3      ZhuJingrui            类说明
 */
public interface IAdInfoManagerService extends BaseService <AdInfoDTO, AdInfo> {

    /**
     * 首页广告新增
     * @param adInfoDTO
     * @return
     */
    void homeAdAdd(AdInfoDTO adInfoDTO);

    /**
     * 首页广告修改
     * @param adInfoDTO
     * @return
     */
    void homeAdUpdate(AdInfoDTO adInfoDTO);

    /**
     * 推荐商家广告新增（第一次编辑）
     * @param adInfoDTO
     * @return
     */
    void shopAdAdd(AdInfoDTO adInfoDTO);

    /**
     * 推荐商家广告编辑
     * @param adInfoDTO
     * @return
     */
    void shopAdUpdate(AdInfoDTO adInfoDTO);

    /**
     * 修改广告的排序
     * @param id 主键id
     * @param sequence 排序号
     * @return
     */
    void sequenceEdit(Long id, Integer sequence);

    /**
     * 逻辑删除首页轮播广告
     * @param id
     * @return
     */
    void logDelete(Long id);

    /**
     * 首页轮播广告发布
     * @param id
     * @return
     */
    void adPublish(Long id);

    /**
     * 首页轮播广告下线
     * @param id
     * @return
     */
    void adOffShelf(Long id);

    /**
     * @Description 首页广告列表查询
     * @param adPosition 广告位位置的英文缩写（homePageAd-平台首页轮播广告；recommendShopAd-推荐店铺广告；
     *                   shopGoodsAd-特产首页轮播广告）
     * @return
     */
    List<AdInfoVO> homeAdList(@Param("adPosition") String adPosition);

    /**
     * @Description recommendShopAd-推荐店铺广告列表查询
     * @param
     * @return
     */
    List<AdInfoVO> shopAdList();

}
