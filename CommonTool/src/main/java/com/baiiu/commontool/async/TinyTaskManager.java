package com.baiiu.commontool.async;

import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;

/**
 * author: baiiu
 * date: on 16/3/18 17:54
 * description:<br>
 * 使用HandlerThread作为后台线程,异步处理任务.
 * 只允许一个任务执行,不能并发执行任务.后续任务排队执行.
 * 可以调用{@link #postAtFrontOfQueue}优先执行该任务.
 * <p>
 * <br>
 * 适用于轻量的后台任务处理.
 */
public class TinyTaskManager {
    private volatile static TinyTaskManager mInstance;
    private HandlerThread mWorkThread;
    private Handler mHandler;

    private TinyTaskManager() {
        mWorkThread = getWorkThread();
        mHandler = getHandler();
    }

    public static TinyTaskManager instance() {
        if (mInstance == null) {
            synchronized (TinyTaskManager.class) {
                if (mInstance == null) {
                    mInstance = new TinyTaskManager();
                }
            }
        }

        return mInstance;
    }

    private HandlerThread getWorkThread() {
        try {
            if (mWorkThread == null) {
                mWorkThread = new HandlerThread("TinyTaskManager");
                mWorkThread.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mWorkThread;
    }

    private Handler getHandler() {
        if (mHandler == null) {
            mHandler = new Handler(getWorkThread().getLooper());
        }

        return mHandler;
    }

    public void post(Runnable command) {
        getHandler().post(command);
    }

    public void postAtFrontOfQueue(Runnable command) {
        getHandler().postAtFrontOfQueue(command);
    }

    public void postDelayed(Runnable command, long delayMillis) {
        getHandler().postDelayed(command, delayMillis);
    }

    public void postAtTime(Runnable command, long delayMillis) {
        getHandler().postAtTime(command, delayMillis);
    }

    public void quit() {
        if (Build.VERSION.SDK_INT >= 18) {
            getWorkThread().quitSafely();
        } else {
            getWorkThread().quit();
        }
    }

}
