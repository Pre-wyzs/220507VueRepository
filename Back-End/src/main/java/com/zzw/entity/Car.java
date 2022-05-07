package com.zzw.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Car {

    private Integer id;
    private String IMEI;
    private String name;
    private String team;
    private String license;
    //初步推测这个东西可能不需要了》。。因为在json的格式下传递参数，无论是接收还是发送，只要有@JsonFormat就都能做到了...
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date timeStamp;
    private Double jd;
    private Double wd;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDate;

    //token
    private String token;
    private Integer pageNum;
    private Integer pageSize;

}
