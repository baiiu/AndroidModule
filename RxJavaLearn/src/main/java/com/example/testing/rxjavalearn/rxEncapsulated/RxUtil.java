package com.example.testing.rxjavalearn.rxEncapsulated;

import rx.Observable;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * author: baiiu
 * date: on 16/8/4 10:38
 * description:
 */
public class RxUtil {

    public static void addSubscription(CompositeSubscription compositeSubscription, Subscription subscription) {
        if (compositeSubscription != null && subscription != null) {
            compositeSubscription.add(subscription);
        }
    }


    public static void unSubscribe(CompositeSubscription compositeSubscription) {
        if (compositeSubscription != null

                && compositeSubscription.hasSubscriptions()) {
            compositeSubscription.unsubscribe();
        }
    }

    public static <T> Observable<T> handleError(Observable<T> observable) {
        return observable.onErrorResumeNext(throwable -> {
            return Observable.just(null);
        });
    }

}
