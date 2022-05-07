package com.zzw.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PLData {
    private Integer num;  //喷淋数量
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;
    //是进场还是出场！
    private Integer purpose;

}
