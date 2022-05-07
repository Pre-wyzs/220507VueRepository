package com.zzw.service;

import com.zzw.entity.DeviceHistory;

import java.util.List;
import java.util.Map;

public interface DeviceHistoryService {

    //***交叉查询的总接口：***
    public List<DeviceHistory> generalSelectInterface(Map<String,Object> map);
    public Integer generalSelectInterfaceNum(Map<String,Object> map);

    //删除一条历史记录
    public Integer delDeviceHistory(Integer deviceId);

    //外部设备接口的操作：增加一条历史记录：
    public Integer addDeviceHistory(DeviceHistory deviceHistory);


}
