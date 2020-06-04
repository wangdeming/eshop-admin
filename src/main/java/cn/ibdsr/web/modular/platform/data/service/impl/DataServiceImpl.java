package cn.ibdsr.web.modular.platform.data.service.impl;

import cn.ibdsr.core.base.tips.SuccessDataTip;
import cn.ibdsr.core.support.HttpKit;
import cn.ibdsr.web.common.constant.state.order.RefundStatus;
import cn.ibdsr.web.common.constant.state.shop.ShopType;
import cn.ibdsr.web.common.exception.BizExceptionEnum;
import cn.ibdsr.web.common.exception.BussinessException;
import cn.ibdsr.web.common.persistence.dao.HotelOrderMapper;
import cn.ibdsr.web.common.persistence.dao.HotelOrderRefundMapper;
import cn.ibdsr.web.common.persistence.dao.ShopOrderMapper;
import cn.ibdsr.web.common.persistence.dao.ShopOrderRefundMapper;
import cn.ibdsr.web.common.persistence.dao.ViewRecordMapper;
import cn.ibdsr.web.common.persistence.model.HotelOrder;
import cn.ibdsr.web.common.persistence.model.HotelOrderRefund;
import cn.ibdsr.web.common.persistence.model.ShopOrder;
import cn.ibdsr.web.common.persistence.model.ShopOrderRefund;
import cn.ibdsr.web.common.persistence.model.ViewRecord;
import cn.ibdsr.web.modular.platform.data.service.DataService;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description: 类功能和用法的说明
 * @Version: V1.0
 * @CreateUser: xujincai
 * @CreateDate: 2019/5/28 8:44
 * <p>
 * Date Author Description
 * ------------------------------------------------------
 * 2019/5/28 xujincai init
 */
@Service
public class DataServiceImpl implements DataService {

    @Autowired
    private ViewRecordMapper viewRecordMapper;

    @Autowired
    private ShopOrderMapper shopOrderMapper;

    @Autowired
    private HotelOrderMapper hotelOrderMapper;

    @Autowired
    private ShopOrderRefundMapper shopOrderRefundMapper;

    @Autowired
    private HotelOrderRefundMapper hotelOrderRefundMapper;

    /**
     * @param type      报表纬度，1为日报，2为周报，3为月报
     * @param beginDate 开始时间
     * @param endDate   结束时间
     * @return List<JSONObject>
     */
    private static List<JSONObject> countDate(Integer type, LocalDate beginDate, LocalDate endDate) {
        List<JSONObject> jsonObjectList = new ArrayList<>();
        JSONObject jsonObject;
        //日报
        if (type == 1) {
            for (long i = 0, length = beginDate.until(endDate.plusDays(1), ChronoUnit.DAYS); i < length; i++) {
                jsonObject = new JSONObject();
                jsonObject.put("beginDate", beginDate.plusDays(i));
                jsonObject.put("endDate", beginDate.plusDays(i));
                jsonObjectList.add(jsonObject);
            }
        }
        //周报
        if (type == 2) {
            beginDate = beginDate.minusDays(beginDate.getDayOfWeek().getValue() - 1);
            endDate = endDate.plusDays(7 - endDate.getDayOfWeek().getValue()).plusDays(1);
            for (long i = 0, length = beginDate.until(endDate, ChronoUnit.WEEKS); i < length; i++) {
                jsonObject = new JSONObject();
                jsonObject.put("beginDate", beginDate.plusDays(7 * i));
                jsonObject.put("endDate", beginDate.plusDays(7 * i).plusDays(6));
                jsonObjectList.add(jsonObject);
            }
        }
        //月报
        if (type == 3) {
            beginDate = beginDate.with(TemporalAdjusters.firstDayOfMonth());
            endDate = endDate.with(TemporalAdjusters.firstDayOfNextMonth());
            for (long i = 0, length = beginDate.until(endDate, ChronoUnit.MONTHS); i < length; i++) {
                jsonObject = new JSONObject();
                jsonObject.put("beginDate", beginDate.plusMonths(i));
                jsonObject.put("endDate", beginDate.plusMonths(i).with(TemporalAdjusters.lastDayOfMonth()));
                jsonObjectList.add(jsonObject);
            }
        }
        return jsonObjectList;
    }

