package com.example.AAproxy;

/**
 * author: baiiu
 * date: on 16/6/28 15:32
 * description: 之后再学习这块
 */
public interface ITest {
    @GET("String GET")
    String addString(String a, String b);



    @GET("int GET")
    int add(int a, int b);



}
