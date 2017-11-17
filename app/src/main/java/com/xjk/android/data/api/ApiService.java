package com.xjk.android.data.api;

import com.xjk.android.bean.ApiBean;
import com.xjk.android.bean.LoginBean;
import com.xjk.android.bean.UploadBean;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * 接口列表
 * Created by xxx on 2016/9/27.
 */
public interface ApiService {

    /**
     * 用户登录
     * @param phone     手机号
     * @param code      验证码
     */
    @FormUrlEncoded
    @POST("/api/login")
    Observable<ApiBean<LoginBean>> login(@Field("phone") String phone, @Field("code") String code);

//    @GET("https://publicobject.com/helloworld.txt")
    @GET("https://kyfw.12306.cn/otn")
    Observable<String> getTest();

    @FormUrlEncoded
    @POST("https://appv2.youguoquan.com/Users/Common/GetInfo")
    Observable<String> testGirl(@Field("Version") String version,
                                @Field("UserId") String userId,
                                @Field("Auth") int auth,
                                @Field("Platform") String platform,
                                @Field("EquipmentCode") String equipmentCode,
                                @Field("AgentCode") String agentCode,
                                @Field("Token") String token);

    /**
     * 上传单个文件
     */
    @Multipart
    @POST("http://uploader.zsoftware.cn/api/v1/picture/upload")
    Observable<UploadBean> uploadFile(@Part MultipartBody.Part file, @Part("appkey") RequestBody appKey, @Part("appscret") RequestBody appSecret);
}
