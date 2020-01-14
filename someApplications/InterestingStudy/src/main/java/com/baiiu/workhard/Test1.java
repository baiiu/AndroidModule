package com.baiiu.workhard;

import com.baiiu.library.LogUtil;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * author: zhuzhe
 * time: 2020-01-14
 * description:
 */
public class Test1 implements Test {


    @Override public void init() {
        EventBus.getDefault()
                .register(this);
    }

    public void destory() {
        EventBus.getDefault()
                .unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onMessageEvent(String event) {
        LogUtil.e("onMessageEvent: begin: " + Thread.currentThread()
                .getName() + ", " + event);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        LogUtil.e("onMessageEvent: end: " + Thread.currentThread()
                .getName() + ", " + event);
    }

}
