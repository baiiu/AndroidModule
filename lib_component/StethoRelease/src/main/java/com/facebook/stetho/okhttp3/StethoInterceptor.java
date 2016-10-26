package com.facebook.stetho.okhttp3;

import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * author: baiiu
 * date: on 16/10/24 16:55
 * description:
 */
public class StethoInterceptor implements Interceptor {

    @Override public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        return chain.proceed(request);
    }

}
