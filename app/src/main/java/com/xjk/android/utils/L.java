package com.xjk.android.utils;

import android.util.Log;
import com.xjk.android.R;

public class L {
    private static String TAG = UIUtils.getString(R.string.app_name);
    private static boolean debug = true;

    public static void e(String msg) {
        if (debug) {
            Log.e(TAG, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (debug) {
            Log.e(tag, msg);
        }
    }

    public static void i(String msg) {
        if (debug) {
            Log.i(TAG, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (debug) {
            Log.i(tag, msg);
        }
    }

    public static void d(String msg) {
        if (debug) {
            Log.d(TAG, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (debug) {
            Log.d(tag, msg);
        }
    }

}

