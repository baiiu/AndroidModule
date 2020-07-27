package com.baiiu.workhard.rx;

/**
 * author: zhuzhe
 * time: 2018/12/18
 * description:
 */
interface Prediction<T> {
    boolean filter(T t);
}
