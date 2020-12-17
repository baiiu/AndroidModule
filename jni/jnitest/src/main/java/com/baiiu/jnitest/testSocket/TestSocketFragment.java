package com.baiiu.jnitest.testSocket;

import com.baiiu.jnitest.base.BaseFragment;

/**
 * author: baiiu
 * time: 2020/12/16
 * description:
 */
public class TestSocketFragment extends BaseFragment implements Runnable {

    static {
        System.loadLibrary("socket-lib");
    }

    private Thread thread;
    private boolean isRunning;

    @Override
    protected void initOnCreateView() {
        if (isRunning) {
            return;
        }
        isRunning = true;

        thread = new Thread(this);
        thread.start();
    }


    @Override
    public void run() {

        boolean ret = native_connect();

        if (!ret) {
            isRunning = false;
        }

        while (isRunning && !Thread.interrupted()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        isRunning = false;

        if (thread != null) {
            thread.interrupt();
            thread = null;
        }
    }

    private native boolean native_connect();
}
