package com.zzw.service.impl;

import com.zzw.entity.SignConfig;
import com.zzw.mapper.SignConfigMapper;
import com.zzw.service.SignConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SignConfigServiceImpl implements SignConfigService {

    @Autowired
    SignConfigMapper mapper;

    @Override
    public Integer setSignTimes(SignConfig config) {
        return mapper.setSignTimes(config);
    }

    @Override
    public SignConfig showSignTimes() {
        return mapper.showSignTimes();
    }
}
