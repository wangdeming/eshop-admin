package cn.ibdsr.web.core.util;

import cn.ibdsr.core.exception.GunsException;
import com.alibaba.fastjson.JSONObject;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;

/**
 * @Description: 高德地图工具类
 * @Version: V1.0
 * @CreateUser: xujincai
 * @CreateDate: 2019/5/8 13:46
 * <p>
 * Date Author Description
 * ------------------------------------------------------
 * 2019/5/8 xujincai init
 */
public class MapUtils {
    /**
     * 两点间距离测量
     *
     * @param key         Web服务API类型KEY
     * @param origin      出发点，例：117.834270,28.540772
     * @param destination 目的地，例：117.834270,28.540772
     * @return 距离
     * @throws IOException    IOException
     * @throws ParseException ParseException
     */
    public static Integer distance(String key, String origin, String destination) throws IOException, ParseException {
        String url = "https://restapi.amap.com/v3/distance?origins=" + origin + "&destination=" + destination + "&key=" + key;
        String result = HttpUtils.request(url, HttpUtils.REQUEST_METHOD_GET, null);
        JSONObject resultJson = JSONObject.parseObject(result);
        if (resultJson.getIntValue("status") == 1) {
            return resultJson.getJSONArray("results").getJSONObject(0).getInteger("distance");
        } else {
            throw new GunsException(500, result);
        }
    }
}
