package com.example.testing.rxjavalearn.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.testing.rxjavalearn.bean.Repo;
import com.example.testing.rxjavalearn.bean.User;
import com.example.testing.rxjavalearn.util.LogUtil;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * author: baiiu
 * date: on 16/5/28 10:59
 * description: 与Retrofit结合
 */
public class RetrofitRxFragment extends Fragment {

  private static final String GITHUB_BASEURL = "https://api.github.com/";

  private Retrofit retrofit;
  private GitHubAPI gitHubAPI;

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    retrofit = new Retrofit.Builder()
        //设置OKHttpClient
        .client(new OkHttpClient())

        //baseUrl
        .baseUrl(GITHUB_BASEURL)

        //gson转化器
        .addConverterFactory(GsonConverterFactory.create())

        //RxJava
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())

        //创建
        .build();

    gitHubAPI = retrofit.create(GitHubAPI.class);

    gitHubAPI.userInfo("baiiu")
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Subscriber<User>() {
          @Override public void onCompleted() {
            LogUtil.d("onComplete");
          }

          @Override public void onError(Throwable e) {
            LogUtil.e(e.toString());
          }

          @Override public void onNext(User user) {
            LogUtil.d(user.toString());
          }
        });

    gitHubAPI.listRepos("baiiu").enqueue(new Callback<List<Repo>>() {
      @Override public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
        List<Repo> body = response.body();
        LogUtil.d(body.toString());
      }

      @Override public void onFailure(Call<List<Repo>> call, Throwable t) {
        LogUtil.d(t.toString());
      }
    });
  }
}
