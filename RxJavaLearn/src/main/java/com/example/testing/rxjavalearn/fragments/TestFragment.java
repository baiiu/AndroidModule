package com.example.testing.rxjavalearn.fragments;

import android.os.Bundle;
import com.example.testing.rxjavalearn.operators.BaseFragment;
import com.example.testing.rxjavalearn.util.LogUtil;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func2;

/**
 * author: baiiu
 * date: on 16/6/7 17:02
 * description:
 */
public class TestFragment extends BaseFragment {

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //filterTest();
        //firstTest();

        //zipTest();
        //zipTest2();
        //filterZip();
        mapOccureError();
    }

    private void mapOccureError() {
        Observable.just(1, 2, 3)
                .map(integer -> {
                    if (integer == 2) {
                        throw new RuntimeException("错误2");
                    }

                    return String.valueOf(integer) + "哈哈";
                })
                .subscribe(getSubscriber());

    }

    private void filterZip() {

        Observable<Integer> filter = Observable.just(2, 6)
                .filter(integer -> integer < 2);

        Observable<Integer> just = Observable.just(1);

        Observable.zip(filter, just, (integer, integer2) -> String.valueOf(integer) + ", " + integer2)
                .subscribe(getSubscriber());

    }

    private void zipTest2() {


        Observable<Integer> just = Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override public void call(Subscriber<? super Integer> subscriber) {
                subscriber.onError(new IllegalStateException("报个错"));
            }
        });

        Observable<String> string1 = Observable.just("String1");


        Observable.zip(just, string1, new Func2<Integer, String, String>() {
            @Override public String call(Integer integer, String s) {
                return String.valueOf(integer) + ", " + s;
            }
        })
                .onErrorResumeNext(throwable -> {
                    //直接走到null,不会对单个接口处理
                    return Observable.just(null);

                    //拦截onError
                    //return Observable.just(22);

                    //使用这个时直接调用onComplete(),onNext()并不会走
                    //return Observable.empty();
                })
                .subscribe(getSubscriber());
    }

    private void zipTest() {

        Observable<Integer> just = Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override public void call(Subscriber<? super Integer> subscriber) {
                subscriber.onError(new IllegalStateException("报个错"));
            }
        })
                .onErrorResumeNext(throwable -> {

                    return Observable.just(null);

                    //拦截onError
                    //return Observable.just(22);

                    //使用这个时直接调用onComplete(),onNext()并不会走
                    //return Observable.empty();
                });

        Observable<String> string1 = Observable.just("String1");


        Observable.zip(just, string1, new Func2<Integer, String, String>() {
            @Override public String call(Integer integer, String s) {
                return String.valueOf(integer) + ", " + s;
            }
        })
                .subscribe(getSubscriber());

    }

    /**
     * 取满足条件的第一个,如无满足条件数据抛出异常.有null数据时会抛空指针异常,要判空处理
     * <p>
     * <p>
     * takeFirst() 只会调用onComplete
     */
    private void firstTest() {
        Observable.concat(Observable.just(null), Observable.range(0, 8))
                .first(integer -> integer != null && integer > 8)
                .subscribe(getSubscriber());
    }

    /**
     * filter reture false后直接回调onComplete,不会走onError.
     */
    private void filterTest() {
        Integer[] ints = new Integer[] { 1, 2, 3, 4 };

        Observable.from(ints)
                .filter(integer -> integer < 3)
                .subscribe(new Subscriber<Integer>() {
                    @Override public void onCompleted() {
                        LogUtil.d("onComplete");
                    }

                    @Override public void onError(Throwable e) {
                        LogUtil.e(e.toString());
                    }

                    @Override public void onNext(Integer integer) {
                        LogUtil.d(integer);
                    }
                });

    }
}
