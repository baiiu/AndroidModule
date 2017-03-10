package com.baiiu.commontool.net.retrofit;

import com.baiiu.commontool.net.ApiContants;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * author: baiiu
 * date: on 16/5/16 16:34
 * description: 初始化Retrofit
 */
public enum RetrofitClient implements ApiContants {
  INSTANCE;

  private final Retrofit retrofit;

  RetrofitClient() {
    retrofit = new Retrofit.Builder()
        //设置OKHttpClient
        .client(OKHttpFactory.INSTANCE.getOkHttpClient())

        //baseUrl
        .baseUrl(GITHUB_BASEURL)

        //gson转化器
        .addConverterFactory(GsonConverterFactory.create())

        //创建
        .build();
  }

  public Retrofit getRetrofit() {
    return retrofit;
  }
}
