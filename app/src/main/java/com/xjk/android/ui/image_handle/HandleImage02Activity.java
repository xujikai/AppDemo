package com.xjk.android.ui.image_handle;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.xjk.android.R;
import com.xjk.android.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by xxx on 2017/7/21.
 */

public class HandleImage02Activity extends BaseActivity{

    @BindView(R.id.imageView)
    BitmapShaderImageView imageView;

    public static void start(Context context){
        Intent intent = new Intent(context, HandleImage02Activity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_image_handle_02;
    }

    @Override
    protected void initInject() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getActionMasked();
                /* Raise view on ACTION_DOWN and lower it on ACTION_UP. */
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        Log.d(TAG, "ACTION_DOWN on view.");
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            view.setTranslationZ(120);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.d(TAG, "ACTION_UP on view.");
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            view.setTranslationZ(0);
                        }
                        break;
                    default:
                        return false;
                }
                return true;
            }
        });
//        Observable.timer(3000, TimeUnit.MILLISECONDS)
//                .compose(RxUtils.<Long>io_main())
//                .subscribe(new Consumer<Long>() {
//                    @Override
//                    public void accept(Long aLong) throws Exception {
//                        imageView.setImageResource(R.mipmap.test_girl_03);
//                    }
//                });
//        ImageUtils.setImageUrl(mContext, imageView, "http://api.maomaoda.tv/uploads/piliang/0_1499372122.jpeg", R.mipmap.test_avatar);
    }

}
