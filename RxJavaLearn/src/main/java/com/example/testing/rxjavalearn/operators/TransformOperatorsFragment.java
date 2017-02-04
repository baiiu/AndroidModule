package com.example.testing.rxjavalearn.operators;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.testing.rxjavalearn.R;
import com.example.testing.rxjavalearn.util.LogUtil;
import com.jakewharton.rxbinding.view.RxView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.observables.GroupedObservable;

/**
 * auther: baiiu
 * time: 16/6/7 07 22:25
 * description: 变换操作符
 */
public class TransformOperatorsFragment extends BaseFragment {

    @BindView(R.id.bt_create)
    Button bt_create;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create, container, false);
        ButterKnife.bind(this, view);
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        buffer();
//        bufferTime();
//        bufferClick();

//        flatMap();
//        concatMap();
//        flaMapIterable();

//        groupBy();

//        map();
//        cast();


//        scan();
        windowByInt();
    }

    /**
     * window 定期将来自原始Observable的数据分解为一个Observable窗口,发送这些窗口,这些窗口是一个Observable.
     * buffer发送的是一些数据集合
     */
    private void windowByInt() {
        Observable
                .range(0, 10)
                //.compose(bindToLifecycle())
                .window(2, 3)
                .subscribe(new Action1<Observable<Integer>>() {
                    @Override
                    public void call(Observable<Integer> integerObservable) {
                        LogUtil.d("a new window emit: " + integerObservable.toString());

                        integerObservable.subscribe(new Action1<Integer>() {
                            @Override
                            public void call(Integer integer) {
                                LogUtil.d("window: " + integer);
                            }
                        });

                    }
                }, e -> LogUtil.d(e.toString()));
    }

    /**
     * 连续地对数据序列的每一项应用一个函数，然后连续发射结果,每一项结果基于之前的结果
     */
    private void scan() {
        Observable
                .range(0, 10)
                //.compose(bindToLifecycle())
                .scan(new Func2<Integer, Integer, Integer>() {
                    @Override
                    public Integer call(Integer integer, Integer integer2) {
                        return integer + integer2;
                    }
                })
                .subscribe(LogUtil::d, e -> LogUtil.d(e.toString()));
    }

    /**
     * 强转为另外一种类型,map的具体实现
     */
    private void cast() {
        Observable
                .just(getMan())
                .doOnNext(person -> LogUtil.d("doOnNext: " + person.getClass().toString()))
                //.compose(bindToLifecycle())
                .cast(Man.class)
                .subscribe(man -> {
                    LogUtil.d("subscriber: " + man + ", " + man.getClass().toString());
                }, e -> LogUtil.d(e.toString()));
    }

    private Person getMan() {
        return new Man();
    }

    public static class Person {
        public Person() {
            LogUtil.d("constructor --> person");
        }
    }

    public static class Man extends Person {
        public Man() {
            LogUtil.d("constructor --> man");
        }
    }

    /**
     * 转换,flatmap是转换为observable
     */
    private void map() {
        Observable
                .range(2, 5)
                //.compose(bindToLifecycle())
                .map(new Func1<Integer, String>() {
                    @Override
                    public String call(Integer integer) {
                        return "map " + integer;
                    }
                })
                .subscribe(LogUtil::d);
    }


    /**
     * 分组操作符.
     * 传入的Func1为分组条件,生成key.
     */
    private void groupBy() {
        Observable
                .range(1, 20)
                //.compose(bindToLifecycle())
                .groupBy(new Func1<Integer, Integer>() {
                    @Override
                    public Integer call(Integer integer) {
                        //分组条件
                        return integer % 2;
                    }
                })
                .subscribe(new Action1<GroupedObservable<Integer, Integer>>() {
                    @Override
                    public void call(GroupedObservable<Integer, Integer> integerIntegerGroupedObservable) {

                        //再次订阅
                        integerIntegerGroupedObservable.subscribe(integer -> {
                            LogUtil.d("key = " + integerIntegerGroupedObservable.getKey() + ", value= " + integer);
                        });

                    }
                });

    }


    /**
     * 转换为Iterable
     */
    private void flaMapIterable() {
        Observable
                .range(3, 9)
                //.compose(bindToLifecycle())
                .flatMapIterable(new Func1<Integer, Iterable<String>>() {
                    @Override
                    public Iterable<String> call(Integer integer) {
                        List<String> list = new ArrayList<String>();
                        for (int i = 0; i < integer; ++i) {
                            list.add("Iterable " + i);
                        }
                        return list;
                    }
                })
                .subscribe(LogUtil::d);

    }

    /**
     * 链接的是有序的,flatmap是merge操作,不保证有序
     */
    private void concatMap() {
        Observable
                .range(0, 10)
                //.compose(bindToLifecycle())
                .concatMap(new Func1<Integer, Observable<String>>() {
                    @Override
                    public Observable<String> call(Integer integer) {
                        return Observable.just("item " + integer);
                    }
                })
                .subscribe(LogUtil::d);
    }

    /**
     * 将一个对象转换为Observable,常用于嵌套请求
     */
    private void flatMap() {
        Observable
                .range(2, 10)
                //.compose(bindToLifecycle())
                .flatMap(integer -> Observable.just("Item: " + integer))
                .subscribe(LogUtil::d);

    }

    private void bufferClick() {
        RxView.clicks(bt_create)
                //.compose(bindToLifecycle())
                .buffer(7)
                .subscribe(voids -> {
                    Toast.makeText(getContext(), "点了7次,开启XXX模式", Toast.LENGTH_SHORT).show();
                });
    }

    private void bufferTime() {
        Observable.interval(1, TimeUnit.SECONDS)
                .buffer(3, TimeUnit.SECONDS)
                //.compose(bindToLifecycle())
                .subscribe(longs -> {
                    LogUtil.d(longs.toString());
                });

    }


    /**
     * buffer操作符,将数据缓存成一个集合然后发射出去
     * 看源码可是buffer(2)调用了buffer(2,2).
     * <p>
     * 缓存skip个,即创建size() = skip的集合,并取前count个.
     */
    private void buffer() {
        Observable.range(0, 11)
                //.compose(bindToLifecycle())
                .buffer(2, 5)
                .subscribe(integers -> {
                    LogUtil.d(integers.toString());
                });
    }


}
