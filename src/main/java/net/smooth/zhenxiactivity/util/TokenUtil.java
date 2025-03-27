package net.smooth.zhenxiactivity.util;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
public class TokenUtil {
    public static String getAccessToken() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        // 嘗試從 header 中獲取 access_token
        String accessToken = request.getHeader("access_token");
        if (accessToken != null && !accessToken.isEmpty()) {
            return accessToken;
        }
        // 如果 header 中沒有，再嘗試從請求參數中獲取
        accessToken = request.getParameter("access_token");
        if (accessToken != null && !accessToken.isEmpty()) {
            return accessToken;
        }
        // 最後嘗試從 POST 參數中獲取 access_token
        if ("POST".equalsIgnoreCase(request.getMethod())) {
            accessToken = request.getParameter("access_token");
            if (accessToken != null && !accessToken.isEmpty()) {
                return accessToken;
            }
        }
        // 如果沒有找到，返回 null 或者拋出異常
        return null;
    }
}

