package com.zzw.controller;

import com.zzw.entity.CarHistory;
import com.zzw.service.CarHistoryService;
import com.zzw.utils.GeneralSelectUtils;
import com.zzw.utils.Result;
import com.zzw.utils.jwttoken.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api/car/history")
public class CarHistoryController {

    @Autowired
    CarHistoryService service;

    //导出所有的轨迹数据
    @GetMapping("/all")
    @CrossOrigin
    public Result selectAll(){
        return Result.success(service.selectAll());
    }

    @PutMapping("/general")
    @CrossOrigin
    public Result generalSelectInterface(@RequestBody CarHistory carHistory){

        Map<String,Object> map = new HashMap<>();
        Integer totalPages = 1;
        map.put("license",carHistory.getLicense());
        map.put("IMEI",carHistory.getIMEI());

        totalPages = GeneralSelectUtils.setTotalPages(service.generalSelectInterfaceNum(map),carHistory.getPageSize());
        map.put("startIndex",GeneralSelectUtils.setStartIndex(carHistory.getPageNum(),carHistory.getPageSize()));
        map.put("pageSize",carHistory.getPageSize());

        List<CarHistory> carHistories= service.generalSelectInterface(map);
        return Result.success(carHistories,totalPages);
    }


    //设个添加不是人为的，而是从那个IMEI设备上传输过来的。
    @PostMapping("/add")
    @CrossOrigin
    public Result addCarHistory(@RequestBody CarHistory carHistory){
        service.addCarHistory(carHistory);
        return Result.success();
    }

    //删除一条历史记录
    @PostMapping("/del")
    @CrossOrigin
    public Result delCar(@RequestBody CarHistory carHistory) {
        /**
         * 接受并解析前端传过来的token令牌。
         *
         * */
        if(JwtUtils.tokenParse(carHistory.getToken()).get("power").toString().compareTo("2") != 0) {
            /** 同样只有超级管理和普通管理有权限进行设备历史记录的删除捏*/
            service.delCarHistory(carHistory.getId());
            return Result.success();
        }
        return Result.error("-1","权限不够");  //返回给前端插入成功或失败的信息
    }


    //传递给地图端的数据
    @PostMapping("/map")
    @CrossOrigin
    public Result mapPath(@RequestBody String[] IMEIS){
        //这个数组中的每一个元素表示一辆车
//        List<List<CarHistory>> paths = new ArrayList<>();
        Map<String,List<CarHistory>> map = new ConcurrentHashMap<>();  //这个是线程安全的
        for (String imei : IMEIS) {
            map.put(imei,service.selectOneCar(imei));
        }
        return Result.success(map);
    }


}
