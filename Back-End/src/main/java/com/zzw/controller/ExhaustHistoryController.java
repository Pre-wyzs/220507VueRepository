package com.zzw.controller;

import com.zzw.entity.ExhaustHistory;
import com.zzw.service.ExhaustHistoryService;
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
@RequestMapping("/api/exhaustHistory")
public class ExhaustHistoryController {

    @Autowired
    ExhaustHistoryService  exhaustHistoryService;
    //导出所有的记录的接口：不过好像没有什么用捏
    @GetMapping("/all")
    @CrossOrigin
    public Result selectAll(){
        return Result.success(exhaustHistoryService.selectAll());
    }

    @GetMapping("/general")
    @CrossOrigin
    public Result generalSelectInterface(@RequestParam(required = false) String partName,
                                         @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date startTime,
                                         @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endTime,
                                         @RequestParam(required = false,defaultValue = "1") Integer pageNum,
                                         @RequestParam(required = false,defaultValue = "7") Integer pageSize){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String startTimeStr = null;
        String endTimeStr = null;
        Map<String,Object> map = new HashMap<>();

        if(partName.compareTo("") == 0){
            partName = null;
        }

        if(startTime != null){
            startTimeStr = sdf.format(startTime);
        }

        if(endTime !=null){
            endTimeStr = sdf.format(endTime);
        }

        map.put("partName",partName);
        map.put("startTime",startTimeStr);
        map.put("endTime",endTimeStr);

        //获取总的记录数
        Integer rowNum = exhaustHistoryService.generalSelectInterfaceNum(map);
        map.put("startIndex",(pageNum-1)*pageSize);
        map.put("pageSize",pageSize);

        //计算出总的页数：
        //传给前端总的页数
        Integer totalPages;
        if(rowNum%pageSize == 0){
            totalPages = rowNum/pageSize;
        }else {
            totalPages = rowNum/pageSize + 1;
        }
        return Result.success(exhaustHistoryService.generalSelectInterface(map),totalPages);
    }




    //超标的查询总接口
    @Deprecated
    @GetMapping("/generalExceed")
    @CrossOrigin
    public Result generalExceedInterface(@RequestParam(required = false) String partName,
                                         @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date startTime,
                                         @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endTime,
                                         @RequestParam(required = false,defaultValue = "1") Integer pageNum,
                                         @RequestParam(required = false,defaultValue = "5") Integer pageSize){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String startTimeStr = null;
        String endTimeStr = null;
        Map<String,Object> map = new HashMap<>();

        if(partName.compareTo("") == 0){
            partName = null;
        }

        if(startTime != null){
            startTimeStr = sdf.format(startTime);
        }

        if(endTime !=null){
            endTimeStr = sdf.format(endTime);
        }

        map.put("partName",partName);
        map.put("startTime",startTimeStr);
        map.put("endTime",endTimeStr);

        //获取总的记录数
        Integer rowNum = exhaustHistoryService.generalExceedInterfaceNum(map);
        map.put("startIndex",(pageNum-1)*pageSize);
        map.put("pageSize",pageSize);

        Integer totalPages;
        if(rowNum%pageSize == 0){
            totalPages = rowNum/pageSize;
        }else {
            totalPages = rowNum/pageSize + 1;
        }
        return Result.success(exhaustHistoryService.generalExceedInterface(map),totalPages);
    }


    @PutMapping("/adds")
    @CrossOrigin
    public Result addExhaustHistory(@RequestBody List<ExhaustHistory> historyList){

        if(exhaustHistoryService.addExhaustHistorys(historyList)!=0){
            return Result.success();
        }
        return Result.error("-1","数据添加失败");

    }


    //删除一条记录
    @PostMapping("/del")
    @CrossOrigin
    public Result delExhaustHistory(@RequestBody ExhaustHistory exhaustHistory){
        /**
         * 接受并解析前端传过来的token令牌。
         * */
        String token = exhaustHistory.getToken();
        Claims claims = JwtUtils.tokenParse(token);

        /** 同样只有超级管理和普通管理有权限进行设备历史记录的删除捏*/
        if(claims.get("power").toString().compareTo("2") != 0){
            exhaustHistoryService.delExhaustHistory(exhaustHistory.getId());
            return Result.success();
        }
        return Result.error("-1","权限不够");  //返回给前端插入成功或失败的信息
    }

    //批量删除多条记录
    @PostMapping("/dels")
    @CrossOrigin
    public Result delExhaustHistorys(@RequestBody String[] ids){
        /**
         * 接受并解析前端传过来的token令牌。
         * */

        System.out.println(ids);
        Claims claims = JwtUtils.tokenParse(ids[ids.length-1]);

        /** 同样只有超级管理和普通管理有权限进行设备历史记录的删除捏*/
        if(claims.get("power").toString().compareTo("2") != 0){
            for(int i=0;i<ids.length-1;i++){
                exhaustHistoryService.delExhaustHistory(Integer.parseInt(ids[i]));
            }
            return Result.success();
        }
        return Result.error("-1","权限不够");  //返回给前端插入成功或失败的信息

    }


}
