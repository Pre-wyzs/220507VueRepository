package com.zzw.controller;

import com.zzw.entity.DeviceHistory;
import com.zzw.entity.DeviceInfo;
import com.zzw.service.DeviceHistoryService;
import com.zzw.utils.GeneralSelectUtils;
import com.zzw.utils.Result;
import com.zzw.utils.jwttoken.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/deviceHistory")
public class DeviceHistoryController {

    @Autowired
    DeviceHistoryService deviceHistoryService;

    //外部设备接口的操作：增加一条历史记录：这个接口的调用者是设备本身，所以应该不需要token令牌的啊
    @PostMapping("/add")
    @CrossOrigin
    public Result addDeviceHistory(@RequestBody DeviceHistory deviceHistory){
        if(deviceHistoryService.addDeviceHistory(deviceHistory) != 0){
            return Result.success();
        }
        return Result.error("-1","增加历史记录失败!");  //返回给前端插入成功或失败的信息
    }


    //删除一条历史记录
    @PostMapping("/del")
    @CrossOrigin
    public Result delDeviceHistory(@RequestBody DeviceHistory deviceHistory) {
        /**
         * 接受并解析前端传过来的token令牌。
         * */
        String token = deviceHistory.getToken();
        Claims claims = JwtUtils.tokenParse(token);

        /** 同样只有超级管理和普通管理有权限进行设备历史记录的删除捏*/
        if(claims.get("power").toString().compareTo("2") != 0){
            deviceHistoryService.delDeviceHistory(deviceHistory.getId());
            return Result.success();
        }
        return Result.error("-1","权限不够");  //返回给前端插入成功或失败的信息
    }

    //删除多条历史记录
    @PostMapping("/dels")
    @CrossOrigin
    public Result delDeviceHistorys(@RequestBody String[] ids) {
        /**
         * 接受并解析前端传过来的token令牌。
         * */

        System.out.println(ids);
        Claims claims = JwtUtils.tokenParse(ids[ids.length-1]);

        /** 同样只有超级管理和普通管理有权限进行设备历史记录的删除捏*/
        if(claims.get("power").toString().compareTo("2") != 0){
            for(int i=0;i<ids.length-1;i++){
                deviceHistoryService.delDeviceHistory(Integer.parseInt(ids[i]));
            }
            return Result.success();
        }
        return Result.error("-1","权限不够");  //返回给前端插入成功或失败的信息
    }



    @GetMapping("/general")
    @CrossOrigin
    public Result<List<DeviceHistory>> generalSelectInterface(@RequestParam(required = false) String nameSure,
                                                              @RequestParam(required = false) String nameFuzzy,
                                                              @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date startDate,
                                                              @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endDate,
                                                              @RequestParam(required = false) Integer status,
                                                              @RequestParam(required = false,defaultValue = "1") Integer pageNum,
                                                              @RequestParam(required = false,defaultValue = "5") Integer pageSize)
    {

        //日期处理函数
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String startDateString = null;
        String endDateString = null;

        if(nameSure.compareTo("") == 0){
            nameSure = null;
        }
        if(nameFuzzy.compareTo("") == 0){
            nameFuzzy = null;
        }
        if(status == null){
//            System.out.println("状态没传");
        }
        if(startDate != null){
            startDateString = dateFormat.format(startDate);
//            System.out.println(startDateString);
        }
        if(endDate != null) {
            endDateString = dateFormat.format(endDate);
        }


        Map<String,Object> map = new HashMap<>();
        synchronized (map){
            map.put("nameSure",nameSure);
            map.put("nameFuzzy",nameFuzzy);
            map.put("status",status);
            map.put("startDate",startDateString);
            map.put("endDate",endDateString);
            map.put("startIndex",(pageNum - 1)*pageSize);
            map.put("pageSize",pageSize);
        }

        Integer totalPages = GeneralSelectUtils.setTotalPages(deviceHistoryService.generalSelectInterfaceNum(map),pageSize);
        return Result.success(deviceHistoryService.generalSelectInterface(map),totalPages);
    }



}
