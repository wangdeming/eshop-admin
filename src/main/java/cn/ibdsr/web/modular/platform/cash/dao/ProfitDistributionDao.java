package cn.ibdsr.web.modular.platform.cash.dao;

import com.baomidou.mybatisplus.plugins.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map; /**
 * @Description 收益分成管理Dao
 * @Version V1.0
 * @CreateDate 2019-04-18 14:41:11
 *
 * Date                     Author               Description
 * ----------------------------------------------------------
 * 2019-04-18 14:41:11    XuZhipeng               类说明
 *
 */
public interface ProfitDistributionDao {

    /**
     * 分页获取收益分成管理列表
     *
     * @param page
     * @param condition 搜索关键字（店铺名称）
     * @param shopType 店铺类型（1-特产店铺；2-酒店店铺；）
     * @return
     */
    List<Map<String,Object>> list4Page(@Param("page") Page page,
                                       @Param("condition") String condition,
                                       @Param("shopType") Integer shopType,
                                       @Param("orderByField") String orderByField,
                                       @Param("isAsc") Boolean isAsc);
}
