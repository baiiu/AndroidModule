package com.example.testing.rxjavalearn.operators;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.testing.rxjavalearn.util.LogUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.functions.FuncN;
import rx.schedulers.Schedulers;

/**
 * auther: baiiu
 * time: 16/6/8 08 23:11
 * description: 组合操作符
 */
public class CombiningOperatorsFragment extends BaseFragment {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        combineLatest();

//        combineLatestTwo();
//        combineLatestList();

//        join();

//        merge();
//        mergeDelayError();


//        startWith();
//        switchOnNext();

//        zip();
//        zipTwo();
        zipWith();
    }

    private void zipWith() {
        getIndexObservable(2)
                .zipWith(getIndexObservable(3), (s, s2) -> s + "--------" + s2)
                //.compose(bindToLifecycle())
                .subscribe(getSubscriber());
    }

    private void zipTwo() {
        Observable
                .zip(getIndexObservable(2), getIndexObservable(3), (s, s2) -> s + "--------" + s2)
                //.compose(bindToLifecycle())
                .subscribe(getSubscriber());

    }

    /**
     * Zip操作符将多个Observable发射的数据按顺序组合起来，每个数据只能组合一次，而且都是有序的。
     * 最终组合的数据的数量由发射数据最少的Observable来决定。
     */
    private void zip() {
        Observable
                .zip(Observable.just(1, 2, 3), Observable.just(4, 5), (integer, integer2) -> integer + ", " + integer2)
                //.compose(bindToLifecycle())
                .subscribe(getSubscriber());
    }

    /**
     * 用来将一个发射多个小Observable的源Observable转化为一个Observable，然后发射这多个小Observable所发射的数据。
     * 需要注意的就是，如果一个小的Observable正在发射数据的时候，源Observable又发射出一个新的小Observable，则前一个Observable发射的数据会被抛弃，直接发射新
     * 的小Observable所发射的数据。
     */
    private void switchOnNext() {
        Observable
                .switchOnNext(
                        //创建一系列observable
                        Observable.create(subscriber -> {
                            for (int i = 1; i < 3; i++) {
                                subscriber.onNext(getIndexObservable(i));
                                try {
                                    Thread.sleep(2000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }))
                //.compose(bindToLifecycle())
                .subscribe(getSubscriber());
    }

    private Observable<String> getIndexObservable(int index) {
        return Observable
                .create((Observable.OnSubscribe<String>) subscriber -> {
                    for (int i = 0; i < 5; i++) {
                        subscriber.onNext(index + "-" + i);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                })
                //在不同线程计时
                .subscribeOn(Schedulers.computation())
                ;
    }

    /**
     * 在数据序列的开头插入一条指定的项
     * <p>
     * 你也可以传递一个Observable给startWith，
     * 它会将那个Observable的发射物插在原始Observable发射的数据序列之前.这可以看作是Concat的反转。
     */
    private void startWith() {
        Observable
                .just(1, 2, 3)
                .startWith(-1, 0)
                //.compose(bindToLifecycle())
                .subscribe(getSubscriber());
    }

    /**
     * 当某一个Observable发出onError的时候，merge的过程会被停止并将错误分发给Subscriber，
     * 如果不想让错误终止merge的过程，可以使用MeregeDelayError操作符，会将错误在merge结束后再分发。
     * <p>
     * 延迟错误
     */
    private void mergeDelayError() {
        Observable<Object> objectObservable = Observable.create(subscriber -> {
            for (int i = 0; i < 10; ++i) {
                if (i == 5) {
                    subscriber.onError(new Throwable("on Error"));
                    continue;
                }

                subscriber.onNext(i);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Observable
//                .merge(objectObservable, Observable.range(10, 10))
                .mergeDelayError(objectObservable, Observable.range(10, 10))
                //.compose(bindToLifecycle())
                .subscribe(getSubscriber());


    }


    /**
     * 使用Merge操作符你可以将多个Observables的输出合并，就好像它们是一个单个的Observable一样。
     * Merge可能会让合并的Observables发射的数据交错（有一个类似的操作符Concat不会让数据交错，它会按顺序一个接着一个发射多个Observables的发射物）。
     */
    private void merge() {
        Observable<Integer> just = Observable.range(0, 10);
        Observable<Integer> just1 = Observable.range(10, 10);

        Observable
                .merge(just, just1)
                //.compose(bindToLifecycle())
                .subscribe(getSubscriber());
    }

    /**
     * 参数说明:
     * <p>
     * 1. 源Observable所要组合的目标Observable
     * 2. 一个函数，就收从源Observable发射来的数据，并返回一个Observable，这个Observable的生命周期决定了源Observable发射出来数据的有效期
     * 3. 一个函数，就收从目标Observable发射来的数据，并返回一个Observable，这个Observable的生命周期决定了目标Observable发射出来数据的有效期
     * 4. 一个函数，接收从源Observable和目标Observable发射来的数据，并返回最终组合完的数据。
     */
    private void join() {

        Observable<String> rightObservable = Observable.create(subscriber -> {
            for (int i = 0; i < 5; ++i) {
                subscriber.onNext("right: " + i);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });

        Observable
                //源Observable
                .just("-Left")

                .join(
                        //目标Observable
                        rightObservable,

                        //这个Observable的生命周期决定了源Observable发射出来数据的有效期
                        new Func1<String, Observable<Long>>() {
                            @Override
                            public Observable<Long> call(String s) {
                                return Observable.interval(3000, TimeUnit.MILLISECONDS);
                            }
                        },

                        //这个Observable的生命周期决定了目标Observable发射出来数据的有效期
                        new Func1<String, Observable<Long>>() {
                            @Override
                            public Observable<Long> call(String s) {
                                return Observable.interval(2000, TimeUnit.MILLISECONDS);
                            }
                        },

                        //4. 一个函数，接收从源Observable和目标Observable发射来的数据，并返回最终组合完的数据。
                        new Func2<String, String, String>() {
                            @Override
                            public String call(String s, String s2) {
                                return s + ", " + s2;
                            }
                        })
                //.compose(bindToLifecycle())
                .subscribe(getSubscriber());


    }

    private void combineLatestList() {
        List<Observable<Integer>> list = new ArrayList<>();
        list.add(createObservable(1));
        list.add(createObservable(2));
        list.add(createObservable(3));

        Observable
                .combineLatest(list, new FuncN<Integer>() {
                    @Override
                    public Integer call(Object... args) {
                        int sum = 0;
                        for (int i = 0; i < args.length; ++i) {
                            sum += i;
                        }
                        return sum;
                    }
                })
                //.compose(bindToLifecycle())
                .subscribe(getSubscriber());
    }

    private void combineLatestTwo() {
        Observable
                .combineLatest(createObservable(1), createObservable(2), new Func2<Integer, Integer, Integer>() {
                    @Override
                    public Integer call(Integer integer, Integer integer2) {
                        LogUtil.d(integer + ", " + integer2);
                        return integer + integer2;
                    }
                })
                //.compose(bindToLifecycle())
                .subscribe(getSubscriber());
    }

    private Observable<Integer> createObservable(int index) {
        return Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                for (int i = 0; i < 5; ++i) {
                    subscriber.onNext(i * index);

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }


    /**
     * 满足两个条件:
     * <p>
     * 1. 所有的Observable都发射过数据。
     * 2. 满足条件1的时候任何一个Observable发射一个数据，就将所有Observable最新发射的数据按照提供的函数组装起来发射出去。
     * <p>
     * 会忽略一些之前发射的数据,只取最近发射的
     * <p>
     * 这个示例中只能等到range开发发射数据才能满足第一个条件
     */
    private void combineLatest() {
        Observable<Integer> just = Observable.just(1, 2);
//        Observable<Integer> range = Observable.range(3, 2);
        Observable<Integer> range = Observable.range(3, 2);

        Observable
                .combineLatest(just, range, new Func2<Integer, Integer, Integer>() {
                    @Override
                    public Integer call(Integer integer, Integer integer2) {
                        LogUtil.d(integer + ", " + integer2);
                        return integer + integer2;
                    }
                })
                //.compose(bindToLifecycle())
                .subscribe(getSubscriber());
    }


}
