package com.example.testing.rxjavalearn.operators;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.testing.rxjavalearn.util.LogUtil;

import java.util.concurrent.TimeUnit;

import rx.Notification;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;

/**
 * auther: baiiu
 * time: 16/6/9 09 09:47
 * description:
 */
public class UtilityOperatorsFragment extends BaseFragment {
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        delay();
//        delaySubscription();

//        doOperator();


//        materialize();
//        dematerialize();

//        timeInterval();
//        timeStamp();

//        timeOut();

        using();
    }

    /**
     * Using操作符创建一个在Observable生命周期内存活的资源，也可以这样理解：
     * 我们创建一个资源并使用它，用一个Observable来限制这个资源的使用时间，当这个Observable终止的时候，这个资源就会被销毁。
     * <p>
     * Using需要使用三个参数，分别是：
     * <p>
     * 1)创建这个一次性资源的函数
     * 2)创建Observable的函数
     * 3)释放资源的函数
     */
    private void using() {
        Observable
                .using(new Func0<Animal>() {
                    @Override
                    public Animal call() {
                        return new Animal();
                    }
                }, new Func1<Object, Observable<Long>>() {
                    @Override
                    public Observable<Long> call(Object o) {
                        //限制5秒
                        return Observable.timer(5000, TimeUnit.MILLISECONDS);
                    }
                }, new Action1<Animal>() {
                    @Override
                    public void call(Animal animal) {
                        animal.relase();
                    }
                })
                //.compose(bindToLifecycle())
                .subscribe(getSubscriber());
    }

    private class Animal {
        Subscriber subscriber = new Subscriber() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Object o) {
                LogUtil.d("animal eat");
            }
        };

        public Animal() {
            LogUtil.d("create animal");
            //每隔1秒吃一次
            Observable
                    .interval(1000, TimeUnit.MILLISECONDS)
                    .subscribe(subscriber);
        }

        public void relase() {
            LogUtil.d("animal released");
            subscriber.unsubscribe();
        }
    }

    /**
     * Timeout操作符给Observable加上超时时间，每发射一个数据后就重置计时器，当超过预定的时间还没有发射下一个数据，就抛出一个超时的异常。
     */
    private void timeOut() {

        Observable
                .create(new Observable.OnSubscribe<Integer>() {
                    @Override
                    public void call(Subscriber<? super Integer> subscriber) {
                        for (int i = 0; i < 5; ++i) {
                            subscriber.onNext(i);

                            try {
                                Thread.sleep(100 * i);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                        subscriber.onCompleted();
                    }
                })
                //.compose(bindToLifecycle())
                .timeout(100, TimeUnit.MILLISECONDS)
                //Rxjava将Timeout实现为很多不同功能的操作符，比如说超时后用一个备用的Observable继续发射数据等。
//                .timeout(100,TimeUnit.MILLISECONDS,Observable.just(10,11))
                .subscribe(getSubscriber());
    }


    /**
     * TimeStamp会将每个数据项给重新包装一下，加上了一个时间戳来标明每次发射的时间
     */
    private void timeStamp() {
        Observable
                .range(0, 10)
                //.compose(bindToLifecycle())
                .timestamp()
                .subscribe(integerTimestamped -> {
                    LogUtil.d(integerTimestamped.getValue() + ", " + integerTimestamped.getTimestampMillis());
                }, throwable -> LogUtil.e(throwable.toString()), () -> LogUtil.d("onComplete"));
    }

    /**
     * TimeInterval会拦截发射出来的数据，取代为前后两个发射两个数据的间隔时间。对于第一个发射的数据，其时间间隔为订阅后到首次发射的间隔。
     */
    private void timeInterval() {
        Observable
                .range(0, 10)
                //.compose(bindToLifecycle())
                .timeInterval()
                .subscribe(integerTimeInterval -> {
                    LogUtil.d(integerTimeInterval.getValue() + ", " + integerTimeInterval.getIntervalInMilliseconds());
                }, throwable -> LogUtil.e(throwable.toString()), () -> LogUtil.d("onComplete"));
    }

    private void dematerialize() {
        Observable
                .just(1, 2, 3)
                //.compose(bindToLifecycle())
                .materialize()
                .dematerialize()
                .subscribe(getSubscriber());
    }

    /**
     * Meterialize操作符将OnNext/OnError/OnComplete都转化为一个Notification对象并按照原来的顺序发射出来
     */
    private void materialize() {
        Observable
                .just(1, 2, 3)
                //.compose(bindToLifecycle())
                .materialize()
                .subscribe(new Subscriber<Notification<Integer>>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.d("onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e(e.toString());
                    }

                    @Override
                    public void onNext(Notification<Integer> integerNotification) {
                        LogUtil.d("onNext" + integerNotification.getValue() + ", " + integerNotification.getKind());
                    }
                });
    }

    /**
     * Do操作符就是给Observable的生命周期的各个阶段加上一系列的回调监听，
     * 当Observable执行到这个阶段的时候，这些回调就会被触发。
     */
    private void doOperator() {
        Observable
                .just(1, 2, 3)
                //.compose(bindUntilEvent(FragmentEvent.DESTROY))
                .doOnEach(notification -> LogUtil.d("doOnEach"))
                .doOnSubscribe(() -> LogUtil.d("doOnSubscribe"))
                .doOnUnsubscribe(() -> LogUtil.d("doOnUnSubscribe"))
                .doOnCompleted(() -> LogUtil.d("doOnCompleted"))
                .doOnError(Throwable::printStackTrace)
                .doOnTerminate(() -> LogUtil.d("doOnTerminate"))
                .doOnNext(integer -> LogUtil.d("doOnNext" + integer))
                .finallyDo(() -> LogUtil.d("finallyDo"))
                .subscribe(getSubscriber());
    }

    /**
     * 延迟注册
     */
    private void delaySubscription() {
        Observable
                .range(5, 3)
                //.compose(bindToLifecycle())
                .delaySubscription(2, TimeUnit.SECONDS)
                .subscribe(getSubscriber());
    }

    /**
     * 延迟发射数据
     */
    private void delay() {
        Observable
                .just(1, 2)
                //.compose(bindToLifecycle())
                .delay(2, TimeUnit.SECONDS)
                .subscribe(getSubscriber());
    }
}
