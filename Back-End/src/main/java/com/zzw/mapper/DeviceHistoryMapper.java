package com.zzw.mapper;


import com.zzw.entity.DeviceHistory;
import com.zzw.entity.DeviceInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface DeviceHistoryMapper {

    /** 1、对于设备启动和关闭的历史记录来说，只有查询和删除的操作；对于外部的接口会有增加一条记录的操作，修改操作应该是不存在的*/
    //***交叉查询的总接口：***
    public List<DeviceHistory> generalSelectInterface(Map<String,Object> map);
    public Integer generalSelectInterfaceNum(Map<String,Object> map);

    //删除一条历史记录
    public Integer delDeviceHistory(Integer deviceId);

    //外部设备接口的操作：增加一条历史记录：
    public Integer addDeviceHistory(DeviceHistory deviceHistory);

}
