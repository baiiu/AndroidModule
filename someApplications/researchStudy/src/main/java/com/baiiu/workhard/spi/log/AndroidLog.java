package com.baiiu.workhard.spi.log;

import com.google.auto.service.AutoService;

/**
 * author: zhuzhe
 * time: 2020-04-21
 * description:
 */
@AutoService(ILog.class)
public class AndroidLog implements ILog {

    @Override
    public void log(Object obj) {
        android.util.Log.e("mLogU", String.valueOf(obj));
    }
}
