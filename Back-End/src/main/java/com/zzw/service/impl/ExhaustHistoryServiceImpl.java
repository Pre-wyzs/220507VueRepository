package com.zzw.service.impl;

import com.zzw.entity.ExhaustHistory;
import com.zzw.mapper.ExhaustHistoryMapper;
import com.zzw.service.ExhaustHistoryService;
import com.zzw.websocket.ServerEndPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.websocket.Session;
import java.util.List;
import java.util.Map;

@Service
public class ExhaustHistoryServiceImpl implements ExhaustHistoryService {

    @Autowired
    ExhaustHistoryMapper exhaustHistoryMapper;

    @Override
    public List<ExhaustHistory> selectAll() {
        return exhaustHistoryMapper.selectAll();
    }

    @Override
    public List<ExhaustHistory> generalSelectInterface(Map<String, Object> map) {
        return exhaustHistoryMapper.generalSelectInterface(map);
    }

    @Override
    public Integer generalSelectInterfaceNum(Map<String, Object> map) {
        return exhaustHistoryMapper.generalSelectInterfaceNum(map);
    }

    @Override
    public List<ExhaustHistory> generalExceedInterface(Map<String, Object> map) {
        return exhaustHistoryMapper.generalExceedInterface(map);
    }

    @Override
    public Integer generalExceedInterfaceNum(Map<String, Object> map) {
        return exhaustHistoryMapper.generalExceedInterfaceNum(map);
    }

    @Override
    @Deprecated
    public Integer addExhaustHistory(ExhaustHistory exhaustHistory) {
        return exhaustHistoryMapper.addExhaustHistory(exhaustHistory);
    }

    @Override
    public Integer addExhaustHistorys(List<ExhaustHistory> historyList){
        Integer flag = 1;
        for (ExhaustHistory exhaustHistory : historyList) {
            flag = flag*exhaustHistoryMapper.addExhaustHistory(exhaustHistory);
        }

        Session websocketSession = null;
        if(flag !=0){
            if(ServerEndPoint.onLinePoints.get("ExhaustHistory-Linking")!=null){
                websocketSession = ServerEndPoint.onLinePoints.get("ExhaustHistory-Linking").getSession();
                try{
                    websocketSession.getBasicRemote().sendText("Status_Updated");
//                    System.out.println("消息已派送了");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }else{
                System.out.println("websocket is now closed");

            }
        }
        return flag;
    }

    @Override
    public Integer delExhaustHistory(Integer id) {

        return exhaustHistoryMapper.delExhaustHistory(id);

    }

}
