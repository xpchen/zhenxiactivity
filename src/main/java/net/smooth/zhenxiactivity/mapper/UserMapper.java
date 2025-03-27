package net.smooth.zhenxiactivity.mapper;

import net.smooth.zhenxiactivity.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {

    List<User> getUserList();

    void addUser(User user);

    void updateUser(User user);

    // 根据用户ID查询会员
    User getUserByUserId(@Param("userId") String userId);

    // 根据手机号查询会员
    User getUserByMobile(@Param("mobile") String mobile);

    // 根据 OpenId 查询会员
    User getUserByOpenId(@Param("openId") String openId);
}
