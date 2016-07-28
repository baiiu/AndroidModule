package com.example.ProxyPattern.sample02;

import java.lang.reflect.Proxy;

/**
 * auther: baiiu
 * time: 16/7/21 21 22:54
 * description: 动态代理增加方法.
 *
 * 好处:
 * 1.不需要为**Subject（抽象主题角色）**写一个形式上完全一样的封装类，如果主题接口中的方法很多，为每一个接口写一个代理方法也很麻烦。
 * 2. 如果接口有变动，则真实主题和代理类都要修改，不利于系统维护；
 * 3. 使用一些动态代理的生成方法甚至可以在运行时制定代理类的执行逻辑，从而大大提升系统的灵活性。
 */

public class ZZMainClass {

    public static void main(String[] args) {

        Subject subject = (Subject) Proxy.newProxyInstance(Subject.class.getClassLoader(), new Class[] {
                Subject.class
        }, new ProxyHandler());

        subject.request();


        subject = new ProxySubject();
        subject.request();
    }
}
