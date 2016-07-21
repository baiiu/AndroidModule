package com.example.BridgePattern.sample02;

/**
 * author: baiiu
 * date: on 16/7/21 11:50
 * description:
 */
public class ImageGIF extends Image {
    public ImageGIF(OS os) {
        super(os);
    }

    @Override public Matrix parseFile(String filePath) {
        System.out.println(os.toString() + "解析JPG");
        return new Matrix("JPG");
    }
}
