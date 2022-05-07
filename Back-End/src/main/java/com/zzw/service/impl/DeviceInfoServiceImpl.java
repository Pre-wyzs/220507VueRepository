package com.zzw.service.impl;

import com.zzw.entity.DeviceInfo;
import com.zzw.mapper.DeviceInfoMapper;
import com.zzw.service.DeviceInfoService;
import com.zzw.websocket.ServerEndPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.websocket.Session;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class DeviceInfoServiceImpl implements DeviceInfoService {

    @Autowired
    DeviceInfoMapper deviceInfoMapper;

    @Override
    public List<DeviceInfo> generalSelectInterface(Map<String, Object> map) {
        return deviceInfoMapper.generalSelectInterface(map);
    }

    @Override
    public Integer generalSelectInterfaceNum(Map<String, Object> map) {
        return deviceInfoMapper.generalSelectInterfaceNum(map);
    }

    @Override
    public Integer addDevice(DeviceInfo deviceInfo) {
        return deviceInfoMapper.addDevice(deviceInfo);
    }

    @Override
    public Integer delDevice(Integer deviceId) {
        return deviceInfoMapper.delDevice(deviceId);
    }

    @Override
    public Integer updateDevice(DeviceInfo deviceInfo) {
        return deviceInfoMapper.updateDevice(deviceInfo);
    }

    //设备模拟或者真实的设备状态更新！
    @Override
    public Integer updateDeviceStatus(DeviceInfo deviceInfo) {
        //在这里应该做两件事，一是通过mapper更新目标设备的状态，二如果跟新成功，再通过websocket更新前端页面的所有数据
        Integer flag = deviceInfoMapper.updateDevice(deviceInfo);
        Session WebSocketSession = null;

        if(flag == 1){  //说明更新成功了捏!
            sendWebSocketMsg("DeviceInfo-Linking","Status_Updated");
            //还要给index页面去发送数据
            sendWebSocketMsg("IndexDevice-Linking","");
        }

        return flag;

    }


    @Override
    public List<DeviceInfo> selectAll() {
        return deviceInfoMapper.selectAll();
    }

    /**
     * 参数一是链接名：
     * 参数二是返回给前端的消息内容
     * */
    private void sendWebSocketMsg(String linkName,String msg){
        Session websocketSession = null;
        if(ServerEndPoint.onLinePoints.get(linkName)!=null){
            websocketSession = ServerEndPoint.onLinePoints.get(linkName).getSession();
            try{
                websocketSession.getBasicRemote().sendText(msg);
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            System.out.println("error:client not open!");
        }
    }

}
