package net.smooth.zhenxiactivity.mapper;

import net.smooth.zhenxiactivity.model.UserBadge;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserBadgeMapper {
    int insert(UserBadge userBadge);
    int update(UserBadge userBadge);
    UserBadge selectById(Integer badgeId);
    List<UserBadge> selectAll();
    int delete(Integer badgeId);
    List<UserBadge> selectByUserId(@Param("userId")String userId,@Param("badgeType") String  badgeType);
}