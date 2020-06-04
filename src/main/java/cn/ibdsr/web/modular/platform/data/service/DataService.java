package cn.ibdsr.web.modular.platform.data.service;

import cn.ibdsr.core.base.tips.SuccessDataTip;
import com.alibaba.fastjson.JSONObject;

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
public interface DataService {
    SuccessDataTip chart(Integer type, String beginDate, String endDate, Integer shopType, Long shopId);

    JSONObject table(Integer type, String beginDate, String endDate, Integer shopType, Long shopId, int offset, int limit);

    void download(Integer type, String beginDate, String endDate, Integer shopType, Long shopId);
}
