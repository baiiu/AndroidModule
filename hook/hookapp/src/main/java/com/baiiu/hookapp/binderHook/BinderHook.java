package com.baiiu.hookapp.binderHook;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Context;
import android.os.IBinder;
import android.os.IInterface;
import com.baiiu.library.LogUtil;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * author: zhuzhe
 * time: 2020-01-16
 * description:
 */
public class BinderHook {

    private static void hookGetSystemService() {

        /*
            hook  serviceFetcher
         */
        try {
            Class<?> systemClass = Class.forName("android.app.SystemServiceRegistry");
            Field system_service_fetchers = systemClass.getDeclaredField("SYSTEM_SERVICE_FETCHERS");
            system_service_fetchers.setAccessible(true);
            HashMap map = (HashMap) system_service_fetchers.get(null);

            final Object rawClipboardFetcher = map.get(Context.CLIPBOARD_SERVICE);


            Object serviceFetcher =
                    Proxy.newProxyInstance(systemClass.getClassLoader(), new Class[] {
                            Class.forName("android.app.SystemServiceRegistry$ServiceFetcher")
                    }, new InvocationHandler() {
                        @Override
                        public Object invoke(Object proxy, Method method,
                                Object[] args) throws Throwable {

                            //return hookServiceManager();

                            return method.invoke(rawClipboardFetcher, args);
                            //return null;
                        }
                    });


            map.put(Context.CLIPBOARD_SERVICE, serviceFetcher);
        } catch (Exception e) {
            LogUtil.e("BinderHook#hookGetSystemService: " + e.toString());
        }
    }

    /**
     * ClipboardManager
     *
     * IClipboard.Stub.asInterface(ServiceManager.getServiceOrThrow(Context.CLIPBOARD_SERVICE));
     */
    public static void hook() {
        try {
            Class<?> serviceManager = Class.forName("android.os.ServiceManager");
            Method getService = serviceManager.getDeclaredMethod("getService", String.class);
            getService.setAccessible(true);
            final IBinder rawBinder = (IBinder) getService.invoke(null, Context.CLIPBOARD_SERVICE);

            Object hookedBinder =
                    Proxy.newProxyInstance(serviceManager.getClassLoader(),
                                           new Class[] { IBinder.class },
                                           new InvocationHandler() {
                                               @Override
                                               public Object invoke(Object proxy, Method method,
                                                       Object[] args) throws Throwable {

                                                   LogUtil.e("BinderHook#hook: invoke: " + method);

                                                   if ("queryLocalInterface".equals(
                                                           method.getName())) {

                                                       // 返回一个伪造的inn
                                                       return hookClipboard(rawBinder);
                                                   }

                                                   return method.invoke(rawBinder, args);
                                               }
                                           });

            Field cacheField = serviceManager.getDeclaredField("sCache");
            cacheField.setAccessible(true);
            Map cache = (Map) cacheField.get(null);
            cache.put(Context.CLIPBOARD_SERVICE, hookedBinder);
        } catch (Exception e) {
            LogUtil.e("BinderHook#hook: " + e.toString());
        }

    }

    private static Object hookClipboard(final Object rawBinder) throws Exception {
        final Class<?> stub = Class.forName("android.content.IClipboard$Stub");
        final Class<?> iinterface = Class.forName("android.content.IClipboard");

        Method asInterface = stub.getDeclaredMethod("asInterface", IBinder.class);
        asInterface.setAccessible(true);
        final Object iClipboard = asInterface.invoke(null, rawBinder);

        return Proxy.newProxyInstance(stub.getClassLoader(),
                                      new Class[] {
                                              IBinder.class,
                                              IInterface.class, iinterface
                                      }, new InvocationHandler() {
                    @Override public Object invoke(Object proxy, Method method,
                            Object[] args) throws Throwable {

                        if ("hasPrimaryClip".equals(method.getName())) {
                            LogUtil.e("BinderHook#hookClipboard: hasPrimaryClip");
                            return true;
                        }

                        if ("getPrimaryClip".equals(method.getName())) {
                            LogUtil.e("BinderHook#hookClipboard: getPrimaryClip");
                            return ClipData.newPlainText(null, "you are hooked");
                        }


                        return method.invoke(iClipboard, args);
                    }
                });
    }

}