    private void checkParameter(Integer type, LocalDate beginDate, LocalDate endDate, Integer shopType) {
        if (type == null) {
            throw new BussinessException(BizExceptionEnum.PARAMETER_ERROR);
        }
        if (type != 1 && type != 2 && type != 3) {
            throw new BussinessException(BizExceptionEnum.PARAMETER_ERROR);
        }
        if (beginDate == null) {
            throw new BussinessException(BizExceptionEnum.PARAMETER_ERROR);
        }
        if (endDate == null) {
            throw new BussinessException(BizExceptionEnum.PARAMETER_ERROR);
        }
        if (beginDate.isAfter(endDate)) {
            throw new BussinessException(BizExceptionEnum.BEGIN_DATE_NOT_AFTER_END_DATE);
        }
        if (shopType != null && shopType != ShopType.STORE.getCode() && shopType != ShopType.HOTEL.getCode()) {
            throw new BussinessException(BizExceptionEnum.PARAMETER_ERROR);
        }
    }

    private List<JSONObject> count(List<JSONObject> dateList, Integer type, LocalDate beginDate, LocalDate endDate, Integer shopType, Long shopId) {
        checkParameter(type, beginDate, endDate, shopType);
        String range = "";
        if (shopType == null && shopId == null) {
            range = "全平台";
        }
        if (shopType != null && shopType == ShopType.STORE.getCode() && shopId == null) {
            range = "全部特产店铺";
        }
        if (shopType != null && shopType == ShopType.HOTEL.getCode() && shopId == null) {
            range = "全部酒店店铺";
        }
        if (shopType != null && shopId != null) {
            range = String.valueOf(shopId);
        }
        List<JSONObject> list = new ArrayList<>();
        JSONObject jsonObject;
        for (JSONObject date : dateList) {
            //时间
            jsonObject = new JSONObject();
            if (type == 1) {
                jsonObject.put("date", date.getString("beginDate"));
            }
            if (type == 2) {
                jsonObject.put("date", date.getString("beginDate") + "~" + date.getString("endDate"));
            }
            if (type == 3) {
                jsonObject.put("date", ((LocalDate) date.get("beginDate")).format(DateTimeFormatter.ofPattern("yyyy-MM")));
            }
            //范围
            jsonObject.put("range", range);

            //浏览量
            EntityWrapper<ViewRecord> viewRecordEntityWrapper = new EntityWrapper<>();
            viewRecordEntityWrapper.ge("created_time", date.getString("beginDate")).lt("created_time", ((LocalDate) date.get("endDate")).plusDays(1));
            if (shopType != null) {
                viewRecordEntityWrapper.where("(SELECT type FROM shop WHERE shop.id=view_record.shop_id)=" + shopType);
            }
            if (shopId != null) {
                viewRecordEntityWrapper.eq("shop_id", shopId);
            }
            List<ViewRecord> viewRecordList = viewRecordMapper.selectList(viewRecordEntityWrapper);
            jsonObject.put("viewCount", viewRecordList.size());

            //访客数
            viewRecordEntityWrapper.setSqlSelect("DISTINCT ip");
            viewRecordList = viewRecordMapper.selectList(viewRecordEntityWrapper);
            jsonObject.put("visitorCount", viewRecordList.size());

            //付款订单数
            EntityWrapper<ShopOrder> shopOrderEntityWrapper = new EntityWrapper<>();
            shopOrderEntityWrapper.ge("payment_time", date.getString("beginDate")).lt("payment_time", ((LocalDate) date.get("endDate")).plusDays(1));
            if (shopType != null) {
                shopOrderEntityWrapper.where("(SELECT type FROM shop WHERE shop.id=shop_order.shop_id)=" + shopType);
            }
            if (shopId != null) {
                shopOrderEntityWrapper.eq("shop_id", shopId);
            }
            List<ShopOrder> shopOrderList = shopOrderMapper.selectList(shopOrderEntityWrapper);
            EntityWrapper<HotelOrder> hotelOrderEntityWrapper = new EntityWrapper<>();
            hotelOrderEntityWrapper.ge("pay_datetime", date.getString("beginDate")).lt("pay_datetime", ((LocalDate) date.get("endDate")).plusDays(1));
            if (shopType != null) {
                hotelOrderEntityWrapper.where("(SELECT type FROM shop WHERE shop.id=hotel_order.shop_id)=" + shopType);
            }
            if (shopId != null) {
                hotelOrderEntityWrapper.eq("shop_id", shopId);
            }
            List<HotelOrder> hotelOrderList = hotelOrderMapper.selectList(hotelOrderEntityWrapper);
            jsonObject.put("orderCount", shopOrderList.size() + hotelOrderList.size());

            //付款人数
            shopOrderEntityWrapper.setSqlSelect("DISTINCT user_id");
            shopOrderList = shopOrderMapper.selectList(shopOrderEntityWrapper);
            hotelOrderEntityWrapper.setSqlSelect("DISTINCT user_id");
            hotelOrderList = hotelOrderMapper.selectList(hotelOrderEntityWrapper);
            jsonObject.put("payerCount", shopOrderList.size() + hotelOrderList.size());

            //访问-付费转化率： 时间段内，付款人数/访客数
            if (jsonObject.getBigDecimal("visitorCount").compareTo(BigDecimal.ZERO) == 0) {
                jsonObject.put("payInversionRate", "0%");
            } else {
                jsonObject.put("payInversionRate", jsonObject.getBigDecimal("payerCount").divide(jsonObject.getBigDecimal("visitorCount"), 4, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.TEN.multiply(BigDecimal.TEN)).stripTrailingZeros().toPlainString() + "%");
            }

            //付款金额
            shopOrderEntityWrapper.setSqlSelect("SUM(order_price) totalAmount");
            List<Map<String, Object>> shopOrderMapList = shopOrderMapper.selectMaps(shopOrderEntityWrapper);
            BigDecimal shopOrderTotalAmount = shopOrderMapList.get(0) == null ? BigDecimal.ZERO : new BigDecimal(shopOrderMapList.get(0).get("totalAmount").toString());
            hotelOrderEntityWrapper.setSqlSelect("SUM(total_amount) totalAmount");
            List<Map<String, Object>> hotelOrderMapList = hotelOrderMapper.selectMaps(hotelOrderEntityWrapper);
            BigDecimal hotelOrderTotalAmount = hotelOrderMapList.get(0) == null ? BigDecimal.ZERO : new BigDecimal(hotelOrderMapList.get(0).get("totalAmount").toString());
            jsonObject.put("totalPayAmount", shopOrderTotalAmount.add(hotelOrderTotalAmount).divide(BigDecimal.TEN.multiply(BigDecimal.TEN), 2, BigDecimal.ROUND_HALF_UP).stripTrailingZeros().toPlainString());

            //客单价：时间段内，付款金额/付款人数
            if (jsonObject.getBigDecimal("payerCount").compareTo(BigDecimal.ZERO) == 0) {
                jsonObject.put("customerUnitPrice", "0");
            } else {
                jsonObject.put("customerUnitPrice", jsonObject.getBigDecimal("totalPayAmount").divide(jsonObject.getBigDecimal("payerCount"), 2, BigDecimal.ROUND_HALF_UP).stripTrailingZeros().toPlainString());
            }

            //退款订单数
            EntityWrapper<ShopOrderRefund> shopOrderRefundEntityWrapper = new EntityWrapper<>();
            shopOrderRefundEntityWrapper.eq("status", RefundStatus.SUCCESS.getCode()).ge("expected_time", date.getString("beginDate")).lt("expected_time", ((LocalDate) date.get("endDate")).plusDays(1));
            if (shopType != null) {
                shopOrderRefundEntityWrapper.where("(SELECT type FROM shop WHERE shop.id=shop_order_refund.shop_id)=" + shopType);
            }
            if (shopId != null) {
                shopOrderRefundEntityWrapper.eq("shop_id", shopId);
            }
            List<ShopOrderRefund> shopOrderRefundList = shopOrderRefundMapper.selectList(shopOrderRefundEntityWrapper);
            EntityWrapper<HotelOrderRefund> hotelOrderRefundEntityWrapper = new EntityWrapper<>();
            hotelOrderRefundEntityWrapper.ge("expected_time", date.getString("beginDate")).lt("expected_time", ((LocalDate) date.get("endDate")).plusDays(1));
            if (shopType != null) {
                hotelOrderRefundEntityWrapper.where("(SELECT type FROM shop WHERE shop.id=hotel_order_refund.shop_id)=" + shopType);
            }
            if (shopId != null) {
                hotelOrderRefundEntityWrapper.eq("shop_id", shopId);
            }
            List<HotelOrderRefund> hotelOrderRefundList = hotelOrderRefundMapper.selectList(hotelOrderRefundEntityWrapper);
            jsonObject.put("refundOrderCount", shopOrderRefundList.size() + hotelOrderRefundList.size());

            //退款金额
            shopOrderRefundEntityWrapper.setSqlSelect("SUM(refund_amount) totalAmount");
            List<Map<String, Object>> shopOrderRefundMapList = shopOrderRefundMapper.selectMaps(shopOrderRefundEntityWrapper);
            BigDecimal shopOrderRefundTotalAmount = shopOrderRefundMapList.get(0) == null ? BigDecimal.ZERO : new BigDecimal(shopOrderRefundMapList.get(0).get("totalAmount").toString());
            hotelOrderRefundEntityWrapper.setSqlSelect("SUM(refund_amount) totalAmount");
            List<Map<String, Object>> hotelOrderRefundMapList = hotelOrderRefundMapper.selectMaps(hotelOrderRefundEntityWrapper);
            BigDecimal hotelOrderRefundTotalAmount = hotelOrderRefundMapList.get(0) == null ? BigDecimal.ZERO : new BigDecimal(hotelOrderRefundMapList.get(0).get("totalAmount").toString());
            jsonObject.put("totalRefundAmount", shopOrderRefundTotalAmount.add(hotelOrderRefundTotalAmount).divide(BigDecimal.TEN.multiply(BigDecimal.TEN), 2, BigDecimal.ROUND_HALF_UP).stripTrailingZeros().toPlainString());

            list.add(jsonObject);
        }
        return list;
    }

