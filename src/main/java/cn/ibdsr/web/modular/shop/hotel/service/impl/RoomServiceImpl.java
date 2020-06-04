package cn.ibdsr.web.modular.shop.hotel.service.impl;

import cn.ibdsr.core.base.tips.SuccessTip;
import cn.ibdsr.core.check.StaticCheck;
import cn.ibdsr.core.shiro.ShiroKit;
import cn.ibdsr.core.shiro.ShiroUser;
import cn.ibdsr.core.shiro.ShopData;
import cn.ibdsr.core.util.ToolUtil;
import cn.ibdsr.web.common.constant.Const;
import cn.ibdsr.web.common.constant.state.IsDeleted;
import cn.ibdsr.web.common.constant.state.hotel.HotelOrderStatus;
import cn.ibdsr.web.common.constant.state.hotel.RoomSettingStatus;
import cn.ibdsr.web.common.constant.state.shop.ShopApplyStatus;
import cn.ibdsr.web.common.exception.BizExceptionEnum;
import cn.ibdsr.web.common.exception.BussinessException;
import cn.ibdsr.web.common.persistence.dao.*;
import cn.ibdsr.web.common.persistence.model.*;
import cn.ibdsr.web.core.util.AmountFormatUtil;
import cn.ibdsr.web.core.util.DateUtils;
import cn.ibdsr.web.core.util.ImageUtil;
import cn.ibdsr.web.modular.shop.hotel.dao.RoomDao;
import cn.ibdsr.web.modular.shop.hotel.dao.RoomIntroDao;
import cn.ibdsr.web.modular.shop.hotel.transfer.*;
import cn.ibdsr.web.modular.shop.hotel.warpper.RoomImgWarpper;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.ibdsr.web.modular.shop.hotel.service.RoomService;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description 酒店商家端房间管理Service
 * @Version V1.0
 * @CreateDate 2019/5/7 15:41
 *
 * Date           Author               Description
 * ------------------------------------------------------
 * 2019/5/7      ZhuJingrui            类说明
 */
