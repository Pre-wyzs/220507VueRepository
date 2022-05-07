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
public class SignConfig {

    @JsonFormat(pattern = "HH:mm:ss")  //作为json数据返回给前端的时候起作用
    @DateTimeFormat(pattern = "HH:mm:ss")  //从前端传递过来的时候起作用
    private Date inTime;

    @JsonFormat(pattern = "HH:mm:ss")
    @DateTimeFormat(pattern = "HH:mm:ss")
    private Date outTime;
}
