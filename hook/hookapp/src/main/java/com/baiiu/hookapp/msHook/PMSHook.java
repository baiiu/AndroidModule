package com.baiiu.hookapp.msHook;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.IBinder;

import com.baiiu.library.LogUtil;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * author: zhuzhe
 * time: 2020-01-17
 * description:
 */
public class PMSHook {

    /**
     * {@link android.app.ActivityThread#sPackageManager}
     */
    public static void hook(Context context) {
        try {
            Class<?> activityThread = Class.forName("android.app.ActivityThread");
            Method currentActivityThreadM = activityThread.getDeclaredMethod("currentActivityThread");
            Object currentActivityThread = currentActivityThreadM.invoke(null);

            Field sPackageManager = activityThread.getDeclaredField("sPackageManager");
            sPackageManager.setAccessible(true);
            final Object rawPackageManager = sPackageManager.get(currentActivityThread);

            Object proxyPackgeManager = Proxy.newProxyInstance(IBinder.class.getClassLoader(), new Class[]{Class.forName("android.content.pm.IPackageManager")}, new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    LogUtil.e("PMSHook#hook: " + method + args);


                    return method.invoke(rawPackageManager, args);
                }
            });

            sPackageManager.set(currentActivityThread, proxyPackgeManager);

            PackageManager packageManager = context.getPackageManager();
            Field mPackageManager = packageManager.getClass().getDeclaredField("mPM");
            mPackageManager.setAccessible(true);
            mPackageManager.set(packageManager, proxyPackgeManager);

        } catch (Exception e) {
            LogUtil.e("PMSHook#hook: " + e.toString());
        }

    }


}
