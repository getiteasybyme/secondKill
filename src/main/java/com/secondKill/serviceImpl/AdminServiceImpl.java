package com.secondKill.serviceImpl;

import com.secondKill.dao.AdminMapper;
import com.secondKill.pojo.Admin;
import com.secondKill.pojo.AdminExample;
import com.secondKill.pojo.User;
import com.secondKill.pojo.UserExample;
import com.secondKill.service.IAdminService;
import com.secondKill.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("iAdminService")
public class AdminServiceImpl  implements IAdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public Admin login(Integer adminId, String password) {
        AdminExample example = new AdminExample();
        AdminExample.Criteria criteria = example.createCriteria();
        criteria.andAdminIdEqualTo(adminId);
        criteria.andPasswordEqualTo(password);
        List<Admin> adminList = adminMapper.selectByExample(example);
        if (adminList.size() > 0){
            Admin adminResult = new Admin();
            for(Admin admin : adminList){
                adminResult = admin;
            }
            return adminResult;
        }
        return null;
    }
}
