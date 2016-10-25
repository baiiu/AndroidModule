package com.baiiu.library.okHttpLog;

import com.baiiu.library.LogUtil;

/**
 * author: baiiu
 * date: on 16/8/31 19:40
 * description:
 */
public enum LogInterceptor implements HttpLoggingInterceptorM.Logger {
    INSTANCE;


    public static final String OKHTTP_TAG_STR = "okhttp";

    @Override public void log(String message, @LogUtil.LogType int type) {
        LogUtil.printLog(false, type, OKHTTP_TAG_STR, message);
    }
}
