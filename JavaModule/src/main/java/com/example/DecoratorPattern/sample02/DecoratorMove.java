package com.example.DecoratorPattern.sample02;

/**
 * auther: baiiu
 * time: 16/7/21 21 23:26
 * description:
 */
public class DecoratorMove extends DecoratorEncryption {


    public DecoratorMove(IEncrypt encryption) {
        super(encryption);
    }

    @Override public String encrypt() {
        return "移位加密" + super.encrypt();
    }
}