    /**
     * 折线图
     *
     * @param type      报表纬度，1为日报，2为周报，3为月报
     * @param beginDate 开始时间
     * @param endDate   结束时间
     * @param shopType  商店类型，1为特产店铺，2为酒店店铺
     * @param shopId    店铺ID
     * @return SuccessDataTip
     */
    @Override
    public SuccessDataTip chart(Integer type, String beginDate, String endDate, Integer shopType, Long shopId) {
        List<Map<String, Object>> maps = viewRecordMapper.selectMaps(new EntityWrapper<ViewRecord>().setSqlSelect("DATE_FORMAT(MIN(created_time),'%Y-%m-%d') beginDate", "DATE_FORMAT(MAX(created_time),'%Y-%m-%d') endDate"));
        if (StringUtils.isNotBlank(beginDate)) {
            try {
                LocalDate.parse(beginDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            } catch (Exception e) {
                throw new BussinessException(BizExceptionEnum.PARAMETER_ERROR);
            }
        } else {
            if (maps.size() > 0 && maps.get(0).get("beginDate") != null) {
                beginDate = (String) maps.get(0).get("beginDate");
            } else {
                beginDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            }
        }
        if (StringUtils.isNotBlank(endDate)) {
            try {
                LocalDate.parse(endDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            } catch (Exception e) {
                throw new BussinessException(BizExceptionEnum.PARAMETER_ERROR);
            }
        } else {
            if (maps.size() > 0 && maps.get(0).get("endDate") != null) {
                endDate = (String) maps.get(0).get("endDate");
            } else {
                endDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            }
        }
        List<JSONObject> dateList = countDate(type, LocalDate.parse(beginDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")), LocalDate.parse(endDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        List<JSONObject> list = count(dateList, type, LocalDate.parse(beginDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")), LocalDate.parse(endDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")), shopType, shopId);
        JSONObject returnJson = new JSONObject();
        returnJson.put("total", list.size());
        returnJson.put("rows", list);
        return new SuccessDataTip(returnJson);
    }

    /**
     * 表格
     *
     * @param type      报表纬度，1为日报，2为周报，3为月报
     * @param beginDate 开始时间
     * @param endDate   结束时间
     * @param shopType  商店类型，1为特产店铺，2为酒店店铺
     * @param shopId    店铺ID
     * @param offset
     * @param limit
     * @return SuccessDataTip
     */
    @Override
    public JSONObject table(Integer type, String beginDate, String endDate, Integer shopType, Long shopId, int offset, int limit) {
        List<Map<String, Object>> maps = viewRecordMapper.selectMaps(new EntityWrapper<ViewRecord>().setSqlSelect("DATE_FORMAT(MIN(created_time),'%Y-%m-%d') beginDate", "DATE_FORMAT(MAX(created_time),'%Y-%m-%d') endDate"));
        if (StringUtils.isNotBlank(beginDate)) {
            try {
                LocalDate.parse(beginDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            } catch (Exception e) {
                throw new BussinessException(BizExceptionEnum.PARAMETER_ERROR);
            }
        } else {
            if (maps.size() > 0 && maps.get(0).get("beginDate") != null) {
                beginDate = (String) maps.get(0).get("beginDate");
            } else {
                beginDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            }
        }
        if (StringUtils.isNotBlank(endDate)) {
            try {
                LocalDate.parse(endDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            } catch (Exception e) {
                throw new BussinessException(BizExceptionEnum.PARAMETER_ERROR);
            }
        } else {
            if (maps.size() > 0 && maps.get(0).get("endDate") != null) {
                endDate = (String) maps.get(0).get("endDate");
            } else {
                endDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            }
        }
        List<JSONObject> dateList = countDate(type, LocalDate.parse(beginDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")), LocalDate.parse(endDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        JSONObject returnJson = new JSONObject();
        returnJson.put("total", dateList.size());
        if ((offset + limit) > dateList.size()) {
            dateList = dateList.subList(offset, dateList.size());
        } else {
            dateList = dateList.subList(offset, offset + limit);
        }
        returnJson.put("rows", count(dateList, type, LocalDate.parse(beginDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")), LocalDate.parse(endDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")), shopType, shopId));
        return returnJson;
    }

    /**
     * 导出
     *
     * @param type      报表纬度，1为日报，2为周报，3为月报
     * @param beginDate 开始时间
     * @param endDate   结束时间
     * @param shopType  商店类型，1为特产店铺，2为酒店店铺
     * @param shopId    店铺ID
     */
    @Override
    public void download(Integer type, String beginDate, String endDate, Integer shopType, Long shopId) {
        List<Map<String, Object>> maps = viewRecordMapper.selectMaps(new EntityWrapper<ViewRecord>().setSqlSelect("DATE_FORMAT(MIN(created_time),'%Y-%m-%d') beginDate", "DATE_FORMAT(MAX(created_time),'%Y-%m-%d') endDate"));
        if (StringUtils.isNotBlank(beginDate)) {
            try {
                LocalDate.parse(beginDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            } catch (Exception e) {
                throw new BussinessException(BizExceptionEnum.PARAMETER_ERROR);
            }
        } else {
            if (maps.size() > 0 && maps.get(0).get("beginDate") != null) {
                beginDate = (String) maps.get(0).get("beginDate");
            } else {
                beginDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            }
        }
        if (StringUtils.isNotBlank(endDate)) {
            try {
                LocalDate.parse(endDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            } catch (Exception e) {
                throw new BussinessException(BizExceptionEnum.PARAMETER_ERROR);
            }
        } else {
            if (maps.size() > 0 && maps.get(0).get("endDate") != null) {
                endDate = (String) maps.get(0).get("endDate");
            } else {
                endDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            }
        }
        List<JSONObject> dateList = countDate(type, LocalDate.parse(beginDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")), LocalDate.parse(endDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        List<JSONObject> list = count(dateList, type, LocalDate.parse(beginDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")), LocalDate.parse(endDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")), shopType, shopId);
        try (XSSFWorkbook wb = new XSSFWorkbook()) {
            Sheet sheet = wb.createSheet();
            Row row = sheet.createRow(0);
            Cell cell = row.createCell(0);
            cell.setCellValue("时间");
            cell = row.createCell(1);
            cell.setCellValue("范围");
            cell = row.createCell(2);
            cell.setCellValue("浏览量");
            cell = row.createCell(3);
            cell.setCellValue("访客数");
            cell = row.createCell(4);
            cell.setCellValue("付款人数");
            cell = row.createCell(5);
            cell.setCellValue("付款订单数");
            cell = row.createCell(6);
            cell.setCellValue("付款金额");
            cell = row.createCell(7);
            cell.setCellValue("访问-付费转化率");
            cell = row.createCell(8);
            cell.setCellValue("客单价");
            cell = row.createCell(9);
            cell.setCellValue("退款订单数");
            cell = row.createCell(10);
            cell.setCellValue("退款金额");
            for (int i = 0, length = list.size(); i < length; i++) {
                row = sheet.createRow(i + 1);
                cell = row.createCell(0);
                cell.setCellValue(list.get(i).getString("date"));
                cell = row.createCell(1);
                cell.setCellValue(list.get(i).getString("range"));
                cell = row.createCell(2);
                cell.setCellValue(list.get(i).getString("viewCount"));
                cell = row.createCell(3);
                cell.setCellValue(list.get(i).getString("visitorCount"));
                cell = row.createCell(4);
                cell.setCellValue(list.get(i).getString("payerCount"));
                cell = row.createCell(5);
                cell.setCellValue(list.get(i).getString("orderCount"));
                cell = row.createCell(6);
                cell.setCellValue(list.get(i).getString("totalPayAmount"));
                cell = row.createCell(7);
                cell.setCellValue(list.get(i).getString("payInversionRate"));
                cell = row.createCell(8);
                cell.setCellValue(list.get(i).getString("customerUnitPrice"));
                cell = row.createCell(9);
                cell.setCellValue(list.get(i).getString("refundOrderCount"));
                cell = row.createCell(10);
                cell.setCellValue(list.get(i).getString("totalRefundAmount"));
            }
            HttpServletResponse response = HttpKit.getResponse();
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("报表.xlsx", StandardCharsets.UTF_8.displayName()));
            ServletOutputStream outputStream = response.getOutputStream();
            wb.write(outputStream);
            if (outputStream != null) {
                outputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
