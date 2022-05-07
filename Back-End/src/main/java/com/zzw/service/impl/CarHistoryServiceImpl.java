package com.zzw.service.impl;

import com.zzw.entity.CarHistory;
import com.zzw.mapper.CarHistoryMapper;
import com.zzw.service.CarHistoryService;
import com.zzw.websocket.ServerEndPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.websocket.Session;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CarHistoryServiceImpl implements CarHistoryService {

    @Autowired
    CarHistoryMapper mapper;

    @Override
    public List<CarHistory> selectAll() {
        return mapper.selectAll();
    }

    @Override
    public List<CarHistory> generalSelectInterface(Map<String, Object> map) {
        return mapper.generalSelectInterface(map) ;
    }

    @Override
    public Integer generalSelectInterfaceNum(Map<String, Object> map) {
        return mapper.generalSelectInterfaceNum(map);
    }

    @Override
    public List<CarHistory> selectOneCar(String IMEI) {
        List<CarHistory> histories = new ArrayList<CarHistory>();
        List<CarHistory> dataList = mapper.selectOneCar(IMEI);

        if(dataList.get(0).getCarStatus()==1){
            //说明车辆是正在运行中的
            for(CarHistory tmp:dataList){
                histories.add(tmp);
                if(tmp.getCarStatus()==0){
                    break;
                }
            }
        }else{
            //说明车辆已经停止了；
            histories = dataList;
        }
        return histories;

    }

    @Override
    public Integer addCarHistory(CarHistory carHistory) {
        Integer flag = -1;
        flag = mapper.addCarHistory(carHistory);
        if(flag == 1){
            sendWebSocketMsg("Car-Linking","Status_updated");
            sendWebSocketMsg("Map-Linking",carHistory.getIMEI());
        }
        return flag;
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

    @Override
    public Integer delCarHistory(Integer id) {
        return mapper.delCarHistory(id);
    }
}
