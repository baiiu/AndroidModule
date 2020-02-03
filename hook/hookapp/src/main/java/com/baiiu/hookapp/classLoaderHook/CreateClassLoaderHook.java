package com.baiiu.hookapp.classLoaderHook;

import android.app.ActivityManager;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import com.baiiu.library.LogUtil;

import java.io.File;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.baiiu.hookapp.classLoaderHook.Util.NAME_CLASS;
import static com.baiiu.hookapp.classLoaderHook.Util.NAME_PACKAGE;

/**
 * author: zhuzhe
 * time: 2020-01-21
 * description:
 */
public class CreateClassLoaderHook {
    private static Map<String, Object> sLoadedApk = new HashMap<String, Object>();
    private static ApplicationInfo sApplicationInfo;

    public static void hook() {
        /*
            1. hook classLoader，让mInstrumentation.newActivity()的classLoader里找到目标act
         */
        hookLoadedApk();

        /*
            2. hook amn，让ams走过权限校验
         */
        hookAMN();


        /*
            3. hook mH，接收端，打开目标act
         */
        hookMH();
    }

    private static void hookLoadedApk() {
        try {
            // 先获取到当前的ActivityThread对象
            Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
            Method currentActivityThreadMethod = activityThreadClass.getDeclaredMethod("currentActivityThread");
            currentActivityThreadMethod.setAccessible(true);
            Object currentActivityThread = currentActivityThreadMethod.invoke(null);

            // 获取到 mPackages 这个静态成员变量, 这里缓存了dex包的信息
            Field mPackagesField = activityThreadClass.getDeclaredField("mPackages");
            mPackagesField.setAccessible(true);
            Map mPackages = (Map) mPackagesField.get(currentActivityThread);
            LogUtil.e("CreateClassLoaderHook#hookLoadedApk: before: " + mPackages);

            File apkFile = Util.copyFromAssets("app-release.apk");

            Class<?> compatibilityInfoClass = Class.forName("android.content.res.CompatibilityInfo");
            sApplicationInfo = getApplicationInfo(apkFile);
            Method getPackageInfoNoCheck = activityThreadClass.getDeclaredMethod("getPackageInfoNoCheck", ApplicationInfo.class, compatibilityInfoClass);
            Object loadedApk = getPackageInfoNoCheck.invoke(currentActivityThread, sApplicationInfo, compatibilityInfoClass.getDeclaredField("DEFAULT_COMPATIBILITY_INFO").get(null));


            String odexPath = Util.getPluginOptDexDir(sApplicationInfo.packageName).getPath();
            String libDir = Util.getPluginLibDir(sApplicationInfo.packageName).getPath();
            ClassLoader classLoader = new CustomClassLoader(apkFile.getPath(), odexPath, libDir, ClassLoader.getSystemClassLoader());
            Field mClassLoaderField = loadedApk.getClass().getDeclaredField("mClassLoader");
            mClassLoaderField.setAccessible(true);
            mClassLoaderField.set(loadedApk, classLoader);

            sLoadedApk.put(sApplicationInfo.packageName, loadedApk);
            mPackages.put(sApplicationInfo.packageName, new WeakReference(loadedApk));

            LogUtil.e("CreateClassLoaderHook#hookLoadedApk: after: " + mPackages);

        } catch (Exception e) {
            LogUtil.e("CreateClassLoaderHook#hookLoadedApk: " + e.toString());
        }
    }

    private static ApplicationInfo getApplicationInfo(File apkFile) throws Exception {

        Class<?> packageParserClass = Class.forName("android.content.pm.PackageParser");
        Class<?> mPackageClass = Class.forName("android.content.pm.PackageParser$Package");
        Class<?> packageUserStateClass = Class.forName("android.content.pm.PackageUserState");

        // 构造Package对象
        Object packageParser = packageParserClass.newInstance();
        Method parsePackageMethod = packageParserClass.getDeclaredMethod("parsePackage", File.class, int.class);

        Object mPackage = parsePackageMethod.invoke(packageParser, apkFile, 0);
        LogUtil.e("CreateClassLoaderHook#getApplicationInfo: " + mPackage);

        // 构造packageUserState对象
        Object packageUserState = packageUserStateClass.newInstance();

        Method generateApplicationInfo = packageParserClass.getDeclaredMethod("generateApplicationInfo", mPackageClass, int.class, packageUserStateClass);
        ApplicationInfo applicationInfo = (ApplicationInfo) generateApplicationInfo.invoke(null, mPackage, 0, packageUserState);
        String apkPath = apkFile.getPath();
        applicationInfo.sourceDir = apkPath;
        applicationInfo.publicSourceDir = apkPath;
        return applicationInfo;
    }

