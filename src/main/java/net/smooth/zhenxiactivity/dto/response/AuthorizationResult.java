package net.smooth.zhenxiactivity.dto.response;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class AuthorizationResult {

    private String accessToken; // 授权token

    private String refresh_token; // 刷新token

    private long currenTimestamp; // oauth服务端当前时间戳

    private long accessTokenExpiryTime; // 授权token过期时间戳

    private long refreshTokenExpiryTime; // 刷新token过期时间戳

    private UserInfo userInfo; // 用户信息
}

