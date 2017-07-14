package com.xjk.android.data;

import android.support.annotation.NonNull;

import com.xjk.android.bean.LoginBean;
import com.xjk.android.bean.UploadBean;
import com.xjk.android.constant.Constants;
import com.xjk.android.data.api.Api;
import com.xjk.android.data.api.ApiService;
import com.xjk.android.utils.RxUtils;

import java.io.File;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * DataManager应该拥有ApiHelper和DbHelper两个对象，和内存缓存Map<String, Task> mCachedTasks;
 *
 * 1.正常情况
 *      直接从网络获取
 * 2.缓存情况
 *      1.添加变量：是否强制刷新
 *      2.获取：
 *          final Task cachedTask = getTaskWithId(taskId);
 *          如果不为null，直接返回
 *          Observable<Task> localTask = mTasksLocalDataSource.getTask(taskId)
 *                                          .doOnNext(task -> mCachedTasks.put(taskId, task))
 *                                          .first();
 *          Observable<Task> remoteTask = mTasksRemoteDataSource.getTask(taskId)
 *                                          .doOnNext(task -> {
 *                                              将数据添加至数据库和内存中
 *                                              mTasksLocalDataSource.saveTask(task);
 *                                              mCachedTasks.put(task.getId(), task);
 *                                          });
 *          将最终数据返回
 *          Observable.concat(localTasks, remoteTasks)
 *               .filter(tasks -> !tasks.isEmpty())
 *               .first();
 *
 */

public class DataManager {

    private static DataManager mInstance;
    private ApiService mApiService;

    public DataManager(){
        mApiService = Api.getInstance().getApiService();
    }

    public static DataManager getInstance(){
        if(mInstance == null){
            mInstance = new DataManager();
        }
        return mInstance;
    }

    public Observable<LoginBean> login(String phone, String code){
        return mApiService.login(phone,code).compose(RxUtils.<LoginBean>handleResult())
                .compose(RxUtils.<LoginBean>io_main());
    }

    public Observable<String> getTest(){
        return mApiService.getTest()
                .compose(RxUtils.<String>io_main());
    }

    /**
     * 上传单个文件
     */
    public Observable<UploadBean> uploadFile(File file) {
        MultipartBody.Part body = prepareFilePart("file", file);
        RequestBody appKey = createPartFromString(Constants.UPLOAD_APP_KEY);
        RequestBody appSecret = createPartFromString(Constants.UPLOAD_APP_SECRET);
        return mApiService.uploadFile(body, appKey, appSecret)
                .compose(RxUtils.<UploadBean>io_main());
    }

    @NonNull
    private RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(
                MediaType.parse(Constants.MULTIPART_FORM_DATA), descriptionString);
    }

    @NonNull
    private MultipartBody.Part prepareFilePart(String partName, File file) {
        // https://github.com/iPaulPro/aFileChooser/blob/master/aFileChooser/src/com/ipaulpro/afilechooser/utils/FileUtils.java
        // use the FileUtils to get the actual file by uri
        // File file = FileUtils.getFile(this, fileUri);
        // create RequestBody instance from file
        RequestBody requestFile =
                RequestBody.create(MediaType.parse(Constants.MULTIPART_FORM_DATA), file);
        // MultipartBody.Part is used to send also the actual file name
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }


}
