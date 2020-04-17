package com.baiiu.hookapp.pathClassLoaderHook2;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Instrumentation;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import com.baiiu.hookapp.MainActivity;
import com.baiiu.hookapp.MyApplication;
import com.baiiu.library.LogUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import dalvik.system.DexClassLoader;

import static com.baiiu.hookapp.loadedApkHook.Util.NAME_CLASS;
import static com.baiiu.hookapp.loadedApkHook.Util.NAME_PACKAGE;

/**
 * author: zhuzhe
 * time: 2020-01-19
 * description:
 */
public class PathClassLoaderHook2 {
    private static boolean isAMSHooked = false;

    /**
     * hook PathClassLoader打开别的dex下的activity
     * 资源合并用ResourceHook，但资源id会冲突
     */
    public static void hook() {
        /*
            1. hook classLoader，让mInstrumentation.newActivity()的classLoader里找到目标act
         */
        hookPathClassLoader();

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
            Class<?> dexPathList = Class.forName("dalvik.system.DexPathList");
            Field dexElementsF = dexPathList.getDeclaredField("dexElements");
            dexElementsF.setAccessible(true);


            Field pathListF = DexClassLoader.class.getSuperclass().getDeclaredField("pathList");
            pathListF.setAccessible(true);
            Object pathList = pathListF.get(MainActivity.class.getClassLoader());

            Object[] dexElements = (Object[]) dexElementsF.get(pathList);
            LogUtil.d("dexElements: " + dexElements);

            // 构造elements
            File apkFile = copyFromAssets("app-debug.apk");
            List<File> list = extractAPK(apkFile);
//            ResourceHook2.hook(MyApplication.getContext(), apkFile); // 资源hook

            Method makeDexElements = Class.forName("dalvik.system.DexPathList").getDeclaredMethod("makeDexElements", List.class, File.class, List.class, ClassLoader.class);
            makeDexElements.setAccessible(true);
            Object[] toAddElementArray = (Object[]) makeDexElements.invoke(null, list, list.get(0).getParentFile(), new ArrayList<>(), dexPathList.getClassLoader());

//            Class<?> elementClass = dexElements.getClass().getComponentType();
//            Constructor<?> constructor = elementClass.getConstructor(DexFile.class, File.class);
//            Object element = constructor.newInstance(DexFile.loadDex(apkFile.getCanonicalPath(), dexFile.getAbsolutePath(), 0), apkFile);
//            LogUtil.e("element: " + element);

//            // 添加
            Class<?> elementClass = dexElements.getClass().getComponentType();
            Object[] newElements = (Object[]) Array.newInstance(elementClass, dexElements.length + toAddElementArray.length);
            System.arraycopy(dexElements, 0, newElements, 0, dexElements.length);
            System.arraycopy(toAddElementArray, 0, newElements, dexElements.length, toAddElementArray.length);

            // 替换
            dexElementsF.set(pathList, newElements);

        } catch (Exception e) {
            LogUtil.e("PathClassLoaderHook2#hookPathClassLoader: " + e.toString());
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
        public Activity newActivity(ClassLoader cl, String className, Intent intent) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
            if (!intent.getComponent().getClassName().equals("com.baiiu.hookapp.StubActivity")) {
                return mBase.newActivity(cl, className, intent);
            }

            LogUtil.d("PathClassLoaderHook2#VInstrumentation: " + className + ", " + intent);

            intent.setClassName(NAME_PACKAGE, NAME_CLASS);
            return mBase.newActivity(cl, NAME_CLASS, intent);
        }

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
