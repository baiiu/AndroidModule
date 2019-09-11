package com.example.testing.myapplication.retrofit;

import com.example.testing.myapplication.retrofit.http.RetrofitClient;

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

  public static GitHubAPI gitHubAPI() {
    if (gitHubAPI == null) {
      gitHubAPI = RetrofitClient.INSTANCE.getRetrofit().create(GitHubAPI.class);
    }
    return gitHubAPI;
  }

  public static AnotherAPI getAnotherAPI() {
    if (anotherAPI == null) {
      anotherAPI = RetrofitClient.INSTANCE.getRetrofit().create(AnotherAPI.class);
    }
    return anotherAPI;
  }
}