@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private ShopUserMapper shopUserMapper;

    @Autowired
    private RoomImgMapper roomImgMapper;

    @Autowired
    private RoomIntroMapper roomIntroMapper;

    @Autowired
    private RoomMapper roomMapper;

    @Autowired
    private RoomSettingMapper roomSettingMapper;

    @Autowired
    private HotelOrderMapper hotelOrderMapper;

    @Autowired
    private RoomIntroDao roomIntroDao;

    @Autowired
    private RoomDao roomDao;

    @Autowired
    private PlatformManageMapper platformManageMapper;

    private static final int ROOM_IMAGE_MAX = 10;

    /**
     * @Description 新增房间
     * @param room 房间对象
     * @param imageListJsonString 房间图片Json字符串
     * @param content 房间详情描述
     * @return
     */
    @Override
    public SuccessTip insert(Room room, String imageListJsonString, String content) {
        //校验
        if (StringUtils.isBlank(room.getName())) {
            throw new BussinessException(BizExceptionEnum.ROOM_NAME_NOT_BLANK);
        }
        if (room.getName().length() > 10) {
            throw new BussinessException(BizExceptionEnum.ROOM_NAME_MAX);
        }
        if (StringUtils.isBlank(imageListJsonString)) {
            throw new BussinessException(BizExceptionEnum.ROOM_IMAGE_MIN);
        }
        List<RoomImg> roomImgList = JSONObject.parseArray(imageListJsonString, RoomImg.class);
        if (roomImgList.size() == 0) {
            throw new BussinessException(BizExceptionEnum.ROOM_IMAGE_MIN);
        }
        if (roomImgList.size() > ROOM_IMAGE_MAX) {
            throw new BussinessException(BizExceptionEnum.GOODS_IMAGE_MAX);
        }
        if (room.getPrice() == null || new BigDecimal(room.getPrice().intValue()).compareTo(room.getPrice()) !=0
                || room.getPrice().compareTo(new BigDecimal(1)) < 0 || room.getPrice().compareTo(new BigDecimal(100000)) > 0) {
            throw new BussinessException(BizExceptionEnum.ROOM_PRICE_MIN_MAX);
        }

        //插入操作
        ShiroUser shiroUser = ShiroKit.getUser();
        Date now = new Date();
        room.setShopId(((ShopData) shiroUser.getData()).getShopId());
        room.setMainImg(ImageUtil.cutImageURL(roomImgList.get(0).getImg())); // 商品主图片
        room.setCreatedUser(shiroUser.getId());
        room.setCreatedTime(now);
        room.setPrice(room.getPrice().multiply(new BigDecimal(100)));
        room.insert();

        for (RoomImg roomImg : roomImgList) {
            roomImg.setRoomId(room.getId());
            roomImg.setImg(ImageUtil.cutImageURL(roomImg.getImg()));
            roomImg.setCreatedTime(now);
            roomImg.setCreatedUser(shiroUser.getId());
            roomImg.insert();
        }

        if (StringUtils.isNotBlank(content)) {
            RoomIntro roomIntro = new RoomIntro();
            roomIntro.setRoomId(room.getId());
            roomIntro.setIntroContent(content);
            roomIntro.setCreatedUser(shiroUser.getId());
            roomIntro.setCreatedTime(now);
            roomIntro.insert();
        }

        return new SuccessTip();
    }

    /**
     * @Description 编辑房间
     *
     * @param roomId 房间id
     * @param room 房间对象
     * @param imageListJsonString 房间图片Json字符串
     * @param content 房间详情描述
     * @return
     */
    @Override
    public SuccessTip update(Long roomId, Room room, String imageListJsonString, String content) {
        //判断是否存在该房间
        if (ToolUtil.isOneEmpty(roomId)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        //校验
        if (StringUtils.isBlank(room.getName())) {
            throw new BussinessException(BizExceptionEnum.ROOM_NAME_NOT_BLANK);
        }
        if (room.getName().length() > 10) {
            throw new BussinessException(BizExceptionEnum.ROOM_NAME_MAX);
        }
        if (StringUtils.isBlank(imageListJsonString)) {
            throw new BussinessException(BizExceptionEnum.ROOM_IMAGE_MIN);
        }
        List<RoomImg> roomImgList = JSONObject.parseArray(imageListJsonString, RoomImg.class);
        if (roomImgList.size() == 0) {
            throw new BussinessException(BizExceptionEnum.GOODS_IMAGE_MIN);
        }
        if (roomImgList.size() > ROOM_IMAGE_MAX) {
            throw new BussinessException(BizExceptionEnum.GOODS_IMAGE_MAX);
        }
        if (room.getPrice() == null || new BigDecimal(room.getPrice().intValue()).compareTo(room.getPrice()) !=0
                || room.getPrice().compareTo(new BigDecimal(1)) < 0 || room.getPrice().compareTo(new BigDecimal(100000)) > 0) {
            throw new BussinessException(BizExceptionEnum.ROOM_PRICE_MIN_MAX);
        }

        //编辑操作
        room.setId(roomId);
        //更新基本信息
        Date now = new Date();
        ShiroUser shiroUser = ShiroKit.getUser();
        // 商品主图片
        room.setMainImg(ImageUtil.cutImageURL(roomImgList.get(0).getImg()));
        room.setModifiedUser(shiroUser.getId());
        room.setModifiedTime(now);
        room.setPrice(room.getPrice().multiply(new BigDecimal(100)));
        room.updateById();

        //更新图片信息
        roomImgMapper.delete(new EntityWrapper<RoomImg>().eq("room_id", roomId));
        for (RoomImg roomImg : roomImgList) {
            roomImg.setRoomId(room.getId());
            roomImg.setImg(ImageUtil.cutImageURL(roomImg.getImg()));
            roomImg.setCreatedTime(now);
            roomImg.setCreatedUser(shiroUser.getId());
            roomImg.insert();
        }
        //更新详情信息
        RoomIntro roomIntro = new RoomIntro();
        roomIntro.setIntroContent(content);
        roomIntroMapper.update(roomIntro, new EntityWrapper<RoomIntro>().eq("room_id", roomId));
        return new SuccessTip();
    }

    /**
     * @Description 根据roomId查找房间基本信息
     * @param roomId 房间id
     * @return
     */
    @Override
    public RoomVO getRoomInfo(Long roomId) {

        //判断是否存在该房间
        if (ToolUtil.isOneEmpty(roomId)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }

        //房间的基本属性信息
        RoomVO roomInfo = roomDao.getRoomInfo(roomId);
        if (roomInfo == null) {
            throw new BussinessException(BizExceptionEnum.DATA_ERROR);
        }
        roomInfo.setPrice(AmountFormatUtil.amountFormat(roomInfo.getPrice()));

        //获取商品的图片集
        JSONArray imgJson = (JSONArray) JSON.toJSON(getRoomImgByRoomId(roomId));
        roomInfo.setImageList(imgJson);

        //获取商品详情描述
        roomInfo.setIntroContent(roomIntroDao.getIntroContentByRoomId(roomId));

        return roomInfo;
    }

    /**
     * @param roomId 房间id
     * @return
     * @Description 根据roomId查找商品的图片集
     */
    private List<RoomImg> getRoomImgByRoomId(Long roomId) {
        Wrapper<RoomImg> entityWrapper = new EntityWrapper<>();
        List<Map<String, Object>> roomImgs = roomImgMapper.selectMaps(
                entityWrapper.eq("room_id", roomId)
                        .eq("is_deleted", Boolean.FALSE));

        return (List<RoomImg>) new RoomImgWarpper(roomImgs).warp();
    }

    /**
     * 分页查询房间列表
     *
     * @param page
     * @param roomQueryDTO 商品查询对象DTO
     *                      status 商品状态（0-未上架，1-销售中；）
     *                      roomName 商品名称
     *                      minPrice 价格范围最低
     *                      maxPrice 价格范围最高
     * @return
     */
    @Override
    public List<Map<String, Object>> list(Page<RoomListVO> page, RoomQueryDTO roomQueryDTO) {
        StaticCheck.check(roomQueryDTO);

        // 登录用户所属店铺ID
        ShiroUser shiroUser = ShiroKit.getUser();
        roomQueryDTO.setShopId(((ShopData) shiroUser.getData()).getShopId());

        // 价格元 => 分
        if (null != roomQueryDTO.getMinPrice()) {
            roomQueryDTO.setMinPrice(roomQueryDTO.getMinPrice().multiply(BigDecimal.valueOf(100)));
        }
        if (null != roomQueryDTO.getMaxPrice()) {
            roomQueryDTO.setMaxPrice(roomQueryDTO.getMaxPrice().multiply(BigDecimal.valueOf(100)));
        }

        List<Map<String, Object>> roomList = roomDao.list(page, roomQueryDTO, page.getOrderByField(), page.isAsc());
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
     * 批量更新商品状态：上架、下架
     *
     * @param roomIds 房间ID数组
     * @param statusCode 房间状态（0-下架，1-上架；）
     */
    @Override
    public void batchUpdateRoomStatus(Long[] roomIds, Integer statusCode) {
        if (roomIds == null || roomIds.length == 0) {
            throw new BussinessException(BizExceptionEnum.ROOM_ID_IS_NULL);
        }

        List<Room> dataList = roomMapper.selectList(
                new EntityWrapper()
                        .in("id", roomIds)
                        .eq("is_deleted", IsDeleted.NORMAL.getCode()));
        Boolean isExist = (dataList != null) && (dataList.size() > 0);
        if (!isExist) {
            throw new BussinessException(BizExceptionEnum.ROOM_IS_NOT_EXIST);
        }
        roomDao.batchUpdateRoomStatus(roomIds, statusCode, ShiroKit.getUser().getId());
    }

    /**
     * 删除房间
     * @param roomIds 房间ID数组
     */
    @Override
    public void deleteRoom(Long[] roomIds) {
        if (roomIds == null || roomIds.length == 0) {
            throw new BussinessException(BizExceptionEnum.ROOM_ID_IS_NULL);
        }

        List<Room> dataList = roomMapper.selectList(
                new EntityWrapper()
                        .in("id", roomIds)
                        .eq("is_deleted", IsDeleted.NORMAL.getCode()));
        Boolean isExist = (dataList != null) && (dataList.size() > 0);
        if (!isExist) {
            throw new BussinessException(BizExceptionEnum.ROOM_IS_NOT_EXIST);
        }
        //删除房间
        roomDao.deleteRoom(roomIds, ShiroKit.getUser().getId(), IsDeleted.DELETED.getCode());

        //删除对应房间的房态设置
        for(Long id : roomIds){
            List<RoomSetting> roomSettingList = roomSettingMapper.selectList(
                    new EntityWrapper()
                            .eq("is_deleted", IsDeleted.NORMAL.getCode())
                            .eq("room_id", id)
            );
            Boolean Exist = (roomSettingList != null) && (roomSettingList.size() > 0);
            if (Exist) {
                roomDao.deleteRoomSetting(id, ShiroKit.getUser().getId(), IsDeleted.DELETED.getCode());
            }
        }
    }

    /**
     * 查询房态设置列表
     * @param roomId 房间ID，0表示查询所有房间
     * @param currentDate 当前时间
     * @return
     */
    @Override
    public List<RoomSettingListVO> roomSettingList(Page<RoomSettingListVO> page, Long roomId, String currentDate) {
        //判断是否存在该房间
        if (ToolUtil.isOneEmpty(roomId)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        if (ToolUtil.isOneEmpty(currentDate)) { //查询日期不能为空
            throw new BussinessException(BizExceptionEnum.DATE_NULL);
        }

        // 登录用户所属店铺ID
        ShiroUser shiroUser = ShiroKit.getUser();
        Long shopId = ((ShopData) shiroUser.getData()).getShopId();

        List<RoomSettingListVO> roomSettingListVOS = new ArrayList<>();

        //查询日期的未来15天日期列表
        List<String> futureDates = DateUtils.getFetureTimeList(currentDate, Const.ROOM_SETTING_SHOW_DAY_LIMIT);

        // 查找出该酒店的所有房间
        List<Room> roomList = roomMapper.selectList(
                new EntityWrapper<Room>()
                        .eq("shop_id", shopId)
                        .eq("is_deleted", IsDeleted.NORMAL.getCode())
        );

        //如果roomId为0，查询该酒店的所有房间的房态设置；对已经设置过房间查找出来进行拼接设置，对没有设置过的房间设置默认
        if (0 == roomId) {
            for (Room room: roomList) {
                List<RoomSetting> roomSettings = roomSettingMapper.selectList(
                        new EntityWrapper<RoomSetting>()
                                .eq("room_id", room.getId())
                                .eq("is_deleted", IsDeleted.NORMAL.getCode())
                );
                //如果查询结果为空，说明该房间没有设置过，按默认设置
                if (roomSettings == null || 0 == roomSettings.size()) {
                    roomSettingListVOS.add(defaultRoomSet(room, futureDates));
                }
                //如果查询结果不为空，说明房间设置过，需要查出房间的已定数量情况
                else {
                    roomSettingListVOS.add(getRoomSetInfo(room, futureDates));
                    }
                }
            return roomSettingListVOS;
        }
        //如果roomId不为0，查询该酒店的所有房间
        else {
            Room room = roomMapper.selectById(roomId);
            roomSettingListVOS.add(getRoomSetInfo(room, futureDates));
            return roomSettingListVOS;
            }
    }

    /**
     * 设置15天的默认房态信息
     * @param room Room对象
     * @param dayList 15天时间列表
     * @return
     */
    public List<Map<String, Object>> defaultSetting (Room room, List<String> dayList) {
        List<Map<String, Object>> roomSetting = new ArrayList<>();
        for (String day: dayList) {
            roomSetting.add(roomSetOneDay(room, day));
        }
        return roomSetting;
    }

    /**
     * 设置某一天默认房态信息
     * @param room Room对象
     * @param day 某一天的日期
     * @return
     */
    public Map<String, Object> roomSetOneDay (Room room, String day) {
        if (room.getPrice() == null || new BigDecimal(room.getPrice().intValue()).compareTo(room.getPrice()) !=0
                || room.getPrice().compareTo(new BigDecimal(1)) < 0 || room.getPrice().compareTo(new BigDecimal(100000)) > 0) {
            throw new BussinessException(BizExceptionEnum.ROOM_PRICE_MIN_MAX);
        }
        Map<String, Object> roomSetting = new HashMap<>();
        roomSetting.put("date",day);
        roomSetting.put("status", "关");
        roomSetting.put("price", AmountFormatUtil.amountFormat(room.getPrice()));
        return roomSetting;
    }

    /**
     * 给从未设置过房态信息的房间设置默认房态信息
     * @param room Room对象
     * @param dayList 15天时间列表
     * @return
     */
    @Override
    public RoomSettingListVO defaultRoomSet (Room room, List<String> dayList) {
        RoomSettingListVO roomSettingListVO = new RoomSettingListVO();
        roomSettingListVO.setRoomId(room.getId());  //房间id
        roomSettingListVO.setName(room.getName());  //房间名称
        List<Map<String, Object>> roomSetting = defaultSetting(room, dayList);//15天默认房态设置
        roomSettingListVO.setRoomSettingList(roomSetting);
        return roomSettingListVO;
    }

    /**
     * 重新拼接设置过房态的房间的房态信息
     * @param room Room对象
     * @param dayList 15天时间列表
     * @return
     */
    @Override
    public RoomSettingListVO getRoomSetInfo(Room room, List<String> dayList) {
        if (room.getPrice() == null || new BigDecimal(room.getPrice().intValue()).compareTo(room.getPrice()) !=0
                || room.getPrice().compareTo(new BigDecimal(1)) < 0 || room.getPrice().compareTo(new BigDecimal(100000)) > 0) {
            throw new BussinessException(BizExceptionEnum.ROOM_PRICE_MIN_MAX);
        }
        RoomSettingListVO roomSettingListVO = new RoomSettingListVO();
        roomSettingListVO.setRoomId(room.getId());
        roomSettingListVO.setName(room.getName());

        //根据房间id查找该房间的已定情况和数量
        Map<String, Integer> roomOrderInfos = roomOrderInfos(room.getId());

        // 设置15天的房态情况
        List<Map<String, Object>> roomSettingResult = new ArrayList<>();

        for (String day: dayList) {
            List<RoomSetting> roomSettingList = roomSettingMapper.selectList(
                    new EntityWrapper<RoomSetting>()
                            .eq("room_id", room.getId())
                            .eq("date", DateUtils.translateStrToDate(day))
                            .eq("is_deleted", IsDeleted.NORMAL.getCode())
                            .last("LIMIT 1")
            );
            if (roomSettingList == null || 0 == roomSettingList.size()){//为空，表示该天该房间没有设置过，默认设置
                roomSettingResult.add(roomSetOneDay(room, day));
            }
            else { //不为空，表示该房间，该天设置过房态
                Map<String, Object> roomSet = new HashMap<>();
                RoomSetting roomSetting = roomSettingList.get(0);
                roomSet.put("date",day);
                roomSet.put("status", RoomSettingStatus.valueOf(roomSetting.getStatus()));
                roomSet.put("price", AmountFormatUtil.amountFormat(roomSetting.getPrice()));
                roomSet.put("number", roomSetting.getNumber());
                if (roomOrderInfos.containsKey(day)) {
                    roomSet.put("numOrder", roomOrderInfos.get(day));
                }
                roomSettingResult.add(roomSet);
            }
        }
        roomSettingListVO.setRoomSettingList(roomSettingResult);
        return roomSettingListVO;
    }

    /**
     * 判断是否存在某天的房态设置信息
     * @param roomSettings 房间设置列表
     * @param day 某天
     * @return
     */
    private Boolean isExistDate(List<Map<String, Object>> roomSettings, String day) {
        Boolean isExist = false;
        for (Map<String, Object> roomSetting: roomSettings) {
            if (roomSetting.get("date").equals(day)) {
                isExist = true;
            }
        }
        return isExist;
    }

    /**
     * 根据房间id查找该房间的已定情况和数量
     * @param roomId 房间id
     * @return
     */
    public Map<String, Integer> roomOrderInfos(Long roomId) {
        List<HotelOrder> hotelOrderList = hotelOrderMapper.selectList(
                new EntityWrapper<HotelOrder>()
                        .eq("room_id", roomId)
                        .eq("is_deleted", IsDeleted.NORMAL.getCode())
                        .in("status", new Integer []{ HotelOrderStatus.WAIT_CONFIRM.getCode(), HotelOrderStatus.WAIT_USE.getCode(),
                                HotelOrderStatus.USED.getCode(), HotelOrderStatus.FINISHED.getCode()})
        );
        Map<String, Integer> roomOder = new HashMap<>();
        for (HotelOrder hotelOrder : hotelOrderList) {
            Calendar calendar = Calendar.getInstance();
            Date date = hotelOrder.getCheckInDate();
            while (!date.equals(hotelOrder.getCheckOutDate())) {  //遍历入住时间到离店时间段
                String dateStr = DateUtils.birthdayDate(date);
                if (roomOder.containsKey(dateStr)) {
                    roomOder.put(dateStr, roomOder.get(dateStr) + hotelOrder.getRoomNumber());
                }
                else {
                    roomOder.put(dateStr, hotelOrder.getRoomNumber());
                }
                calendar.setTime(date);
                calendar.add(Calendar.DATE, 1); // 日期加1天
                date = calendar.getTime();
            }

        }
        return roomOder;
    }

    /**
     * 判断某房间在某天是否设置过
     * @param roomId 房间ID
     * @param date 日期
     * @return
     */
    public Boolean roomSettingIsExist(Long roomId, Date date) {
        List<RoomSetting> roomSettingList = roomSettingMapper.selectList(
                new EntityWrapper<RoomSetting>()
                        .eq("room_id", roomId)
                        .eq("date", date)
                        .eq("is_deleted", IsDeleted.NORMAL.getCode())
        );
        if (null == roomSettingList || 0 == roomSettingList.size()) {
            // 不存在该房间在该日期的设置信息，返回false
            return false;
        }
        return true;
    }

    /**
     * 单个房态设置
     * @param roomSettingDTO 房态设置对象
     *  roomId 房间ID
     *  date 日期
     *  number 房间数量
     *  price 房间价格
     *  status 房间开关状态
     * @return
     */
    @Override
    public SuccessTip setRoom(RoomSettingDTO roomSettingDTO) {
        if (ToolUtil.isOneEmpty(roomSettingDTO.getRoomId())) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        if (roomSettingDTO.getPrice() == null || new BigDecimal(roomSettingDTO.getPrice().intValue()).compareTo(roomSettingDTO.getPrice()) !=0
                || roomSettingDTO.getPrice().compareTo(new BigDecimal(1)) < 0 || roomSettingDTO.getPrice().compareTo(new BigDecimal(100000)) > 0) {
            throw new BussinessException(BizExceptionEnum.ROOM_PRICE_MIN_MAX);
        }
        SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd" );
        Date time = null;
        try {
            time = sdf.parse(roomSettingDTO.getDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String today = DateUtils.birthdayDate(new Date());
        if (time.before(DateUtils.translateStrToDate(today))) { //历史日期不能设置
            throw new BussinessException(BizExceptionEnum.PAST_CANNOT_SET);
        }
        if (time.after(DateUtils.getFetureTime(new Date(), Const.ROOM_CAN_SET_DAY_LIMIT))) { //90天之后的房间不能设置
            throw new BussinessException(BizExceptionEnum.AFTER_NINETY_CANNOT_SET);
        }
        ShiroUser shiroUser = ShiroKit.getUser();
        Boolean isExist = roomSettingIsExist(roomSettingDTO.getRoomId(), time);
        if (isExist) { //如果房态设置表存在设置信息，更新房态设置信息
            RoomSetting roomSetting = new RoomSetting();
            roomSetting.setNumber(roomSettingDTO.getNumber());
            roomSetting.setPrice(roomSettingDTO.getPrice().multiply(new BigDecimal(100)));
            roomSetting.setStatus(roomSettingDTO.getStatus());
            roomSetting.setModifiedTime(new Date());
            roomSetting.setModifiedUser(shiroUser.getId());
            roomSetting.update(new EntityWrapper<RoomSetting>()
                    .eq("room_id", roomSettingDTO.getRoomId()).eq("date", time)
                    .eq("is_deleted", IsDeleted.NORMAL.getCode()));
//            roomSettingMapper.update(roomSetting, new EntityWrapper<RoomSetting>()
//                    .eq("room_id", roomSettingDTO.getRoomId()).eq("date", time)
//                    .eq("is_deleted", IsDeleted.NORMAL.getCode()));

        }
        else { //如果不存在，则新插入一天房态设置记录
            RoomSetting roomSetting = new RoomSetting();
            roomSetting.setShopId(((ShopData) shiroUser.getData()).getShopId());
            roomSetting.setRoomId(roomSettingDTO.getRoomId());
            roomSetting.setDate(time);
            roomSetting.setNumber(roomSettingDTO.getNumber());
            roomSetting.setPrice(roomSettingDTO.getPrice().multiply(new BigDecimal(100)));
            roomSetting.setStatus(roomSettingDTO.getStatus());
            roomSetting.setCreatedTime(new Date());
            roomSetting.setCreatedUser(shiroUser.getId());
            roomSetting.insert();
        }

        return new SuccessTip();
    }

    /**
     * 批量房态设置
     * @param roomSettingDTO 房态设置对象
     *  roomId 房间ID
     *  startDate 起始日期
     *  endDate 起始日期
     *  weekdayList 工作日列表:1－7分别代表周一到周日
     *  number 房间数量
     *  price 房间价格
     *  status 房间开关状态
     * @return
     */
    @Override
    public SuccessTip batchSetRoom(RoomSettingDTO roomSettingDTO) {
        SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd" );
        Date startTime = null;
        Date endTime = null;
        try {
            startTime = sdf.parse(roomSettingDTO.getStartDate());
            endTime = sdf.parse(roomSettingDTO.getEndDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String today = DateUtils.birthdayDate(new Date());
        if (startTime.before(DateUtils.translateStrToDate(today))) { //历史日期不能设置
            throw new BussinessException(BizExceptionEnum.PAST_CANNOT_SET);
        }
        if (endTime.after(DateUtils.getFetureTime(new Date(), Const.ROOM_CAN_SET_DAY_LIMIT))) { //90天之后的房间不能设置
            throw new BussinessException(BizExceptionEnum.AFTER_NINETY_CANNOT_SET);
        }
        List<String> dateList = new ArrayList<>();
        for (Integer weekday: roomSettingDTO.getWeekdayList()) {
            dateList.addAll(DateUtils.getDayOfWeek(roomSettingDTO.getStartDate(), roomSettingDTO.getEndDate(), weekday));
        }
        if (0 == dateList.size()) {
            throw new BussinessException(BizExceptionEnum.DATE_IS_NULL);
        }
        for (String date: dateList) {
            roomSettingDTO.setDate(date);
            setRoom(roomSettingDTO);
        }
        return new SuccessTip();
    }

    /**
     * 被系统下架的房间批量申请重新上架
     *
     * @param roomIds 房间ID数组
     */
    @Override
    public void applyRoomOnShelf(Long[] roomIds) {
        if (roomIds == null || roomIds.length == 0) {
            throw new BussinessException(BizExceptionEnum.ROOM_ID_IS_NULL);
        }

        List<Room> dataList = roomMapper.selectList(
                new EntityWrapper()
                        .in("id", roomIds)
                        .eq("is_deleted", IsDeleted.NORMAL.getCode()));
        Boolean isExist = (dataList != null) && (dataList.size() > 0);
        if (!isExist) {
            throw new BussinessException(BizExceptionEnum.ROOM_IS_NOT_EXIST);
        }
        else {
            for (Room room: dataList) {
                //更新平台审核表信息
                List<PlatformManage> platformManageList = platformManageMapper.selectList(
                        new EntityWrapper<PlatformManage>()
                                .eq("room_id", room.getId())
                                .eq("is_deleted", IsDeleted.NORMAL.getCode())
                                .orderBy("created_time", false)
                                .last("LIMIT 1")
                );
                if (platformManageList != null && platformManageList.size() > 0) {
                    PlatformManage platformManage = platformManageList.get(0);
                    platformManage.setShopApply(ShopApplyStatus.YES.getCode());
                    platformManage.updateById();
                }
            }
        }
    }
}
