package com.example.BridgePattern.sample02;

/**
 * author: baiiu
 * date: on 16/7/21 10:50
 * description:
 */
public class Matrix {

    public String type;

    public Matrix(String type) {
        this.type = type;
    }

    @Override public String toString() {
        return "Matrix{" +
                "type='" + type + '\'' +
                '}';
    }
}
