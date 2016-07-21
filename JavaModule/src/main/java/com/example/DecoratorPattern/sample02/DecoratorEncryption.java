package com.example.DecoratorPattern.sample02;

/**
 * auther: baiiu
 * time: 16/7/21 21 23:26
 * description:
 */
public class DecoratorEncryption implements IEncrypt {

    protected IEncrypt encryption;

    public DecoratorEncryption(IEncrypt encryption) {
        this.encryption = encryption;
    }

    @Override public String encrypt() {
        return encryption.encrypt();
    }
}
