package com.zzw.service.impl;

import com.zzw.entity.User;
import com.zzw.mapper.UserMapper;
import com.zzw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;


    //总接口获取分页数据
    @Override
    public List<User> generalSelectInterface(Map<String, Object> map) {
        return userMapper.generalSelectInterface(map);
    }
    //总接口获取数据总量
    @Override
    public Integer generalSelectInterfaceNum(Map<String,Object> map){
        return userMapper.generalSelectInterfaceNum(map);
    }


    //分页查询所有power用户
    public List<User> selectByPower(String power,Integer pageNum,Integer pageSize){
        List<User> users;
        Map<String,Object> map = new HashMap<>();
        map.put("power",power);
        map.put("startIndex",(pageNum-1)*pageSize);
        map.put("pageSize",pageSize);
        users = userMapper.selectByPower(map);
        return users;
    }

    @Override
    public List<User> getUsersByName(String name, Integer pageNum, Integer pageSize) {
        Map<String,Object> map = new HashMap<>();
        map.put("name",name);
        map.put("startIndex",(pageNum-1)*pageSize);
        map.put("pageSize",pageSize);
        return userMapper.getUsersByName(map);
    }



    //返回数据库的总长度
    @Override
    public Integer selectLength(Map<String,Object> map) {
        return userMapper.selectLength(map);
    }

    //分页查询所有的用户
    public List<User> selectUsers(Integer pageNum,Integer pageSize) {  //参数一是页号，参数二是一页的大小
        Map<String,Object> map = new HashMap<>();


        map.put("startIndex",(pageNum-1)*pageSize);
        map.put("pageSize",pageSize);
        List<User> users = userMapper.selectUsers(map);
        return users;
    }


    @Override
    public User selectUserById(Integer id) {
        User user = userMapper.selectUserById(id);
        return user;
    }

    //精确的名字查询
    @Override
    public User selectUserByName(String name) {
        User user = userMapper.selectUserByName(name);
        return user;
    }

    @Override
    public Integer updateUser(User user) {
        Integer flag = userMapper.updateUser(user);
        return flag;
    }

    @Override
    public Integer deleteUser(Integer id) {
        Integer flag = userMapper.deleteUser(id);
        return flag;
    }

    @Override
    public Integer addUser(User user) {
        Integer flag = userMapper.addUser(user);
        return flag;
    }



}
