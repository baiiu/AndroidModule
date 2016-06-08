package com.example.testing.rxjavalearn.operators;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.testing.rxjavalearn.util.LogUtil;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;

/**
 * author: baiiu
 * date: on 16/6/8 16:59
 * description:
 */
public class FilterOperatorsFragment extends BaseFragment {
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        throttle();
    }

    private void throttle() {
        Observable
                .interval(1, TimeUnit.SECONDS)
                .compose(bindToLifecycle())
                .throttleWithTimeout(3, TimeUnit.SECONDS)
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.d("onComplete");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d(e.toString());
                    }

                    @Override
                    public void onNext(Long aLong) {
                        LogUtil.d(aLong);
                    }
                });
    }


}
