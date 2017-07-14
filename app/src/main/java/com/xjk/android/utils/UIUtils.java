package com.xjk.android.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;

import com.xjk.android.App;

public class UIUtils {

    //提供获取上下环境方法
    public static Context getContext() {
        return App.getContext();
    }

    //Handler
    public static Handler getHandler() {
        return App.getHandler();
    }

    public static void showToastShort(int resId) {
        ToastUtils.showShortToastSafe(getContext(),resId);
//        Toast.makeText(getContext(), UIUtils.getString(resId), Toast.LENGTH_SHORT).show();
    }

    public static void showToastShort(String msg) {
        ToastUtils.showShortToastSafe(getContext(),msg);
//        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public static void showToastLong(int resId) {
        ToastUtils.showLongToastSafe(getContext(),resId);
//        Toast.makeText(getContext(), UIUtils.getString(resId), Toast.LENGTH_LONG).show();
    }

    public static void showToastLong(String msg) {
        ToastUtils.showLongToastSafe(getContext(),msg);
//        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
    }

    //xml--->view
    public static View inflate(int layoutId) {
        return LayoutInflater.from(getContext()).inflate(layoutId, null);
    }

    //获取资源文件夹
    public static Resources getResources() {
        return getContext().getResources();
    }

    //获取string操作
    public static String getString(int stringId) {
        return getResources().getString(stringId);
    }

    //获取drawable
    public static Drawable getDrawable(int drawableId) {
        return ContextCompat.getDrawable(getContext(),drawableId);
    }

    //获取color
    public static int getColor(int colorId) {
        return ContextCompat.getColor(getContext(),colorId);
    }

    //获取Dimension
    public static float getDimension(int dimensionId){
        return getResources().getDimension(dimensionId);
    }

    public static int getInteger(int integerId){
        return getResources().getInteger(integerId);
    }

    //获取stringArray数组
    public static String[] getStringArray(int stringArrayId) {
        return getResources().getStringArray(stringArrayId);
    }

    //获取屏幕的宽
    public static int getScreenWidth(){
        return getResources().getDisplayMetrics().widthPixels;
    }

    //获取屏幕的高
    public static int getScreenHeight(){
        return getResources().getDisplayMetrics().heightPixels;
    }

    //获取导航栏的高度
    public static int getNavigationBarHeight() {
        int resourceId;
        int rid = getResources().getIdentifier("config_showNavigationBar", "bool", "android");
        if (rid != 0){
            resourceId = getResources().getIdentifier("navigation_bar_height", "dimen", "android");
            return getResources().getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    //手机的像素密度跟文档中的最接近值
    //dip--->px
    public static int dip2px(int dip) {
        //获取dip和px的比例关系
        float d = getResources().getDisplayMetrics().density;
        // (int)(80.4+0.5)   (int)(80.6+0.5)
        return (int) (dip * d + 0.5);
    }

    //px---->dip
    public static int px2dip(int px) {
        float d = getResources().getDisplayMetrics().density;
        return (int) (px / d + 0.5);
    }

}
