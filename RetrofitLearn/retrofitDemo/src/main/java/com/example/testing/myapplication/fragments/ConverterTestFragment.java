package com.example.testing.myapplication.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import com.example.testing.myapplication.bean.User;
import com.example.testing.myapplication.retrofit.ApiFactory;
import com.example.testing.myapplication.util.LogUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * author: baiiu
 * date: on 16/8/10 19:45
 * description:
 */
public class ConverterTestFragment extends Fragment {

    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //stringGet();
        //normalGet();
        rxGet();
    }


    private void rxGet() {
        ApiFactory.gitHubAPI()
                .userInfoRxS("baiiu")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override public void onCompleted() {
                        LogUtil.d("onCompleted");
                    }

                    @Override public void onError(Throwable e) {
                        LogUtil.e(e.toString());
                    }

                    @Override public void onNext(String user) {
                        LogUtil.d(user.toString());
                    }
                });
    }

    /**
     * 获取String
     */
    private void stringGet() {
        ApiFactory.gitHubAPI()
                .userInfoString("baiiu")
                .enqueue(new Callback<String>() {
                    @Override public void onResponse(Call<String> call, Response<String> response) {
                        LogUtil.d("stringGet: " + response.body());
                    }

                    @Override public void onFailure(Call<String> call, Throwable t) {
                        LogUtil.d("stringGet: " + t.toString());
                    }
                });
    }

    /**
     * 获取对象
     */
    private void normalGet() {
        ApiFactory.gitHubAPI()
                .userInfo("baiiu")
                .enqueue(new Callback<User>() {
                    @Override public void onResponse(Call<User> call, Response<User> response) {
                        User body = response.body();
                        LogUtil.d("normalGet: " + (body == null ? "body == null" : body.toString()));
                    }

                    @Override public void onFailure(Call<User> call, Throwable t) {
                        if (call.isCanceled()) {
                            LogUtil.d("normalGet: " + toString());
                        } else {
                            LogUtil.e("normalGet: " + t.toString());
                        }
                    }
                });

    }

}
