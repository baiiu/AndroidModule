package com.baiiu.commontool.net.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Url;

/**
 * auther: baiiu
 * time: 16/5/17 17 21:49
 * description: 另外的一些链接,不是基于baseUrl,使用 @url 直接传入url即可
 */
public interface AnotherAPI {

    @Headers("Cache-Control:no-cache,max-age=0") @GET Call<Object> gankIOHistory(@Url String url);

    //http://gank.io/api/day/2015/08/07
    @GET Call<Object> getOneDay(@Url String url);

    //http://news-at.zhihu.com/api/4/news/before/20160517
    @Headers("Cache-Control: max-age=120") @GET Call<Object> getNewsList(@Url String url);
}
