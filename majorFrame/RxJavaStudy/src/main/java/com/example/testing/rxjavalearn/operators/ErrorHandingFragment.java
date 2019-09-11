package com.example.testing.rxjavalearn.operators;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.testing.rxjavalearn.util.LogUtil;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.functions.Func2;

/**
 * auther: baiiu
 * time: 16/6/9 09 08:35
 * description:
 */
public class ErrorHandingFragment extends BaseFragment {
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


//        onErrorReturn();
//        onErrorResumeNext();
//        OnExceptionResumeNext();


//        retry();
        retryWhen();
    }

    /**
     * 当错误发生时，retryWhen会接收onError的throwable作为参数，
     * 并根据定义好的函数返回一个Observable，如果这个Observable发射一个数据，就会重新订阅。
     * <p>
     * 需要注意的是使用retryWhen的时候,因为每次重新订阅都会产生错误，
     * 所以作为参数的obserbvable会不断地发射数据，使用zipWith操作符可以限制重新订阅的次数，否则会无限制地重新订阅。
     *
     * 会正常结束
     */
    private void retryWhen() {
        getErrorObservable()
                //.compose(bindToLifecycle())
                .retryWhen(new Func1<Observable<? extends Throwable>, Observable<Long>>() {
                    @Override
                    public Observable<Long> call(Observable<? extends Throwable> observable) {
                        //会重复执行,处理这个errorObservables时
//                        return observable.flatMap(o -> Observable.timer(1, TimeUnit.SECONDS));

                        //使用Zip限制次数,zip操作符发射最少的那个
                        return observable
                                .zipWith(Observable.just(1, 2, 3), new Func2<Throwable, Integer, String>() {
                                    @Override
                                    public String call(Throwable throwable, Integer integer) {
                                        return throwable.toString() + integer;
                                    }
                                })
                                .flatMap(new Func1<String, Observable<Long>>() {
                                    @Override
                                    public Observable<Long> call(String s) {
                                        LogUtil.e(s);
                                        return Observable.timer(1, TimeUnit.SECONDS);
                                    }
                                });

                    }
                })
                .subscribe(getSubscriber());
    }

    /**
     * Retry操作符在发生错误的时候会重新进行订阅,而且可以重复多次，
     * 所以发射的数据可能会产生重复。如果重复指定次数还有错误的话就会将错误返回给观察者,走onError
     */
    private void retry() {
        getErrorObservable()
                //.compose(bindToLifecycle())
                .retry(3)
                .subscribe(getSubscriber());
    }

    /**
     * 类似于OnErrorResume,不同之处在于其会对onError抛出的数据类型做判断，
     * 如果是Exception，也会使用另外一个Observable代替原Observable继续发射数据，
     * 否则会将错误分发给Subscriber。
     */
    private void OnExceptionResumeNext() {
        getErrorObservable()
                //.compose(bindToLifecycle())
                .onExceptionResumeNext(Observable.just(9, 10, 11))
                .subscribe(getSubscriber());
    }

    /**
     * 当发生错误的时候，由另外一个Observable来代替当前的Observable并继续发射数据
     */
    private void onErrorResumeNext() {
        getErrorObservable()
                //.compose(bindToLifecycle())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends Integer>>() {
                    @Override
                    public Observable<? extends Integer> call(Throwable throwable) {
                        return Observable.just(11, 12, 13);
                    }
                })
                .subscribe(getSubscriber());
    }

    /**
     * OnErrorReturn-当发生错误的时候，让Observable发射一个预先定义好的数据并正常地终止
     */
    private void onErrorReturn() {
        getErrorObservable()
                .onErrorReturn(new Func1<Throwable, Integer>() {
                    @Override
                    public Integer call(Throwable throwable) {
                        //发生错误时发射10.
                        return 10;
                    }
                })
                //.compose(bindToLifecycle())
                .subscribe(getSubscriber());
    }


    private Observable<Integer> getErrorObservable() {
        return Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                for (int i = 0; i < 8; ++i) {
                    if (i == 6) {
//                        subscriber.onError(new Throwable("onError"));
                        subscriber.onError(new Exception("onError"));
                        continue;
                    }

                    subscriber.onNext(i);
                }
            }
        });
    }


}

