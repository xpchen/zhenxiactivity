package net.smooth.zhenxiactivity.mapper;
import net.smooth.zhenxiactivity.model.Prize;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface PrizeMapper {

    List<Prize> getActivePrizes();

    Prize getPrizeById(int id);

    int updatePrizeStock(int id, int stock);

    Prize getPrizeByTemplateId(String templateId);

    int insertPrize(Prize prize);
}
