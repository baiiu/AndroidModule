package com.example.testing.myapplication.util;

import com.baiiu.library.LogUtil;

/**
 * author: baiiu
 * date: on 16/8/31 19:40
 * description:
 */
public enum LogInterceptor implements MHttpLoggingInterceptor.Logger {
    INSTANCE;

    @Override public void log(String message, @LogUtil.LogType int type) {
        LogUtil.printLog(false, type, null, message);
    }
}
