package com.baiiu.nativeleak;

/**
 * author: baiiu
 * time: 2021/5/20
 * description:
 */
public class XHook {
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

    static {
        System.loadLibrary("leak-monitor-lib");
    }

    public void init() {
        _init();
    }

    private native void _init();

}
