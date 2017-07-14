package com.xjk.android.base;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import com.xjk.android.utils.L;


/**
 * Created by xxx on 2016/12/9.
 */

public class InitializeService extends IntentService {

    private boolean isInit;
    private static final String ACTION_INIT_WHEN_APP_CREATE = "com.chiporadar.zhiboapp.service.action.init";

    public InitializeService() {
        super("InitializeService");
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, InitializeService.class);
        intent.setAction(ACTION_INIT_WHEN_APP_CREATE);
        //intent.setData(Uri.parse("xxx"));
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_INIT_WHEN_APP_CREATE.equals(action)) {
                performInit();
            }
        }
    }

    private void performInit() {
        if(isInit) return;
        isInit = true;
        L.e("performInit begin:" + System.currentTimeMillis());
        initRealm();
//        Stetho.initializeWithDefaults(this.getApplicationContext());
        L.e("performInit end:" + System.currentTimeMillis());
    }

    /**
     * 初始化Realm数据库
     */
    private void initRealm() {
//        Realm.init(this.getApplicationContext());
//        RealmConfiguration config = new RealmConfiguration.Builder().build();
////        执行此方法会把之前的数据清空
////        Realm.deleteRealm(config);
//        Realm.setDefaultConfiguration(config);
    }

}
