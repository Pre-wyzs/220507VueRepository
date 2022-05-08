package com.zzw.controller;

import com.zzw.entity.CarPL;
import com.zzw.entity.Dust;
import com.zzw.service.*;
import com.zzw.utils.Result;
import com.zzw.utils.datetoweek.WeekOfDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


@RestController
@RequestMapping("/api/index/")
public class AllIndexController {

    private Date date = null;
    private String dateStr = null;
    private SimpleDateFormat  sdf = null;
    public AllIndexController(){
        this.sdf = new SimpleDateFormat("yyyy-MM-dd");
        this.date = new Date();
        this.dateStr = this.sdf.format(this.date);
    }

    /**
     * 推送地图的controller
     * */

    /**
     * 推送视频controller
     * */

    /**
     * 推送车辆进出数据的controller
     * 先分析一下：
     * 因为是车辆的进出数据，而每一次显示的只有三条记录
     * 所以每次只要推送三条记录就行了
     *当推送的时候还要触发之前做的那个转轮的效果，然后每次新增一条记录的还是偶就会往后反装一下.
     *
     * 因为这部分的数据也会被推送到喷淋的模块里面的所以应该自定义要推送的数据量是多少次独爱
     * */

    @Autowired
    CarPLService carPLService;

    //这个接口就是返回所有的5条喷淋数据
    /**接口作用：返回喷淋的数据
     * 接口参数：就是指定是进场的喷淋还是出场的喷淋，如果不指定的话那这个条件就不生效了
     * size:表示要返回的数据量的大小
     * */
    @GetMapping("/pl")
    @CrossOrigin
    public Result indexSelectPL(@RequestParam(required = false) Integer purpose,
                                @RequestParam Integer size){

        Map<String,Object> map = new HashMap<>();
        synchronized (map){
            map.put("date",sdf.format(date));
            map.put("purpose",purpose);
            map.put("size",size);
        }
        List<CarPL> plList = carPLService.indexSelectPL(map);
//        System.out.println(plList);
        return Result.success(plList);
    }


    /**
     *接口作用：返回喷淋的总的数量，以及进口的数量和出口的数量
     * */
    @GetMapping("/pl/num")
    @CrossOrigin
    public Result indexPLNum(){
        Map<String,Object> map = new HashMap<>();
        map = carPLService.selectPLData();
        return Result.success(map.get(dateStr));
    }



    /**
     *  推送设备的实时数据controller
     *
     * */
    @Autowired
    DeviceInfoService deviceInfoService;

    /**
     * 接口的作用，获取所有的设备当前的信息。
     * */
    @GetMapping("/device")
    @CrossOrigin
    public Result indexDevice(){
        return Result.success(deviceInfoService.selectAll());
    }



    /** * 推送考勤的实时数据controller
     *  */
    @Autowired
    WorkerSignService workerSignService;

    /**
     * 接口作用：获取本周的每一天的考勤的记录并发送给前端的考勤图表
     *
     * */
    @GetMapping("/sign")
    @CrossOrigin
    public Result indexSign(){
        //首先先创建一个LinkedMap比较好，因为返回的时候一定使用这个
        Map<String,Object> retMap = new LinkedHashMap<>();

        //参数的Map
        Map<String,Object> map = new HashMap<>();

        List<String> dates = WeekOfDate.getDates(date);
        for (int i = 0; i < dates.size(); i++) {
//            java map的key可以重复吗
//            如果重复添加的话，hashmap会自动覆盖key一样的数据，保证一个key对应一个value
            map.put("startTime",dates.get(i));
            if(i == dates.size() - 1){
                map.put("endTime",null);
            }else{
                map.put("endTime",dates.get(i+1));
            }
            retMap.put(WeekOfDate.getWeekOfDate(dates.get(i)),workerSignService.signNumADay(map));
        }

        return Result.success(retMap);
    }


    /**
     * * 推送扬尘的实时数据controller
     * */
    @Autowired
    DustService dustService;

    @GetMapping("/dust")
    @CrossOrigin
    public Result indexDust(@RequestParam(name = "size",required = false,defaultValue = "5") Integer size){
        //默认就返回最新的5条记录给index主页进行展示。
        List<Dust> dusts = dustService.selectLatest(size);
        return Result.success(dusts);
    }




    /**
     *  推送尾气在线排放的controller
     *
     *  */
    @Autowired
    ExhaustService exhaustService;

    @GetMapping("/exhaust")
    @CrossOrigin
    public Result indexExhaust(){
        return Result.success(exhaustService.selectLatest());
    }








}
