package com.xjk.android.utils;

/**
 * Created by xxx on 2016/7/29.
 */
public class StringUtils {

    public static CharSequence formatNull(CharSequence str){
        return isEmpty(str) ? "" : str;
    }

    public static CharSequence formatInt(int num){
        return String.valueOf(num);
    }

    public static boolean isEmpty(CharSequence str){
        return str == null || str.toString().trim().length() == 0;
    }

    public static boolean isNotEmpty(CharSequence str){
        return str != null && str.length() > 0;
    }
    public static boolean isMoreThan(CharSequence str){
        return str != null && str.length() > 5;
    }
    public static boolean isMore(CharSequence str){
        return str.toString().trim().length() == 5;
    }

}
