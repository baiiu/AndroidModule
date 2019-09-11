package com.baiiu.myapplication.util;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;

import com.baiiu.myapplication.CommonApplication;

/**
 * UI 工具包
 **/
public class UIUtil {

    /**
     * 获取ApplicationContext
     **/
    public static Context getContext() {
        return CommonApplication.getContext();
    }

    /**
     * 获取监听对象
     */
    //public static RefWatcher getRefWatcher() {
    //    CommonApplication application = (CommonApplication) getContext().getApplicationContext();
    //    return application.getRefWatcher();
    //}

    public static Handler mMainHandler;

    /**
     * 获取到主线程Handler对象,一系列handler对象用于线程切换
     **/
    public static Handler getMainThreadHandler() {
        if (mMainHandler == null) {
            mMainHandler = new Handler(Looper.getMainLooper());
        }
        return mMainHandler;
    }

    /**
     * 在主线程执行runnable
     */
    public static boolean post(Runnable runnable) {
        return getMainThreadHandler().post(runnable);
    }

    /**
     * 延时在主线程执行runnable
     */
    public static boolean postDelayed(Runnable runnable, long delayMillis) {
        return getMainThreadHandler().postDelayed(runnable, delayMillis);
    }

    /**
     * 从主线程looper里面移除runnable
     */
    public static void removeCallbacks(Runnable runnable) {
        getMainThreadHandler().removeCallbacks(runnable);
    }

    public static boolean isInMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    /**
     * 在主线程执行
     **/
    public static void runInMainThread(Runnable runnable) {
        if (isInMainThread()) {
            runnable.run();
        } else {
            post(runnable);
        }
    }

    /**
     * 获取包名
     */
    public static String getPackageName() {
        return getContext().getPackageName();
    }

    /**
     * 根据图片名获取图片id
     */
    public static int getDrawableId(Context context, String name) {
        return context
                .getResources()
                .getIdentifier(name, "mipmap", context.getPackageName());
    }

    /**
     * 传入dp，即输出dp值
     */
    public static int dp(int dp) {
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getContext()
                .getResources()
                .getDisplayMetrics()) + 0.5F);
    }

    /**
     * 获取布局
     **/
    public static View inflate(Context context, @LayoutRes int layoutId) {
        return inflate(context, layoutId, null);
    }

    public static View inflate(Context context, @LayoutRes int layoutId, ViewGroup parent) {
        return inflate(context, layoutId, parent, false);
    }

    public static View inflate(Context context, @LayoutRes int layoutId, ViewGroup parent, boolean attatch) {
        return getInflater(context).inflate(layoutId, parent, attatch);
    }

    /**
     * 获取布局天重启LayoutInflater.建议获取该context相关填充器.
     */
    public static LayoutInflater getInflater(Context context) {
        return LayoutInflater.from(context);
    }

    /**
     * 获取颜色
     */
    public static int getColor(int resId) {
        return getContext()
                .getResources()
                .getColor(resId);
    }
}
