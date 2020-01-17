package com.baiiu.hookapp.binderHook;

import android.app.Application;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.IBinder;

import com.baiiu.hookapp.MyApplication;
import com.baiiu.library.LogUtil;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * author: zhuzhe
 * time: 2020-01-16
 * description:
 */
public class BinderHook2 {

    /**
     * {@link android.content.ClipboardManager}
     * {@link android.os.ServiceManager}
     * <p>
     * https://cs.android.com/android/platform/superproject/+/master:frameworks/base/core/java/android/content/ClipboardManager.java
     * https://cs.android.com/android/platform/superproject/+/master:frameworks/base/core/java/android/os/ServiceManager.java
     */
    public static void hook() {
        try {

            /*
                1. 获取到原始到clipboard
             */
            Class<?> serviceManager = Class.forName("android.os.ServiceManager");
            Method getService = serviceManager.getDeclaredMethod("getService", String.class);
            getService.setAccessible(true);
            final Object rawBinder = getService.invoke(null, Context.CLIPBOARD_SERVICE);


            Class<?> stub = Class.forName("android.content.IClipboard$Stub");
            Method asInterface = stub.getDeclaredMethod("asInterface", IBinder.class);
            final Object rawClipborad = asInterface.invoke(null, rawBinder);


            final Object clipboradProxy = Proxy.newProxyInstance(IBinder.class.getClassLoader(),
                    new Class[]{Class.forName("android.content.IClipboard")},
                    new InvocationHandler() {
                        @Override
                        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                            if ("hasPrimaryClip".equals(method.getName())) {
                                LogUtil.e("BinderHook#hookClipboard: hasPrimaryClip");
                                return true;
                            }

                            if ("getPrimaryClip".equals(method.getName())) {
                                LogUtil.e("BinderHook#hookClipboard: getPrimaryClip");
                                return ClipData.newPlainText(null, "you are hooked");
                            }

                            return method.invoke(rawClipborad, args);
                        }
                    });



            /*
                2. hook binder queryLocalInterface
             */
            Object binderPorxy = Proxy.newProxyInstance(IBinder.class.getClassLoader(), new Class[]{IBinder.class}, new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    if ("queryLocalInterface".equals(method.getName())) {
                        return clipboradProxy;
                    }

                    return method.invoke(rawBinder, args);
                }
            });

            /*
                3. 替换serviceManager里的成hook过的binder
             */
            Field sCache = serviceManager.getDeclaredField("sCache");
            sCache.setAccessible(true);
            Map map = (Map) sCache.get(null);
            map.put(Context.CLIPBOARD_SERVICE, binderPorxy);


        } catch (Exception e) {
            LogUtil.e("BinderHook#hook: " + e.toString());
        }
    }


}
