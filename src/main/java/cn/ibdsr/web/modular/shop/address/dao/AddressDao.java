package cn.ibdsr.web.modular.shop.address.dao;

import cn.ibdsr.web.modular.shop.address.transfer.AddressListVO;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description 店铺地址Dao
 * @Version V1.0
 * @CreateDate 2019-04-19 14:01:57
 *
 * Date           Author               Description
 * ------------------------------------------------------
 * 2019-04-19     Wujiayun            类说明
 */
public interface AddressDao {

    /**
     * 分页获取店铺地址列表
     * @param page
     * @param shopId 店铺Id
     */
    List<AddressListVO> list(@Param("page")Page page,@Param("shopId") Long shopId);
}
