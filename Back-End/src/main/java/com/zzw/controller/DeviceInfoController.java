package com.zzw.controller;

import com.zzw.entity.DeviceInfo;
import com.zzw.service.DeviceInfoService;
import com.zzw.utils.GeneralSelectUtils;
import com.zzw.utils.Result;
import com.zzw.utils.jwttoken.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/deviceInfo")
public class DeviceInfoController {

    @Autowired
    DeviceInfoService deviceInfoService;

    @GetMapping("/general")
    @CrossOrigin
    public Result generalSelectInterface(@RequestParam(required = false) String nameSure,
                                                   @RequestParam(required = false) String nameFuzzy,
                                                   @RequestParam(required = false) Integer status,
                                                   @RequestParam(required = false,defaultValue = "1") Integer pageNum,
                                                   @RequestParam(required = false,defaultValue = "5") Integer pageSize)
    {
        if(nameSure.compareTo("") == 0){
            nameSure = null;
        }
        if(nameFuzzy.compareTo("") == 0){
            nameFuzzy = null;
        }
        if(status == null){
            System.out.println("状态没传");
        }

        Map<String,Object> map = new HashMap<>();
        synchronized (map){
            map.put("nameSure",nameSure);
            map.put("nameFuzzy",nameFuzzy);
            map.put("status",status);
            map.put("startIndex",(pageNum - 1)*pageSize);
            map.put("pageSize",pageSize);
        }

        //获取总的记录数
        Integer totalPages = GeneralSelectUtils.setTotalPages(deviceInfoService.generalSelectInterfaceNum(map),pageSize);
        return Result.success(deviceInfoService.generalSelectInterface(map),totalPages);
    }


    /** 2、添加一个设备*/
    @PostMapping("/add")
    @CrossOrigin
    public Result addDevice(@RequestBody DeviceInfo deviceInfo){
        /**
         * 接受并解析前端传过来的token令牌。
         * */
        String token = deviceInfo.getToken();
        //token解析
        Claims claims = JwtUtils.tokenParse(token);

        if(claims.get("power").toString().compareTo("2") != 0){  //只有超级管理和普通管理可以添加设备，普通用户不能添加设备
            deviceInfoService.addDevice(deviceInfo);
            return Result.success();
        }
        return Result.error("-1","权限不够");  //返回给前端插入成功或失败的信息
    }

    /** 3、更新一台设备，其实就是更新它的名字而已了*/
    @PutMapping("/update")
    @CrossOrigin
    public Result updateDevice(@RequestBody DeviceInfo deviceInfo) {
        /**
         * 接受并解析前端传过来的token令牌。
         * */
        String token = deviceInfo.getToken();
        //token解析
        Claims claims = JwtUtils.tokenParse(token);

        String power = claims.get("power").toString();
        String id = claims.get("id").toString();

        /** 只有超级管理员和普通管理员可以对设备进行update，普通用户只能看看*/
        if(power.compareTo("2") != 0) {
            deviceInfoService.updateDevice(deviceInfo);
            return Result.success();
        }
        return Result.error("-1","权限不够");  //返回给前端插入成功或失败的信息
    }

    //删除一个设备
    @PostMapping("/del")  //restful风格的参数传递
    @CrossOrigin
    public Result deleteDevice(@RequestBody DeviceInfo deviceInfo) {
        /**
         * 接受并解析前端传过来的token令牌。
         * */
        String token = deviceInfo.getToken();
        Claims claims = JwtUtils.tokenParse(token);

        /** 同样只有超级管理和普通管理有权限进行设备的删除捏*/
        if(claims.get("power").toString().compareTo("2") != 0){
            deviceInfoService.delDevice(deviceInfo.getDeviceId());
            return Result.success();
        }
        return Result.error("-1","权限不够");  //返回给前端插入成功或失败的信息
    }


    /** 5、设备模拟器 */
    @PutMapping("/updateStatus")
    @CrossOrigin
    public Result updateDeviceStatus(@RequestBody DeviceInfo deviceInfo) throws ParseException, IOException {
        /** 外部设备传过来的数据不需要去进行用户token的验证*/

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        if(deviceInfo.getStartTime()!=null){
            String dateString = "1000-00-00 00:00:00";
            Date date = dateFormat.parse(dateString);
            deviceInfo.setCloseTime(date);
        }

        if(deviceInfoService.updateDeviceStatus(deviceInfo) == 1){
            return Result.success();  //返回给前端插入成功或失败的信息
        }
        return Result.error("-1","设备数据更新失败了捏");  //返回给前端插入成功或失败的信息
    }


}
