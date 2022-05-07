package com.zzw.service.impl;

import com.zzw.entity.Car;
import com.zzw.entity.CarHistory;
import com.zzw.mapper.CarHistoryMapper;
import com.zzw.mapper.CarMapper;
import com.zzw.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class CarServiceImpl implements CarService {

    @Autowired
    CarMapper mapper;
    @Autowired
    CarHistoryMapper historyMapper;


    @Override
    public List<Car> selectAll() {
        List<Car> cars = mapper.selectAll();
        System.out.println(cars);
        return cars;
    }

    @Override
    public String[] selectAllIMEIS() {
        return mapper.selectAllIMEIS();
    }

    @Override
    public List<Car> generalSelectInterface(Map<String, Object> map) {
        return mapper.generalSelectInterface(map);
    }

    @Override
    public Integer generalSelectInterfaceNum(Map<String, Object> map) {
        return mapper.generalSelectInterfaceNum(map);
    }

    @Override
    public Integer addCar(Car car) {

        CarHistory history = new CarHistory();

        history.setCarStatus(0);  //初始情况下一定是熄火的，这样设计不太会出错;
        history.setTimeStamp(car.getTimeStamp());
        history.setIMEI(car.getIMEI());
        //设置车辆的出生点，就是西湖那个地方哈哈
        history.setJd(car.getJd());
        history.setWd(car.getWd());
        history.setLicense(car.getLicense());
        historyMapper.addCarHistory(history);
        return mapper.addCar(car);
    }

    //是根据设备的imei号进行删除的
    @Override
    public Integer delCar(String IMEI) {
        //同时删除该车辆的所有历史轨迹捏，真棒捏
        historyMapper.delHistoryByIMEI(IMEI);
        return mapper.delCar(IMEI);
    }

    @Override
    public Integer updateCar(Car car) {
        Map<String,Object> map = new LinkedHashMap<>();
        map.put("license",car.getLicense());
        map.put("IMEI",car.getIMEI());
        historyMapper.updateCarHistory(map);
        return mapper.updateCar(car);
    }
}
