package net.smooth.zhenxiactivity.model;

import lombok.Data;

import java.util.Date;

@Data
public class UserBadge {
    private Integer badgeId;
    private String userId;
    private String nickName;
    private String path;
    private Date dateEarned;
    private String type;
}
