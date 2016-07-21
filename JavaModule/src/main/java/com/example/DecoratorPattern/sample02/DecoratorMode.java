package com.example.DecoratorPattern.sample02;

/**
 * auther: baiiu
 * time: 16/7/21 21 23:26
 * description:
 */
public class DecoratorMode extends DecoratorEncryption {


    public DecoratorMode(IEncrypt encryption) {
        super(encryption);
    }

    @Override public String encrypt() {
        return "求模加密" + super.encrypt();
    }
}
