package com.example.SimpleFactory.sample02;

/**
 * author: baiiu
 * date: on 16/7/18 19:48
 * description:
 */
public class UnSupportedShapeException extends RuntimeException {

    public UnSupportedShapeException() {
        super("does not support this shape");
    }

    public UnSupportedShapeException(String message) {
        super(message);
    }
}
