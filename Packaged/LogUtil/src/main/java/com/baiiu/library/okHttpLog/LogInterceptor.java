package com.baiiu.library.okHttpLog;

import com.baiiu.library.LogUtil;

/**
 * author: baiiu
 * date: on 16/8/31 19:40
 * description:
 */
public enum LogInterceptor implements HttpLoggingInterceptorM.Logger {
    INSTANCE;

    @Override public void log(String message, @LogUtil.LogType int type) {
        LogUtil.printLog(false, type, null, message);
    }
}
