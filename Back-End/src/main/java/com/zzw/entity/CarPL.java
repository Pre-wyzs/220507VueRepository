package com.zzw.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarPL {
    private Integer id;
    private String license;
    private String team;
    private Integer purpose;
    private Integer status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date timeStamp;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;


    /**
     * 这部分数据在前台直接展示就行了，可能不需要 分页啊，权限这些东西了捏...
     * */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDate;


}
