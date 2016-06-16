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
@Scope
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface PerFragment {}
