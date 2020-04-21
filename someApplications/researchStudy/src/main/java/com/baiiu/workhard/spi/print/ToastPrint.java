package com.baiiu.workhard.spi.print;

import android.widget.Toast;

import com.baiiu.workhard.MyApplication;

/**
 * author: zhuzhe
 * time: 2020-04-21
 * description:
 */
public class ToastPrint implements IPrint {

    @Override
    public void print(Object obj) {
        Toast.makeText(MyApplication.sContext, String.valueOf(obj), Toast.LENGTH_SHORT).show();
    }
}
