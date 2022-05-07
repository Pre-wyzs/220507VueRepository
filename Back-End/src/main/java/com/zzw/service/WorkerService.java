package com.zzw.service;


import com.zzw.entity.Worker;

import java.util.List;
import java.util.Map;

public interface WorkerService {

    public Worker selectIdByPhoto(String photo);
    public Worker selectById(Integer id);

    public List<Worker> selectAll();
    //总查询接口
    public List<Worker> generalSelectInterface(Map<String,Object> map);
    public Integer generalSelectInterfaceNum(Map<String,Object> map);

    //增；可以手动在增加页面增加，也可以通过扫描二维码的方式添加
    public Integer addWorker(Worker worker);

    //删：一次删一个，多次还是用循环吧，习惯了
    public Integer delWorker(Integer id);

    //改：
    public Integer updateWorker(Worker worker);

    //只改：员工当前的状态：
    public Integer updateWorkerStatus(Map<String,Object> map);


}
