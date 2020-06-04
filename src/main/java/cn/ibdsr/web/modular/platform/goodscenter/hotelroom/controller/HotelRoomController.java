package cn.ibdsr.web.modular.platform.goodscenter.hotelroom.controller;

import cn.ibdsr.core.base.controller.BaseController;
import cn.ibdsr.core.base.tips.SuccessDataTip;
import cn.ibdsr.web.common.constant.factory.PageFactory;
import cn.ibdsr.web.modular.platform.goodscenter.hotelroom.service.HotelRoomService;
import cn.ibdsr.web.modular.platform.goodscenter.hotelroom.transfer.RoomQueryVO;
import cn.ibdsr.web.modular.shop.hotel.service.RoomService;
import cn.ibdsr.web.modular.shop.hotel.transfer.RoomQueryDTO;
import cn.ibdsr.web.modular.shop.hotel.transfer.RoomSettingListVO;
import cn.ibdsr.web.modular.shop.hotel.transfer.RoomVO;
import cn.ibdsr.web.modular.shop.hotel.warpper.RoomListWarpper;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

/**
 * @Description 商品中心-酒店房间控制器
 * @Version V1.0
 * @CreateDate 2019/5/23 8:42
 *
 * Date           Author               Description
 * ------------------------------------------------------
 * 2019/5/23      ZhuJingrui            类说明
 */
@Controller
@RequestMapping("/platform/goodscenter")
public class HotelRoomController extends BaseController {

    @Autowired
    private HotelRoomService hotelRoomService;

    @Autowired
    private RoomService roomService;

    private String PREFIX = "/platform/goodscenter/hotelroom/";

    /**
     * 跳转到商品中心-酒店房间首页
     */
    @RequestMapping("/hotel")
    public String hotelIndex() {
        return PREFIX + "hotelRoom.html";
    }

    /**
     * 跳转到商品中心-房间详情页
     */
    @RequestMapping("/roomDetailPage/{roomId}")
    public String roomDetailPage(@PathVariable Long roomId, Model model) {
        model.addAttribute(roomId);
        RoomVO roomInfo = roomService.getRoomInfo(roomId);
        model.addAttribute("roomInfo", roomInfo);
        return PREFIX + "roomDetail.html";
    }

    /**
     * 跳转到商品中心-房态详情页
     */
    @RequestMapping("/roomSetDetailPage")
    public String roomSetDetailPage() {
        return PREFIX + "roomSetDetail.html";
    }

    /**
     * 分页获取房间列表
     *
     * @param roomQueryDTO 房间查询对象DTO
     *                 status 房间状态（0-未上架，1-销售中；）
     *                 platformManage 平台管理: 0为已下架，1为未下架
     *                 roomName 房间名称
     *                 roomId 房间Id
     *                 shopName 酒店名
     *                 shopId 酒店id
     * @return
     */
    @RequestMapping(value = "/roomList")
    @ResponseBody
    public Object roomList(RoomQueryDTO roomQueryDTO) {
        Page<RoomQueryVO> page = new PageFactory<RoomQueryVO>().defaultPage();
        List<Map<String, Object>> result = hotelRoomService.roomList(page, roomQueryDTO);
        page.setRecords((List<RoomQueryVO>) new RoomListWarpper(result).warp());
        return super.packForBT(page);
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
     * 系统下架房间
     *
     * @param roomId 商品ID
     * @param reason 下架原因
     * @return
     */
    @RequestMapping(value = "/offShelfRoom")
    @ResponseBody
    public Object offShelfRoom(@RequestParam Long roomId, @RequestParam String reason) {
        hotelRoomService.offShelfRoom(roomId, reason);
        return SUCCESS_TIP;
    }

    /**
     * 查询房态详情
     *
     * @param shopId      酒店Id
     * @param currentDate 当前时间
     * @return
     */
    @RequestMapping(value = "/roomSetDetail")
    @ResponseBody
    public Object roomSetDetail(Long shopId, String currentDate) {
        Page<RoomSettingListVO> page = new PageFactory<RoomSettingListVO>().defaultPage();
        List<RoomSettingListVO> roomSettingListVOList = hotelRoomService.roomSettingList(page, shopId, currentDate);
        page.setRecords(roomSettingListVOList);
        return new SuccessDataTip(super.packForBT(page));
    }

    /**
     * 平台审核房间是否可以重新上架
     *
     * @param roomId 房间ID
     * @param isAgree 是否同意上架；1-同意，0-不同意
     * @return
     */
    @RequestMapping(value = "/roomPlatformCheck")
    @ResponseBody
    public Object roomPlatformCheck(Long roomId, Boolean isAgree) {
        hotelRoomService.roomPlatformCheck(roomId, isAgree);
        return SUCCESS_TIP;
    }
}
