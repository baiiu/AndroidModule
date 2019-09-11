package com.example.testing.rxjavalearn.fragments;

import android.os.Bundle;
import com.example.testing.rxjavalearn.operators.BaseFragment;
import com.example.testing.rxjavalearn.util.LogUtil;
import com.orhanobut.logger.Logger;
import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func2;

/**
 * auther: baiiu
 * time: 16/8/10 10 23:08
 * description:
 */
public class ErrorTestFragment extends BaseFragment {

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //errorOne();

        //Observable.timer(1, TimeUnit.SECONDS)
        //        .subscribe(aLong -> {
        //            errorTwo();
        //        }, e -> LogUtil.e(e.toString()));

        //Observable.just(null)
        //        .map(s -> s.toString())
        //        .onErrorResumeNext(throwable -> {
        //            if (throwable instanceof NullPointerException) {
        //                LogUtil.e("是空指针异常");
        //                return Observable.error(new IllegalStateException("哈哈哈哈空指针拦截"));
        //            }
        //
        //            return Observable.just(null);
        //        })
        //        .subscribe(getSubscriber());


        Observable.zip(Observable.just(1), Observable.just(null), new Func2<Integer, Integer, String>() {
            @Override public String call(Integer integer, Integer integer2) {
                return String.valueOf(integer) + ", " + String.valueOf(integer2);
            }
        })
                .subscribe(getSubscriber());

    }

    /**
     * 可以在对Observable转换时抛出异常
     */
    private void errorOne() {
        Observable.interval(1, TimeUnit.SECONDS)
                //.compose(bindUntilEvent(FragmentEvent.DESTROY))
                .doOnNext(aLong -> {
                    if (aLong == 3) {
                        throw new RuntimeException("sth error");
                    }
                })
                .subscribe(getSubscriber());
    }

    /**
     * onNext中处理后抛出异常到onError中处理
     */
    private void errorTwo() {
        Observable.interval(1, TimeUnit.SECONDS)
                //.compose(bindUntilEvent(FragmentEvent.DESTROY))
                .subscribe(new Subscriber<Long>() {
                    @Override public void onCompleted() {
                        LogUtil.d("onCompleted");
                    }

                    @Override public void onError(Throwable e) {
                        Logger.e(e.toString());
                    }

                    @Override public void onNext(Long aLong) {
                        LogUtil.d("errorTwo: " + aLong);
                        if (aLong == 5) {
                            throw new RuntimeException("sth error");
                        }
                    }
                });
    }

}
