package cn.ibdsr.web.modular.shop.hotel.controller;

import cn.ibdsr.core.base.controller.BaseController;
import cn.ibdsr.core.base.tips.SuccessDataTip;
import cn.ibdsr.core.base.tips.SuccessTip;
import cn.ibdsr.web.common.constant.factory.PageFactory;
import cn.ibdsr.web.common.constant.state.hotel.RoomStatus;
import cn.ibdsr.web.common.persistence.model.Room;
import cn.ibdsr.web.modular.shop.hotel.service.RoomService;
import cn.ibdsr.web.modular.shop.hotel.transfer.RoomListVO;
import cn.ibdsr.web.modular.shop.hotel.transfer.RoomQueryDTO;
import cn.ibdsr.web.modular.shop.hotel.transfer.RoomSettingDTO;
import cn.ibdsr.web.modular.shop.hotel.transfer.RoomSettingListVO;
import cn.ibdsr.web.modular.shop.hotel.transfer.RoomVO;
import cn.ibdsr.web.modular.shop.hotel.warpper.RoomListWarpper;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @Description 酒店商家端房间管理控制器
 * @Version V1.0
 * @CreateDate 2019/5/7 15:41
 * <p>
 * Date           Author               Description
 * ------------------------------------------------------
 * 2019/5/7      ZhuJingrui            类说明
 */
@Controller
@RequestMapping("shop/hotel")
public class RoomController extends BaseController {

    @Autowired
    private RoomService roomService;

    private String PREFIX = "/shop/hotel/room/";

    /**
     * 跳转到房间管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "room.html";
    }

    /**
     * 跳转新增房间页面
     *
     * @return
     */
    @RequestMapping(value = "roomInsert")
    public String toInsert() {
        return PREFIX + "insert.html";
    }

    /**
     * 跳转编辑房间页面
     *
     * @return
     */
    @RequestMapping(value = "/roomUpdate/{roomId}")
    public String goodsUpdate(@PathVariable Long roomId, Model model) {
        model.addAttribute(roomId);
        RoomVO roomInfo = roomService.getRoomInfo(roomId);
        model.addAttribute("roomInfo", roomInfo);
        return PREFIX + "update.html";
    }

    /**
     * 房间详情
     *
     * @param roomId 商品ID
     * @return
     */
    @RequestMapping(value = "/roomDetail")
    @ResponseBody
    public Object goodsDetail(Long roomId) {
        RoomVO roomInfo = roomService.getRoomInfo(roomId);
        return new SuccessDataTip(roomInfo);
    }

    /**
     * @param room                房间对象
     * @param imageListJsonString 房间图片Json字符串
     * @param content             房间详情描述
     * @return
     * @Description 新增房间
     */
    @RequestMapping(value = "/insert")
    @ResponseBody
    public SuccessTip insert(Room room, String imageListJsonString, String content) {
        return roomService.insert(room, imageListJsonString, content);
    }

    /**
     * @param roomId              房间id
     * @param room                房间对象
     * @param imageListJsonString 房间图片Json字符串
     * @param content             房间详情描述
     * @return
     * @Description 编辑房间
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public SuccessTip update(Long roomId, Room room, String imageListJsonString, String content) {
        return roomService.update(roomId, room, imageListJsonString, content);
    }

    /**
     * 分页查询房间列表
     *
     * @param roomQueryDTO 商品查询对象DTO
     *                     status 商品状态（0-未上架，1-销售中；）
     *                     roomName 商品名称
     *                     minPrice 价格范围最低
     *                     maxPrice 价格范围最高
     * @return
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(RoomQueryDTO roomQueryDTO) {
        Page<RoomListVO> page = new PageFactory<RoomListVO>().defaultPage();
        List<Map<String, Object>> result = roomService.list(page, roomQueryDTO);
        page.setRecords((List<RoomListVO>) new RoomListWarpper(result).warp());
        return super.packForBT(page);
    }

    /**
     * 批量上架商品
     *
     * @param roomIds 商品ID数组
     * @return
     */
    @RequestMapping(value = "/onshelf")
    @ResponseBody
    public Object onshelf(@RequestParam Long[] roomIds) {
        roomService.batchUpdateRoomStatus(roomIds, RoomStatus.ONSHELF.getCode());
        return SUCCESS_TIP;
    }

    /**
     * 批量下架商品
     *
     * @param roomIds 商品ID数组
     * @return
     */
    @RequestMapping(value = "/offshelf")
    @ResponseBody
    public Object offshelf(@RequestParam Long[] roomIds) {
        roomService.batchUpdateRoomStatus(roomIds, RoomStatus.OFFSHELF.getCode());
        return SUCCESS_TIP;
    }

    /**
     * 被系统下架的房间批量申请重新上架
     *
     * @param roomIds 房间ID数组
     */
    @RequestMapping(value = "/applyRoomOnShelf")
    @ResponseBody
    public Object applyRoomOnShelf(@RequestParam Long[] roomIds) {
        roomService.applyRoomOnShelf(roomIds);
        return SUCCESS_TIP;
    }

    /**
     * 删除房间
     *
     * @param roomIds 房间ID
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object deleteGoods(@RequestParam(required = false) Long[] roomIds) {
        roomService.deleteRoom(roomIds);
        return SUCCESS_TIP;
    }

    /**
     * 跳转房态管理页面
     *
     * @return
     */
    @RequestMapping(value = "roomManage")
    public String toRoomManage() {
        return PREFIX + "roomManage.html";
    }

    /**
     * 跳转所有房态管理页面
     *
     * @return
     */
    @RequestMapping(value = "allRoomManage")
    public String toAllRoomManage() {
        return PREFIX + "allRoomManage.html";
    }

    /**
     * 跳转所有房态管理页面
     *
     * @return
     */
    @RequestMapping(value = "batchSetup")
    public String toBatchSetup() {
        return PREFIX + "batchSetup.html";
    }

    /**
     * 查询房态设置列表
     *
     * @param roomId      房间ID: 0表示查询所有房间
     * @param currentDate 当前时间
     * @return
     */
    @RequestMapping(value = "/roomSettingList")
    @ResponseBody
    public Object roomSettingList(Long roomId, String currentDate) {
        Page<RoomSettingListVO> page = new PageFactory<RoomSettingListVO>().defaultPage();
        List<RoomSettingListVO> roomSettingListVOList = roomService.roomSettingList(page, roomId, currentDate);
        page.setRecords(roomSettingListVOList);
        return new SuccessDataTip(super.packForBT(page));
    }

    /**
     * 单个房态设置
     *
     * @param roomSettingDTO 房态设置对象
     *                       roomId 房间ID
     *                       date 日期
     *                       number 房间数量
     *                       price 房间价格
     *                       status 房间开关状态
     * @return
     */
    @RequestMapping(value = "/setRoom")
    @ResponseBody
    public SuccessTip setRoom(RoomSettingDTO roomSettingDTO) {
        return roomService.setRoom(roomSettingDTO);
    }

    /**
     * 批量房态设置
     *
     * @param roomSettingDTO 房态设置对象
     *                       roomId 房间ID
     *                       startDate 起始日期
     *                       endDate 起始日期
     *                       weekdayList 工作日列表:1－7分别代表周一到周日
     *                       number 房间数量
     *                       price 房间价格
     *                       status 房间开关状态
     * @return
     */
    @RequestMapping(value = "/batchSetRoom")
    @ResponseBody
    public SuccessTip batchSetRoom(RoomSettingDTO roomSettingDTO) {
        return roomService.batchSetRoom(roomSettingDTO);
    }

}
