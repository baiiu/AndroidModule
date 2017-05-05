package com.baiiu.dagger2learn.di.scope;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.inject.Scope;

/**
 * author: baiiu
 * date: on 16/6/13 10:57
 * description:
 */
/*
    使用 @Scope 自定义作用域

    添加在依赖上
    同时添加在相应的Component上,该Scope的生命周期依赖于Component生命周期.
 */
@Scope
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface PerApp {}
