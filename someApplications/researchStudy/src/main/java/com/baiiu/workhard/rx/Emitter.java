package com.baiiu.workhard.rx;

/**
 * author: zhuzhe
 * time: 2018/12/18
 * description: 数据发送者，被观察者
 */
interface Emitter<T> {
    void onNext(T t);

    void onComplete();

    void onError(Throwable e);
}