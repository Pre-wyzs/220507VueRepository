package com.zzw.mapper;

import com.zzw.entity.CarPL;
import com.zzw.entity.PLData;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface CarPLMapper {

    //返回所有的喷淋数据对吧
    public List<CarPL> selectAll();

    //因为要在大屏上显示的对吧，所有直接返回今天的喷淋数据就行了，
    //往期的喷淋数据可以通过后台的echarts看到的捏
    public List<CarPL> selectCurrentDay(Map<String,Object> map);

    //外部设备添加一条喷淋记录到数据库
    public Integer addCarPL(CarPL carPL);


    //修改是没有必要的
    //删除的话我打算放在这个，设置中做一个能删除指定的时间段内的数据功能，这样就能解决喷淋数据没有设计删除页面的缺陷了
    //这个删除应该是我做过的最特别的吧。
    public Integer delCalPL(Map<String,Object> map);


    //怎么把这些数据展示在图表上确实是一个很艺术的事情了。
    //因为要展示的x轴是一个个的日期所以要返回一组组日期数据才行捏。
    public List<PLData> selectPLData();

    public String[] selectAllDate();


    /******************************************Index相关的接口 start************************************************/
    //查找今日的喷淋过的车辆的数据
    public List<CarPL> indexSelectPL(Map<String,Object> map);



    /******************************************Index相关的接口 end************************************************/

}
