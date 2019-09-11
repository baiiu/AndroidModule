package com.example.testing.rxjavalearn.operators;

import android.os.Bundle;
import com.example.testing.rxjavalearn.util.LogUtil;
import rx.subjects.AsyncSubject;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;
import rx.subjects.ReplaySubject;
import rx.subjects.Subject;

/**
 * auther: baiiu
 * time: 16/7/16 16 10:42
 * description:
 */
public class SubjectFragment extends BaseFragment {

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //publishSubject();

        //ReplaySubject();

        //behaviorSubject();

        asyncSubject();
    }

    /**
     * AsyncSubject 也缓存最后一个数据。区别是
     * AsyncSubject 只有当数据发送完成时（onCompleted 调用的时候）才发射这个缓存的最后一个数据。
     * 可以使用 AsyncSubject 发射一个数据并立刻结束。
     */
    private void asyncSubject() {
        onProvideSubject(AsyncSubject.create());
    }

    /**
     * BehaviorSubject 只保留最后一个值。
     * 等同于限制 ReplaySubject 的个数为 1 的情况。在创建的时候可以指定一个初始值，这样可以确保党订阅者订阅的时候可以立刻收到一个值。
     */
    private void behaviorSubject() {
        onProvideSubject(BehaviorSubject.create("-1"));
    }


    /**
     * ReplaySubject 可以缓存所有发射给他的数据。
     * 当一个新的订阅者订阅的时候，缓存的所有数据都会发射给这个订阅者。 由于使用了缓存，所以每个订阅者都会收到所以的数据.
     */
    private void ReplaySubject() {
        onProvideSubject(ReplaySubject.createWithSize(2));
    }

    /**
     * PublishSubject 是最直接的一个 Subject。当一个数据发射到 PublishSubject 中时，PublishSubject 将立刻把这个数据发射到订阅到该 subject
     * 上的所有subscriber 中。
     */
    private void publishSubject() {
        onProvideSubject(PublishSubject.create());
    }

    private void onProvideSubject(Subject<String, String> subject) {
        subject.onNext("0");
        subject.subscribe(s -> LogUtil.d("one " + s), Throwable::toString,
                          () -> LogUtil.d("one onComplete"));
        subject.onNext("1");
        subject.onNext("2");
        subject.subscribe(s -> LogUtil.d("two " + s), Throwable::toString,
                          () -> LogUtil.d("two onComplete"));
        subject.onNext("3");
        subject.onCompleted();
    }

}

