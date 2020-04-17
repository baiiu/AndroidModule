package com.baiiu.hookapp.loadedApkHook;

import android.content.res.AssetManager;

import com.baiiu.hookapp.MyApplication;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * author: zhuzhe
 * time: 2020-02-03
 * description:
 */
public class Util {

//    public static final String NAME_PACKAGE = "com.baiiu.zhihudaily";
    public static final String NAME_PACKAGE = "com.baiiu.testapk";
//    public static final String NAME_CLASS = "com.baiiu.zhihudaily.NewsListActivity";
    public static final String NAME_CLASS = "com.baiiu.testapk.TestActivity";

    static File copyFromAssets(String sourceName) {
        AssetManager am = MyApplication.getContext().getAssets();

        File outputFile = MyApplication.getContext().getFileStreamPath(sourceName);

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


    private static File sBaseDir;

    static File getPluginOptDexDir(String packageName) {
        return enforceDirExists(new File(getPluginBaseDir(packageName), "odex"));
    }

    static File getPluginLibDir(String packageName) {
        return enforceDirExists(new File(getPluginBaseDir(packageName), "lib"));
    }

    // 需要加载得插件得基本目录 /data/data/<package>/files/plugin/
    private static File getPluginBaseDir(String packageName) {
        if (sBaseDir == null) {
            sBaseDir = MyApplication.getContext().getFileStreamPath("plugin");
            enforceDirExists(sBaseDir);
        }
        return enforceDirExists(new File(sBaseDir, packageName));
    }

    private static synchronized File enforceDirExists(File sBaseDir) {
        if (!sBaseDir.exists()) {
            boolean ret = sBaseDir.mkdir();
            if (!ret) {
                throw new RuntimeException("create dir " + sBaseDir + "failed");
            }
        }
        return sBaseDir;
    }
}
