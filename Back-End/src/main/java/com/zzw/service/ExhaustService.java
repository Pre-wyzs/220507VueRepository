package com.zzw.service;

import com.zzw.entity.Exhaust;

import java.util.List;
import java.util.Map;

public interface ExhaustService {



    public List<Exhaust> selectAll();

    public List<Exhaust> selectByLimit(Map<String,Object> map);

    //增加一条记录
    public Integer addExhaustData(Exhaust exhaust);

    //删除一条记录
    public Integer delExhaustData(Integer id);

    public Integer delExhaustDataMonth(Map<String,Object> map);

    /*******************************************index主页要用到的接口*************************************************************/
    //返回最近的一条记录信息就行了
    public Exhaust selectLatest();


}
