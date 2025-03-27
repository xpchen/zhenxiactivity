package net.smooth.zhenxiactivity.service;

import net.smooth.zhenxiactivity.model.UserJournal;

import java.util.List;

public interface UserJournalService {
    int addUserJournal(UserJournal userJournal);
    int updateUserJournal(UserJournal userJournal);
    UserJournal getUserJournalById(Integer journalId);
    List<UserJournal> getAllUserJournals();
    int removeUserJournal(Integer journalId);
    List<UserJournal> getUserJournalsByUserId(String userId);  // 新增的根据 userId 查询的方法
}
