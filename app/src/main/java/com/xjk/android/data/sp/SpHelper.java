package com.xjk.android.data.sp;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * SP缓存
 * Created by xxx on 2017/5/23.
 */

public class SpHelper {

    private static final String SP_FILE_NAME = "sharedPref";
    private static SharedPreferences sp;

    public static void putLong(Context context, String key, long value) {
        if (sp == null) {
            sp = context.getSharedPreferences(SP_FILE_NAME,
                    Context.MODE_PRIVATE);
        }
        sp.edit().putLong(key, value).apply();
    }

    public static long getLong(Context context, String key, long defValue) {
        if (sp == null) {
            sp = context.getSharedPreferences(SP_FILE_NAME,
                    Context.MODE_PRIVATE);
        }
        return sp.getLong(key, defValue);
    }

    public static void putBoolean(Context context, String key, boolean value) {
        if (sp == null) {
            sp = context.getSharedPreferences(SP_FILE_NAME,
                    Context.MODE_PRIVATE);
        }
        sp.edit().putBoolean(key, value).apply();
    }

    public static boolean getBoolean(Context context, String key,
                                     boolean defValue) {
        if (sp == null) {
            sp = context.getSharedPreferences(SP_FILE_NAME,
                    Context.MODE_PRIVATE);
        }
        return sp.getBoolean(key, defValue);
    }

    public static void putString(Context context, String key, String value) {
        if (sp == null) {
            sp = context.getSharedPreferences(SP_FILE_NAME,
                    Context.MODE_PRIVATE);
        }

        sp.edit().putString(key, value).apply();
    }

    public static String getString(Context context, String key, String defValue) {
        if (sp == null) {
            sp = context.getSharedPreferences(SP_FILE_NAME,
                    Context.MODE_PRIVATE);
        }
        return sp.getString(key, defValue);
    }

    public static void putInt(Context context, String key, int value) {
        if (sp == null) {
            sp = context.getSharedPreferences(SP_FILE_NAME,
                    Context.MODE_PRIVATE);
        }
        sp.edit().putInt(key, value).apply();
    }

    public static int getInt(Context context, String key, int defValue) {
        if (sp == null) {
            sp = context.getSharedPreferences(SP_FILE_NAME,
                    Context.MODE_PRIVATE);
        }
        return sp.getInt(key, defValue);
    }

    public static void clear(Context context) {
        if (sp == null) {
            sp = context.getSharedPreferences(SP_FILE_NAME,
                    Context.MODE_PRIVATE);
        }
        sp.edit().clear().apply();
    }

}
