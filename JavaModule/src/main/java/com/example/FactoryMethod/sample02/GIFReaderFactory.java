package com.example.FactoryMethod.sample02;

/**
 * auther: baiiu
 * time: 16/7/19 19 06:43
 * description:
 */
public class GIFReaderFactory extends ImageReaderFactory {
    @Override public ImageReader createImageReader() {
        System.out.println("初始化GIFReader");
        return new GIFReader();
    }
}
