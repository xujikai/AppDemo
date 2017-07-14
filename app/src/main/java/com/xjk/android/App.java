package com.xjk.android;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.facebook.stetho.Stetho;
import com.xjk.android.utils.L;

/**
 * Created by xxx on 2016/12/27.
 */

public class App extends Application{

    private static App app;
    private static Handler handler;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        handler = new Handler();

        Stetho.initializeWithDefaults(this);
        L.e("App ==> onCreate");
    }

    public static Context getContext(){
        return app;
    }

    public static Handler getHandler(){
        return handler;
    }

}
