package net.smooth.zhenxiactivity.mapper;

import net.smooth.zhenxiactivity.model.ScratchRecord;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ScratchRecordMapper {

    void insertScratchRecord(ScratchRecord scratchRecord);


    ScratchRecord getScratchRecordById(int id);

    List<ScratchRecord> getScratchRecordsByUserId(String userId);


    void updateScratchRecord(ScratchRecord scratchRecord);

    void deleteScratchRecord(int id);
}
