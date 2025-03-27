package net.smooth.zhenxiactivity.model;
import lombok.Data;
@Data
public class Prize {

    private int id;
    private String prizeName;
    private int prizeType;
    private int stock;
    private double probability;
    private double prizeValue;
    private boolean isActive;
    private boolean isPhysical;
    private String templateId;
    private int effectiveDays;


    // 奖品类型常量
    public static final int COUPON = 1;  // 优惠券
    public static final int PHYSICAL_GOODS = 2;  // 实物奖品
}
