//
// Created by baiiu on 2021/7/8.
//
#include "jni.h"
#include <netdb.h>
#include <cstdio>
#include <log.h>

extern "C"
JNIEXPORT void JNICALL
Java_com_baiiu_jnitest_dns_DNSFragment_nativeTest(JNIEnv *env, jobject thiz) {
    struct addrinfo hints = {0}, *ai, *cur_ai;
    hints.ai_family = AF_INET;
//    hints.ai_socktype = SOCK_STREAM;


    const char *hostname = "mtplatform-tx-flv.meituan.net";
    char portstr[10];
    int port = 443;
    snprintf(portstr, sizeof(portstr), "%d", port);

    int64_t dns_starttime = av_gettime_relative();
    int ret = getaddrinfo(hostname, portstr, &hints, &ai);
    if (ret) {
        __android_log_print(ANDROID_LOG_DEBUG, "mLogU", "error: %d, %s", ret, gai_strerror(ret));
    }
    int64_t dnsCost = (av_gettime_relative() - dns_starttime) / 1000;
    __android_log_print(ANDROID_LOG_DEBUG, "mLogU", "native dnsCost: %lld\n", dnsCost);

    freeaddrinfo(ai);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_baiiu_jnitest_dns_DNSFragment_nativeTest4(JNIEnv *env, jobject thiz) {
    struct addrinfo hints = {0}, *ai, *cur_ai;
    hints.ai_family = AF_INET; // 为0表示v4和v6都去查找
//    hints.ai_socktype = SOCK_STREAM;


    const char *hostname = "mtplatform-tx-flv.meituan.net";
    char portstr[10];
    int port = 443;
    snprintf(portstr, sizeof(portstr), "%d", port);

    int64_t dns_starttime = av_gettime_relative();
    int ret = getaddrinfo(hostname, portstr, &hints, &ai);
    if (ret) {
        __android_log_print(ANDROID_LOG_DEBUG, "mLogU", "error: %d, %s", ret, gai_strerror(ret));
    }
    int64_t dnsCost = (av_gettime_relative() - dns_starttime) / 1000;
    __android_log_print(ANDROID_LOG_DEBUG, "mLogU", "ipv4 native dnsCost: %lld\n", dnsCost);

    freeaddrinfo(ai);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_baiiu_jnitest_dns_DNSFragment_nativeTest6(JNIEnv *env, jobject thiz) {
    struct addrinfo hints = {0}, *ai, *cur_ai;
    hints.ai_family = AF_INET6;
//    hints.ai_socktype = SOCK_STREAM;


    const char *hostname = "mtplatform-tx-flv.meituan.net";
    char portstr[10];
    int port = 443;
    snprintf(portstr, sizeof(portstr), "%d", port);

    int64_t dns_starttime = av_gettime_relative();
    int ret = getaddrinfo(hostname, portstr, &hints, &ai);
    if (ret) {
        __android_log_print(ANDROID_LOG_DEBUG, "mLogU", "error: %d, %s", ret, gai_strerror(ret));
    }
    int64_t dnsCost = (av_gettime_relative() - dns_starttime) / 1000;
    __android_log_print(ANDROID_LOG_DEBUG, "mLogU", "ipv6 native dnsCost: %lld\n", dnsCost);

    freeaddrinfo(ai);
}