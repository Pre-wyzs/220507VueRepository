package com.zzw.mapper;

import com.sun.org.apache.xpath.internal.objects.XObject;
import com.zzw.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/*
*说明：该接口一共有8个方法：3个增删改，5个查询：分页查询，id查询，用户名精确查询，用户名分页模糊查询，权限分页查询
* @param
* @ret
*  */
@Mapper  //使用这个注解就可以被mybatis扫描到成为一个Mapper类
@Repository
public interface UserMapper {

    //***交叉查询的总接口：***
    public List<User> generalSelectInterface(Map<String,Object> map);
    public Integer generalSelectInterfaceNum(Map<String,Object> map);






    //查询数据库长度
    public Integer selectLength(Map<String, Object> map);

    //搜索所有用户：需要分页处理
    public List<User> selectUsers(Map<String,Object> map);

    /*
    * 通过权限查询和通过用户名模糊查询都是要分页的所以要分页
    * */

    //power分页查询
    public List<User> selectByPower(Map<String,Object> map);


    //通过id查找用户
    public User selectUserById(Integer id);

    //通过用户名精确查询
    public User selectUserByName(String name);

    //更新一个用户
    public Integer updateUser(User user);

    //删除一个用户
    public Integer deleteUser(Integer id);

    //增加一个用户
    public Integer addUser(User user);


    //通过用户名模糊分页查询
    public List<User> getUsersByName(Map<String,Object> map);

}
