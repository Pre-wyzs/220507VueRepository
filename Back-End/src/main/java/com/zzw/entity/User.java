package com.zzw.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor  //无参构造，有参构造
@AllArgsConstructor
public class User {
    private Integer id;
    private String name;
    private String password;
    private String power;
    private String token;  //用户的令牌

}
