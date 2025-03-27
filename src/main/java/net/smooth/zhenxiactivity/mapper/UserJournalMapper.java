package net.smooth.zhenxiactivity.mapper;

import net.smooth.zhenxiactivity.model.UserJournal;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserJournalMapper {
    int insert(UserJournal userJournal);
    int update(UserJournal userJournal);
    UserJournal selectById(Integer journalId);
    List<UserJournal> selectAll();
    int delete(Integer journalId);
    List<UserJournal> selectByUserId(@Param("userId")String userId,@Param("journalType") String  journalType);
}
