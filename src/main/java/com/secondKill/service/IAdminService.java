package com.secondKill.service;

import com.secondKill.pojo.Admin;

public interface IAdminService {
    Admin login(Integer adminId,String password);
}
