package cn.ibdsr.web.modular.platform.shop.account.dao;

import cn.ibdsr.web.modular.platform.shop.account.transfer.ShopAccountVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 店铺账号管理Dao
 *
 * @author XuZhipeng
 * @Date 2019-02-26 11:26:18
 */
public interface ShopAccountDao {

    /**
     * 查询店铺账号列表
     *
     * @param condition 搜索关键字（店铺名称/账号/手机号码）
     * @param shopType 店铺类型（1-特产店铺；2-酒店店铺；）
     * @return
     */
    List<ShopAccountVO> list(@Param("condition") String condition,
                             @Param("shopType") Integer shopType);

}
