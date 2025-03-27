package net.smooth.zhenxiactivity.dto.request;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class CouponDispatchRequest {

    private String userId;              // 用户ID
    private String mobile;              // 手机号
    private String openId;              // openId
    private String unionId;             // unionId
    private String tenantId;            // 租户ID (必填)
    private Map<String, Object> extend; // 业务拓展字段
    private List<DispatchCoupon> dispatchCoupons; // 优惠券发放列表

    // Getter 和 Setter 方法省略

    @Data
    public static class ExtendFields {
        private Integer source;         // 活动来源
        private Integer relateId;       // 专员类型
        private Integer accountType;    // 专员类型
        private String businessCode;    // 业务码

        // Getter 和 Setter 方法省略
    }

    @Data
    public static class DispatchCoupon {
        private String templateId;  // 优惠券模板ID (必填)
        private int quantity;       // 发放数量 (必填)
        private int useType;        // 使用类型：1固定期限 2领取后期限 (必填)
        private Integer expireDays; // 领取后期限内失效（天）
        private Long useStartDate;  // 固定期限开始时间（当前时间戳13位）
        private Long useEndDate;    // 固定期限结束时间（当前时间戳13位）

        // Getter 和 Setter 方法省略
    }
}
