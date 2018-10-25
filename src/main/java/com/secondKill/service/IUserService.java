package com.secondKill.service;

import com.secondKill.pojo.User;
import com.secondKill.vo.UserInformationVo;

public interface IUserService {
    User login(String username, String password);
    Boolean register(User user);
    Boolean resetPassword(User user);
    Boolean changePassword(Integer userId,String password);
    UserInformationVo getInformation(Integer userId);
    Integer changeInformation(User user);
}
