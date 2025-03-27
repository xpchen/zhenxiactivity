package net.smooth.zhenxiactivity.model;

import lombok.Data;

import java.util.Date;

@Data
public class ScratchRecord {
    private int id;
    private String userId;  // 用户ID
    private int prizeId;    // 奖品ID
    private boolean isWinner; // 是否中奖
    private Date createdAt = new Date();

    // 新增的地址字段
    private String recipientName;  // 收货人姓名
    private String phoneNumber;    // 收货人电话
    private String addressLine1;   // 地址行1
    private String addressLine2;   // 地址行2
    private String city;           // 城市
    private String state;          // 州/省
    private String postalCode;     // 邮政编码
    private String country;        // 国家
}
