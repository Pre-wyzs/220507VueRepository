package com.zzw.controller;

import com.zzw.entity.SignConfig;
import com.zzw.service.SignConfigService;
import com.zzw.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/worker/setting")
public class SignConfigController {

    @Autowired
    SignConfigService service;

    @PostMapping("/sign")
    @CrossOrigin
    public Result setSignTimes(@RequestBody SignConfig config){
        return Result.success(service.setSignTimes(config));
    }


}
