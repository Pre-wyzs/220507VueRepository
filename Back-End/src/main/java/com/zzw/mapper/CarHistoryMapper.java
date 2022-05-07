package com.zzw.mapper;

import com.zzw.entity.Car;
import com.zzw.entity.CarHistory;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface CarHistoryMapper {

    //这个模块除了增，删和查之外是不需要改的。
    //还有它的查询也和普通的查询是不太一样的，它需要先筛选出同样的车牌号的一组数据，这个主要是用于对于地图上路径显示的一个
    //外部接口，如果是在增删改查的那个表中的话，直接使用查询车牌号和车架号就行了。查询时间戳和车辆的状态都没偶什么意义，
    //因为那都不是实时的数据，实时的数据主要可以在地图上展示。

    //导出所有的轨迹历史数据
    public List<CarHistory> selectAll();

    //查询总接口
    public List<CarHistory> generalSelectInterface(Map<String,Object> map);
    public Integer generalSelectInterfaceNum(Map<String,Object> map);

    //mapSelect地图查询的接口
    //他返回一辆车的指定一段时间内的数据，至于多辆车的情况就由service层的数据来处理吧。
    public List<CarHistory> selectOneCar(String IMEI);

    //增加一条轨迹的记录
    public Integer addCarHistory(CarHistory carHistory);

    //删除一条轨迹的记录
    public Integer delCarHistory(Integer id);//这个我还真的不建议批量删除，首先是因为这个数据的重要性。


    //修改一条轨迹的有关信息
    public Integer updateCarHistory(Map<String,Object> map);

    //根据设备号删除车辆的轨迹路径
    public Integer delHistoryByIMEI(String IMEI);

}
