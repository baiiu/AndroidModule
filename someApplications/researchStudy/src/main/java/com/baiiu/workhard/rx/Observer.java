package com.baiiu.workhard.rx;

/**
 * author: zhuzhe
 * time: 2018/12/18
 * description:
 */
// 数据接收者，观察者
interface Observer<T> extends Emitter<T> {
    // 观察上了
    void onSubscription();
}