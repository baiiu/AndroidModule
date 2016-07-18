package com.example.FactoryMethod.sample02;

/**
 * auther: baiiu
 * time: 16/7/19 19 06:42
 * description:
 */
public abstract class ImageReaderFactory {

    public void read() {
        ImageReader imageReader = createImageReader();
        imageReader.read();
    }

    public abstract ImageReader createImageReader();

}
