package com.baiiu.jnitest.thread;

import android.os.Looper;

import com.baiiu.jnitest.base.BaseFragment;

/**
 * author: baiiu
 * time: 2020/5/24
 * description:
 */
public class ThreadFragment extends BaseFragment {
    static {
        System.loadLibrary("thread-lib");
    }

    public interface ICallBack {
        void onCallBack();
    }

    @Override
    protected void initOnCreateView() {
        nativeCallBack(new ICallBack() {
            @Override
            public void onCallBack() {
                log();
            }
        });


        nativeThreadCallBack(new ICallBack() {
            @Override
            public void onCallBack() {
                log();
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                nativeCallBack(new ICallBack() {
                    @Override
                    public void onCallBack() {
                        log();
                    }
                });
                nativeThreadCallBack(new ICallBack() {
                    @Override
                    public void onCallBack() {
                        log();
                    }
                });
            }
        }).start();


    }

    private void log() {
        android.util.Log.e("mLogU", (Looper.getMainLooper() == Looper.myLooper()) + "， " + Thread.currentThread().getName() + ", " + +Thread.currentThread().getId());
    }

    // 当前线程
    public native void nativeCallBack(ICallBack callBack);

    // native中newThread传回
    public native void nativeThreadCallBack(ICallBack callBack);

}