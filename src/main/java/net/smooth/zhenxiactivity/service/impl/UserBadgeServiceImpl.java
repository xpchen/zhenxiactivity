package net.smooth.zhenxiactivity.service.impl;

import net.smooth.zhenxiactivity.mapper.UserBadgeMapper;
import net.smooth.zhenxiactivity.model.UserBadge;
import net.smooth.zhenxiactivity.service.UserBadgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserBadgeServiceImpl implements UserBadgeService {
    @Autowired
    private UserBadgeMapper userBadgeMapper;

    @Override
    public int addUserBadge(UserBadge userBadge) {
        return userBadgeMapper.insert(userBadge);
    }

    @Override
    public int updateUserBadge(UserBadge userBadge) {
        return userBadgeMapper.update(userBadge);
    }

    @Override
    public UserBadge getUserBadgeById(Integer badgeId) {
        return userBadgeMapper.selectById(badgeId);
    }

    @Override
    public List<UserBadge> getAllUserBadges() {
        return userBadgeMapper.selectAll();
    }

    @Override
    public int removeUserBadge(Integer badgeId) {
        return userBadgeMapper.delete(badgeId);
    }

    @Override
    public List<UserBadge> getUserBadgesByUserId(String userId,String badgeType) {

try{
    return userBadgeMapper.selectByUserId(userId,badgeType);
}catch (Exception e)
{
    throw  e;
}

    }
}

