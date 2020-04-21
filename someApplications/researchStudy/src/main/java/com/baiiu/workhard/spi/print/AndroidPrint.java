package com.baiiu.workhard.spi.print;

import com.google.auto.service.AutoService;

/**
 * author: zhuzhe
 * time: 2020-04-21
 * description:
 */
public class AndroidPrint implements IPrint {
    @Override
    public void print(Object obj) {
        android.util.Log.e("mLogU", String.valueOf(obj));
    }
}
