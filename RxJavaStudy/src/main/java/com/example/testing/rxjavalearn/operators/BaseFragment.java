package com.example.testing.rxjavalearn.operators;

import com.example.testing.rxjavalearn.util.LogUtil;
import com.trello.rxlifecycle.components.support.RxFragment;
import rx.Subscriber;

/**
 * auther: baiiu
 * time: 16/6/6 06 22:10
 * description:
 */
public class BaseFragment extends RxFragment {


    protected <T> Subscriber<T> getSubscriber() {
        return new Subscriber<T>() {
            @Override public void onCompleted() {
                LogUtil.d("onCompleted");
            }

            @Override public void onError(Throwable e) {
                LogUtil.e("错误了: " + e.toString());
            }

            @Override public void onNext(T t) {
                LogUtil.d(String.valueOf(t));
            }
        };
    }

}
