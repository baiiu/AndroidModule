package com.example.testing.rxjavalearn.operators;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import com.example.testing.rxjavalearn.util.LogUtil;
import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.observables.ConnectableObservable;

/**
 * auther: baiiu
 * time: 16/6/9 09 13:11
 * description: 连接操作符
 */
public class ConnectOperatorsFragment extends BaseFragment {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        connect();
//        refCount();

        replay();
    }

    /**
     * Replay操作符返回一个Connectable Observable 对象并且可以缓存其发射过的数据，这样即使有订阅者在其发射数据之后进行订阅也能收到其之前发射过的数据。
     * 不过使用Replay操作符我们最好还是限定其缓存的大小，否则缓存的数据太多了可会占用很大的一块内存。
     * 对缓存的控制可以从空间和时间两个方面来实现。
     */
    private void replay() {
        ConnectableObservable<Long> replay = Observable
                .interval(1, TimeUnit.SECONDS)
                .compose(bindToLifecycle())
                //缓存10个数据,subscriber1订阅时把过去的全部接收到了
//                .replay(10);
                //缓存过去10秒数据,subscriber1订阅时把过去的全部接收到了
                .replay(10, TimeUnit.SECONDS);

        Subscriber<Long> subscriber1 = new Subscriber<Long>() {

            @Override
            public void onCompleted() {
                LogUtil.d("subscriber1 onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                LogUtil.e(e.toString());
            }

            @Override
            public void onNext(Long aLong) {
                LogUtil.d("subscriber1: " + aLong);
            }
        };

        Subscriber<Long> subscriber2 = new Subscriber<Long>() {

            @Override
            public void onCompleted() {
                LogUtil.d("subscriber2 onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                LogUtil.e(e.toString());
            }

            @Override
            public void onNext(Long aLong) {
                LogUtil.d("subscriber2: " + aLong);

                if (aLong == 5) {
                    replay.subscribe(subscriber1);
                }
            }
        };


        replay.subscribe(subscriber2);
        Subscription connect = replay.connect();

        new Handler().postDelayed(connect::unsubscribe, 10000);
    }

    /**
     * RefCount操作符就是将一个Connectable Observable 对象再重新转化为一个普通的Observable对象，这时候如果由订阅者进行订阅将会触发数据的发射。
     */
    private void refCount() {
        Subscription subscribe = publishObservable()
                .refCount()
                .subscribe(getSubscriber());

        new Handler().postDelayed(subscribe::unsubscribe, 6000);

    }

    /**
     * Connect操作符就是用来触发Connectable Observable发射数据的。
     * 应用Connect操作符后会返回一个Subscription对象，通过这个Subscription对象，我们可以调用其unsubscribe方法来终止数据的发射。
     * 另外，如果还没有订阅者订阅的时候就应用Connect操作符也是可以使其开始发射数据的。
     */
    private void connect() {

        ConnectableObservable<Long> longConnectableObservable = publishObservable();
        longConnectableObservable.subscribe(getSubscriber());

        //调用了connect才发射数据
        Subscription connect = longConnectableObservable.connect();

        new Handler().postDelayed(connect::unsubscribe, 5000);


    }

    private ConnectableObservable<Long> publishObservable() {
        return Observable
                .interval(1, TimeUnit.SECONDS)
                .compose(bindToLifecycle())
                .publish();
    }


}
