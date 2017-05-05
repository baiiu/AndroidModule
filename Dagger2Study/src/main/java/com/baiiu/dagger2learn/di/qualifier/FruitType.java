package com.baiiu.dagger2learn.di.qualifier;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.inject.Qualifier;

/**
 * auther: baiiu
 * time: 16/6/12 12 23:23
 * description:
 */
/*
    使用@Qualifier自定义标识
 */

@Qualifier
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface FruitType {
    int value();
}
