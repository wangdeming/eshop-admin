package cn.ibdsr.web.modular.platform.ordercenter.hotelorder.service.impl;

import cn.ibdsr.core.shiro.ShiroKit;
import cn.ibdsr.core.shiro.ShopData;
import cn.ibdsr.core.util.ToolUtil;
import cn.ibdsr.web.common.exception.BizExceptionEnum;
import cn.ibdsr.web.common.exception.BussinessException;
import cn.ibdsr.web.core.util.DateUtils;
import cn.ibdsr.web.modular.platform.ordercenter.hotelorder.dao.HotelDao;
import cn.ibdsr.web.modular.shop.hotel.transfer.HotelOrderListVO;
import cn.ibdsr.web.modular.shop.hotel.transfer.HotelOrderQueryDTO;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.ibdsr.web.modular.platform.ordercenter.hotelorder.service.HotelService;

import java.util.List;

/**
 * @Description 订单中心-酒店订单Service
 * @Version V1.0
 * @CreateDate 2019/5/23 11:34
 *
 * Date           Author               Description
 * ------------------------------------------------------
 * 2019/5/23      ZhuJingrui           类说明
 */
@Service
public class HotelServiceImpl implements HotelService {

    @Autowired
    private HotelDao hotelDao;

    /**
     * 分页获取酒店订单列表
     *
     * @param page
     * @param queryDTO 商品订单查询对象DTO
     *                 orderNo 订单编号
     *                 roomName 房间名称
     *                 roomId 房间Id
     *                 shopId 酒店Id
     *                 shopName 酒店名称
     *                 realname 入住人
     *                 createdTimeStart 下单时间查询起始时间
     *                 createdTimeEnd 下单时间查询终止时间
     *                 checkInDate 入住时间
     *                 status 状态，1待付款，2待确认，3待使用，4已消费，5已取消
     * @return
     */
    @Override
    public List<HotelOrderListVO> hotelOrderList(Page<HotelOrderListVO> page, HotelOrderQueryDTO queryDTO) {
        if (queryDTO.getCreatedTimeStart() != null && queryDTO.getCreatedTimeStart() != ""){
            queryDTO.setCreatedTimeStart(DateUtils.getStartTimeOfDay(queryDTO.getCreatedTimeStart()));
        }
        if (queryDTO.getCreatedTimeEnd() != null && queryDTO.getCreatedTimeEnd() != ""){
            queryDTO.setCreatedTimeEnd(DateUtils.getEndTimeOfDay(queryDTO.getCreatedTimeEnd()));
        }
        List<HotelOrderListVO> hotelOrderListVOList = hotelDao.hotelOrderList(page, queryDTO);
        return hotelOrderListVOList;
    }

}
