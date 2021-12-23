package com.baiiu.multiprocess;

import android.os.Handler;
import android.os.Message;

/**
 * author: baiiu
 * date: on 17/12/27 15:27
 * description:
 */
public enum UserManager {
    INSTANCE;

    UserManager() {
        //LogUtil.d("Looper.myLooper(): " + Looper.myLooper() == null);
    }

    public int age = 1;

    public static int sId = 1;

    Handler handler = new Handler(new Handler.Callback() {
        @Override public boolean handleMessage(Message msg) {
            return false;
        }
    });

}
