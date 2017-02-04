package com.example.testing.rxjavalearn.operators;

import android.os.Bundle;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Action2;
import rx.functions.Func0;
import rx.functions.Func2;

/**
 * auther: baiiu
 * time: 16/6/9 09 12:49
 * description:
 */
public class AggregateOperatorsFragment extends BaseFragment {
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        concat();

//        count();
//        count2();

//        reduce();
        collect();

    }

    /**
     * collect用来将源Observable发射的数据给收集到一个数据结构里面，需要使用两个参数：
     * 1. 一个产生收集数据结构的函数
     * 2. 一个接收第一个函数产生的数据结构和源Observable发射的数据作为参数的函数。
     */
    private void collect() {
        Observable
                .range(0, 10)
                .collect(new Func0<List<Integer>>() {
                    @Override
                    public List<Integer> call() {
                        return new ArrayList<Integer>();
                    }
                }, new Action2<List<Integer>, Integer>() {
                    @Override
                    public void call(List<Integer> o, Integer integer) {
                        o.add(integer);
                    }
                })
                //.compose(bindToLifecycle())
                .subscribe(getSubscriber());
    }

    /**
     * Reduce操作符应用一个函数接收Observable发射的数据和函数的计算结果作为下次计算的参数，输出最后的结果。跟前面我们了解过的scan操作符很类似，只是scan会输出每次计算的结果，而reduce只会输出最后的结果。
     */
    private void reduce() {
        Observable
                .range(0, 10)
                //换成scan也可以,不过会输出每次的值
                .reduce(new Func2<Integer, Integer, Integer>() {
                    @Override
                    public Integer call(Integer integer, Integer integer2) {
                        return integer + integer2;
                    }
                })
                //.compose(bindToLifecycle())
                .subscribe(getSubscriber());
    }

    private void count2() {
        Observable
                .create(subscriber -> {
                    for (int i = 0; i < 10; ++i) {
                        subscriber.onNext(i);

                        if (i == 9) {
                            subscriber.onError(new Throwable("onError"));
                        }
                    }
                    subscriber.onCompleted();
                })
                //.compose(bindToLifecycle())
                .count()
                .subscribe(getSubscriber());
    }

    /**
     * Count操作符用来统计源Observable发射了多少个数据，最后将数目给发射出来；
     * 如果源Observable发射错误，则会将错误直接报出来；
     * 在源Observable没有终止前，count是不会发射统计数据的。
     */
    private void count() {
        Observable
                .just(1, 2, 3)
                //.compose(bindToLifecycle())
                .count()
                .subscribe(getSubscriber());
    }

    /**
     * Concat操作符将多个Observable结合成一个Observable并发射数据，并且严格按照先后顺序发射数据，
     * 前一个Observable的数据没有发射完，是不能发射后面Observable的数据的。
     */
    private void concat() {
        Observable
                .concat(
                        Observable.just(1, 2),
                        Observable.range(4, 5)
                )
                //.compose(bindToLifecycle())
                .subscribe(getSubscriber());
    }


}
