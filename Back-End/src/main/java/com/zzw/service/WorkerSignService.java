package com.zzw.service;

import com.zzw.entity.WorkerSign;

import java.util.List;
import java.util.Map;

public interface WorkerSignService {


    public List<WorkerSign> selectAll();
    //签到查询的总接口
    public List<WorkerSign> generalSelectInterface(Map<String,Object> map);
    public Integer generalSelectInterfaceNum(Map<String,Object> map);

    //增：增加的话要通过人脸的识别认证
    public Integer addWorkerSign(WorkerSign workerSign);

    //删：这个应该没有什么好说的
    public Integer delWorkerSign(Integer id);

    //改的话就是如果是未签到的话，就在操作栏添加一个按钮，就是用来补签的按钮，然后把它的状态该成补签，以及有补签时间戳就行了。
    public Integer updateWorkerSign(WorkerSign workerSign);


    /***********************************index要用到的接口*********************************************************/
    //返回一天的签到人数
    public Integer signNumADay(Map<String,Object> map);

}
