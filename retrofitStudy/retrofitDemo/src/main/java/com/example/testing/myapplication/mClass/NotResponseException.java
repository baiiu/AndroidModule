package com.example.testing.myapplication.mClass;

/**
 * author: baiiu
 * date: on 16/8/11 15:06
 * description:
 */
public class NotResponseException extends RuntimeException {

    public NotResponseException() {
    }

    public NotResponseException(String detailMessage) {
        super(detailMessage);
    }


}
