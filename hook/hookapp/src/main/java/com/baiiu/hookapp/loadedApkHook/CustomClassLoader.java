package com.baiiu.hookapp.loadedApkHook;

import dalvik.system.DexClassLoader;

/**
 * author: zhuzhe
 * time: 2020-02-03
 * description:
 */
public class CustomClassLoader extends DexClassLoader {

    public CustomClassLoader(String dexPath, String optimizedDirectory, String libraryPath, ClassLoader parent) {
        super(dexPath, optimizedDirectory, libraryPath, parent);
    }

}
