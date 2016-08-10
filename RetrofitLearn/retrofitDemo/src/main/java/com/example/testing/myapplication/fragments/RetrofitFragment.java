package com.example.testing.myapplication.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import com.example.testing.myapplication.bean.GankIODay;
import com.example.testing.myapplication.bean.GankIOHistory;
import com.example.testing.myapplication.bean.Repo;
import com.example.testing.myapplication.bean.User;
import com.example.testing.myapplication.retrofit.ApiFactory;
import com.example.testing.myapplication.util.LogUtil;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * auther: baiiu
 * time: 16/5/16 16 22:43
 * description:
 */
public class RetrofitFragment extends Fragment {

    @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //normalGet();
        stringGet();
        //rxGet();

        //getWithParams();
        //post();

        //无缓存
        //anotherUrl();

        //默认缓存
        //getOneDay();
    }

    /**
     * 无网络状态下:
     *
     * 没设置缓存,onError:java.net.UnknownHostException: Unable to resolve host "api.github.com": No address
     * associated with hostname
     *
     * 设置了缓存:
     * 1. 有缓存,读取缓存
     * 2. 没有缓存,onErroretrofit2.adapter.rxjava.HttpException: HTTP 504 Unsatisfiable Request
     * (only-if-cached)
     * 和缓存策略相关
     */
    private void stringGet() {
        ApiFactory.gitHubAPI()
                .userInfoString("baiiu")
                .enqueue(new Callback<String>() {
                    @Override public void onResponse(Call<String> call, Response<String> response) {
                        LogUtil.d(response.body());
                    }

                    @Override public void onFailure(Call<String> call, Throwable t) {
                        LogUtil.d(t.toString());
                    }
                });
    }

    private void normalGet() {

        Call<User> userCall = ApiFactory.gitHubAPI()
                .userInfo("baiiu");

        userCall.enqueue(new Callback<User>() {
            @Override public void onResponse(Call<User> call, Response<User> response) {
                User body = response.body();
                LogUtil.d(body == null ? "body == null" : body.toString());
            }

            @Override public void onFailure(Call<User> call, Throwable t) {
                if (call.isCanceled()) {
                    LogUtil.d("the call is canceled , " + toString());
                } else {
                    LogUtil.e(t.toString());
                }
            }
        });

        //userCall.cancel();
    }


    private void rxGet() {
        ApiFactory.gitHubAPI()
                .userInfoRx("baiiu")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<User>() {
                    @Override public void onCompleted() {
                        LogUtil.d("onCompleted");
                    }

                    @Override public void onError(Throwable e) {
                        LogUtil.e(e.toString());
                    }

                    @Override public void onNext(User user) {
                        LogUtil.d(user.toString());
                    }
                });
    }

    private void getWithParams() {
        ApiFactory.gitHubAPI()
                .listRepos("baiiu")
                .enqueue(new Callback<List<Repo>>() {
                    @Override public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                        List<Repo> body = response.body();
                        LogUtil.d(body == null ? "body == null" : body.toString());
                    }

                    @Override public void onFailure(Call<List<Repo>> call, Throwable t) {

                    }
                });
    }

    private void post() {
        ApiFactory.gitHubAPI()
                .createUser(new User())
                .enqueue(new Callback<User>() {
                    @Override public void onResponse(Call<User> call, Response<User> response) {
                        User body = response.body();
                        LogUtil.d(body == null ? "body == null" : body.toString());
                    }

                    @Override public void onFailure(Call<User> call, Throwable t) {
                        LogUtil.d(t.toString());
                    }
                });
    }

    private void anotherUrl() {
        String s = "http://gank.io/api/day/history";
        ApiFactory.getAnotherAPI()
                .gankIOHistory(s)
                .enqueue(new Callback<GankIOHistory>() {
                    @Override
                    public void onResponse(Call<GankIOHistory> call, Response<GankIOHistory> response) {
                        GankIOHistory body = response.body();
                        LogUtil.d(body == null ? "body == null" : body.toString());
                    }

                    @Override public void onFailure(Call<GankIOHistory> call, Throwable t) {
                        LogUtil.e("anotherUrl ," + t.toString());
                    }
                });
    }

    private void getOneDay() {
        String s = "http://gank.io/api/day/2015/08/07";
        ApiFactory.getAnotherAPI()
                .getOneDay(s)
                .enqueue(new Callback<GankIODay>() {
                    @Override public void onResponse(Call<GankIODay> call, Response<GankIODay> response) {
                        GankIODay body = response.body();
                        LogUtil.d(body == null ? "body == null" : body.toString());
                    }

                    @Override public void onFailure(Call<GankIODay> call, Throwable t) {
                        LogUtil.e("getOneDay ," + t.toString());
                    }
                });
    }
}
