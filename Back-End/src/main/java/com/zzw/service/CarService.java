package com.zzw.service;

import com.zzw.entity.Car;

import java.util.List;
import java.util.Map;

public interface CarService {

    //导出所有的车辆数据
    public List<Car> selectAll();

    //获得所有的车辆的IMEI号，因为在地图上画点的时候是用得到的东西捏！
    public String[] selectAllIMEIS();

    //查询总接口
    public List<Car> generalSelectInterface(Map<String,Object> map);
    public Integer generalSelectInterfaceNum(Map<String,Object> map);


    //增加一辆车
    public Integer addCar(Car car);

    //删除一辆车
    public Integer delCar(String IMEI);

    //修改车辆的信息
    public Integer updateCar(Car car);


}
