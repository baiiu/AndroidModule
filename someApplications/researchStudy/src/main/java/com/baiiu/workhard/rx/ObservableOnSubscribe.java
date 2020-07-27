package com.baiiu.workhard.rx;

/**
 * author: zhuzhe
 * time: 2018/12/18
 * description:
 */
interface ObservableOnSubscribe<T> {
    void subscribe(Emitter<T> emitter);
}