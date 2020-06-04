package cn.ibdsr.web.core.http;


import cn.ibdsr.web.common.constant.factory.ConstantFactory;
import cn.ibdsr.web.common.constant.state.MessageStatus;
import cn.ibdsr.web.modular.shop.account.transfer.SmsgDto;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

/**
 * @Description 短信模块发送get与post请求。
 * @Author Administrator.xiaorongsheng
 * @Date created in 2018/4/16 11:46
 * @Modifed by
 */
public class HttpUtils {

    public static SmsgDto sendPostMsg(Map<String, String> paramMap, String Url) {

        String charset = "utf-8";
        StringBuffer resultBuffer = null;
        // 构建请求参数
        String sbParams = JoiningTogetherParams(paramMap);
        URLConnection con = null;
        OutputStreamWriter osw = null;
        BufferedReader br = null;
        try {
            URL realUrl = new URL(ConstantFactory.me().getUrls().getMessageurl() + "" + Url);
            // 打开和URL之间的连接
            con = realUrl.openConnection();
            // 设置通用的请求属性
            con.setRequestProperty("accept", "*/*");
            con.setRequestProperty("connection", "Keep-Alive");
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            con.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            con.setDoOutput(true);
            con.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            osw = new OutputStreamWriter(con.getOutputStream(), charset);
            if (sbParams != null && sbParams.length() > 0) {
                // 发送请求参数
                osw.write(sbParams);
                // flush输出流的缓冲
                osw.flush();
            }
            // 定义BufferedReader输入流来读取URL的响应
            resultBuffer = new StringBuffer();
            int contentLength = Integer.parseInt(con.getHeaderField("Content-Length"));
            if (contentLength > 0) {
                br = new BufferedReader(new InputStreamReader(con.getInputStream(), charset));
                String temp;
                while ((temp = br.readLine()) != null) {
                    resultBuffer.append(temp);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (osw != null) {
                try {
                    osw.close();
                } catch (IOException e) {
                    osw = null;
                    throw new RuntimeException(e);
                }
            }
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    br = null;
                    throw new RuntimeException(e);
                }
            }
        }
        SmsgDto smsgDto = null;
        try {
            smsgDto = JSON.parseObject(resultBuffer.toString(), new TypeReference<SmsgDto>() {
            });
        } catch (Exception e) {

        } finally {
            if (null == smsgDto) {
                smsgDto = new SmsgDto();
                smsgDto.setCode(MessageStatus.ERRO.getCode());
                smsgDto.setMessage(MessageStatus.ERRO.getMessage());
            }
        }

        return smsgDto;
    }

    private static String JoiningTogetherParams(Map<String, String> paramMap) {
        if (paramMap == null || paramMap.isEmpty()) {
            return "";
        }
        StringBuffer sbParams = new StringBuffer();
        for (String key : paramMap.keySet()) {
            if (paramMap.get(key) != null) {
                sbParams.append(key);
                sbParams.append("=");
                sbParams.append(paramMap.get(key));
                sbParams.append("&");
            }
        }
        return sbParams.substring(0, sbParams.length() - 1);
    }


}
