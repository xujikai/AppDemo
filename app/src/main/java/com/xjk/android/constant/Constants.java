package com.xjk.android.constant;

import android.os.Environment;

import com.xjk.android.R;
import com.xjk.android.utils.UIUtils;

import java.io.File;

import okhttp3.MediaType;

import static android.os.Environment.DIRECTORY_DCIM;

/**
 * 项目常量值
 * Created by xxx on 2016/10/9.
 */

public class Constants {

    /**
     * 上传视频所需参数
     */
    public static final String UPLOAD_APP_KEY = "xnXcNtotNDIyMTA5NDI1MTIwNzEwNjU2";
    public static final String UPLOAD_APP_SECRET = "YzkwNDc2ODFjMTE5MmQ5ODcyNWY2NGNiYWQyMzdjYWQ=";

    /**
     * 上传视频参数类型
     */
    public static final String MULTIPART_FORM_DATA = "multipart/form-data";
    public static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown; charset=utf-8");
    public static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");

//======================================================================================

    /**
     * 阿里支付成功状态码
     */
    public static final String PAY_ALI_SUCCESS = "9000";

    /**
     * 微信支付成功状态码
     */
    public static final String WX_APP_ID = "wxd930ea5d5a258f4f";

    /**
     * 网络请求成功
     */
    public static final int STATUS_SUCCESS = 0;
    /**
     * 极光推送别名
     */
    public static final String JPUSH_ALIAS = "jpush_alias";

    /**
     * 注册时向服务器传递的Session值
     */
    public static String COM_SESSION = "";
    public static String COM_CHANGE_SESSION = "";
    /**
     * key     通用软键盘键盘高度
     */
    public static String COM_SOFTKEY_HEIGHT = "soft_key_height";
    /**
     * 用户信息
     */
    public static final String COM_USER_INFO = "user_info";
    /**
     * key     通用位置索引
     */
    public static String COM_POSITION = "position";
    /**
     * key     通用联系人集合数据
     */
    public static String COM_CONTACTS = "contacts";
    /**
     * key     通用图片数据
     */
    public static String COM_PHOTO = "photo";
    /**
     * key     通用图片集合数据
     */
    public static String COM_PHOTOS = "photos";
    /**
     * key     通用视频数据
     */
    public static String COM_VIDEO = "video";
    /**
     * key     通用详细地址
     */
    public static String COM_ADDRESS = "address";

//======================================================================================

    /**
     * 点击按钮默认间隔时长
     */
    public static long DEFAULT_CLICK_INTERVAL = 1000;
    /**
     * 默认列表索引
     */
    public static int DEFAULT_POSITION = 0;
    /**
     * 默认空值
     */
    public static String DEFAULT_NULL = "";
    /**
     * 默认软键盘高度
     */
    public static int DEFAULT_SOFTKEY_HEIGHT = (int) (267 * UIUtils.getResources().getDisplayMetrics().density);
    /**
     * 默认经度
     */
    public static double DEFAULT_LONGITUDE = 116.521321;
    /**
     * 默认纬度
     */
    public static double DEFAULT_LATITUDE = 39.911809;
    /**
     * 默认请求数据条目
     */
    public static int DEFAULT_PAGE_SIZE = 10;
    /**
     * 图片最大张数
     */
    public static int MAX_PHOTO_SIZE = 9;


//======================================================================================

    /**
     * 拍照
     */
    public static final int REQUEST_CODE_PICK = 1;//选取单张图片
    public static final int REQUEST_CODE_PICK_MULTI = 2;//选取多张图片
    public static final int REQUEST_CODE_TAKE = 3;//拍照
    public static final int REQUEST_CODE_CROP = 4;//裁剪图片
    public static final int REQUEST_CODE_RECORD_VIDEO = 5;//录制视频

    /**
     * 默认保存数据路径
     */
    private static final String DEFAULT_SAVE_PATH = Environment.getExternalStorageDirectory()
            + File.separator + UIUtils.getString(R.string.app_name);
    public static final String DEFAULT_IMAGE_PATH = DEFAULT_SAVE_PATH + "/image";
    public static final String DEFAULT_THUMB_PATH = DEFAULT_SAVE_PATH + "/thumb";
    public static final String DEFAULT_VIDEO_PATH = DEFAULT_SAVE_PATH + "/video";
    public static final String DEFAULT_TEMP_PATH = DEFAULT_SAVE_PATH + "/temp";
    public static final String DEFAULT_OBJECT_PATH = DEFAULT_SAVE_PATH + "/object";

    /**
     * 默认相册路径
     */
    public static final String DEFAULT_CAMERA_PATH =
            Environment.getExternalStoragePublicDirectory(DIRECTORY_DCIM).getAbsolutePath()
                    + File.separator + UIUtils.getString(R.string.app_name);

//======================================================================================

    /**
     * RxBus事件类型
     */
    public final class EventType {
        public final static String TAG_FAMILY_MEMBER_ACTIVITY = "family_member_activity";
        public final static String TAG_UNREAD_MSG_ACTIVITY = "unread_msg_activity";
        public final static String TAG_JPUSH_UNREAD_MSG_ACTIVITY = "jpush_unread_msg_activity";
    }

}
