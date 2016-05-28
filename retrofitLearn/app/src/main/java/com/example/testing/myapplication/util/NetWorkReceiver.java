package com.example.testing.myapplication.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by baiiu on 15/11/17.
 * 监听网络变化.
 */
public class NetWorkReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        if (HttpNetUtil.setConnected()) {
            //if turns to true, show post event to refresh UI
        }


    }

}
