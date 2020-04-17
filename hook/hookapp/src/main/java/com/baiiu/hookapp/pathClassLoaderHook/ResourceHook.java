package com.baiiu.hookapp.pathClassLoaderHook;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.res.AssetManager;
import android.os.Build;

import com.baiiu.library.LogUtil;

import java.io.File;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * author: zhuzhe
 * time: 2020-04-16
 * description:
 */
class ResourceHook {

    /*
        合并资源方案：
        将插件资源添加到宿主的Resource中，资源id会冲突，需解决
     */
    static void hook(Context context, File apkFile) {
        String newAssetPath = apkFile.getAbsolutePath();
        ApplicationInfo info = context.getApplicationInfo();

        // 1. 将newAssetPath插入info.splitSourceDirs
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            info.splitSourceDirs = append(info.splitSourceDirs, newAssetPath);
        }

        try {
            // 2.将newAssetPath插入所有LoadedApk的splitSourceDirs
            Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
            Method currentActivityThread = activityThreadClass.getDeclaredMethod("currentActivityThread");
            Object activityThread = currentActivityThread.invoke(null);

            Field mPackages = activityThreadClass.getDeclaredField("mPackages");
            mPackages.setAccessible(true);

            Field mResourcePackages = activityThreadClass.getDeclaredField("mResourcePackages");
            mResourcePackages.setAccessible(true);

            Field mSplitResDirs = Class.forName("android.app.LoadedApk").getDeclaredField("mSplitResDirs");
            mSplitResDirs.setAccessible(true);

            for (Field field : new Field[]{mPackages, mResourcePackages}) {
                Object value = field.get(activityThread);

                for (Map.Entry<String, WeakReference<?>> entry : ((Map<String, WeakReference<?>>) value).entrySet()) {
                    Object loadedApk = entry.getValue().get();

                    mSplitResDirs.set(loadedApk, append((String[]) mSplitResDirs.get(loadedApk), newAssetPath));
                }
            }


            // 构造插件的assetManager
            Method addAssetPath = AssetManager.class.getDeclaredMethod("addAssetPath", String.class);
            addAssetPath.setAccessible(true);
            AssetManager newAssetManager = AssetManager.class.getConstructor().newInstance();
            addAssetPath.invoke(newAssetManager, newAssetPath);


            // 4. 添加资源asset
            Class<?> resourcesManagerClass = Class.forName("android.app.ResourcesManager");
            Object resourcesManager = resourcesManagerClass.getDeclaredMethod("getInstance").invoke(null);

            // tinker
//            Field mResourceReferences = resourcesManagerClass.getDeclaredField("mResourceReferences");
//            mResourceReferences.setAccessible(true);
//            List<WeakReference<Resources>> list = (List<WeakReference<Resources>>) mResourceReferences.get(resourcesManager);
//            Field mResourcesImplClass = Resources.class.getDeclaredField("mResourcesImpl");
//            mResourcesImplClass.setAccessible(true);
//
//            for (WeakReference<Resources> reference : list) {
//                Resources resources = reference.get();
//
//                Object resourcesImpl = mResourcesImplClass.get(resources);
//
//                Field implAssets = resourcesImpl.getClass().getDeclaredField("mAssets");
//                implAssets.setAccessible(true);
//
//                AssetManager assetManager = (AssetManager) implAssets.get(resourcesImpl);
//                addAssetPath.invoke(assetManager, newAssetPath);
//
////                Field modifersField = Field.class.getDeclaredField("accessFlags");
////                modifersField.setAccessible(true);
////                modifersField.setInt(implAssets, implAssets.getModifiers() & ~Modifier.FINAL);
////                implAssets.set(resourcesImpl, newAssetManager);
//
//                resources.updateConfiguration(resources.getConfiguration(), resources.getDisplayMetrics());
//            }

            Field mResourceImpls = resourcesManagerClass.getDeclaredField("mResourceImpls");
            mResourceImpls.setAccessible(true);
            Map<?, WeakReference<?>> map = (Map) mResourceImpls.get(resourcesManager);
            for (Map.Entry<?, WeakReference<?>> entry : map.entrySet()) {
                Object resourcesImpl = entry.getValue().get();

                Field implAssets = resourcesImpl.getClass().getDeclaredField("mAssets");
                implAssets.setAccessible(true);

                AssetManager assetManager = (AssetManager) implAssets.get(resourcesImpl);
                addAssetPath.invoke(assetManager, newAssetPath);
//                Field modifersField = Field.class.getDeclaredField("accessFlags");
//                modifersField.setAccessible(true);
//                modifersField.setInt(implAssets, implAssets.getModifiers() & ~Modifier.FINAL);
//                implAssets.set(resourcesImpl, newAssetManager);
            }

            Method appendPath = resourcesManagerClass.getDeclaredMethod("appendLibAssetForMainAssetPath", String.class, String.class);
            appendPath.setAccessible(true);
            appendPath.invoke(resourcesManager, info.publicSourceDir, "com.baiiu.zhihudaily.vastub");


        } catch (Exception e) {
            LogUtil.e("ResourceHook#hook: " + e.toString());
        }
    }

    private static String[] append(String[] paths, String newPath) {
        if (paths == null) {
            return null;
        }
        String[] newPaths = new String[paths.length + 1];
        System.arraycopy(paths, 0, newPaths, 0, paths.length);
        newPaths[newPaths.length - 1] = newPath;

        return newPaths;
    }


}
