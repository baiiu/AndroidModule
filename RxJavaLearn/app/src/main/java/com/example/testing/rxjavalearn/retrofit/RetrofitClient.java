package com.example.testing.rxjavalearn.retrofit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * auther: baiiu
 * time: 16/5/31 31 23:15
 * description:
 */
public class RetrofitClient {
    private Retrofit retrofit;

    public RetrofitClient(String baseUrl) {
        retrofit = new Retrofit.Builder()
                //设置OKHttpClient
                .client(new OkHttpClient())

                //baseUrl
                .baseUrl(baseUrl)

                //gson转化器
                .addConverterFactory(GsonConverterFactory.create())

                //RxJava
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())

                //创建
                .build();
    }




}
