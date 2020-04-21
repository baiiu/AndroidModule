package com.baiiu.workhard.spi.log;

import android.widget.Toast;

import com.baiiu.workhard.MyApplication;
import com.google.auto.service.AutoService;

/**
 * author: zhuzhe
 * time: 2020-04-21
 * description:
 */
@AutoService(ILog.class)
public class ToastLog implements ILog {

    @Override
    public void log(Object obj) {
        Toast.makeText(MyApplication.sContext, String.valueOf(obj), Toast.LENGTH_SHORT).show();
    }
}
