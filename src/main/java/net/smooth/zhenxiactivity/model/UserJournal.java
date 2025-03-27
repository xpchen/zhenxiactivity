package net.smooth.zhenxiactivity.model;

import lombok.Data;

import java.util.Date;

@Data
public class UserJournal {
    private Integer journalId;
    private String userId;
    private String nickName;
    private String content;
    private String path;
    private String type;
    private Date publishedDate;
}
