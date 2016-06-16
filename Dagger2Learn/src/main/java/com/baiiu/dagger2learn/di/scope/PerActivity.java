package com.baiiu.dagger2learn.di.scope;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * auther: baiiu
 * time: 16/6/12 12 22:26
 * description:
 */
@Scope
@Documented
@Retention(RUNTIME)
public @interface PerActivity {}
