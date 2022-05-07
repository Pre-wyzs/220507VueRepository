package com.zzw.mapper;

import com.zzw.entity.Exhaust;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface ExhaustMapper {

    //这个接口没有修改的功能，他只有增，删除（按每个月一次定时清除所有数据...），


    //查询所有的数据记录
    public List<Exhaust> selectAll();

    //分页查询所有的数据记录：其实也不是分页，而是因为数据要展示在图表上面对吧，所以要展示24小时之内的数据，
    //至于24小时之前的数据要怎么展示在图表上内？干脆别展示了。。。屁事多
    //至于是展示24小时内的数据还是一周之内的数据，还是24小时比较好把，因为这种数据你看一周以前的有意思吗,你的主要目的
    //是通过这些数据实时看看有没有报警而已，不是拿长期的数据做分析的啊好吧。。。
    public List<Exhaust> selectByLimit(Map<String,Object> map);


    //增加一条记录
    public Integer addExhaustData(Exhaust exhaust);

    /** 这个接口被废弃了*/
    //删除一条记录
    public Integer delExhaustData(Integer id);

    //删除一条记录没啥意思...这里使用的场景应该是删除一个月之前的记录
    public Integer delExhaustDataMonth(Map<String,Object> map);

    /*******************************************index主页要用到的接口*************************************************************/
    //返回最近的一条记录信息就行了
    public Exhaust selectLatest();



}
