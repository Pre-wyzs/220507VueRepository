package com.zzw.service.impl;

import com.zzw.entity.User;
import com.zzw.mapper.LoginMapper;
import com.zzw.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    LoginMapper loginMapper;

    @Override
    public Integer selectLogin(User user) {
        return loginMapper.selectLogin(user);
    }

    @Override
    public User selectLoginUser(User user) {
        return loginMapper.selectLoginUser(user);
    }
}
