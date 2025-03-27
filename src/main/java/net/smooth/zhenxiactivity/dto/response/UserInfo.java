package net.smooth.zhenxiactivity.dto.response;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfo {

    private String userId; // 用户id
    @SerializedName("nick_name")
    private String nickName; // 用户昵称
    private String mobile; // 用户手机号
    @SerializedName("open_id")
    private String openId; // 用户openId
    @SerializedName("union_id")
    private String unionId; // 用户unionId
    @SerializedName("avatar_url")
    private String avatarUrl; // 用户头像
    @SerializedName("tenant_id")
    private String tenantId; // 所属品牌方唯一标识
    @SerializedName("register_timestamp")
    private long registerTimestamp; // 用户注册时间戳
    @SerializedName("register_source")
    private String registerSource; // 注册渠道值
}

