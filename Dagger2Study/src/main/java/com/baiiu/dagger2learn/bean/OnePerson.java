package com.baiiu.dagger2learn.bean;

import javax.inject.Inject;

/**
 * author: baiiu
 * date: on 16/6/12 19:38
 * description:
 */
public class OnePerson {
    public String name;
    public String age;


    /*
        当两者同时都能提供依赖时,优先Module.
     */
    @Inject public OnePerson() {
        this.name = "name";
        this.age = "age";
    }

    /*
        不能添加@Inject注解: Error:(20, 20) 错误: Types may only contain one @Inject constructor.

        通过 @Module 使用 @Provides 注入
     */
    public OnePerson(String name, String age) {
        this.name = name;
        this.age = age;
    }

    @Override public String toString() {
        return "OnePerson{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                '}';
    }
}
