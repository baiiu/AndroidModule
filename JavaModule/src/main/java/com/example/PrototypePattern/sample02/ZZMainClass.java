package com.example.PrototypePattern.sample02;

/**
 * author: baiiu
 * date: on 16/7/20 10:55
 * description:
 * 设计并实现一个客户类Customer，其中包含一个名为客户地址的成员变量，客户地址的类型为Address，
 * 用浅克隆和深克隆分别实现Customer对象的复制并比较这两种克隆方式的异同。
 */
public class ZZMainClass {

    public static void main(String[] args) {
        Customer customer = new Customer();
        customer.name = "小明";
        customer.address = new Address();
        customer.address.string = "在哪里";

        Customer clone = customer.clone();
    }

}
