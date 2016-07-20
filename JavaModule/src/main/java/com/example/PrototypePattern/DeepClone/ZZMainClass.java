package com.example.PrototypePattern.DeepClone;

import com.example.PrototypePattern.ShallowClone.InnerObject;

/**
 * author: baiiu
 * date: on 16/7/20 10:17
 * description:
 */
public class ZZMainClass {

    public static void main(String[] args) {
        DeepConcretePrototype concretePrototype = new DeepConcretePrototype();
        concretePrototype.name = "哈哈";
        concretePrototype.innerObject = new DeepInnerObject();
        concretePrototype.innerObject.inner = "inner";
        concretePrototype.innerObject.innerInnerObject = new InnerInnerObject();
        concretePrototype.innerObject.innerInnerObject.innerInnerString = "InnerInner";
        concretePrototype.innerObject.innerInnerObject.innerObject = new InnerObject();


        DeepConcretePrototype clone = concretePrototype.clone();

        System.out.println(clone.name);
        System.out.println(clone == concretePrototype);
        System.out.println(clone.innerObject == concretePrototype.innerObject);
        System.out.println(
                clone.innerObject.innerInnerObject == concretePrototype.innerObject.innerInnerObject);
        System.out.println(clone.innerObject.innerInnerObject.innerObject
                                   == concretePrototype.innerObject.innerInnerObject.innerObject);

    }
}
