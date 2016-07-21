package com.example.BridgePattern.sample02;

/**
 * author: baiiu
 * date: on 16/7/21 10:57
 * description:
 */
public abstract class Image {
    OS os;

    public Image(OS os) {
        this.os = os;
    }

    public void setOs(OS os) {
        this.os = os;
    }

    public abstract Matrix parseFile(String filePath);

    public void draw(String filePath) {
        os.draw(parseFile(filePath));
    }

}
