package com.baiiu.commontool.net.retrofit.interceptor;

import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * auther: baiiu
 * time: 16/5/17 17 07:31
 * description:
 */
public final class UserAgentInterceptor implements Interceptor {
  private static final String USER_AGENT_HEADER_NAME = "User-Agent";
  private final String userAgentHeaderValue;

  public UserAgentInterceptor(String userAgentHeaderValue) {
    this.userAgentHeaderValue = userAgentHeaderValue;
  }

  @Override public Response intercept(Chain chain) throws IOException {
    final Request originalRequest = chain.request();

    final Request requestWithUserAgent = originalRequest.newBuilder()

        //移除先前默认的UA
        .removeHeader(USER_AGENT_HEADER_NAME)

        //设置UA
        .addHeader(USER_AGENT_HEADER_NAME, userAgentHeaderValue)


        .build();
    return chain.proceed(requestWithUserAgent);
  }
}
