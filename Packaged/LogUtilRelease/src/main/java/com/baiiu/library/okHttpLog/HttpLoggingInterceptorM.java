package com.baiiu.library.okHttpLog;

import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * author: baiiu
 * date: on 16/8/31 19:09
 * description:
 */
public final class HttpLoggingInterceptorM implements Interceptor {

    public HttpLoggingInterceptorM(Logger logger) {
    }


    public enum Level {
        BODY
    }

    public HttpLoggingInterceptorM setLevel(Level level) {
        return this;
    }

    interface Logger {
        void log(String message, int type);
    }

    @Override public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        return chain.proceed(request);
    }

}