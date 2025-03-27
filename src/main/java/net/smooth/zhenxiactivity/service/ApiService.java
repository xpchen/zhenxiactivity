package net.smooth.zhenxiactivity.service;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.TreeMap;
import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.Getter;
import net.smooth.zhenxiactivity.dto.response.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Getter
@Service
public class ApiService {
    private static final Logger logger = LoggerFactory.getLogger(ApiService.class);
    // 公共常量
    @Value("${api.app.appKey}")
    private  String appKey;
    @Value("${api.app.secret}")
    private String appSecret;

    @Value("${api.app.url}")
    private String serviceUrl;

    @Value("${api.app.host}")
    private String host;

    private String requestData="";
    private String responseData="";

    // API请求方法
    public <T> ApiResponse<T> callApi(String method, String accessToken, Object bizContent) throws Exception {
        // 构建请求体
        long timestamp = System.currentTimeMillis() / 1000;  // 获取当前时间戳
        String sign = generateSign(method, timestamp, accessToken, bizContent);
        // 请求参数
        Map<String, Object> params = new TreeMap<>();
        params.put("app_key", appKey);
        params.put("method", method);
        // 如果是"buyer.oauth2.authorization"方法，可以不传access_token
        if (!"buyer.oauth2.authorization".equalsIgnoreCase(method)) {
            params.put("access_token", accessToken); // 注意：根据需要传递 access_token
        }
        params.put("timestamp", timestamp);
        params.put("sign", sign);
        if(bizContent!=null)
        {
            params.put("biz_content", new Gson().toJson(bizContent)); // 序列化 bizContent
        }
        // 构建请求数据
        this.requestData = new Gson().toJson(params);
        // 发送HTTP POST请求
        this.responseData = sendPostRequest(serviceUrl, host, requestData);
        // 解析响应
        // 使用 TypeToken 来处理泛型
        Type responseType = new TypeToken<ApiResponse<T>>() {
        }.getType();
        return new Gson().fromJson(responseData, responseType);
    }

    // 生成签名
    private String generateSign(String method, long timestamp, String accessToken, Object bizContent) {
        // 排序参数
        Map<String, String> sortedParams = new TreeMap<>();
        sortedParams.put("app_key", appKey);
        sortedParams.put("method", method);
        if (!"buyer.oauth2.authorization".equalsIgnoreCase(method)) {
            sortedParams.put("access_token", accessToken); // 注意：根据需要传递 access_token
        }
        //sortedParams.put("access_token", accessToken);
        sortedParams.put("timestamp", String.valueOf(timestamp));
        if(bizContent!=null)
        {
            sortedParams.put("biz_content", new Gson().toJson(bizContent));
        }
        // 拼接字符串
        StringBuilder signStr = new StringBuilder();
        for (Map.Entry<String, String> entry : sortedParams.entrySet()) {
            signStr.append(entry.getKey()).append(entry.getValue());
        }
        // 加密：添加appSecret前后，进行MD5加密
        String toSign = appSecret + signStr.toString() + appSecret;
        return md5(toSign);
    }

    // MD5加密
    private String md5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException("Error during MD5 encryption", e);
        }
    }

    // 发送POST请求
    private String sendPostRequest(String urlStr, String host, String requestData) throws Exception {
        logger.debug("request=>"+urlStr);
        logger.debug("host=>"+host);
        StringBuilder request = new StringBuilder();
        request.append("\nurl=>"+urlStr);
        request.append("\nhost=>"+host);
        request.append("\nrequest body=>"+requestData);
        logger.debug(request.toString());
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("host", host);
        conn.setDoOutput(true);
        conn.setDoInput(true);
        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = requestData.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }
        try (InputStreamReader reader = new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8)) {
            StringBuilder response = new StringBuilder();
            int ch;
            while ((ch = reader.read()) != -1) {
                response.append((char) ch);
            }
            String responseString = response.toString();
            logger.debug(responseString);
            return responseString;
        }
    }
}

