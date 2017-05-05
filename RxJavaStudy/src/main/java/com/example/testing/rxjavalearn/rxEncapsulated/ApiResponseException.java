package com.example.testing.rxjavalearn.rxEncapsulated;

/**
 * author: baiiu
 * date: on 16/8/19 14:41
 * description:
 */
public class ApiResponseException extends RuntimeException {

    public ApiResponseException(String detailMessage) {
        super(detailMessage);
    }
}
