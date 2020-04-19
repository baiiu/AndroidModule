package com.baiiu.hookapp.pathClassLoaderHook3;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Instrumentation;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PersistableBundle;

import com.baiiu.hookapp.MainActivity;
import com.baiiu.hookapp.MyApplication;
import com.baiiu.hookapp.R;
import com.baiiu.library.LogUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import dalvik.system.DexClassLoader;

import static com.baiiu.hookapp.loadedApkHook.Util.NAME_CLASS;
import static com.baiiu.hookapp.loadedApkHook.Util.NAME_PACKAGE;
import static java.lang.ClassLoader.getSystemClassLoader;

/**
 * author: zhuzhe
 * time: 2020-01-19
 * description:
 */
public class PathClassLoaderHook3 {
    private static boolean isAMSHooked = false;
    private static File sApkFile;
    private static String sOptimizedPath;

    public static void hook() {
        /*
            1. hook classLoader，双亲委派代理
         */
        hookPathClassLoader2();

        /*
            2. hook amn，让ams走过权限校验
         */
        hookAMN();


        /*
            3. hook instrumentation，接收端，打开目标act
         */
        hookInstrumentation();
    }

    private static void hookPathClassLoader() {
        try {
            sApkFile = copyFromAssets("app-debug.apk");
            File optimize = new File(sApkFile.getParentFile(), "optimize");
            if (!optimize.exists()) {
                optimize.mkdirs();
            }
            sOptimizedPath = optimize.getAbsolutePath();

            Field mLoadedApkF = MyApplication.getContext().getClass().getSuperclass().getDeclaredField("mLoadedApk");
            mLoadedApkF.setAccessible(true);
            Object loadedApk = mLoadedApkF.get(MyApplication.getContext());

            Field mClassLoaderF = loadedApk.getClass().getDeclaredField("mClassLoader");
            mClassLoaderF.setAccessible(true);
            ClassLoader origin = (ClassLoader) mClassLoaderF.get(loadedApk);

            /*

                bootClassLoader

                PathClassLoader

                pluginClassLoader
             */
            PluginClassLoader pluginClassLoader = new PluginClassLoader(sApkFile.getAbsolutePath(), sOptimizedPath, null, origin);
            mClassLoaderF.set(loadedApk, pluginClassLoader);
        } catch (Exception e) {
            LogUtil.e("PathClassLoaderHook2#hookPathClassLoader: " + e.toString());
        }
    }

    // 只hook classLoader
    private static void hookPathClassLoader2() {
        try {
            sApkFile = copyFromAssets("app-debug.apk");
            File optimize = new File(sApkFile.getParentFile(), "optimize");
            if (!optimize.exists()) {
                optimize.mkdirs();
            }
            sOptimizedPath = optimize.getAbsolutePath();

            /*
                bootClassLoader

                pluginClassLoader

                PathClassLoader
             */

            ClassLoader pathClassLoader = MyApplication.class.getClassLoader(); // pathClassLoader

            ClassLoader bootClassLoader = pathClassLoader.getParent(); // bootClassLoader

            PluginClassLoader pluginClassLoader = new PluginClassLoader(sApkFile.getAbsolutePath(), sOptimizedPath, null, bootClassLoader);

            // 设置父类
            Field parentF = ClassLoader.class.getDeclaredField("parent");
            parentF.setAccessible(true);
            Field modifersField = Field.class.getDeclaredField("accessFlags");
            modifersField.setAccessible(true);
            modifersField.setInt(parentF, parentF.getModifiers() & ~Modifier.FINAL);

            parentF.set(pathClassLoader, pluginClassLoader);

        } catch (Exception e) {
            LogUtil.e("PathClassLoaderHook#hookPathClassLoader: " + e.toString());
        }
    }

    private static class PluginClassLoader extends DexClassLoader {
        private Map<String, String> map = new HashMap<>();

        PluginClassLoader(String dexPath, String optimizedDirectory, String librarySearchPath, ClassLoader parent) {
            super(dexPath, optimizedDirectory, librarySearchPath, parent);
            map.put("com.baiiu.hookapp.StubActivity", NAME_CLASS);
        }

        @Override
        public Class<?> loadClass(String name) throws ClassNotFoundException {
            LogUtil.e("PluginClassLoader#loadClass: " + name);

            if (map.containsKey(name)) {
                name = map.get(name);
            }

            return super.loadClass(name);
        }

        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {
            LogUtil.e("PluginClassLoader#findClass: " + name);
            return super.findClass(name);
        }
    }

