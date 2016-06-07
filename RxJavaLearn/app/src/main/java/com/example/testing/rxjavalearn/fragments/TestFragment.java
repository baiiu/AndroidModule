package com.example.testing.rxjavalearn.fragments;

import android.os.Bundle;

import com.example.testing.rxjavalearn.operators.BaseFragment;
import com.example.testing.rxjavalearn.util.LogUtil;

import rx.Observable;
import rx.Subscriber;

/**
 * author: baiiu
 * date: on 16/6/7 17:02
 * description:
 */
public class TestFragment extends BaseFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        filterTest();
    }

    /**
     * filter reture false后直接回调onComplete,不会走onError.
     */
    private void filterTest() {
        Integer[] ints = new Integer[]{1, 2, 3, 4};

        Observable.from(ints)
                .filter(integer -> integer < 3)
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.d("onComplete");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e(e.toString());
                    }

                    @Override
                    public void onNext(Integer integer) {
                        LogUtil.d(integer);
                    }
                });


    }
}
