package com.example.testing.myapplication.retrofit.http;

import com.example.testing.myapplication.MyApplication;
import com.example.testing.myapplication.retrofit.http.interceptor.OnOffLineCachedInterceptor;
import com.example.testing.myapplication.retrofit.http.interceptor.UserAgentInterceptor;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import java.util.concurrent.TimeUnit;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * author: baiiu
 * date: on 16/5/16 16:43
 * description:
 */
enum OKHttpFactory {

  INSTANCE;

  private final OkHttpClient okHttpClient;

  private static final int TIMEOUT_READ = 25;
  private static final int TIMEOUT_CONNECTION = 25;

  OKHttpFactory() {
    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

    Cache cache = new Cache(MyApplication.mContext.getCacheDir(), 10 * 1024 * 1024);

    okHttpClient = new OkHttpClient.Builder()
        //打印请求log
        .addInterceptor(interceptor)

        //stetho,可以在chrome中查看请求
        .addNetworkInterceptor(new StethoInterceptor())

        //添加UA
        .addInterceptor(new UserAgentInterceptor(HttpHelper.getUserAgent()))

        //必须是设置Cache目录
        .cache(cache)

        //走缓存
        .addInterceptor(new OnOffLineCachedInterceptor())
        .addNetworkInterceptor(new OnOffLineCachedInterceptor())

        //失败重连
        .retryOnConnectionFailure(true)

        //time out
        .readTimeout(TIMEOUT_READ, TimeUnit.SECONDS)
        .connectTimeout(TIMEOUT_CONNECTION, TimeUnit.SECONDS)

        .build();
  }

  public OkHttpClient getOkHttpClient() {
    return okHttpClient;
  }
}
