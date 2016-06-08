package com.example.testing.rxjavalearn.operators;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.testing.rxjavalearn.util.LogUtil;
import com.orhanobut.logger.Logger;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * author: baiiu
 * date: on 16/6/8 16:59
 * description:
 */
public class FilterOperatorsFragment extends BaseFragment {
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        throttleFirst();
//        throttleWithTimeout();
//        debounce();

//        distinct();
//        distinctUtilChanged();


//        elementAt();
//        filter();

//        first();
//        firstAndBlockingObservable();

//        skip();
//        take();
        sample();
        throttleFirst();
    }

    /**
     * ThrottleFirst操作符则会定期发射这个时间段里源Observable发射的第一个数据
     * <p>
     * 记录第一个observable发射时间,过滤掉3秒内的数据,等到3秒时在发射新数据.
     */
    private void throttleFirst() {
        Observable
                //从0开始发射
                .interval(200, TimeUnit.MILLISECONDS)
                .compose(bindToLifecycle())
                .throttleFirst(1000, TimeUnit.MILLISECONDS)
                .subscribe(getSubscriber());
    }

    /**
     * 每隔1秒抽一次样
     */
    private void sample() {
        Observable
                .interval(200, TimeUnit.MILLISECONDS)
                .sample(1000, TimeUnit.MILLISECONDS)
                .compose(bindToLifecycle())
                .subscribe(getSubscriber());
    }

    private void take() {
        Observable
                .range(0, 10)
                .compose(bindToLifecycle())
                .take(5)
                .subscribe(getSubscriber());
    }

    /**
     * 跳过前面几个
     */
    private void skip() {
        Observable
                .range(0, 10)
                .compose(bindToLifecycle())
                .skip(5)
                .subscribe(getSubscriber());

    }

    /**
     * Observable.toBlocking或者BlockingObservable.from方法来将一个Observable对象转化为BlockingObservable对象
     */
    private void firstAndBlockingObservable() {

        Observable
                .create(new Observable.OnSubscribe<Integer>() {
                    @Override
                    public void call(Subscriber<? super Integer> subscriber) {
                        for (int i = 0; i < 10; ++i) {
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            LogUtil.d("onNext " + i);
                            subscriber.onNext(i);
                        }

                        subscriber.onCompleted();
                    }
                })
                .compose(bindToLifecycle())
                .first(integer -> integer > 5)
                .toBlocking()
                .subscribe(LogUtil::d, e -> Logger.e(e.toString()), () -> LogUtil.d("onComplete"));

    }

    /**
     * 只取满足条件的第一个数据
     */
    private void first() {
        Observable
                .range(0, 10)
                .first(integer -> integer > 5)
                .subscribe(LogUtil::d, e -> Logger.e(e.toString()), () -> LogUtil.d("onComplete"));
    }

    /**
     * 过滤操作
     */
    private void filter() {
        Observable
                .range(0, 10)
                .compose(bindToLifecycle())
                .filter(integer -> integer % 2 == 0)
                .subscribe(LogUtil::d, e -> Logger.e(e.toString()), () -> LogUtil.d("onComplete"));
    }

    /**
     * 获取指定位置的数据
     */
    private void elementAt() {
        Observable
                .range(0, 10)
                .compose(bindToLifecycle())
                .elementAt(5)
                .subscribe(LogUtil::d, e -> Logger.e(e.toString()), () -> LogUtil.d("onComplete"));
    }


    /**
     * 去除相邻的重复数据
     */
    private void distinctUtilChanged() {

        Observable
                .just(1, 1, 2, 2, 1, 1, 2, 2)
                .compose(bindToLifecycle())
                .distinctUntilChanged()
                .map(integer -> "distinctUntilChanged: " + integer)
                .subscribe(LogUtil::d, e -> Logger.e(e.toString()), () -> LogUtil.d("onComplete"));
    }

    /**
     * 输出的是全部不重复的数据,只输出1,2
     */
    private void distinct() {
        Observable
                .just(1, 1, 2, 2, 1, 1, 2, 2)
                .compose(bindToLifecycle())
                .distinct()
                .map(integer -> "distinct: " + integer)
                .subscribe(LogUtil::d, e -> Logger.e(e.toString()), () -> LogUtil.d("onComplete"));
    }

    /**
     * debounce操作符也可以使用时间来进行过滤，这时它跟throttleWithTimeOut使用起来是一样，
     * <p>
     * deounce操作符还可以根据一个函数来进行限流。这个函数的返回值是一个临时Observable，
     * 如果源Observable在发射一个新的数据的时候，上一个数据根据函数所生成的临时Observable还没有结束,没有调用onComplete，
     * 那么上一个数据就会被过滤掉。如果是最后一个,还是会发射.
     * <p>
     * <p>
     * 丢弃上一个数据
     */
    private void debounce() {
        Observable
                .range(0, 10)
                .debounce(new Func1<Integer, Observable<Integer>>() {
                    @Override
                    public Observable<Integer> call(Integer integer) {
//                        LogUtil.d("debounce: " + integer);


                        return Observable.create(new Observable.OnSubscribe<Integer>() {
                            @Override
                            public void call(Subscriber<? super Integer> subscriber) {
                                if (!subscriber.isUnsubscribed() && integer % 2 == 0) {
                                    LogUtil.d("onCompleted: " + integer);
                                    subscriber.onNext(integer);
                                    subscriber.onCompleted();
                                }
                            }
                        });
                    }
                })
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.d("onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d(e.toString());
                    }

                    @Override
                    public void onNext(Integer integer) {
                        //最后一个9发射出来了.
                        LogUtil.d("subscriber: " + integer);
                    }
                });
    }

    /**
     * 发射时间小于200ms被过滤掉
     * <p>
     * 源Observable每次发射出来一个数据后就会进行计时,
     * 如果在设定好的时间结束前源Observable有新的数据发射出来，这个数据就会被丢弃，同时重新开始计时。
     * 如果每次都是在计时结束前发射数据，那么这个限流就会走向极端：只会发射最后一个数据。
     */
    private void throttleWithTimeout() {
        Observable
                .create(new Observable.OnSubscribe<Integer>() {
                    @Override
                    public void call(Subscriber<? super Integer> subscriber) {
                        for (int i = 0; i < 10; ++i) {
                            if (!subscriber.isUnsubscribed()) {
                                subscriber.onNext(i);
                            }

                            int sleep = i % 3 == 0 ? 300 : 100;

                            try {
                                Thread.sleep(sleep);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                        subscriber.onCompleted();
                    }
                })
                .throttleWithTimeout(200, TimeUnit.MILLISECONDS)
                .compose(bindToLifecycle())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.d("onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d(e.toString());
                    }

                    @Override
                    public void onNext(Integer integer) {
                        LogUtil.d(integer);
                    }
                });

    }


}
