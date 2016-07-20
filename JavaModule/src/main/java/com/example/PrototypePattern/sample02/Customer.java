package com.example.PrototypePattern.sample02;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * author: baiiu
 * date: on 16/7/20 10:55
 * description:
 */
public class Customer implements Cloneable, Serializable {
    public String name;
    public Address address;


    //@Override protected Customer clone() {
    //    Object o = null;
    //
    //    try {
    //        super.clone();
    //    } catch (CloneNotSupportedException e) {
    //        e.printStackTrace();
    //    }
    //
    //    return (Customer) o;
    //}


    @Override protected Customer clone() {
        try {
            //将对象写入流中
            ByteArrayOutputStream bao = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bao);
            oos.writeObject(this);

            //将对象从流中取出
            ByteArrayInputStream bis = new ByteArrayInputStream(bao.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bis);
            return (Customer) ois.readObject();
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return null;
    }
}
