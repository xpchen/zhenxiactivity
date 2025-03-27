package net.smooth.zhenxiactivity.service.impl;
import net.smooth.zhenxiactivity.mapper.PrizeMapper;
import net.smooth.zhenxiactivity.model.Prize;
import net.smooth.zhenxiactivity.service.PrizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Random;

@Service
public class PrizeServiceImpl implements PrizeService {

    private final PrizeMapper prizeMapper;

    @Autowired
    public PrizeServiceImpl(PrizeMapper prizeMapper) {
        this.prizeMapper = prizeMapper;
    }

    @Override
    public List<Prize> getActivePrizes() {
        return prizeMapper.getActivePrizes();  // 获取所有有效奖品
    }

    @Override
    public Prize getPrizeById(int id) {
        return prizeMapper.getPrizeById(id);  // 获取指定奖品的详情
    }

    @Override
    public boolean updatePrizeStock(int prizeId, int stock) {
        return prizeMapper.updatePrizeStock(prizeId, stock) > 0;  // 更新奖品库存
    }

    @Override
    public Prize getRandomPrize() {
        List<Prize> prizes = getActivePrizes();
        double randomChance = Math.random();  // 随机生成中奖概率
        double cumulativeProbability = 0;
        for (Prize prize : prizes) {
            cumulativeProbability += prize.getProbability();
            if (randomChance <= cumulativeProbability && prize.getStock() > 0) {
                // 中奖逻辑
                updatePrizeStock(prize.getId(), prize.getStock() - 1);  // 更新库存
                return prize;  // 返回中奖的奖品
            }
        }
        //默認返回優惠卷
        //1892833358732525570
        return this.prizeMapper.getPrizeByTemplateId("1892833358732525570");
    }
}
