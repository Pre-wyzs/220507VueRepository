package com.zzw.controller;

import com.zzw.entity.Car;
import com.zzw.service.CarService;
import com.zzw.utils.GeneralSelectUtils;
import com.zzw.utils.Result;
import com.zzw.utils.jwttoken.JwtUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/api/car/info")
@RestController
public class CarController {

    @Autowired
    CarService service;

    //导出所有的车辆的数据
    @GetMapping("/all")
    @CrossOrigin
    public Result selectAll(){
        return Result.success(service.selectAll());
    }

    //返回前端所有已经报备的车辆的imei设备号
    @GetMapping("/all/imeis")
    @CrossOrigin
    public Result selectAllIMEIS(){
        return Result.success(service.selectAllIMEIS());
    }

    @PutMapping("/general")
    @CrossOrigin
    public Result generalSelectInterface(@RequestBody Car car){

        Map<String,Object> map = new HashMap<>();
        Integer totalPages = 1;
        map.put("name",car.getName());
        map.put("team",car.getTeam());
        map.put("IMEI",car.getIMEI());
        map.put("license",car.getLicense());
        map.put("startDate",car.getStartDate());
        map.put("endDate",car.getEndDate());

        totalPages = GeneralSelectUtils.setTotalPages(service.generalSelectInterfaceNum(map),car.getPageSize());
        map.put("startIndex",GeneralSelectUtils.setStartIndex(car.getPageNum(),car.getPageSize()));
        map.put("pageSize",car.getPageSize());

        List<Car> cars = service.generalSelectInterface(map);

        return Result.success(cars,totalPages);
    }

    @PostMapping("/add")
    @CrossOrigin
    public Result addCar(@RequestBody Car car){
        /**
         * 接受并解析前端传过来的token令牌。
         *
         * 这个的代码是很简单的，权限验证透过就让他添加写到数据库里就行了
         * */
        //设置备案的时间是吧。
        Date date = new Date(System.currentTimeMillis()+28800000);

        if(JwtUtils.tokenParse(car.getToken()).get("power").toString().compareTo("2")!=0){
            car.setTimeStamp(date);
            service.addCar(car);
            return Result.success();
        }
        return Result.error("-1","权限不够");  //返回给前端插入成功或失败的信息
    }



    @PostMapping("/update")
    @CrossOrigin
    public Result updateCar(@RequestBody Car car) {
        /**
         * 接受并解析前端传过来的token令牌。
         * */
        if(JwtUtils.tokenParse(car.getToken()).get("power").toString().compareTo("2") != 0) {
            service.updateCar(car);
            return Result.success();
        }
        return Result.error("-1","权限不够");  //返回给前端插入成功或失败的信息
    }

    //删除一条历史记录
    @PostMapping("/del")
    @CrossOrigin
    public Result delCar(@RequestBody Car car) {
        /**
         * 接受并解析前端传过来的token令牌。
         * 
         * */
        if(JwtUtils.tokenParse(car.getToken()).get("power").toString().compareTo("2") != 0) {
            /** 同样只有超级管理和普通管理有权限进行设备历史记录的删除捏*/
            service.delCar(car.getIMEI());
            return Result.success();
        }
        return Result.error("-1","权限不够");  //返回给前端插入成功或失败的信息
    }

    //删除多条历史记录
    @PostMapping("/dels")
    @CrossOrigin
    public Result delCars(@RequestBody String[] imeis) {
        /**
         * 接受并解析前端传过来的token令牌。
         * */
        System.out.println(imeis);
        /** 同样只有超级管理和普通管理有权限进行设备历史记录的删除捏*/
        if(JwtUtils.tokenParse(imeis[imeis.length-1]).get("power").toString().compareTo("2") != 0){
            //这段代码的效率可能没有 for i 的效率高哦。。。
            for (String imei : ArrayUtils.remove(imeis,imeis.length-1)) {
                service.delCar(imei);
            }
            return Result.success();
        }
        return Result.error("-1","权限不够");  //返回给前端插入成功或失败的信息
    }

}
