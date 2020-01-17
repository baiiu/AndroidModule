package com.baiiu.hookapp.startStubActivity;

import android.app.ActivityManager;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import com.baiiu.library.LogUtil;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * author: zhuzhe
 * time: 2020-01-17
 * description:
 */
public class StubHook {

    public static void hook() {

        // 1. hook 发送端
        hookAMS();

        // 2. hook 接收端
        hookActivityThread();
    }

    /**
     * {@link com.baiiu.hookapp.msHook.AMSHook}
     */
    private static void hookAMS() {
        try {
            Field iActivityManagerSingletonF = ActivityManager.class.getDeclaredField("IActivityManagerSingleton");
            iActivityManagerSingletonF.setAccessible(true);
            Object singleton = iActivityManagerSingletonF.get(null);

            Field mInstance = Class.forName("android.util.Singleton").getDeclaredField("mInstance");
            mInstance.setAccessible(true);
            final Object rawAMS = mInstance.get(singleton);

            Object proxyAMS = Proxy.newProxyInstance(IBinder.class.getClassLoader(), new Class[]{Class.forName("android.app.IActivityManager")}, new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//                    LogUtil.e("StubHook#hook: " + method + ", " + args);

                    if ("startActivity".equals(method.getName())) {
                        int index = 0;
                        for (; index < args.length; index++) {
                            if (args[index] instanceof Intent) {
                                break;
                            }
                        }

                        Intent rawItent = (Intent) args[index];
                        if (rawItent.getComponent().getClassName().equals("com.baiiu.hookapp.startStubActivity.TargetActivity")) {
                            Intent intent = new Intent();
                            intent.setClassName("com.baiiu.hookapp", "com.baiiu.hookapp.StubActivity");
                            args[index] = intent;
                        }
                    }

                    return method.invoke(rawAMS, args);
                }
            });

            mInstance.set(singleton, proxyAMS);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * {@link android.app.ActivityThread#mH}
     * <p>
     * 拦截callBack
     */
    private static void hookActivityThread() {
        try {
            Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
            Method currentActivityThreadMethod = activityThreadClass.getDeclaredMethod("currentActivityThread");
            Object currentActivityThread = currentActivityThreadMethod.invoke(null);

            Field mH = activityThreadClass.getDeclaredField("mH");
            mH.setAccessible(true);
            Handler handler = (Handler) mH.get(currentActivityThread);

            Handler.Callback callback = new Handler.Callback() {
                @Override
                public boolean handleMessage(Message msg) {
                    switch (msg.what) {
                        case 159:
                            Object obj = msg.obj;
                            try {
                                Class<?> transaction = Class.forName("android.app.servertransaction.ClientTransaction");
                                Field mActivityCallbacksF = transaction.getDeclaredField("mActivityCallbacks");
                                mActivityCallbacksF.setAccessible(true);
                                List mActivityCallbacks = (List) mActivityCallbacksF.get(obj);
                                Object item = mActivityCallbacks.get(0);

                                Field mIntentF = Class.forName("android.app.servertransaction.LaunchActivityItem").getDeclaredField("mIntent");
                                mIntentF.setAccessible(true);
                                Intent intent = (Intent) mIntentF.get(item);
                                if (intent.getComponent().getClassName().equals("com.baiiu.hookapp.StubActivity")) {
                                    mIntentF.set(item, new Intent().setClassName("com.baiiu.hookapp.startStubActivity", TargetActivity.class.getName()));
                                }
                            } catch (Exception e) {
                                LogUtil.e("StubHook#hookActivityThread: " + e.toString());
                            }


                            break;
                    }

                    return false;
                }
            };
            Field mCallback = Handler.class.getDeclaredField("mCallback");
            mCallback.setAccessible(true);
            mCallback.set(handler, callback);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
