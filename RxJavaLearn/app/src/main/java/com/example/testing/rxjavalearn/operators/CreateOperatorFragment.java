package com.example.testing.rxjavalearn.operators;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.testing.rxjavalearn.R;
import com.example.testing.rxjavalearn.util.LogUtil;
import com.jakewharton.rxbinding.view.RxView;
import com.orhanobut.logger.Logger;
import com.trello.rxlifecycle.FragmentEvent;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;

/**
 * auther: baiiu
 * time: 16/6/6 06 22:03
 * description:
 */
public class CreateOperatorFragment extends BaseFragment {

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

//        intClick();

        Observable<Long> defer = defer();
        Observable<Long> just = just();
        longClick(just);
    }


    //======================================================================================
    //======================================================================================
    private void longClick(Observable<Long> observable) {
        RxView.clicks(bt_create)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .compose(this.bindUntilEvent(FragmentEvent.DESTROY))
                .flatMap(aVoid -> observable)
                .subscribe(LogUtil::d);
    }

    /**
     * defer 为每一个观察者创建一个*新的*Observable
     */
    private Observable<Long> defer() {
        return Observable.defer(() -> Observable.just(System.currentTimeMillis()));
    }

    private Observable<Long> just() {
        return Observable.just(System.currentTimeMillis());
    }


    //======================================================================================
    //======================================================================================

    private void intClick() {
        RxView.clicks(bt_create)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .compose(this.bindUntilEvent(FragmentEvent.DESTROY))
//                .flatMap(aVoid -> createOperator())
                .flatMap(aVoid -> rangeOperator())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        Logger.d("onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d("onError " + e.toString());
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Logger.d("" + integer);
                    }
                });

    }

    /**
     * range操作符,发射从start开始的count个数
     */
    private Observable<Integer> rangeOperator() {
        return Observable.range(4, 8);
    }


    /**
     * create操作符
     */
    private Observable<Integer> createOperator() {
        return Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                if (!subscriber.isUnsubscribed()) {
                    for (int i = 0; i < 3; ++i) {
                        int temp = new Random().nextInt(10);

                        if (temp > 5) {
                            subscriber.onError(new Throwable("integer bigger than 8"));
                        } else {
                            subscriber.onNext(temp);
                        }
                    }

                    subscriber.onCompleted();

                }
            }
        });
    }


}
