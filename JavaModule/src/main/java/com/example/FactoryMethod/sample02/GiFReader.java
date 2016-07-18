package com.example.FactoryMethod.sample02;

/**
 * auther: baiiu
 * time: 16/7/19 19 06:41
 * description:
 */
public class GIFReader implements ImageReader {
    @Override public void read() {
        System.out.println("读取GIF");
    }
}
