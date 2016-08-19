package com.example.testing.rxjavalearn.transformer;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * author: baiiu
 * date: on 16/8/17 11:15
 * description:
 */
public class Transformers {

    public static <T> Observable.Transformer<T, T> switchSchedulers() {
        return observable -> observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static <T> Observable.Transformer<T, T> catchExceptionToNull() {
        return observable -> observable.onErrorResumeNext(throwable -> Observable.just(null));
    }

}
