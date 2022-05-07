package com.zzw.mapper;


import com.zzw.entity.DeviceInfo;
import com.zzw.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface DeviceInfoMapper {

    //***交叉查询的总接口：***
    public List<DeviceInfo> generalSelectInterface(Map<String,Object> map);
    public Integer generalSelectInterfaceNum(Map<String,Object> map);

    //增加一台设备
    public Integer addDevice(DeviceInfo deviceInfo);

    //删除一台设备
    public Integer delDevice(Integer deviceId);

    //修改一台设备
    public Integer updateDevice(DeviceInfo deviceInfo);

    /** 2、对于外部的设备接口来说，他们会访问服务器接口从而改变数据库中的记录对吧，
     * 我们来考虑一下他们会做些什么
     * 2.1、当一个设备启动的时候，它应该会向服务器接口发送一条数据，就是指定id的设备启动了，这时它应该向接口发送以下的内容
     * 设备id，设备名称，启动时间戳，以及status字段在数据库中的值应该被激活成1了
     * 2.2、当这个设备在运行的时候，每隔10分钟向接口发送数据，。。。这个东西好像没有什么用，是在有图表的那些数据中才用得到的。
     * 2.3、当这个设备关闭的时候，它应该在关闭之前向接口发送数据，数据包含一下的内容：
     * 设备id，设备名称，关闭时间戳,status字段的值会在数据库中变成0
     *
     * 分析可得，我们在mapper中只要写一个update就行反正都是它传过来的数据在改变，最后都是使用update语句在更新捏。
     *
     * */


    /********************************这是index页面要用到的接口**********************************************************/
    public List<DeviceInfo> selectAll();


}
