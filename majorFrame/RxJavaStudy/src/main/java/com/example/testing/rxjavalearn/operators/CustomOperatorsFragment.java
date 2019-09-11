package com.example.testing.rxjavalearn.operators;

import android.os.Bundle;
import com.trello.rxlifecycle.android.FragmentEvent;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * author: baiiu
 * date: on 16/7/15 16:10
 * description: 自定义操作符
 */
public class CustomOperatorsFragment extends BaseFragment {


    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //lift();

        compose();
    }

    /**
     * Compose操作符是将源Observable按照自定义的方式转化成另外一个新的Observable
     */
    private void compose() {
        Observable.error(new NullPointerException())
                .compose(mExceptionTransformer)
                //.compose(bindToLifecycle())
                .subscribe(getSubscriber());
    }

    /**
     * 该Observerable发生错误时发射null数据
     */
    Observable.Transformer<Object, Object> mExceptionTransformer =
            objectObservable -> objectObservable.onErrorResumeNext(
                    (Func1<Throwable, Observable<?>>) throwable -> Observable.just(null));


    /**
     * 使用Lift 自定义操作符
     */
    private void lift() {
        Observable.range(1, 5)
                .compose(bindUntilEvent(FragmentEvent.DESTROY))
                .lift(mCustomOperator)
                .subscribe(getSubscriber());
    }

    Observable.Operator<String, Integer> mCustomOperator = subscriber -> new Subscriber<Integer>() {
        @Override public void onCompleted() {
            if (!subscriber.isUnsubscribed()) {
                subscriber.onCompleted();
            }
        }

        @Override public void onError(Throwable e) {
            if (!subscriber.isUnsubscribed()) {
                subscriber.onNext("error");
            }
        }

        @Override public void onNext(Integer integer) {
            if (!subscriber.isUnsubscribed()) {
                subscriber.onNext("Custom Operator: " + integer);
            }
        }
    };

}
