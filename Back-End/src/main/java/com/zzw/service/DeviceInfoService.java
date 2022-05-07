package com.zzw.service;

import com.zzw.entity.DeviceInfo;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface DeviceInfoService {


    //***交叉查询的总接口：***
    public List<DeviceInfo> generalSelectInterface(Map<String,Object> map);
    public Integer generalSelectInterfaceNum(Map<String,Object> map);


    //增加一台设备
    public Integer addDevice(DeviceInfo deviceInfo);

    //删除一台设备
    public Integer delDevice(Integer deviceId);

    //修改一台设备
    public Integer updateDevice(DeviceInfo deviceInfo);

    //设备根新状态，通过外部的设备模拟或者真实设备接口触发而实现
    public Integer updateDeviceStatus(DeviceInfo deviceInfo) throws IOException;


    /*******************************index主页要用到的接口******************************************************/
    //获取所有设备的信息，然后展示在大屏上
    public List<DeviceInfo> selectAll();


}
