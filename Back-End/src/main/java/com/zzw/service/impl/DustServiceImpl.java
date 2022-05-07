package com.zzw.service.impl;

import com.zzw.entity.Dust;
import com.zzw.mapper.DustMapper;
import com.zzw.service.DustService;
import com.zzw.websocket.ServerEndPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.websocket.Session;
import java.util.List;
import java.util.Map;

@Service
public class DustServiceImpl implements DustService {

    @Autowired
    private DustMapper dustMapper;

    @Override
    public List<Dust> selectAll() {
        return dustMapper.selectAll();
    }

    @Override
    public Integer generalSelectInterfaceNum(Map<String, Object> map) {
        return dustMapper.generalSelectInterfaceNum(map);
    }

    @Override
    public List<Dust> generalSelectInterface(Map<String, Object> map) {
        return dustMapper.generalSelectInterface(map);
    }

    @Override
    public Integer addDust(Dust dust) {
        Integer flag = -1;
        flag = dustMapper.addDust(dust);
        if(flag == 1){
            sendWebSocketMsg("Dust-Linking","Status_Updated");

            sendWebSocketMsg("IndexDust-Linking","");
        }
        return flag;
    }

    @Override
    public Integer delDust(Integer id) {
        return dustMapper.delDust(id);
    }


    @Override
    public List<Dust> selectLatest(Integer size) {
        return dustMapper.selectLatest(size);
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
