//
// Created by baiiu on 2021/4/19.
//

#include "jni.h"
#include <android/log.h>

#ifdef ENABLE_FIBO

#include "fibo.h"

#endif

extern "C"
JNIEXPORT jstring JNICALL
Java_com_baiiu_cmaketest_MainActivity_hello(JNIEnv *env, jobject thiz) {
#ifdef ENABLE_LOG
    __android_log_print(ANDROID_LOG_ERROR, "mLogU", "log");
    return env->NewStringUTF("hello with log");
#endif

#ifdef ENABLE_FIBO
    // 1 1 2 3 5 8
    __android_log_print(ANDROID_LOG_ERROR, "mLogU", "log: %d", test(5));
#endif

    return env->NewStringUTF("hello");
}