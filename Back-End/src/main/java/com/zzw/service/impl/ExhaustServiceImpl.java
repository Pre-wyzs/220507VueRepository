package com.zzw.service.impl;


import com.zzw.entity.Exhaust;
import com.zzw.mapper.ExhaustMapper;
import com.zzw.service.ExhaustService;
import com.zzw.websocket.ServerEndPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.websocket.Session;
import java.util.List;
import java.util.Map;

@Service
public class ExhaustServiceImpl implements ExhaustService {

    @Autowired
    ExhaustMapper exhaustMapper;

    @Override
    public List<Exhaust> selectAll() {
        return exhaustMapper.selectAll();
    }

    @Override
    public List<Exhaust> selectByLimit(Map<String, Object> map) {
        return exhaustMapper.selectByLimit(map);
    }

    @Override
    public Integer addExhaustData(Exhaust exhaust) {
        Integer flag = -1;
        flag = exhaustMapper.addExhaustData(exhaust);
        if(flag == 1){
            sendWebSocketMsg("Exhaust-Linking","Status_Updated");

            //还要给主页推送更新的消息捏
            sendWebSocketMsg("IndexExhaust-Linking","");
        }
        return flag;

    }

    @Override
    public Integer delExhaustData(Integer id) {
        return exhaustMapper.delExhaustData(id);
    }

    @Override
    public Integer delExhaustDataMonth(Map<String, Object> map) {
        return exhaustMapper.delExhaustDataMonth(map);
    }

    @Override
    public Exhaust selectLatest() {
        return exhaustMapper.selectLatest();
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
