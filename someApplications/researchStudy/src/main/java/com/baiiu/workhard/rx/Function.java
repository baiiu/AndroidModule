package com.baiiu.workhard.rx;

/**
 * author: zhuzhe
 * time: 2018/12/18
 * description:
 */
interface Function<T, R> {
    R apply(T t);
}
