package cn.ibdsr.web.core.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description: 类功能和用法的说明
 * @Version: V1.0
 * @CreateUser: xujincai
 * @CreateDate: 2019/5/8 13:54
 * <p>
 * Date Author Description
 * ------------------------------------------------------
 * 2019/5/8 xujincai init
 */
public class HttpUtils {

    public static final String REQUEST_METHOD_GET = "GET";

    public static final String REQUEST_METHOD_POST = "POST";

    /**
     * @param contentType
     * @param defaultCharset
     * @return String
     */
    public static String getCharset(String contentType, String defaultCharset) {
        if (StringUtils.isNotBlank(contentType)) {
            if (StringUtils.containsIgnoreCase(contentType, "charset=")) {
                String regex = ".*charset=([^;]*).*";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(contentType);
                if (matcher.find()) {
                    return matcher.group(1);
                } else {
                    return defaultCharset;
                }
            }
            if (StringUtils.containsIgnoreCase(contentType, "encoding=")) {
                defaultCharset = StringUtils.substringAfter(contentType, "encoding=");
            }
        }
        return defaultCharset;
    }

    /**
     * HTTP请求
     *
     * @param url         请求URL
     * @param method      请求方法
     * @param requestBody 请求体
     * @return String
     * @throws IOException    IOException
     * @throws ParseException ParseException
     */
    public static String request(String url, String method, String requestBody) throws IOException, ParseException {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            CloseableHttpResponse response = null;
            //GET请求
            if (StringUtils.equalsIgnoreCase(REQUEST_METHOD_GET, method)) {
                HttpGet httpGet = new HttpGet(url);
                response = client.execute(httpGet);
            }
            //POST请求
            if (StringUtils.equalsIgnoreCase(REQUEST_METHOD_POST, method)) {
                HttpPost httpPost = new HttpPost(url);
                if (null != requestBody) {
                    StringEntity stringEntity = new StringEntity(requestBody, StandardCharsets.UTF_8);
                    httpPost.setEntity(stringEntity);
                }
                response = client.execute(httpPost);
            }
            HttpEntity responseEntity = response.getEntity();
            return EntityUtils.toString(responseEntity, getCharset(responseEntity.getContentType(), StandardCharsets.UTF_8.toString()));
        }
    }
}
