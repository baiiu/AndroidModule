package com.example.PrototypePattern.sample01;

/**
 * author: baiiu
 * date: on 16/7/20 10:14
 * description:
 */
public class ConcretePrototypeA implements Prototype {
    private String attr;

    public String getAttr() {
        return attr;
    }

    public void setAttr(String attr) {
        this.attr = attr;
    }

    @Override public ConcretePrototypeA clone() {
        ConcretePrototypeA concretePrototype = new ConcretePrototypeA();
        concretePrototype.setAttr(this.attr);
        return concretePrototype;
    }
}