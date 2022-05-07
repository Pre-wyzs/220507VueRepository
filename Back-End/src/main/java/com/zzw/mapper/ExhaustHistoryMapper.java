package com.zzw.mapper;

import com.zzw.entity.ExhaustHistory;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface ExhaustHistoryMapper {


    //查询所有，可能导出所有的excel的时候会用到（真是有先见之明）
    public List<ExhaustHistory> selectAll();

    //总的接口查询，主要就是根据成分名称和日期来进行查询，同时还要完成分页的
    public List<ExhaustHistory> generalSelectInterface(Map<String,Object> map);
    public Integer generalSelectInterfaceNum(Map<String,Object> map);

    //只显示报警记录的总接口查询。
    public List<ExhaustHistory> generalExceedInterface(Map<String,Object> map);
    public Integer generalExceedInterfaceNum(Map<String,Object> map);



    //提供给外部设备的增加记录的接口
    public Integer addExhaustHistory(ExhaustHistory exhaustHistory);


    //删除的话还是根据选中的id号来进行的删除捏。
    public Integer delExhaustHistory(Integer id);


}
