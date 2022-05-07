package com.zzw.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Worker {
    private Integer id;
    private String name;
    private String team;
    private String idCard;
    private Integer age;
    private Integer sex;
    private String phone;
    private String enterprise;
    private String kind;
    private String photo;
    private Integer status;
    //这个令牌是为了增删改的操作得到权限的管控
    private String token;
    //把分页的也加入到这个实体类当中：默认第1页，7条记录
    private Integer pageNum = 1;
    private Integer pageSize = 6;
}
