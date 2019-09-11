package com.baiiu.rxjava2study;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.baiiu.library.LogUtil;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * author: baiiu
 * date: on 17/5/12 11:38
 * description:
 */
class CreateFragment extends Fragment {


    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Observable.just("abc")
                .subscribe(new Consumer<String>() {
                    @Override public void accept(@NonNull String s) throws Exception {
                        LogUtil.d("accept:" + s);
                    }
                }, new Consumer<Throwable>() {
                    @Override public void accept(@NonNull Throwable throwable) throws Exception {
                        LogUtil.d("accept:" + throwable.toString());

                    }
                }, new Action() {
                    @Override public void run() throws Exception {
                        LogUtil.d("onComplete");
                    }
                });

        Observable.just("a")
                .subscribe(System.out::println);


        Observable.just("ab")
                .subscribe(new Observer<String>() {
                    @Override public void onSubscribe(@NonNull Disposable d) {
                        LogUtil.d("onSubscribe");
                    }

                    @Override public void onNext(@NonNull String s) {
                        LogUtil.d("onNext: " + s);
                    }

                    @Override public void onError(@NonNull Throwable e) {
                        LogUtil.d("onError: " + e.toString());
                    }

                    @Override public void onComplete() {
                        LogUtil.d("onComplete");
                    }
                });

        Single.just("e")
                .subscribe(new Consumer<String>() {
                    @Override public void accept(@NonNull String s) throws Exception {
                        LogUtil.d(s);
                    }
                });

        //Flowable.create(new FlowableOnSubscribe<String>() {
        //    @Override public void subscribe(@NonNull FlowableEmitter<String> emitter) throws Exception {
        //
        //    }
        //}, BackpressureStrategy.DROP)
        //        .subscribe(new FlowableSubscriber<String>() {
        //            @Override public void onSubscribe(@NonNull Subscription s) {
        //
        //            }
        //
        //            @Override public void onNext(String s) {
        //
        //            }
        //
        //            @Override public void onError(Throwable t) {
        //
        //            }
        //
        //            @Override public void onComplete() {
        //
        //            }
        //        });
    }
}