    private static boolean isAMSHooked = false;

    private static void hookAMN() {
        if (isAMSHooked) {
            return;
        }

        try {
            Field iActivityManagerSingleton = ActivityManager.class.getDeclaredField("IActivityManagerSingleton");
            iActivityManagerSingleton.setAccessible(true);
            Object singleton = iActivityManagerSingleton.get(null);

            Field mInstance = Class.forName("android.util.Singleton").getDeclaredField("mInstance");
            mInstance.setAccessible(true);
            final Object IActivityManager = mInstance.get(singleton);

            Object IActivityManagerProxy = Proxy.newProxyInstance(IBinder.class.getClassLoader(), new Class[]{Class.forName("android.app.IActivityManager")}, new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                    if ("startActivity".equals(method.getName())) {
                        int index = 0;
                        for (; index < args.length; index++) {
                            if (args[index] instanceof Intent) {
                                break;
                            }
                        }

                        LogUtil.e("CreateClassLoaderHook#hookAMS: " + args[index]);

                        Intent rawIntent = (Intent) args[index];
                        if (NAME_CLASS.equals(rawIntent.getComponent().getClassName())) {
                            rawIntent.setClassName("com.baiiu.hookapp", "com.baiiu.hookapp.StubActivity");
                        }
                    }

                    return method.invoke(IActivityManager, args);
                }
            });

            mInstance.set(singleton, IActivityManagerProxy);
            isAMSHooked = true;

        } catch (Exception e) {
            LogUtil.e("CreateClassLoaderHook#hookAMS: " + e.toString());
        }
    }

    private static void hookMH() {
        try {
            Class<?> activityThread = Class.forName("android.app.ActivityThread");
            Method currentActivityThreadM = activityThread.getDeclaredMethod("currentActivityThread");
            Object currentActivityThread = currentActivityThreadM.invoke(null);

            Field mHF = activityThread.getDeclaredField("mH");
            mHF.setAccessible(true);
            Object mH = mHF.get(currentActivityThread);

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

                                Class launchActivityItemClass = Class.forName("android.app.servertransaction.LaunchActivityItem");
                                if (item.getClass().isAssignableFrom(launchActivityItemClass)) {
                                    Field mIntentF = launchActivityItemClass.getDeclaredField("mIntent");
                                    mIntentF.setAccessible(true);
                                    Intent intent = (Intent) mIntentF.get(item);
                                    if (intent.getComponent().getClassName().equals("com.baiiu.hookapp.StubActivity")) {
                                        mIntentF.set(item, new Intent().setClassName(NAME_PACKAGE, NAME_CLASS));
                                    }

                                    Field mInfoF = launchActivityItemClass.getDeclaredField("mInfo");
                                    mInfoF.setAccessible(true);
                                    ActivityInfo mInfo = (ActivityInfo) mInfoF.get(item);
                                    mInfo.packageName = NAME_PACKAGE;
                                    mInfo.name = NAME_CLASS;
                                    mInfo.applicationInfo = sApplicationInfo;
                                }
                            } catch (Exception e) {
                                LogUtil.e("CreateClassLoaderHook#hookMH: " + e.toString());
                            }


                            break;
                    }

                    return false;
                }
            };
            Field callbackF = Handler.class.getDeclaredField("mCallback");
            callbackF.setAccessible(true);
            callbackF.set(mH, callback);

        } catch (Exception e) {
            LogUtil.e("CreateClassLoaderHook#hookMH: " + e.toString());
        }
    }


}
