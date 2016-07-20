package com.example.PrototypePattern.DeepClone;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * author: baiiu
 * date: on 16/7/20 10:21
 * description:
 */
public class DeepConcretePrototype implements Cloneable, Serializable {
    public String name;
    public DeepInnerObject innerObject;

    @Override protected DeepConcretePrototype clone() {
        try {
            //将对象写入流中
            ByteArrayOutputStream bao = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bao);
            oos.writeObject(this);

            //将对象从流中取出
            ByteArrayInputStream bis = new ByteArrayInputStream(bao.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bis);
            return (DeepConcretePrototype) ois.readObject();
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return null;
    }
}
