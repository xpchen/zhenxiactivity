package net.smooth.zhenxiactivity.mapper;

import net.smooth.zhenxiactivity.model.UserBadge;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface UserBadgeMapper {
    int insert(UserBadge userBadge);
    int update(UserBadge userBadge);
    UserBadge selectById(Integer badgeId);
    List<UserBadge> selectAll();
    int delete(Integer badgeId);
    List<UserBadge> selectByUserId(String userId);  // 新增的根据 userId 查询的方法
}