package com.example.testing.rxjavalearn.operators;

import android.os.Bundle;
import android.support.annotation.Nullable;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Func1;

/**
 * auther: baiiu
 * time: 16/6/9 09 11:53
 * description: 条件布尔操作符
 */
public class ConditionalBooleanOperatorsFragment extends BaseFragment {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        all();
//        amb();

//        contains();
//        isEmpty();
//        defaultIsEmpty();

//        sequenceEqual();

//        skipUtil();
//        skipWhile();

//        takeUtil();
        takeWhile();

    }

    /**
     * 和SkipWhile相反
     *
     * 获取满足skipWhile的数据
     */
    private void takeWhile() {
        Observable
                .range(0, 10)
                .takeWhile(new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer integer) {
                        return integer < 5;
                    }
                })
                .compose(bindToLifecycle())
                .subscribe(getSubscriber());

    }

    /**
     * 和SkipUtil恰好相反
     * <p>
     * 发射直到takeUntil里的Observable之前的数据
     */
    private void takeUtil() {
        Observable
                .merge(
                        Observable.range(0, 10),
                        Observable.just(5),
                        Observable.interval(1, TimeUnit.SECONDS)
                )
                .takeUntil(Observable.timer(5, TimeUnit.SECONDS))
                .compose(bindToLifecycle())
                .subscribe(getSubscriber());

    }

    /**
     * SkipWhile则是根据一个函数来判断是否跳过数据，当函数返回值为true的时候则一直跳过源Observable发射的数据；当函数返回false的时候则开始正常发射数据。
     */
    private void skipWhile() {
        Observable
                .range(0, 10)
                .skipWhile(new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer integer) {
                        return integer < 5;
                    }
                })
                .compose(bindToLifecycle())
                .subscribe(getSubscriber());
    }

    /**
     * SkipUnitl是根据一个标志Observable来判断的，当这个标志Observable没有发射数据的时候，
     * 所有源Observable发射的数据都会被跳过；当标志Observable发射了一个数据，则开始正常地发射数据。
     * <p>
     * 一直等到skipUntil发射了数据才能发射源Observable的数据
     */
    private void skipUtil() {
        Observable
                .merge(
                        Observable.range(0, 10),
                        Observable.just(5),
                        Observable.interval(1, TimeUnit.SECONDS)
                )
                .skipUntil(Observable.timer(5, TimeUnit.SECONDS))
                .compose(bindToLifecycle())
                .subscribe(getSubscriber());
    }

    /**
     * SequenceEqual操作符用来判断两个Observable发射的数据序列是否相同（发射的数据相同，数据的序列相同，结束的状态相同），如果相同返回true，否则返回false
     */
    private void sequenceEqual() {
        Observable
                .sequenceEqual(
                        Observable.just(1, 2, 3),
//                        Observable.just(1, 2, 3)
                        Observable.just(1, 2)
                )
                .compose(bindToLifecycle())
                .subscribe(getSubscriber());
    }

    /**
     * DefaultIfEmpty操作符会判断源Observable是否发射数据，
     * 如果源Observable发射了数据则正常发射这些数据，如果没有则发射一个默认的数据
     */
    private void defaultIsEmpty() {
        Observable
                .create(subscriber -> subscriber.onCompleted())
                .defaultIfEmpty("default")
                .compose(bindToLifecycle())
                .subscribe(getSubscriber());
    }

    /**
     * IsEmpty操作符用来判断源Observable是否发射过数据.
     * 没发射过返回true
     */
    private void isEmpty() {
        Observable
                .create(subscriber -> {
                    subscriber.onCompleted();
                })
                .compose(bindToLifecycle())
                .isEmpty()
                .subscribe(getSubscriber());
    }

    /**
     * Contains操作符用来判断源Observable所发射的数据是否包含某一个数据，
     * 如果包含会返回true，如果源Observable已经结束了却还没有发射这个数据则返回false。
     */
    private void contains() {
        Observable
                .just(1, 2, 3)
                .compose(bindToLifecycle())
                .contains(3)
                .subscribe(getSubscriber());

    }

    /**
     * Amb操作符可以将至多9个Observable结合起来，让他们竞争。
     * 哪个Observable首先发射了数据（包括onError和onComplete)就会继续发射这个Observable的数据，其他的Observable所发射的数据都会别丢弃。
     */
    private void amb() {
        Observable
                .amb(
                        Observable.just(1, 2).delay(1000, TimeUnit.MILLISECONDS),
                        Observable.just(3, 4).delay(2000, TimeUnit.MILLISECONDS),
                        Observable.just(5, 6).delay(100, TimeUnit.MILLISECONDS))
                .compose(bindToLifecycle())
                .subscribe(getSubscriber());
    }

    /**
     * All操作符根据一个函数对源Observable发射的所有数据进行判断，最终返回的结果就是这个判断结果。
     * <p>
     * 对发射的所有数据应用这个函数,如果全部都满足则返回true，否则就返回false。
     */
    private void all() {
        Observable
                .range(0, 10)
                .all(integer -> integer < 8)
                .compose(bindToLifecycle())
                .subscribe(getSubscriber());
    }

}
