package com.zzw.utils;

public class GeneralSelectUtils {

    //设置limit分页时候的第一个参数
    public static Integer setStartIndex(Integer pageNum,Integer pageSize){
        return (pageNum-1)*pageSize;
    }

    //设置totalPages
    /**
     * 参数一是总的数据库记录数
     * 参数二是分页的每一页的大小
     *
     * */
    public static Integer setTotalPages(Integer rowNum,Integer pageSize){
        Integer totalPages = 1;
        if(rowNum%pageSize == 0){
            totalPages = rowNum/pageSize;
        }else {
            totalPages = rowNum/pageSize+ 1;
        }
        return totalPages;
    }


}
