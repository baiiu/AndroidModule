package com.example.testing.myapplication.retrofit;

import com.example.testing.myapplication.retrofit.http.RetrofitClient;

/**
 * author: baiiu
 * date: on 16/5/16 17:42
 * description:
 */
public enum ApiFactory {
  INSTANCE;

  private final GitHubAPI gitHubAPI;
  private final AnotherAPI anotherAPI;

  ApiFactory() {
    gitHubAPI = RetrofitClient.INSTANCE.getRetrofit().create(GitHubAPI.class);
    anotherAPI = RetrofitClient.INSTANCE.getRetrofit().create(AnotherAPI.class);
  }

  public GitHubAPI gitHubAPI() {
    return gitHubAPI;
  }

  public AnotherAPI getAnotherAPI() {
    return anotherAPI;
  }
}
