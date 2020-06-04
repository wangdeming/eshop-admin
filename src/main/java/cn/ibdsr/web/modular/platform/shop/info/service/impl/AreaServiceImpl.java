package cn.ibdsr.web.modular.platform.shop.info.service.impl;

import cn.ibdsr.web.common.persistence.dao.AreaMapper;
import cn.ibdsr.web.common.persistence.model.Area;
import cn.ibdsr.web.modular.platform.shop.info.service.IAreaService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 地区信息管理Service
 *
 * @author XuZhipeng
 * @Date 2019-02-21 11:26:18
 */
@Service
public class AreaServiceImpl implements IAreaService {

    @Autowired
    private AreaMapper areaMapper;

    /**
     * 根据父地区ID获取区域集合
     *
     * @param pid 父区域ID
     * @return
     */
    @Override
    public List<Map<String, Object>> listAreasByPid(Long pid) {
        List<Map<String, Object>> areaList = areaMapper.selectMaps(
                new EntityWrapper<Area>().setSqlSelect("id, name").eq("pid", pid));
        return areaList;
    }
}
