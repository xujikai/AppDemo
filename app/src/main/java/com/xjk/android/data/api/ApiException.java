package com.xjk.android.data.api;

/**
 * 服务器数据返回异常
 * Created by xxx on 2016/10/31.
 */

public class ApiException extends Exception {

    public ApiException(String message) {
        super(message);
    }
}
