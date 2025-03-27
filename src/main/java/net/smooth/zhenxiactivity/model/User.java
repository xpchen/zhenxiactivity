package net.smooth.zhenxiactivity.model;

import lombok.Data;

@Data
public class User {
    private String userId;  // 用户id
    private String nickName; // 用户昵称
    private String mobile;   // 用户手机号
    private String openId;   // 用户openId
    private String unionId;  // 用户unionId
    private String avatarUrl; // 用户头像
    private String tenantId; // 所属品牌方唯一标识
    private long registerTimestamp; // 用户注册时间戳
    private String registerSource; // 注册渠道值
}