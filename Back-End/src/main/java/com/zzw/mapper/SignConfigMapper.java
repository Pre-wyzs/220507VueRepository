package com.zzw.mapper;

import com.zzw.entity.SignConfig;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface SignConfigMapper {

    public Integer setSignTimes(SignConfig config);

    public SignConfig showSignTimes();


}
