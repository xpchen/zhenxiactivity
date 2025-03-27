package net.smooth.zhenxiactivity.dto.request;

import lombok.Data;

@Data
public class GenerateJournalRequest {
    private String nickName;
    private String journalType;
    private String content;
}
