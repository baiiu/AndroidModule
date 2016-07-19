package com.example.AAproxy;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * author: baiiu
 * date: on 16/6/28 15:32
 * description:
 */
@Documented
@Target(METHOD)
@Retention(RUNTIME)
public @interface GET {
    String value() default "";
}