    private static List<File> extractAPK(File apkFile) {
        List<File> list = new ArrayList<>();

        File parentFile = apkFile.getParentFile();

        // 解压文件目录
        File dexDir = new File(parentFile, "secondary-dexes");
        if (!dexDir.exists()) {
            dexDir.mkdir();
        }

        try {
            final ZipFile apk = new ZipFile(apkFile);

            ZipEntry dexFile = apk.getEntry("classes.dex");
            int secondaryNumber = 1;
            while (dexFile != null) {
                File tempFile = new File(dexDir, dexFile.getName());
                tempFile.createNewFile();

                list.add(tempFile);

                InputStream is = apk.getInputStream(dexFile);
                FileOutputStream fos = new FileOutputStream(tempFile);
                int len;
                byte[] buf = new byte[1024];
                while ((len = is.read(buf)) != -1) {
                    fos.write(buf, 0, len);
                }
                fos.close();
                is.close();


                ++secondaryNumber;
                dexFile = apk.getEntry("classes" + secondaryNumber + ".dex");
            }


        } catch (Exception e) {
            LogUtil.e("PathClassLoaderHook2#extractAPK: Exception:" + e.toString());
        }

        LogUtil.d("PathClassLoaderHook2#extractAPK: " + list);
        return list;

    }

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

