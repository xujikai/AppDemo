package com.xjk.android.ui.image_handle;

import android.content.Context;
import android.content.Intent;

import com.xjk.android.R;
import com.xjk.android.base.BaseActivity;
import com.xjk.android.utils.ImageUtils;

import butterknife.BindView;

/**
 * Created by xxx on 2017/7/21.
 */

public class HandleImage01Activity extends BaseActivity{

    @BindView(R.id.imageView)
    XfermodeImageView imageView;

    public static void start(Context context){
        Intent intent = new Intent(context, HandleImage01Activity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_image_handle_01;
    }

    @Override
    protected void initInject() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
//        Observable.timer(3000, TimeUnit.MILLISECONDS)
//                .compose(RxUtils.<Long>io_main())
//                .subscribe(new Consumer<Long>() {
//                    @Override
//                    public void accept(Long aLong) throws Exception {
//                        imageView.setImageResource(R.mipmap.test_girl_03);
//                    }
//                });
        ImageUtils.setImageUrl(mContext, imageView, "http://api.maomaoda.tv/uploads/piliang/0_1499372122.jpeg", R.mipmap.test_avatar);
    }

}
