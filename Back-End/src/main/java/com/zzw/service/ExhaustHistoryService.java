package com.zzw.service;

import com.zzw.entity.ExhaustHistory;

import java.util.List;
import java.util.Map;

public interface ExhaustHistoryService {

    //查询所有，可能导出所有的excel的时候会用到
    public List<ExhaustHistory> selectAll();

    //总的接口查询，主要就是根据成分名称和日期来进行查询，同时还要完成分页的
    public List<ExhaustHistory> generalSelectInterface(Map<String,Object> map);
    public Integer generalSelectInterfaceNum(Map<String,Object> map);

    //只显示报警记录的总接口查询。
    public List<ExhaustHistory> generalExceedInterface(Map<String,Object> map);
    public Integer generalExceedInterfaceNum(Map<String,Object> map);

    //提供给外部设备的增加记录的接口
    @Deprecated
    public Integer addExhaustHistory(ExhaustHistory exhaustHistory);

    public Integer addExhaustHistorys(List<ExhaustHistory> historyList);

    //删除的话还是根据选中的id号来进行的删除捏。
    public Integer delExhaustHistory(Integer id);


}
