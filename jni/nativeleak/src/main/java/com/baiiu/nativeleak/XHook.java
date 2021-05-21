package com.baiiu.nativeleak;

/**
 * author: baiiu
 * time: 2021/5/20
 * description:
 */
public class XHook {
    static {
        System.loadLibrary("leak-monitor-lib");
    }

    private static volatile XHook xHook;

    private XHook() {
    }

    public static XHook get() {
        if (xHook == null) {
            synchronized (XHook.class) {
                if (xHook == null) {
                    xHook = new XHook();
                }
            }
        }
        return xHook;
    }

    /*
        hook系统so加载器，hook dlopen方法，获取准备加载的so name，再hook malloc等方法
     */
    public void init() {
        _init();
    }

    public void fixLeak() {
        _fixLeak();
    }


    private native void _init();

    private native void _fixLeak();

}
