package net.smooth.zhenxiactivity.service.impl;
import net.smooth.zhenxiactivity.dto.response.ApiResponse;
import net.smooth.zhenxiactivity.dto.response.UserInfo;
import net.smooth.zhenxiactivity.mapper.UserMapper;
import net.smooth.zhenxiactivity.model.User;
import net.smooth.zhenxiactivity.service.ApiService;
import net.smooth.zhenxiactivity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class UserServiceImpl  implements UserService {
    @Autowired
    private UserMapper memberMapper;

    @Autowired
    private ApiService apiService;

    private User userToMember(UserInfo user_info){
        User member = new User();
        member.setUserId(user_info.getUserId());
        member.setMobile(user_info.getMobile());
        member.setAvatarUrl(user_info.getAvatarUrl());
        member.setNickName(user_info.getNickName());
        member.setOpenId(user_info.getOpenId());
        member.setRegisterSource(user_info.getRegisterSource());
        member.setTenantId(user_info.getTenantId());
        member.setRegisterTimestamp(user_info.getRegisterTimestamp());
        return member;
    }

    private UserInfo memberToUserInfo(User member){
        UserInfo user = new UserInfo();
        user.setUserId(member.getUserId());
        user.setMobile(member.getMobile());
        user.setAvatarUrl(member.getAvatarUrl());
        user.setNickName(member.getNickName());
        user.setOpenId(member.getOpenId());
        user.setRegisterSource(member.getRegisterSource());
        user.setTenantId(member.getTenantId());
        user.setRegisterTimestamp(member.getRegisterTimestamp());
        return user;
    }

    @Override
    public void addUser(UserInfo user_info) {
        if(this.memberMapper.getUserByUserId(user_info.getUserId())==null)
        {
            User member = this.userToMember(user_info);
            this.memberMapper.addUser(member);
        }
    }

    @Override
    public void updateUser(UserInfo user_info) {
        if(this.memberMapper.getUserByUserId(user_info.getUserId())!=null)
        {
            User member = this.userToMember(user_info);
            this.memberMapper.updateUser(member);
        }
    }

    @Override
    public UserInfo getUserById(String user_id) {
        User member =  this.memberMapper.getUserByUserId(user_id);
        return this.memberToUserInfo(member);
    }

    @Override
    public UserInfo getUserByAccessToken(String access_token) throws Exception {
        ApiResponse<UserInfo> response = this.apiService.callApi("buyer.oauth2.info",access_token,new HashMap<>());
        return response.getData();
    }
}
