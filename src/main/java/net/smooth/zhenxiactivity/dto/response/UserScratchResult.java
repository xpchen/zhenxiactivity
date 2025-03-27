package net.smooth.zhenxiactivity.dto.response;
import lombok.Data;
import java.util.Date;
@Data
public class UserScratchResult {
    private int recordId;
    private String prizeName;
    private int prizeType;
    private Date winningTime;
    private int effectiveDays;
}
