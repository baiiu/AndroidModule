package com.example.testing.myapplication.fragments;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.baiiu.library.LogUtil;
import com.example.testing.myapplication.bean.User;
import com.example.testing.myapplication.mClass.NotResponseException;
import com.example.testing.myapplication.retrofit.ApiFactory;
import java.net.ConnectException;
import java.net.UnknownHostException;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * auther: baiiu
 * time: 16/8/10 10 23:06
 * description:
 */
public class ErrorTestFragment extends Fragment {


    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        rxGet();
        rxGetError();
    }

    private void rxGetError() {
        ApiFactory.gitHubAPI()
                .userInfoRx("baiiu")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<User, Observable<User>>() {
                    @Override public Observable<User> call(User user) {
                        return Observable.error(new RuntimeException("sth error"));
                    }
                })
                .subscribe(new Subscriber<User>() {
                    @Override public void onCompleted() {
                        LogUtil.d("onComplete");
                    }

                    @Override public void onError(Throwable e) {
                        LogUtil.d(e.toString());

                        if (e instanceof UnknownHostException || e instanceof ConnectException) {
                            //无网
                            LogUtil.d("无网");
                            Toast.makeText(getContext(), "无网", Toast.LENGTH_SHORT)
                                    .show();
                        } else if (e instanceof NotResponseException) {
                            //非ApiResponse
                            LogUtil.d("非正常数据");
                            Toast.makeText(getContext(), "非正常数据" + e.getMessage(), Toast.LENGTH_SHORT)
                                    .show();
                        } else {
                            LogUtil.d("其他错误" + e.toString());
                            Toast.makeText(getContext(), "非正常数据", Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }

                    @Override public void onNext(User user) {
                        LogUtil.d(user.toString());
                    }
                });

    }


    /**
     * 无设置缓存情况下
     * 无网时直接抛出异常,直接onError:java.net.UnknownHostException: Unable to resolve host "api.github.com": No address
     * associated with hostname
     * java.net.ConnectException: Failed to connect to /172.16.12.246:8888
     *
     * 设置缓存时:
     *
     * 有缓存,读取缓存
     * 无缓存,抛出异常,直接onError: retrofit2.adapter.rxjava.HttpException: HTTP 504 Unsatisfiable Request (only-if-cached)
     */
    private void rxGet() {
        ApiFactory.gitHubAPI()
                .userInfoRx("baiiu")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Func1<User, Boolean>() {
                    @Override public Boolean call(User user) {
                        if (user == null) {
                            return false;
                        }

                        return true;
                    }
                })
                .subscribe(new Subscriber<User>() {
                    @Override public void onCompleted() {
                        LogUtil.d("onComplete");
                    }

                    @Override public void onError(Throwable e) {
                        LogUtil.d(e.toString());

                        if (e instanceof UnknownHostException || e instanceof ConnectException) {
                            //无网
                            LogUtil.d("无网");
                            Toast.makeText(getContext(), "无网", Toast.LENGTH_SHORT)
                                    .show();
                        } else if (e instanceof NotResponseException) {
                            //非ApiResponse
                            LogUtil.d("非正常数据");
                            Toast.makeText(getContext(), "非正常数据" + e.getMessage(), Toast.LENGTH_SHORT)
                                    .show();
                        } else {
                            LogUtil.d("其他错误" + e.toString());
                            Toast.makeText(getContext(), "非正常数据", Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }

                    @Override public void onNext(User user) {
                        LogUtil.d(user.toString());
                    }
                });
    }
}
