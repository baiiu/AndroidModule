package com.example.PrototypePattern.ShallowClone;

/**
 * author: baiiu
 * date: on 16/7/20 10:17
 * description:
 */
public class ZZMainClass {

    public static void main(String[] args) {
        ConcretePrototype concretePrototype = new ConcretePrototype();
        concretePrototype.name = "哈哈";
        concretePrototype.innerObject = new InnerObject();
        concretePrototype.innerObject.inner = "inner";

        ConcretePrototype clone = concretePrototype.clone();

        System.out.println(clone.name);
        System.out.println(clone == concretePrototype);
        System.out.println(clone.innerObject == concretePrototype.innerObject);

    }
}
