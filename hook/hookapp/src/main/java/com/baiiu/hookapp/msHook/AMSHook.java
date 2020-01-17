package com.baiiu.hookapp.msHook;

import android.app.ActivityManager;
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
 * <p>
 */
public class AMSHook {

    /**
     * {@link android.app.ActivityManager#IActivityManagerSingleton}
     * {@link android.util.Singleton}
     * <p>
     * https://cs.android.com/android/platform/superproject/+/master:frameworks/base/core/java/android/app/ActivityManager.java
     * https://cs.android.com/android/platform/superproject/+/master:frameworks/base/core/java/android/util/Singleton.java
     */
    public static void hook() {
        try {
            Field singleton = ActivityManager.class.getDeclaredField("IActivityManagerSingleton");
            singleton.setAccessible(true);
            Object singleObj = singleton.get(null);

            Class<?> singtonClass = Class.forName("android.util.Singleton");
            Field sInstance = singtonClass.getDeclaredField("mInstance");
            sInstance.setAccessible(true);

            final Object rawAMS = sInstance.get(singleObj);

            Object proxyAMS = Proxy.newProxyInstance(IBinder.class.getClassLoader(), new Class[]{Class.forName("android.app.IActivityManager")}, new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    LogUtil.e("AMSHook#hook: " + method + ", " + args);

                    return method.invoke(rawAMS, args);
                }
            });
            sInstance.set(singleObj, proxyAMS);


        } catch (Exception e) {
            LogUtil.e("AMSHook#hook: " + e.toString());
        }
    }

}
