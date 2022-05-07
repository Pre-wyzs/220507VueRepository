package com.zzw.service.impl;

import com.zzw.entity.CarPL;
import com.zzw.entity.PLData;
import com.zzw.mapper.CarPLMapper;
import com.zzw.service.CarPLService;
import com.zzw.websocket.ServerEndPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.websocket.Session;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class CarPLServiceImpl implements CarPLService {

    @Autowired
    CarPLMapper mapper;

    @Override
    public List<CarPL> selectAll() {
        return mapper.selectAll();
    }

    @Override
    public List<CarPL> selectCurrentDay(Map<String, Object> map) {
        return mapper.selectCurrentDay(map);
    }

    @Override
    public Integer addCarPL(CarPL carPL) {
        Integer flag = -1;
        flag = mapper.addCarPL(carPL);
        if(flag!=-1){
            sendWebSocketMsg("PL-Linking","Status_updated");
            /**还要给主页发送推送弄的消息捏*/
            sendWebSocketMsg("IndexPL-Linking","");
        }
        return flag;
    }

    @Override
    public Integer delCalPL(Map<String, Object> map) {
        return mapper.delCalPL(map);
    }


    @Override
    public Map<String,Object> selectPLData() {
        Map<String,Object> map = new LinkedHashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //num[0]代表进场数据，num[1]代表出场的数据
        Integer[] num = new Integer[2];
        //初始化
        num[0] = 0;
        num[1] = 0;

        List<PLData> dataList = mapper.selectPLData();

        String[] dates = mapper.selectAllDate();
        for (String date : dates) {
            for (PLData plData : dataList) {
                //这就是 当天的进场数据赋值
                if(sdf.format(plData.getDate()).equals(date)&&plData.getPurpose()==1){
                    num[0] += plData.getNum();
                }else if(sdf.format(plData.getDate()).equals(date)&&plData.getPurpose()==2){
                    //这里是当天的出场数据赋值
                    num[1] += plData.getNum();
                }
            }
            map.put(date,new Integer[]{num[0],num[1]});
            //重置
            num[0] = 0;
            num[1] = 0;
        }

        return map;
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





    /******************************************Index相关的接口 start************************************************/
    //查找今日的喷淋过的车辆的数据
    @Override
    public List<CarPL> indexSelectPL(Map<String,Object> map){
        return mapper.indexSelectPL(map);
    }

    /******************************************Index相关的接口 end************************************************/









}
