package net.smooth.zhenxiactivity.service.impl;
import net.smooth.zhenxiactivity.mapper.ScratchRecordMapper;
import net.smooth.zhenxiactivity.model.ScratchRecord;
import net.smooth.zhenxiactivity.service.ScratchRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ScratchRecordServiceImpl implements ScratchRecordService {

    @Autowired
    private ScratchRecordMapper scratchRecordMapper;

    @Override
    public void addScratchRecord(ScratchRecord scratchRecord) {
        scratchRecordMapper.insertScratchRecord(scratchRecord);
    }

    @Override
    public ScratchRecord getScratchRecordById(int id) {
        return scratchRecordMapper.getScratchRecordById(id);
    }

    @Override
    public List<ScratchRecord> getScratchRecordsByUserId(String userId) {
        return scratchRecordMapper.getScratchRecordsByUserId(userId);
    }

    @Override
    public void updateScratchRecord(ScratchRecord scratchRecord) {
        scratchRecordMapper.updateScratchRecord(scratchRecord);
    }

    @Override
    public void deleteScratchRecord(int id) {
        scratchRecordMapper.deleteScratchRecord(id);
    }
}

