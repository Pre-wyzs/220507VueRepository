package com.zzw.service;


import com.zzw.entity.User;

public interface LoginService {
    public Integer selectLogin(User user);

    public User selectLoginUser(User user);

}