                        LogUtil.d("PathClassLoaderHook2#hookAMS: " + args[index]);

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
            LogUtil.e("PathClassLoaderHook2#hookAMS: " + e.toString());
        }
    }

    private static void hookInstrumentation() {
        try {
            Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
            Method currentActivityThread = activityThreadClass.getDeclaredMethod("currentActivityThread");
            Object activityThread = currentActivityThread.invoke(null);

            Field mInstrumentationF = activityThreadClass.getDeclaredField("mInstrumentation");
            mInstrumentationF.setAccessible(true);
            Instrumentation origin = (Instrumentation) mInstrumentationF.get(activityThread);

            VInstrumentation vInstrumentation = new VInstrumentation(origin);

            mInstrumentationF.set(activityThread, vInstrumentation);

        } catch (Exception e) {
            LogUtil.e("PathClassLoaderHook2#hookInstrumentation: " + e.toString());
        }
    }

    private static class VInstrumentation extends Instrumentation {
        private final Instrumentation mBase;

        VInstrumentation(Instrumentation base) {
            this.mBase = base;
        }

        @Override
        public boolean onException(Object obj, Throwable e) {
            LogUtil.d("VInstrumentation#onException: " + obj + ", " + e.toString());
            return false;
        }

        @Override
        public Activity newActivity(ClassLoader cl, String className, Intent intent) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
//            if (!intent.getComponent().getClassName().equals("com.baiiu.hookapp.StubActivity")) {
//                return mBase.newActivity(cl, className, intent);
//            }
//
//            LogUtil.d("VInstrumentation#newActivity: " + className + ", " + intent);
//
//            intent.setClassName(NAME_PACKAGE, NAME_CLASS);
//
            return mBase.newActivity(cl, NAME_CLASS, intent);
        }

        @Override
        public void callActivityOnCreate(Activity activity, Bundle icicle) {
            injectActivity(activity);
            mBase.callActivityOnCreate(activity, icicle);
        }

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void callActivityOnCreate(Activity activity, Bundle icicle, PersistableBundle persistentState) {
            injectActivity(activity);
            mBase.callActivityOnCreate(activity, icicle, persistentState);
        }

        private void injectActivity(Activity activity) {
            try {
                android.util.Log.d("mLogU", "before hook===============");
                android.util.Log.d("mLogU", "decor_content_parent:" + Integer.toHexString(androidx.appcompat.R.id.decor_content_parent) + "，" + activity.getResources().getResourceEntryName(androidx.appcompat.R.id.decor_content_parent));
                android.util.Log.d("mLogU", "app_name:" + +R.string.app_name + "， " + activity.getString(R.string.app_name));
                android.util.Log.d("mLogU", "getText:" + activity.getResources().getText(0x7f0c0026));
                android.util.Log.d("mLogU", "===============before hook");
            } catch (Exception e) {
                android.util.Log.d("mLogU", "before hook error:: " + e.toString());
            }


            try {
                Resources resources = ResourceHook3.hook2(activity, sApkFile);


                Class<?> aClass = activity.getClass();
                while (aClass != null) {
                    try {
                        android.util.Log.d("mLogU", aClass.getName());
                        Field mResources = aClass.getDeclaredField("mResources");
                        mResources.setAccessible(true);
                        mResources.set(activity, resources);

                    } catch (Exception e) {
                        android.util.Log.d("mLogU", "22222222222: " + e.toString());
                    }

                    aClass = aClass.getSuperclass();
                }


                Field mBase = ContextWrapper.class.getDeclaredField("mBase");
                mBase.setAccessible(true);

                Context origin = activity.getBaseContext();
                Field contextImplResource = origin.getClass().getDeclaredField("mResources");
                contextImplResource.setAccessible(true);
                contextImplResource.set(origin, resources);


                Object mPackage = PathClassLoaderHook3.getPackageInfo(sApkFile);
                Field applicationInfoF = mPackage.getClass().getDeclaredField("applicationInfo");
                applicationInfoF.setAccessible(true);
                ApplicationInfo mApplicationInfo = (ApplicationInfo) applicationInfoF.get(mPackage);


                VContext vContext = new VContext(activity.getBaseContext(), resources, mApplicationInfo);
                mBase.set(activity, vContext);

                activity.setTheme(mApplicationInfo.theme);

                try {
                    android.util.Log.d("mLogU", "after hook===============");
                    android.util.Log.d("mLogU", "decor_content_parent:" + Integer.toHexString(androidx.appcompat.R.id.decor_content_parent) + "，" + resources.getResourceEntryName(androidx.appcompat.R.id.decor_content_parent));
                    android.util.Log.d("mLogU", "app_name:" + R.string.app_name + "， " + resources.getString(R.string.app_name));
                    android.util.Log.d("mLogU", "getText:" + resources.getText(0x7f0c0026));
                    android.util.Log.d("mLogU", "getText:" + activity.getText(0x7f0c0026));
                    android.util.Log.d("mLogU", "===============after hook");
                } catch (Exception e) {
                    android.util.Log.d("mLogU", "after hook error: " + e.toString());
                }

            } catch (Exception e) {
                LogUtil.e("VInstrumentation#injectActivity: " + e.toString());
            }
        }
    }

    private static class VContext extends ContextWrapper {
        private final Resources resources;
        private final ApplicationInfo mApplicationInfo;

        public VContext(Context base, Resources resources, ApplicationInfo applicationInfo) {
            super(base);
            this.resources = resources;
            this.mApplicationInfo = applicationInfo;
        }

        @Override
        public Context getBaseContext() {
            return super.getBaseContext();
        }

        @Override
        public ApplicationInfo getApplicationInfo() {
            return mApplicationInfo;
        }

        @Override
        public Resources getResources() {
            return resources;
        }

        @Override
        public AssetManager getAssets() {
            return resources.getAssets();
        }

        @Override
        public Resources.Theme getTheme() {
            Resources.Theme theme = this.resources.newTheme();
            theme.applyStyle(mApplicationInfo.theme, false);
            return theme;
        }
    }

    private static Object getPackageInfo(File apkFile) throws Exception {
        Class<?> packageParserClass = Class.forName("android.content.pm.PackageParser");
        Class<?> mPackageClass = Class.forName("android.content.pm.PackageParser$Package");
        Class<?> packageUserStateClass = Class.forName("android.content.pm.PackageUserState");

        // 构造Package对象
        Object packageParser = packageParserClass.newInstance();
        Method parsePackageMethod = packageParserClass.getDeclaredMethod("parsePackage", File.class, int.class);

        return parsePackageMethod.invoke(packageParser, apkFile, 1);
    }


    private static File copyFromAssets(String sourceName) {
        AssetManager am = MyApplication.getContext().getAssets();

        File outputFile = MyApplication.getContext().getFileStreamPath(sourceName);
        deleteFile(outputFile);

        try {
            InputStream is = am.open(sourceName);

            FileOutputStream fos = new FileOutputStream(outputFile);
            byte[] buffer = new byte[1024];
            int count = 0;
            while ((count = is.read(buffer)) > 0) {
                fos.write(buffer, 0, count);
            }
            fos.flush();

            is.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return outputFile;
    }

    private static void deleteFile(File file) {
        if (!file.exists()) {
            return;
        }
        if (file.isDirectory()) {
            File[] files = file.listFiles();

            for (File f : files) {
                if (f.isDirectory()) {
                    deleteFile(f);
                } else {
                    f.delete();
                }
            }

        } else {
            file.delete();
        }
    }


}
