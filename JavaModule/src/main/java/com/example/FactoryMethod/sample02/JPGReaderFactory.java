package com.example.FactoryMethod.sample02;

/**
 * auther: baiiu
 * time: 16/7/19 19 06:44
 * description:
 */
public class JPGReaderFactory extends ImageReaderFactory {
    @Override public ImageReader createImageReader() {
        System.out.println("初始化JPGReader");
        return new JPGReader();
    }
}
