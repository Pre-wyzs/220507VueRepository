package com.zzw.service.impl;

import com.zzw.entity.Worker;
import com.zzw.mapper.WorkerMapper;
import com.zzw.service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class WorkerServiceImpl implements WorkerService {

    @Autowired
    WorkerMapper workerMapper;

    @Override
    public Worker selectIdByPhoto(String photo) {

        Worker worker = workerMapper.selectIdByPhoto(photo);
        System.out.println(worker);

        return worker;
    }

    @Override
    public Worker selectById(Integer id) {
        return workerMapper.selectById(id);
    }

    @Override
    public List<Worker> selectAll() {
        return workerMapper.selectAll();
    }

    @Override
    public List<Worker> generalSelectInterface(Map<String, Object> map) {
        return workerMapper.generalSelectInterface(map);
    }

    @Override
    public Integer generalSelectInterfaceNum(Map<String, Object> map) {
        return workerMapper.generalSelectInterfaceNum(map);
    }

    @Override
    public Integer addWorker(Worker worker) {
        return workerMapper.addWorker(worker);
    }

    @Override
    public Integer delWorker(Integer id) {
        return workerMapper.delWorker(id);
    }

    @Override
    public Integer updateWorker(Worker worker) {
        return workerMapper.updateWorker(worker);
    }

    @Override
    public Integer updateWorkerStatus(Map<String, Object> map) {
        return workerMapper.updateWorkerStatus(map);
    }
}
