package com.example.BridgePattern.sample02;

/**
 * author: baiiu
 * date: on 16/7/21 11:01
 * description:
 */
public class OSWindows implements OS {
    @Override public void draw(Matrix matrix) {
        System.out.println("windows draw" + matrix.toString());
    }

    @Override public String toString() {
        return "windows os";
    }
}
