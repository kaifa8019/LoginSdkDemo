package com.ecook.loginsdkdemo;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * @author parting_soul
 * @date 2019-09-04
 */
public class CommonUtils {

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 获取屏幕的宽
     */
    public static int getScreenWidth(Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return metrics.widthPixels;
    }

    /**
     * 获取屏幕的高
     */
    public static int getScreenHeight(Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return metrics.heightPixels;
    }

    public static int getScreenWidthIndp(Context context) {
        return px2dip(context, getScreenWidth(context));
    }

    public static int getScreenHeightIndp(Context context) {
        return px2dip(context, getScreenHeight(context));
    }

}
