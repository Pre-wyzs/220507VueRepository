package com.zzw.mapper;

import com.zzw.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface LoginMapper {
    public Integer selectLogin(User user);

    public User selectLoginUser(User user);

}
