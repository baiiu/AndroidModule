package com.baiiu.hookapp.pathClassLoaderHook;

import android.app.ActivityManager;
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
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import dalvik.system.DexClassLoader;
import dalvik.system.DexFile;

import static com.baiiu.hookapp.loadedApkHook.Util.NAME_CLASS;
import static com.baiiu.hookapp.loadedApkHook.Util.NAME_PACKAGE;

/**
 * author: zhuzhe
 * time: 2020-01-19
 * description:
 */
public class PathClassLoaderHook {
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
            3. hook mH，接收端，打开目标act
         */
        hookMH();
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

            /*
                不hook的话，如果资源资源id刚好有，则不会崩溃；否则会崩溃
                android.content.res.Resources$NotFoundException: String resource ID #0x7f0c0028
             */
            ResourceHook.hook(MyApplication.getContext(), apkFile); // 资源hook

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
            LogUtil.e("PathClassLoaderHook#hookPathClassLoader: " + e.toString());
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
            LogUtil.e("PathClassLoaderHook#extractAPK: Exception:" + e.toString());
        }

        LogUtil.d("PathClassLoaderHook#extractAPK: " + list);
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

                        LogUtil.d("PathClassLoaderHook#hookAMS: " + args[index]);

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
            LogUtil.e("PathClassLoaderHook#hookAMS: " + e.toString());
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
                                if (mActivityCallbacks == null || mActivityCallbacks.size() == 0) {
                                    break;
                                }
                                Object item = mActivityCallbacks.get(0);
                                if (!Class.forName("android.app.servertransaction.LaunchActivityItem").isInstance(item)) {
                                    break;
                                }

                                Field mIntentF = Class.forName("android.app.servertransaction.LaunchActivityItem").getDeclaredField("mIntent");
                                mIntentF.setAccessible(true);
                                Intent intent = (Intent) mIntentF.get(item);

                                if (intent.getComponent().getClassName().equals("com.baiiu.hookapp.StubActivity")) {
                                    mIntentF.set(item, new Intent().setClassName(NAME_PACKAGE, NAME_CLASS));
                                }
                            } catch (Exception e) {
                                LogUtil.e("PathClassLoaderHook#hookMH: " + e.toString());
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
            LogUtil.e("PathClassLoaderHook#hookMH: " + e.toString());
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
