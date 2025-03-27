package net.smooth.zhenxiactivity.mapper;

import net.smooth.zhenxiactivity.model.UserJournal;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface UserJournalMapper {
    int insert(UserJournal userJournal);
    int update(UserJournal userJournal);
    UserJournal selectById(Integer journalId);
    List<UserJournal> selectAll();
    int delete(Integer journalId);
    List<UserJournal> selectByUserId(String userId);  // 新增的根据 userId 查询的方法
}
