package com.baiiu.commontool.net.retrofit.interceptor;

import android.text.TextUtils;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * author: baiiu
 * date: on 16/5/17 15:14
 * description:
 * 默认所有请求先读缓存,1分钟内不刷新
 * 若单独设置某个Url的header时,以该header为准
 */
public class AllCachedInterceptor implements Interceptor {

  private static final int MAX_AGE = 60;

  @Override public Response intercept(Chain chain) throws IOException {
    Request request = chain.request();

    /*
      自己设置的请求头中没有设置Cache-Control的话就添加

      //自己请求头中添加特定header
      @Headers("Cache-Control: max-age=60,max-stale=60")
      @GET Call<Daily> getNewsList(@Url String url);
     */
    String cacheControl = request.cacheControl().toString();

    if (TextUtils.isEmpty(cacheControl)) {
      cacheControl = "public, max-age=" + MAX_AGE;
    }

    Response response = chain.proceed(request);

    //设置response的Cache-Control,设置缓存时间
    return response.newBuilder()
        .header("Cache-Control", cacheControl)
        .removeHeader("Pragma")
        .build();
  }
}
