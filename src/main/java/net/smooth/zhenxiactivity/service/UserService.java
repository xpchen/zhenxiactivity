package net.smooth.zhenxiactivity.service;

import net.smooth.zhenxiactivity.dto.response.UserInfo;

public interface UserService {

     void addUser(UserInfo user_info);

     void updateUser(UserInfo user_info);

     UserInfo getUserById(String user_id);

     UserInfo getUserByAccessToken(String access_token) throws Exception;
}
