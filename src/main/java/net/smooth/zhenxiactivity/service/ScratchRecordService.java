package net.smooth.zhenxiactivity.service;

import net.smooth.zhenxiactivity.model.ScratchRecord;

import java.util.List;

public interface ScratchRecordService {

    void addScratchRecord(ScratchRecord scratchRecord);

    ScratchRecord getScratchRecordById(int id);

    List<ScratchRecord> getScratchRecordsByUserId(String userId);

    void updateScratchRecord(ScratchRecord scratchRecord);

    void deleteScratchRecord(int id);
}
