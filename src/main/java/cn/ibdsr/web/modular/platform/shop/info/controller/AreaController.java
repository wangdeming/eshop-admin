package cn.ibdsr.web.modular.platform.shop.info.controller;

import cn.ibdsr.core.base.controller.BaseController;
import cn.ibdsr.core.base.tips.SuccessDataTip;
import cn.ibdsr.web.common.exception.BizExceptionEnum;
import cn.ibdsr.web.common.exception.BussinessException;
import cn.ibdsr.web.modular.platform.shop.info.service.IAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 地区信息管理控制器
 *
 * @author XuZhipeng
 * @Date 2019-02-21 11:26:17
 */
@Controller
@RequestMapping("/platformshop/area")
public class AreaController extends BaseController {

    @Autowired
    private IAreaService areaService;

    /**
     * 根据父地区ID获取区域集合
     *
     * @param pid 父地区ID（0-省份级别；）
     * @return
     */
    @RequestMapping(value = "/listAreasByPid")
    @ResponseBody
    public Object listAreasByPid(@RequestParam Long pid) {
        if (pid == null) {
            throw new BussinessException(BizExceptionEnum.AREA_PID_IS_NULL);
        }
        List<Map<String, Object>> areaList = areaService.listAreasByPid(pid);
        return new SuccessDataTip(areaList);
    }
}
