package com.example.ProxyPattern.sample02;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * author: baiiu
 * date: on 16/7/22 17:04
 * description: 动态代理增加方法,解耦
 */
public class ProxyHandler implements InvocationHandler {
    private Subject subject;

    public ProxyHandler() {
        this.subject = new RealSubject();
    }

    @Override public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("before====");
        Object invoke = method.invoke(subject, args);
        System.out.println("after====");

        return invoke;
    }
}
