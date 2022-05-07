package com.zzw.service;

import com.zzw.entity.Dust;

import java.util.List;
import java.util.Map;

public interface DustService {

    //查询所有的记录：全部导出excel的时候会用到
    public List<Dust> selectAll();


    //经典的分页查询：根据严重程度和时间戳来查询返回数据
    public List<Dust> generalSelectInterface(Map<String,Object> map);
    public Integer generalSelectInterfaceNum(Map<String,Object> map);

    //外部设备传输进来的新的一条记录
    public Integer addDust(Dust dust);

    //删除一条记录
    public Integer delDust(Integer id);

    /******************************************Index相关的接口 start************************************************/
    //返回前端最新的7条记录，然后
    public List<Dust> selectLatest(Integer size);

}
