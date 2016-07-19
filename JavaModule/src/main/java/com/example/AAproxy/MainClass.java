package com.example.AAproxy;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 动态代理练习代码,仿Retrofit.
 */
public class MainClass {

    public static void main(String[] args) {
        ITest test = (ITest) Proxy.newProxyInstance(

                ITest.class.getClassLoader(), new Class[] { ITest.class }, new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        String methodName = method.getName();

                        System.out.println("方法名: " + methodName);

                        if ("add".equals(methodName)) {
                            int a = (int) args[0];
                            int b = (int) args[1];

                            System.out.println("参数: " + a + " , " + b);

                            GET annotation = method.getAnnotation(GET.class);
                            System.out.println("这是GET注解: " + annotation.value());

                            System.out.println(method.getGenericReturnType());

                            return a + b;
                        } else if ("addString".equals(methodName)) {

                            String a = (String) args[0];
                            String b = (String) args[1];

                            System.out.println(method.getGenericReturnType());

                            return a + " ," + b;
                        }

                        return null;
                    }
                });

        System.out.println("返回值: " + test.add(1, 3));
        System.out.println("返回值: " + test.addString("1", "3"));
    }

}


