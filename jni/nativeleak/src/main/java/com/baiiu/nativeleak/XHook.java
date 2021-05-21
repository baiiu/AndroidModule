package com.baiiu.nativeleak;

/**
 * author: baiiu
 * time: 2021/5/20
 * description:
 * <p>
 * native开发会遇到的问题：
 * 1. 内存管理不当，溢出、重复释放或错误释放，接入asan解决
 * 2. native内存泄露，使用一些开源库检测，如Raphael等；
 *
 * 参考 https://github.com/bytedance/memory-leak-detector 、 https://juejin.cn/post/6953430618726203399
 * native内存检测库设计注意点：
 * 1. hook so加载
 * 2. hook malloc、free等函数
 * 3. unwind获取堆栈
 * 4. 内存缓存设计，本地缓存设计、上报设计
 * <p>
 * <p>
 * native内存泄露的危害：
 * 1. 虚拟内存耗尽，再分配的话会返回null，使用时导致崩溃，或判断为空直接崩溃
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
