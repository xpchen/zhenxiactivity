package net.smooth.zhenxiactivity.service;

import net.smooth.zhenxiactivity.model.Prize;

import java.util.List;

public interface PrizeService {

    List<Prize> getActivePrizes();  // 获取所有有效奖品

    Prize getPrizeById(int id);  // 获取某个奖品的详情

    boolean updatePrizeStock(int prizeId, int stock);  // 更新奖品库存

    Prize getRandomPrize();  // 根据概率随机抽取一个奖品
}
