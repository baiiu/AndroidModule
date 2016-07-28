package com.example.ProxyPattern.sample01;

/**
 * author: baiiu
 * date: on 16/7/22 16:44
 * description:
 */
public class RealSearch implements ISearch {

    @Override public void doSearch() {
        System.out.println("真正的Search商务信息");
    }
}
