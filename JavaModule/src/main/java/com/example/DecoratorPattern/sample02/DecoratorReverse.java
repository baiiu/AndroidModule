package com.example.DecoratorPattern.sample02;

/**
 * auther: baiiu
 * time: 16/7/21 21 23:26
 * description:
 */
public class DecoratorReverse extends DecoratorEncryption {


    public DecoratorReverse(IEncrypt encryption) {
        super(encryption);
    }

    @Override public String encrypt() {
        return "逆向加密" + super.encrypt();
    }
}
