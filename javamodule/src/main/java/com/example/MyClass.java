package com.example;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class MyClass {


    public static void main(String[] args) {


        ITest test =
                (ITest) Proxy.newProxyInstance(ITest.class.getClassLoader(), new Class[] { ITest.class },
                                               new InvocationHandler() {
                                                   @Override
                                                   public Object invoke(Object proxy, Method method,
                                                           Object[] args) throws Throwable {

                                                       int a = (int) args[0];
                                                       int b = (int) args[1];


                                                       System.out.println("方法名: " + method.getName());
                                                       System.out.println("参数: " + a + " , " + b);

                                                       GET annotation = method.getAnnotation(GET.class);
                                                       System.out.println("这是GET注解: " + annotation.value());


                                                       return a + b;
                                                   }
                                               });

        System.out.println("返回值: " + test.add(1, 3));

    }

}


