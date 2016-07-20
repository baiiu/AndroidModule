package com.example.PrototypePattern.ShallowClone;

/**
 * author: baiiu
 * date: on 16/7/20 10:21
 * description:
 */
public class ConcretePrototype implements Cloneable {
    public String name;
    public InnerObject innerObject;

    @Override protected ConcretePrototype clone() {
        Object object = null;
        try {
            object = super.clone();
        } catch (CloneNotSupportedException e) {
            System.out.println(e.toString());
        }

        return (ConcretePrototype) object;
    }
}
