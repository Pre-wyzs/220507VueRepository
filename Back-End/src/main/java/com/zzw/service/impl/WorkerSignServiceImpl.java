package com.zzw.service.impl;

import com.zzw.entity.WorkerSign;
import com.zzw.mapper.WorkerSignMapper;
import com.zzw.service.WorkerSignService;
import com.zzw.websocket.ServerEndPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.websocket.Session;
import java.util.List;
import java.util.Map;

@Service
public class WorkerSignServiceImpl implements WorkerSignService {

    @Autowired
    WorkerSignMapper workerSignMapper;

    @Override
    public List<WorkerSign> selectAll() {
        return workerSignMapper.selectAll();
    }

    @Override
    public List<WorkerSign> generalSelectInterface(Map<String, Object> map) {
        return workerSignMapper.generalSelectInterface(map);
    }

    @Override
    public Integer generalSelectInterfaceNum(Map<String, Object> map) {
        return workerSignMapper.generalSelectInterfaceNum(map);
    }

    @Override
    public Integer addWorkerSign(WorkerSign workerSign) {
        Integer flag = -1;
        flag = workerSignMapper.addWorkerSign(workerSign);
        Session websocketSession = null;
        if(flag == 1){
            if(ServerEndPoint.onLinePoints.get("WorkerSign-Linking")!=null){
                websocketSession = ServerEndPoint.onLinePoints.get("WorkerSign-Linking").getSession();
                try{
                    websocketSession.getBasicRemote().sendText("Status_Updated");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }else{
                System.out.println("客户端websocket没打开!");
            }
        }
        return flag;
    }

    @Override
    public Integer delWorkerSign(Integer id) {
        return workerSignMapper.delWorkerSign(id);
    }

    @Override
    public Integer updateWorkerSign(WorkerSign workerSign) {
        return workerSignMapper.updateWorkerSign(workerSign);
    }


    /******************************************Index相关的接口 start************************************************/
    @Override
    public Integer signNumADay(Map<String, Object> map) {
        return workerSignMapper.signNumADay(map);
    }
}
