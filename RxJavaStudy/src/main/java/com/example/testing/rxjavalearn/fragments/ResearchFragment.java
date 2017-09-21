package com.example.testing.rxjavalearn.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.example.testing.rxjavalearn.operators.BaseFragment;
import com.example.testing.rxjavalearn.util.LogUtil;
import java.util.List;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * author: baiiu
 * date: on 17/9/21 16:47
 * description:
 */
public class ResearchFragment extends BaseFragment {

    @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        test();
        betterOne();
    }

    private void test() {
        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override public void onNext(String s) {
                LogUtil.d("onNext: " + s);
            }

            @Override public void onCompleted() {
                LogUtil.d("onCompleted");
            }

            @Override public void onError(Throwable e) {
                LogUtil.d("onError");
            }
        };

        Observable.just(1,2);

        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("Hello");
                subscriber.onNext("Hi");
                subscriber.onNext("Aloha");
                subscriber.onCompleted();
            }
        });

        observable.subscribe(subscriber);
    }


    private void betterOne() {
        Subscription subscription = Observable.

                zip(Observable.from(new Integer[] { 6, 5, 5, 3, 2, 2, 1 }), Observable.just(0, 0, 0, 0, 0, 0, 0, 0, 0),
                    new Func2<Integer, Integer, Integer>() {
                        @Override public Integer call(Integer integer, Integer integer2) {
                            LogUtil.d(integer + ", " + integer2);
                            return integer + integer2;
                        }
                    })
                .sorted()
                .distinct()
                .map(new Func1<Integer, String>() {
                    @Override public String call(Integer integer) {
                        return String.valueOf(integer);
                    }
                })
                .toList()
                //.delay(3, TimeUnit.SECONDS)
                .doOnUnsubscribe(new Action0() {
                    @Override public void call() {
                        LogUtil.d("unsubscribe");
                    }
                })
                .doOnSubscribe(new Action0() {
                    @Override public void call() {
                        LogUtil.d("subscribe");
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<String>>() {
                    @Override public void onCompleted() {
                        LogUtil.d("onCompleted"); //完了自动释放
                    }

                    @Override public void onError(Throwable e) {

                    }

                    @Override public void onNext(List<String> strings) {
                        LogUtil.d(strings.toString());
                    }
                });

        //subscription.unsubscribe();
    }

}
