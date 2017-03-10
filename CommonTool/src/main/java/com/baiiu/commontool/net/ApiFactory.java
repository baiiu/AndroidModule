package com.baiiu.commontool.net;


import com.baiiu.commontool.net.api.AnotherAPI;
import com.baiiu.commontool.net.api.GitHubAPI;
import com.baiiu.commontool.net.retrofit.RetrofitClient;

/**
 * author: baiiu
 * date: on 16/5/16 17:42
 * description:
 */
public enum ApiFactory {
  INSTANCE;

  private static GitHubAPI gitHubAPI;
  private static AnotherAPI anotherAPI;

  ApiFactory() {
  }

  public static <T> T getAPI(T t, Class<T> tClass) {
    return t == null ? RetrofitClient.INSTANCE.getRetrofit().create(tClass) : t;
  }

  public static GitHubAPI gitHubAPI() {
    return gitHubAPI = getAPI(gitHubAPI, GitHubAPI.class);
  }

  public static AnotherAPI getAnotherAPI() {
    return anotherAPI = getAPI(anotherAPI, AnotherAPI.class);
  }
}
