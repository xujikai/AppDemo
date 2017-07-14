package com.xjk.android.bean;

/**
 * Created by admin on 2016/6/10.
 */
public class ApiBean<T> {

    private int result;//状态码
    private T data;//返回成功数据
    private String msg;//错误提示信息

//   private int status;//状态码
//   private T data;//返回成功数据
//   private String msg;//错误提示信息

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
