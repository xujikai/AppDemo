package com.xjk.android.bean;

/**
 * 上传图片，上传视频
 * Created by xxx on 2016/12/12.
 */

public class UploadBean {

    private String message;
    private int result;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "UploadBean{" +
                "result=" + result +
                ", message='" + message + '\'' +
                '}';
    }
}
