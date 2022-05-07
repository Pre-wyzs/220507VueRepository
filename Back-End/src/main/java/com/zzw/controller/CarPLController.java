package com.zzw.controller;

import com.zzw.entity.CarHistory;
import com.zzw.entity.CarPL;
import com.zzw.service.CarPLService;
import com.zzw.utils.Result;
import com.zzw.utils.jwttoken.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/car/pl")
public class CarPLController {

    @Autowired
    CarPLService service;


    @GetMapping("/all")
    @CrossOrigin
    public Result selectAll(){
        return Result.success(service.selectAll());
    }


    @PutMapping("/current")
    @CrossOrigin
    public Result selectCurrentDay(@RequestBody CarPL carPL){
        if(carPL.getStartDate()==null || carPL.getEndDate()==null){
            return Result.error("-1","前后时间段不完整");
        }
        Map<String,Object> map = new HashMap<>();
        map.put("startDate",carPL.getStartDate());
        map.put("endDate",carPL.getEndDate());

        return Result.success(service.selectCurrentDay(map));
    }

    //这里是设备在添加喷淋数据的所以不需要进行token的验证了
    @PostMapping("/add")
    @CrossOrigin
    public Result addCarPl(@RequestBody CarPL carPl){
        Integer flag = -1;
        flag = service.addCarPL(carPl);
        if(flag!=-1){
            return Result.success();
        }else{
            return Result.error("-1","添加失败!");
        }
    }


    //删除设备的时候也还是需要token啦进行设备的校验的
    //删除一条历史记录
    @PostMapping("/del")
    @CrossOrigin
    public Result delCarPL(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date startDate,
                           @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endDate,
                           @RequestParam String token) {
        /**
         * 接受并解析前端传过来的token令牌。
         *
         * */
        if(startDate==null || endDate==null){
            return Result.error("-1","时间段不完整!");
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map<String,Object> map = new HashMap<>();
        map.put("startDate",sdf.format(startDate));
        map.put("endDate",sdf.format(endDate));

        if(JwtUtils.tokenParse(token).get("power").toString().compareTo("2") != 0) {
            /** 同样只有超级管理和普通管理有权限进行设备历史记录的删除捏*/
            service.delCalPL(map);
            return Result.success();
        }
        return Result.error("-1","权限不够");  //返回给前端插入成功或失败的信息
    }


    @GetMapping("/chart")
    @CrossOrigin
    public Result selectChartData(){
        return Result.success(service.selectPLData());
    }


}
