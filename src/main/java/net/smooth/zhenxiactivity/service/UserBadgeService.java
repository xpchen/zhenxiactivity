package net.smooth.zhenxiactivity.service;

import net.smooth.zhenxiactivity.model.UserBadge;

import java.util.List;

public interface  UserBadgeService {
    int addUserBadge(UserBadge userBadge);
    int updateUserBadge(UserBadge userBadge);
    UserBadge getUserBadgeById(Integer badgeId);
    List<UserBadge> getAllUserBadges();
    int removeUserBadge(Integer badgeId);
    List<UserBadge> getUserBadgesByUserId(String userId);  // 新增的根据 userId 查询的方法
}
