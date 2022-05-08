package com.zzw.controller;

import com.zzw.entity.SignConfig;
import com.zzw.entity.Worker;
import com.zzw.entity.WorkerSign;
import com.zzw.service.SignConfigService;
import com.zzw.service.WorkerService;
import com.zzw.service.WorkerSignService;
import com.zzw.utils.GeneralSelectUtils;
import com.zzw.utils.Result;
import com.zzw.utils.facesign.BaiduAipUtil;
import com.zzw.utils.jwttoken.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@RestController
//考勤签到的接口
@RequestMapping("/api/worker/sign")
public class WorkerSignController {

    @Autowired
    WorkerSignService signService;

    @Autowired
    WorkerService workerService;

    @Autowired
    SignConfigService configService;

    @Autowired
    BaiduAipUtil aipUtil;

    /**
     * 这个接口是实现刷脸签到的后端的接口，
     * 首先接收笔记本摄像头拍摄的一张照片，然后通过baiduAiUtil中的比对方法找那个人的唯一id
     * 有了这个唯一id说明人已经签到成功了,从worker表中查询出该id对应的信息，然后往worker_sign表中添加一条记录，然后
     * 返回签到成功的结果。
     * */

    /** 获取配置的时间*/
    public SignConfig retSignConfigTimes(){
        return configService.showSignTimes();
    }

    /** 比较两个时间大小的接口函数：如果当前时间比配置时间大就返回1，不然就返回0*/
    public Integer compareTimes(String configTime,String currentTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        try {
            Date date1 = sdf.parse(configTime);
            Date date2 = sdf.parse(currentTime);
            if(date2.compareTo(date1) > 0){
                return 1;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }



    @PostMapping("/face")
    @CrossOrigin
    public Result faceSign(@RequestParam(name = "file")String imgBase64,  //上传进来的照片
                           @RequestParam(name = "code")Integer code){ //进出场的标志，如果是1代表进场，2代表出场

        //首先还是先获取一下时间戳，为了对比是否迟到或早退用的
//        System.out.println(TimeZone.getDefault());
//        TimeZone.setDefault(TimeZone.getTimeZone("UTC+08:00"));

        long systemTime =  System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Date currentDate = new Date(systemTime + 28800000);  //东八区
        String currentTime = sdf.format(systemTime);
        SignConfig config = retSignConfigTimes();  //获取配置好的时间

        Worker worker = null;
        WorkerSign workerSign = new WorkerSign();
        //检查上传的照片是否人脸照片，如果不是直接返回error
        if(aipUtil.isFaceImgByBase64(imgBase64)){
            //把照片上传到百度云进行检查
            String uid = aipUtil.faceSearchByBase64(imgBase64);
            if(uid!=null){
                worker = workerService.selectById(Integer.parseInt(uid));
                workerSign.setName(worker.getName());
                workerSign.setIdCard(worker.getIdCard());
                workerSign.setTeam(worker.getTeam());
                workerSign.setEnterprise(worker.getEnterprise());
                workerSign.setPurpose(code);
                workerSign.setTimeStamp(currentDate);
                //还要对签到状态进行设置捏
                if(code == 1){//如果是进场的情况
                    String tmp = sdf.format(config.getInTime());
                    if(compareTimes(tmp,currentTime) == 1){
                        workerSign.setSign(2);
                    }else{
                        workerSign.setSign(1);
                    }
                }else{
                    String tmp = sdf.format(config.getOutTime());
                    if(compareTimes(tmp,currentTime) == 1){
                        workerSign.setSign(1);
                    }else{
                        workerSign.setSign(4);
                    }
                }
                /**============通过上面的操作就能实现对一条签到记录的构造，然后把这条记录写到worker_sign表中=======================================================*/
                signService.addWorkerSign(workerSign);
            }
        }else{
            return Result.error("-1","读取不到人脸信息，请重新拍摄!");
        }
        return Result.success("签到成功!");
    }


    @GetMapping("/all")
    @CrossOrigin
    public Result selectAll(){
        return Result.success(signService.selectAll());
    }


    @PutMapping("/general")
    @CrossOrigin
    public Result generalSelectInterface(@RequestBody WorkerSign workerSign){

        Map<String,Object> map = new HashMap<>();
        synchronized (map){
            map.put("name",workerSign.getName());
            map.put("team",workerSign.getTeam());
            map.put("enterprise",workerSign.getEnterprise());
            map.put("idCard",workerSign.getIdCard());
            map.put("purpose",workerSign.getPurpose());
            map.put("sign",workerSign.getSign());
            map.put("startTime",workerSign.getStartTime());
            map.put("endTime",workerSign.getEndTime());
            map.put("startIndex",(workerSign.getPageNum()- 1)*workerSign.getPageSize());
            map.put("pageSize",workerSign.getPageSize());
        }

        Integer totalPages = GeneralSelectUtils.setTotalPages(signService.generalSelectInterfaceNum(map),workerSign.getPageSize());
        List<WorkerSign> workerSignList = signService.generalSelectInterface(map);
        return Result.success(workerSignList,totalPages);
    }


//    增加的接口还没有考虑好要怎么做因为要考虑到人脸识别考勤的设备的实现，又要模拟设备实现，可能又要在后端项目中添加新的包了

    //删除的接口
    @PostMapping("/del")
    @CrossOrigin
    public Result deleteWorkerSign(@RequestBody WorkerSign workerSign) {
        /**
         * 接受并解析前端传过来的token令牌。
         * */
        String token = workerSign.getToken();
        //token解析
        Claims claims = JwtUtils.tokenParse(token);

        if(claims.get("power").toString().compareTo("0") == 0){
            signService.delWorkerSign(workerSign.getId());
            return Result.success();
        }
        return Result.error("-1","权限不够");  //返回给前端插入成功或失败的信息
    }

    //批量删除的接口
    @PostMapping("/dels")
    @CrossOrigin
    public Result delWorkerSigns(@RequestBody String[] ids){
        /**
         * 接受并解析前端传过来的token令牌。
         * */

        System.out.println(ids);
        Claims claims = JwtUtils.tokenParse(ids[ids.length-1]);

        /** 同样只有超级管理和普通管理有权限进行设备历史记录的删除捏*/
        if(claims.get("power").toString().compareTo("2") != 0){
            for(int i=0;i<ids.length-1;i++){
                signService.delWorkerSign(Integer.parseInt(ids[i]));
            }
            return Result.success();
        }
        return Result.error("-1","权限不够");  //返回给前端插入成功或失败的信息
    }

    //修改补签到的接口
    @PostMapping("/update")
    @CrossOrigin
    public Result updateWorkerSign(@RequestBody WorkerSign workerSign){
        /**
         * 接受并解析前端传过来的token令牌。
         * */
        String token = workerSign.getToken();
        //token解析
        Claims claims = JwtUtils.tokenParse(token);
        /** 只有超级管理员和普通管理员可以去修改员工的信息捏*/
        String power = claims.get("power").toString();
        if(power.compareTo("0")==0 || power.compareTo("1")==0){
            signService.updateWorkerSign(workerSign);
            return Result.success();
        }
        return Result.error("-1","权限不够");  //返回给前端插入成功或失败的信息
    }


    @PostMapping("/add")
    @CrossOrigin
    public Result addWorkerSign(@RequestBody WorkerSign sign){
//        因为是补签，所以要设置签到状态为补签的状态；
        sign.setSign(3);
        if(signService.addWorkerSign(sign) == 1){
            return Result.success("补签成功!");
        }
        return Result.error("-1","补签失败!");
    }

}
