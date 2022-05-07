package com.zzw.utils;

//返回给前端的类Result
public class Result<T> {

    private String code;
    private String msg;
    private T data;
    private Integer totalPages;

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Result(T data,Integer totalPages){
        this.data = data;
        this.totalPages = totalPages;
    }

    public Result(T data) {
        this.data = data;
    }

    public Result() {

    }

    //成功时返回的静态方法
    public static Result success() {
        Result ret = new Result<>();
        ret.setCode("0");
        ret.setMsg("成功");
        return ret;

    }

    public static Result success(Integer totalPages) {
        Result ret = new Result<>();
        ret.setCode("0");
        ret.setMsg("成功");
        ret.setTotalPages(totalPages);  //设置返回给前端的总页数
        return ret;
    }

    public static <T>Result<T> success(T data) {
        Result<T> result = new Result<>(data);
        result.setCode("0");
        result.setMsg("成功");
        return result;
    }

    public static <T>Result<T> success(T data,Integer totalPages) {
        Result<T> result = new Result<>(data,totalPages);
        result.setCode("0");
        result.setMsg("成功");
        return result;
    }



    public static Result error(String code,String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }


}

