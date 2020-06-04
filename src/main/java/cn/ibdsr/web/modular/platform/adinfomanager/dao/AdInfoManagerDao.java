package cn.ibdsr.web.modular.platform.adinfomanager.dao;

import cn.ibdsr.web.modular.platform.adinfomanager.transfer.AdInfoVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description 测试Dao
 * @Version V1.0
 * @CreateDate 2019/4/3 14:38
 *
 * Date           Author               Description
 * ------------------------------------------------------
 * 2019/4/3      ZhuJingrui            类说明
 */
public interface AdInfoManagerDao {

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
