package com.example.BridgePattern.sample02;

/**
 * author: baiiu
 * date: on 16/7/21 11:50
 * description:
 */
public class OSUnix implements OS {
    @Override public void draw(Matrix matrix) {
        System.out.println("Unix draw" + matrix.toString());
    }

    @Override public String toString() {
        return "Unix os";
    }
}
