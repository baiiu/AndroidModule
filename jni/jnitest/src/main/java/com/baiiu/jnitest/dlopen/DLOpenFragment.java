package com.baiiu.jnitest.dlopen;

import android.text.TextUtils;

import com.baiiu.jnitest.base.BaseFragment;
import com.baiiu.jnitest.testSocket.TestSocketFragment;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import dalvik.system.BaseDexClassLoader;

/**
 * author: baiiu
 * time: 2020/12/24
 * description:
 */
public class DLOpenFragment extends BaseFragment {

    static {
        System.loadLibrary("dlopen-lib");
    }

    @Override
    protected void initOnCreateView() {
        try {
            Method findLibrary = BaseDexClassLoader.class.getDeclaredMethod("findLibrary", String.class);
            findLibrary.setAccessible(true);

            BaseDexClassLoader dexPathClassLoader = (BaseDexClassLoader) TestSocketFragment.class.getClassLoader();
            String targetSoAbsolutePath = dexPathClassLoader.findLibrary("socket-lib");
            android.util.Log.e("mLogU", "ClassLoader#findLibrary: " + targetSoAbsolutePath);

        } catch (Exception e) {
            android.util.Log.e("mLogU", e.toString());
        }


        try {
            Field pathListF = BaseDexClassLoader.class.getDeclaredField("pathList");
            pathListF.setAccessible(true);
            ClassLoader dexPathClassLoader = TestSocketFragment.class.getClassLoader();
            Object pathList = pathListF.get(dexPathClassLoader);

            Field nativeLibraryPathElementsF = Class.forName("dalvik.system.DexPathList").getDeclaredField("nativeLibraryPathElements");
            nativeLibraryPathElementsF.setAccessible(true);
            Object nativeLibraryPathElements = nativeLibraryPathElementsF.get(pathList);
            android.util.Log.e("mLogU", nativeLibraryPathElements.toString());

            // find the nativeLibraryPathElements
            Object[] arr = (Object[]) nativeLibraryPathElements;
            for (Object o : arr) {
                android.util.Log.e("mLogU", o.toString());
            }


            Field pathF = Class.forName("dalvik.system.DexPathList$NativeLibraryElement").getDeclaredField("path");
            pathF.setAccessible(true);

            String targetSoAbsolutePath = null;
            for (Object o : arr) {
                File path = (File) pathF.get(o);
                targetSoAbsolutePath = findNativeLibrary(path, "libsocket-lib.so");
                if (!TextUtils.isEmpty(targetSoAbsolutePath)) {
                    break;
                }
            }

            testdlopen(targetSoAbsolutePath);

        } catch (Exception e) {
            android.util.Log.e("mLogU", e.toString());
        }

    }


    private native void testdlopen(String targetSoAbsolutePath);


    public String findNativeLibrary(File path, String name) {
        File file = new File(path, name);
        android.util.Log.e("mLogU", file.getAbsolutePath());

        if (file.exists()) {
            return file.getAbsolutePath();
        }

        return null;
    }


}
