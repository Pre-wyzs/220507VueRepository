package com.zzw.service.impl;
import com.zzw.entity.DeviceHistory;
import com.zzw.mapper.DeviceHistoryMapper;
import com.zzw.service.DeviceHistoryService;
import com.zzw.websocket.ServerEndPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.websocket.Session;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class DeviceHistoryServiceImpl implements DeviceHistoryService {

    @Autowired
    DeviceHistoryMapper deviceHistoryMapper;

    @Override
    public List<DeviceHistory> generalSelectInterface(Map<String, Object> map) {
        return deviceHistoryMapper.generalSelectInterface(map);
    }

    @Override
    public Integer generalSelectInterfaceNum(Map<String, Object> map) {
        return deviceHistoryMapper.generalSelectInterfaceNum(map);
    }

    @Override
    public Integer delDeviceHistory(Integer deviceId) {
        return deviceHistoryMapper.delDeviceHistory(deviceId);
    }

    @Override
    public Integer addDeviceHistory(DeviceHistory deviceHistory) {

        //在这里应该做两件事，一是通过mapper更新目标设备的状态，二如果跟新成功，再通过websocket更新前端页面的所有数据
        Integer flag = deviceHistoryMapper.addDeviceHistory(deviceHistory);
        Session WebSocketSession = null;
        if(flag == 1){
            //如果endPoints集合中已经有这个websocket session了，就传输数据更新的消息给前端捏
            if(ServerEndPoint.onLinePoints.get("DeviceHistory-Linking")!=null){
                WebSocketSession = ServerEndPoint.onLinePoints.get("DeviceHistory-Linking").getSession();
                //返回给前端一个flag，而不是直接返回数据...这个有一点点捞内
                try{
                    WebSocketSession.getBasicRemote().sendText("Status_Updated");
                }catch (IOException e){
                    e.printStackTrace();
                }
            }else{
                System.out.println("websocket is now closed!");
            }

        }
        return flag;
    }

}
