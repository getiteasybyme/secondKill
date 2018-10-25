package com.secondKill.serviceImpl;

import com.secondKill.dao.UserMapper;
import com.secondKill.pojo.User;
import com.secondKill.pojo.UserExample;
import com.secondKill.service.IUserService;
import com.secondKill.util.MD5Util;
import com.secondKill.vo.UserInformationVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("iUserService")
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;


    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    @Override
    public User login(String username, String password) {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andUserNameEqualTo(username);
        criteria.andPasswordEqualTo(MD5Util.MD5EncodeUtf8(password));
        List<User> userList = userMapper.selectByExample(example);
        if (userList.size() > 0){
            User userResult = new User();
            for(User user : userList){
                userResult = user;
            }
            return userResult;
        }
        return null;
    }

    /**
     * 注册
     * @param user
     * @return
     */
    @Override
    public Boolean register(User user) {
        user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));
        Integer resultValue = userMapper.insert(user);
        if (resultValue <= 0){
            return false;
        }
        return true;
    }

    /**
     * 忘记密码
     * @param user
     * @return
     */
    @Override
    public Boolean resetPassword(User user) {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andUserNameEqualTo(user.getUserName());
        criteria.andEmailEqualTo(user.getEmail());
        criteria.andTelephoneEqualTo(user.getTelephone());
        List<User> userList = userMapper.selectByExample(example);
        if (userList.size() <= 0){
            return false;
        }

        User resetUser = new User();
        for (User user1 : userList){
            resetUser = user1;
        }

        String md5Password = MD5Util.MD5EncodeUtf8(user.getPassword());
        resetUser.setPassword(md5Password);

        Integer resultValue = userMapper.updateByPrimaryKeySelective(resetUser);
        if (resultValue <= 0){
            return false;
        }

        return true;
    }

    @Override
    public Boolean changePassword(Integer userId, String password) {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId);
        List<User> userList = userMapper.selectByExample(example);
        User changeUser = new User();
        for (User user : userList){
            changeUser = user;
        }

        changeUser.setPassword(MD5Util.MD5EncodeUtf8(password));

        Integer resultValue = userMapper.updateByPrimaryKey(changeUser);
        if (resultValue <= 0){
            return false;
        }

        return true;
    }

    @Override
    public UserInformationVo getInformation(Integer userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        if (user == null){
            return null;
        }

        return new UserInformationVo(user);
    }

    @Override
    public Integer changeInformation(User user) {
        return userMapper.updateByPrimaryKey(user);
    }
}
