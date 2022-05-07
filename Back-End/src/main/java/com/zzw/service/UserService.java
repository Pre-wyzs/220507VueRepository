package com.zzw.service;

import com.zzw.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {


    public List<User> generalSelectInterface(Map<String,Object> map);
    public Integer generalSelectInterfaceNum(Map<String,Object> map);

    //查询数据库长度
    public Integer selectLength(Map<String,Object> map);

    //搜索所有用户

    public List<User> selectUsers(Integer pageNum,Integer pageSieze);

    //通过id查找用户
    public User selectUserById(Integer id);

    //通过用户名模糊查询
    public User selectUserByName(String name);

    //更新一个用户
    public Integer updateUser(User user);

    //删除一个用户
    public Integer deleteUser(Integer id);

    //增加一个用户
    public Integer addUser(User user);


    public List<User> selectByPower(String power,Integer pageNum,Integer pageSize);

    public List<User> getUsersByName(String name,Integer pageNum,Integer pageSize);

}
