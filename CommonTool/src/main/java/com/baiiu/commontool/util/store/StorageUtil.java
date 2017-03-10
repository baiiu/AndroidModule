/*******************************************************************************
 * Copyright 2011-2014 Sergey Tarasevich
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.baiiu.commontool.util.store;

import android.content.pm.PackageManager;
import android.os.Environment;
import com.baiiu.commontool.app.UIUtil;
import com.baiiu.library.LogUtil;
import java.io.File;
import java.io.IOException;

import static android.os.Environment.MEDIA_MOUNTED;

/**
 * 文件存储路径类
 * <p/>
 * 所有Files目录都可通过FrameWork提供的API获取,可以查看<a href="http://blog.csdn.net/u014099894/article/details/50947248">该文章</a>
 */
public final class StorageUtil {

    private static final String EXTERNAL_STORAGE_PERMISSION = "android.permission.WRITE_EXTERNAL_STORAGE";

    private StorageUtil() {
    }

    /**
     * 返回该应用的数据存储目录.当存在SD卡,并且有权限时,优先返回ExternalStorage. <br>
     * 外部缓存目录为:/Android/data/包名/cache <br>
     * 内部缓存目录为:/data/data/包名/cache。 <br>
     */
    public static File getCacheDirectory() {
        return getCacheDirectory(true);
    }

    /**
     * @param preferExternal true表示优先选择外部存储.false直接获取内部cache目录
     */
    @SuppressWarnings("TryWithIdenticalCatches")
    public static File getCacheDirectory(boolean preferExternal) {
        File appCacheDir = null;
        String externalStorageState;
        try {
            externalStorageState = Environment.getExternalStorageState();
        } catch (NullPointerException e) { // (sh)it happens (Issue #660)
            externalStorageState = "";
        } catch (IncompatibleClassChangeError e) { // (sh)it happens too (Issue #989)
            externalStorageState = "";
        }

        if (preferExternal && MEDIA_MOUNTED.equals(externalStorageState) && hasExternalStoragePermission()) {
            appCacheDir = getExternalCacheDir();
        }
        if (appCacheDir == null) {
            appCacheDir = UIUtil.getContext().getCacheDir();
        }
        if (appCacheDir == null) {//api8 以下需要自己创建缓存目录
            String cacheDirPath = "/data/data/" + UIUtil.getContext().getPackageName() + "/cache/";
            LogUtil.w(String.format("Can't define system cache directory! '%s' will be used.", cacheDirPath));
            appCacheDir = new File(cacheDirPath);
        }
        return appCacheDir;
    }

    /**
     * 优先创建外部Cache目录下指定文件<br>
     * 外部缓存目录为:/Android/data/包名/cache/fileName <br>
     * 内部缓存目录为:/data/data/包名/cache/fileName <br>
     *
     * @param fileName 文件名
     */
    public static File getUnderCacheDirectory(String fileName) {
        return getUnderCacheDirectory(fileName, true);
    }

    /**
     * 创建Cache目录下指定文件.
     *
     * @param fileName       文件名
     * @param preferExternal true表示优先选择外部cache目录.false直接获取内部cache目录
     */
    public static File getUnderCacheDirectory(String fileName, boolean preferExternal) {
        File appCacheDir = getCacheDirectory(preferExternal);
        File individualCacheDir = new File(appCacheDir, fileName);
        if (!individualCacheDir.exists()) {
            if (!individualCacheDir.mkdir()) {
                individualCacheDir = appCacheDir;
            }
        }
        return individualCacheDir;
    }

    /**
     * @return /storage/emulated/0/Android/data/包名/cache.
     */
    public static File getExternalCacheDir() {
        File externalCacheDir = UIUtil.getContext().getExternalCacheDir();
        if (externalCacheDir != null) {
            return externalCacheDir;
        }

        File dataDir = new File(new File(Environment.getExternalStorageDirectory(), "Android"), "data");
        File appCacheDir = new File(new File(dataDir, UIUtil.getContext().getPackageName()), "cache");
        if (!appCacheDir.exists()) {
            if (!appCacheDir.mkdirs()) {
                LogUtil.w("Unable to create external cache directory");
                return null;
            }
            try {
                new File(appCacheDir, ".nomedia").createNewFile();
            } catch (IOException e) {
                LogUtil.i("Can't create \".nomedia\" file in application external cache directory");
            }
        }
        return appCacheDir;
    }

    /**
     * 在SD卡上创建公有目录,如果创建失败,则返回InternalStorage的cache目录.
     *
     * @param fileName 文件目录名
     * @return /storage/emulated/0/fileName
     */
    public static File getPublicDirectory(String fileName) {
        File appCacheDir = null;
        if (MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) && hasExternalStoragePermission()) {
            appCacheDir = new File(Environment.getExternalStorageDirectory(), fileName);
        }
        if (appCacheDir != null && !appCacheDir.exists() && !appCacheDir.mkdirs()) {
            appCacheDir = UIUtil.getContext().getCacheDir();
        }
        return appCacheDir;
    }

    private static boolean hasExternalStoragePermission() {
        int perm = UIUtil.getContext().checkCallingOrSelfPermission(EXTERNAL_STORAGE_PERMISSION);
        return perm == PackageManager.PERMISSION_GRANTED;
    }
}
