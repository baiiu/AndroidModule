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


    @Inject
    public OnePerson() {
        this.name = "name";
        this.age = "age";
    }

    @Override
    public String toString() {
        return "OnePerson{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                '}';
    }
}
