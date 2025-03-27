package net.smooth.zhenxiactivity.service.impl;
import net.smooth.zhenxiactivity.mapper.UserJournalMapper;
import net.smooth.zhenxiactivity.model.UserJournal;
import net.smooth.zhenxiactivity.service.UserJournalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class UserJournalServiceImpl implements UserJournalService {
    @Autowired
    private UserJournalMapper userJournalMapper;

    @Override
    public int addUserJournal(UserJournal userJournal) {
        return userJournalMapper.insert(userJournal);
    }

    @Override
    public int updateUserJournal(UserJournal userJournal) {
        return userJournalMapper.update(userJournal);
    }

    @Override
    public UserJournal getUserJournalById(Integer journalId) {
        return userJournalMapper.selectById(journalId);
    }

    @Override
    public List<UserJournal> getAllUserJournals() {
        return userJournalMapper.selectAll();
    }

    @Override
    public int removeUserJournal(Integer journalId) {
        return userJournalMapper.delete(journalId);
    }

    @Override
    public List<UserJournal> getUserJournalsByUserId(String userId) {
        return userJournalMapper.selectByUserId(userId);
    }
}
