package com.example.testing.rxjavalearn.rxEncapsulated;

import rx.functions.Func1;

/**
 * author: baiiu
 * date: on 16/8/27 12:02
 * description:
 */
public class Funcs {

    public static <T> Func1<T, Boolean> notNull() {
        return t -> t != null;
    }

    public static <T> Func1<ApiResponse<T>, T> extractResponse() {
        return apiResponse -> {
            if (CommonUtil.isNotResponse(apiResponse)) {
                if (apiResponse == null) {
                    throw new ApiResponseException("网络错误");
                } else {

                    //exception egine
                    if (apiResponse.code == 1024 || apiResponse.code == 1025) {
                    }

                    throw new ApiResponseException(apiResponse.msg);
                }
            }

            return apiResponse.data;
        };
    }

}
