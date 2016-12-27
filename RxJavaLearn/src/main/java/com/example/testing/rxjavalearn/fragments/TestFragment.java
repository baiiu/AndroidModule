package com.example.testing.rxjavalearn.fragments;

import android.os.Bundle;
import com.example.testing.rxjavalearn.operators.BaseFragment;
import com.example.testing.rxjavalearn.util.LogUtil;
import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.subscriptions.CompositeSubscription;

/**
 * author: baiiu
 * date: on 16/6/7 17:02
 * description:
 */
public class TestFragment extends BaseFragment {

    //一个Subscription在执行完后就unsubscribe释放自己了,不再存进CompositeSubscription中
    CompositeSubscription mCompositeSubscription = new CompositeSubscription();

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //flatmapTest();
        testOne();

        //filterTest();
        //firstTest();

        //zipTest();
        //zipTest2();
        //filterZip();
        //mapOccureError();

        //anotherOne();
    }

    //func中抛出一次
    private void testOne() {
        Observable.just(null)
                .map(o -> {
                    throw new NullPointerException("为空");
                })
                .subscribe(getSubscriber());
    }

    /*
        flatmap: 第二个报出异常,直接error
        有错误直接中断
     */
    private void flatmapTest() {
        //Observable.just("1", "2", "3", "....")
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override public void call(Subscriber<? super String> subscriber) {
                for (int i = 0; i < 10; ++i) {
                    subscriber.onNext(String.valueOf(i));

                    if (i == 4) {
                        throw new IllegalStateException("错误");
                    }
                }
            }
        })
                .flatMap(new Func1<String, Observable<Integer>>() {
                    @Override public Observable<Integer> call(String s) {
                        return Observable.just(Integer.parseInt(s));
                    }
                })
                .subscribe(getSubscriber());
    }

    private void anotherOne() {
        mCompositeSubscription.add(Observable.interval(1, TimeUnit.SECONDS)
                                           .subscribe(getSubscriber()));
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        LogUtil.d("" + mCompositeSubscription.hasSubscriptions());
        if (mCompositeSubscription != null
                && !mCompositeSubscription.isUnsubscribed()
                && mCompositeSubscription.hasSubscriptions()) {

            mCompositeSubscription.unsubscribe();
        }
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


        Observable.zip(just, string1, (integer, s) -> String.valueOf(integer) + ", " + s)
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
        mCompositeSubscription.add(Observable.concat(Observable.just(null), Observable.range(0, 10))
                                           .first(integer -> integer != null && integer > 8)
                                           .subscribe(getSubscriber()));
    }

    /**
     * filter reture false后直接回调onComplete,不会走onError.
     */
    private void filterTest() {
        Integer[] ints = new Integer[] { 1, 2, 3, 4 };

        mCompositeSubscription.add(Observable.from(ints)
                                           .filter(integer -> integer < 3)
                                           .subscribe(getSubscriber()));

    }
}
