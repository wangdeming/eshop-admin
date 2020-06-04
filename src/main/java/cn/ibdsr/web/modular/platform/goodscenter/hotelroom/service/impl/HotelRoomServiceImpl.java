package cn.ibdsr.web.modular.platform.goodscenter.hotelroom.service.impl;

import cn.ibdsr.core.check.StaticCheck;
import cn.ibdsr.core.shiro.ShiroKit;
import cn.ibdsr.core.util.ToolUtil;
import cn.ibdsr.web.common.constant.Const;
import cn.ibdsr.web.common.constant.state.IsDeleted;
import cn.ibdsr.web.common.constant.state.shop.PlatformManageStatus;
import cn.ibdsr.web.common.constant.state.shop.ShopApplyStatus;
import cn.ibdsr.web.common.exception.BizExceptionEnum;
import cn.ibdsr.web.common.exception.BussinessException;
import cn.ibdsr.web.common.persistence.dao.PlatformManageMapper;
import cn.ibdsr.web.common.persistence.dao.RoomMapper;
import cn.ibdsr.web.common.persistence.dao.RoomSettingMapper;
import cn.ibdsr.web.common.persistence.model.PlatformManage;
import cn.ibdsr.web.common.persistence.model.Room;
import cn.ibdsr.web.common.persistence.model.RoomSetting;
import cn.ibdsr.web.core.util.AmountFormatUtil;
import cn.ibdsr.web.core.util.DateUtils;
import cn.ibdsr.web.modular.platform.goodscenter.hotelroom.dao.HotelRoomDao;
import cn.ibdsr.web.modular.platform.goodscenter.hotelroom.service.HotelRoomService;
import cn.ibdsr.web.modular.platform.goodscenter.hotelroom.transfer.RoomQueryVO;
import cn.ibdsr.web.modular.shop.hotel.service.RoomService;
import cn.ibdsr.web.modular.shop.hotel.transfer.RoomQueryDTO;
import cn.ibdsr.web.modular.shop.hotel.transfer.RoomSettingListVO;
import cn.ibdsr.web.modular.shop.hotel.transfer.RoomVO;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description 商品中心-酒店房间Service
 * @Version V1.0
 * @CreateDate 2019/5/23 8:42
 *
 * Date           Author               Description
 * ------------------------------------------------------
 * 2019/5/23      ZhuJingrui           类说明
 */
@Service
public class HotelRoomServiceImpl implements HotelRoomService {

    @Autowired
    private HotelRoomDao hotelRoomDao;

    @Autowired
    private PlatformManageMapper platformManageMapper;

    @Autowired
    private RoomService roomService;

    @Autowired
    private RoomMapper roomMapper;

    @Autowired
    RoomSettingMapper roomSettingMapper;

    /**
     * 分页获取房间列表
     *
     * @param page
     * @param roomQueryDTO 房间查询对象DTO
     *                 status 房间状态（0-未上架，1-销售中；）
     *                 platformManage 平台管理: 0为已下架，1为未下架
     *                 roomName 房间名称
     *                 roomId 房间Id
     *                 shopName 酒店名
     *                 shopId 酒店id
     * @return
     */
    @Override
    public List<Map<String, Object>> roomList(Page<RoomQueryVO> page, RoomQueryDTO roomQueryDTO) {
        StaticCheck.check(roomQueryDTO);
        List<Map<String, Object>> roomList = hotelRoomDao.roomList(page, roomQueryDTO, page.getOrderByField(), page.isAsc());
        for (Map<String, Object> room: roomList) {
            if (!room.isEmpty()) {
                if (room.containsKey("price")) {
                    room.put("price", AmountFormatUtil.amountFormat(room.get("price")));
                }
                Integer platformManageStatus = (Integer)room.get("platformManage");
                if ( platformManageStatus == 0) {
                    PlatformManage platformManage = getPlatformInfos((Long)room.get("id"));
                    if (platformManage != null) {
                        room.put("platformManageReason", platformManage.getPlatformManageReason()); //平台端下架理由
                        room.put("shopApply", platformManage.getShopApply()); //商家是否申请上架，0未申请，1已申请
                    }
                }
            }
        }
        return roomList;
    }

    /**
     * 获得商品的平台审核信息
     * @param roomId 房间id
     * @return
     */
    private PlatformManage getPlatformInfos(Long roomId) {
        List<PlatformManage> platformManageList = platformManageMapper.selectList(
                new EntityWrapper<PlatformManage>()
                        .eq("room_id", roomId)
                        .eq("is_deleted", IsDeleted.NORMAL.getCode())
                        .orderBy("created_time",false)
                        .last("LIMIT 1"));
        if (platformManageList == null || 0 == platformManageList.size()){
            return null;
        }
        else {
            return platformManageList.get(0);
        }
    }

