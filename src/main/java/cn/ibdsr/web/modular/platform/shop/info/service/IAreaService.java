package cn.ibdsr.web.modular.platform.shop.info.service;

import java.util.List;
import java.util.Map;

/**
 * 地区信息管理Service
 *
 * @author XuZhipeng
 * @Date 2019-02-21 11:26:18
 */
public interface IAreaService {

    /**
     * 根据父地区ID获取区域集合
     *
     * @param pid 父区域ID
     * @return
     */
    List<Map<String, Object>> listAreasByPid(Long pid);
}
