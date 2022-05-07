package com.zzw.controller;


import com.zzw.entity.Exhaust;
import com.zzw.service.ExhaustService;
import com.zzw.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/exhaust")
public class ExhaustController {

    @Autowired
    ExhaustService exhaustService;

    //获取24小时内的所有成分排放的数据监控结果
    //每5分钟获取一次记录，一个小时可以获取到12条记录，24小时就是288条记录
    @GetMapping("/day")
    @CrossOrigin
    public Result selectByLimit(){

        Map<String,Object> map = new HashMap<>();
        map.put("startIndex",0);
        //获取前288条记录
        map.put("chartSize",288);

        return Result.success(exhaustService.selectByLimit(map));
    }


    //增加一条记录，这是给外部的设备使用的增加记录的接口
    @PutMapping("/add")
    @CrossOrigin
    public Result addExhaustData(@RequestBody Exhaust exhaust){
        System.out.println(exhaust);

        Integer flag = exhaustService.addExhaustData(exhaust);
//        Integer flag = 0;

        if(flag == 1){
            return Result.success();
        }else{
            return Result.error("-1","添加记录失败");
        }
    }


    /** 有涉及到新的业务逻辑了：数据的删除与备份...**/
    //系统自带的删除功能，因为当初在设计页面的时候没有考虑到删除的功能，以为只要在图表上显示一下就行了，
    //可是当数据变得很多的时候，你不可能不删除，所以可以定时每隔一个月自动备份一份文件，然后删除数据库中的数据。
    @PostMapping("/del")
    @CrossOrigin
    public Result delExhaustData(){
        //因为是系统自动的删除最老的一个月的数据，所以不用传参数说明要删除谁了，所以不需要参数对吧。
        //关键条件用时间戳吧，因为用id就很不靠谱的...
        Calendar calendar = Calendar.getInstance();
        Map<String,Object> map = new HashMap<>();

        /** 首先先获取当前的系统时间*/
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dateNow = new Date();
        String currentTime = sdf.format(dateNow);
//        System.out.println("当前时间：" + currentTime);

        /** 然后获取以现在为节点的一个月前的日期*/
        calendar.setTime(dateNow);
        calendar.add(Calendar.MONTH,-1);
        Date monthAgo = calendar.getTime();
        String aMonthAgo = sdf.format(monthAgo);
//        System.out.println("一个月以前："+aMonthAgo);

        /** 设置参数*/
        map.put("currentTime",currentTime);
        map.put("aMonthAgo",aMonthAgo);

        //删除这一个月内的数据然后返回结果
        return  Result.success(exhaustService.delExhaustDataMonth(map));
    }


}