    /**
     * 系统下架房间
     *
     * @param roomId 商品ID
     * @param reason 下架原因
     * @return
     */
    @Override
    public void offShelfRoom(Long roomId, String reason) {
        //插入数据
        RoomVO roomDetail = roomService.getRoomInfo(roomId);
        PlatformManage platformManage = new PlatformManage();
        platformManage.setCreatedUser(ShiroKit.getUser().getId());
        platformManage.setCreatedTime(new Date());
        platformManage.setRoomId(roomId);
        platformManage.setShopId(roomDetail.getShopId());
        platformManage.setPlatformManage(PlatformManageStatus.OFFSHELF.getCode());
        platformManage.setPlatformManageReason(reason);
        platformManage.insert();

        //更新房间字段信息
        Room room = roomMapper.selectById(roomId);
        if (ToolUtil.isNotEmpty(room)) {
            room.setPlatformManage(PlatformManageStatus.OFFSHELF.getCode());
            room.updateById();
        }
    }

    /**
     * 查询房态详情
     * @param shopId 酒店ID
     * @param currentDate 当前时间
     * @return
     */
    @Override
    public List<RoomSettingListVO> roomSettingList(Page<RoomSettingListVO> page, Long shopId, String currentDate) {
        if (ToolUtil.isOneEmpty(currentDate)) { //查询日期不能为空
            throw new BussinessException(BizExceptionEnum.DATE_NULL);
        }
        List<RoomSettingListVO> roomSettingListVOS = new ArrayList<>();

        //查询日期的未来15天日期列表
        List<String> futureDates = DateUtils.getFetureTimeList(currentDate, Const.ROOM_SETTING_SHOW_DAY_LIMIT);

        // 查找出该酒店的所有房间
        List<Room> roomList = roomMapper.selectList(
                new EntityWrapper<Room>()
                        .eq("shop_id", shopId)
                        .eq("is_deleted", IsDeleted.NORMAL.getCode())
        );
        for (Room room: roomList) {
            List<RoomSetting> roomSettings = roomSettingMapper.selectList(
                    new EntityWrapper<RoomSetting>()
                            .eq("room_id", room.getId())
                            .eq("is_deleted", IsDeleted.NORMAL.getCode())
            );
            //如果查询结果为空，说明该房间没有设置过，按默认设置
            if (roomSettings == null || 0 == roomSettings.size()) {
                roomSettingListVOS.add(roomService.defaultRoomSet(room, futureDates));
            }
            //如果查询结果不为空，说明房间设置过，需要查出房间的已定数量情况
            else {
                roomSettingListVOS.add(roomService.getRoomSetInfo(room, futureDates));
            }
        }
        return roomSettingListVOS;
    }

    /**
     * 平台审核房间是否可以重新上架
     *
     * @param roomId 房间ID
     * @param isAgree 是否同意上架；1-同意，0-不同意
     * @return
     */
    @Override
    public void roomPlatformCheck(Long roomId, Boolean isAgree) {
        if (isAgree) {  //同意
            //更新商品表字段信息
            Room room = roomMapper.selectById(roomId);
            if (ToolUtil.isNotEmpty(room)) {
                room.setPlatformManage(PlatformManageStatus.ONSHELF.getCode());
                room.updateById();
            }

            //更新平台审核表信息
            List<PlatformManage> platformManageList = platformManageMapper.selectList(
                    new EntityWrapper<PlatformManage>()
                            .eq("room_id", roomId)
                            .eq("is_deleted", IsDeleted.NORMAL.getCode())
                            .orderBy("created_time", false)
                            .last("LIMIT 1")
            );
            if (platformManageList != null && platformManageList.size() > 0) {
                PlatformManage platformManage = platformManageList.get(0);
                platformManage.setPlatformManage(PlatformManageStatus.ONSHELF.getCode());
                platformManage.updateById();
            }
        }
        else { //不同意
            //更新平台审核表信息
            List<PlatformManage> platformManageList = platformManageMapper.selectList(
                    new EntityWrapper<PlatformManage>()
                            .eq("room_id", roomId)
                            .eq("is_deleted", IsDeleted.NORMAL.getCode())
                            .orderBy("created_time", false)
                            .last("LIMIT 1")
            );
            if (platformManageList != null && platformManageList.size() > 0) {
                PlatformManage platformManage = platformManageList.get(0);
                platformManage.setShopApply(ShopApplyStatus.NO.getCode());
                platformManage.updateById();
            }
        }
    }
}
