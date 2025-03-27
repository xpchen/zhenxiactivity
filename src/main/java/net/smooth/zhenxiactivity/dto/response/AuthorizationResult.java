package net.smooth.zhenxiactivity.dto.response;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class AuthorizationResult {
    @SerializedName("access_token")
    private String accessToken; // 授权token
    @SerializedName("refresh_token")
    private String refresh_token; // 刷新token
    @SerializedName("current_timestamp")
    private long currenTimestamp; // oauth服务端当前时间戳
    @SerializedName("access_token_expiry_time")
    private long accessTokenExpiryTime; // 授权token过期时间戳
    @SerializedName("refresh_token_expiry_time")
    private long refreshTokenExpiryTime; // 刷新token过期时间戳
    @SerializedName("user_info")
    private UserInfo userInfo; // 用户信息
}

