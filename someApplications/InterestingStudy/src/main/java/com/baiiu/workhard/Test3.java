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
public class Test3 implements Test {


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
        LogUtil.e("onMessageEvent: " + event);
    }
}
