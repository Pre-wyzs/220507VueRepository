package com.zzw.service;

import com.zzw.entity.SignConfig;

public interface SignConfigService {

    public Integer setSignTimes(SignConfig config);

    public SignConfig showSignTimes();

}
