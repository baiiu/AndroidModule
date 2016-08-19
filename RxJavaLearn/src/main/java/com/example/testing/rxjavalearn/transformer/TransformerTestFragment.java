package com.example.testing.rxjavalearn.transformer;

import android.os.Bundle;
import com.example.testing.rxjavalearn.operators.BaseFragment;
import rx.Observable;

/**
 * author: baiiu
 * date: on 16/8/17 10:38
 * description:
 */
public class TransformerTestFragment extends BaseFragment {

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //exceptionTransformerInteger();
        exceptionTransformerString();
    }

    private void exceptionTransformerString() {
        Observable.just("1", "2", "3", "4")
                .filter(s -> {
                    if ("3".equals(s)) {
                        throw new IllegalStateException("不能是3");
                    }

                    return true;
                })
                .compose(bindToLifecycle())
                .compose(Transformers.catchExceptionToNull())
                .compose(Transformers.switchSchedulers())
                .subscribe(getSubscriber());

    }

    private void exceptionTransformerInteger() {
        Observable.range(0, 10)
                .filter(integer -> {
                    if (integer == 3) {
                        throw new IllegalStateException("不能是3");
                    }

                    return true;
                })
                .compose(bindToLifecycle())
                .compose(Transformers.catchExceptionToNull())
                .subscribe(getSubscriber());
    }

}
