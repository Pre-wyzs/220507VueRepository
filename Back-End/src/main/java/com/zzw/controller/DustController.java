package com.zzw.controller;

import com.zzw.entity.Dust;
import com.zzw.service.DustService;
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
import java.util.Map;

@RestController
@RequestMapping("/api/dust")
public class DustController {

    @Autowired
    private DustService dustService;

    @GetMapping("/all")
    @CrossOrigin
    public Result selectAll(){
        return Result.success(dustService.selectAll());
    }


    @GetMapping("/general")
    @CrossOrigin
    public Result generalSelectInterface(@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date startTime,
                                         @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endTime,
                                         @RequestParam(required = false) String degree,
                                         @RequestParam(required = false,defaultValue = "1") Integer pageNum,
                                         @RequestParam(required = false,defaultValue = "7") Integer pageSize
                                         ){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String startTimeStr = null;
        String endTimeStr = null;
        Map<String,Object> map = new HashMap<>();

        if(degree.compareTo("") == 0){
            degree = null;
        }
        if(startTime !=null){
            startTimeStr = sdf.format(startTime);
        }
        if(endTime !=null){
            endTimeStr = sdf.format(endTime);
        }

        synchronized (map){
            map.put("startTime",startTimeStr);
            map.put("endTime",endTimeStr);
            map.put("degree",degree);
            map.put("startIndex",(pageNum-1)*pageSize);
            map.put("pageSize",pageSize);
        }

        Integer totalPages = GeneralSelectUtils.setTotalPages(dustService.generalSelectInterfaceNum(map),pageSize);
        return Result.success(dustService.generalSelectInterface(map),totalPages);
    }


    //增加一条记录
    //增加一条记录，这是给外部的设备使用的增加记录的接口
    @PutMapping("/add")
    @CrossOrigin
    public Result addDust(@RequestBody Dust dust){
        System.out.println(dust);

        Integer flag = dustService.addDust(dust);
//        Integer flag = 0;

        if(flag == 1){
            return Result.success();
        }else{
            return Result.error("-1","添加记录失败");
        }
    }



    //批量删除多条记录
    @PostMapping("/dels")
    @CrossOrigin
    public Result delDusts(@RequestBody String[] ids){
        /**
         * 接受并解析前端传过来的token令牌。
         * */

        System.out.println(ids);
        Claims claims = JwtUtils.tokenParse(ids[ids.length-1]);

        /** 同样只有超级管理和普通管理有权限进行设备历史记录的删除捏*/
        if(claims.get("power").toString().compareTo("2") != 0){
            for(int i=0;i<ids.length-1;i++){
                dustService.delDust(Integer.parseInt(ids[i]));
            }
            return Result.success();
        }
        return Result.error("-1","权限不够");  //返回给前端插入成功或失败的信息

    }



}
